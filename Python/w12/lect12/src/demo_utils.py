"""Shared helpers for Lecture 12 classroom demos.

These demos target Ollama running locally on port 11434. The helpers make
classroom sessions predictable:

- ``ensure_ollama_ready`` fails fast with a clear message if the daemon is
  not running, instead of leaving students staring at a stalled connect.
- ``section`` and ``kv`` keep stdout output readable when projected.
- ``DEFAULT_MODEL`` / ``DEFAULT_EMBED_MODEL`` / ``DEFAULT_OPTIONS`` are the
  single source of truth the lecture uses across scripts so the lecturer can
  switch between current 2026 model families from one place.
- ``export_script_to_notebook`` mirrors the helper used in lectures 9 and 11
  so each ``.py`` file has a matching ``.ipynb`` for students who prefer
  JupyterLab.
"""

from __future__ import annotations

import json
import os
import re
import sys
from pathlib import Path
from typing import Any, Mapping


HERE = Path(__file__).resolve().parent

DEFAULT_MODEL = os.environ.get("LECTURE12_MODEL", "gemma4:e4b")
"""Model tag used across every demo. Override via ``LECTURE12_MODEL``."""

DEFAULT_EMBED_MODEL = os.environ.get("LECTURE12_EMBED_MODEL", "embeddinggemma")
"""Embedding model tag used by the RAG demo."""

DEFAULT_THINK = os.environ.get("LECTURE12_THINK", "").strip().lower() in {
    "1",
    "true",
    "yes",
    "on",
}
"""Whether to allow models to emit a separate thinking channel in demos."""

DEFAULT_OPTIONS: dict[str, Any] = {
    "temperature": 0.3,
    "num_ctx": 4096,
}
"""Inference options used by every demo unless a specific demo overrides them."""


def section(title: str) -> None:
    """Print a visually distinct section heading when projected on a slide."""

    bar = "=" * max(len(title), 12)
    print(f"\n{bar}\n{title}\n{bar}")


def kv(key: str, value: Any) -> None:
    """Aligned ``key: value`` one-liner for classroom-readable output."""

    print(f"{key:>24s} : {value}")


def ensure_ollama_ready(*models: str) -> None:
    """Fail fast with a classroom-friendly message if Ollama is unreachable.

    In class we want the common failure modes to be obvious:
      1. ``ollama`` package missing.
      2. Ollama daemon not running.
      3. One of the requested models is not pulled yet.
    """

    try:
        import ollama  # noqa: WPS433 - local import on purpose
    except ModuleNotFoundError as exc:
        print(
            "ERROR: the `ollama` Python package is not installed. Run:\n"
            "       uv sync (from the lecture `src/` folder)"
        )
        raise SystemExit(1) from exc

    try:
        tags = ollama.list()
    except Exception as exc:  # noqa: BLE001 - surface any connection error
        print(
            "ERROR: could not reach the Ollama daemon on http://localhost:11434.\n"
            "       Start it with `ollama serve` (or open the Ollama app) and retry."
        )
        raise SystemExit(1) from exc

    available = {getattr(m, "model", None) or m.get("name") for m in tags.models}  # type: ignore[attr-defined]
    available = {name for name in available if name}

    required = tuple(dict.fromkeys(models or (DEFAULT_MODEL,)))
    missing = [
        model
        for model in required
        if model not in available and f"{model}:latest" not in available
    ]

    if missing:
        pull_lines = "\n".join(f"         ollama pull {model}" for model in missing)
        print(
            "WARNING: some required models are not pulled yet.\n"
            f"{pull_lines}\n"
            f"         Models currently available: {sorted(available) or 'none'}"
        )
        # Not fatal: Ollama will try to pull on first `chat` call and may succeed.


def pretty_metrics(response: Any) -> dict[str, Any]:
    """Extract a compact dict of metrics from an Ollama chat response."""

    def _get(name: str, default: Any = None) -> Any:
        if hasattr(response, name):
            return getattr(response, name)
        if isinstance(response, Mapping):
            return response.get(name, default)
        return default

    eval_count = _get("eval_count")
    eval_duration = _get("eval_duration")
    prompt_eval_count = _get("prompt_eval_count")
    prompt_eval_duration = _get("prompt_eval_duration")

    tokens_per_second: float | None = None
    if eval_count and eval_duration:
        tokens_per_second = round(eval_count / (eval_duration / 1e9), 2)

    return {
        "prompt_eval_count": prompt_eval_count,
        "prompt_eval_duration_ns": prompt_eval_duration,
        "eval_count": eval_count,
        "eval_duration_ns": eval_duration,
        "total_duration_ns": _get("total_duration"),
        "tokens_per_second": tokens_per_second,
    }


