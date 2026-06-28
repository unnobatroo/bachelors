from __future__ import annotations

from pathlib import Path


HERE = Path(__file__).resolve().parent


def data_path(filename: str) -> Path:
    path = HERE / filename
    if not path.exists():
        raise FileNotFoundError(f"Missing demo dataset: {path}")
    return path


def section(title: str) -> None:
    bar = "=" * len(title)
    print(f"\n{bar}\n{title}\n{bar}")
