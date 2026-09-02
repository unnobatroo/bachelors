# %% [markdown]
# # Lecture 12 Demo 1: Tokens, Attention Intuition, and Sampling
#
#
# 1. **Real tokenization with `tiktoken`** — integer IDs, byte pieces, and edge cases.
# 2. **Softmax + temperature on a toy next-token distribution** — no GPU, pure Python `math`.
# 3. **Top-p (nucleus)** — see how the tail gets trimmed differently when confidence changes.
# 4. **Attention/context/KV-cache intuition** — why long prompts raise time-to-first-token.
# 5. **Ollama chat** — prompt token accounting and greedy vs sampling on a real local model.
#
# `tiktoken` ships encodings used by OpenAI-style APIs. Your local Ollama model uses a
# *different* tokenizer shipped inside the GGUF bundle; the counts will not match exactly,
# but the *mechanism* (text → IDs → model) is what we are teaching.

# %%
from __future__ import annotations

import math
import random
from collections import Counter
from typing import Sequence

import ollama
import tiktoken

from demo_utils import (
    DEFAULT_MODEL,
    chat_kwargs,
    ensure_ollama_ready,
    kv,
    pretty_metrics,
    response_text,
    section,
)


def softmax(logits: Sequence[float]) -> list[float]:
    """Turn raw scores into probabilities that sum to 1."""

    m = max(logits)
    exps = [math.exp(x - m) for x in logits]
    s = sum(exps)
    return [e / s for e in exps]


def apply_temperature(logits: Sequence[float], temperature: float) -> list[float]:
    """Scale logits before softmax. temperature < 1 sharpens, > 1 flattens."""

    if temperature <= 0:
        raise ValueError("temperature must be > 0 for this demo")
    return [x / temperature for x in logits]


def sample_categorical(probs: Sequence[float], rng: random.Random) -> int:
    """Draw an index according to discrete probabilities."""

    r = rng.random()
    acc = 0.0
    for i, p in enumerate(probs):
        acc += p
        if r <= acc:
            return i
    return len(probs) - 1


def sample_counts(
    labels: Sequence[str],
    probs: Sequence[float],
    *,
    draws: int,
    seed: int,
) -> list[tuple[str, int]]:
    """Draw repeatedly so probability changes are visible in classroom output."""

    rng = random.Random(seed)
    counts = Counter(labels[sample_categorical(probs, rng)] for _ in range(draws))
    return [(label, counts[label]) for label in labels if counts[label]]


def nucleus_mass(probs: Sequence[float], top_p: float) -> list[float]:
    """Keep smallest prefix of sorted-by-probability tokens with cumulative mass >= top_p."""

    if not 0 < top_p <= 1:
        raise ValueError("top_p must be in (0, 1]")
    order = sorted(range(len(probs)), key=lambda i: probs[i], reverse=True)
    kept: list[int] = []
    total = 0.0
    for idx in order:
        kept.append(idx)
        total += probs[idx]
        if total >= top_p:
            break
    mask = [0.0] * len(probs)
    for idx in kept:
        mask[idx] = probs[idx]
    s = sum(mask)
    return [m / s for m in mask]


def probability_table(labels: Sequence[str], probs: Sequence[float]) -> list[tuple[str, float]]:
    """Compact label/probability rows for projected notebook output."""

    return [(label, round(prob, 4)) for label, prob in zip(labels, probs)]


def print_bar_chart(
    title: str,
    rows: Sequence[tuple[str, float]],
    *,
    width: int = 34,
    value_format: str = ".3g",
    value_suffix: str = "",
) -> None:
    """Print a small ASCII bar chart that works in terminals and notebooks."""

    print(f"\n{title}")
    max_value = max((value for _, value in rows), default=0)
    label_width = max((len(label) for label, _ in rows), default=0)

    for label, value in rows:
        bar_len = 0 if max_value == 0 else round((value / max_value) * width)
        bar = "#" * bar_len
        value_text = format(value, value_format)
        print(f"  {label:<{label_width}} | {bar:<{width}} {value_text}{value_suffix}")


