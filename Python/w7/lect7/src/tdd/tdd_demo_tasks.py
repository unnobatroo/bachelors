"""
This file represents the "application code" for the TDD demo.
We will modify this file *only* after writing a failing test.
"""

class Task:
    """Represents a single To-Do item."""
    
    #
    # STEP 2: [GREEN]
    # Add 'priority=None' and 'self.priority = priority'
    # to make the first test pass.
    #
    # STEP 3: [REFACTOR]
    # Change 'priority=None' to 'priority=3' and add validation.
    #
    def __init__(self, description, status="pending", priority=3):
        
        # This validation is added during the [REFACTOR] step
        if not isinstance(priority, int) or not 1 <= priority <= 5:
            raise ValueError("Priority must be an integer between 1 and 5.")
            
        self.description = description
        self.status = status
        self.priority = priority # This line is added in the [GREEN] step

    def __repr__(self):
        """Developer-friendly representation."""
        return f"Task(desc='{self.description}', status='{self.status}', priority={self.priority})"

    def complete(self):
        """Marks the task as complete."""
        self.status = "complete"
