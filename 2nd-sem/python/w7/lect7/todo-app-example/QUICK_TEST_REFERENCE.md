# Quick Test Reference

## Setup (Lecture 7 / uv)

From the repo root:

```bash
cd src/todo-app-example
uv sync
```

First-time equivalent when authoring a project: `uv add --dev pytest`.

## Running Tests

Use `uv run` so pytest runs in this project’s virtual environment:

```bash
# Run all tests in this file
uv run pytest test_storage.py

# Quiet (matches slide examples)
uv run pytest test_storage.py -q

# Verbose output (recommended while learning)
uv run pytest test_storage.py -v

# Show print statements
uv run pytest test_storage.py -s

# Run specific test
uv run pytest test_storage.py::test_save_tasks_to_file -v

# Run tests matching pattern
uv run pytest test_storage.py -k "load" -v
```

## What is `tmp_path`?

`tmp_path` is a pytest fixture that provides a temporary directory:

```python
def test_example(tmp_path):
    # tmp_path is a Path object to a temporary directory
    test_file = tmp_path / "my_file.json"

    # Use it for testing
    storage = JsonFileStorage(str(test_file))
    storage.save([{"description": "Test", "completed": False}])

    # pytest automatically cleans up after the test!
```

**Benefits:**

- ✅ Each test gets a fresh, clean directory
- ✅ No manual cleanup needed
- ✅ Tests don't interfere with each other
- ✅ Safe - won't touch real files

## Test Coverage

The test suite includes 11 tests covering:

1. Saving tasks to file
2. Loading tasks from file
3. Loading from non-existent file
4. Round-trip (save then load)
5. Empty list handling
6. File overwriting
7. Corrupted JSON handling
8. Non-list JSON handling
9. File path storage
10. Multiple storage independence
11. JSON formatting

## Expected Output

When all tests pass:

```
test_storage.py::test_save_tasks_to_file PASSED
test_storage.py::test_load_tasks_from_existing_file PASSED
test_storage.py::test_load_from_nonexistent_file PASSED
...
========== 11 passed in 0.05s ==========
```

For more details, see [TESTING_GUIDE.md](TESTING_GUIDE.md).
