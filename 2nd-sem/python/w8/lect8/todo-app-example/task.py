class Task:
    def __init__(
        self,
        id: int,
        description: str,
        completed: bool = False,
    ):
        self.id = id
        self.description = description
        self.completed = completed

    def mark_complete(self) -> None:
        self.completed = True

    def update_description(self, new_description: str) -> None:
        self.description = new_description

    def __repr__(self) -> str:
        return (
            f"Task(id={self.id}, "
            f"description={self.description!r}, "
            f"completed={self.completed})"
        )