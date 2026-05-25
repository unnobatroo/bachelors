from __future__ import annotations

from dataclasses import dataclass
from datetime import datetime


class VesselReading:
    """Raw telemetry report. Stored as-is, validated only by pandas later."""

    def __init__(
        self,
        vessel_id: str,
        readings: dict[str, float],
        timestamp: datetime,
    ) -> None:
        self.vessel_id = vessel_id
        self.readings = readings
        self.timestamp = timestamp

    def to_dict(self) -> dict[str, object]:
        # convert domain object to json-serialisable mapping
        return {
            "vessel_id": self.vessel_id,
            "readings": self.readings,
            "timestamp": self.timestamp.isoformat(),
        }


@dataclass
class VesselInfo:
    """Static vessel metadata + last known state."""

    id: str
    location: str
    longitude: float
    latitude: float
    metadata: dict[str, str]
    last_reading: dict[str, float] | None = None
    last_update: datetime | None = None
    # note: last_reading and last_update are updated by the manager on ingest
