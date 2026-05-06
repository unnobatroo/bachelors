# %% [markdown]
# # Lecture 10 Demo 2: Matplotlib and Seaborn, Built Up Step by Step
#
# Aligned with the "Matplotlib Foundations" and "Seaborn Grammar" sections.
# We tell **one** story across the whole notebook, adding layers of plotting
# grammar one concept at a time.
#
# The story: *"How did the agency mix and the cost of going to space change
# from Sputnik to today, and what would the same data look like once we put it
# on a map?"*
#
# Concepts covered, in the order the lecture introduces them:
#
# 1. Tidy data: a DataFrame is the shared input for every plot.
# 2. Figure / Axes / Axis: the explicit object-oriented Matplotlib API.
# 3. `subplot_mosaic`: semantic, named multi-panel layouts.
# 4. `seaborn.objects`: the same chart re-expressed as a grammar
#    (`Plot + Mark + Stat + Move + Scale + Facet`).
# 5. WKT parsing: turn geometry text into numeric coordinates and plot them.
# 6. Saving figures with `fig.savefig` for slides and papers.

# %%
from __future__ import annotations

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns
import seaborn.objects as so

from demo_utils import configure_static_theme, read_earthquakes, read_space_missions, section


configure_static_theme()

section("Versions")
print(f"pandas: {pd.__version__}")
print(f"seaborn: {sns.__version__}")
print("This demo builds one figure at a time, adding lecture concepts as we go.")

# %% [markdown]
# ## Step 1 — Start from a tidy DataFrame
#
# Slide 10-1 says: one variable per column, one row per observation. That is
# what every plotting layer in this lecture will consume.

# %%
section("Step 1: tidy data is the shared starting point")

missions = read_space_missions()
print(missions.dtypes)
print()
print(missions.head(6).to_string(index=False))
print()
print(
    f"shape: {missions.shape},  "
    f"agencies: {missions['agency_short'].nunique()},  "
    f"years: {missions['launch_year'].min()}-{missions['launch_year'].max()}"
)

# %% [markdown]
# ## Step 2 — Pyplot vs the explicit OO API
#
# The state-machine style (`plt.plot(...)`) is fine for a quick sketch but
# becomes awkward as the figure grows. The OO style is what we use for the
# rest of the demo: ask Matplotlib for a figure + axes, then talk to the
# axes directly.

# %%
section("Step 2: state-machine vs object-oriented Matplotlib")

per_agency = (
    missions.groupby("agency_short", observed=True)
    .size()
    .sort_values()
    .rename("missions")
)

# State-machine version (do not use this in real code, kept as a contrast).
plt.figure(figsize=(7, 3.6))
plt.barh(per_agency.index, per_agency.values, color=sns.color_palette("colorblind", len(per_agency)))
plt.title("State-machine style: works, but global state is fragile")
plt.xlabel("Missions flown")
plt.show()

# OO style: this is the lecture's recommended habit.
fig, ax = plt.subplots(figsize=(7, 3.6), layout="constrained")
ax.barh(per_agency.index, per_agency.values, color=sns.color_palette("colorblind", len(per_agency)))
ax.set(title="OO style: ax knows exactly which plot it is", xlabel="Missions flown")
plt.show()

# %% [markdown]
# ## Step 3 — `subplot_mosaic` for a named multi-panel layout
#
# Slide 7-3: name the panels semantically (`"trend"`, `"hist"`) instead of
# remembering `axs[1, 0]`. The same DataFrame feeds three different views of
# the story:
#
# * who has flown the most missions (bar)
# * how that count moves over time (line)
# * how mission costs are distributed (histogram, log scale)

# %%
section("Step 3: subplot_mosaic with named panels")

ordered = per_agency.sort_values(ascending=False).index.tolist()
yearly = (
    missions.groupby(["launch_year", "agency_short"], observed=True)
    .size()
    .unstack(fill_value=0)
    .sort_index()
)[ordered]

fig = plt.figure(figsize=(11, 6.2), layout="constrained")
axd = fig.subplot_mosaic(
    [
        ["totals", "trend"],
        ["totals", "cost"],
    ],
    width_ratios=[1.0, 1.4],
)

