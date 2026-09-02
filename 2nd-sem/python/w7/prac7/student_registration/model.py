"""Domain model objects for student registration."""

from dataclasses import dataclass


@dataclass
class Student:
    """Represents one student record in the system."""

    neptun_code: str
    name: str
    age: int
    gpa: float
    classes: list[str]

    def to_dict(self) -> dict:
        """Convert the student object to a JSON-serializable dictionary."""
        return {
            "neptun_code": self.neptun_code,
            "name": self.name,
            "age": self.age,
            "gpa": self.gpa,
            "classes": self.classes,
        }

    @classmethod
    def from_dict(cls, data: dict) -> "Student":
        """Build a Student from a dictionary loaded from JSON."""
        return cls(
            neptun_code=data["neptun_code"],
            name=data["name"],
            age=int(data["age"]),
            gpa=float(data["gpa"]),
            classes=list(data["classes"]),
        )
