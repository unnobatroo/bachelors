# %% [markdown]
# # Lecture 12 Demo 3: Use Cases, Evaluation, and Safety Checks
#
# The goal is not to build a full product. Students should see the safe
# beginner workflow:
#
# 1. Send small, checkable prompts to a local model.
# 2. Decide whether a task is a good GenAI fit.
# 3. Keep a tiny evaluation harness beside prompts.
# 4. Redact secrets and block unsafe tool requests outside the model.
# 5. Connect vocabulary to current production directions.

# %%
from __future__ import annotations

import re
from collections.abc import Mapping

import ollama

from demo_utils import (
    DEFAULT_MODEL,
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
    "Return only the final answer. Keep outputs short and easy to verify. "
    "Do not reveal hidden reasoning."
)


def ask_model(prompt: str, *, temperature: float = 0.3, num_predict: int = 120) -> str:
    """Call the local classroom model with a consistent tutor system message."""

    response = ollama.chat(
        **chat_kwargs(
            model=DEFAULT_MODEL,
            messages=[
                {"role": "system", "content": BASE_SYSTEM},
                {"role": "user", "content": prompt},
            ],
            options={"temperature": temperature, "num_predict": num_predict},
        )
    )
    return response_text(response)


# %% [markdown]
# ## 1. One compact prompt and visible metrics
#
# A prompt is the instruction text we send. The response includes token and
# timing metrics, which are useful when discussing cost and latency.

# %%
section("One compact prompt")

response = ollama.chat(
    **chat_kwargs(
        model=DEFAULT_MODEL,
        messages=[
            {"role": "system", "content": BASE_SYSTEM},
            {
                "role": "user",
                "content": "Explain a Python list comprehension in exactly 3 bullets.",
            },
        ],
        options={"temperature": 0.2, "num_predict": 100},
    )
)

print(response_text(response))
print("\nMetrics:")
for key, value in pretty_metrics(response).items():
    kv(key, value)


# %% [markdown]
# ## 2. Strength demo: draft, explain, transform, brainstorm
#
# These tasks are useful because the outputs are small enough for a beginner to
# read and check.

# %%
section("Strength demo: draft, explain, transform, brainstorm")

examples = {
    "draft code": (
        "Write a short Python function `is_even(n)` with one example call. "
        "No imports and no long explanation."
    ),
    "explain code": (
        "Explain this Python expression for a beginner in exactly 3 bullets:\n"
        "[x * x for x in range(6) if x % 2 == 0]"
    ),
    "transform notes": (
        "Turn these rough notes into a 3-row Markdown table: "
        "token = text piece, context = what model sees, validation = check drafts."
    ),
    "brainstorm tests": (
        "Suggest 4 test cases for a function that removes duplicate items from a list."
    ),
}

for label, prompt in examples.items():
    print(f"\n--- {label} ---")
    print(ask_model(prompt, temperature=0.3, num_predict=120))


# %% [markdown]
# ## 3. Use-case triage: should a model be in this workflow?
#
# The revised lecture emphasizes: ship GenAI where a checked draft is useful.
# If the workflow cannot detect a bad answer, the model is in the wrong place.

# %%
section("Use-case triage")

use_cases = [
    {
        "name": "draft docstring",
        "checked_draft_useful": True,
        "failure_easy_to_detect": True,
        "private_or_irreversible": False,
    },
    {
        "name": "delete old invoices automatically",
        "checked_draft_useful": False,
        "failure_easy_to_detect": False,
        "private_or_irreversible": True,
    },
    {
        "name": "summarize course handout with citations",
        "checked_draft_useful": True,
        "failure_easy_to_detect": True,
        "private_or_irreversible": False,
    },
]


def genai_fit_score(case: Mapping[str, object]) -> str:
    if case["private_or_irreversible"]:
        return "HUMAN APPROVAL REQUIRED"
    if case["checked_draft_useful"] and case["failure_easy_to_detect"]:
        return "GOOD CLASSROOM FIT"
    return "BAD FIT UNTIL EVALUATION EXISTS"


for case in use_cases:
    kv(case["name"], genai_fit_score(case))


# %% [markdown]
# ## 4. Evaluation: generated output is external input
#
# A generated checklist may look useful but still have the wrong shape. Python
# should validate it before another function depends on it. Keep these checks
# as a regression suite when you change the prompt or model.

# %%
section("Tiny evaluation harness")


