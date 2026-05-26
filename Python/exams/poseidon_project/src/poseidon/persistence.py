from __future__ import annotations

import json
import logging
import pandas as pd
from datetime import datetime
from pathlib import Path

from poseidon.vessel import VesselReading

logger = logging.getLogger(__name__)

HISTORICAL_COLUMNS = [
    "vessel_id",
    "timestamp",
    "speed_knots",
    "draft_m",
    "heading_deg",
    "fuel_rate_lph",
]


class PersistenceService:
    def __init__(self, storage_file: Path, historical_file: Path) -> None:
        # handles JSON realtime storage and CSV historical loading
        self.storage_file = storage_file
        self.historical_file = historical_file

    def append_reading(self, reading: VesselReading) -> None:
        # append a reading to the realtime json storage (safe recreate on corrupt)
        self.storage_file.parent.mkdir(parents=True, exist_ok=True)
        payload = reading.to_dict()

        records: list[dict[str, object]] = []
        if self.storage_file.exists():
            try:
                records_raw = json.loads(self.storage_file.read_text(encoding="utf-8"))
                if isinstance(records_raw, list):
                    records = [row for row in records_raw if isinstance(row, dict)]
            except (json.JSONDecodeError, OSError):
                logger.warning("Corrupt realtime storage detected. Recreating storage file.")

        records.append(payload)
        self.storage_file.write_text(json.dumps(records, indent=2), encoding="utf-8")

    def load_realtime_readings(self) -> list[VesselReading]:
        # load persisted realtime readings, gracefully handle corruption
        if not self.storage_file.exists():
            return []

        try:
            raw = json.loads(self.storage_file.read_text(encoding="utf-8"))
        except (json.JSONDecodeError, OSError):
            logger.warning("Could not load realtime readings. Starting with empty state.")
            return []

        if not isinstance(raw, list):
            return []

        readings: list[VesselReading] = []
        for item in raw:
            # validate each record shape before converting
            if not isinstance(item, dict):
                continue
            vessel_id = item.get("vessel_id")
            values = item.get("readings")
            timestamp_raw = item.get("timestamp")
            if not isinstance(vessel_id, str):
                continue
            if not isinstance(values, dict):
                continue
            if not isinstance(timestamp_raw, str):
                continue

            try:
                timestamp = datetime.fromisoformat(timestamp_raw)
            except ValueError:
                continue

            numeric_values = {
                key: float(value)
                for key, value in values.items()
                if isinstance(key, str) and isinstance(value, int | float)
            }
            readings.append(
                VesselReading(vessel_id=vessel_id, readings=numeric_values, timestamp=timestamp)
            )

        return readings

    def load_historical_data(self) -> pd.DataFrame:
        # read historical CSV into a dataframe using pyarrow backend
        if not self.historical_file.exists():
            logger.warning("Historical CSV not found: %s", self.historical_file)
            return pd.DataFrame(columns=HISTORICAL_COLUMNS)

        try:
            return pd.read_csv(self.historical_file, dtype_backend="pyarrow")
        except (OSError, ValueError, pd.errors.ParserError):
            logger.exception("Failed to load historical CSV. Using empty historical data.")
            return pd.DataFrame(columns=HISTORICAL_COLUMNS)
