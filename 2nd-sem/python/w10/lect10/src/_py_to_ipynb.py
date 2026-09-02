"""Convert a `# %%`-marked Python script to a Jupyter notebook.

This is a tiny helper for keeping the lecture's `.py` and `.ipynb` files in
sync. It treats the source script as a sequence of cells separated by

    # %%               <- code cell
    # %% [markdown]    <- markdown cell

The output notebook adds an `IPython.display.display` import so any
`display(...)` calls in the converted cells work without further effort.

Run from the lecture src directory:

    uv run python _py_to_ipynb.py 01_chart_choice_and_workhorses.py \\
                                  01_chart_choice_and_workhorses.ipynb
"""

from __future__ import annotations

import sys
from pathlib import Path

import nbformat


def parse_cells(text: str) -> list[tuple[str, str]]:
    cells: list[tuple[str, str]] = []
    current_kind = "code"
    buffer: list[str] = []

    def flush() -> None:
        if not buffer:
            return
        body = "".join(buffer).strip("\n")
        if body:
            cells.append((current_kind, body))
        buffer.clear()

    for line in text.splitlines(keepends=True):
        stripped = line.strip()
        if stripped == "# %%" or stripped == "# %% [markdown]":
            flush()
            current_kind = "markdown" if "[markdown]" in stripped else "code"
            continue
        if current_kind == "markdown" and stripped.startswith("#"):
            cleaned = line[1:]
            if cleaned.startswith(" "):
                cleaned = cleaned[1:]
            buffer.append(cleaned)
        else:
            buffer.append(line)
    flush()
    return cells


def convert(src: Path, dst: Path) -> None:
    text = src.read_text()
    cells = parse_cells(text)
    nb = nbformat.v4.new_notebook()
    nb["metadata"] = {
        "kernelspec": {"display_name": "Python 3", "language": "python", "name": "python3"},
        "language_info": {"name": "python"},
    }
    out_cells = []
    for kind, body in cells:
        if kind == "markdown":
            out_cells.append(nbformat.v4.new_markdown_cell(body))
        else:
            out_cells.append(nbformat.v4.new_code_cell(body))
    nb["cells"] = out_cells
    nbformat.write(nb, dst.open("w"))
    print(f"wrote {dst}  ({len(out_cells)} cells)")


def main() -> None:
    if len(sys.argv) != 3:
        print("usage: _py_to_ipynb.py <source.py> <dest.ipynb>", file=sys.stderr)
        raise SystemExit(2)
    convert(Path(sys.argv[1]), Path(sys.argv[2]))


if __name__ == "__main__":
    main()
