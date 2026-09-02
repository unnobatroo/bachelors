from __future__ import annotations

import json
from pathlib import Path

from poseidon.config_loader import load_vessels, parse_point


def test_parse_point_valid() -> None:
    lon, lat = parse_point("POINT(4.4792 51.9225)")
    assert lon == 4.4792
    assert lat == 51.9225


def test_load_vessels_skips_invalid_wkt(tmp_path: Path) -> None:
    payload = [
        {"id": "ok", "location": "POINT(1 2)", "metadata": {"flag_state": "X"}},
        {"id": "bad", "location": "POINT(x y)", "metadata": {"flag_state": "Y"}},
    ]
    path = tmp_path / "vessels.json"
    path.write_text(json.dumps(payload), encoding="utf-8")

    vessels = load_vessels(path)
    assert "ok" in vessels
    assert "bad" not in vessels
