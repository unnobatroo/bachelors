# Design patterns: State

The **State Design Pattern** is a behavioral pattern that allows an object to alter its behavior when its internal state changes. The object will appear to change its class.

## Problem

When an object's behavior depends on its state (e.g., a Vending Machine, a Traffic Light, or a Game Character), we often use massive `if-else` or `switch` blocks. This leads to code that is:

- **Rigid:** Hard to add new states.
- **Complex:** High "Cyclomatic Complexity."
- **Fragile:** Changing one state might break logic in another.

## Solution

Move state-specific behaviors into separate **State Classes**. The main object (the **Context**) delegates work to the current state object.

### Structural components

| Component | Role |
| :--- | :--- |
| **Context** | The "Brain" (e.g., `TrafficLight`). Holds a reference to the current state. |
| **State Interface** | The "Contract" (e.g., `TrafficState`). Defines what every state must do. |
| **Concrete States** | The "Logic" (e.g., `RedState`, `GreenState`). Implements specific behavior. |

## Example: traffic light

### Contract (interface)

```java
interface TrafficState {
    void handle(TrafficLight light);
}
```

### Concrete states

```java
class RedState implements TrafficState {
    public void handle(TrafficLight light) {
        System.out.println("RED: Stop!");
        light.setState(new GreenState()); // Logic for next transition
    }
}

class GreenState implements TrafficState {
    public void handle(TrafficLight light) {
        System.out.println("GREEN: Go!");
        light.setState(new YellowState());
    }
}
```

### Context

```java
class TrafficLight {
    private TrafficState currentState;

    public TrafficLight() {
        this.currentState = new RedState(); // Initial state
    }

    public void setState(TrafficState state) {
        this.currentState = state;
    }

    public void change() {
        currentState.handle(this); // Polymorphism: No if-else needed!
    }
}
```

## Interfaces vs. abstract classes

A common question is: **"Why use an Interface instead of an Abstract Class for the State?"**

### Why we choose interfaces

1. **Multiple Inheritance:** Java classes can only `extend` one class, but can `implement` many interfaces. Using an interface keeps the State classes "free" to inherit from other families (like a `Timer` or `Logger`).
2. **Decoupling:** Interfaces provide the "thinnest" possible connection. It ensures that the states are truly independent units.
3. **Composition over Inheritance:** The State Pattern relies on *composition* (The Light **has a** State). Interfaces are the cleanest way to enforce this without forcing a "family DNA" on the states.

### When to use an abstract class instead

- Use an **Abstract Class** only if all your states share a lot of **duplicate code** or need to share **private variables** (like `int duration`).
