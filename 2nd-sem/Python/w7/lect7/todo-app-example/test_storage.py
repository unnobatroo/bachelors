"""
Test Suite for JsonFileStorage - Demonstrating pytest and tmp_path fixture

This test file demonstrates how to write unit tests for file-based storage
using pytest's built-in fixtures. The tmp_path fixture is particularly useful
for testing file operations because it provides a temporary directory that's
automatically cleaned up after each test.

Key Concepts:
- pytest: A popular Python testing framework
- Fixtures: Reusable test components (like tmp_path)
- tmp_path: A pytest fixture that provides a temporary directory
- Test isolation: Each test runs independently with its own temporary files
- Assertions: Statements that verify expected behavior

To run these tests (from todo-app-example, after uv sync):
    uv run pytest test_storage.py -v

Quiet output (as in the lecture slides):
    uv run pytest test_storage.py -q

To run with print output:
    uv run pytest test_storage.py -v -s

To run a specific test:
    uv run pytest test_storage.py::test_save_tasks_to_file -v
"""

import json

from todo.storage import JsonFileStorage


# ============================================================================
# Understanding pytest fixtures
# ============================================================================
# 
# A "fixture" is a function that pytest calls before running a test.
# The tmp_path fixture is built into pytest and provides a Path object
# pointing to a temporary directory. This directory is:
# - Created fresh for each test
# - Automatically deleted after the test completes
# - Unique for each test (tests don't interfere with each other)
#
# To use a fixture, simply include it as a parameter in your test function.
# pytest automatically finds and provides the fixture value.
# ============================================================================


def test_save_tasks_to_file(tmp_path):
    """
    Test that we can save tasks to a JSON file.
    
    This test verifies the basic save functionality:
    1. Create a JsonFileStorage pointing to a temporary file
    2. Save some task data
    3. Verify the file was created
    4. Verify the file contains the correct data
    
    Args:
        tmp_path: pytest fixture providing a temporary directory (Path object)
    """
    # Create a path to a temporary JSON file in the temporary directory
    # tmp_path is a Path object (from pathlib), so we can use / to join paths
    test_file = tmp_path / "test_tasks.json"
    
    # Create a JsonFileStorage instance pointing to our temporary file
    storage = JsonFileStorage(str(test_file))
    
    # Prepare some test data (tasks as dictionaries)
    test_tasks = [
        {"description": "Learn Python", "completed": False},
        {"description": "Write tests", "completed": True}
    ]
    
    # Save the tasks
    storage.save(test_tasks)
    
    # Verify the file was created
    # assert statements check if a condition is True
    # If False, the test fails with an error message
    assert test_file.exists(), "File should be created after saving"
    
    # Read the file and verify its contents
    with open(test_file, encoding="utf-8") as f:
        loaded_data = json.load(f)
    
    # Verify the data matches what we saved
    assert loaded_data == test_tasks, "Saved data should match original data"
    assert len(loaded_data) == 2, "Should have saved 2 tasks"


def test_load_tasks_from_existing_file(tmp_path):
    """
    Test that we can load tasks from an existing JSON file.
    
    This test verifies the load functionality:
    1. Create a JSON file with some data
    2. Create a JsonFileStorage pointing to that file
    3. Load the data
    4. Verify the loaded data matches what we wrote
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    # Create a temporary file path
    test_file = tmp_path / "test_tasks.json"
    
    # Manually create a JSON file with test data
    # This simulates a file that already exists
    test_tasks = [
        {"description": "Existing task 1", "completed": False},
        {"description": "Existing task 2", "completed": True}
    ]
    
    # Write the file manually (simulating a previous save)
    with open(test_file, "w", encoding="utf-8") as f:
        json.dump(test_tasks, f, indent=2)
    
    # Create storage and load from the file
    storage = JsonFileStorage(str(test_file))
    loaded_tasks = storage.load()
    
    # Verify the loaded data matches
    assert loaded_tasks == test_tasks, "Loaded data should match file contents"
    assert len(loaded_tasks) == 2, "Should have loaded 2 tasks"


def test_load_from_nonexistent_file(tmp_path):
    """
    Test that loading from a non-existent file returns an empty list.
    
    This tests error handling - when a file doesn't exist yet,
    JsonFileStorage should gracefully return an empty list rather than
    crashing. This is important for the first time a user runs the app.
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    # Create a path to a file that doesn't exist
    non_existent_file = tmp_path / "nonexistent.json"
    
    # Verify the file doesn't exist
    assert not non_existent_file.exists(), "File should not exist yet"
    
    # Create storage pointing to the non-existent file
    storage = JsonFileStorage(str(non_existent_file))
    
    # Load should return an empty list, not crash
    loaded_tasks = storage.load()
    
    # Verify we got an empty list
    assert loaded_tasks == [], "Loading from non-existent file should return empty list"
    assert isinstance(loaded_tasks, list), "Should return a list, not None"


