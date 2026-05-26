from __future__ import annotations

import pandas as pd
import plotly.express as px
import plotly.graph_objects as go
from typing import Any


def create_map_figure(
        df: pd.DataFrame,
        map_config: dict[str, Any],
) -> go.Figure:
    # colour palette used for risk bands
    color_map = {
        "Safe": "#2E8B57",
        "Moderate": "#F2C14E",
        "Unsafe": "#F07F13",
        "Danger": "#CC2F2F",
        "No data": "#5A5A5A",
    }

    # build a scatter_map figure using plotly express (maplibre)
    fig = px.scatter_map(
        df,
        lat="lat",
        lon="lon",
        color="risk_band",
        color_discrete_map=color_map,
        hover_name="vessel_id",
        hover_data={
            "risk_band": True,
            "vessel_class": True,
            "home_port": True,
            "flag_state": True,
            "speed_knots": True,
            "heading_deg": True,
            "draft_m": True,
            "lat": False,
            "lon": False,
        },
        zoom=map_config.get("default_zoom", 5),
        map_style=map_config.get("map_style", "carto-positron"),
        center=map_config.get("center", {"lat": 54.5, "lon": 8.5}),
        height=700,
    )

    fig.update_traces(
        hovertemplate=(
            "<b>%{hovertext}</b><br>"
            "Risk band: %{customdata[0]}<br>"
            "Class: %{customdata[1]}<br>"
            "Home port: %{customdata[2]}<br>"
            "Flag state: %{customdata[3]}<br>"
            "Speed: %{customdata[4]} kn<br>"
            "Heading: %{customdata[5]} deg<br>"
            "Draft: %{customdata[6]} m<extra></extra>"
        )
    )

    fig.update_layout(
        margin={"l": 0, "r": 0, "t": 50, "b": 0}, title="Project Poseidon: Fleet Risk Map"
    )

    return fig