def validate_three_item_checklist(text: str) -> bool:
    """Accept exactly three short, non-empty checklist items."""

    lines = [line.strip(" -\t") for line in text.splitlines() if line.strip()]
    if len(lines) != 3:
        return False
    return all(8 <= len(line) <= 90 for line in lines)


checklist = ask_model(
    "Create exactly 3 short checklist items for reviewing AI-generated Python code. "
    "One item per line, no numbering.",
    temperature=0.2,
    num_predict=90,
)

print(checklist)
kv("shape check passes", validate_three_item_checklist(checklist))

golden_cases = [
    ("schema check", "validate JSON before using it"),
    ("code review", "run tests before integration"),
    ("security", "do not send secrets to prompts"),
]

print("\nGolden examples for a future regression run:")
for topic, expected_behavior in golden_cases:
    kv(topic, expected_behavior)


# %% [markdown]
# ## 5. Security: redact secrets before prompts leave your program
#
# This demo uses simple patterns only. Production systems need stronger secret
# detection, access controls, logging policy, and human review.

# %%
section("Redact obvious secrets")

SECRET_PATTERNS = [
    re.compile(r"sk-[A-Za-z0-9_-]{12,}"),
    re.compile(r"(?i)(api[_-]?key|token|password)\s*=\s*['\"][^'\"]+['\"]"),
]


def redact_secrets(text: str) -> str:
    """Replace obvious secret-looking substrings before model calls."""

    redacted = text
    for pattern in SECRET_PATTERNS:
        redacted = pattern.sub("[REDACTED_SECRET]", redacted)
    return redacted


unsafe_prompt = 'Summarize this config: api_key = "sk-1234567890abcdef"'
safe_prompt = redact_secrets(unsafe_prompt)

print("Before:", unsafe_prompt)
print("After: ", safe_prompt)
kv("safe to send", "[REDACTED_SECRET]" in safe_prompt and "sk-" not in safe_prompt)


# %% [markdown]
# ## 6. Tool requests: the application decides what is allowed
#
# A tool is any function the application can call for the model, such as
# reading a file, sending an email, or updating a database. The model may
# propose a tool call, but Python must enforce permissions.

# %%
section("Allow-list tool requests")

ALLOWED_TOOLS = {"summarize_text", "format_table", "explain_code"}
BLOCKED_TOOLS = {"send_email", "delete_file", "update_database"}


def allowed_tool_call(call: Mapping[str, object]) -> bool:
    """Return True only for explicitly allowed, low-risk classroom tools."""

    name = call.get("name")
    args = call.get("args", {})
    if name not in ALLOWED_TOOLS:
        return False
    if isinstance(args, Mapping):
        return all(no_secret_value(value) for value in args.values())
    return False


def no_secret_value(value: object) -> bool:
    return not isinstance(value, str) or redact_secrets(value) == value


requests = [
    {"name": "explain_code", "args": {"code": "print(sum([1, 2, 3]))"}},
    {"name": "send_email", "args": {"to": "student@example.com"}},
    {"name": "format_table", "args": {"text": 'token = "sk-1234567890abcdef"'}},
]

for request in requests:
    print(f"{request!r} -> {'ALLOW' if allowed_tool_call(request) else 'BLOCK'}")

print(f"Blocked high-risk tools: {sorted(BLOCKED_TOOLS)}")


# %% [markdown]
# ## 7. Current vocabulary as a Python dictionary
#
# The same definitions from the slides can live in code, quizzes, or reference
# sheets. Keeping definitions explicit prevents overloaded terms from becoming
# magic words.

# %%
section("2026 direction vocabulary")

terms = {
    "copilot": "assistant embedded in a tool, such as an editor",
    "agent": "system where a model chooses steps and calls tools under rules",
    "multimodal model": "model that handles more than one data type",
    "RAG": "retrieve relevant documents, then generate with those documents in context",
    "evaluation": "tests that measure correctness, safety, and usefulness",
    "open-weight model": "model whose learned weights can be downloaded and run locally",
    "unbounded consumption": "missing token, cost, or loop limits that let requests run away",
    "system prompt leakage": "exposing hidden instructions; never treat prompts as secrets",
}

for term, definition in terms.items():
    kv(term, definition)


# %% [markdown]
# ## Classroom takeaway
#
# * Generative AI is strongest on small, checkable drafting and transformation tasks.
# * The model returns drafts, not truth guarantees.
# * A prompt without a repeatable evaluation set is a demo, not an engineering system.
# * Python code owns validation, secret handling, permissions, budgets, tests, and integration.
