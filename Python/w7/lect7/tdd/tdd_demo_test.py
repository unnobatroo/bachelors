"""
This file represents the "test code" for the TDD demo.
We will add tests here *first* to watch them fail.
"""

import pytest
from tdd_demo_tasks import Task

#
# STEP 1: [RED]
# Write this test first.
# Run `pytest`. It will fail with:
# TypeError: Task.__init__() got an unexpected keyword argument 'priority'
#
def test_task_creation_with_priority():
    """Tests that a task can be created with a priority."""
    task = Task("Buy milk", priority=1)
    assert task.description == "Buy milk"
    assert task.status == "pending"
    assert task.priority == 1

#
# STEP 3: [REFACTOR]
# After making the first test pass, we decide to refactor.
# Our refactor goal: Add a default priority and validation.
# We must add new tests *first* to support this refactor.
#

def test_task_creation_default_priority():
    """
    [RED]
    This test will fail until we change the __init__
    default from 'priority=None' to 'priority=3'.
    """
    task = Task("Do laundry")
    assert task.priority == 3

def test_task_creation_invalid_priority_low():
    """
    [RED]
    This test will fail until we add validation
    in the __init__ method.
    """
    with pytest.raises(ValueError, match="Priority must be an integer between 1 and 5"):
        Task("Mow lawn", priority=0)

def test_task_creation_invalid_priority_high():
    """
    [RED]
    This test will fail until we add validation
    in the __init__ method.
    """
    with pytest.raises(ValueError, match="Priority must be an integer between 1 and 5"):
        Task("Call mom", priority=6)

def test_task_creation_invalid_priority_type():
    """
    [RED]
    This test will fail until we add validation
    in the __init__ method.
    """
    with pytest.raises(ValueError, match="Priority must be an integer between 1 and 5"):
        Task("Clean room", priority="high")

#
# This test is for a new feature, added *after* the
# priority feature is fully refactored.
#
def test_task_complete_method():
    """
    [RED]
    A new TDD cycle. This test will fail until
    we create the `complete` method.
    """
    task = Task("Write tests")
    assert task.status == "pending"
    
    # Call the method that doesn't exist yet
    task.complete()
    
    assert task.status == "complete"