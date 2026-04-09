"""
This file represents the "test code" for the TDD demo.
We will add tests here *first* to watch them fail.
"""

import pytest
from tdd_demo_step1_tasks import Task

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
