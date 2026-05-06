# %% [markdown]
# # Lecture 9 Demo 3: When Polars Is Worth It
#
# Aligned with the "When Polars Is Worth It" section of the lecture.
# Uses the same local datasets as the pandas demo so you can compare
# workload choices rather than learn a disconnected second universe.

# %%
from __future__ import annotations

import pandas as pd
import polars as pl

from demo_utils import data_path, section


section("Versions")
print(f"pandas: {pd.__version__}")
print(f"polars: {pl.__version__}")
print("This demo focuses on when Polars is worth the extra mental model.")

# %% [markdown]
# ## 1. Large CSV, small answer table

# %%
section("Large CSV, small answer table")

quake_query = (
    pl.scan_csv(data_path("earthquakes.csv"))
    .select("net", "mag", "tsunami", "place")
    .filter(pl.col("mag").cast(pl.Float64) >= 6.0)
    .group_by("net")
    .agg(
        pl.len().alias("quake_count"),
        pl.col("mag").cast(pl.Float64).mean().alias("avg_magnitude"),
        pl.col("tsunami").cast(pl.Int64).sum().alias("tsunami_events"),
    )
    .sort("quake_count", descending=True)
)

print("Optimized plan:")
print(quake_query.explain())
quake_summary = quake_query.collect()
print("\nMaterialized summary:")
print(quake_summary.head(10))

# %% [markdown]
# ## 2. Rank within each agency

# %%
section("Rank within each agency")

missions = pl.read_csv(data_path("space_missions.csv"))

ranked_missions = (
    missions.select("agency", "mission_name", "cost_million_usd")
    .with_columns(
        pl.col("cost_million_usd")
        .rank("dense", descending=True)
        .over("agency")
        .alias("cost_rank_within_agency")
    )
    .filter(pl.col("cost_rank_within_agency") <= 2)
    .sort(["agency", "cost_rank_within_agency"])
)

print(ranked_missions)

# %% [markdown]
# ## 3. Optimize in Polars, deliver in pandas

# %%
section("Optimize in Polars, deliver in pandas")

ranked_missions_pd = ranked_missions.to_pandas()
print(ranked_missions_pd.head(6).to_string(index=False))

round_tripped = pl.from_pandas(ranked_missions_pd)
print("\nRound-tripped schema:")
print(round_tripped.schema)
