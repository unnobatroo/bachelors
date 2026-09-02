"""Business logic for CRUD operations."""

from student_registration.exceptions import (
    DuplicateStudentError,
    StudentNotFoundError,
)
from student_registration.model import Student
from student_registration.storage import StudentStorage
from student_registration.validators import (
    validate_age,
    validate_classes,
    validate_gpa,
    validate_name,
    validate_neptun_code,
)


class StudentService:
    """Coordinates validation, in-memory data, and persistence."""

    def __init__(self, storage: StudentStorage) -> None:
        self.storage = storage
        self.students = storage.load_students()

    def create_student(
        self,
        neptun_code: str,
        name: str,
        age: str | int,
        gpa: str | float,
        classes: str | list[str],
    ) -> Student:
        """Create a student after validating all fields."""
        code = validate_neptun_code(neptun_code)
        if code in self.students:
            raise DuplicateStudentError(f"Student with Neptun code '{code}' already exists.")

        student = Student(
            neptun_code=code,
            name=validate_name(name),
            age=validate_age(age),
            gpa=validate_gpa(gpa),
            classes=validate_classes(classes),
        )
        self.students[code] = student
        self.storage.save_students(self.students)
        return student

    def get_student(self, neptun_code: str) -> Student:
        """Return one student by Neptun code."""
        code = validate_neptun_code(neptun_code)
        student = self.students.get(code)
        if not student:
            raise StudentNotFoundError(f"Student '{code}' was not found.")
        return student

    def get_all_students(self) -> list[Student]:
        """Return all students sorted by Neptun code for stable output."""
        return sorted(self.students.values(), key=lambda s: s.neptun_code)

    def update_student(
        self,
        neptun_code: str,
        name: str,
        age: str | int,
        gpa: str | float,
        classes: str | list[str],
    ) -> Student:
        """Update all editable fields of an existing student."""
        code = validate_neptun_code(neptun_code)
        if code not in self.students:
            raise StudentNotFoundError(f"Student '{code}' was not found.")

        updated = Student(
            neptun_code=code,
            name=validate_name(name),
            age=validate_age(age),
            gpa=validate_gpa(gpa),
            classes=validate_classes(classes),
        )
        self.students[code] = updated
        self.storage.save_students(self.students)
        return updated

    def delete_student(self, neptun_code: str) -> None:
        """Delete a student by Neptun code."""
        code = validate_neptun_code(neptun_code)
        if code not in self.students:
            raise StudentNotFoundError(f"Student '{code}' was not found.")

        del self.students[code]
        self.storage.save_students(self.students)
