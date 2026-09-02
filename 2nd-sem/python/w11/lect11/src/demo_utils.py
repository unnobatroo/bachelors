from __future__ import annotations

import json
from pathlib import Path

import matplotlib.pyplot as plt


HERE = Path(__file__).resolve().parent
LECTURE_ROOT = HERE.parent
ASSETS_DIR = LECTURE_ROOT / "assets"


def source_path(filename: str) -> Path:
    path = HERE / filename
    if not path.exists():
        raise FileNotFoundError(f"Missing source asset: {path}")
    return path


def lecture_asset_path(filename: str) -> Path:
    path = ASSETS_DIR / filename
    path.parent.mkdir(parents=True, exist_ok=True)
    return path


def section(title: str) -> None:
    bar = "=" * len(title)
    print(f"\n{bar}\n{title}\n{bar}")


def configure_static_theme() -> None:
    plt.rcParams.update(
        {
            "figure.facecolor": "#fbfdfb",
            "axes.facecolor": "#ffffff",
            "axes.edgecolor": "#d8e4d9",
            "axes.labelcolor": "#1f2a22",
            "axes.titlecolor": "#1f2a22",
            "grid.color": "#d8e4d9",
            "grid.alpha": 0.5,
            "axes.spines.top": False,
            "axes.spines.right": False,
        }
    )


def select_device():
    import torch

    if torch.cuda.is_available():
        return torch.device("cuda")
    if torch.backends.mps.is_available():
        return torch.device("mps")
    return torch.device("cpu")


def export_script_to_notebook(script_name: str, notebook_name: str | None = None) -> Path:
    script_path = source_path(script_name)
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
            cells.append(
                {
                    "cell_type": "markdown",
                    "metadata": {},
                    "source": source,
                }
            )
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
            "language_info": {
                "name": "python",
                "version": "3.12",
            },
        },
        "nbformat": 4,
        "nbformat_minor": 5,
    }
    notebook_path.write_text(json.dumps(notebook, indent=2))
    return notebook_path
