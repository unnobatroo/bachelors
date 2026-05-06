from __future__ import annotations

from pathlib import Path

import matplotlib.pyplot as plt
import pandas as pd
import seaborn as sns


HERE = Path(__file__).resolve().parent
WKT_PATTERN = r"POINT Z \((?P<lon>.*?) (?P<lat>.*?) (?P<depth>.*?)\)"


def data_path(filename: str) -> Path:
    path = HERE / filename
    if not path.exists():
        raise FileNotFoundError(f"Missing demo dataset: {path}")
    return path


def section(title: str) -> None:
    bar = "=" * len(title)
    print(f"\n{bar}\n{title}\n{bar}")


def configure_static_theme() -> None:
    sns.set_theme(style="whitegrid", palette="colorblind")
    plt.rcParams.update(
        {
            "figure.facecolor": "#fbfdfb",
            "axes.facecolor": "#ffffff",
            "axes.edgecolor": "#d8e4d9",
            "axes.labelcolor": "#1f2a22",
            "axes.titlecolor": "#1f2a22",
            "grid.color": "#d8e4d9",
            "grid.alpha": 0.5,
        }
    )


def read_space_missions() -> pd.DataFrame:
    """Load the lecture's curated space-mission dataset.

    Adds an ``agency_short`` column that collapses joint-agency labels
    (``NASA/ESA``, ``ESA/JAXA``...) to their primary partner so that
    "compare by agency" charts stay readable with a small palette.
    """
    df = pd.read_csv(data_path("space_missions.csv"), dtype_backend="pyarrow")
    primary = df["agency"].astype("string").str.split("/").str[0]
    df = df.assign(
        agency_short=primary.replace({"USSR": "USSR/Roscosmos", "Roscosmos": "USSR/Roscosmos"}),
        decade=(df["launch_year"] // 10 * 10).astype("int64"),
    )
    return df


def read_space_race() -> pd.DataFrame:
    return pd.read_csv(data_path("space_race_data.csv"), dtype_backend="pyarrow")


def read_earthquakes() -> pd.DataFrame:
    quakes = pd.read_csv(data_path("earthquakes.csv"), dtype_backend="pyarrow")
    return add_quake_coordinates(quakes)


def read_earthquakes_large() -> pd.DataFrame:
    return pd.read_csv(data_path("earthquakes_large.csv"), dtype_backend="pyarrow")


def add_quake_coordinates(df: pd.DataFrame) -> pd.DataFrame:
    coords = df["geometry"].str.extract(WKT_PATTERN).astype("float64[pyarrow]")
    return df.join(coords)
