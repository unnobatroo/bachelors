# %% [markdown]
# # Lecture 12 Demo 2: Local Ollama stack (Python)
#
#
# Students should see the same path in code that they just saw on the slides:
#
# 1. A **prompt** goes into a local model and a generated draft comes back.
# 2. A useful Python draft is treated as reviewable code, not trusted code.
# 3. A model call is stateless unless our Python program resends history.
# 4. A `system` message shapes behavior but does not replace application logic.
# 5. Streaming lowers perceived latency by showing the first tokens early.

# %%
from __future__ import annotations

import time

import ollama

from demo_utils import (
    DEFAULT_MODEL,
    DEFAULT_OPTIONS,
    chat_kwargs,
    ensure_ollama_ready,
    kv,
    pretty_metrics,
    response_text,
    section,
)


ensure_ollama_ready()

BASE_SYSTEM = (
    "You are a careful Python tutor for first-year computer science students. "
    "Return only the final answer. Do not reveal hidden reasoning. "
    "When code is requested, keep it short and runnable."
)


def ask_model(
    prompt: str,
    *,
    system: str = BASE_SYSTEM,
    temperature: float = 0.3,
    num_predict: int = 120,
) -> str:
    """Small wrapper so the classroom examples differ only in the prompt."""

    response = ollama.chat(
        **chat_kwargs(
            model=DEFAULT_MODEL,
            messages=[
                {"role": "system", "content": system},
                {"role": "user", "content": prompt},
            ],
            options={"temperature": temperature, "num_predict": num_predict},
        )
    )
    return response_text(response)


# %% [markdown]
# ## 1. One prompt, one generated draft
#
# `ollama.chat` sends a message list to a local model. The result is a generated draft plus useful
# timing and token-count metrics.

# %%
section("One local model call")

response = ollama.chat(
    **chat_kwargs(
        model=DEFAULT_MODEL,
        messages=[
            {"role": "system", "content": BASE_SYSTEM},
            {
                "role": "user",
                "content": (
                    "In two sentences, define generative AI and give one Python-learning example."
                ),
            },
        ],
        options=DEFAULT_OPTIONS,
    )
)

print("Reply:")
print(response_text(response))

print("\nMetrics:")
for key, value in pretty_metrics(response).items():
    kv(key, value)


# %% [markdown]
# ## 2. Strength demo: natural language → Python draft
#
# The exact `ollama.chat` call that produced the draft.

# %%
section("Strength demo: natural language -> Python draft")

examples = {
    "draft code": (
        "Write a Python function that groups transactions by month. "
        "Rows are dictionaries with a 'date' key formatted like YYYY-MM-DD. "
        "Use row['date'][:7] as the month key. Do not parse dates and do not import anything. "
        "Return only runnable code, no Markdown fence, no docstring. "
        "Use defaultdict and keep it under 8 lines."
    ),
    "explain code": (
        "Explain this for a beginner in exactly 3 bullets:\n"
        "groups[row['date'][:7]].append(row)"
    ),
    "review checklist": (
        "List 4 edge cases to test for a function that groups transactions by month. "
        "One short bullet per line."
    ),
}

for label, prompt in examples.items():
    print(f"\n--- {label} ---")
    max_tokens = 170 if label == "draft code" else 120
    print(ask_model(prompt, num_predict=max_tokens))

print(
    "\nHabit: the draft is useful because it is reviewable. "
    "Check empty input, malformed dates, time zones, and large files before integration."
)


# %% [markdown]
# ## 3. Stateless behavior: independent calls do not remember
#
# A model has learned weights, but those weights are not a live conversation memory. If we do not
# resend history, each call sees only the messages in that call.

# %%
section("Stateless behavior without Python history")


def one_shot(prompt: str) -> str:
    return ask_model(prompt, temperature=0.0)


turns = [
    "Hi there.",
    "My name is Bela.",
    "What is my name?",
]

for i, prompt in enumerate(turns, 1):
    print(f"\nQ{i}: {prompt}")
    print(f"A{i}: {one_shot(prompt)}")

