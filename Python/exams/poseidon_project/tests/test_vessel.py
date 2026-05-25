from __future__ import annotations

from datetime import UTC, datetime

from poseidon.vessel import VesselInfo, VesselReading


def test_vessel_reading_to_dict() -> None:
    now = datetime.now(UTC)
    reading = VesselReading("v1", {"speed_knots": 10.0}, now)
    data = reading.to_dict()
    assert data["vessel_id"] == "v1"
    assert data["timestamp"] == now.isoformat()


def test_vessel_info_defaults() -> None:
    vessel = VesselInfo(
        id="v1",
        location="POINT(0 0)",
        longitude=0,
        latitude=0,
        metadata={},
    )
    assert vessel.last_reading is None
    assert vessel.last_update is None
