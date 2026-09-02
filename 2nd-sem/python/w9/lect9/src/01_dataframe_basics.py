# %% [markdown]
# # Lecture 9 Demo 1: Modern Python Data Workflow
#
# Aligned with the "Modern Python Data Workflow" section of the lecture.
# Uses only local data so the demo works offline.

# %%
from __future__ import annotations

import pandas as pd

from demo_utils import data_path, section


section("Versions")
print(f"pandas: {pd.__version__}")
print("This demo focuses on the fast loop: read, inspect, ask, answer.")

# %% [markdown]
# ## 1. Series and DataFrame: the minimal mental model

# %%
section("Series and DataFrame: the minimal mental model")

penguin_counts = pd.Series(
    [152, 68, 124],
    index=["Adelie", "Chinstrap", "Gentoo"],
    name="records",
)

df_islands = pd.DataFrame(
    {
        "island": ["Torgersen", "Dream", "Biscoe"],
        "records": [152, 68, 124],
        "sampled": [True, True, True],
    },
    index=["Adelie", "Chinstrap", "Gentoo"],
)

print(penguin_counts)
print(f"\nAdelie count: {penguin_counts['Adelie']}")
print(df_islands)
print("\nColumn access:")
print(df_islands["island"])
print("\nLabel-based row access:")
print(df_islands.loc["Adelie"])
print("\nPosition-based row access:")
print(df_islands.iloc[0])

# %% [markdown]
# ## 2. Read a real table and inspect it quickly

# %%
section("Read a real table and inspect it")

missions = pd.read_csv(
    data_path("space_missions.csv"),
    dtype_backend="pyarrow",
)

print(missions.head(5).to_string(index=False))
print("\nDtypes:")
print(missions.dtypes.to_string())
print("\nMissing values:")
print(missions.isna().sum().to_string())

# %% [markdown]
# ## 3. Ask first questions with common access patterns

# %%
section("Ask first questions with common access patterns")

print("First row with iloc:")
print(missions.iloc[0])

moon_missions = missions.loc[
    missions["destination"] == "Moon",
    ["mission_name", "launch_year", "agency", "cost_million_usd"],
].sort_values("launch_year")

print("\nMoon missions with loc + sorting:")
print(moon_missions.to_string(index=False))

agency_counts = missions["agency"].value_counts()
print("\nAgency counts:")
print(agency_counts.to_string())

top_agency = agency_counts.index[0]
print(f"\nMost frequent agency in the demo data: {top_agency}")
