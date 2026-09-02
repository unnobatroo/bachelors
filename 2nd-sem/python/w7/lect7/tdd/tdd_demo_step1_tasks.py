"""
This file represents the "application code" for the TDD demo.
We will modify this file *only* after writing a failing test.
"""

class Task:
    """Represents a single To-Do item."""
 
    def __init__(self, description, status="pending"):
                
        self.description = description
        self.status = status

    def __repr__(self):
        """Developer-friendly representation."""
        return f"Task(desc='{self.description}', status='{self.status}')"

    def complete(self):
        """Marks the task as complete."""
        self.status = "complete"
