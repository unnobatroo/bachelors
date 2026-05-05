# End-Term Assignment: Project Poseidon

*ELTE Python Course — Semester 2025/26/2 — End-Term Homework*

## Overview & Goal

Project Poseidon is an **enterprise-grade** distributed **Maritime Vessel Traffic Monitor**
backend for the North Sea and the Baltic. You will build a robust Web API using **FastAPI**
that acts as a central server for a fleet of 15 AIS-instrumented commercial vessels operating
out of ports in **the Netherlands, Germany, Poland, and Denmark**.

The application must validate and ingest telemetry from vessel transponders, persist both
real-time and historical readings, serve interactive geospatial dashboards, and provide
comprehensive temporal analysis capabilities over one full year of hourly data.

The system operates on a **Configuration-Driven Architecture** following **Clean
Architecture** principles, **Domain-Driven Design**, and **Dependency Injection** patterns.

The course content you will apply comes from **Lectures 1–10**: Python basics, control
flow, strings, collections, functions, error handling, files & JSON, OOP, testing with
pytest, **FastAPI + Pydantic v2 (Lecture 8)**, **modern pandas (Lecture 9)**, and
**Plotly maps & performance (Lecture 10)**.

## Learning Objectives

By completing this project, you will demonstrate mastery of:

- **Web API Development** — Typed REST endpoints with FastAPI + Pydantic v2 (Lecture 8)
- **Dependency Injection** — `Annotated[T, Depends()]` providers & `dependency_overrides`
  in tests (Lecture 8)
- **Modern FastAPI lifespan** — startup/shutdown via `@asynccontextmanager`, no
  `@app.on_event` (Lecture 8)
- **Data Engineering** — WKT parsing with named-capture regex (two flavours: single-string
  and batch `str.extract`) (Lecture 3 + Lecture 9)
- **Modern pandas workflows** — `dtype_backend="pyarrow"`, method chaining,
  `.agg(name=(col, fn))` named aggregation (Lecture 9)
- **Clean Architecture** — DTOs ≠ Domain models, layered design (Lectures 6, 8)
- **Interactive geospatial visualization** — `px.scatter_map` (modern MapLibre API),
  range sliders, 100 %-stacked bars (Lecture 10)
- **Automated testing** — pytest + FastAPI `TestClient` with `dependency_overrides`
  (Lectures 7, 8)
- **Error handling & files** — custom exceptions, JSON persistence (Lectures 5, 7)
- **Domain-Driven Design** — framework-independent business logic (Lectures 6, 8)

## Domain Primer (Read This First)

Each **vessel** is a commercial ship that broadcasts AIS-like telemetry to a shore-side
station once per hour. Every telemetry report contains four numeric **readings**:

| Reading          | Units        | Meaning                                                   |
| ---------------- | ------------ | --------------------------------------------------------- |
| `speed_knots`    | knots        | Speed over ground                                         |
| `draft_m`        | metres       | Vessel draft (how deep below the waterline it sits)       |
| `heading_deg`    | degrees      | Compass heading, 0 – 360                                  |
| `fuel_rate_lph`  | litres / hr  | Instantaneous fuel consumption                            |

The **primary risk indicator** for the map and distribution chart is `speed_knots`:
high speed in restricted waters (TSS zones, port approaches) is the most immediate risk.
The threshold bands are **configurable** and read from `server_config.json`.

## Detailed Requirements

### 1. Configuration & Startup

**Requirement**: The server must be configuration-driven with no hard-coded values. It must
initialise by reading configuration files and loading historical data.

#### Configuration Files

1. **System Config** (`config/server_config.json`):
   ```json
   {
     "storage_file": "data/readings.json",
     "historical_data_file": "data/historical_readings.csv",
     "host": "0.0.0.0",
     "port": 8000,
     "thresholds": {
       "speed_safe": 10.0,
       "speed_moderate": 15.0,
       "speed_danger": 22.0,
       "draft_safe": 6.0,
       "draft_moderate": 9.0,
       "draft_danger": 12.0
     },
     "map_config": {
       "default_zoom": 5,
       "map_style": "carto-positron",
       "center": { "lat": 54.5, "lon": 8.5 }
     }
   }
   ```

