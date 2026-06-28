# Storage Abstraction & Dependency Injection - Implementation Guide

## Overview

This refactoring implements **Object-Oriented Programming (OOP) principles** using **Abstract Base Classes (ABC)** and **Dependency Injection** to make the todo application more flexible, testable, and maintainable.

## What Was Changed

### Before Refactoring
- `TodoManager` was tightly coupled to JSON file storage
- File I/O logic was mixed with task management logic
- Hard to test (required actual file system)
- Hard to swap storage backends

### After Refactoring
- Storage is abstracted through an ABC interface
- `TodoManager` is decoupled from storage implementation
- Easy to test (can use in-memory storage)
- Easy to swap storage backends (JSON, memory, database, etc.)

---

## Key Concepts Explained

### 1. Abstract Base Class (ABC)

**What is it?**
An Abstract Base Class is a class that cannot be instantiated directly. It defines a "contract" (interface) that all subclasses must follow.

**Why use it?**
- Ensures all implementations have the same interface
- Makes code more maintainable
- Provides clear documentation of required methods
- Enables polymorphism

**Example:**
```python
from abc import ABC, abstractmethod

class Storage(ABC):
    @abstractmethod
    def load(self):
        pass  # Must be implemented by subclasses
    
    @abstractmethod
    def save(self, tasks):
        pass  # Must be implemented by subclasses

# This would raise TypeError:
# storage = Storage()  # ❌ Cannot instantiate ABC directly

# But this works:
class JsonFileStorage(Storage):
    def load(self):
        # Implementation here
        pass
    
    def save(self, tasks):
        # Implementation here
        pass

storage = JsonFileStorage()  # ✅ This works!
```

**In our code:**
- `Storage` is the ABC defining the contract
- `JsonFileStorage` and `MemoryStorage` are concrete implementations

---

### 2. Dependency Injection

**What is it?**
Dependency Injection is a design pattern where dependencies (like storage) are passed into a class rather than created inside the class.

**Before (Tight Coupling):**
```python
class TodoManager:
    def __init__(self):
        self.file_path = "tasks.json"  # Hard-coded dependency
        # TodoManager creates its own storage
```

**After (Dependency Injection):**
```python
class TodoManager:
    def __init__(self, storage: Storage):  # Storage is injected
        self.storage = storage  # Use the provided storage
        # TodoManager doesn't know or care about file paths
```

**Benefits:**
- **Decoupling**: TodoManager doesn't depend on specific storage types
- **Testability**: Easy to inject a test storage (like MemoryStorage)
- **Flexibility**: Can swap storage implementations easily
- **Single Responsibility**: Each class has one job

**In our code:**
- `TodoManager.__init__()` accepts a `storage` parameter
- `TodoCLI` creates the storage and injects it into `TodoManager`

---

### 3. Polymorphism

**What is it?**
Polymorphism means "many forms" - the ability to use different implementations through the same interface.

**In our code:**
```python
# Both of these work with the same TodoManager code:
json_storage = JsonFileStorage("tasks.json")
memory_storage = MemoryStorage()

# TodoManager works with either one!
manager1 = TodoManager(json_storage)   # Uses file storage
manager2 = TodoManager(memory_storage) # Uses memory storage

# Both managers have the same methods and work the same way
manager1.add_task("Task 1")
manager2.add_task("Task 2")
```

---

## File Structure

```
todo-app-example/
├── pyproject.toml          # uv project metadata; dev dependency: pytest
├── uv.lock                 # Locked versions (commit with pyproject.toml)
├── main.py                 # Entry point (unchanged)
├── storage_demo.py         # Demo file showing both storage types
├── test_storage.py         # pytest tests for JsonFileStorage (tmp_path)
├── TESTING_GUIDE.md        # Detailed testing walkthrough
├── QUICK_TEST_REFERENCE.md # Short pytest / uv command reference
├── REFACTORING_GUIDE.md    # This file
└── todo/
    ├── __init__.py
    ├── model.py            # Task class (unchanged)
    ├── storage.py          # ✨ NEW: Storage ABC and implementations
    ├── manager.py          # ✨ REFACTORED: Now uses dependency injection
    └── cli.py              # ✨ UPDATED: Creates and injects storage
```

---

## Implementation Details

### Feature 4.1: Storage ABC

**File:** `todo/storage.py`

Created `Storage` abstract base class with:
- `load() -> List[Dict[str, Any]]`: Abstract method to load tasks
- `save(tasks: List[Dict[str, Any]]) -> None`: Abstract method to save tasks

