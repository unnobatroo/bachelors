"""
CLI Module - Demonstrating Dependency Injection in Practice

This module shows how Dependency Injection works at the application level.
The CLI creates a storage instance and "injects" it into TodoManager.
This is where we decide which storage implementation to use.
"""

from __future__ import annotations

import sys
from collections.abc import Sequence

from .manager import TodoManager
from .storage import JsonFileStorage


class TodoCLI:
    """
    Command-line interface for the Todo application.

    This class demonstrates Dependency Injection: it creates a storage
    instance and passes it to TodoManager. This is the "injection point"
    where we decide which storage backend to use.
    """

    def __init__(self) -> None:
        storage = JsonFileStorage("tasks.json")
        self.manager = TodoManager(storage)

    def run(self, argv: Sequence[str]) -> None:
        if len(argv) < 2:
            self._print_usage()
            return

        command = argv[1].lower()

        if command == "add":
            if len(argv) < 3:
                print("Error: Task description is required for 'add' command.")
                return
            description = " ".join(argv[2:])
            self.manager.add_task(description)
            print(f"Added task: {description}")

        elif command == "list":
            tasks = self.manager.list_tasks()
            if not tasks:
                print("No tasks found.")
                return
            for idx, task in enumerate(tasks, start=1):
                status = "✓" if task.completed else "✗"
                print(f"{idx}. [{status}] {task.description}")

        elif command == "complete":
            if len(argv) < 3:
                print("Error: Task index is required for 'complete' command.")
                return

            try:
                task_index = int(argv[2])
                if task_index <= 0:
                    print("Error: Task index must be a positive number.")
                    return
            except ValueError:
                print(
                    f"Error: '{argv[2]}' is not a valid task index. Please use a positive number."
                )
                return

            _success, message = self.manager.complete_task(task_index)
            print(message)

        elif command == "help":
            self._print_usage()

        else:
            print(f"Error: Unknown command '{command}'.")
            self._print_usage()

    def _print_usage(self) -> None:
        print("Usage: uv run python main.py <command> [args...]")
        print("Commands:")
        print("  add <task_description>  - Add a new task")
        print("  list                    - List all tasks")
        print("  complete <task_index>   - Mark a task as completed")
        print("  help                    - Show this help message")


def main() -> None:
    cli = TodoCLI()
    cli.run(sys.argv)
