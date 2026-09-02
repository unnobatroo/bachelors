from __future__ import annotations

import pandas as pd
from typing import Any


class DataCleaner:
    REQUIRED_READINGS = ("speed_knots", "draft_m", "heading_deg", "fuel_rate_lph")
    HISTORICAL_COLUMNS = (
        "vessel_id",
        "timestamp",
        "speed_knots",
        "draft_m",
        "heading_deg",
        "fuel_rate_lph",
    )

    @staticmethod
    def validate_report(readings: dict[str, Any]) -> tuple[bool, list[str]]:
        # quick validation of an incoming readings dict
        errors: list[str] = []

        for key in DataCleaner.REQUIRED_READINGS:
            if key not in readings:
                errors.append(f"Missing reading: {key}")
                continue
            value = readings[key]
            if not isinstance(value, int | float):
                errors.append(f"Reading {key} must be numeric")
                continue

            numeric = float(value)
            # non-negative checks for physical quantities
            if key in {"speed_knots", "draft_m", "fuel_rate_lph"} and numeric < 0:
                errors.append(f"Reading {key} cannot be negative")
            if key == "speed_knots" and numeric > 50:
                errors.append("Reading speed_knots cannot exceed 50")
            if key == "heading_deg" and not (0 <= numeric < 360):
                errors.append("Reading heading_deg must be in [0, 360)")

        return len(errors) == 0, errors

    @staticmethod
    def clean_historical_batch(df: pd.DataFrame) -> pd.DataFrame:
        # vectorised cleaning pipeline for historical dataframe
        cleaned = df.copy()

        # drop rows missing key identifiers
        cleaned = cleaned.dropna(subset=["vessel_id", "timestamp"])

        # coerce numeric columns, invalid entries become NaN
        cleaned[["speed_knots", "draft_m", "heading_deg", "fuel_rate_lph"]] = cleaned[
            ["speed_knots", "draft_m", "heading_deg", "fuel_rate_lph"]
        ].apply(pd.to_numeric, errors="coerce")

        # filter out physically impossible values and obvious glitches
        cleaned = cleaned.loc[
            cleaned["speed_knots"].between(0, 50)
            & (cleaned["draft_m"] >= 0)
            & (cleaned["fuel_rate_lph"] >= 0)
            & cleaned["heading_deg"].between(0, 359.999999)
            ]

        # parse timestamps once and drop parse failures
        cleaned = cleaned.assign(
            timestamp=pd.to_datetime(cleaned["timestamp"], utc=True, errors="coerce")
        )
        cleaned = cleaned.dropna(subset=["timestamp"])

        return cleaned.reset_index(drop=True)

    @staticmethod
    def parse_wkt_column(series: pd.Series) -> pd.DataFrame:
        # extract lon/lat from a series of WKT POINT strings using named groups
        pattern = r"POINT\s*\(\s*(?P<lon>-?\d+\.?\d*)\s+(?P<lat>-?\d+\.?\d*)\s*\)"
        coords = series.str.extract(pattern).astype("float32[pyarrow]")
        return coords

    @staticmethod
    def categorise_by_speed(df: pd.DataFrame, thresholds: dict[str, float]) -> pd.DataFrame:
        result = df.copy()

        safe = thresholds["speed_safe"]
        moderate = thresholds["speed_moderate"]
        danger = thresholds["speed_danger"]

        result["risk_band"] = pd.cut(
            result["speed_knots"],
            bins=[-0.000001, safe, moderate, danger, float("inf")],
            labels=["Safe", "Moderate", "Unsafe", "Danger"],
            include_lowest=True,
            ordered=True,
        )

        return result

    @staticmethod
    def distribution_by_flag(
            df: pd.DataFrame,
            thresholds: dict[str, float],
            year: int,
            month: int,
    ) -> pd.DataFrame:
        if month < 1 or month > 12:
            raise ValueError("Invalid month")

        if "timestamp" not in df.columns:
            raise ValueError("Historical data missing timestamp")

        month_mask = (df["timestamp"].dt.year == year) & (df["timestamp"].dt.month == month)
        month_df = df.loc[month_mask]
        if month_df.empty:
            raise ValueError("No data for requested period")

        month_df = DataCleaner.categorise_by_speed(month_df, thresholds)

        grouped = (
            month_df.groupby(["flag_state", "risk_band"], observed=True)
            .agg(reports=("vessel_id", "size"))
            .reset_index()
        )

        grouped["percentage"] = (
                grouped["reports"] / grouped.groupby("flag_state")["reports"].transform("sum") * 100
        )

        return grouped.sort_values(["flag_state", "risk_band"]).reset_index(drop=True)
