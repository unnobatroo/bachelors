from __future__ import annotations

import logging
from collections.abc import AsyncIterator
from contextlib import asynccontextmanager
from fastapi import FastAPI, HTTPException, Request
from fastapi import Path as PathParam
from fastapi.exceptions import RequestValidationError
from fastapi.responses import HTMLResponse, JSONResponse
from pathlib import Path
from typing import Annotated

from poseidon.config_loader import load_server_config, load_vessels
from poseidon.dependencies import (
    VesselManagerDep,
    get_vessel_manager,
    init_dependencies,
    reset_dependencies,
)
from poseidon.models import IngestRequest, IngestResponse, StatusResponse
from poseidon.persistence import PersistenceService
from poseidon.temporal_visualization import create_distribution_chart, create_time_series
from poseidon.vessel_manager import InvalidReportError, UnauthorizedVesselError, VesselManager
from poseidon.visualization import create_map_figure

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

ROOT_DIR = Path(__file__).resolve().parents[2]
CONFIG_PATH = ROOT_DIR / "config" / "server_config.json"
VESSELS_PATH = ROOT_DIR / "config" / "vessels.json"


@asynccontextmanager
async def lifespan(app: FastAPI) -> AsyncIterator[None]:
    # load configuration and vessel whitelist at startup
    config = load_server_config(CONFIG_PATH)
    vessels = load_vessels(VESSELS_PATH)

    storage_file = ROOT_DIR / config["storage_file"]
    historical_file = ROOT_DIR / config["historical_data_file"]

    persistence = PersistenceService(storage_file=storage_file, historical_file=historical_file)
    manager = VesselManager(vessels=vessels, config=config, persistence=persistence)
    init_dependencies(manager)

    dropped = manager.stats.rows_loaded - manager.stats.rows_after_cleaning
    ratio = 0.0 if manager.stats.rows_loaded == 0 else dropped / manager.stats.rows_loaded * 100
    logger.info(
        "Historical rows loaded=%s kept=%s dropped=%s (%.2f%%)",
        manager.stats.rows_loaded,
        manager.stats.rows_after_cleaning,
        dropped,
        ratio,
    )

    yield

    reset_dependencies()


app = FastAPI(title="Project Poseidon", version="1.0.0", lifespan=lifespan)


@app.exception_handler(RequestValidationError)
async def validation_exception_handler(
        request: Request, exc: RequestValidationError
) -> JSONResponse:
    # special-case validation errors for distribution month parameter
    if request.url.path.startswith("/distribution/"):
        for err in exc.errors():
            loc = err.get("loc", ())
            if "month" in loc:
                return JSONResponse(status_code=400, content={"detail": "Invalid month value"})
    return JSONResponse(status_code=422, content={"detail": exc.errors()})


@app.get("/", response_class=HTMLResponse)
def home() -> HTMLResponse:
    # simple welcome page with links to useful endpoints
    return HTMLResponse(
        """
        <html>
          <head><title>Project Poseidon</title></head>
          <body>
            <h1>Project Poseidon</h1>
            <p>Enterprise maritime vessel traffic monitor.</p>
            <ul>
              <li><a href=\"/docs\">API docs</a></li>
              <li><a href=\"/status\">System status</a></li>
              <li><a href=\"/map\">Fleet risk map</a></li>
              <li><a href=\"/history/vessel_rotterdam_001\">Sample vessel history</a></li>
              <li><a href=\"/distribution/2025/2\">Sample monthly distribution</a></li>
            </ul>
          </body>
        </html>
        """
    )


@app.post("/report", response_model=IngestResponse)
def report(request: IngestRequest, manager: VesselManagerDep) -> IngestResponse:
    try:
        reading = manager.ingest_report(request.vessel_id, request.readings)
    except UnauthorizedVesselError as exc:
        raise HTTPException(status_code=403, detail=str(exc)) from exc
    except InvalidReportError as exc:
        raise HTTPException(status_code=400, detail=str(exc)) from exc

    return IngestResponse(
        status="ok",
        message="Telemetry ingested",
        vessel_id=reading.vessel_id,
        timestamp=reading.timestamp,
    )


@app.get("/map", response_class=HTMLResponse)
def fleet_map(manager: VesselManagerDep) -> HTMLResponse:
    # build the dataframe and render a plotly map to html
    map_config = manager.config.get("map_config", {})
    df = manager.map_dataframe()
    fig = create_map_figure(df=df, map_config=map_config)
    return HTMLResponse(fig.to_html(include_plotlyjs="cdn", full_html=True))


@app.get("/status", response_model=StatusResponse)
def status(manager: VesselManagerDep) -> StatusResponse:
    return StatusResponse(**manager.status())


@app.get("/history/{vessel_id}", response_class=HTMLResponse)
def history(
        vessel_id: Annotated[str, PathParam(min_length=1)],
        manager: VesselManagerDep,
) -> HTMLResponse:
    try:
        df = manager.vessel_history(vessel_id)
    except KeyError as exc:
        raise HTTPException(status_code=404, detail=str(exc)) from exc

    # create time-series figure for vessel and return html
    fig = create_time_series(df=df, vessel_id=vessel_id)
    return HTMLResponse(fig.to_html(include_plotlyjs="cdn", full_html=True))


@app.get("/distribution/{year}/{month}", response_class=HTMLResponse)
def distribution(
        year: Annotated[int, PathParam(ge=2020, le=2100)],
        month: Annotated[int, PathParam(ge=1, le=12)],
        manager: VesselManagerDep,
) -> HTMLResponse:
    try:
        df = manager.distribution(year=year, month=month)
    except ValueError as exc:
        message = str(exc)
        if "Invalid month" in message:
            raise HTTPException(status_code=400, detail=message) from exc
        raise HTTPException(status_code=404, detail=message) from exc

    # build distribution chart and return as html
    fig = create_distribution_chart(df=df, year=year, month=month)
    return HTMLResponse(fig.to_html(include_plotlyjs="cdn", full_html=True))


__all__ = ["app", "get_vessel_manager"]
