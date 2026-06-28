from __future__ import annotations

import pandas as pd
from datetime import UTC, datetime
from pathlib import Path

from poseidon.persistence import PersistenceService
from poseidon.vessel import VesselReading


def test_append_and_load_realtime(tmp_path: Path) -> None:
    persistence = PersistenceService(tmp_path / "r.json", tmp_path / "h.csv")
    reading = VesselReading("v1", {"speed_knots": 1.0}, datetime.now(UTC))
    persistence.append_reading(reading)
    loaded = persistence.load_realtime_readings()
    assert len(loaded) == 1
    assert loaded[0].vessel_id == "v1"


def test_load_historical_missing_file_returns_empty(tmp_path: Path) -> None:
    persistence = PersistenceService(tmp_path / "r.json", tmp_path / "missing.csv")
    df = persistence.load_historical_data()
    assert isinstance(df, pd.DataFrame)
    assert df.empty
