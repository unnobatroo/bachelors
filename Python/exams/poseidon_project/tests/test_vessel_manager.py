from __future__ import annotations

import pytest

from poseidon.vessel_manager import InvalidReportError, UnauthorizedVesselError, VesselManager


def test_ingest_report_success(tmp_path) -> None:
    manager = VesselManager.for_tests(tmp_path)
    reading = manager.ingest_report(
        "vessel_test_001",
        {
            "speed_knots": 10.0,
            "draft_m": 5.0,
            "heading_deg": 50.0,
            "fuel_rate_lph": 100.0,
        },
    )
    assert reading.vessel_id == "vessel_test_001"
    assert manager.total_reports == 1


def test_ingest_rejects_unknown_vessel(tmp_path) -> None:
    manager = VesselManager.for_tests(tmp_path)
    with pytest.raises(UnauthorizedVesselError):
        manager.ingest_report(
            "unknown",
            {"speed_knots": 1.0, "draft_m": 1.0, "heading_deg": 1.0, "fuel_rate_lph": 1.0},
        )


def test_ingest_rejects_bad_payload(tmp_path) -> None:
    manager = VesselManager.for_tests(tmp_path)
    with pytest.raises(InvalidReportError):
        manager.ingest_report("vessel_test_001", {"speed_knots": -1.0})
