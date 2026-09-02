"""
Storage Demo - Demonstrating the Flexibility of Dependency Injection

This file shows how easy it is to swap storage implementations thanks to
the Storage ABC and Dependency Injection pattern. The same TodoManager
code works with different storage backends without any changes!

Run this file to see both storage types in action (from this directory):

    uv run python storage_demo.py
"""

from todo.manager import TodoManager
from todo.storage import JsonFileStorage, MemoryStorage


def demo_json_storage():
    """Demonstrate using JSON file storage (persistent across program runs)"""
    print("=" * 60)
    print("DEMO 1: JSON File Storage (Persistent)")
    print("=" * 60)
    
    # Create JSON file storage
    storage = JsonFileStorage("demo_tasks.json")
    
    # Inject it into TodoManager
    manager = TodoManager(storage)
    
    # Add some tasks
    print("\nAdding tasks...")
    manager.add_task("Learn Python ABCs")
    manager.add_task("Understand Dependency Injection")
    manager.add_task("Practice with Storage pattern")
    
    # List tasks
    print("\nCurrent tasks:")
    tasks = manager.list_tasks()
    for idx, task in enumerate(tasks, start=1):
        status = "✓" if task.completed else "✗"
        print(f"  {idx}. [{status}] {task.description}")
    
    # Complete a task
    print("\nCompleting task 1...")
    success, message = manager.complete_task(1)
    print(f"  {message}")
    
    # List tasks again
    print("\nTasks after completion:")
    tasks = manager.list_tasks()
    for idx, task in enumerate(tasks, start=1):
        status = "✓" if task.completed else "✗"
        print(f"  {idx}. [{status}] {task.description}")
    
    print("\n✓ Data is saved to 'demo_tasks.json' and will persist!")
    print("  Try running this demo again - the tasks will still be there!\n")


def demo_memory_storage():
    """Demonstrate using in-memory storage (temporary, lost when program exits)"""
    print("=" * 60)
    print("DEMO 2: Memory Storage (Temporary)")
    print("=" * 60)
    
    # Create memory storage
    storage = MemoryStorage()
    
    # Inject it into TodoManager (same TodoManager, different storage!)
    manager = TodoManager(storage)
    
    # Add some tasks
    print("\nAdding tasks...")
    manager.add_task("Task in memory 1")
    manager.add_task("Task in memory 2")
    
    # List tasks
    print("\nCurrent tasks:")
    tasks = manager.list_tasks()
    for idx, task in enumerate(tasks, start=1):
        status = "✓" if task.completed else "✗"
        print(f"  {idx}. [{status}] {task.description}")
    
    print("\n✓ Data is stored in memory only (RAM)")
    print("  When the program exits, this data will be lost!")
    print("  This is useful for testing or temporary sessions.\n")


def demo_storage_swap():
    """Demonstrate how easy it is to swap storage implementations"""
    print("=" * 60)
    print("DEMO 3: Swapping Storage (Polymorphism in Action)")
    print("=" * 60)
    
    print("\nThe same TodoManager code works with ANY Storage implementation!")
    print("This is called 'polymorphism' - different objects, same interface.\n")
    
    # Try with JSON storage
    print("1. Using JsonFileStorage:")
    json_storage = JsonFileStorage("swap_demo.json")
    json_manager = TodoManager(json_storage)
    json_manager.add_task("Task saved to file")
    print("   ✓ Task added and saved to file")
    
    # Try with Memory storage
    print("\n2. Using MemoryStorage:")
    memory_storage = MemoryStorage()
    memory_manager = TodoManager(memory_storage)
    memory_manager.add_task("Task saved to memory")
    print("   ✓ Task added and saved to memory")
    
    print("\n3. Both managers work the same way!")
    print("   - Same TodoManager class")
    print("   - Same methods (add_task, list_tasks, etc.)")
    print("   - Different storage backends")
    print("   - No changes needed to TodoManager code!\n")


if __name__ == "__main__":
    print("\n" + "=" * 60)
    print("STORAGE ABSTRACTION & DEPENDENCY INJECTION DEMO")
    print("=" * 60)
    print("\nThis demo shows how the Storage ABC and Dependency Injection")
    print("make our code flexible and testable.\n")
    
    # Run all demos
    demo_json_storage()
    demo_memory_storage()
    demo_storage_swap()
    
    print("=" * 60)
    print("KEY TAKEAWAYS:")
    print("=" * 60)
    print("1. ABC (Abstract Base Class) defines a contract that all")
    print("   storage implementations must follow.")
    print("2. Dependency Injection allows us to pass storage to TodoManager")
    print("   instead of creating it inside TodoManager.")
    print("3. This 'decouples' TodoManager from specific storage types,")
    print("   making the code more flexible and testable.")
    print("4. We can easily swap storage implementations without changing")
    print("   TodoManager code - this is the power of good design!")
    print("=" * 60 + "\n")

