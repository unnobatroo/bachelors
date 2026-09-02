from __future__ import annotations

import pandas as pd
from dataclasses import dataclass
from datetime import UTC, datetime
from pathlib import Path
from typing import Any

from poseidon.data_cleaning import DataCleaner
from poseidon.persistence import PersistenceService
from poseidon.vessel import VesselInfo, VesselReading


class UnauthorizedVesselError(Exception):
    # raised when an incoming report is from an unknown vessel
    pass


class InvalidReportError(Exception):
    # raised when a report fails shallow validation
    pass


@dataclass
class ManagerStats:
    rows_loaded: int
    rows_after_cleaning: int


class VesselManager:
    def __init__(
            self,
            vessels: dict[str, VesselInfo],
            config: dict[str, Any],
            persistence: PersistenceService,
    ) -> None:
        # initialise manager state, load historical data and hydrate realtime storage
        self.vessels = vessels
        self.config = config
        self.persistence = persistence
        self.start_time = datetime.now(UTC)
        self.total_reports = 0
        self.last_update: datetime | None = None

        historical_raw = self.persistence.load_historical_data()
        # clean historical data and keep stats for logging
        self.stats = self._clean_historical_with_stats(historical_raw)
        self.historical_df = self._attach_vessel_metadata(self.cleaned_historical)
        self.historical_latest = self._build_latest_historical_lookup(self.historical_df)

        self._hydrate_from_realtime_storage()

    @classmethod
    def for_tests(cls, tmp_path: Path) -> VesselManager:
        vessels = {
            "vessel_test_001": VesselInfo(
                id="vessel_test_001",
                location="POINT(4.0 52.0)",
                longitude=4.0,
                latitude=52.0,
                metadata={
                    "flag_state": "Netherlands",
                    "home_port": "TestPort",
                    "vessel_class": "container",
                    "commissioned_date": "2020-01-01",
                },
            )
        }

        config: dict[str, Any] = {
            "thresholds": {
                "speed_safe": 10.0,
                "speed_moderate": 15.0,
                "speed_danger": 22.0,
            },
            "map_config": {
                "default_zoom": 5,
                "map_style": "carto-positron",
                "center": {"lat": 54.5, "lon": 8.5},
            },
        }

        persistence = PersistenceService(
            storage_file=tmp_path / "readings.json",
            historical_file=tmp_path / "historical_readings.csv",
        )

        return cls(vessels=vessels, config=config, persistence=persistence)

    def _clean_historical_with_stats(self, df: pd.DataFrame) -> ManagerStats:
        """runs cleaner and returns counts for logging"""
        loaded = len(df)
        self.cleaned_historical = DataCleaner.clean_historical_batch(df)
        cleaned = len(self.cleaned_historical)
        return ManagerStats(rows_loaded=loaded, rows_after_cleaning=cleaned)

    def _attach_vessel_metadata(self, df: pd.DataFrame) -> pd.DataFrame:
        if df.empty:
            return df.copy()

        vessel_meta_rows = [
            {
                "vessel_id": vessel.id,
                "flag_state": vessel.metadata.get("flag_state", "Unknown"),
                "home_port": vessel.metadata.get("home_port", "Unknown"),
                "vessel_class": vessel.metadata.get("vessel_class", "Unknown"),
            }
            for vessel in self.vessels.values()
        ]
        meta_df = pd.DataFrame(vessel_meta_rows)

        # merge vessel static metadata into the historical frame
        merged = df.merge(meta_df, on="vessel_id", how="left")
        merged["flag_state"] = merged["flag_state"].fillna("Unknown")
        return merged

    def _build_latest_historical_lookup(self, df: pd.DataFrame) -> dict[str, dict[str, Any]]:
        if df.empty:
            return {}

        # extract last known historical reading per vessel
        latest_rows = df.sort_values("timestamp").drop_duplicates("vessel_id", keep="last")
        return {
            row["vessel_id"]: {
                "speed_knots": float(row["speed_knots"]),
                "draft_m": float(row["draft_m"]),
                "heading_deg": float(row["heading_deg"]),
                "fuel_rate_lph": float(row["fuel_rate_lph"]),
            }
            for _, row in latest_rows.iterrows()
        }

    def _hydrate_from_realtime_storage(self) -> None:
        """applies persisted realtime readings to vessel state"""
        for reading in self.persistence.load_realtime_readings():
            vessel = self.vessels.get(reading.vessel_id)
            if vessel is None:
                continue
            vessel.last_reading = reading.readings
            vessel.last_update = reading.timestamp
            self.total_reports += 1
            if self.last_update is None or reading.timestamp > self.last_update:
                self.last_update = reading.timestamp

    def ingest_report(self, vessel_id: str, readings: dict[str, float]) -> VesselReading:
        """validates and persists an incoming telemetry report"""
        if vessel_id not in self.vessels:
            raise UnauthorizedVesselError(f"Vessel {vessel_id} is not authorised")

        valid, errors = DataCleaner.validate_report(readings)
        if not valid:
            raise InvalidReportError("; ".join(errors))

        timestamp = datetime.now(UTC)
        report = VesselReading(vessel_id=vessel_id, readings=readings, timestamp=timestamp)
        self.persistence.append_reading(report)

        # update in-memory vessel state
        vessel = self.vessels[vessel_id]
        vessel.last_reading = readings
        vessel.last_update = timestamp

        self.total_reports += 1
        self.last_update = timestamp

        return report

    def status(self) -> dict[str, Any]:
        """returns a dict suitable for the status endpoint"""
        uptime_seconds = (datetime.now(UTC) - self.start_time).total_seconds()
        active_vessels = sum(
            1 for vessel in self.vessels.values() if vessel.last_reading is not None
        )
        status = "healthy" if active_vessels > 0 else "degraded"

        return {
            "status": status,
            "uptime_seconds": uptime_seconds,
            "active_vessels": active_vessels,
            "total_reports": self.total_reports,
            "last_update": self.last_update,
        }

    def map_dataframe(self) -> pd.DataFrame:
        rows: list[dict[str, Any]] = []
        thresholds = self.config["thresholds"]

        for vessel in self.vessels.values():
            display_reading = vessel.last_reading or self.historical_latest.get(vessel.id)
            speed = None if display_reading is None else display_reading.get("speed_knots")
            if speed is None:
                risk_band = "No data"
            elif speed <= thresholds["speed_safe"]:
                risk_band = "Safe"
            elif speed <= thresholds["speed_moderate"]:
                risk_band = "Moderate"
            elif speed <= thresholds["speed_danger"]:
                risk_band = "Unsafe"
            else:
                risk_band = "Danger"

            rows.append(
                {
                    "vessel_id": vessel.id,
                    "lat": vessel.latitude,
                    "lon": vessel.longitude,
                    "risk_band": risk_band,
                    "speed_knots": None
                    if display_reading is None
                    else display_reading.get("speed_knots"),
                    "draft_m": None if display_reading is None else display_reading.get("draft_m"),
                    "heading_deg": None
                    if display_reading is None
                    else display_reading.get("heading_deg"),
                    "fuel_rate_lph": None
                    if display_reading is None
                    else display_reading.get("fuel_rate_lph"),
                    "flag_state": vessel.metadata.get("flag_state", "Unknown"),
                    "home_port": vessel.metadata.get("home_port", "Unknown"),
                    "vessel_class": vessel.metadata.get("vessel_class", "Unknown"),
                }
            )

        return pd.DataFrame(rows)

    def vessel_history(self, vessel_id: str) -> pd.DataFrame:
        if vessel_id not in self.vessels:
            raise KeyError("Vessel not found")
        vessel_df = self.historical_df.loc[self.historical_df["vessel_id"] == vessel_id].copy()
        if vessel_df.empty:
            raise KeyError("No historical data for vessel")
        return vessel_df.sort_values("timestamp")

    def distribution(self, year: int, month: int) -> pd.DataFrame:
        return DataCleaner.distribution_by_flag(
            df=self.historical_df,
            thresholds=self.config["thresholds"],
            year=year,
            month=month,
        )
