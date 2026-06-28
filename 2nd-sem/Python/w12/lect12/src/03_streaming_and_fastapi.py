# %% [markdown]
# # Optional Extension: Streaming and the FastAPI Sidecar
#
# Preserved as an optional extension after the core **local stack** slides on streaming,
# `AsyncClient`, and FastAPI `StreamingResponse`.
# Canonical narrative: `presentation.html` parts 2-3 + notebooks `02_*`, `04_*`.
#
# Two classroom demos in one file:
#
#   1. **Synchronous streaming** with `ollama.chat(stream=True)`. This is
#      the shortest possible end-to-end demo of the "typewriter effect".
#   2. **Async streaming + FastAPI** exposing a `/chat` endpoint. Students
#      can `curl -N` against it and watch tokens arrive live.
#
# The FastAPI section uses a single module-level `AsyncClient` via
# `lifespan`, which is the 2026 recommended pattern.

# %%
from __future__ import annotations

import asyncio
import os
import time
from contextlib import asynccontextmanager
from types import SimpleNamespace
from typing import Awaitable, Mapping

import ollama
from fastapi import FastAPI, HTTPException, Request
from fastapi.responses import StreamingResponse
from ollama import AsyncClient
from pydantic import BaseModel, ConfigDict, Field

from demo_utils import DEFAULT_MODEL, chat_kwargs, ensure_ollama_ready, kv, section


def chunk_text(chunk: object) -> str:
    """Extract visible content from one Ollama streaming chunk.

    The current `ollama` package returns typed chunk objects, while older or
    mocked clients may return dictionaries. The demo accepts both so the same
    parsing logic can be tested without a live model.
    """

    message = getattr(chunk, "message", None)
    if message is not None and hasattr(message, "content"):
        return getattr(message, "content") or ""

    if isinstance(chunk, Mapping):
        raw_message = chunk.get("message", {})
        if isinstance(raw_message, Mapping):
            return str(raw_message.get("content") or "")

    return ""


class InlineThinkFilter:
    """Filter `<think>...</think>` blocks from streamed token text."""

    def __init__(self) -> None:
        self.buffer = ""
        self.in_think = False

    def feed(self, fragment: str) -> str:
        self.buffer += fragment
        out: list[str] = []

        while self.buffer:
            if self.in_think:
                end = self.buffer.find("</think>")
                if end == -1:
                    self.buffer = self.buffer[-7:]
                    break
                self.buffer = self.buffer[end + len("</think>") :]
                self.in_think = False
                continue

            start = self.buffer.find("<think>")
            if start == -1:
                keep = 0
                marker = "<think>"
                max_prefix = min(len(self.buffer), len(marker) - 1)
                for size in range(max_prefix, 0, -1):
                    if marker.startswith(self.buffer[-size:]):
                        keep = size
                        break
                emit = self.buffer[:-keep] if keep else self.buffer
                out.append(emit)
                self.buffer = self.buffer[-keep:] if keep else ""
                break

            out.append(self.buffer[:start])
            self.buffer = self.buffer[start + len("<think>") :]
            self.in_think = True

        return "".join(out)


async def close_async_client(client: object) -> None:
    """Close an Ollama async client when the installed version supports it."""

    close = getattr(client, "aclose", None)
    if callable(close):
        await close()


# %% [markdown]
# ## 1. Synchronous streaming: see the typewriter effect live
#
# Run this cell and students will watch tokens appear one by one. The loop
# blocks only for the duration of one token (~20-50 ms on CPU).

