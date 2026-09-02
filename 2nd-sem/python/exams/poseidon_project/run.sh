#!/usr/bin/env bash
set -euo pipefail

export PYTHONPATH="src"
uv run uvicorn poseidon.main:app --host 0.0.0.0 --port 8000 --reload
