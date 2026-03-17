# Lecture 6: OOP, Packages, and Project Structure — Study Notes

## 1. Object-Oriented Programming (OOP) Foundations
Object-Oriented Programming is a paradigm where **data and behavior live together**. While procedural programming focuses on a linear sequence of steps (like a recipe), OOP focuses on modular reuse by encapsulating data and methods into "Kitchen Stations" or objects.

### Class vs. Object
* **Class (The Blueprint):** A reusable definition or template that describes what data and behavior instances should have.
* **Object (The Instance):** A concrete instance created from a class that stores actual values (e.g., a specific dog's name or age).
* **Analogy:** A cookie cutter is the **class**; the individual cookies produced are the **objects**.

### Anatomy of a Python Class
A standard class consists of four core pieces:
1.  **`class` keyword:** Creates the new type.
2.  **`__init__` method:** The constructor that runs when an object is created.
3.  **Attributes:** Variables (like `self.name`) that store the object's state.
4.  **Methods:** Functions defined inside the class that represent behaviors.

> **Note on `self`:** It is not a reserved keyword like `if`, but a convention used to refer to the current instance of the object.

## 2. Relationships: Composition vs. Inheritance
To build complex systems, objects must collaborate using specific patterns.

### Composition ("has-a")
Composition involves separate classes that cooperate by storing references to one another.
* **`has-a`**: One object keeps a reference to another (e.g., a Person has a Pet).
* **`has-many`**: One object manages a collection of others (e.g., a Shelter has many Dogs).

### Inheritance ("is-a")
Inheritance allows a **child class** to reuse and specialize the behavior of a **parent class**.
* **Relationship:** Must be a true "is-a" relationship (e.g., a Dog *is an* Animal).
* **`super()`**: Used by the child to reuse the parent's setup logic.
* **Specialization:** A child can keep parent behavior untouched, **override** it (replace it), **extend** it (add to it), or add entirely new methods.

## 3. Modules, Packages, and Environments
Organization is key to making Python projects reproducible and navigable.

### Modules and Packages
* **Module:** Any `.py` file. It acts as a namespace to reduce name collisions.
* **Package:** A folder containing related modules, typically marked by an `__init__.py` file.
* **Entry Point Guard:** Using `if __name__ == "__main__":` ensures that code only runs when the file is executed directly, not when it is imported as a module.

### Dependency Management
* **`pip` & `requirements.txt`:** The classic workflow for installing and recording third-party packages.
* **Virtual Environments (`venv`):** Isolate project-specific packages to prevent version collisions between different projects.
* **Modern Tools:** * **`uv`:** Recommended for pure Python projects; handles environments and syncing quickly.
    * **`pixi`:** Used when projects require non-PyPI or system-level dependencies.

## 4. Type Hints, Style, and Documentation
These tools improve communication between humans, editors, and static checkers.

* **Type Hints:** Specify expected data types for parameters and return values (e.g., `def func(name: str) -> None:`). They do **not** make Python "strictly typed" at runtime but help tools catch bugs early.
* **PEP 8:** The official style guide. Key rules include 4-space indentation, `snake_case` for functions/variables, and `PascalCase` for classes.
* **Docstrings:** Strings placed as the first line in a function or class to describe its public contract. They are accessible via `help()`.

## 5. Command-Line Applications (CLI)
* **`sys.argv`:** A low-level list containing command-line arguments. `sys.argv[0]` is always the script name.
* **`argparse`:** A more powerful library that provides automatic help output, type conversion, and validation rules (like `choices=`).

### To-Do App Case Study (Architecture)
1.  **Model (`Task`):** Represents the core noun (the task) and its state (completed or not).
2.  **Manager (`TodoManager`):** Centralizes business logic, such as managing the collection of tasks and saving/loading JSON data.
3.  **CLI (`todo.cli`):** The "front door" that parses user input and communicates with the Manager.