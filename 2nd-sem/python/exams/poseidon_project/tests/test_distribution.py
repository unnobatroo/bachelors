from __future__ import annotations


def test_distribution_ok(client) -> None:
    response = client.get("/distribution/2025/2")
    assert response.status_code == 200
    assert "plotly" in response.text.lower()


def test_distribution_invalid_month(client) -> None:
    response = client.get("/distribution/2025/13")
    assert response.status_code == 400
