"""FastAPI patterns beyond Hello World: path, query, JSON body, and Annotated metadata.

Companion to lecture stack 11. Run from this directory::

    uv run uvicorn basics:app --reload

Then open http://127.0.0.1:8000/docs

Endpoints
---------

- ``GET /items`` — list with ``skip`` / ``limit`` query params (Annotated + Query).
- ``GET /items/{item_id}`` — single item by id (Annotated + Path).
- ``POST /items`` — create item (JSON body, 201 Created).
- ``PUT /items/{item_id}`` — update item; optional ``dry_run`` query mirrors the lecture slide.

Try validation (422): e.g. ``GET /items/not-a-number`` or POST JSON with wrong types.

Dependencies: same as the slides (``uv add fastapi`` with a venv / project that has FastAPI + Pydantic).
"""

from typing import Annotated

from fastapi import FastAPI, HTTPException, Path, Query
from pydantic import BaseModel

app = FastAPI(
    title='FastAPI basics demo',
    description='Path, query, body, and Annotated(Query/Path) — in-memory items API.',
)


class ItemCreate(BaseModel):
    name: str
    price: float


class ItemRead(BaseModel):
    id: int
    name: str
    price: float


_next_id: int = 1
_items: dict[int, ItemRead] = {}


@app.get('/items')
def list_items(
    skip: Annotated[
        int,
        Query(ge=0, description='Offset into the list'),
    ] = 0,
    limit: Annotated[
        int,
        Query(ge=1, le=100, description='Page size'),
    ] = 10,
) -> list[ItemRead]:
    ids = sorted(_items.keys())
    slice_ids = ids[skip : skip + limit]
    return [_items[i] for i in slice_ids]


@app.get('/items/{item_id}')
def read_item(
    item_id: Annotated[
        int,
        Path(ge=1, description='Primary key of the item'),
    ],
) -> ItemRead:
    if item_id not in _items:
        raise HTTPException(status_code=404, detail='Item not found')
    return _items[item_id]


@app.post('/items', status_code=201)
def create_item(item: ItemCreate) -> ItemRead:
    global _next_id
    record = ItemRead(id=_next_id, name=item.name, price=item.price)
    _items[_next_id] = record
    _next_id += 1
    return record


@app.put('/items/{item_id}')
def update_item(
    item_id: Annotated[
        int,
        Path(ge=1, description='Primary key of the item'),
    ],
    item: ItemCreate,
    dry_run: bool = False,
) -> dict:
    if item_id not in _items:
        raise HTTPException(status_code=404, detail='Item not found')
    if dry_run:
        return {'would_update': item_id, 'data': item.model_dump()}
    updated = ItemRead(id=item_id, name=item.name, price=item.price)
    _items[item_id] = updated
    return {'item_id': item_id, 'saved': updated.model_dump()}