ensure_ollama_ready()
section("Versions and model")
kv("chat model", DEFAULT_MODEL)
kv("ollama library", ollama.__version__ if hasattr(ollama, "__version__") else "n/a")
kv("tiktoken", getattr(tiktoken, "__version__", "n/a"))

# %% [markdown]
# ## 1. Tokenization with `tiktoken` (real IDs, not guesses)
#
# We load the `cl100k_base` vocabulary used by many GPT-4 class models. The integer IDs you
# see here are **not** the same integers Ollama uses for `gemma4`, but the pipeline is identical.
# The examples are chosen to show classroom surprises: leading spaces, casing, punctuation,
# code-like text, Unicode bytes, and Hungarian accents.

# %%
section("Token IDs and byte pieces (tiktoken, cl100k_base)")
enc = tiktoken.get_encoding("cl100k_base")

token_samples = [
    ("same visible word", "cat"),
    ("leading space changes the token", " cat"),
    ("case changes the token", "Cat"),
    ("word split by statistics", "unbelievable"),
    ("space creates a different merge", "un believable"),
    ("punctuation changes merges", "data-science"),
    ("Unicode may split inside bytes", "naïve café 😊"),
    ("Hungarian accents cost tokens", "árvíztűrő tükörfúrógép"),
    ("code has its own frequent pieces", "def tokenize(text):\n    return enc.encode(text)"),
]

token_count_rows: list[tuple[str, float]] = []

for note, text in token_samples:
    ids = enc.encode(text)
    pieces = enc.decode_tokens_bytes(ids)
    token_count_rows.append((note, float(len(ids))))
    print(f"\n{note}: {text!r}")
    kv("token count", len(ids))
    print(f"  ids: {ids[:12]}{' ...' if len(ids) > 12 else ''}")
    print(f"  byte pieces: {pieces[:8]}{' ...' if len(pieces) > 8 else ''}")

print_bar_chart("Token counts by example", token_count_rows, width=26, value_format=".0f", value_suffix=" tokens")

print(
    "\nTakeaway: token boundaries follow learned merge statistics. A space, accent, "
    "or punctuation mark can change both IDs and counts, which is why API pricing "
    "and context windows are measured in tokens instead of characters or words."
)

# %% [markdown]
# ## 2. Toy softmax, temperature, and repeated sampling
#
# We use a plausible next-token context so the labels carry meaning:
# `"The capital of France is"` followed by candidate next tokens. The logits are invented
# for readability, but the mechanism is the same as a real language model.

# %%
section("Toy next-token distribution: temperature changes the dice")

prompt_fragment = '"The capital of France is"'
labels = [" Paris", " Lyon", " Marseille", " Berlin", " croissant", " spaceship"]
base_logits = [4.0, 2.7, 1.6, 0.3, -0.4, -1.5]

print("Prompt fragment:", prompt_fragment)
print("Candidate next tokens:", labels)
print("Base logits:", base_logits)
print("Base softmax:", probability_table(labels, softmax(base_logits)))
print_bar_chart("Base probability mass", list(zip(labels, softmax(base_logits))), value_format=".3f", value_suffix=" prob")

for temp in (0.3, 1.0, 1.8):
    tlogits = apply_temperature(base_logits, temp)
    probs = softmax(tlogits)
    draws = sample_counts(labels, probs, draws=200, seed=1200 + int(temp * 10))
    print(f"\nTemperature={temp}")
    print("  adjusted logits:", [round(x, 3) for x in tlogits])
    print("  probs:", probability_table(labels, probs))
    print_bar_chart("  probability shape", list(zip(labels, probs)), value_format=".3f", value_suffix=" prob")
    print_bar_chart(
        "  sampled 200 times",
        [(label, float(count)) for label, count in draws],
        value_format=".0f",
        value_suffix=" draws",
    )

# %% [markdown]
# ## 3. Top-p adapts to confidence
#
# Top-p keeps the smallest high-probability prefix whose cumulative mass reaches the threshold.
# That set is tiny when the model is confident and wider when the model is unsure.

# %%
section("Top-p nucleus: narrow when confident, wider when uncertain")

