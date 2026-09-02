# Test Code (test_tasks.py):
# Import the function we want to test
from tasks import add

def test_add_positive_numbers():
    """This test checks adding two positive numbers."""
    result = add(2, 3)
    assert result == 5

def test_add_negative_numbers():
    """This test checks adding two negative numbers."""
    assert add(-1, -1) == -2

def test_add_mixed_numbers():
    assert add(5, -2) == 3