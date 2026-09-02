"""Validation helpers used across the application."""

from student_registration.exceptions import ValidationError


def validate_neptun_code(value: str) -> str:
    """Validate and normalize a Neptun code.

    Neptun code is expected to be exactly 6 alphanumeric characters.
    """
    cleaned = value.strip().upper()
    if len(cleaned) != 6 or not cleaned.isalnum():
        raise ValidationError("Neptun code must be 6 alphanumeric characters.")
    return cleaned


def validate_name(value: str) -> str:
    """Validate student name."""
    cleaned = value.strip()
    if not cleaned:
        raise ValidationError("Name cannot be empty.")
    return cleaned


def validate_age(value: str | int) -> int:
    """Validate age and convert to integer."""
    try:
        age = int(value)
    except (TypeError, ValueError) as exc:
        raise ValidationError("Age must be a whole number.") from exc

    if age < 14 or age > 120:
        raise ValidationError("Age must be between 14 and 120.")
    return age


def validate_gpa(value: str | float) -> float:
    """Validate GPA and convert to float."""
    try:
        gpa = float(value)
    except (TypeError, ValueError) as exc:
        raise ValidationError("GPA must be a number.") from exc

    if gpa < 0.0 or gpa > 5.0:
        raise ValidationError("GPA must be between 0.0 and 5.0.")
    return round(gpa, 2)


def validate_classes(value: str | list[str]) -> list[str]:
    """Validate classes list.

    Accepts comma-separated string from CLI or list from storage.
    """
    if isinstance(value, str):
        classes = [item.strip() for item in value.split(",") if item.strip()]
    elif isinstance(value, list):
        classes = [str(item).strip() for item in value if str(item).strip()]
    else:
        raise ValidationError("Classes must be a comma-separated list.")

    if not classes:
        raise ValidationError("At least one class is required.")
    return classes