def stream_chat(prompt: str) -> None:
    stream = ollama.chat(
        **chat_kwargs(
            model=DEFAULT_MODEL,
            messages=[
                {
                    "role": "system",
                    "content": "Answer directly in plain text. Keep it short and complete.",
                },
                {"role": "user", "content": prompt},
            ],
            options={"temperature": 0.2, "num_predict": 64},
            stream=True,
        )
    )
    start = time.perf_counter()
    first_visible_at: float | None = None
    chunk_count = 0
    visible_chars = 0

    print("AI: ", end="", flush=True)
    think_filter = InlineThinkFilter()
    for chunk in stream:
        fragment = chunk_text(chunk)
        if not fragment:
            continue
        chunk_count += 1
        if visible := think_filter.feed(fragment):
            if first_visible_at is None:
                first_visible_at = time.perf_counter() - start
            visible_chars += len(visible)
            print(visible, end="", flush=True)

    total = time.perf_counter() - start
    print("\n[done]")
    kv("Time To First Visible Text (s)", round(first_visible_at or 0.0, 3))
    kv("Total duration (s)", round(total, 3))
    kv("Chunks received", chunk_count)
    kv("Visible characters", visible_chars)
    if chunk_count and total:
        kv("Chunk throughput (chunks/s)", round(chunk_count / total, 2))

# %% [markdown]
# ## 2. Async streaming: the same thing, non-blocking
#
# `AsyncClient().chat(..., stream=True)` returns an **async iterator**.
# Use `async for chunk in stream:`. This is what FastAPI uses below.
# In a notebook we can drive it with `asyncio.run` inside a helper.

async def stream_async(prompt: str) -> None:
    client = AsyncClient()
    try:
        stream = await client.chat(
            **chat_kwargs(
                model=DEFAULT_MODEL,
                messages=[
                    {
                        "role": "system",
                        "content": "Answer directly in plain text. Keep it short and complete.",
                    },
                    {"role": "user", "content": prompt},
                ],
                options={"temperature": 0.2, "num_predict": 64},
                stream=True,
            )
        )
        print("AI (async): ", end="", flush=True)
        think_filter = InlineThinkFilter()
        async for chunk in stream:
            if fragment := chunk_text(chunk):
                if visible := think_filter.feed(fragment):
                    print(visible, end="", flush=True)
        print("\n[done]")
    finally:
        await close_async_client(client)


def run_async_demo(coro: Awaitable[None]) -> asyncio.Task[None] | None:
    """Run an async coroutine both from a script and from a running loop.

    In plain Python we can call `asyncio.run`. In Jupyter the event loop
    is already running, so we schedule the coroutine on it instead.
    """

    try:
        loop = asyncio.get_running_loop()
    except RuntimeError:
        asyncio.run(coro)
        return None

    print("Notebook event loop detected; scheduled the async demo task.")
    return loop.create_task(coro)

# %% [markdown]
# ## 3. The FastAPI sidecar: `uvicorn 03_streaming_and_fastapi:app --reload`
#
# Everything below this line is a real FastAPI application. Running the
# module with `uvicorn` serves a `/chat` endpoint that streams tokens as
# plain text over HTTP. Test from another terminal:
#
# ```bash
# curl -N -X POST http://localhost:8000/chat \
#      -H 'Content-Type: application/json' \
#      -d '{"prompt": "Explain streaming in one paragraph."}'
# ```

@asynccontextmanager
async def lifespan(app: FastAPI):
    ensure_ollama_ready(DEFAULT_MODEL)
    client = AsyncClient()
    app.state.ollama = client
    try:
        yield
    finally:
        await close_async_client(client)


app = FastAPI(
    title="Lecture 12 Streaming Sidecar",
    version="1.0.0",
    lifespan=lifespan,
)


class ChatRequest(BaseModel):
    model_config = ConfigDict(strict=True)
    prompt: str = Field(..., min_length=1, max_length=4096)
    model: str = DEFAULT_MODEL
    temperature: float = Field(0.3, ge=0.0, le=2.0)
    max_tokens: int = Field(80, ge=1, le=256)


