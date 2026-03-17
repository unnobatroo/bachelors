from .manager import TodoManager
import sys


class TodoCLI:
    def __init__(self):
        self.manager = TodoManager()  # This will auto-load tasks.json

    def run(self, argv: list[str]) -> None:
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

            success, message = self.manager.complete_task(task_index)
            print(message)

        elif command == "help":
            self._print_usage()

        else:
            print(f"Error: Unknown command '{command}'.")
            self._print_usage()

    def _print_usage(self) -> None:
        print("Usage:")
        print("Usage: python main.py <command> [args...]")
        print("Commands:")
        print("  add <task_description>  - Add a new task")
        print("  list                    - List all tasks")
        print("  complete <task_index>   - Mark a task as completed")
        print("  help                    - Show this help message")


def main():
    cli = TodoCLI()
    cli.run(sys.argv)
