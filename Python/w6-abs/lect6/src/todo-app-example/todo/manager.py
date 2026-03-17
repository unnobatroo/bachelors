import json
import os
from .model import Task

class TodoManager:
    def __init__(self, file_path="tasks.json"):
        self.tasks = []
        self.file_path = file_path
        self.load_from_file()

    def add_task(self, description):
        task = Task(description)
        self.tasks.append(task)
        self.save_to_file()

    def list_tasks(self):
        return self.tasks
    
    def complete_task(self, task_index) -> tuple[bool, str]:
        """Complete a task by index (1-based). Returns success status and message."""
        # Convert to 0-based index
        index = task_index - 1
        
        # Validate index
        if index < 0 or index >= len(self.tasks):
            return False, "Error: Invalid task index."
        
        # Check if task is already completed
        if self.tasks[index].completed:
            return False, f"Task {task_index} is already completed."
        
        # Complete the task and save
        self.tasks[index].complete()
        self.save_to_file()
        return True, f"Marked task {task_index} as completed."
    
    def save_to_file(self):
        """Save tasks to JSON file"""
        try:
            data = [task.to_dict() for task in self.tasks]
            with open(self.file_path, 'w') as f:
                json.dump(data, f, indent=2)
        except Exception as e:
            print(f"Error saving tasks: {e}")
    
    def load_from_file(self):
        """Load tasks from JSON file"""
        try:
            if os.path.exists(self.file_path):
                with open(self.file_path, 'r') as f:
                    data = json.load(f)
                    self.tasks = [Task.from_dict(task_data) for task_data in data]
        except Exception as e:
            print(f"Error loading tasks: {e}")
            self.tasks = []