nucleus_scenarios = [
    (
        "confident: factual next token",
        [0.72, 0.18, 0.06, 0.025, 0.01, 0.005],
    ),
    (
        "uncertain: creative continuation",
        [0.28, 0.22, 0.18, 0.14, 0.10, 0.08],
    ),
]

for scenario, probs in nucleus_scenarios:
    print(f"\n{scenario}")
    print("  original:", probability_table(labels, probs))
    print_bar_chart("  original mass", list(zip(labels, probs)), value_format=".3f", value_suffix=" prob")
    for top_p in (0.5, 0.8, 0.95):
        adj = nucleus_mass(probs, top_p)
        kept_indices = [i for i, prob in enumerate(adj) if prob > 0]
        retained_mass = sum(probs[i] for i in kept_indices)
        kept = [(labels[i], round(adj[i], 3)) for i in kept_indices]
        print(f"  top_p={top_p}: kept={kept}, raw mass={retained_mass:.3f}")
        print_bar_chart(
            "    renormalized nucleus",
            [(labels[i], adj[i]) for i in kept_indices],
            width=24,
            value_format=".3f",
            value_suffix=" prob",
        )

# %% [markdown]
# ## 4. Attention, context budget, and KV-cache intuition
#
# This is intentionally not a transformer implementation. It gives students a
# runnable mental model for the new slide: long prompts cost work during prefill,
# then the runtime can reuse cached key/value vectors while generating new tokens.

# %%
section("Context budget and KV-cache intuition")


def rough_attention_pairs(prompt_tokens: int, generated_tokens: int) -> dict[str, int]:
    """Toy cost proxy for classroom discussion, not a hardware benchmark."""

    prefill_pairs = prompt_tokens * prompt_tokens
    cached_generation_pairs = sum(prompt_tokens + i for i in range(generated_tokens))
    no_cache_generation_pairs = sum((prompt_tokens + i) ** 2 for i in range(generated_tokens))
    return {
        "prompt_tokens": prompt_tokens,
        "generated_tokens": generated_tokens,
        "prefill_pairs": prefill_pairs,
        "cached_generation_pairs": cached_generation_pairs,
        "no_cache_generation_pairs": no_cache_generation_pairs,
    }


baseline_prefill = rough_attention_pairs(prompt_tokens=64, generated_tokens=32)["prefill_pairs"]
ttft_rows: list[tuple[str, float]] = []

for prompt_tokens in (64, 512, 2048):
    costs = rough_attention_pairs(prompt_tokens=prompt_tokens, generated_tokens=32)
    cache_savings = costs["no_cache_generation_pairs"] / costs["cached_generation_pairs"]
    ttft_multiple = costs["prefill_pairs"] / baseline_prefill
    ttft_rows.append((f"{prompt_tokens} tokens", ttft_multiple))
    print(f"\nPrompt length: {prompt_tokens} tokens")
    kv("prefill pair proxy", f"{costs['prefill_pairs']:,}")
    kv("TTFT vs 64-token prompt", f"{ttft_multiple:.0f}x")
    kv("cached generation proxy", f"{costs['cached_generation_pairs']:,}")
    kv("without cache proxy", f"{costs['no_cache_generation_pairs']:,}")
    kv("cache saves during generation", f"{cache_savings:.0f}x in this toy proxy")

print_bar_chart(
    "TTFT proxy grows quadratically with prompt length",
    ttft_rows,
    value_format=".0f",
    value_suffix="x",
)

print(
    "\nTakeaway: context construction is an engineering decision. "
    "Send the useful facts, examples, and constraints, but do not dump everything by habit."
)

# %% [markdown]
# ## 5. Ollama: prompt token proxy + greedy vs creative chat
#
# `prompt_eval_count` is Ollama's accounting of how many tokens the **prompt** became after
# templating. Compare strings without chasing exact tiktoken parity.

# %%
section("Approx prompt token cost via Ollama metrics")