def chat_kwargs(**kwargs: Any) -> dict[str, Any]:
    """Attach classroom-safe defaults to Ollama chat calls.

    Some current models (notably Gemma 4 in certain Ollama builds) emit a
    separate `thinking` channel by default. In lecture demos this is noisy and
    can even leave `message.content` empty, so we disable it unless the
    lecturer explicitly opts in via `LECTURE12_THINK=true`.

    We also merge in `DEFAULT_OPTIONS` so long-context-capable models such as
    `qwen3:4b` do not silently allocate an enormous context window in small
    classroom demos. Per-call overrides still win.
    """

    merged_options = dict(DEFAULT_OPTIONS)
    merged_options.update(kwargs.get("options") or {})
    kwargs["options"] = merged_options
    kwargs.setdefault("think", DEFAULT_THINK)
    return kwargs


THINK_BLOCK_RE = re.compile(r"<think>.*?</think>", re.DOTALL)


def clean_chat_text(text: str) -> str:
    """Strip model-internal reasoning wrappers from visible output.

    Some current Ollama templates (notably the shipped `qwen3` template) can
    emit `<think>...</think>` blocks inline in `message.content`. For classroom
    demos we want the final answer, not the raw reasoning transcript.
    """

    text = THINK_BLOCK_RE.sub("", text)
    return text.strip()


def response_text(response: Any) -> str:
    """Extract cleaned visible text from an Ollama chat response."""

    message = getattr(response, "message", None)
    if message is not None and hasattr(message, "content"):
        return clean_chat_text(message.content or "")
    if isinstance(response, Mapping):
        return clean_chat_text(response.get("message", {}).get("content", ""))
    return ""


def export_script_to_notebook(script_name: str, notebook_name: str | None = None) -> Path:
    """Convert a ``# %% [markdown]`` cell-marker script to a Jupyter notebook."""

    script_path = HERE / script_name
    if not script_path.exists():
        raise FileNotFoundError(script_path)

    target_name = notebook_name or script_path.with_suffix(".ipynb").name
    notebook_path = HERE / target_name

    cells: list[dict[str, object]] = []
    current_lines: list[str] = []
    current_kind: str | None = None

    def flush() -> None:
        nonlocal current_lines, current_kind
        if current_kind is None:
            return
        source = current_lines[:]
        if current_kind == "markdown":
            cells.append({"cell_type": "markdown", "metadata": {}, "source": source})
        else:
            cells.append(
                {
                    "cell_type": "code",
                    "execution_count": None,
                    "metadata": {},
                    "outputs": [],
                    "source": source,
                }
            )
        current_lines = []
        current_kind = None

    for raw_line in script_path.read_text().splitlines(keepends=True):
        if raw_line.startswith("# %%"):
            flush()
            current_kind = "markdown" if "[markdown]" in raw_line else "code"
            continue
        if current_kind is None:
            current_kind = "code"

        if current_kind == "markdown":
            if raw_line.startswith("# "):
                current_lines.append(raw_line[2:])
            elif raw_line.startswith("#\n"):
                current_lines.append("\n")
            else:
                current_lines.append(raw_line)
        else:
            current_lines.append(raw_line)

    flush()

    notebook = {
        "cells": cells,
        "metadata": {
            "kernelspec": {
                "display_name": "Python 3",
                "language": "python",
                "name": "python3",
            },
            "language_info": {"name": "python", "version": "3.12"},
        },
        "nbformat": 4,
        "nbformat_minor": 5,
    }
    notebook_path.write_text(json.dumps(notebook, indent=2))
    return notebook_path


if __name__ == "__main__":
    # Running the module directly exports every numbered script to an .ipynb.
    exported: list[Path] = []
    for name in sorted(HERE.glob("0[1-9]_*.py")):
        exported.append(export_script_to_notebook(name.name))
    for path in exported:
        print(f"Exported: {path.name}", file=sys.stderr)
