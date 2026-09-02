"""Simple CLI layer for interacting with the student service."""

from student_registration.exceptions import (
    DuplicateStudentError,
    StudentNotFoundError,
    ValidationError,
)
from student_registration.service import StudentService


class StudentCLI:
    """Text menu interface for CRUD operations."""

    def __init__(self, service: StudentService) -> None:
        self.service = service

    def run(self) -> None:
        """Start the interactive command loop."""
        while True:
            self._print_menu()
            choice = input("Choose an option: ").strip()

            if choice == "1":
                self._create_student()
            elif choice == "2":
                self._view_students()
            elif choice == "3":
                self._update_student()
            elif choice == "4":
                self._delete_student()
            elif choice == "0":
                print("Goodbye.")
                break
            else:
                print("Invalid option. Please choose from the menu.")

    def _print_menu(self) -> None:
        """Display available operations."""
        print("\n=== Student Registration System ===")
        print("1. Create a student")
        print("2. View student information")
        print("3. Update student information")
        print("4. Delete a student")
        print("0. Exit")

    def _collect_student_payload(self, code: str | None = None) -> dict:
        """Collect all fields needed for create/update operations."""
        return {
            "neptun_code": code if code is not None else input("Neptun code: "),
            "name": input("Name: "),
            "age": input("Age: "),
            "gpa": input("GPA (0.0 - 5.0): "),
            "classes": input("Classes (comma-separated): "),
        }

    def _create_student(self) -> None:
        """Handle student creation with robust error handling."""
        try:
            payload = self._collect_student_payload()
            student = self.service.create_student(**payload)
            print(f"Student '{student.neptun_code}' created successfully.")
        except (ValidationError, DuplicateStudentError) as error:
            print(f"Error: {error}")

    def _view_students(self) -> None:
        """Show all students or a single student by code."""
        mode = input("View (a)ll students or (o)ne student? ").strip().lower()

        try:
            if mode == "a":
                students = self.service.get_all_students()
                if not students:
                    print("No student records found.")
                    return
                for student in students:
                    self._print_student(student)
            elif mode == "o":
                code = input("Neptun code: ")
                student = self.service.get_student(code)
                self._print_student(student)
            else:
                print("Invalid view option.")
        except (ValidationError, StudentNotFoundError) as error:
            print(f"Error: {error}")

    def _update_student(self) -> None:
        """Update an existing student."""
        code = input("Neptun code of student to update: ")

        try:
            payload = self._collect_student_payload(code=code)
            student = self.service.update_student(**payload)
            print(f"Student '{student.neptun_code}' updated successfully.")
        except (ValidationError, StudentNotFoundError) as error:
            print(f"Error: {error}")

    def _delete_student(self) -> None:
        """Delete one student by code."""
        code = input("Neptun code of student to delete: ")

        try:
            self.service.delete_student(code)
            print("Student deleted successfully.")
        except (ValidationError, StudentNotFoundError) as error:
            print(f"Error: {error}")

    def _print_student(self, student) -> None:
        """Print one student record in a readable format."""
        classes_text = ", ".join(student.classes)
        print("------------------------------")
        print(f"Neptun code: {student.neptun_code}")
        print(f"Name:        {student.name}")
        print(f"Age:         {student.age}")
        print(f"GPA:         {student.gpa}")
        print(f"Classes:     {classes_text}")
