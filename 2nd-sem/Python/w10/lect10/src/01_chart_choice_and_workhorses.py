# %% [markdown]
# # Lecture 10 Demo 1: Chart Choice and Workhorse Charts
#
# Aligned with the "Why Visuals Matter" section of the lecture.
# Run cell by cell in JupyterLab, Cursor, or VS Code interactive mode.
#
# Storyline: we use a curated 280-row space-mission dataset (1957-2026) and let
# each workhorse chart answer one specific question:
#
# 1. **Anscombe's quartet**: plot before trusting summary statistics.
# 2. **Bar chart** (compare / rank): which agency has flown the most missions?
# 3. **Line chart** (change over time): how did the agency mix evolve since
#    Sputnik?
# 4. **Histogram + box** (distribution): how are mission costs spread out, and
#    where do the outliers sit?
# 5. **Scatter** (relationship): does it cost more to fly longer missions?
# 6. **Readable defaults**: long mission names handled cleanly with horizontal
#    bars and direct labels.

# %%
from __future__ import annotations

import matplotlib.pyplot as plt
import numpy as np
import pandas as pd
import seaborn as sns

from demo_utils import configure_static_theme, read_space_missions, section


configure_static_theme()

section("Versions")
print(f"pandas: {pd.__version__}")
print(f"seaborn: {sns.__version__}")
print("This demo focuses on choosing safe default charts before touching advanced tooling.")

# %% [markdown]
# ## 1. Anscombe's quartet: why plotting matters

# %%
section("Anscombe's quartet: same stats, different shape")

quartet = pd.DataFrame(
    {
        "dataset": ["I"] * 11 + ["II"] * 11 + ["III"] * 11 + ["IV"] * 11,
        "x": [
            10, 8, 13, 9, 11, 14, 6, 4, 12, 7, 5,
            10, 8, 13, 9, 11, 14, 6, 4, 12, 7, 5,
            10, 8, 13, 9, 11, 14, 6, 4, 12, 7, 5,
            8, 8, 8, 8, 8, 8, 8, 19, 8, 8, 8,
        ],
        "y": [
            8.04, 6.95, 7.58, 8.81, 8.33, 9.96, 7.24, 4.26, 10.84, 4.82, 5.68,
            9.14, 8.14, 8.74, 8.77, 9.26, 8.10, 6.13, 3.10, 9.13, 7.26, 4.74,
            7.46, 6.77, 12.74, 7.11, 7.81, 8.84, 6.08, 5.39, 8.15, 6.42, 5.73,
            6.58, 5.76, 7.71, 8.84, 8.47, 7.04, 5.25, 12.50, 5.56, 7.91, 6.89,
        ],
    }
)

summary = quartet.groupby("dataset").agg(
    mean_x=("x", "mean"),
    mean_y=("y", "mean"),
    corr_xy=("x", lambda s: quartet.loc[s.index, "x"].corr(quartet.loc[s.index, "y"])),
)
print(summary.round(2).to_string())

fig, axes = plt.subplots(2, 2, figsize=(9, 7), layout="constrained")
for ax, (label, group) in zip(axes.flat, quartet.groupby("dataset"), strict=True):
    ax.scatter(group["x"], group["y"], s=45, alpha=0.9)
    ax.set_title(f"Dataset {label}")
    ax.set_xlim(2, 20)
    ax.set_ylim(2, 14)
    ax.grid(alpha=0.25)
fig.suptitle("Plot first. Summary statistics are not enough.", fontsize=15)
plt.show()

# %% [markdown]
# ## 2. Compare / rank with a bar chart
#
# Question: **which agency has flown the most missions in this dataset?**
# Sort by value, draw horizontally so long labels stay readable, and add direct
# value labels so the chart answers the question without a legend.

# %%
section("Compare / rank: missions per agency (bar)")

missions = read_space_missions()
print(f"loaded {len(missions)} missions across {missions['launch_year'].nunique()} years\n")

per_agency = (
    missions.groupby("agency_short", observed=True)
    .size()
    .sort_values()
    .rename("missions")
)
print(per_agency.to_string())

palette = sns.color_palette("colorblind", n_colors=len(per_agency))
fig, ax = plt.subplots(figsize=(9, 4.8), layout="constrained")
bars = ax.barh(per_agency.index, per_agency.values, color=palette)
ax.set_title("NASA still leads, but the agency mix is broader than people think")
ax.set_xlabel("Missions flown (1957-2026)")
for bar, value in zip(bars, per_agency.values, strict=True):
    ax.text(value + 1.5, bar.get_y() + bar.get_height() / 2, f"{value}", va="center")
ax.set_xlim(0, per_agency.max() * 1.12)
plt.show()

# %% [markdown]
# ## 3. Change over time with a line chart
#
# Question: **how did the agency mix evolve from Sputnik to today?**
# A line chart per agency shows the Cold War duopoly, the 1990s slowdown,
# and the rise of commercial + Asian programs after 2010.

# %%
section("Change over time: yearly missions per agency (line)")

yearly = (
    missions.groupby(["launch_year", "agency_short"], observed=True)
    .size()
    .unstack(fill_value=0)
    .sort_index()
)

ordered = per_agency.sort_values(ascending=False).index.tolist()
yearly = yearly[ordered]

