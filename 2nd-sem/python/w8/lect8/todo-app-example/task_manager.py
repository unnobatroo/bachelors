from models import TaskCreate
from task import Task

class TaskManager:
    def __init__(self) -> None:
        self._tasks: dict[int, Task] = {}
        self._next_id = 1

    def create_task(self, task_in: TaskCreate) -> Task:
        task = Task(
            id=self._next_id,
            description=task_in.description,
        )
        self._tasks[self._next_id] = task
        self._next_id += 1
        return task

    def get_all_tasks(self) -> list[Task]:
        return list(self._tasks.values())

    def get_task_by_id(self, task_id: int) -> Task | None:
        return self._tasks.get(task_id)

    def clear_all_tasks(self) -> None:
        self._tasks.clear()
        self._next_id = 1
