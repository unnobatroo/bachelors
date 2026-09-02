# Lecture 9 Demo Source

This folder contains the classroom demo source code aligned with the Lecture 9 presentation.

## Structure

### Clean source scripts

- `01_dataframe_basics.py`
  - Slides: Modern Python Data Workflow
  - Focus: dataframe mental model, quick inspection, and first question-answer access patterns
- `02_modern_pandas_workflow.py`
  - Slides: pandas in Practice
  - Focus: inspect a table, filter rows, derive columns, parse messy text, build summary tables, join lookup data, handle missing values, and parse time columns
- `03_polars_lazy_workflow.py`
  - Slides: When Polars Is Worth It
  - Focus: workload-based tool choice, lazy scans on wide CSV files, window expressions, and pandas interoperability

### Student-facing notebooks

- `01_dataframe_basics.ipynb`
- `02_modern_pandas_workflow.ipynb`
- `03_polars_lazy_workflow.ipynb`

These notebook versions follow the same logic as the scripts, but with more explanatory  markdown.

### Shared helper

- `demo_utils.py`
  - Small helpers shared by the demos

## Datasets used

- `space_missions.csv`
- `earthquakes.csv`

These demos deliberately avoid network access so you can use them offline as well.

## Why keep both scripts and notebooks?

The scripts use `# %%` cell markers so they work well in:

- JupyterLab
- Cursor / VS Code interactive execution
- Plain terminal runs with `uv run python ...`

This keeps the examples easy to version-control, diff, and clean up, while still supporting cell-by-cell live demos.

The notebooks are there for classroom delivery:

- more explanatory markdown
- cleaner step-by-step pacing
- a more familiar notebook reading experience

## Quickstart

From this directory:

```bash
uv sync
uv run python 01_dataframe_basics.py
uv run python 02_modern_pandas_workflow.py
uv run python 03_polars_lazy_workflow.py
```

For the notebook versions:

```bash
uv run jupyter lab
```

Then open any of:

- `01_dataframe_basics.ipynb`
- `02_modern_pandas_workflow.ipynb`
- `03_polars_lazy_workflow.ipynb`

