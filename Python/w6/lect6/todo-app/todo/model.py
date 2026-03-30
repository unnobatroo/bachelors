class Task:
    def __init__(self, description):
        self.description = description
        self.completed = False

    def __repr__(self):
        # A special method for a developer-friendly string representation
        return f"Task(description='{self.description}', completed={self.completed})"

    def complete(self):
        self.completed = True

    def to_dict(self):
        """Convert task to dictionary for JSON serialization"""
        return {"description": self.description, "completed": self.completed}

    @classmethod
    def from_dict(cls, data):
        """Create task from dictionary (for JSON deserialization)"""
        task = cls(data["description"])
        task.completed = data["completed"]
        return task
