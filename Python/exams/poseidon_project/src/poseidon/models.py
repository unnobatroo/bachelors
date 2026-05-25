from __future__ import annotations

from datetime import datetime

from pydantic import BaseModel, ConfigDict, Field


class IngestRequest(BaseModel):
    model_config = ConfigDict(strict=True, extra="forbid")

    # request DTO for telemetry ingestion validated by pydantic
    vessel_id: str = Field(..., min_length=1)
    readings: dict[str, float]


class IngestResponse(BaseModel):
    # response DTO returned after successful ingestion
    status: str
    message: str
    vessel_id: str
    timestamp: datetime


class StatusResponse(BaseModel):
    # DTO for system status endpoint
    status: str
    uptime_seconds: float
    active_vessels: int
    total_reports: int
    last_update: datetime | None


class VesselInfoRead(BaseModel):
    model_config = ConfigDict(from_attributes=True)
    # DTO used to serialize VesselInfo domain objects
    id: str
    longitude: float
    latitude: float
    metadata: dict[str, str]
