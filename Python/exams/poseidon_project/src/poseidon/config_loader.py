from __future__ import annotations

import json
import logging
import re
from pathlib import Path
from typing import Any

from poseidon.vessel import VesselInfo

logger = logging.getLogger(__name__)

WKT_POINT_PATTERN = re.compile(
    r"POINT\s*\(\s*(?P<lon>-?\d+\.?\d*)\s+(?P<lat>-?\d+\.?\d*)\s*\)",
    re.IGNORECASE,
)


def parse_point(wkt: str) -> tuple[float, float]:
    # parse a single WKT POINT string and validate lon/lat ranges
    match = WKT_POINT_PATTERN.fullmatch(wkt.strip())
    if match is None:
        raise ValueError(f"Invalid WKT: {wkt!r}")

    lon = float(match["lon"])
    lat = float(match["lat"])

    if not -180 <= lon <= 180:
        raise ValueError(f"Longitude out of range: {lon}")
    if not -90 <= lat <= 90:
        raise ValueError(f"Latitude out of range: {lat}")

    return lon, lat


def load_json(path: Path) -> Any:
    # load a json file from disk
    with path.open("r", encoding="utf-8") as file:
        return json.load(file)


def load_server_config(path: Path) -> dict[str, Any]:
    # read server configuration (must be an object)
    data = load_json(path)
    if not isinstance(data, dict):
        raise ValueError("Server config must be a JSON object")
    return data


def load_vessels(path: Path) -> dict[str, VesselInfo]:
    # load and validate vessel whitelist; parse wkt locations
    vessels_raw = load_json(path)
    if not isinstance(vessels_raw, list):
        raise ValueError("Vessels config must be a JSON list")

    result: dict[str, VesselInfo] = {}

    for item in vessels_raw:
        if not isinstance(item, dict):
            logger.warning("Skipping non-object vessel entry: %r", item)
            continue

        vessel_id = str(item.get("id", "")).strip()
        wkt = str(item.get("location", "")).strip()
        metadata_raw = item.get("metadata", {})

        if not vessel_id:
            logger.warning("Skipping vessel with empty id: %r", item)
            continue
        if not isinstance(metadata_raw, dict):
            logger.warning("Skipping vessel %s due to invalid metadata", vessel_id)
            continue

        metadata: dict[str, str] = {k: str(v) for k, v in metadata_raw.items()}

        try:
            lon, lat = parse_point(wkt)
        except ValueError as exc:
            logger.warning("Skipping vessel %s due to invalid WKT: %s", vessel_id, exc)
            continue

        result[vessel_id] = VesselInfo(
            id=vessel_id,
            location=wkt,
            longitude=lon,
            latitude=lat,
            metadata=metadata,
        )

    return result
