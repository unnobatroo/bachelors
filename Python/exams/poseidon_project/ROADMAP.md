## Section 1: Modern Python Tooling & Package Management (`uv`)

### 1. `uv` (by Astral)

* **What it does:** `uv` is a blazing-fast, single-binary Python package installer and resolver written in Rust. It
  serves as a drop-in replacement for `pip`, `pip-tools`, `virtualenv`, and `poetry`.
* **Why it is needed:** Traditional Python dependency management using `pip install` and `requirements.txt` lacks
  deterministic guarantees. It leads to the "it works on my machine" problem because it does not lock sub-dependencies
  transitively. Tools like Poetry can be slow. `uv` handles dependency resolution up to 10–100x faster than `pip` and
  natively generates robust, cross-platform lockfiles (`uv.lock`).
* **How it works:** * `uv init` generates a modern `pyproject.toml` configuration file.
* When you execute `uv add "fastapi>=0.135"`, `uv` reads the target package requirements, resolves conflicts down to the
  exact transitively nested dependency version, downloads the wheels concurrently using an optimized global cache, and
  locks those states inside `uv.lock`.
* `uv sync` perfectly mirrors that exact snapshot into a localized isolated environment (`.venv`).

### 2. `pyproject.toml` and `uv.lock`

* **What it does:** `pyproject.toml` is the PEP 518 standard file containing human-readable project declarations,
  metadata, tool configurations (like `ruff` and `mypy`), and broad version constraints. `uv.lock` is a
  machine-generated cryptographic lock file pinning exact dependency graphs.
* **Why it is needed:** It separates human intent ("I need FastAPI version 0.135 or higher") from runtime reality ("The
  app is running with exactly FastAPI 0.135.1 and Pydantic 2.12.0"). This prevents unexpected package updates from
  breaking your production server down the line.
* **How it works:** You modify `pyproject.toml` either directly or via `uv add` commands. The `uv` CLI processes changes
  and writes the exact cryptographic hashes and versions to `uv.lock`, ensuring that cloning the repository and running
  `uv sync` reproduces an identical execution environment on any machine.

## Section 2: Clean Architecture & Domain-Driven Design (DDD)

### 1. Architectural Layers (Web vs. Service vs. Data vs. Domain)

* **What it does:** This pattern structures software into concentric rings of responsibility. Outer layers (Web, Data)
  depend strictly on inner layers (Service, Domain). The core business logic (Domain) has zero dependencies on
  frameworks, databases, or web servers.
* **Why it is needed:** Mixing HTTP endpoint routing with Pandas queries and file writing creates brittle, untestable
  code. If you decide to swap FastAPI for another framework or switch from local JSON files to an SQL database, tightly
  coupled code requires a complete rewrite. Clean Architecture isolates these layers so changes in the infrastructure do
  not impact business logic.
* **How it works:**
* **Web Layer (`main.py`):** Handles network concerns. It captures incoming HTTP requests, maps JSON into Pydantic
  validation schemas, coordinates with the service layer, and shapes outgoing HTTP responses.
* **Service Layer (`vessel_manager.py`):** Coordinates business use cases (e.g., verifying if a vessel is allowed to
  report telemetry, logging events, raising domain exceptions).
* **Data/Persistence Layer (`persistence.py`):** Executes low-level infrastructure I/O, like loading CSV files or
  appending data to disk.
* **Domain Layer (`vessel.py`):** Houses raw data structures representing core business entities (`VesselReading`,
  `VesselInfo`) that remain completely independent of frameworks.

### 2. Domain Models vs. Data Transfer Objects (DTOs)

* **What it does:** Domain models represent your application's internal business entities, whereas DTOs (Pydantic
  models) represent data structures flowing across the API boundary.
* **Why it is needed:** API data contracts must change independently of business entities. For example, your public
  endpoint may accept an input field named `readings`, but internally your core analytics engine processes that data as
  an un-nested, flattened array or structured matrix. If your domain objects inherit from framework-specific models like
  Pydantic's `BaseModel`, you pollute your business logic with external constraints and runtime performance overhead.
