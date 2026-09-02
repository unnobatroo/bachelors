"""
Storage Module - Demonstrating Abstract Base Classes (ABC) and Polymorphism

This module implements the Storage pattern using Python's ABC (Abstract Base Class).
This is a design pattern that allows us to define a contract (interface) that
all storage implementations must follow, while allowing different implementations
(JSON file, memory, database, etc.) to be used interchangeably.

Key Concepts:
- ABC (Abstract Base Class): A class that cannot be instantiated directly,
  but defines methods that subclasses must implement.
- Polymorphism: The ability to use different storage implementations
  through the same interface.
- Dependency Injection: Passing dependencies (like storage) into a class
  rather than creating them inside the class.
"""

from __future__ import annotations

import json
from abc import ABC, abstractmethod
from pathlib import Path
from typing import Any, TypedDict


class TaskRecord(TypedDict):
    """Shape of each task dict on disk (matches JSON the app reads and writes)."""

    description: str
    completed: bool


class Storage(ABC):
    """
    Abstract Base Class for Storage implementations.

    This class defines the "contract" that all storage implementations must follow.
    Any class that inherits from Storage MUST implement both load() and save() methods.

    Why use ABC?
    - Ensures all storage implementations have the same interface
    - Makes code more maintainable and testable
    - Allows easy swapping of storage backends
    - Provides clear documentation of what methods are required

    Note: You cannot create an instance of Storage directly:
        storage = Storage()  # This would raise TypeError
    """

    @abstractmethod
    def load(self) -> list[dict[str, Any]]:
        """
        Load tasks from storage.

        Returns:
            A list of task dictionaries with 'description' and 'completed' keys.

        Implementations should handle errors gracefully and return an empty list
        when no data is available or when the data cannot be read.
        """
        ...

    @abstractmethod
    def save(self, tasks: list[TaskRecord]) -> None:
        """
        Save tasks to storage.

        Args:
            tasks: Task dictionaries to save.
        """
        ...


class JsonFileStorage(Storage):
    """
    JSON File Storage Implementation.

    This class implements the Storage ABC by storing tasks in a JSON file.
    This is a concrete implementation - you CAN create instances of this class.

    This refactors the JSON file logic that was previously in TodoManager,
    following the Single Responsibility Principle: TodoManager handles task
    management, while JsonFileStorage handles file I/O.

    Example:
        storage = JsonFileStorage("tasks.json")
        tasks = storage.load()  # Load from file
        storage.save([{"description": "Learn Python", "completed": False}])
    """

    def __init__(self, file_path: str | Path = "tasks.json") -> None:
        """
        Initialize JSON file storage.

        Args:
            file_path: Path to the JSON file where tasks will be stored.
        """
        self.file_path = str(file_path)

    def load(self) -> list[dict[str, Any]]:
        """
        Load tasks from a JSON file.

        Returns:
            List of task dictionaries. Returns empty list if file doesn't exist
            or if there's an error reading the file.
        """
        path = Path(self.file_path)
        try:
            if not path.is_file():
                return []
            with path.open(encoding="utf-8") as f:
                data = json.load(f)
            return data if isinstance(data, list) else []
        except (OSError, json.JSONDecodeError) as e:
            print(f"Error loading tasks from {self.file_path}: {e}")
            return []

    def save(self, tasks: list[TaskRecord]) -> None:
        """
        Save tasks to a JSON file.

        Args:
            tasks: List of task dictionaries to save.
        """
        path = Path(self.file_path)
        try:
            with path.open("w", encoding="utf-8") as f:
                json.dump(tasks, f, indent=2)
        except OSError as e:
            print(f"Error saving tasks to {self.file_path}: {e}")


class MemoryStorage(Storage):
    """
    In-Memory Storage Implementation.

    This class implements the Storage ABC by storing tasks in a list in memory.
    This is useful for:
    - Testing (fast, no file I/O)
    - Temporary sessions (data is lost when program exits)
    - Demonstrating that different storage backends can be swapped easily

    This demonstrates polymorphism: the same Storage interface, but different
    implementation. TodoManager doesn't need to know or care whether it's
    using file storage or memory storage!

    Example:
        storage = MemoryStorage()
        tasks = storage.load()  # Load from memory
        storage.save([{"description": "Learn Python", "completed": False}])
    """

    def __init__(self) -> None:
        self._tasks: list[dict[str, Any]] = []

    def load(self) -> list[dict[str, Any]]:
        """
        Load tasks from memory.

        Returns:
            List of task dictionaries stored in memory.
        """
        return [task.copy() for task in self._tasks]

    def save(self, tasks: list[TaskRecord]) -> None:
        """
        Save tasks to memory.

        Args:
            tasks: List of task dictionaries to save.
        """
        self._tasks = [task.copy() for task in tasks]