axd["totals"].barh(per_agency.index, per_agency.values, color=sns.color_palette("colorblind", len(per_agency)))
axd["totals"].set(title="Missions per agency", xlabel="Count")

palette = sns.color_palette("colorblind", n_colors=len(ordered))
for agency, color in zip(ordered, palette, strict=True):
    axd["trend"].plot(yearly.index, yearly[agency], color=color, linewidth=1.6, label=agency)
axd["trend"].set(title="Yearly missions per agency", xlabel="Year", ylabel="Count")
axd["trend"].legend(frameon=False, ncol=2, fontsize=8)

# Mission cost spans roughly five orders of magnitude (sub-million-dollar
# cubesats up to flagship missions in the tens of billions). Linear bins
# would lump every cheap mission into one fat bar at the left, so we build
# bins that are evenly spaced **on the log scale** before applying the log
# x-axis. That way each bar represents a comparable multiplicative range.
cost_values = missions["cost_million_usd"].astype("float64").dropna()
cost_bins = np.geomspace(cost_values.min(), cost_values.max(), 25)
axd["cost"].hist(cost_values, bins=cost_bins, color="#1e5128", edgecolor="white")
axd["cost"].set_xscale("log")
axd["cost"].set(title="Cost distribution (log-spaced bins)", xlabel="Cost (M USD, log)")

fig.suptitle("subplot_mosaic: one named panel per question", fontsize=15)
plt.show()

# %% [markdown]
# ## Step 4 — Re-express the bar chart as `seaborn.objects` grammar
#
# Slide 10-2 introduces `seaborn.objects`. The same chart is now described as
# `Plot(data, x=..., y=...).add(Mark())`. The grammar makes the encoding
# explicit instead of hiding it behind a single chart-type function.

# %%
section("Step 4: seaborn.objects grammar (Plot + Mark)")

missions_so = missions.convert_dtypes(dtype_backend="numpy_nullable")

(
    so.Plot(missions_so, x="agency_short")
    .add(so.Bar(), so.Count())
    .label(
        title="Step 4: same bar chart, expressed as Plot + Mark + Stat (Count)",
        x="Agency",
        y="Missions flown",
    )
).show()

# %% [markdown]
# ## Step 5 — Add a Stat: explicit aggregation
#
# Slide 10-3: marks, stats, and moves are *separate* choices. Here we ask for
# the **mean cost** per agency by composing a `Bar` mark with an `Agg("mean")`
# stat. There is no hidden aggregation: we asked for `mean` and we get `mean`.

# %%
section("Step 5: Mark + Stat (Agg)")

# Render onto an explicit figure so the constrained layout makes room for
# the long agency labels and the legend without them overlapping.
fig = plt.figure(figsize=(10, 4.5), layout="constrained")
(
    so.Plot(missions_so, x="agency_short", y="cost_million_usd", color="agency_short")
    .add(so.Bar(), so.Agg("mean"))
    .label(
        title="Step 5: mean mission cost per agency, with explicit Agg('mean')",
        x="Agency",
        y="Mean cost (M USD)",
        color="Agency",
    )
    .on(fig)
    .plot()
)
plt.show()

# %% [markdown]
# ## Step 6 — Add a Move: dodge two categories side by side
#
# Now we want mean cost per agency *and* per decade. `Dodge()` moves the bars
# of each color group sideways instead of stacking them, so the two
# categorical variables stay readable.

# %%
section("Step 6: Mark + Stat + Move (Dodge)")

