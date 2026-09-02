"""Entry point for the Student Registration application."""

from pathlib import Path

from student_registration.cli import StudentCLI
from student_registration.service import StudentService
from student_registration.storage import StudentStorage


def main() -> None:
    """Configure dependencies and start the CLI app."""
    data_file = Path(__file__).parent / "students.json"
    storage = StudentStorage(data_file)
    service = StudentService(storage)
    cli = StudentCLI(service)
    cli.run()


if __name__ == "__main__":
    main()
