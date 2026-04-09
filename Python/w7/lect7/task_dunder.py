class Task:
    def __init__(self, description, completed=False):
        self.description = description
        self.completed = completed

    def __repr__(self) -> str:
        """Developer-focused, unambiguous representation."""
        # Note: Shows how to re-create the object
        return f"Task(description='{self.description}', completed={self.completed})"

    def __str__(self) -> str:
        """User-friendly, readable representation."""
        status = "[x]" if self.completed else "[ ]"
        return f"{status} {self.description}"

# --- Let's see them in action ---
task = Task("Buy groceries", completed=True)

# 1. Called by print()
print(task)
# Output (user-friendly): [x] Buy groceries

# 2. Called in a shell/debugger
# >>> task
# Output (developer-focused):
# Task(description='Buy groceries', completed=True)

# 3. Explicitly called
print(str(task))  # [x] Buy groceries
print(repr(task)) # Task(description='Buy groceries', completed=True)