print(
    "\nNotice: the third call cannot reliably know the name. "
    "The model saw only that one prompt."
)


# %% [markdown]
# ## 4. Conversation state: Python resends the transcript
#
# We store history as a list of role/content dictionaries. Every new call appends the user message,
# sends the whole list, then appends the assistant reply.

# %%
section("Stateful loop with an in-Python history")


class Chat:
    """Minimal classroom chat session.

    Production systems also trim, summarize, or retrieve history when the context window gets large.
    """

    def __init__(self, system: str | None = None, model: str = DEFAULT_MODEL) -> None:
        self.model = model
        self.history: list[dict[str, str]] = []
        if system:
            self.history.append({"role": "system", "content": system})

    def ask(self, user_text: str) -> str:
        self.history.append({"role": "user", "content": user_text})
        reply = ollama.chat(
            **chat_kwargs(
                model=self.model,
                messages=self.history,
                options={"temperature": 0.0, "num_predict": 80},
            )
        )
        text = response_text(reply)
        self.history.append({"role": "assistant", "content": text})
        return text


chat = Chat(system=BASE_SYSTEM)

for prompt in ("My name is Bela.", "What is my name?"):
    print(f"\nQ: {prompt}")
    print("A:", chat.ask(prompt))

kv("history length", len(chat.history))
print("(system + 2 user + 2 assistant messages = 5 entries)")


# %% [markdown]
# ## 5. System prompts shape style and format
#
# A `system` message is a high-priority instruction included at the beginning of the context. It is
# useful for style and format, but your application still needs validation and permissions.

# %%
section("System prompts: same user request, different behavior")


def answer_with_system(system: str) -> str:
    return ask_model(
        "Explain a Python list comprehension. Keep the answer compact for a lecture slide.",
        system=system,
        temperature=0.0,
    )


systems = {
    "friendly tutor": (
        "You are a friendly first-year Python tutor. "
        "Use exactly 2 short sentences and one everyday analogy. Return only the answer."
    ),
    "strict three-line format": (
        "You are a Python documentation assistant. "
        "Return exactly these three fields as plain text lines: Definition:, Example:, Warning:. "
        "Keep each field under 12 words."
    ),
}

for label, system in systems.items():
    print(f"\n--- {label} ---")
    print(answer_with_system(system))


# %% [markdown]
# ## 6. Streaming tokens and time-to-first-token
#
# `stream=True` yields chunks while generation continues. The total answer may
# take the same time, but students see the first visible token earlier.

# %%
section("Streaming and time-to-first-token")


def chunk_text(chunk: object) -> str:
    """Extract visible text from one streaming chunk."""

    message = getattr(chunk, "message", None)
    if message is not None and hasattr(message, "content"):
        return message.content or ""
    if isinstance(chunk, dict):
        return chunk.get("message", {}).get("content", "")
    return ""


stream = ollama.chat(
    **chat_kwargs(
        model=DEFAULT_MODEL,
        messages=[
            {"role": "system", "content": BASE_SYSTEM},
            {"role": "user", "content": "Stream a haiku about pandas and Python."},
        ],
        stream=True,
        options={"temperature": 0.4, "num_predict": 80},
    )
)

started = time.perf_counter()
first_piece_at: float | None = None
pieces: list[str] = []

for chunk in stream:
    piece = chunk_text(chunk)
    if not piece:
        continue
    if first_piece_at is None:
        first_piece_at = time.perf_counter()
    pieces.append(piece)
    print(piece, end="", flush=True)

finished = time.perf_counter()
print("\n")
kv("time to first token (s)", round((first_piece_at or finished) - started, 3))
kv("total streamed time (s)", round(finished - started, 3))
kv("visible characters", len("".join(pieces)))


# %% [markdown]
# ## Classroom takeaway
#
# * A generative model produces drafts from prompts and context.
# * The strongest beginner uses are small, checkable drafts plus review.
# * Conversation memory lives in Python's message list, not magically inside the model.
# * System prompts shape behavior; your Python app still owns state and decisions.
# * Streaming improves perceived latency; it does not make answers more correct.