**Key Points:**
- Uses `@abstractmethod` decorator
- Cannot be instantiated directly
- Defines the contract all storage implementations must follow

---

### Feature 4.2: JsonFileStorage

**File:** `todo/storage.py`

Refactored existing JSON file logic into `JsonFileStorage` class:
- Implements `Storage` ABC
- Handles file I/O operations
- Manages JSON serialization/deserialization
- Error handling for file operations

**Key Points:**
- Moved from `TodoManager.save_to_file()` and `TodoManager.load_from_file()`
- Follows Single Responsibility Principle
- Can be used independently or with TodoManager

---

### Feature 4.3: MemoryStorage

**File:** `todo/storage.py`

Created `MemoryStorage` class:
- Implements `Storage` ABC
- Stores data in a dictionary (in-memory)
- Data is lost when program exits
- Useful for testing and temporary sessions

**Key Points:**
- Demonstrates polymorphism (same interface, different implementation)
- Fast (no file I/O)
- Perfect for unit testing

---

### Feature 4.4: Dependency Injection in TodoManager

**File:** `todo/manager.py`

Refactored `TodoManager`:
- **Before:** `__init__(self, file_path="tasks.json")`
- **After:** `__init__(self, storage: Storage)`
- Removed file I/O code (moved to JsonFileStorage)
- Uses `storage.load()` and `storage.save()` instead

**Key Changes:**
- Removed `json` and `os` imports
- Removed `save_to_file()` and `load_from_file()` methods
- Added `_load_tasks()` and `_save_tasks()` private methods
- Now works with any Storage implementation

---

### Updated CLI

**File:** `todo/cli.py`

Updated to create and inject storage:
- Creates `JsonFileStorage` instance
- Passes it to `TodoManager` constructor
- This is the "injection point" where we choose storage type

---

## How to Use

### Using JSON File Storage (Default)
```python
from todo.manager import TodoManager
from todo.storage import JsonFileStorage

storage = JsonFileStorage("tasks.json")
manager = TodoManager(storage)
manager.add_task("Learn Python")
```

### Using Memory Storage (For Testing)
```python
from todo.manager import TodoManager
from todo.storage import MemoryStorage

storage = MemoryStorage()
manager = TodoManager(storage)
manager.add_task("Test task")
```

### Running the Demo

From this directory (same as `pyproject.toml`), use uv so the interpreter is consistent with the rest of Lecture 7:

```bash
cd 7_OOP2_Testing_Debugging/src/todo-app-example
uv run python storage_demo.py
```

This will demonstrate:
1. JSON file storage (persistent)
2. Memory storage (temporary)
3. How easy it is to swap storage implementations

### Tests and quality tools

Storage behavior is covered in `test_storage.py`. After `uv sync`, run:

```bash
uv run pytest test_storage.py -q
```

See [TESTING_GUIDE.md](TESTING_GUIDE.md) for the full pytest and `tmp_path` explanation. Later in the lecture, the slides describe a broader quality loop (`uv sync`, then `uv run ruff check .`, `uv run pyright`, `uv run pytest -q`); you can add **ruff** and **pyright** as dev dependencies when you reach that iteration.

---

## Benefits of This Refactoring

1. **Separation of Concerns**
   - TodoManager handles task logic
   - Storage classes handle persistence
   - Each class has one responsibility

2. **Testability**
   - Can use MemoryStorage for fast unit tests
   - No need to create/clean up files during testing

3. **Flexibility**
   - Easy to add new storage types (database, cloud, etc.)
   - Just implement the Storage ABC interface

4. **Maintainability**
   - Changes to storage logic don't affect TodoManager
   - Changes to TodoManager don't affect storage logic

5. **Reusability**
   - Storage classes can be used independently
   - TodoManager can work with any storage implementation

---

## Learning Outcomes

After studying this refactoring, you should understand:

1. **Abstract Base Classes (ABC)**
   - How to define an interface using ABC
   - How to enforce method implementation
   - When and why to use ABCs

2. **Dependency Injection**
   - How to inject dependencies instead of creating them
   - Benefits of dependency injection
   - How it improves testability and flexibility

3. **Polymorphism**
   - How different implementations can share the same interface
   - How to write code that works with multiple types

4. **Design Principles**
   - Single Responsibility Principle
   - Dependency Inversion Principle
   - Separation of Concerns

---

## Summary

This refactoring demonstrates important OOP concepts:
- **ABC** for defining contracts
- **Dependency Injection** for decoupling
- **Polymorphism** for flexibility
- **Separation of Concerns** for maintainability

The code is now more professional, testable, and maintainable while being easier to extend with new features!

