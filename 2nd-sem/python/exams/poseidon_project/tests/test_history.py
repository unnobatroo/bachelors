from __future__ import annotations


def test_history_existing_vessel(client) -> None:
    response = client.get("/history/vessel_test_001")
    assert response.status_code == 200
    assert "plotly" in response.text.lower()


def test_history_unknown_vessel(client) -> None:
    response = client.get("/history/unknown")
    assert response.status_code == 404
