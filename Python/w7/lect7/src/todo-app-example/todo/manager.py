"""
TodoManager Module - Demonstrating Dependency Injection

This module has been refactored to use Dependency Injection, a design pattern
where dependencies (like storage) are passed into a class rather than created
inside the class. This "decouples" the TodoManager from specific storage
implementations, making the code more flexible and testable.

Key Concepts:
- Dependency Injection: Instead of TodoManager creating its own storage,
  we pass a storage object to it. This makes TodoManager independent of
  how data is stored (file, memory, database, etc.).
- Decoupling: TodoManager no longer knows or cares about JSON files,
  file paths, or file I/O. It only knows about the Storage interface.
- Flexibility: We can easily swap storage implementations without
  changing TodoManager code.
"""

from __future__ import annotations

from .model import Task
from .storage import Storage


class TodoManager:
    """
    Manages todo tasks with storage abstraction.

    This class is now "decoupled" from file storage. It works with any
    storage implementation that follows the Storage interface (ABC).

    Dependency Injection Pattern:
    - Before: TodoManager created its own file storage internally
    - After: TodoManager receives a storage object from outside

    Benefits:
    - Easier to test (can use MemoryStorage for fast tests)
    - More flexible (can switch storage backends easily)
    - Follows Single Responsibility Principle (TodoManager manages tasks,
      Storage handles persistence)
    """

    def __init__(self, storage: Storage) -> None:
        """
        Initialize TodoManager with a storage implementation.

        This is Dependency Injection: instead of creating storage inside
        this class, we receive it as a parameter. This makes TodoManager
        independent of how data is stored.

        Args:
            storage: A Storage implementation (e.g., JsonFileStorage, MemoryStorage)
                Must implement the Storage ABC interface (load() and save() methods)

        Example:
            # Use JSON file storage
            json_storage = JsonFileStorage("tasks.json")
            manager = TodoManager(json_storage)

            # Or use in-memory storage (for testing)
            memory_storage = MemoryStorage()
            manager = TodoManager(memory_storage)
        """
        self.storage = storage
        self.tasks: list[Task] = []
        self._load_tasks()

    def _load_tasks(self) -> None:
        """
        Load tasks from storage and convert them to Task objects.

        Private method: converts dictionaries (storage format)
        to Task objects (in-memory format).
        """
        task_data_list = self.storage.load()
        self.tasks = [Task.from_dict(task_data) for task_data in task_data_list]

    def _save_tasks(self) -> None:
        """
        Convert Task objects to dictionaries and save to storage.

        Private method: converts Task objects to dictionaries (storage format).
        """
        task_data_list = [task.to_dict() for task in self.tasks]
        self.storage.save(task_data_list)

    def add_task(self, description: str) -> None:
        """
        Add a new task.

        Args:
            description: The task description text.
        """
        task = Task(description)
        self.tasks.append(task)
        self._save_tasks()

    def list_tasks(self) -> list[Task]:
        """
        Get all tasks.

        Returns:
            List of Task objects.
        """
        return self.tasks

    def complete_task(self, task_index: int) -> tuple[bool, str]:
        """
        Complete a task by index (1-based).

        Args:
            task_index: The task number (1-based, as shown to users)

        Returns:
            Tuple of (success: bool, message: str)
        """
        index = task_index - 1

        if index < 0 or index >= len(self.tasks):
            return False, "Error: Invalid task index."

        if self.tasks[index].completed:
            return False, f"Task {task_index} is already completed."

        self.tasks[index].complete()
        self._save_tasks()

        return True, f"Marked task {task_index} as completed."