* **How it works:** Incoming payloads pass through a Pydantic DTO (`IngestRequest`) at the web layer. Once parsed and
  validated, the web layer unpacks this schema and instantiates a plain Python class domain entity (`VesselReading`) to
  pass deep into the service layer. When serving data, the process reverses: domain fields populate a serialization DTO
  via `ConfigDict(from_attributes=True)`.

## Section 3: Configuration-Driven Startup & Advanced Regex (WKT Parsing)

### 1. Configuration-Driven Architecture

* **What it does:** It externalizes environment variables, execution flags, validation limits, and file paths into
  static files (`server_config.json`, `vessels.json`) loaded into memory at startup.
* **Why it is needed:** Hardcoding values like `speed_safe = 10.0` or local file paths across multiple files creates a
  maintenance headache. If a maritime authority modifies a harbor's speed limit, modifying code and redeploying
  introduces risk. A configuration-driven setup lets you update a single JSON file without altering code.
* **How it works:** During the server startup sequence, a configuration utility reads the JSON configurations, parses
  them into structured application states, and injects those threshold parameters directly into classes like the
  `VesselManager` or the data-cleaning pipeline.

### 2. Regular Expressions (`re` Module) with Named Capture Groups

* **What it does:** It uses pattern matching to extract text segments from unstructured or semi-structured data strings
  based on explicit token definitions.
* **Why it is needed:** Well-Known Text (WKT) representations like `POINT(4.4792 51.9225)` are prone to formatting
  variations, such as irregular spacing or varying case sensitivity (e.g., `point ( 4.4792   51.9225)`). Brittle
  approaches like string slicing or calling `.split(" ")` fail when spacing changes. Regular expressions securely
  isolate components based on structural grammar rules. Using named groups (`?P<name>`) makes your parsing
  self-documenting and resilient.
* **How it works:**
* **Single-String Parsing (Flavour 1):** You compile a pattern using
  `re.compile(r"POINT\s*\(\s*(?P<lon>-?\d+\.?\d*)\s+(?P<lat>-?\d+\.?\d*)\s*\)", re.IGNORECASE)`. This pattern looks for
  the word "POINT", handles optional whitespace (`\s*`), opens a parenthesis, captures a coordinate into a group named
  `lon`, identifies separating spaces (`\s+`), captures another coordinate into a group named `lat`, and matches the
  trailing parenthesis. Calling `.fullmatch()` evaluates the target string and returns a match object where you extract
  values directly by group names: `match["lon"]` and `match["lat"]`.
* **Vectorized Batch Parsing (Flavour 2):** When applied across a full Pandas series dataframe column, calling
  `.str.extract(pattern)` applies the matching compiled expression to every row simultaneously at the C-level,
  automatically splitting out an entire dataframe with columns named `lon` and `lat`.

## Section 4: Data Engineering Pipeline (Modern Pandas & PyArrow)

### 1. Apache Arrow Backend (`dtype_backend="pyarrow"`)

* **What it does:** It tells Pandas to utilize Apache Arrow as its underlying memory manager and datatype layout engine,
  replacing legacy NumPy types.
* **Why it is needed:** Project Poseidon requires processing historical batches comprising 131,400 entries. Standard
  NumPy backends process text data as expensive generic Python objects, which consume significant memory and slow down
  operations. Furthermore, NumPy does not support a native integer missing-value type (forcing casting to floating-point
  `NaN`). PyArrow organizes data in an un-nested, columnar, continuous memory format, drastically accelerating execution
  speeds and natively supporting missing values across all datatypes.
* **How it works:** Initializing data loading via `pd.read_csv("...", dtype_backend="pyarrow")` instructs Pandas to
  instantiate contiguous data columns managed directly in C++ via Apache Arrow memory arrays.

