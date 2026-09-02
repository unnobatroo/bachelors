# %% [markdown]
# # Lecture 9 Demo 2: pandas in Practice
#
# Aligned with the "pandas in Practice" section of the lecture.
# Uses only local data so the demo works offline.

# %%
from __future__ import annotations

import pandas as pd

from demo_utils import data_path, section


section("Versions")
print(f"pandas: {pd.__version__}")
print("This demo is organized around typical pandas tasks.")

# %% [markdown]
# ## 1. Inspect the table before touching it

# %%
section("Inspect the table before touching it")

missions = pd.read_csv(data_path("space_missions.csv"), dtype_backend="pyarrow")
quakes = pd.read_csv(data_path("earthquakes.csv"), dtype_backend="pyarrow")

print("First rows:")
print(missions.head(5).to_string(index=False))
print("\nMissions dtypes:")
print(missions.dtypes.to_string())
print("\nMissing values:")
print(missions.isna().sum().to_string())

# %% [markdown]
# ## 2. Filter to the relevant rows

# %%
section("Filter to the relevant rows")

moon_missions = missions.loc[
    missions["destination"] == "Moon",
    ["mission_name", "launch_year", "agency", "cost_million_usd"],
].sort_values("launch_year")

print(moon_missions.to_string(index=False))

# %% [markdown]
# ## 3. Derive meaningful columns safely

# %%
section("Derive meaningful columns safely")

missions = missions.copy()
missions["mission_style"] = "uncrewed"
missions.loc[missions["crew_size"] > 0, "mission_style"] = "crewed"

missions["distance_band"] = "local"
missions.loc[missions["distance_km"] >= 384_400, "distance_band"] = "lunar_or_beyond"
missions.loc[missions["distance_km"] >= 1_000_000, "distance_band"] = "deep_space"

print(
    missions[
        ["mission_name", "crew_size", "mission_style", "distance_km", "distance_band"]
    ]
    .head(8)
    .to_string(index=False)
)

# %% [markdown]
# ## 4. Parse a messy text column with vectorized operations

# %%
section("Parse a messy text column")

wkt_pattern = r"POINT Z \((?P<longitude>.*?) (?P<latitude>.*?) (?P<depth>.*?)\)"

coordinates = quakes["geometry"].str.extract(wkt_pattern).astype("float32[pyarrow]")

quakes = pd.concat([quakes, coordinates], axis=1)

print(
    quakes[["place", "latitude", "longitude", "depth"]].head(5).to_string(index=False)
)

# %% [markdown]
# ## 5. Build a summary table

# %%
section("Build a summary table")

destination_summary = (
    missions[missions["destination"].isin(["Moon", "Mars", "ISS"])]
    .assign(
        cost_per_day=lambda df: (df["cost_million_usd"] / df["duration_days"]).round(2)
    )
    .groupby("destination")
    .agg(
        mission_count=("mission_name", "size"),
        avg_cost_per_day=("cost_per_day", "mean"),
        crewed_missions=("mission_style", lambda s: (s == "crewed").sum()),
    )
    .sort_values("avg_cost_per_day", ascending=False)
    .round(2)
)

print(destination_summary.to_string())

# %% [markdown]
# ## 6. Attach lookup data with merge()

# %%
section("Attach lookup data with merge()")

agency_info = pd.DataFrame(
    {
        "agency": ["NASA", "CNSA", "ISRO"],
        "region": ["USA", "China", "India"],
        "crew_program": ["yes", "planned", "planned"],
    }
)

moon_with_agency = moon_missions.merge(
    agency_info,
    on="agency",
    how="left",
    validate="many_to_one",
)

print(moon_with_agency.to_string(index=False))

# %% [markdown]
# ## 7. Handle missing values explicitly

# %%
section("Handle missing values explicitly")

quake_alerts = (
    quakes.loc[:, ["place", "mag", "mmi", "alert"]]
    .assign(alert=lambda df: df["alert"].fillna("unknown"))
    .dropna(subset=["mmi"])
    .sort_values(["mmi", "mag"], ascending=[False, False])
    .head(5)
)

print(quake_alerts.to_string(index=False))

# %% [markdown]
# ## 8. Parse time columns before grouping

# %%
section("Parse time columns before grouping")

quakes = quakes.assign(
    time_utc=pd.to_datetime(quakes["time"], unit="ms", utc=True),
)

quakes_by_year = (
    quakes.assign(year=quakes["time_utc"].dt.year)
    .groupby("year")
    .agg(
        event_count=("id", "size"),
        max_mag=("mag", "max"),
    )
    .sort_index()
    .tail(6)
)

print(quakes_by_year.to_string())
