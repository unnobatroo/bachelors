from fastapi import FastAPI
from pydantic import BaseModel

app = FastAPI()

class ItemCreate(BaseModel):
    name: str
    price: float


@app.get("/")
def read_root() -> dict[str, str]:
    return {"message": "Hello, World"}


@app.get("/items/{item_id}")
def read_item(item_id: int):
    return {"item_id": item_id}

@app.get('/items')
def list_items(skip: int = 0, limit: int = 10):
    return {'skip': skip, 'limit': limit}

@app.post("/items/")
def create_item(item: ItemCreate):
    return {"saved": item.model_dump()}