### 2. Method Chaining

* **What it does:** Method chaining links multiple data frame manipulation steps sequentially into a single expression
  statement using trailing dot notation wrapped in parentheses.
* **Why it is needed:** Writing sequential processing routines using intermediate variable assignments (e.g.,
  `df1 = df.dropna()`, `df2 = df1[df1['speed'] > 0]`) creates unreadable code and pollutes memory with temporary
  variable handles. Method chaining builds a highly readable data-cleaning pipeline that reads like a structured
  processing recipe.
* **How it works:**

```python
df = (
    pd.read_csv(historical_file, dtype_backend="pyarrow")
    .dropna(subset=["vessel_id", "timestamp"])
    .loc[lambda d: (d["speed_knots"] >= 0) & (d["speed_knots"] <= 50)]
    .assign(timestamp=lambda d: pd.to_datetime(d["timestamp"], utc=True))
)

```

Each function takes an immutable snapshot of the dataframe from the preceding operation, applies its operational vector
transform, and immediately hands it off to the next method in the chain.

### 3. Named Aggregations (`.agg(name=(col, fn))`)

* **What it does:** It provides a precise, explicit syntax for mapping group-by aggregation operations directly into
  human-readable, flat data structures in a single step.
* **Why it is needed:** Legacy Pandas aggregations require passing nested dictionaries or lists (e.g.,
  `.agg({"speed_knots": ["mean", "max"]})`). Doing this creates a **MultiIndex column header** (hierarchical,
  multi-level columns). MultiIndex headers break your code downstream because they are highly annoying to query, break
  standard data contracts, and force you to write ugly boilerplate like
  `df.columns = ['_'.join(col) for col in df.columns]` just to flatten them. Named aggregations bypass this completely,
  keeping your column headers flat, clean, and self-explanatory from the jump.
* **How it works:** You pass named keyword arguments inside the `.agg()` method. Each argument accepts a tuple where the
  first element is the target raw column name, and the second element is the aggregation function (like `"size"`,
  `"mean"`, `"max"`, or `"sum"`).

#### Real-World Example:

If your raw dataset contains these entries:

| vessel_id | flag_state  | speed_knots |
|-----------|-------------|-------------|
| vessel_01 | Netherlands | 12.0        |
| vessel_02 | Netherlands | 10.0        |
| vessel_03 | Germany     | 20.0        |

Executing this exact named aggregation pipeline:

```python
summary = df.groupby("flag_state").agg(
    total_reports=("vessel_id", "size"),
    average_velocity=("speed_knots", "mean")
)

```

Directly processes the records and transforms them into this clean, single-level dataframe structure, ready to be
immediately serialized to JSON or fed straight into a Plotly chart:

| flag_state (Index) | total_reports | average_velocity |
|--------------------|---------------|------------------|
| **Germany**        | 1             | 20.0             |
| **Netherlands**    | 2             | 11.0             |

## Section 5: Web API Layer & Dependency Injection (FastAPI & Lifespan)

### 1. Asynchronous Lifespan Handler (`@asynccontextmanager`)

* **What it does:** It defines a unified context manager hook managing the complete setup phase before the server
  accepts traffic, and the complete cleanup teardown phase when the process stops.
* **Why it is needed:** The old approach of using `@app.on_event("startup")` and `@app.on_event("shutdown")` is
  deprecated. If an application startup fails halfway through loading configuration profiles, the system can hang in an
  undefined state. The modern `lifespan` pattern provides a clean context-manager interface, ensuring resources like
  historical datasets are loaded completely before requests arrive, and files are safely closed upon shutdown.
* **How it works:**

```python
@asynccontextmanager
async def lifespan(app: FastAPI):
    # Startup Execution Path
    # Load configs, read CSVs, populate whitelists
    yield
    # Shutdown Execution Path
    # Clear connections, flush remaining memory frames to disk

```

