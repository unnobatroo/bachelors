# Testing Guide - JsonFileStorage with pytest

## Overview

This guide explains how to test `JsonFileStorage` using pytest and the `tmp_path` fixture. The tests demonstrate best practices for testing file-based operations in Python.

Use **[uv](https://docs.astral.sh/uv/)** for the environment and **`uv run pytest`** to execute tests.

## Prerequisites

### Environment and pytest

From the repository root, go to this example project:

```bash
cd src/todo-app-example
```

Install dependencies from the lock file (creates or updates `.venv` and installs dev tools including pytest):

```bash
uv sync
```

If you are setting up a fresh project yourself, the equivalent step is:

```bash
uv add --dev pytest
```

After `uv sync`, run every pytest command through **`uv run`** so it uses this project’s virtual environment.

## Understanding pytest Fixtures

### What is a Fixture?

A **fixture** is a function that pytest calls before running a test. Fixtures provide reusable setup code that multiple tests can use.

### The `tmp_path` Fixture

`tmp_path` is a built-in pytest fixture that provides a `Path` object pointing to a temporary directory. This directory:

- ✅ Is created fresh for each test
- ✅ Is automatically deleted after the test completes
- ✅ Is unique for each test (tests don't interfere with each other)
- ✅ Works on all operating systems (Windows, macOS, Linux)

**Why use `tmp_path` instead of a real directory?**

1. **Isolation**: Each test gets its own clean directory
2. **No cleanup needed**: pytest automatically deletes temporary files
3. **No conflicts**: Tests can run in parallel without interfering
4. **Safety**: Won't accidentally delete or modify real files

### Example Usage

```python
def test_my_file_operation(tmp_path):
    # tmp_path is a Path object pointing to a temporary directory
    test_file = tmp_path / "my_file.txt"

    # Use the file for testing
    test_file.write_text("Hello, World!")

    # Verify the file exists
    assert test_file.exists()

    # pytest automatically cleans up after the test!
```

## Running the Tests

### Run All Tests

```bash
uv run pytest test_storage.py
```

### Quiet output (course default)

```bash
uv run pytest -q
```

or for this file only:

```bash
uv run pytest test_storage.py -q
```

### Run with Verbose Output

```bash
uv run pytest test_storage.py -v
```

The `-v` flag shows which tests passed or failed with more detail.

### Run with Print Statements Visible

```bash
uv run pytest test_storage.py -s
```

The `-s` flag shows `print()` statements (useful for debugging).

### Run a Specific Test

```bash
uv run pytest test_storage.py::test_save_tasks_to_file -v
```

### Run Tests Matching a Pattern

```bash
uv run pytest test_storage.py -k "load" -v
```

This runs all tests with "load" in their name.

### Run with Detailed Output

```bash
uv run pytest test_storage.py -vv
```

Double `-v` provides even more detailed output.

## Test Coverage

The test suite covers:

1. ✅ **Basic Save**: Saving tasks to a file
2. ✅ **Basic Load**: Loading tasks from an existing file
3. ✅ **Non-existent File**: Loading when file doesn't exist
4. ✅ **Round-trip**: Save then load (data integrity)
5. ✅ **Empty List**: Saving and loading empty lists
6. ✅ **Overwrite**: Saving overwrites existing data
7. ✅ **Error Handling**: Corrupted JSON files
8. ✅ **Data Validation**: Non-list JSON data
9. ✅ **Path Storage**: File path is stored correctly
10. ✅ **Independence**: Multiple storage instances don't interfere
11. ✅ **Formatting**: JSON is properly formatted

## Understanding the Tests

### Test Structure

Each test follows this pattern:

```python
def test_something(tmp_path):
    """
    Docstring explaining what this test verifies.

    Args:
        tmp_path: pytest fixture providing a temporary directory
    """
    # 1. Setup: Create test data and storage
    test_file = tmp_path / "test.json"
    storage = JsonFileStorage(str(test_file))

    # 2. Action: Perform the operation being tested
    storage.save([{"description": "Test", "completed": False}])

    # 3. Assert: Verify the expected outcome
    assert test_file.exists()
    assert storage.load() == [{"description": "Test", "completed": False}]
```

### Key Testing Concepts

#### 1. Arrange-Act-Assert (AAA) Pattern

- **Arrange**: Set up test data and objects
- **Act**: Perform the operation being tested
- **Assert**: Verify the results

#### 2. Test Isolation

Each test is independent:

- Uses its own temporary directory
- Doesn't depend on other tests
- Can run in any order

#### 3. Assertions

Assertions verify expected behavior:

```python
assert condition, "Error message if condition is False"
```

If an assertion fails, the test fails with the error message.

#### 4. Testing Edge Cases

Good tests cover:

- **Happy path**: Normal, expected usage
- **Edge cases**: Empty data, missing files, etc.
- **Error cases**: Invalid data, corrupted files, etc.

## Common pytest Commands

All of these assume you run them from `todo-app-example` with `uv run` so pytest comes from the project environment.

| Command | Description |
|---------|-------------|
| `uv run pytest` | Run all tests pytest discovers in the current directory |
| `uv run pytest test_storage.py` | Run tests in a specific file |
| `uv run pytest -q` | Quiet output |
| `uv run pytest -v` | Verbose output |
| `uv run pytest -s` | Show print statements |
| `uv run pytest -k "pattern"` | Run tests matching pattern |
| `uv run pytest --tb=short` | Shorter traceback format |
| `uv run pytest --tb=no` | No traceback (just pass/fail) |
| `uv run pytest -x` | Stop on first failure |
| `uv run pytest --maxfail=3` | Stop after 3 failures |

## Understanding Test Output

### Passing Test

```
test_storage.py::test_save_tasks_to_file PASSED
```

### Failing Test

```
test_storage.py::test_save_tasks_to_file FAILED
...
AssertionError: File should be created after saving
```

The output shows:

- Which test failed
- The assertion that failed
- The error message
- A traceback showing where the failure occurred

## Best Practices

1. **One test, one thing**: Each test should verify one specific behavior
2. **Descriptive names**: Test names should clearly describe what they test
3. **Use fixtures**: Don't duplicate setup code; use fixtures
4. **Test edge cases**: Don't just test the happy path
5. **Keep tests fast**: Use temporary files, not real filesystem
6. **Isolate tests**: Tests shouldn't depend on each other
7. **Clear assertions**: Use descriptive error messages in assertions

## Troubleshooting

### "No module named pytest"

Run `uv sync` from `todo-app-example` (same directory as `pyproject.toml`). Always invoke tests with `uv run pytest`, not a global `pytest` from outside the venv.

### Tests pass but you expected them to fail

Check that you're testing the right thing. Sometimes tests pass because the code works, not because the test is wrong!

### "Permission denied" errors

This shouldn't happen with `tmp_path`, but if it does, check file permissions in your temporary directory.

### Tests are slow

If tests are slow, make sure you're using `tmp_path` and not real files. Also check for unnecessary file I/O operations.

## Next Steps

After understanding these tests, you can:

1. **Add more tests**: Test additional edge cases
2. **Test other classes**: Write tests for `MemoryStorage` and `TodoManager`
3. **Use fixtures**: Create custom fixtures for common test data
4. **Test coverage**: Add `pytest-cov` and measure coverage, for example:

   ```bash
   uv add --dev pytest-cov
   uv run pytest --cov=todo --cov-report=term-missing
   ```

5. **Parametrize tests**: Use `@pytest.mark.parametrize` for testing multiple inputs

### Quality loop (later in the lecture)

The slides also mention a daily loop: `uv sync`, then `uv run ruff check .`, `uv run pyright`, and `uv run pytest -q`. You can add **ruff** and **pyright** as dev dependencies to this project when you reach that part of the course.

## Additional Resources

- [pytest Documentation](https://docs.pytest.org/)
- [pytest Fixtures](https://docs.pytest.org/en/stable/fixture.html)
- [Python pathlib](https://docs.python.org/3/library/pathlib.html)
- [Testing Best Practices](https://docs.pytest.org/en/stable/goodpractices.html)
- [uv documentation](https://docs.astral.sh/uv/)
