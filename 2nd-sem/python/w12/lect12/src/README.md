# Lecture 12 Demo Source

Classroom demo code for **Lecture 12: Applied Python for Generative AI & Local Inference**.
Everything here runs against a local [Ollama](https://ollama.com) daemon — no cloud API, no keys.

The canonical validated lecture setup is:

- chat model: `gemma4:e4b`
- embedding model: `embeddinggemma`

## Prerequisites

1. Install and start Ollama:

   ```bash
   # macOS: download the app or `brew install ollama`
   # Linux: see https://ollama.com/download
   ollama serve              # usually auto-started by the desktop app
   ```

2. Pull the classroom models:

   ```bash
   ollama pull gemma4:e4b
   ollama pull embeddinggemma
   ```

   `gemma4:e4b` is the validated live-demo default for this lecture on the
   current local setup: modern, multimodal-capable, and predictable through the
   Ollama chat API. `embeddinggemma` is the canonical local retriever model for
   the RAG section.

   Demo 1 also uses **`tiktoken`** (installed via `uv sync`) to show real byte-pair
   token IDs using the `cl100k_base` vocabulary. Those integers will not match
   Ollama's internal tokenizer for Gemma, but the exercise is intentionally about
   the pipeline, not exact parity between vendors.

   No environment overrides are needed for the canonical lecture path.
   Set `LECTURE12_MODEL=<tag>` only if you want to swap the chat model for all
   demos, and `LECTURE12_EMBED_MODEL=<tag>` only if you want to swap the
   embedding model for demo 4.

3. Install Python dependencies with `uv`:

   ```bash
   uv sync
   ```

## Canonical demo sequence

Each script is written with `# %%` cell markers so it works well in:

- JupyterLab
- Cursor / VS Code interactive execution (`# %%`)
- plain `python` runs for quick smoke tests

| Script | Notebook | Coverage |
| --- | --- | --- |
| `01_tokens_and_sampling.py` | `01_tokens_and_sampling.ipynb` | Foundations: `tiktoken` IDs, toy softmax/temperature/top-p, attention/context/KV-cache intuition, Ollama token metrics and sampling |
| `02_ollama_basics_and_state.py` | `02_ollama_basics_and_state.ipynb` | Ollama + Python: first local call, natural-language-to-Python draft, statelessness, history loop, system prompts, streaming and TTFT |
| `03_genai_strengths_use_cases_security.py` | `03_genai_strengths_use_cases_security.ipynb` | Optional extra drills: use-case fit, tiny eval harnesses, secret redaction, tool allow-lists, current security vocabulary |
| `04_structured_output_and_rag.py` | `04_structured_output_and_rag.ipynb` | GenAI systems: checked drafts, evaluation, structured output, grounded retrieval, RAG-quality checks, structured RAG, and guardrails |

## Shared helper

- `demo_utils.py`
  - `DEFAULT_MODEL` / `DEFAULT_EMBED_MODEL` / `DEFAULT_OPTIONS` - one place to swap the lecture defaults
  - `ensure_ollama_ready(*models)` - clear error if Ollama is not running or a required model is missing
  - `pretty_metrics(response)` - turns an Ollama response into a compact dict (`prompt_eval_count`, `eval_count`, tokens/s, ...)
  - `chat_kwargs(...)` - disables model thinking channels by default and merges the classroom `num_ctx` cap for cleaner, faster local demos
  - `section(title)` / `kv(key, value)` - classroom-readable output helpers
  - `export_script_to_notebook(script)` - regenerate `.ipynb` files from the `.py` masters

Regenerate every notebook from the scripts:

```bash
uv run python demo_utils.py
```

## Quickstart

From this directory:

```bash
uv sync

# Demo 1: foundations of generative AI
uv run python 01_tokens_and_sampling.py

# Demo 2: chat, state, system prompts
uv run python 02_ollama_basics_and_state.py

# Demo 3: use-case fit, evaluation, and safety checks
uv run python 03_genai_strengths_use_cases_security.py

# Demo 4: responsible GenAI systems loop with structured output and RAG quality
uv run python 04_structured_output_and_rag.py
```

 `03_streaming_and_fastapi.py` is  optional
 if you want to play with HTTP streaming after the core GenAI foundations. 

For interactive classroom use, JupyterLab is usually nicer:

```bash
uv run jupyter lab
```

Then open any of the `.ipynb` companions.