The FastAPI engine boots up, fully executes everything preceding the `yield` statement, unlocks request processing
traffic, and executes everything following the `yield` statement once a termination signal occurs.

### 2. Dependency Injection via `Annotated[T, Depends()]`

* **What it does:** It decouples routes from the instantiation logic of required services. Endpoints explicitly state
  *what* dependency signature types they expect, and FastAPI dynamically resolves and injects them at runtime.
* **Why it is needed:** Hardcoding global manager instances inside your endpoints breaks test isolation. If an endpoint
  directly references a global variable, you cannot easily isolate that endpoint during testing with a mocked
  configuration. Dependency injection lets you swap out service components effortlessly.
* **How it works:** You define a lookup provider function `get_vessel_manager()`. In your endpoints, you declare type
  signatures via `manager: Annotated[VesselManager, Depends(get_vessel_manager)]`. When an HTTP request hits that route,
  FastAPI invokes your provider, resolves the object instance, and injects it directly into the execution scope of the
  endpoint function.

### 3. Synchronous Routes Execution Model

* **What it does:** Declaring paths as standard synchronous functions (`def route():`) rather than asynchronous ones (
  `async def route():`) instructs FastAPI to handle the execution path within an internal background threadpool.
* **Why it is needed:** Heavy CPU-bound data operations, like running Pandas cleaning pipelines or rendering large
  Plotly HTML figures, block the main event loop if executed inside an `async def` function. This halts the server and
  prevents it from handling concurrent requests.
* **How it works:** When a request hits a synchronous route, FastAPI hands the execution over to an internal thread pool
  managed by `anyio`. This leaves the main asynchronous loop free to continuously process other lightweight IO tasks.

## Section 6: Boundary Data Validation (Pydantic v2)

### 1. Type Enforcement via `ConfigDict(strict=True, extra="forbid")`

* **What it does:** `strict=True` forces Pydantic to validate data types exactly without implicit conversions (e.g.,
  rejecting an incoming string `"12.5"` when a float is expected). `extra="forbid"` throws an error if an incoming JSON
  payload includes fields not explicitly declared in the schema.
* **Why it is needed:** Loose validations can allow corrupt data to slip into your system. For instance, if an AIS
  transponder sends an unvalidated dictionary or typos sneak into a JSON payload, standard parsing rules might ignore
  them or quietly cast malformed types. Enforcing strict boundary controls ensures that data strictly conforms to your
  API contract before reaching your business logic.
* **How it works:** When an incoming byte payload hits `POST /report`, Pydantic passes it through your `IngestRequest`
  validation schema. If any undeclared JSON attributes are found or an integer is passed where a string belongs,
  Pydantic immediately halts execution and raises a detailed validation error, which FastAPI translates into an HTTP
  `422 Unprocessable Entity` response.

### 2. DTO Serialization via `ConfigDict(from_attributes=True)`

* **What it does:** It enables Pydantic models to read data directly from generic object attributes (like standard
  Python classes), rather than requiring standard dictionary structures.
* **Why it is needed:** Clean architecture requires keeping domain entities as lightweight, native Python classes.
  However, FastAPI requires returning serialized JSON string outputs. `from_attributes=True` bridges this gap, allowing
  Pydantic models to easily ingest domain instances and extract fields for serialization.
* **How it works:** By setting `model_config = ConfigDict(from_attributes=True)` inside a read schema, you can call
  `VesselInfoRead.model_validate(domain_vessel_instance)`. Pydantic automatically navigates object pointers, reads
  values via standard `getattr(obj, key)` lookups, and safely constructs your serialization schema.

## Section 7: Interactive Analytical Visualizations (Plotly 6)

### 1. Modern MapLibre Engine (`px.scatter_map`)

* **What it does:** It plots interactive geospatial coordinates onto localized web maps using modern MapLibre engine
  components.
