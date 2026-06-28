# Lecture 10 Demo Source

This folder contains the classroom demo source code aligned with Lecture 10: data visualization with Matplotlib, seaborn, and Plotly.

## Canonical demo sequence

- `01_chart_choice_and_workhorses.py`
  - Notebook companion: `01_chart_choice_and_workhorses.ipynb`
  - Slides: why visualization matters, chart choice, workhorse charts, accessibility defaults
  - Story arc, one workhorse chart per question:
    1. **Anscombe's quartet** &mdash; plot before trusting summary statistics.
    2. **Bar** &mdash; *which agency has flown the most missions?*
    3. **Line** &mdash; *how did the agency mix evolve since Sputnik?*
    4. **Histogram + box** &mdash; *how are mission costs spread out?*
    5. **Scatter** &mdash; *do longer missions cost more?*
    6. **Readable defaults** &mdash; long mission names handled cleanly.
- `02_matplotlib_and_seaborn.py`
  - Notebook companion: `02_matplotlib_and_seaborn.ipynb`
  - Slides: Matplotlib foundations, seaborn grammar, vectorized data-prep bridge
  - Step-by-step build of one figure, adding one lecture concept per cell:
    1. tidy DataFrame as the shared starting point
    2. state-machine vs. explicit OO Matplotlib API
    3. `subplot_mosaic` for named multi-panel layouts
    4. `seaborn.objects` Plot + Mark
    5. add a Stat: `Agg("mean")`
    6. add a Move: `Dodge()`
    7. add a Scale: log
    8. add a Facet: `col="mission_class"`
    9. WKT parsing &rarr; map-ready coordinates
    10. `fig.savefig` for slides and papers
- `03_plotly_maps_and_performance.py`
  - Notebook companion: `03_plotly_maps_and_performance.ipynb`
  - Slides: Plotly interactivity, maps, and large-data workflows
  - Focus: hover/range sliders, `px.scatter_map`, and upstream aggregation with Polars

These scripts use `# %%` cell markers so they work well in:

- JupyterLab
- Cursor / VS Code interactive execution
- plain `python` runs when you want a quick smoke test

## Shared helper

- `demo_utils.py`
  - dataset path helpers
  - shared plotting theme
  - earthquake WKT parsing helper

## Datasets used

- `space_missions.csv` &mdash; 280-row curated mission dataset (1957&ndash;2026) covering
  eight agencies and a heavy-tailed cost distribution, regenerated from
  `_build_space_missions.py`.
- `earthquakes.csv` &mdash; small WKT-encoded sample for the data-prep bridge.
- `earthquakes_large.csv` &mdash; multi-megabyte file for the upstream-aggregation demo.
- `space_race_data.csv` &mdash; legacy Cold War milestones, kept for backwards compatibility.

All demos are designed to work offline.

## Reproducible asset generation

- `_build_space_missions.py` &mdash; rebuilds `space_missions.csv` from a curated
  list of real missions plus deterministic fillers seeded with `random(7)`.
- `../assets/_generate_lecture_assets.py` &mdash; rebuilds the chart-taxonomy
  overview and the four workhorse illustrations used by the lecture deck.
- `_py_to_ipynb.py` &mdash; converts a `# %%`-marked script to a Jupyter notebook
  (used to regenerate the companion `.ipynb` files after editing the `.py`).

The numbered scripts are the canonical live-demo path, and the numbered notebooks are the cleaner student-facing notebook versions of the same material.

## Quickstart

From this directory:

```bash
uv sync
uv run python 01_chart_choice_and_workhorses.py
uv run python 02_matplotlib_and_seaborn.py
uv run python 03_plotly_maps_and_performance.py
```

For classroom use, interactive execution is usually nicer:

```bash
uv run jupyter lab
```

Then open any of:

- `01_chart_choice_and_workhorses.ipynb`
- `02_matplotlib_and_seaborn.ipynb`
- `03_plotly_maps_and_performance.ipynb`

Or run the matching numbered scripts cell by cell in Cursor / VS Code.
