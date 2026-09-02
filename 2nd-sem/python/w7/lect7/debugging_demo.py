"""
This file contains a  bug for the debugging demo.
The bug is a classic "string key vs. integer key" mismatch.
"""

# Our "database" of tasks. Note the keys are INTEGERS.
TASKS_DB = {
    1: {"desc": "Buy milk", "status": "pending"},
    2: {"desc": "Do laundry", "status": "pending"},
    3: {"desc": "Call mom", "status": "complete"},
}

def find_task_by_id(task_id):
    """
    Finds a task by its ID. Returns the task dict or None.
    
    The 'task_id' argument we receive will be a STRING (e.g., "2"),
    but the keys in TASKS_DB are INTEGERS (e.g., 2).
    """
    print(f"--- Searching for ID: {task_id} (Type: {type(task_id)}) ---")
    
    # THE BUG IS ON THIS LINE
    # We are checking if a STRING (e.g., "2") is in
    # the dictionary keys, which are INTEGERS (1, 2, 3).
    # This will always be False.
    if task_id in TASKS_DB:
        return TASKS_DB[task_id]
    
    # The code will always return None
    return None

def main():
    """
    Main function to run the demo.
    """
    # This simulates input from sys.argv or input(),
    # which always returns a STRING.
    id_from_user = "2"
    
    print(f"Attempting to find task with ID: {id_from_user}")
    
    # --- SET YOUR BREAKPOINT HERE ---
    # In VSCode, click in the gutter to the left of this line.
    # When execution pauses, 'id_from_user' will clearly show "2".
    
    task = find_task_by_id(id_from_user)
    
    # When you "Step Into" find_task_by_id, 'task_id' will also be "2".
    # Hovering 'TASKS_DB' will show {1: ..., 2: ..., 3: ...}.
    # The mismatch between "2" (str) and 2 (int) will be obvious.
    
    if task:
        print(f"\nSUCCESS: Found task {id_from_user}: {task['desc']}")
    else:
        # This is what will incorrectly print
        print(f"\nERROR: Task {id_from_user} not found!")

if __name__ == "__main__":
    main()