## Inheritance and subclassing

**Subclassing** is the act of creating a "child" class that inherits traits from a "parent" (superclass). This
represents an **"is-a"** relationship.

* **Superclass (parent):** The general category (e.g., `Animal`).
* **Subclass (child):** The specific type (e.g., `Dog`).

| Feature  | Abstract Class `extends`                              | Interface `implements`                           |
|:---------|:------------------------------------------------------|:-------------------------------------------------|
| Identity | High-level "Type", `Animal`.                          | A "Skill", `Swimmable`.                          |
| State    | Can have variables (fields) like `int health`.        | Only constants, usually.                         |
| Methods  | Can have fully written methods **AND** abstract ones. | Mostly abstract, though `default` methods exist. |
| Quantity | You can only extend **one** class.                    | You can implement **many** interfaces.           |

```java
// Abstract Class (The Core identity)
abstract class Animal {
    int age;

    void breathe() {
        System.out.println("Inhaling...");
    }
}

// Interfaces (The Skills)
interface Pet {
    void play();
}

interface Guard {
    void alert();
}

// A class can do it all!
class Labrador extends Animal implements Pet, Guard {
    public void play() {
        System.out.println("Chasing ball");
    }

    public void alert() {
        System.out.println("Barking at mailman");
    }
}
```