from __future__ import annotations


def test_status_endpoint(client) -> None:
    response = client.get("/status")
    assert response.status_code == 200
    assert response.json()["status"] in {"healthy", "degraded"}


def test_unauthorised_vessel_rejected(client) -> None:
    response = client.post(
        "/report",
        json={
            "vessel_id": "vessel_not_in_whitelist",
            "readings": {
                "speed_knots": 5.0,
                "draft_m": 1.0,
                "heading_deg": 1.0,
                "fuel_rate_lph": 1.0,
            },
        },
    )
    assert response.status_code == 403


def test_report_success(client) -> None:
    response = client.post(
        "/report",
        json={
            "vessel_id": "vessel_test_001",
            "readings": {
                "speed_knots": 12.0,
                "draft_m": 5.5,
                "heading_deg": 90.0,
                "fuel_rate_lph": 150.0,
            },
        },
    )
    assert response.status_code == 200
    assert response.json()["status"] == "ok"