modern = missions_so[missions_so["launch_year"] >= 1980].copy()
modern["decade"] = (modern["launch_year"] // 10 * 10).astype("int64").astype("string") + "s"

fig = plt.figure(figsize=(10, 4.5), layout="constrained")
(
    so.Plot(modern, x="agency_short", y="cost_million_usd", color="decade")
    .add(so.Bar(), so.Agg("mean"), so.Dodge())
    .label(
        title="Step 6: mean cost per agency per decade, separated with Dodge()",
        x="Agency",
        y="Mean cost (M USD)",
        color="Decade",
    )
    .on(fig)
    .plot()
)
plt.show()

# %% [markdown]
# ## Step 7 — Add a Scale: log axis tames orders of magnitude
#
# Slide 10-4: when one variable spans orders of magnitude, the right answer is
# usually a log scale, not a redesign. Here we plot every mission as a point
# (`Dot`), put the cost on a log y-axis, and color by agency.

# %%
section("Step 7: Mark + Scale (log)")

(
    so.Plot(missions_so, x="launch_year", y="cost_million_usd", color="agency_short")
    .add(so.Dot(pointsize=6, alpha=0.75))
    .scale(y="log")
    .label(
        title="Step 7: mission cost over time, log-scaled to keep both ends visible",
        x="Launch year",
        y="Cost (M USD, log)",
        color="Agency",
    )
).show()

# %% [markdown]
# ## Step 8 — Add a Facet: split the view when overlap hides the story
#
# Slide 10-4 again: when one panel is too crowded, facets split it into small
# multiples. The grammar stays the same; we just add `.facet(col=...)`.

# %%
section("Step 8: Mark + Scale + Facet")

mc_focus = missions_so[
    missions_so["mission_class"].isin(["crewed", "orbiter", "lander", "rover", "telescope"])
]

# With facets, `.label(title=...)` would repeat the same title on every
# panel. Use `fig.suptitle` for a single figure-level title and let `.label`
# only carry per-panel labels.
fig = plt.figure(figsize=(14, 4.8), layout="constrained")
(
    so.Plot(mc_focus, x="launch_year", y="cost_million_usd", color="agency_short")
    .add(so.Dot(pointsize=6, alpha=0.85))
    .scale(y="log")
    .facet(col="mission_class")
    .label(
        x="Launch year",
        y="Cost (M USD, log)",
        color="Agency",
    )
    .on(fig)
    .plot()
)
fig.suptitle("Step 8: faceted by mission class so each panel tells one sub-story", fontsize=14)
plt.show()

# %% [markdown]
# ## Step 9 — Bridge to maps: vectorized WKT parsing
#
# Slide 10-5: most plotting libraries cannot consume raw WKT geometry text;
# they need numeric `lon` / `lat` columns. `Series.str.extract` with named
# groups does the conversion in one vectorized step.

# %%
section("Step 9: vectorized WKT parsing for map-ready coordinates")

quakes = read_earthquakes()
print(quakes[["place", "geometry", "lon", "lat", "depth"]].head(5).to_string(index=False))

subset = quakes.nlargest(150, "mag")

fig, ax = plt.subplots(figsize=(9.5, 4.8), layout="constrained")
scatter = ax.scatter(
    subset["lon"],
    subset["lat"],
    c=subset["mag"],
    s=subset["depth"].fillna(0) + 25,
    cmap="viridis",
    alpha=0.8,
    edgecolor="white",
    linewidth=0.4,
)
ax.set(
    title="Step 9: 150 strongest earthquakes after vectorized lon/lat extraction",
    xlabel="Longitude",
    ylabel="Latitude",
)
fig.colorbar(scatter, ax=ax, label="Magnitude")
plt.show()

# %% [markdown]
# ## Step 10 — Save the final figure for slides or papers
#
# Slide 7-4: SVG / PDF for publication, PNG for screens. We keep the file in
# memory for the demo; uncomment the savefig line to write to disk.

# %%
section("Step 10: savefig with the right format")

fig, ax = plt.subplots(figsize=(7, 4), layout="constrained")
ax.plot(yearly.index, yearly.sum(axis=1), color="#1e5128", linewidth=2.2)
ax.set(
    title="Total missions launched per year (all agencies)",
    xlabel="Year",
    ylabel="Mission count",
)
plt.show()

# fig.savefig("missions_per_year.svg", bbox_inches="tight")  # crisp for papers
# fig.savefig("missions_per_year.png", dpi=200, bbox_inches="tight")  # for slides

print("\nRecap of the layered grammar we built up:")
print("  Plot (data + x/y) + Mark (Bar/Dot) + Stat (Count/Agg)")
print("                   + Move (Dodge) + Scale (log) + Facet (col=...)")
print("Each step changed exactly one concept; the overall figure stayed honest.")
