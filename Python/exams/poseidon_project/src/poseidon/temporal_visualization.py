from __future__ import annotations

import pandas as pd
import plotly.graph_objects as go
from plotly.subplots import make_subplots

COLOR_MAP = {
    "Safe": "#2E8B57",
    "Moderate": "#F2C14E",
    "Unsafe": "#F07F13",
    "Danger": "#CC2F2F",
}


def create_time_series(df: pd.DataFrame, vessel_id: str) -> go.Figure:
    # create a 4-row subplot: one trace per reading
    fig = make_subplots(
        rows=4,
        cols=1,
        shared_xaxes=True,
        vertical_spacing=0.03,
        subplot_titles=("Speed (kn)", "Draft (m)", "Heading (deg)", "Fuel rate (lph)"),
    )

    fig.add_trace(
        go.Scatter(
            x=df["timestamp"],
            y=df["speed_knots"],
            mode="lines",
            name="speed_knots",
            line={"color": "#1f77b4"},
        ),
        row=1,
        col=1,
    )
    fig.add_trace(
        go.Scatter(
            x=df["timestamp"],
            y=df["draft_m"],
            mode="lines",
            name="draft_m",
            line={"color": "#ff7f0e"},
        ),
        row=2,
        col=1,
    )
    fig.add_trace(
        go.Scatter(
            x=df["timestamp"],
            y=df["heading_deg"],
            mode="lines",
            name="heading_deg",
            line={"color": "#2ca02c"},
        ),
        row=3,
        col=1,
    )
    fig.add_trace(
        go.Scatter(
            x=df["timestamp"],
            y=df["fuel_rate_lph"],
            mode="lines",
            name="fuel_rate_lph",
            line={"color": "#d62728"},
        ),
        row=4,
        col=1,
    )

    fig.update_xaxes(rangeslider_visible=True, row=4, col=1)
    fig.update_layout(
        title=f"Historical telemetry for {vessel_id}",
        height=900,
        hovermode="x unified",
        legend={"orientation": "h", "yanchor": "bottom", "y": 1.02, "xanchor": "right", "x": 1},
    )

    return fig


def create_distribution_chart(df: pd.DataFrame, year: int, month: int) -> go.Figure:
    # pivot aggregated percentages into columns per risk band
    pivot = (
        df.pivot(index="flag_state", columns="risk_band", values="percentage")
        .fillna(0)
        .reindex(columns=["Safe", "Moderate", "Unsafe", "Danger"], fill_value=0)
    )

    fig = go.Figure()
    for band in ["Safe", "Moderate", "Unsafe", "Danger"]:
        fig.add_trace(
            go.Bar(
                x=pivot.index,
                y=pivot[band],
                name=band,
                marker_color=COLOR_MAP[band],
                text=[f"{value:.1f}%" if value > 0 else "" for value in pivot[band]],
                textposition="inside",
            )
        )

    fig.update_layout(
        barmode="stack",
        yaxis={"title": "Percentage", "range": [0, 100]},
        xaxis={"title": "Flag State"},
        title=f"Risk distribution by flag state ({year}-{month:02d})",
        height=650,
    )

    return fig
