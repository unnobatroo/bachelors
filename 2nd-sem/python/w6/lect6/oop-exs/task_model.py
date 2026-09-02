# task_model.py
class Task:
    def __init__(self, description):
        self.description = description
        self.completed = False

    def __repr__(self):
        # A special method for a developer-friendly string representation
        return f"Task(description='{self.description}', completed={self.completed})"

    def complete(self):
        self.completed = True


# Demo: creating and updating a Task object
task = Task("Write unit tests")
print(task)  # Task(description='Write unit tests', completed=False)
task.complete()
print(task)  # Task(description='Write unit tests', completed=True)
