# script to generate one-year hourly historical csv for vessels
from __future__ import annotations

import csv
import json
import sys
from datetime import datetime, timedelta, timezone
from pathlib import Path

ROOT = Path(__file__).resolve().parent
VESSELS_PATH = ROOT / "config" / "vessels.json"
OUTPUT_PATH = ROOT / "data" / "historical_readings.csv"


def build_value(base: float, vessel_idx: int, hour: int, factor: float) -> float:
    wave = ((hour % 24) - 12) / 12
    seasonal = ((hour // (24 * 30)) % 12) / 12
    return round(base + vessel_idx * factor + wave * factor * 4 + seasonal * factor * 2, 3)


def main() -> None:
    if not VESSELS_PATH.exists():
        sys.exit(f"vessels file not found: {VESSELS_PATH}")

    vessels = json.loads(VESSELS_PATH.read_text(encoding="utf-8"))
    if not isinstance(vessels, list):
        sys.exit(f"invalid vessels.json: expected a list, got {type(vessels).__name__}")

    vessel_ids = [v.get("id") for v in vessels if isinstance(v, dict) and "id" in v]
    if not vessel_ids:
        sys.exit("no vessel ids found in vessels.json")
    if len(vessel_ids) != 15:
        print(f"warning: expected 15 vessels, found {len(vessel_ids)}")

    start = datetime(2025, 1, 1, tzinfo=timezone.utc)
    hours = 365 * 24

    OUTPUT_PATH.parent.mkdir(parents=True, exist_ok=True)

    written = 0
    with OUTPUT_PATH.open("w", newline="", encoding="utf-8") as file:
        writer = csv.writer(file)
        writer.writerow(
            [
                "vessel_id",
                "timestamp",
                "speed_knots",
                "draft_m",
                "heading_deg",
                "fuel_rate_lph",
            ]
        )

        for vessel_idx, vessel_id in enumerate(vessel_ids):
            for hour in range(hours):
                ts = start + timedelta(hours=hour)
                speed = max(0.2, min(49.8, build_value(9.0, vessel_idx, hour, 0.4)))
                draft = max(2.0, min(13.5, build_value(5.5, vessel_idx, hour, 0.07)))
                heading = (vessel_idx * 22 + hour * 7) % 360
                fuel = max(50.0, min(800.0, build_value(180.0, vessel_idx, hour, 2.8)))

                writer.writerow(
                    [
                        vessel_id,
                        ts.isoformat().replace("+00:00", "Z"),
                        speed,
                        draft,
                        heading,
                        fuel,
                    ]
                )
                written += 1

    print(f"Wrote {written} rows to {OUTPUT_PATH}")


if __name__ == "__main__":
    main()
