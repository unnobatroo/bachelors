from __future__ import annotations

import pandas as pd
import pytest
from fastapi.testclient import TestClient
from pathlib import Path

from poseidon.main import app, get_vessel_manager
from poseidon.vessel_manager import VesselManager


@pytest.fixture
def manager(tmp_path: Path) -> VesselManager:
    historical = pd.DataFrame(
        [
            {
                "vessel_id": "vessel_test_001",
                "timestamp": "2025-02-01T00:00:00Z",
                "speed_knots": 8.0,
                "draft_m": 5.0,
                "heading_deg": 120.0,
                "fuel_rate_lph": 200.0,
            },
            {
                "vessel_id": "vessel_test_001",
                "timestamp": "2025-02-01T01:00:00Z",
                "speed_knots": 18.0,
                "draft_m": 5.2,
                "heading_deg": 121.0,
                "fuel_rate_lph": 210.0,
            },
        ]
    )
    historical.to_csv(tmp_path / "historical_readings.csv", index=False)
    return VesselManager.for_tests(tmp_path)


@pytest.fixture
def client(manager: VesselManager) -> TestClient:
    app.dependency_overrides[get_vessel_manager] = lambda: manager
    with TestClient(app) as test_client:
        yield test_client
    app.dependency_overrides.clear()
