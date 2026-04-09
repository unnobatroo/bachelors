from __future__ import annotations

from typing import Any, Mapping, Self


class Task:
    def __init__(self, description: str) -> None:
        self.description = description
        self.completed = False

    def __repr__(self) -> str:
        return f"Task(description={self.description!r}, completed={self.completed!r})"

    def complete(self) -> None:
        self.completed = True

    def to_dict(self) -> dict[str, str | bool]:
        """Serialize for JSON-compatible storage."""
        return {
            "description": self.description,
            "completed": self.completed,
        }

    @classmethod
    def from_dict(cls, data: Mapping[str, Any]) -> Self:
        """Restore from storage; missing keys are handled defensively."""
        task = cls(str(data["description"]))
        task.completed = bool(data.get("completed", False))
        return task