def test_save_and_load_round_trip(tmp_path):
    """
    Test saving and then loading (round-trip test).
    
    This is an important test that verifies data integrity:
    - Save some data
    - Load it back
    - Verify it's exactly the same
    
    This ensures our save/load cycle preserves data correctly.
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    test_file = tmp_path / "round_trip_test.json"
    storage = JsonFileStorage(str(test_file))
    
    # Original data
    original_tasks = [
        {"description": "Task 1", "completed": False},
        {"description": "Task 2", "completed": True},
        {"description": "Task 3", "completed": False}
    ]
    
    # Save it
    storage.save(original_tasks)
    
    # Load it back
    loaded_tasks = storage.load()
    
    # Verify it's identical
    assert loaded_tasks == original_tasks, "Round-trip should preserve data exactly"
    assert len(loaded_tasks) == 3, "Should preserve all tasks"
    
    # Verify individual task properties
    assert loaded_tasks[0]["description"] == "Task 1"
    assert not loaded_tasks[0]["completed"]
    assert loaded_tasks[1]["completed"]


def test_save_empty_list(tmp_path):
    """
    Test that we can save and load an empty list.
    
    This tests edge cases - what happens when there are no tasks?
    The storage should handle this gracefully.
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    test_file = tmp_path / "empty_test.json"
    storage = JsonFileStorage(str(test_file))
    
    # Save an empty list
    storage.save([])
    
    # Verify file was created
    assert test_file.exists(), "File should exist even with empty data"
    
    # Load it back
    loaded_tasks = storage.load()
    
    # Verify it's an empty list
    assert loaded_tasks == [], "Should load empty list correctly"
    assert len(loaded_tasks) == 0, "Should have zero tasks"


def test_save_overwrites_existing_file(tmp_path):
    """
    Test that saving overwrites existing file contents.
    
    This verifies that save() replaces the file contents,
    not appends to them. This is important for data consistency.
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    test_file = tmp_path / "overwrite_test.json"
    storage = JsonFileStorage(str(test_file))
    
    # Save initial data
    initial_tasks = [
        {"description": "Old task 1", "completed": False},
        {"description": "Old task 2", "completed": False}
    ]
    storage.save(initial_tasks)
    
    # Save different data (should overwrite)
    new_tasks = [
        {"description": "New task", "completed": True}
    ]
    storage.save(new_tasks)
    
    # Load and verify only new data is present
    loaded_tasks = storage.load()
    assert loaded_tasks == new_tasks, "Save should overwrite, not append"
    assert len(loaded_tasks) == 1, "Should have only the new task"
    assert "Old task 1" not in [t["description"] for t in loaded_tasks]


def test_load_handles_corrupted_json(tmp_path, capsys):
    """
    Test that loading handles corrupted/invalid JSON gracefully.
    
    This tests error handling - if the JSON file is corrupted,
    JsonFileStorage should return an empty list and print an error,
    rather than crashing the application.
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
        capsys: pytest fixture for capturing print output
    """
    test_file = tmp_path / "corrupted.json"
    storage = JsonFileStorage(str(test_file))
    
    # Write invalid JSON to the file
    with open(test_file, "w", encoding="utf-8") as f:
        f.write("This is not valid JSON!!! {invalid syntax}")
    
    # Load should handle the error gracefully
    loaded_tasks = storage.load()
    
    # Should return empty list, not crash
    assert loaded_tasks == [], "Should return empty list for corrupted JSON"
    
    # Verify an error message was printed
    # capsys.readouterr() captures stdout/stderr
    captured = capsys.readouterr()
    assert "Error loading" in captured.out or "Error loading" in captured.err


def test_load_handles_non_list_json(tmp_path):
    """
    Test that loading handles JSON that's not a list.
    
    JsonFileStorage expects a list, but if the file contains
    something else (like a dict or string), it should handle it gracefully.
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    test_file = tmp_path / "non_list.json"
    storage = JsonFileStorage(str(test_file))
    
    # Write a dictionary instead of a list
    with open(test_file, "w", encoding="utf-8") as f:
        json.dump({"not": "a list"}, f)
    
    # Load should return empty list (as per implementation)
    loaded_tasks = storage.load()
    assert loaded_tasks == [], "Should return empty list for non-list JSON"