* **Why it is needed:** Legacy Plotly methods like `px.scatter_mapbox` are fully deprecated as of Plotly 6.0. Continuing
  to use them causes console errors and risks breaking when dependencies update. `px.scatter_map` utilizes modern
  open-source rendering protocols, ensuring smooth interactions when mapping vessels across maritime areas.
* **How it works:** You provide a dataframe containing extracted coordinates along with your style configurations.
  Plotly converts this data into map coordinates, renders the base layer using the configured style (e.g.,
  `carto-positron`), and dynamically applies marker colors according to your speed thresholds.

### 2. Time-Series Timeline Adjustments (`rangeslider_visible=True`)

* **What it does:** It adds an interactive timeline slider component below a chart's x-axis, allowing users to
  dynamically zoom into specific time ranges.
* **Why it is needed:** Plotting a full year of hourly vessel readings generates approximately 8,760 data points per
  trace. Displaying this volume of data on a standard line chart makes it difficult to read and analyze specific
  voyages. A range slider lets users view broad annual trends while providing the ability to zoom in on specific hourly
  events.
* **How it works:** Calling `fig.update_xaxes(rangeslider_visible=True)` instructs Plotly to append a mini-timeline
  underneath the main chart window. Dragging the slider bounds dynamically recalibrates the visible window coordinates
  on the fly.

## Section 8: Isolated Automated Testing Suite (`pytest` & TestClient)

### 1. `pytest` Test Architecture

* **What it does:** `pytest` is a robust testing framework that uses standard `assert` statements, automated test
  discovery, and modular fixtures to validate code correctness.
* **Why it is needed:** Manually running scripts or test commands to verify your app is inefficient and error-prone.
  Automated test suites let you verify that modifications to one component—such as updating your data-cleaning
  pipeline—don't break other parts of your application like your API endpoints.
* **How it works:** Running `uv run pytest` scans files prefixed with `test_*.py`, executes each test function in
  isolation, and surfaces detailed reports on any failed asset evaluations or unhandled exceptions.

### 2. Mocking Dependency Injections via `app.dependency_overrides`

* **What it does:** It allows you to replace a production dependency provider with a test double or mock implementation
  during testing.
* **Why it is needed:** You must never run unit tests against actual production storage databases or configurations, as
  this creates fragile dependencies and mutates your application state. Overriding dependencies lets you redirect file
  operations to a temporary directory (`tmp_path`) for isolated verification.
* **How it works:**

```python
@pytest.fixture
def client(tmp_path):
    test_manager = VesselManager.for_tests(tmp_path)
    app.dependency_overrides[get_vessel_manager] = lambda: test_manager
    with TestClient(app) as test_client:
        yield test_client
    app.dependency_overrides.clear()

```

This replaces your production service layer instance with a test-isolated double, routes all incoming test traffic
through it, and clears the overrides after the test finishes to maintain test isolation.

## Tech Stack Reference Table

| Tool                   | Role                                                                      | Key command                                   |
|------------------------|---------------------------------------------------------------------------|-----------------------------------------------|
| **`uv`**               | High-performance dependency resolution and runtime lockfile tracking.     | `uv init` / `uv add` / `uv sync`              |
| **`re` module**        | Resilient WKT parsing using named capture groups to avoid string slicing. | `re.compile(r"(?P<lon>...)(?P<lat>...)")`     |
| **`pandas` (PyArrow)** | Vectorized data cleaning and aggregation using an Apache Arrow backend.   | `pd.read_csv(..., dtype_backend="pyarrow")`   |
| **`fastapi`**          | Web API routing, lifecycle management, and dependency injection.          | `FastAPI(lifespan=...)` / `Depends()`         |
| **`pydantic`**         | Strict input validation and serialization at the API boundary.            | `ConfigDict(strict=True, extra="forbid")`     |
| **`plotly`**           | Rendering interactive dashboards and time-series charts.                  | `px.scatter_map` / `rangeslider_visible=True` |
| **`pytest`**           | Running automated unit tests with dependency overrides.                   | `app.dependency_overrides`                    |