"""Custom exceptions for student registration."""


class ValidationError(ValueError):
    """Raised when user input or data payload is invalid."""


class StudentNotFoundError(LookupError):
    """Raised when a student is not found in storage."""


class DuplicateStudentError(ValueError):
    """Raised when trying to create a student with an existing Neptun code."""
