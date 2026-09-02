from __future__ import annotations

import pandas as pd

from poseidon.data_cleaning import DataCleaner


def test_validate_report_detects_errors() -> None:
    ok, errors = DataCleaner.validate_report({"speed_knots": -1})
    assert not ok
    assert errors


def test_clean_historical_batch_filters_bad_rows() -> None:
    df = pd.DataFrame(
        [
            {
                "vessel_id": "v1",
                "timestamp": "2025-01-01T00:00:00Z",
                "speed_knots": 10,
                "draft_m": 4,
                "heading_deg": 10,
                "fuel_rate_lph": 100,
            },
            {
                "vessel_id": "v2",
                "timestamp": "2025-01-01T00:00:00Z",
                "speed_knots": 100,
                "draft_m": 4,
                "heading_deg": 10,
                "fuel_rate_lph": 100,
            },
        ]
    )
    cleaned = DataCleaner.clean_historical_batch(df)
    assert len(cleaned) == 1


def test_distribution_by_flag() -> None:
    df = pd.DataFrame(
        [
            {
                "vessel_id": "v1",
                "timestamp": pd.Timestamp("2025-02-01", tz="UTC"),
                "speed_knots": 8,
                "flag_state": "A",
            },
            {
                "vessel_id": "v1",
                "timestamp": pd.Timestamp("2025-02-02", tz="UTC"),
                "speed_knots": 18,
                "flag_state": "A",
            },
        ]
    )
    out = DataCleaner.distribution_by_flag(
        df,
        {"speed_safe": 10, "speed_moderate": 15, "speed_danger": 22},
        2025,
        2,
    )
    assert set(out["risk_band"].astype(str)) == {"Safe", "Unsafe"}
