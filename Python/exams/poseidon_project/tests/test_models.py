from __future__ import annotations

from datetime import UTC, datetime

import pytest
from pydantic import ValidationError

from poseidon.models import IngestRequest, IngestResponse


def test_ingest_request_strict_rejects_extra() -> None:
    with pytest.raises(ValidationError):
        IngestRequest(vessel_id="v1", readings={"speed_knots": 1.0}, extra_field=1)


def test_ingest_response_model_dump_json() -> None:
    response = IngestResponse(
        status="ok",
        message="done",
        vessel_id="v1",
        timestamp=datetime.now(UTC),
    )
    data = response.model_dump(mode="json")
    assert "timestamp" in data
