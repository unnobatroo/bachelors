# Generics in Java

Java Generics are a powerful feature that allows you to write code that is "type-agnostic." Instead of locking a class into one specific data type (like `String`), you use a placeholder. This ensures **Type safety**—the compiler will catch errors if you try to put a `Dog` into a list of `Cats` before you ever run the program.

## Type parameters

While you can use any letter, these are the industry standards:

* **`E`**: Element (used extensively by the Java Collections Framework).
* **`T`**: Type (the general-purpose standby).
* **`K`**: Key (used in Maps).
* **`V`**: Value (used in Maps).
* **`S, U, V`**: Used if you have second, third, or fourth types.

### Generic classes

You define a generic class by adding `<T>` after the class name.

```java
// T is a placeholder for any Object type
public class Box<T> {
    private T content;

    public void set(T content) { this.content = content; }
    public T get() { return content; }
}

// You "fill in" the placeholder during instantiation
Box<String> stringBox = new Box<>();
stringBox.set("Hello Generics!");
```

### Generic methods

You can make a single method generic, even if the rest of the class is not. The type parameter goes **before** the return type.

```java
public class Utils {
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
}
```

### Type erasure

Java performs a trick called **Type Erasure**. During compilation, it replaces all generic types with their "bound" (usually `Object`). This means the bytecode doesn't actually know about `<String>` or `<Integer>`, which maintains compatibility with older versions of Java.

## Wildcards (`?`)

Sometimes you want a method to accept a list of *any* type, or a list of types that inherit from a specific class.

* **`List<?>`**: A list of absolutely anything (Unknown).
* **`List<? extends Number>`**: Upper Bounded. Accepts `Integer`, `Double`, etc.
* **`List<? super Integer>`**: Lower Bounded. Accepts `Integer`, `Number`, or `Object`.

### Type parameters vs. wildcards

The main difference lies in naming and scope. A type parameter gives the type a name (T) so you can refer to it multiple times, while a wildcard is an anonymous "placeholder" usually used for flexibility in method arguments.

| Feature | `<T extends Number>` | `<? extends Number>` |
|---|---|---|
| Primary use | Defining a class or method signature. | Using a generic type in a variable or argument. |
| Naming | **Named.** You can use `T` as a variable or return type. | **Anonymous.** You don't have a name for the type. |
| Flexibility | **Higher.** Allows you to enforce relationships between multiple arguments. | **Lower.** Great for "reading" data, but restricts "writing." |