2. **Vessel Whitelist** (`config/vessels.json`):
   - 15 authorised vessels home-ported across Netherlands, Germany, Poland, Denmark
   - Each entry contains: `id`, `location` (WKT `POINT` of **home port**), `metadata`
     (flag_state, home_port, vessel_class, commissioned_date)
   - Example:
     ```json
     {
       "id": "vessel_rotterdam_001",
       "location": "POINT(4.4792 51.9225)",
       "metadata": {
         "flag_state": "Netherlands",
         "home_port": "Rotterdam",
         "vessel_class": "container",
         "commissioned_date": "2021-04-12"
       }
     }
     ```

#### Startup Logic

1. **Parse Configuration**
   - Load `vessels.json`
   - Parse each vessel's `location` **using a compiled `re` pattern with named capture
     groups** — **no `.split()` / `.replace()` / slicing**.
   - Extract `lon` and `lat` using named groups.
   - Validate coordinate ranges (-180 … 180 for lon, -90 … 90 for lat).
   - Log and discard vessels with invalid WKT — **do not crash** the server.

2. **Load Historical Data**
   - Read `data/historical_readings.csv` with `pd.read_csv(..., dtype_backend="pyarrow")`.
   - One year of hourly telemetry: `15 vessels × 365 × 24 = 131 400` rows.
   - Clean the frame with **vectorised pandas operations only** (no per-row Python loops):
     - Drop rows where `vessel_id` or `timestamp` is missing.
     - Remove rows with negative `speed_knots`, `draft_m`, or `fuel_rate_lph`.
     - Remove rows with `speed_knots > 50` (AIS glitch — commercial ships don't exceed this).
     - Clamp `heading_deg` to `[0, 360)` or drop invalid rows.
     - Parse `timestamp` with `pd.to_datetime(..., utc=True)`.
   - Log statistics (rows loaded, rows dropped, percentage cleaned).

3. **Initialise Services**
   - Set up dependency-injection providers.
   - Initialise the service layer (business logic).
   - Prepare visualisation components.
   - Hydrate state from `data/readings.json` if it exists.

### 2. API Endpoints

The application must expose **7 HTTP endpoints**:

#### A. `POST /report` — Telemetry Ingestion

**Input** (JSON): `vessel_id` + `readings` dictionary.

**Logic**
- **Security**: verify `vessel_id` against the whitelist → `403 Forbidden` if unauthorised.
- **Validation**: DTO-level validation via Pydantic (see §3); deeper batch validation is
  done with pandas before visualisation, **not** at ingestion.
- **Storage**: create a domain model (`VesselReading`) storing raw readings **without
  validation inside the domain model**.
- **Persistence**: append to the JSON storage file immediately.
- **State update**: update the vessel's `last_reading` and `last_update`.

**Success response** (`IngestResponse` DTO): `status`, `message`, `vessel_id`,
`timestamp` (ISO-8601, UTC).

#### B. `GET /map` — Real-Time Dashboard

**Output**: HTML page with an interactive Plotly map.

**Requirements**
- Combine vessel home-port locations with their latest reading.
- Use **`px.scatter_map`** (the modern MapLibre API taught in Lecture 10 —
  *not* the deprecated `scatter_mapbox`).
- Use `map_style` from config (default `carto-positron`).
- Colour markers by the `speed_knots` threshold from config:
  - 🟢 **Safe** — `speed_knots ≤ speed_safe`
  - 🟡 **Moderate** — `speed_safe < speed_knots ≤ speed_moderate`
  - 🟠 **Unsafe** — `speed_moderate < speed_knots ≤ speed_danger`
  - 🔴 **Danger** — `speed_knots > speed_danger`
  - ⚫ **No data** — vessel has not reported yet
- Hover tooltip must show: `vessel_class`, `home_port`, `flag_state`,
  `speed_knots`, `heading_deg`, `draft_m`.
- Return HTML via `fig.to_html(include_plotlyjs="cdn", full_html=True)`.

#### C. `GET /status` — System Health

**Output** (JSON): `status` (`"healthy"` / `"degraded"`), `uptime_seconds`,
`active_vessels` (count with ≥ 1 reading), `total_reports`, `last_update` (ISO-8601).

#### D. `GET /history/{vessel_id}` — Time-Series Chart

Path parameter validated with **`Annotated[str, Path(min_length=1)]`**.

**Output**: HTML page with a Plotly time-series plot showing the four readings
(`speed_knots`, `draft_m`, `heading_deg`, `fuel_rate_lph`) as separate traces.

**Requirements**
- Data source: one year of hourly rows for the requested vessel (≈ 8 760 points).
- **Range slider** enabled: `fig.update_xaxes(rangeslider_visible=True)`.
- Unified hover mode; each trace on a separate y-axis or clearly distinguished scale.
- Return `404 Not Found` if the vessel doesn't exist **or** has no historical data.

#### E. `GET /distribution/{year}/{month}` — Flag-State Distribution

Path parameters validated with `Annotated[int, Path(ge=1, le=12)]` for `month`.

**Output**: HTML page with a **100 %-stacked bar chart** — one bar per `flag_state`.

**Requirements**
- Filter the historical frame to the requested month using `.dt.year` / `.dt.month`.
- Categorise each row by the `speed_knots` threshold (Safe / Moderate / Unsafe / Danger).
- Group by `flag_state` (which is carried from the vessel metadata via a join/merge).
- Build a 100 %-stacked bar chart (`barmode="stack"`, y-axis `[0, 100]`).
- Show percentages inside the bars; colours must match the map colours.
- Return `400 Bad Request` for invalid month values (0, 13 …).
- Return `404 Not Found` if no data exists for the requested period.

#### F. `GET /` — Welcome Page

HTML page with navigation links to all endpoints.

#### G. `GET /docs` — API Documentation

Auto-generated Swagger UI (FastAPI default).

### 3. Data Models & Architecture

#### Domain Models (`src/poseidon/vessel.py`)

Plain Python classes — **no Pydantic**, **no validation** inside:

```python
class VesselReading:
    """Raw telemetry report. Stored as-is, validated only by pandas later."""
    def __init__(
        self,
        vessel_id: str,
        readings: dict[str, float],
        timestamp: datetime,
    ) -> None:
        self.vessel_id = vessel_id
        self.readings = readings
        self.timestamp = timestamp

    def to_dict(self) -> dict[str, object]:
        ...


class VesselInfo:
    """Static vessel metadata + last known state."""
    id: str
    location: str            # original WKT POINT string
    longitude: float
    latitude: float
    metadata: dict[str, str]
    last_reading: dict[str, float] | None
    last_update: datetime | None
```

#### DTOs (`src/poseidon/models.py`) — Pydantic v2

```python
from pydantic import BaseModel, ConfigDict, Field


class IngestRequest(BaseModel):
    model_config = ConfigDict(strict=True, extra="forbid")
    vessel_id: str = Field(..., min_length=1)
    readings: dict[str, float]


class IngestResponse(BaseModel):
    status: str
    message: str
    vessel_id: str
    timestamp: datetime


class StatusResponse(BaseModel):
    status: str
    uptime_seconds: float
    active_vessels: int
    total_reports: int
    last_update: datetime | None


class VesselInfoRead(BaseModel):
    """Example use of from_attributes to read directly from VesselInfo objects."""
    model_config = ConfigDict(from_attributes=True)
    id: str
    longitude: float
    latitude: float
    metadata: dict[str, str]
```

**Key Principle**: validation happens **at the API boundary** (DTOs) and in the pandas
cleaning step — **not** inside domain models. This mirrors the pattern from Lecture 8.

#### Data Cleaning (`src/poseidon/data_cleaning.py`)

`DataCleaner` class with static methods using **vectorised pandas**:

- `validate_report(readings: dict) -> tuple[bool, list[str]]` — shallow check on an incoming
  dict before it becomes a domain model.
- `clean_historical_batch(df: pd.DataFrame) -> pd.DataFrame` — vectorised cleaning of the
  full historical frame (drop NaNs, filter negatives, parse timestamps).
- `parse_wkt_column(series: pd.Series) -> pd.DataFrame` — batch WKT parsing with
  `.str.extract(pattern)` and named groups (Lecture 9 pattern).
- `categorise_by_speed(df, thresholds) -> pd.DataFrame` — adds a `risk_band` column.
- `distribution_by_flag(df, thresholds, year, month) -> pd.DataFrame` — aggregation for
  endpoint E, returning a tidy table ready for Plotly.

### 4. Service Layer Architecture

Strict layer separation:

**Web Layer** (`src/poseidon/main.py`)
- FastAPI routes **only**; no business logic.
- Translates service-layer exceptions into HTTP exceptions.
- Uses `Annotated[T, Depends(get_X)]` for every injected service.
- Uses `@asynccontextmanager`-based `lifespan` (Lecture 8) — **not** `@app.on_event`.

**Service Layer** (`src/poseidon/vessel_manager.py`)
- Business logic: authorisation, ingestion, retrieval.
- State management: counters, timestamps, last-seen table.
- Custom exceptions: `UnauthorizedVesselError`, `InvalidReportError`.
- Must be usable **outside** FastAPI (the test suite exercises this).

**Data Layer** (`src/poseidon/persistence.py`)
- File I/O for JSON (real-time) and CSV (historical).
- Robust error handling — corrupt/missing files must not crash the server.

**Dependency Injection** (`src/poseidon/dependencies.py`)

```python
from typing import Annotated
from fastapi import Depends

_vessel_manager: VesselManager | None = None


def get_vessel_manager() -> VesselManager:
    if _vessel_manager is None:
        msg = "VesselManager not initialised"
        raise RuntimeError(msg)
    return _vessel_manager


VesselManagerDep = Annotated[VesselManager, Depends(get_vessel_manager)]
```

In routes:
```python
@app.post("/report", response_model=IngestResponse)
def report(request: IngestRequest, manager: VesselManagerDep) -> IngestResponse:
    ...
```

### 5. WKT Parsing — Two Flavours Required

This assignment requires both flavours of WKT parsing **so you can demonstrate both
Lecture 3 regex skills and Lecture 9 pandas skills**.

**Flavour 1 — Single-string parsing** (used for the vessel whitelist):

```python
WKT_POINT_PATTERN = re.compile(
    r"POINT\s*\(\s*(?P<lon>-?\d+\.?\d*)\s+(?P<lat>-?\d+\.?\d*)\s*\)",
    re.IGNORECASE,
)

def parse_point(wkt: str) -> tuple[float, float]:
    match = WKT_POINT_PATTERN.fullmatch(wkt.strip())
    if match is None:
        raise ValueError(f"Invalid WKT: {wkt!r}")
    return float(match["lon"]), float(match["lat"])
```

**Flavour 2 — Vectorised parsing** (used if the historical CSV carries positions):

```python
wkt_pattern = r"POINT\s*\((?P<lon>-?\d+\.?\d*)\s+(?P<lat>-?\d+\.?\d*)\)"
coords = (
    df["position"]
    .str.extract(wkt_pattern)
    .astype("float32[pyarrow]")
)
df = pd.concat([df, coords], axis=1)
```

**Must**
- Use **named groups** (`?P<lon>`, `?P<lat>`) in both flavours.
- Handle whitespace variations.
- Be case-insensitive (flavour 1).
- Validate coordinate ranges.

**Must Not**
- Use `.split()`, `.replace()`, or string slicing.
- Hard-code coordinate positions.

### 6. Temporal Visualisations

`src/poseidon/temporal_visualization.py`.

#### Time-Series with Range Slider

```python
def create_time_series(df: pd.DataFrame, vessel_id: str) -> go.Figure:
    fig = make_subplots(...)  # or px.line with facet_row
    ...
    fig.update_xaxes(rangeslider_visible=True)  # Lecture 10 idiom
    fig.update_layout(hovermode="x unified")
    return fig
```

- 4 traces: `speed_knots`, `draft_m`, `heading_deg`, `fuel_rate_lph`.
- Different colours per trace.
- ~8 760 hourly points per vessel.

#### Distribution Chart

```python
def create_distribution_chart(
    df: pd.DataFrame,
    thresholds: dict[str, float],
    year: int,
    month: int,
) -> go.Figure:
    ...
```

- Categorise rows into `Safe / Moderate / Unsafe / Danger`.
- Group by `flag_state`.
- 100 %-stacked bar (`barmode="stack"`).
- Colours must match the map colours (consistency rule from Lecture 10).
- Show percentages inside bars.

### 7. Data Persistence

**Real-time storage** — JSON file (`config["storage_file"]`):
- Append on every `POST /report`.
- Hydrate on startup.
- Survive corrupt / missing files gracefully (log + start fresh).

**Historical storage** — CSV file (`config["historical_data_file"]`):
- Loaded once at startup.
- Cleaned with vectorised pandas.
- Stored **raw**; cleaning happens only at load time and before visualisation.

## Technical Implementation Guidelines

### 1. Project & Dependency Management — use `uv`

This semester we use **`uv`** (Lectures 8–12). Create the project with:

```bash
uv init poseidon-aqms
cd poseidon-aqms
uv add "fastapi>=0.135" "uvicorn[standard]>=0.42" "pydantic>=2.12" \
       "pandas>=2.2" "pyarrow>=18.0" "plotly>=6.0"
uv add --dev "pytest>=9.0" "pytest-cov>=6.0" "httpx>=0.28" "ruff>=0.8" "mypy>=1.13"
uv sync
uv run pytest
uv run uvicorn poseidon.main:app --reload
```

Commit `pyproject.toml` **and** `uv.lock`; do not commit `.venv/`.

### 2. Python Version

**Target Python 3.13+** (matching Lecture 8 / 9 / 10 examples).

### 3. FastAPI Patterns (Lecture 8)

**Lifespan handler** (modern pattern — replaces `@app.on_event("startup")`):

```python
from contextlib import asynccontextmanager
from fastapi import FastAPI


@asynccontextmanager
async def lifespan(app: FastAPI):
    # startup
    initialise_services(config_path, vessels_path)
    yield
    # shutdown
    reset_services()


app = FastAPI(title="Project Poseidon", version="1.0.0", lifespan=lifespan)
```

**Validated path/query parameters** (lecture 8 "Annotated + Path(...)" slide):

```python
from typing import Annotated
from fastapi import Path, Query

@app.get("/distribution/{year}/{month}")
def distribution(
    year: Annotated[int, Path(ge=2020, le=2100)],
    month: Annotated[int, Path(ge=1, le=12)],
    manager: VesselManagerDep,
) -> HTMLResponse:
    ...
```

**Synchronous routes** — keep them simple. FastAPI runs sync routes in a threadpool.

### 4. Pydantic v2 Patterns (Lecture 8)

- `model_config = ConfigDict(strict=True, extra="forbid")` on `IngestRequest` to reject
  unknown fields and mis-typed payloads.
- `model_config = ConfigDict(from_attributes=True)` on read DTOs that pull data from a
  domain model.
- `Field(..., min_length=1, gt=0.0, …)` for constraint declaration.
- Always call `model.model_dump(mode="json")` before persisting to JSON (handles `datetime`).

### 5. Modern pandas (Lecture 9)

- Read CSVs with `pd.read_csv(..., dtype_backend="pyarrow")` — faster and uses less memory.
- Use **method chaining**:
  ```python
  summary = (
      df.loc[df["speed_knots"].between(0, 50)]
      .assign(risk=lambda d: pd.cut(d["speed_knots"], bins=..., labels=...))
      .groupby("flag_state", observed=True)
      .agg(
          reports=("vessel_id", "size"),
          avg_speed=("speed_knots", "mean"),
      )
      .sort_values("reports", ascending=False)
  )
  ```
- Use `.agg(named_col=("src_col", "func"))` — named aggregation is clearer than passing
  dicts.
- Parse datetimes **once**, at load time: `pd.to_datetime(df["timestamp"], utc=True)`.
- Prefer `.dt.year` / `.dt.month` over string slicing.

### 6. Plotly (Lecture 10)

- Use `px.scatter_map` (MapLibre) — **not** `px.scatter_mapbox` (deprecated in Plotly 6).
- `map_style="carto-positron"` is the default "quiet" basemap used in lectures.
- `fig.update_xaxes(rangeslider_visible=True)` for time-series zooming.
- Keep colours consistent across map + distribution chart (same 4-tier palette).
- Aggregate **upstream** (with pandas) before handing data to Plotly — do not ask the
  browser to render 131 400 points.

### 7. Testing with `TestClient` + `dependency_overrides` (Lecture 8)

This is the **required** testing pattern:

```python
import pytest
from fastapi.testclient import TestClient

from poseidon.main import app, get_vessel_manager
from poseidon.vessel_manager import VesselManager


@pytest.fixture
def client(tmp_path):
    test_manager = VesselManager.for_tests(tmp_path)
    app.dependency_overrides[get_vessel_manager] = lambda: test_manager
    with TestClient(app) as test_client:
        yield test_client
    app.dependency_overrides.clear()


def test_status_endpoint(client):
    response = client.get("/status")
    assert response.status_code == 200
    assert response.json()["status"] in {"healthy", "degraded"}


def test_unauthorised_vessel_is_rejected(client):
    response = client.post(
        "/report",
        json={"vessel_id": "vessel_not_in_whitelist", "readings": {"speed_knots": 5.0}},
    )
    assert response.status_code == 403
```

**Requirements**
- Use `dependency_overrides` — **do not** mutate module globals directly.
- Use `tmp_path` (pytest fixture) for any on-disk JSON persistence in tests.
- Wrap `TestClient(app)` in a `with` block so `lifespan` startup/shutdown run around the
  test session.

### 8. Code Quality

- **Type hints on every function** (`disallow_untyped_defs = true` in mypy config).
- Run `uv run ruff check src tests`, `uv run ruff format src tests`,
  `uv run mypy src`.
- Target **≥ 80 % test coverage**.

## Sample Usage Scenarios

### Scenario 1 — Real-Time Monitoring

1. Server starts, parses WKT for 15 vessels, loads ≈ 131 400 historical rows.
2. Cleaning drops ~0.1 % as AIS glitches (report exact numbers in the log).
3. `POST /report` with `{"vessel_id": "vessel_rotterdam_001", "readings": {"speed_knots": 18.4, "draft_m": 11.2, "heading_deg": 274, "fuel_rate_lph": 310}}`.
4. Response `200 OK`, data persisted, vessel state updated.
5. `GET /map` — Rotterdam shows an **orange** marker (18.4 kn falls in the "Unsafe" band).

### Scenario 2 — Historical Analysis

1. `GET /history/vessel_hamburg_001`.
2. HTML page returns a 4-trace time-series:
   - Speed spikes on open-sea legs, drops in port.
   - Draft correlates with cargo loading — stable for most of the year with two refits.
   - Heading looks like a noisy sawtooth (round-trip routes).
   - Fuel rate tracks speed.
3. Range slider lets you zoom into a specific voyage.

### Scenario 3 — Fleet Distribution

1. `GET /distribution/2025/2` (Feb).
   - Netherlands: 60 % Safe (port calls), 25 % Moderate, 15 % Unsafe.
   - Germany:    50 % Safe, 30 % Moderate, 20 % Unsafe.
   - Poland:     40 % Safe, 35 % Moderate, 20 % Unsafe, 5 % Danger.
   - Denmark:    55 % Safe, 30 % Moderate, 15 % Unsafe.
2. `GET /distribution/2025/7` (Jul) — summer season, more transits, shift towards Moderate.

### Scenario 4 — Unauthorised Vessel

1. `POST /report` with `{"vessel_id": "vessel_unknown_999", ...}`.
2. Response `403 Forbidden` with clear error message.
3. Warning logged, server keeps running.

### Scenario 5 — Invalid Input

1. `POST /report` with `{"vessel_id": "", "readings": {"speed_knots": "fast"}}`.
2. Response `422 Unprocessable Entity` — Pydantic catches both violations.

## Scoring System (100 Points Total)

| Category              | Task                                                                  | Points |
| --------------------- | --------------------------------------------------------------------- | -----: |
| Architecture & Config | JSON config loading, vessel whitelist, DI providers, `lifespan`        |     15 |
| Data Engineering      | Regex WKT parsing (both flavours), pandas cleaning with vector ops     |     15 |
| API Implementation    | 7 endpoints with `Annotated` path/query validation                     |     20 |
| Visualisation         | `px.scatter_map` + range-slider time-series + stacked-bar distribution |     15 |
| Robustness            | Error handling, graceful degradation, custom exceptions → HTTP         |      5 |
| Clean Architecture    | DTO ≠ Domain separation, service layer, presentation-independent       |      5 |
| Code Quality          | Type hints, `ruff` clean, `mypy` clean, modern idioms                  |      5 |
| Testing               | pytest + TestClient + `dependency_overrides`, ≥ 80 % coverage          |     20 |
| **TOTAL**             |                                                                       | **100** |

## Required Technologies

| Area        | Package                 | Minimum version |
| ----------- | ----------------------- | --------------- |
| Runtime     | Python                  | **3.13**        |
| Web         | `fastapi`               | 0.135           |
| Web         | `uvicorn[standard]`     | 0.42            |
| Validation  | `pydantic`              | 2.12            |
| Data        | `pandas`                | 2.2             |
| Data        | `pyarrow`               | 18.0            |
| Visual      | `plotly`                | 6.0             |
| Testing     | `pytest`                | 9.0             |
| Testing     | `pytest-cov`            | 6.0             |
| Testing     | `httpx`                 | 0.28            |
| Quality     | `ruff`                  | 0.8             |
| Quality     | `mypy`                  | 1.13            |

## Submission Requirements

### What to Submit

1. **Complete source code** in `src/poseidon/`.
2. **Configuration files** in `config/` (both JSON files included with the starter).
3. **Test suite** in `tests/` — pytest, TestClient, `dependency_overrides`.
4. **`README.md`** with:
   - Installation (`uv sync`).
   - Usage examples (`curl` commands for each endpoint).
   - API documentation summary.
   - Architecture description.
5. **`pyproject.toml`** and **`uv.lock`**.
6. **`run.sh`** (or equivalent) that starts the server via `uv run`.
7. A short note describing how the **historical CSV** was generated (you may re-use the
   provided `data_generator.py` pattern, or write your own — either is fine, but the
   one-year / hourly / 15-vessel shape is required).

### Project Layout (Recommended)

```
poseidon/
├── config/
│   ├── server_config.json
│   └── vessels.json
├── data/
│   ├── historical_readings.csv        # generated, ≥ 131 400 rows
│   └── readings.json                  # created on first ingest
├── src/poseidon/
│   ├── __init__.py
│   ├── main.py                        # FastAPI app, lifespan, routes
│   ├── dependencies.py                # DI providers
│   ├── vessel_manager.py              # service layer
│   ├── vessel.py                      # domain models (no Pydantic)
│   ├── models.py                      # Pydantic DTOs
│   ├── data_cleaning.py               # pandas / WKT batch parsing
│   ├── config_loader.py               # WKT regex parsing (single)
│   ├── persistence.py                 # JSON + CSV I/O
│   ├── visualization.py               # px.scatter_map
│   └── temporal_visualization.py      # time-series + stacked bar
├── tests/
│   ├── conftest.py                    # fixtures using dependency_overrides
│   ├── test_config_loader.py
│   ├── test_models.py
│   ├── test_vessel.py
│   ├── test_data_cleaning.py
│   ├── test_persistence.py
│   ├── test_vessel_manager.py
│   ├── test_api_endpoints.py
│   ├── test_history.py
│   └── test_distribution.py
├── pyproject.toml
├── uv.lock
├── run.sh
└── README.md
```

### Verification Checklist

Before submitting, verify:

- ✅ `uv sync` installs dependencies cleanly.
- ✅ `uv run uvicorn poseidon.main:app` starts without errors.
- ✅ All 7 endpoints respond correctly.
- ✅ `uv run pytest` passes with ≥ 80 % coverage.
- ✅ `uv run ruff check src tests` is clean.
- ✅ `uv run mypy src` is clean.
- ✅ Map centres on the southern North Sea / Baltic; all 15 vessels visible.
- ✅ Range slider works on `GET /history/{vessel_id}`.
- ✅ `GET /distribution/{year}/{month}` shows all four flag states.
- ✅ No hard-coded paths, thresholds, or vessel IDs anywhere in `src/`.

## Best Practices & Tips

### 1. Start with Architecture

1. Domain models (`vessel.py`) — two tiny classes.
2. DTOs (`models.py`) — Pydantic with `ConfigDict(strict=True, extra="forbid")`.
3. Service layer (`vessel_manager.py`) — unit-testable without FastAPI.
4. DI providers (`dependencies.py`) — singletons + `Annotated` aliases.
5. Web layer (`main.py`) — keep it thin.

### 2. Use pandas Effectively (Lecture 9)

```python
# Good: vectorised, chainable, named aggregation.
summary = (
    df.loc[df["speed_knots"].between(0, 50)]
      .groupby("flag_state", observed=True)
      .agg(
          reports=("vessel_id", "size"),
          avg_speed=("speed_knots", "mean"),
      )
      .sort_values("avg_speed", ascending=False)
)

# Bad: Python-level loop over rows.
for _, row in df.iterrows():
    if row["speed_knots"] < 0: ...
```

### 3. Test As You Build (Lecture 7)

- Test **domain models first** — they have no dependencies.
- Test the **service layer** next — it's pure Python, no FastAPI.
- Test the **web layer last** — with `TestClient` + `dependency_overrides`.

### 4. Let FastAPI Validate for You (Lecture 8)

- Never do manual `int(request.path_param)` — use `Annotated[int, Path(...)]`.
- Never do manual `assert isinstance(body, dict)` — declare a Pydantic model.
- Let FastAPI translate bad input into `422` automatically.

## Common Pitfalls to Avoid

- ❌ **Don't** validate inside domain models — use DTOs + pandas.
- ❌ **Don't** use `.split()` or slicing to parse WKT — must use regex.
- ❌ **Don't** mix DTOs and domain models in the same file.
- ❌ **Don't** use `@app.on_event("startup")` — it is deprecated. Use `lifespan`.
- ❌ **Don't** use `px.scatter_mapbox` — it is deprecated in Plotly 6. Use `px.scatter_map`.
- ❌ **Don't** use `pip install` — use `uv add` / `uv sync` (see the Python Environments
  rule for this course).
- ❌ **Don't** mutate module globals in tests — use `app.dependency_overrides`.
- ❌ **Don't** hard-code thresholds — load them from config.
- ❌ **Don't** skip type hints — they are required for full credit.

## Success Criteria Summary

- ✅ **Configuration-driven** — all behaviour from JSON files.
- ✅ **WKT regex** — named groups, single-string **and** vectorised parse.
- ✅ **7 endpoints** — real-time + temporal + utility.
- ✅ **Modern pandas** — pyarrow backend, named agg, chaining.
- ✅ **Clean architecture** — DTOs ≠ domain.
- ✅ **Modern DI** — `Annotated[T, Depends()]` + `dependency_overrides`.
- ✅ **Modern FastAPI** — `lifespan`, sync routes, Pydantic v2.
- ✅ **Modern Plotly** — `px.scatter_map`, range slider, consistent colours.
- ✅ **Testing** — `TestClient` with `dependency_overrides`, ≥ 80 % coverage.
- ✅ **Type hints** — 100 % coverage, `mypy` clean.
- ✅ **`uv`-managed** — `pyproject.toml` + `uv.lock` committed, no stray `requirements.txt`.

---

*Good luck, and fair winds.*