@app.post("/chat")
async def chat_endpoint(req: ChatRequest, request: Request):
    try:
        client = request.app.state.ollama
    except AttributeError as exc:
        raise HTTPException(status_code=503, detail="Ollama client is not initialized") from exc

    async def gen():
        think_filter = InlineThinkFilter()
        stream = await client.chat(
            **chat_kwargs(
                model=req.model,
                messages=[
                    {"role": "system", "content": "Answer directly and concisely."},
                    {"role": "user", "content": req.prompt},
                ],
                options={"temperature": req.temperature, "num_predict": req.max_tokens},
                stream=True,
            )
        )
        async for chunk in stream:
            if await request.is_disconnected():
                break
            if fragment := chunk_text(chunk):
                if visible := think_filter.feed(fragment):
                    yield visible

    return StreamingResponse(gen(), media_type="text/plain")


@app.get("/health")
async def health() -> dict[str, str]:
    return {"status": "ok", "model": DEFAULT_MODEL}


class FakeStreamingClient:
    """Tiny fake Ollama client for a fast sidecar smoke test."""

    def __init__(self, pieces: tuple[str, ...] = ("hello", " ", "<think>hidden</think>", "stream")) -> None:
        self.pieces = pieces

    async def chat(self, **_: object):
        async def stream():
            for piece in self.pieces:
                yield SimpleNamespace(message=SimpleNamespace(content=piece))

        return stream()


async def run_fastapi_self_test_async() -> None:
    """Smoke-test the FastAPI app without starting Uvicorn or calling Ollama."""

    from httpx import ASGITransport, AsyncClient as HttpxAsyncClient

    had_client = hasattr(app.state, "ollama")
    previous_client = getattr(app.state, "ollama", None)
    app.state.ollama = FakeStreamingClient()

    try:
        transport = ASGITransport(app=app)
        async with HttpxAsyncClient(transport=transport, base_url="http://test") as client:
            health_response = await client.get("/health")
            assert health_response.status_code == 200
            assert health_response.json()["status"] == "ok"

            invalid_response = await client.post("/chat", json={"prompt": ""})
            assert invalid_response.status_code == 422

            chat_response = await client.post(
                "/chat",
                json={"prompt": "Explain streaming.", "max_tokens": 8},
            )
            assert chat_response.status_code == 200
            assert chat_response.text == "hello stream"
    finally:
        if had_client:
            app.state.ollama = previous_client
        else:
            delattr(app.state, "ollama")

    print("FastAPI self-test passed: /health, validation, and streamed /chat")


def run_fastapi_self_test() -> asyncio.Task[None] | None:
    """Run the sidecar smoke test from a script or schedule it in notebooks."""

    return run_async_demo(run_fastapi_self_test_async())


# %%
def run_demo_sequence() -> None:
    """Execute the classroom streaming demos in order.

    Keep this separate from module import so `uvicorn 03_streaming_and_fastapi:app`
    behaves like a real sidecar server instead of replaying the notebook demo.
    """

    ensure_ollama_ready(DEFAULT_MODEL)

    section("Synchronous streaming with ollama.chat(stream=True)")
    stream_chat(
        "Explain why streaming feels faster in exactly two short sentences."
    )

    section("Async streaming with AsyncClient")
    run_async_demo(stream_async("Give exactly 3 terse reasons web servers use async I/O."))

    section("FastAPI sidecar self-test")
    run_fastapi_self_test()

    section("FastAPI sidecar definition")
    print("App object is ready. Start the server with:")
    print("  uvicorn 03_streaming_and_fastapi:app --reload")


# %%
if __name__ == "__main__":
    run_demo_sequence()
    if os.environ.get("LECTURE12_RUN_FASTAPI", "").strip().lower() in {"1", "true", "yes"}:
        import uvicorn

        print("Starting uvicorn on http://localhost:8000 ... (Ctrl+C to stop)")
        uvicorn.run(app, host="0.0.0.0", port=8000)

# %% [markdown]
# ## Classroom takeaway
#
# * `stream=True` turns `ollama.chat` into an **iterator of chunks**.
# * Use the **sync** iterator in scripts, the **async** iterator in servers.
# * In FastAPI, `StreamingResponse` keeps the HTTP connection open so
#   tokens reach the browser the moment they are generated -- that is
#   what makes `TTFT < 400 ms` feel instant (Doherty threshold).