def test_storage_preserves_file_path(tmp_path):
    """
    Test that JsonFileStorage correctly stores the file path.
    
    This verifies that the file_path is stored as an instance variable
    and can be accessed later if needed.
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    test_file = tmp_path / "path_test.json"
    file_path_str = str(test_file)
    
    storage = JsonFileStorage(file_path_str)
    
    # Verify the path is stored correctly
    assert storage.file_path == file_path_str, "Should store the file path"


def test_multiple_storage_instances_independent(tmp_path):
    """
    Test that multiple JsonFileStorage instances are independent.
    
    This verifies that creating multiple storage instances with different
    file paths doesn't cause interference between them.
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    # Create two separate storage instances with different files
    file1 = tmp_path / "storage1.json"
    file2 = tmp_path / "storage2.json"
    
    storage1 = JsonFileStorage(str(file1))
    storage2 = JsonFileStorage(str(file2))
    
    # Save different data to each
    storage1.save([{"description": "Storage 1 task", "completed": False}])
    storage2.save([{"description": "Storage 2 task", "completed": True}])
    
    # Verify they're independent
    tasks1 = storage1.load()
    tasks2 = storage2.load()
    
    assert len(tasks1) == 1, "Storage 1 should have 1 task"
    assert len(tasks2) == 1, "Storage 2 should have 1 task"
    assert tasks1[0]["description"] == "Storage 1 task"
    assert tasks2[0]["description"] == "Storage 2 task"
    assert tasks1 != tasks2, "Storages should be independent"


def test_json_formatting_preserved(tmp_path):
    """
    Test that JSON formatting (indentation) is preserved.
    
    While not critical for functionality, this verifies that
    the JSON is saved with proper formatting (indent=2).
    
    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    test_file = tmp_path / "formatting_test.json"
    storage = JsonFileStorage(str(test_file))
    
    test_tasks = [
        {"description": "Task with formatting", "completed": False}
    ]
    
    storage.save(test_tasks)
    
    # Read the raw file content
    with open(test_file, encoding="utf-8") as f:
        raw_content = f.read()
    
    # Verify it has indentation (formatted JSON)
    # Formatted JSON should have newlines and spaces
    assert '\n' in raw_content, "JSON should be formatted with newlines"
    assert '  ' in raw_content, "JSON should be indented (2 spaces)"


# ============================================================================
# Test Organization Best Practices
# ============================================================================
#
# 1. Each test function should test ONE thing
# 2. Test names should be descriptive (test_what_we_are_testing)
# 3. Use fixtures (like tmp_path) to set up test data
# 4. Use assertions to verify expected behavior
# 5. Test both happy paths and error cases
# 6. Keep tests independent (each test should work in isolation)
#
# Running Tests (from todo-app-example, after uv sync):
#   uv run pytest test_storage.py
#   uv run pytest test_storage.py -v
#   uv run pytest test_storage.py -s
#   uv run pytest test_storage.py::test_save_tasks_to_file -v
#   uv run pytest test_storage.py -k "load"
# ============================================================================

