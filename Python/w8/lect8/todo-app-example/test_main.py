import pytest
from fastapi.testclient import TestClient

from main import app, get_manager
from task_manager import TaskManager


@pytest.fixture
def client():
    """Each test gets its own TaskManager; production singleton is not used."""
    test_manager = TaskManager()
    app.dependency_overrides[get_manager] = lambda: test_manager
    with TestClient(app) as test_client:
        yield test_client
    app.dependency_overrides.clear()


def test_read_root(client):
    """Test the root GET / endpoint."""
    response = client.get("/")
    assert response.status_code == 200
    assert response.json() == {"message": "Welcome to the To-Do API"}


def test_create_task(client):
    task_data = {"description": "Test Task"}
    response = client.post("/tasks", json=task_data)
    assert response.status_code == 201
    data = response.json()
    assert data["description"] == "Test Task"
    assert data["completed"] is False
    assert data["id"] == 1


def test_get_all_tasks_empty(client):
    """Test GET /tasks when no tasks exist."""
    response = client.get("/tasks")
    assert response.status_code == 200
    assert response.json() == []  # Should be an empty list


def test_get_all_tasks_with_data(client):
    client.post("/tasks", json={"description": "Task 1"})
    client.post("/tasks", json={"description": "Task 2"})
    response = client.get("/tasks")
    assert response.status_code == 200
    data = response.json()
    assert len(data) == 2
    assert data[0]["description"] == "Task 1"
    assert data[1]["description"] == "Task 2"


def test_get_task_by_id(client):
    post_response = client.post("/tasks", json={"description": "A specific task"})
    task_id = post_response.json()["id"]
    response = client.get(f"/tasks/{task_id}")
    assert response.status_code == 200
    data = response.json()
    assert data["id"] == task_id
    assert data["description"] == "A specific task"


def test_get_task_by_id_not_found(client):
    response = client.get("/tasks/999")
    assert response.status_code == 404
    assert response.json() == {"detail": "Task not found"}


def test_create_task_bad_data(client):
    response = client.post("/tasks", json={"description": 123})
    assert response.status_code == 422