color_map = dict(zip(ordered, sns.color_palette("colorblind", n_colors=len(ordered)), strict=True))
fig, ax = plt.subplots(figsize=(11, 5.0), layout="constrained")
for agency in ordered:
    ax.plot(
        yearly.index,
        yearly[agency],
        linewidth=2.0 if agency in {"NASA", "USSR/Roscosmos"} else 1.6,
        color=color_map[agency],
        label=agency,
    )
ax.set_title("Yearly mission counts: Cold War duopoly, then a much wider field")
ax.set_xlabel("Launch year")
ax.set_ylabel("Missions launched")
ax.legend(title="Agency", loc="upper left", frameon=False, ncol=2)
plt.show()

# %% [markdown]
# ## 4. Distribution with a histogram + box
#
# Question: **how are mission costs spread out?** A handful of flagship
# missions (Apollo, ISS, Webb...) live far in the tail, so we plot the cost on
# a log axis and pair the histogram with a box plot to show spread + outliers
# explicitly.

# %%
section("Distribution: mission cost (histogram + box on log scale)")

cost = missions["cost_million_usd"].astype("float64")
log_bins = np.geomspace(cost.min(), cost.max(), 22)
median_cost = cost.median()
p95_cost = cost.quantile(0.95)
fig, axes = plt.subplots(
    1, 2, figsize=(11, 4.4), layout="constrained", gridspec_kw={"width_ratios": [1.4, 1.0]}
)

axes[0].hist(
    cost,
    bins=log_bins,
    color=sns.color_palette("colorblind")[0],
    edgecolor="white",
    linewidth=0.8,
)
axes[0].set_xscale("log")
axes[0].axvline(median_cost, color="#1e5128", linewidth=2, linestyle="--", label="median")
axes[0].axvline(p95_cost, color="#c27a0b", linewidth=2, linestyle=":", label="95th percentile")
axes[0].set_title("Log-spaced bins reveal the bulk and the tail together")
axes[0].set_xlabel("Mission cost (million USD, log bins)")
axes[0].set_ylabel("Mission count")
axes[0].legend(frameon=False, loc="upper left")

bp = axes[1].boxplot(
    cost,
    vert=True,
    patch_artist=True,
    widths=0.55,
    tick_labels=["all missions"],
    medianprops=dict(color="#1e5128", linewidth=2),
    flierprops=dict(marker="o", markersize=4, markerfacecolor="#5a6a5f", markeredgecolor="none", alpha=0.5),
)
bp["boxes"][0].set_facecolor(sns.color_palette("colorblind")[0])
bp["boxes"][0].set_alpha(0.85)
bp["boxes"][0].set_edgecolor("white")
axes[1].set_yscale("log")
axes[1].set_ylabel("Cost (M USD, log scale)")
axes[1].set_title("Spread + outliers in one glance")

plt.show()

print(f"median cost: {median_cost:,.1f} M USD")
print(f"95th percentile: {p95_cost:,.1f} M USD")
print(f"max cost: {cost.max():,.1f} M USD")

# %% [markdown]
# ## 5. Relationship with a scatter plot
#
# Question: **do longer missions cost more?** Scatter is the right default;
# we add a third variable (agency) via color and use a log y-axis again because
# costs span four orders of magnitude.

# %%
section("Relationship: duration vs cost colored by agency (scatter)")

scatter_df = missions.dropna(subset=["duration_days", "cost_million_usd"]).copy()
scatter_df = scatter_df[scatter_df["duration_days"] > 0]

agency_palette = sns.color_palette("colorblind", n_colors=len(ordered))
fig, ax = plt.subplots(figsize=(10, 5.4), layout="constrained")
for agency, color in zip(ordered, agency_palette, strict=True):
    sub = scatter_df[scatter_df["agency_short"] == agency]
    if sub.empty:
        continue
    ax.scatter(
        sub["duration_days"],
        sub["cost_million_usd"],
        s=42,
        alpha=0.75,
        color=color,
        edgecolor="white",
        linewidth=0.5,
        label=agency,
    )
ax.set_xscale("log")
ax.set_yscale("log")
ax.set_title("Longer missions tend to cost more, but agency choice still matters")
ax.set_xlabel("Mission duration (days, log scale)")
ax.set_ylabel("Mission cost (million USD, log scale)")
ax.legend(title="Agency", loc="upper left", frameon=False, ncol=2)
plt.show()

# %% [markdown]
# ## 6. Readable defaults for long category labels
#
# Question: **which were the most expensive missions ever flown?** Long mission
# names handle cleanly with horizontal bars and direct value labels, no legend
# required.

# %%
section("Readable defaults for long labels")

top_costs = (
    missions.loc[:, ["mission_name", "cost_million_usd", "agency_short"]]
    .sort_values("cost_million_usd", ascending=False)
    .head(8)
    .sort_values("cost_million_usd")
)

palette = sns.color_palette("colorblind", n_colors=len(top_costs))
fig, ax = plt.subplots(figsize=(10, 5), layout="constrained")
ax.barh(top_costs["mission_name"], top_costs["cost_million_usd"], color=palette)
ax.set_xscale("log")
ax.set_title("Horizontal bars + log scale handle long labels and skewed values")
ax.set_xlabel("Cost (million USD, log scale)")

for y, value in enumerate(top_costs["cost_million_usd"]):
    ax.text(value * 1.05, y, f"  {value:,.0f}", va="center")

plt.show()

print("\nChecklist:")
print("- choose by question, not by chart habit")
print("- prefer bars / lines / scatter / histograms before fancy formats")
print("- log scales tame skew without lying about the numbers")
print("- direct labels and colorblind-safe palettes")