prompt_cost_samples = [
    ("tiny user message", "Hi"),
    ("short task", "Summarize: revenue up 8%, churn down 2%."),
    (
        "task plus useful context",
        (
            "Use these facts:\n"
            "- revenue rose 8% this quarter\n"
            "- churn fell 2%\n"
            "- support tickets are flat\n"
            "Write a two-sentence update for the team."
        ),
    ),
    (
        "structured output contract",
        (
            "Classify this ticket as bug, billing, or feature. "
            "Return JSON with keys category and confidence. "
            "Ticket: The invoice total changed after I updated my VAT number."
        ),
    ),
]

prompt_cost_rows: list[tuple[str, float]] = []

for label, text in prompt_cost_samples:
    response = ollama.chat(
        **chat_kwargs(
            model=DEFAULT_MODEL,
            messages=[{"role": "user", "content": text}],
            options={"temperature": 0, "num_predict": 1},
        )
    )
    metrics = pretty_metrics(response)
    prompt_cost_rows.append((label, float(metrics["prompt_eval_count"] or 0)))
    print(f"\n{label}")
    kv("tiktoken count", len(enc.encode(text)))
    kv("Ollama prompt tokens", metrics["prompt_eval_count"])

print_bar_chart("Ollama prompt-token cost", prompt_cost_rows, value_format=".0f", value_suffix=" tokens")

print(
    "\nObservation: Ollama counts the chat template and uses the model's own GGUF tokenizer. "
    "The exact numbers differ from tiktoken, but both make the same engineering point: "
    "extra context is not free."
)

# %%
section("Greedy (T=0) vs sampling (T=1.2)")


def ask_three(prompt: str, temperature: float) -> list[str]:
    answers: list[str] = []
    for _ in range(3):
        response = ollama.chat(
            **chat_kwargs(
                model=DEFAULT_MODEL,
                messages=[
                    {
                        "role": "system",
                        "content": "Return only the final answer. Do not explain your reasoning or planning.",
                    },
                    {"role": "user", "content": prompt},
                ],
                options={"temperature": temperature, "num_predict": 24},
            )
        )
        answers.append(response_text(response))
    return answers


prompt = (
    "Invent a two-word product name for a small app that turns lecture notes "
    "into flashcards. Return only the name."
)

greedy = ask_three(prompt, temperature=0.0)
creative = ask_three(prompt, temperature=1.2)

print("Greedy (T=0):")
for i, a in enumerate(greedy, 1):
    print(f"  [{i}] {a}")

print("\nCreative (T=1.2):")
for i, a in enumerate(creative, 1):
    print(f"  [{i}] {a}")

# %%
section("Temperature + top-p combined")

options_matrix = [
    ("deterministic", {"temperature": 0.0, "num_predict": 32}),
    ("default chat", {"temperature": 0.7, "top_p": 0.9, "num_predict": 32}),
    ("creative, bounded nucleus", {"temperature": 1.2, "top_p": 0.85, "num_predict": 32}),
    ("creative, wide-open tail", {"temperature": 1.6, "top_p": 1.0, "num_predict": 32}),
]

for label, opts in options_matrix:
    response = ollama.chat(
        **chat_kwargs(
            model=DEFAULT_MODEL,
            messages=[
                {
                    "role": "system",
                    "content": "Complete the text directly. Return only the final sentence, with no headings or meta-commentary.",
                },
                {
                    "role": "user",
                    "content": (
                        'Complete the fragment "The debugging duck whispered..." '
                        "in exactly one short sentence. Do not add bullet points, "
                        "headings, or explanations."
                    ),
                },
            ],
            options=opts,
        )
    )
    print(f"\n[{label}] options={opts}")
    print(f"  -> {response_text(response)}")

# %% [markdown]
# ## Classroom takeaway
#
# * Tokenizers turn text into integer streams; different products ship different tables.
# * Leading spaces, punctuation, code, and Unicode can change token IDs and counts.
# * Softmax converts logits to probabilities; temperature and top-p reshape *how* you roll the dice.
# * Attention works over the visible context; long prompts raise prefill work before streaming starts.
# * KV caches reuse previous token work during generation, but they do not create conversation memory.
# * Ollama exposes token accounting via `prompt_eval_count` / `eval_count` — use them when budgeting.
