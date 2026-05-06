from pydantic import BaseModel, ConfigDict

class TaskCreate(BaseModel):
    description: str


class TaskRead(BaseModel):
    model_config = ConfigDict(from_attributes=True)

    id: int
    description: str
    completed: bool
