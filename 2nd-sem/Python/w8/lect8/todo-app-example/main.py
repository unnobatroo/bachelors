"""To-do API with injected TaskManager.

Production uses a module-level singleton via get_manager. Tests override
get_manager so routes use a fresh TaskManager without touching that singleton.
"""

from typing import Annotated

from fastapi import Depends, FastAPI, HTTPException, status

from models import TaskCreate, TaskRead
from task_manager import TaskManager

app = FastAPI()
task_manager = TaskManager()


def get_manager() -> TaskManager:
    """Default provider: shared in-memory store for the live app process."""
    return task_manager


ManagerDep = Annotated[TaskManager, Depends(get_manager)]


@app.get("/")
def read_root():
    return {"message": "Welcome to the To-Do API"}


@app.post(
    "/tasks",
    response_model=TaskRead,
    status_code=status.HTTP_201_CREATED,
)
def create_task(task_in: TaskCreate, manager: ManagerDep):
    return manager.create_task(task_in)


@app.get("/tasks", response_model=list[TaskRead])
def get_all_tasks(manager: ManagerDep):
    return manager.get_all_tasks()


@app.get("/tasks/{task_id}", response_model=TaskRead)
def get_task(task_id: int, manager: ManagerDep):
    task = manager.get_task_by_id(task_id)
    if task is None:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Task not found",
        )
    return task
