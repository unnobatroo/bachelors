"""Persistence layer for student data."""

import json
from pathlib import Path

from student_registration.model import Student


class StudentStorage:
    """Stores and retrieves students from a JSON file."""

    def __init__(self, file_path: str | Path) -> None:
        self.file_path = Path(file_path)

    def load_students(self) -> dict[str, Student]:
        """Load all students from JSON at program startup."""
        if not self.file_path.exists():
            return {}

        with self.file_path.open("r", encoding="utf-8") as file:
            raw_data = json.load(file)

        students: dict[str, Student] = {}
        for item in raw_data:
            student = Student.from_dict(item)
            students[student.neptun_code] = student
        return students

    def save_students(self, students: dict[str, Student]) -> None:
        """Write all student records to disk."""
        self.file_path.parent.mkdir(parents=True, exist_ok=True)
        serialized = [student.to_dict() for student in students.values()]

        with self.file_path.open("w", encoding="utf-8") as file:
            json.dump(serialized, file, indent=2)
