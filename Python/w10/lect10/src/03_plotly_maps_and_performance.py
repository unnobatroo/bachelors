# %% [markdown]
# # Lecture 10 Demo 3: Plotly, Maps, and Performance
#
# Aligned with the interactive and performance-aware sections of the lecture.

# %%
from __future__ import annotations

import pandas as pd
import plotly
import plotly.express as px
import plotly.io as pio
import polars as pl

from demo_utils import data_path, read_earthquakes, read_space_missions, section


pio.templates.default = "simple_white"

section("Versions")
print(f"pandas: {pd.__version__}")
print(f"plotly: {plotly.__version__}")
print(f"polars: {pl.__version__}")
print("This demo focuses on interactive charts, quiet basemaps, and aggregated browser workloads.")

# %% [markdown]
# ## 1. Plotly hover + range slider

# %%
section("Plotly Express: hover and range slider")

missions = read_space_missions()
missions_plotly = missions.convert_dtypes(dtype_backend="numpy_nullable")
missions_by_year = (
    missions_plotly.groupby("launch_year", as_index=False)
    .agg(
        mission_count=("mission_name", "size"),
        total_cost=("cost_million_usd", "sum"),
    )
    .sort_values("launch_year")
)

fig = px.line(
    missions_by_year,
    x="launch_year",
    y="total_cost",
    markers=True,
    hover_data=["mission_count"],
    title="Total mission cost by launch year",
)
fig.update_xaxes(rangeslider_visible=True)
fig.update_yaxes(title="Total cost (million USD)")
fig.show()

# %% [markdown]
# ## 2. Modern Plotly map API: px.scatter_map

# %%
section("MapLibre-based scatter map")

quakes = read_earthquakes().dropna(subset=["lon", "lat"])
quakes = quakes.convert_dtypes(dtype_backend="numpy_nullable")
map_sample = quakes.nlargest(200, "mag")

fig = px.scatter_map(
    map_sample,
    lat="lat",
    lon="lon",
    size="mag",
    color="mag",
    hover_name="title",
    hover_data=["depth", "alert"],
    map_style="carto-positron",
    zoom=0.8,
    title="Largest earthquakes in the demo dataset",
)
fig.show()

# %% [markdown]
# ## 3. Aggregate first, then render

# %%
section("Large data workflow with Polars")

summary = (
    pl.scan_csv(data_path("earthquakes_large.csv"))
    .filter(pl.col("mag") >= 6.0)
    .group_by("year")
    .agg(
        pl.len().alias("event_count"),
        pl.col("mag").mean().alias("mean_magnitude"),
    )
    .sort("year")
    .collect(engine="streaming")
    .to_pandas(use_pyarrow_extension_array=False)
)

summary["year"] = summary["year"].astype(str)
summary["event_count"] = summary["event_count"].astype("int64")

print(summary.tail(10).to_string(index=False))

fig = px.bar(
    summary,
    x="year",
    y="event_count",
    hover_data={"mean_magnitude": ":.2f"},
    text="event_count",
    title="Events per year after upstream aggregation (mag &ge; 6.0)",
    labels={"year": "Year", "event_count": "Number of events"},
)
fig.update_traces(texttemplate="%{y:,}", textposition="outside", cliponaxis=False)
fig.update_layout(
    height=450,
    margin={"l": 70, "r": 30, "t": 70, "b": 60},
    bargap=0.25,
    yaxis={"rangemode": "tozero"},
)
fig.update_xaxes(type="category", title="Year")
fig.update_yaxes(title="Number of events")
fig.show()
