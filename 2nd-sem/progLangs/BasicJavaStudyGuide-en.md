# Comprehensive Basic Java Study Guide

*A complete reference for Java programming fundamentals, from theory to practice*

## Table of Contents

1. [Introduction](#introduction)
2. [Getting Started](#getting-started)
3. [Java Language Fundamentals](#java-language-fundamentals)
4. [Data Types & Variables](#data-types--variables)
5. [Operators & Expressions](#operators--expressions)
6. [Control Flow](#control-flow)
7. [Methods & Functions](#methods--functions)
8. [Object-Oriented Programming](#object-oriented-programming)
9. [Visibility & Packages](#visibility--packages)
10. [Exception Handling](#exception-handling)
11. [Arrays & Collections](#arrays--collections)
12. [Generics & Type Parameters](#generics--type-parameters)
13. [Inheritance & Polymorphism](#inheritance--polymorphism)
14. [Interfaces & Abstract Classes](#interfaces--abstract-classes)
15. [Equals, Hashing & Ordering](#equals-hashing--ordering)
16. [File I/O & Resources](#file-io--resources)
17. [Standard Library Reference](#standard-library-reference)
18. [Testing with JUnit](#testing-with-junit)
19. [CheckThat Framework](#checkthat-framework)
20. [Design Patterns & Idioms](#design-patterns--idioms)
21. [Lab Guide: Practical Progression](#lab-guide-practical-progression)
22. [Compilation, Execution & Tools](#compilation-execution--tools)
23. [Common Errors & Debugging](#common-errors--debugging)
24. [Best Practices & Performance](#best-practices--performance)
25. [Quick Reference](#quick-reference)

---

## Introduction

This study guide covers Java programming fundamentals as taught in the Basic Java course. It combines:
- **Core Language Concepts**: How Java works, syntax, and semantics
- **Standard Library**: Essential APIs from java.lang, java.util, java.io
- **Object-Oriented Design**: Classes, objects, inheritance, interfaces, polymorphism
- **Testing & Quality**: JUnit testing frameworks and structure verification
- **Practical Applications**: Lab exercises and real-world patterns
- **Development Tools**: Compilation, execution, debugging, and common errors

**Target Audience**: Students new to Java, learning through a combination of theory and hands-on coding exercises.

**Prerequisites**: Basic programming experience (any language), understanding of algorithms and data structures.

**JDK Version**: Course baseline is Java 25. Students are expected to know both classic style (`public static void main(String[] args)`) and modern simple-program style (`void main()` with compact source files), including `IO` usage from `java.lang`.

---

## Getting Started

### Setting Up Your Environment

1. **Install JDK**: Download Java Development Kit (JDK) 25
2. **Verify Installation**: 
   ```bash
   javac -version
   java -version
   ```
3. **Text Editor/IDE**: Use VS Code, IntelliJ IDEA, or Eclipse
4. **Terminal**: Use PowerShell (Windows), Terminal (Mac), or bash (Linux)

### Your First Java Program

**Step 1**: Create a file named `HelloWorld.java`:
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

**Step 2**: Compile:
```bash
javac HelloWorld.java
```

**Step 3**: Run:
```bash
java HelloWorld
```

**Output**:
```
Hello, World!
```

### Key Concepts in First Program
- **`public class HelloWorld`**: Classes are blueprints for objects; `public` means accessible everywhere
- **`static void main(String[] args)`**: Entry point for any Java program
  - `static`: Method belongs to the class, not instances
  - `void`: Returns nothing
  - `String[] args`: Command-line arguments passed to the program
- **`System.out.println(...)`**: Prints text to console with newline

### Java 25 Simple Programs (Required)

In addition to classic class-based style, Java 25 course work also uses compact/simple source files.

**Example 1: `void main()` style**
```java
void main() {
        IO.println("Hello from Java 25 simple source style");
}
```

**Example 2: Input + output using `java.lang.IO`**
```java
void main() {
        IO.print("Name: ");
        String name = IO.readln();
        IO.println("Hello, " + name + "!");
}
```

Notes:
- `IO` is available from `java.lang`, so no import is needed for basic usage.
- Compact source files are excellent for short lab programs and fast experimentation.
- You still need classic class-based style for many graded tasks and structure tests.

### Compact Compilation Units / Source Files

In course terminology, a compact source file is a minimal Java source file intended for rapid iteration.

Typical usage:
- small exercises and experiments
- quick console input/output tasks
- one-file execution with `java FileName.java`

Practical expectation:
- Be able to switch between compact style and regular class-based style confidently.

---

## Java Language Fundamentals

### Keywords

Java has 53 reserved keywords that cannot be used as variable names:

**Declarations & Modifiers:**
- `class`, `enum`, `interface`, `abstract` - Define types
- `public`, `protected`, `private` - Access modifiers (visibility)
- `static`, `final` - Scope/mutability modifiers
- `extends`, `implements` - Inheritance
- `package`, `import` - Organization

**Control Flow:**
- `if`, `else`, `switch`, `case`, `default` - Conditionals
- `for`, `while`, `do` - Loops
- `break`, `continue`, `return`, `goto` - Flow control
- `try`, `catch`, `finally`, `throw`, `throws` - Exception handling

**Types & References:**
- `void` - No return value
- `boolean`, `char`, `byte`, `short`, `int`, `long`, `float`, `double` - Primitive types
- `new`, `this`, `super`, `instanceof` - Object operations
- `null` - Null reference

**Other:**
- `const` - Reserved but not used in modern Java
- `volatile`, `synchronized` - Concurrency (advanced)
- `transient` - Serialization (advanced)


### Comments

**Single-line comment:**
```java
// This is a comment and won't be executed
int x = 5; // Comment at end of line
```

**Multi-line comment:**
```java
/* This is a multi-line comment
   spanning multiple lines */
```

**Javadoc comment (generates documentation):**
```java
/**
 * Calculates the sum of two numbers.
 * 
 * @param a the first number
 * @param b the second number
 * @return the sum of a and b
 */
public static int sum(int a, int b) {
    return a + b;
}
```

### Literals

**Numeric Literals:**
- `int` (default for whole numbers): `42`, `0`, `-17`, `1_000_000` (underscores for readability)
- `long` (suffix `L`): `42L`, `1_000_000_000L`
- `float` (suffix `f` or `F`): `3.14f`, `1.0F`
- `double` (default for decimals): `3.14`, `1.0`, `1.0d` (suffix `d` optional)
- Hexadecimal (prefix `0x`): `0xFF`, `0xDEADBEEF`
- Octal (prefix `0`): `077` (equals 63)
- Binary (prefix `0b`): `0b1010` (equals 10)

**Boolean Literals:**
```java
boolean isTrue = true;
boolean isFalse = false;
```

**Character Literals (single character in single quotes):**
```java
char letter = 'A';
char digit = '5';
char space = ' ';
char newline = '\n';
char tab = '\t';
```

**String Literals (text in double quotes):**
```java
String message = "Hello, World!";
String empty = "";
String withEscape = "Line 1\nLine 2";
String path = "C:\\Users\\Documents";
```

**String Formatting (Java 15+):**
```java
int age = 25;
String name = "Alice";
String formatted = "My name is %s and I am %d years old.".formatted(name, age);
// Output: "My name is Alice and I am 25 years old."
```

**Null Reference:**
```java
String notInitialized = null;
int[] emptyArray = null;
Object obj = null;
```

---

## Data Types & Variables

### Primitive Types

Java has 8 primitive types (not objects), stored in memory with fixed sizes:

| Type | Size | Range | Default | Suffix/Prefix | Examples |
|------|------|-------|---------|---|---|
| `byte` | 1 byte | -128 to 127 | 0 | - | `42`, `0`, `-1` |
| `short` | 2 bytes | -32,768 to 32,767 | 0 | - | `1000`, `-500` |
| `int` | 4 bytes | -2Â³Â¹ to 2Â³Â¹-1 | 0 | - | `42`, `1_000_000` |
| `long` | 8 bytes | -2â¶Â³ to 2â¶Â³-1 | 0L | `L` | `42L`, `1_000_000_000L` |
| `float` | 4 bytes | IEEE 754 32-bit | 0.0f | `f` or `F` | `3.14f`, `1.0F` |
| `double` | 8 bytes | IEEE 754 64-bit | 0.0d | `d` (optional) | `3.14`, `1.0d` |
| `boolean` | 1 byte | true/false | false | - | `true`, `false` |
| `char` | 2 bytes | Unicode (0 to 65,535) | `'\u0000'` | - | `'A'`, `'5'`, `'\n'` |

**Type Casting Between Primitives:**
```java
// Widening (automatic, safe)
int number = 42;
long bigNumber = number;  // int â†’ long (no data loss)
double decimal = bigNumber;  // long â†’ double

// Narrowing (requires explicit cast, may lose data)
double value = 3.14;
int truncated = (int) value;  // double â†’ int (loses .14)
byte small = (byte) 300;  // int â†’ byte (wraps around: 44)
```

**Automatic Promotion in Operations:**
When operating on primitives of different types, Java promotes the weaker type:
```
boolean < byte < char < short < int < long < float < double
```

Example:
```java
int x = 5;
double y = 2.5;
double result = x + y;  // x promoted to double, result is 7.5
```

### Reference Types

Reference types are objects (heap-allocated), storing references (memory addresses) rather than values:

**Built-in Reference Types:**
- `String` - Text (immutable)
- `Object` - Parent of all classes
- Arrays - Collections of primitives or references
- User-defined classes and interfaces

**Declaration & Usage:**
```java
String name = "Alice";      // reference to String object
String emptyRef = null;     // reference to nothing
int[] numbers = new int[10];  // reference to array object

// Multiple references to same object
String original = "Hello";
String alias = original;    // Both point to same String
```

**Key Differences from Primitives:**
- Primitives: Value stored directly, comparison with `==` compares values
- References: Address stored, comparison with `==` compares addresses (identity)
- Primitives: Default to 0/false, never null
- References: Default to null

```java
String s1 = "Hello";
String s2 = "Hello";
System.out.println(s1 == s2);        // false (different objects)
System.out.println(s1.equals(s2));   // true (same content)
```

### Variable Declaration & Initialization

**Syntax:**
```java
Type variableName = initialValue;
```

**Examples:**
```java
int age = 25;
String name = "Bob";
boolean isActive = true;
double salary = 50000.50;
int[] scores = {95, 87, 92};
```

**Declaration Without Initialization:**
```java
int number;  // Declared but not initialized (local scope)
// number is unusable until assigned a value
number = 42;
```

**Default Values (fields only, not local variables):**
When a field is not explicitly initialized:
```java
public class Person {
    int age;           // Default: 0
    String name;       // Default: null
    boolean active;    // Default: false
    double salary;     // Default: 0.0
    int[] scores;      // Default: null
}
```

**Type Inference with `var` (Java 10+):**
```java
var number = 42;      // Inferred as int
var text = "Hello";   // Inferred as String
var list = new ArrayList<String>();  // Inferred as ArrayList<String>
// var x;  // Error: cannot infer type without initializer
```

### Variable Scope

**Local Variables** (declared in methods/blocks):
- Accessible only within that block
- Must be initialized before use
- Created when block is entered, destroyed when exited

```java
public void example() {
    int x = 5;  // Scope: entire method
    if (x > 0) {
        int y = 10;  // Scope: only this if block
        System.out.println(x + y);  // OK
    }
    System.out.println(y);  // Error: y not in scope
}
```

**Method Parameters:**
- Accessible throughout the method
- Initialized with passed arguments

```java
public void greet(String name, int age) {
    // name and age accessible throughout method
    System.out.println(name + " is " + age + " years old.");
}
```

**Fields (instance/class variables):**
- Accessible throughout the class
- Have default values if not initialized
- Instance fields: one per object
- Static fields: shared across all instances

```java
public class Person {
    String name;          // Instance field
    static int count = 0; // Class field
    
    public Person(String name) {
        this.name = name;
        count++;
    }
}
```

---

## Operators & Expressions

### Arithmetic Operators

**Binary operators** (operate on two values):
```java
int a = 10, b = 3;

int sum = a + b;        // 13 (addition)
int diff = a - b;       // 7 (subtraction)
int product = a * b;    // 30 (multiplication)
int quotient = a / b;   // 3 (integer division, truncates)
int remainder = a % b;  // 1 (modulo - remainder)

double precise = 10.0 / 3.0;  // 3.333... (float division)
```

**Unary operators** (operate on one value):
```java
int x = 5;
int neg = -x;           // -5 (negation)
int pos = +x;           // 5 (positive)

x++;                    // 6 (post-increment: return old value, then increment)
++x;                    // 7 (pre-increment: increment, then return new value)
x--;                    // 6 (post-decrement)
--x;                    // 5 (pre-decrement)
```

### Comparison Operators

Return `boolean` (true/false):
```java
int a = 5, b = 3;

a == b;  // false (equal to)
a != b;  // true (not equal to)
a > b;   // true (greater than)
a < b;   // false (less than)
a >= b;  // true (greater than or equal to)
a <= b;  // false (less than or equal to)
```

**Important**: Never use `==` for objects (compares reference):
```java
String s1 = new String("Hello");
String s2 = new String("Hello");
s1 == s2;        // false (different objects)
s1.equals(s2);   // true (same content)
```

### Logical Operators

**AND (`&&`)**: True only if both conditions are true
```java
boolean a = true, b = false;
a && b;  // false

if (age >= 18 && hasLicense) {
    canDrive = true;
}
```

**OR (`||`)**: True if at least one condition is true
```java
a || b;  // true

if (isStudent || isTeacher) {
    canAccessLibrary = true;
}
```

**NOT (`!`)**: Reverses boolean
```java
!a;  // false
!b;  // true

if (!hasError) {
    proceed();
}
```

**Short-circuit Evaluation:**
```java
// If left side of && is false, right side is never evaluated
if (array != null && array.length > 0) {  // Safe: no null pointer exception
}

// If left side of || is true, right side is never evaluated
if (isAdmin || hasPermission()) {  // hasPermission() not called if isAdmin is true
}
```

### Assignment Operators

```java
int x = 5;

x += 3;   // x = x + 3;    â†’ 8
x -= 2;   // x = x - 2;    â†’ 6
x *= 2;   // x = x * 2;    â†’ 12
x /= 3;   // x = x / 3;    â†’ 4
x %= 3;   // x = x % 3;    â†’ 1
```

### String Concatenation

**Using `+` operator:**
```java
String first = "Hello";
String second = "World";
String result = first + " " + second;  // "Hello World"

int age = 25;
String message = "I am " + age + " years old.";  // "I am 25 years old."
```

**Careful with operator precedence:**
```java
String result = "Sum: " + 5 + 3;  // "Sum: 53" (string concatenation)
String result = "Sum: " + (5 + 3); // "Sum: 8" (arithmetic first)
```

**Using `.format()` or `.formatted()`:**
```java
String message = String.format("Name: %s, Age: %d", "Alice", 30);
// "Name: Alice, Age: 30"

String message = "Name: %s, Age: %d".formatted("Alice", 30);  // Java 15+
```

### instanceof Operator

Checks if an object is an instance of a class or interface:
```java
Object obj = "Hello";
obj instanceof String;   // true
obj instanceof Integer;  // false

if (obj instanceof String) {
    String text = (String) obj;  // Safe cast
}
```

---

## Control Flow

### if-else Statements

**Basic if:**
```java
int age = 15;
if (age < 18) {
    System.out.println("You are a minor.");
}
```

**if-else:**
```java
if (age < 18) {
    System.out.println("Minor");
} else {
    System.out.println("Adult");
}
```

**if-else if-else:**
```java
if (score >= 90) {
    grade = 'A';
} else if (score >= 80) {
    grade = 'B';
} else if (score >= 70) {
    grade = 'C';
} else if (score >= 60) {
    grade = 'D';
} else {
    grade = 'F';
}
```

**Ternary operator (one-liner conditional):**
```java
String status = age >= 18 ? "Adult" : "Minor";
int max = a > b ? a : b;
```

### switch Statements

**Basic switch:**
```java
int day = 3;
String dayName;

switch (day) {
    case 1:
        dayName = "Monday";
        break;  // Must break to prevent fall-through
    case 2:
        dayName = "Tuesday";
        break;
    case 3:
        dayName = "Wednesday";
        break;
    default:
        dayName = "Unknown";
}
```

**Common mistake - forgetting break:**
```java
switch (day) {
    case 1:
        System.out.println("Monday");
        // Falls through to case 2!
    case 2:
        System.out.println("Tuesday");
        break;
}
// If day == 1, prints both "Monday" and "Tuesday"
```

**Switch with multiple cases (fall-through intended):**
```java
switch (month) {
    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
        daysInMonth = 31;
        break;
    case 4: case 6: case 9: case 11:
        daysInMonth = 30;
        break;
    case 2:
        daysInMonth = 28;  // Ignore leap years for simplicity
        break;
}
```

### Loops

**while loop** (repeats while condition is true):
```java
int count = 0;
while (count < 5) {
    System.out.println(count);
    count++;
}
// Output: 0 1 2 3 4
```

**do-while loop** (executes at least once, then repeats):
```java
int count = 0;
do {
    System.out.println(count);
    count++;
} while (count < 5);
// Output: 0 1 2 3 4 (same result)

int x = 10;
do {
    System.out.println(x);  // Prints 10 even though x > 5
} while (x < 5);
```

**for loop** (traditional, with initialization, condition, increment):
```java
for (int i = 0; i < 5; i++) {
    System.out.println(i);
}
// Output: 0 1 2 3 4

for (int i = 10; i >= 0; i -= 2) {
    System.out.println(i);
}
// Output: 10 8 6 4 2 0
```

**Enhanced for loop (for-each)** (iterates over collections):
```java
int[] numbers = {1, 2, 3, 4, 5};
for (int num : numbers) {
    System.out.println(num);
}

String[] names = {"Alice", "Bob", "Charlie"};
for (String name : names) {
    System.out.println(name);
}

List<String> fruits = Arrays.asList("apple", "banana", "cherry");
for (String fruit : fruits) {
    System.out.println(fruit);
}
```

### Loop Control Statements

**break** (exits the loop immediately):
```java
for (int i = 0; i < 10; i++) {
    if (i == 5) {
        break;  // Exit loop when i reaches 5
    }
    System.out.println(i);
}
// Output: 0 1 2 3 4
```

**continue** (skips to next iteration):
```java
for (int i = 0; i < 10; i++) {
    if (i % 2 == 0) {
        continue;  // Skip even numbers
    }
    System.out.println(i);
}
// Output: 1 3 5 7 9
```

**Labeled break/continue** (for nested loops):
```java
outerLoop:
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (i == 1 && j == 1) {
            break outerLoop;  // Break out of outer loop
        }
        System.out.println("i=" + i + ", j=" + j);
    }
}
```

---

## Methods & Functions

### Method Declaration

**Syntax:**
```java
[modifiers] returnType methodName(parameterType parameterName, ...) [throws ExceptionType, ...] {
    // Method body
    return value;  // if returnType is not void
}
```

**Example:**
```java
public static int add(int a, int b) {
    return a + b;
}

private double calculateTax(double income) throws IllegalArgumentException {
    if (income < 0) {
        throw new IllegalArgumentException("Income cannot be negative");
    }
    return income * 0.1;
}
```

### Instance Methods vs. Static Methods

**Instance Method** (operates on object instance):
```java
public class Person {
    String name;
    
    public Person(String name) {
        this.name = name;
    }
    
    // Instance method - has access to 'this'
    public String getName() {
        return this.name;
    }
    
    public void greet() {
        System.out.println("Hello, my name is " + name);
    }
}

Person p = new Person("Alice");
p.greet();  // "Hello, my name is Alice"
```

**Static Method** (belongs to class, not instances):
```java
public class Math {
    // Static method - no 'this'
    public static int max(int a, int b) {
        return a > b ? a : b;
    }
    
    public static double absoluteValue(double x) {
        return x < 0 ? -x : x;
    }
}

int result = Math.max(5, 3);  // Called on class, not instance
```

### Method Parameters

**Pass-by-Value (all Java parameters):**
```java
public void increment(int x) {
    x++;  // Changes local copy, not original
}

int number = 5;
increment(number);
System.out.println(number);  // Still 5
```

**Pass-by-Reference (for objects, reference is passed):**
```java
public void addToList(List<String> list, String item) {
    list.add(item);  // Modifies actual list object
}

List<String> myList = new ArrayList<>();
addToList(myList, "Alice");
System.out.println(myList.size());  // 1 (modified)
```

**Multiple Parameters:**
```java
public double calculateArea(double length, double width) {
    return length * width;
}

double area = calculateArea(5.0, 3.0);  // 15.0
```

**Varargs (Variable Arguments):**
```java
public int sum(int... numbers) {  // numbers is an int array
    int total = 0;
    for (int num : numbers) {
        total += num;
    }
    return total;
}

sum(1, 2, 3, 4, 5);  // 15
sum(10, 20);          // 30
sum();                // 0
```

### Return Types

**void (no return value):**
```java
public void printMessage(String msg) {
    System.out.println(msg);
    // No return statement needed
}
```

**Primitive return:**
```java
public int getAge() {
    return 25;
}

public double calculatePrice(int quantity, double unitPrice) {
    return quantity * unitPrice;
}
```

**Reference return:**
```java
public String getName() {
    return "Alice";
}

public List<String> getNames() {
    return new ArrayList<String>();
}
```

**Null return:**
```java
public String findUser(int id) {
    if (id < 0) {
        return null;  // No user found
    }
    return "User" + id;
}
```

### Method Overloading

Multiple methods with same name but different parameters:
```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }
    
    public double add(double a, double b) {  // Different parameter type
        return a + b;
    }
    
    public int add(int a, int b, int c) {  // Different number of parameters
        return a + b + c;
    }
}

Calculator calc = new Calculator();
calc.add(5, 3);         // Calls int add(int, int) â†’ 8
calc.add(5.5, 3.2);     // Calls double add(double, double) â†’ 8.7
calc.add(1, 2, 3);      // Calls int add(int, int, int) â†’ 6
```

**Method signature:**
- Method name + parameter types (number, order, type)
- Return type is NOT part of signature (cannot overload on return type alone)

```java
public int getValue() { return 1; }
// public double getValue() { return 1.0; }  // Error: cannot overload on return type
```

### Calling Methods

**From another instance method:**
```java
public void doSomething() {
    String name = getName();  // Calls another method
    printGreeting(name);
}
```

**From a static method:**
```java
public static void main(String[] args) {
    // Can only call other static methods directly
    calculate();  // OK - static method
    
    // To call instance method, need an object:
    MyClass obj = new MyClass();
    obj.instanceMethod();  // OK
}

public static void calculate() {
    // ...
}

public void instanceMethod() {
    // ...
}
```

**From Java 25 `void main()` style:**
```java
void main() {
    IO.println("Simple entry point");
    int value = compute();
    IO.println("value = " + value);
}

int compute() {
    return 42;
}
```

**Using `this` to call other methods:**
```java
public class Person {
    String name;
    int age;
    
    public void printInfo() {
        System.out.println(getName() + " is " + getAge());
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getAge() {
        return this.age;
    }
}
```

---

## Object-Oriented Programming

### Classes & Objects

**Class** is a blueprint; **object** is an instance of a class:

```java
public class Dog {
    // Fields (attributes)
    String name;
    int age;
    String breed;
    
    // Constructor
    public Dog(String name, int age, String breed) {
        this.name = name;
        this.age = age;
        this.breed = breed;
    }
    
    // Instance methods
    public void bark() {
        System.out.println(name + " says: Woof!");
    }
    
    public void celebrate() {
        age++;
        System.out.println(name + " is now " + age + " years old!");
    }
}
```

**Creating objects:**
```java
Dog dog1 = new Dog("Buddy", 3, "Golden Retriever");
Dog dog2 = new Dog("Max", 5, "Labrador");

dog1.bark();       // "Buddy says: Woof!"
dog1.celebrate();  // "Buddy is now 4 years old!"
```

### Constructors

**Purpose**: Initialize objects when created with `new`

**Default constructor** (no parameters, generated automatically):
```java
public class Point {
    int x;
    int y;
    // Implicit default constructor:
    // public Point() {
    //     this.x = 0;
    //     this.y = 0;
    // }
}

Point p = new Point();  // Uses default constructor
```

**Explicit constructors:**
```java
public class Point {
    int x;
    int y;
    
    public Point() {
        this.x = 0;
        this.y = 0;
    }
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

Point p1 = new Point();      // Uses no-arg constructor
Point p2 = new Point(5, 10); // Uses two-arg constructor
```

**Constructor chaining with `this(...)`:**
```java
public class Rectangle {
    int width;
    int height;
    
    public Rectangle() {
        this(1, 1);  // Call another constructor
    }
    
    public Rectangle(int size) {
        this(size, size);
    }
    
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
```

**Constructor with `super(...)` (inheritance):**
```java
public class Shape {
    String color;
    
    public Shape(String color) {
        this.color = color;
    }
}

public class Circle extends Shape {
    int radius;
    
    public Circle(String color, int radius) {
        super(color);  // Call parent constructor
        this.radius = radius;
    }
}
```

### this Keyword

Refers to the current object instance:

```java
public class Person {
    String name;
    int age;
    
    public Person(String name, int age) {
        this.name = name;  // 'this.name' is the field; 'name' is the parameter
        this.age = age;
    }
    
    public void printInfo() {
        System.out.println(this.name + " is " + this.age);
    }
    
    public void celebrate() {
        this.age++;
        System.out.println(this.printInfo());  // Call another method
    }
}
```

### Encapsulation & getters/setters

**Principle**: Hide internal details, expose controlled interface

```java
public class BankAccount {
    private double balance;  // Private field
    
    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }
    
    // Getter
    public double getBalance() {
        return this.balance;
    }
    
    // Setter with validation
    public void deposit(double amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }
    
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            this.balance -= amount;
        }
    }
}

BankAccount account = new BankAccount(100);
System.out.println(account.getBalance());  // 100
account.deposit(50);
System.out.println(account.getBalance());  // 150
```

### Static Methods & Fields

**Static methods** (class-level, no instance needed):
```java
public class MathUtils {
    public static double square(double x) {
        return x * x;
    }
    
    public static double sqrt(double x) {
        return Math.sqrt(x);
    }
}

double result = MathUtils.square(5);  // No object needed
```

**Static fields** (shared across all instances):
```java
public class Counter {
    static int count = 0;  // Shared among all instances
    
    public Counter() {
        count++;
    }
    
    public static int getCount() {
        return count;
    }
}

new Counter();
new Counter();
new Counter();
System.out.println(Counter.getCount());  // 3
```

---

## Visibility & Packages

### Access Modifiers

Control who can access classes, methods, and fields:

| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| `public` | âœ“ | âœ“ | âœ“ | âœ“ |
| `protected` | âœ“ | âœ“ | âœ“ | âœ— |
| Package-private | âœ“ | âœ“ | âœ— | âœ— |
| `private` | âœ“ | âœ— | âœ— | âœ— |

**Examples:**
```java
public class Shape {
    public int id;              // Accessible everywhere
    protected String color;     // Accessible in package & subclasses
    int vertices;               // Package-private (default)
    private double area;        // Accessible only in this class
    
    public void draw() {}       // Public method
    protected void validate() {} // Protected method
    void calculate() {}         // Package-private method
    private void init() {}      // Private method
}
```

### Packages

**Purpose**: Organize code, avoid name conflicts

**Package declaration** (must be first line, one per file):
```java
package com.mycompany.geometry;

public class Circle {
    // ...
}
```

**File structure:**
```
src/
  com/
    mycompany/
      geometry/
        Circle.java
        Square.java
      utils/
        Calculator.java
```

**Importing from packages:**
```java
// Import single class
import com.mycompany.geometry.Circle;

// Import all classes from package
import com.mycompany.geometry.*;

// Use without import with fully qualified name
com.mycompany.geometry.Circle c = new com.mycompany.geometry.Circle();
```

**Default package:**
```java
// No package statement = default package (all files in same directory)
public class SimpleClass {
    // Only accessible within same directory
}
```

### Data Leaks (Common Pitfall)

**Problem**: Exposing mutable references without copying

```java
// BAD - Data leak
public class Team {
    private List<String> members = new ArrayList<>();
    
    public List<String> getMembers() {
        return members;  // Returns reference, can be modified!
    }
}

Team team = new Team();
team.getMembers().add("Hacker");  // Modifies internal list!
```

**Solution**: Return copies
```java
// GOOD - Defensive copy
public List<String> getMembers() {
    return new ArrayList<>(members);  // Return copy
}

// Or immutable collection (Java 9+)
public List<String> getMembers() {
    return Collections.unmodifiableList(members);
}
```

---

## Exception Handling

### Exception Hierarchy

```
Throwable
â”œâ”€â”€ Error
â”‚   â”œâ”€â”€ OutOfMemoryError
â”‚   â”œâ”€â”€ StackOverflowError
â”‚   â””â”€â”€ ...
â””â”€â”€ Exception
    â”œâ”€â”€ Checked Exception (must be caught/declared)
    â”‚   â”œâ”€â”€ IOException
    â”‚   â”‚   â”œâ”€â”€ FileNotFoundException
    â”‚   â”‚   â””â”€â”€ ...
    â”‚   â”œâ”€â”€ SQLException
    â”‚   â””â”€â”€ ...
    â””â”€â”€ RuntimeException (unchecked, optional to catch)
        â”œâ”€â”€ NullPointerException
        â”œâ”€â”€ ArrayIndexOutOfBoundsException
        â”œâ”€â”€ ClassCastException
        â”œâ”€â”€ IllegalArgumentException
        â””â”€â”€ ...
```

### Checked vs. Unchecked Exceptions

**Checked Exceptions** must be caught or declared (compiler enforces):
```java
import java.io.IOException;
import java.io.FileReader;

public void readFile(String filename) throws IOException {
    FileReader reader = new FileReader(filename);  // Can throw FileNotFoundException
    reader.close();
}

// Must either:
// 1. Catch it
try {
    readFile("data.txt");
} catch (IOException e) {
    System.out.println("File not found");
}

// 2. Declare it (propagate)
public void process() throws IOException {
    readFile("data.txt");
}
```

**Unchecked Exceptions** (RuntimeException subclasses) - optional to catch:
```java
// No compiler requirement to handle
int[] arr = new int[5];
int value = arr[10];  // ArrayIndexOutOfBoundsException (unchecked)

String str = null;
System.out.println(str.length());  // NullPointerException (unchecked)
```

### try-catch-finally

**Basic try-catch:**
```java
try {
    int result = 10 / 0;  // Throws ArithmeticException
} catch (ArithmeticException e) {
    System.out.println("Cannot divide by zero");
    System.out.println("Error: " + e.getMessage());
}
```

**Multiple catch blocks:**
```java
try {
    // Code that might throw exceptions
} catch (FileNotFoundException e) {
    System.out.println("File not found");
} catch (IOException e) {
    System.out.println("I/O error");
} catch (Exception e) {
    System.out.println("Unknown error");
}
```

**Catching multiple exceptions (Java 7+):**
```java
try {
    // Code
} catch (IOException | SQLException e) {
    System.out.println("Database or file error");
}
```

**finally block** (always executes, even if exception):
```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {
    System.out.println("Caught error");
} finally {
    System.out.println("This always executes");
}
// Output:
// Caught error
// This always executes
```

### try-with-resources

Automatically closes resources (implements `AutoCloseable`):
```java
try (FileReader reader = new FileReader("data.txt")) {
    // Use reader
    int ch = reader.read();
} catch (IOException e) {
    System.out.println("Error reading file");
}
// reader automatically closed
```

Multiple resources:
```java
try (BufferedReader br = new BufferedReader(new FileReader("input.txt"));
     PrintWriter pw = new PrintWriter("output.txt")) {
    
    String line;
    while ((line = br.readLine()) != null) {
        pw.println(line.toUpperCase());
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

### Throwing Exceptions

**Throw exceptions:**
```java
public void validateAge(int age) throws IllegalArgumentException {
    if (age < 0) {
        throw new IllegalArgumentException("Age cannot be negative");
    }
    if (age > 150) {
        throw new IllegalArgumentException("Age is unrealistic");
    }
}
```

**Common exceptions to throw:**
- `IllegalArgumentException` - Invalid argument
- `NullPointerException` - Null where not allowed
- `UnsupportedOperationException` - Operation not supported
- `IllegalStateException` - Invalid state
- `IndexOutOfBoundsException` - Invalid index

### Exception Methods

**Common exception methods:**
```java
try {
    // Code
} catch (Exception e) {
    String message = e.getMessage();           // Error description
    String type = e.getClass().getName();      // Exception class
    StackTraceElement[] trace = e.getStackTrace(); // Where error occurred
    
    e.printStackTrace();  // Print full trace to console
}
```

---

## Arrays & Collections

### Arrays (Fixed Size)

**Declaration and creation:**
```java
int[] numbers = new int[5];           // Array of 5 ints (default: 0)
String[] names = new String[3];       // Array of 3 Strings (default: null)

int[] initialized = {1, 2, 3, 4, 5}; // Initialize with values
String[] fruits = {"apple", "banana", "cherry"};
```

**Accessing elements:**
```java
int[] arr = {10, 20, 30, 40, 50};

int first = arr[0];   // 10
int third = arr[2];   // 30
arr[1] = 25;          // Change value
System.out.println(arr.length);  // 5
```

**Array iteration:**
```java
// Traditional for loop
for (int i = 0; i < arr.length; i++) {
    System.out.println(arr[i]);
}

// Enhanced for loop (for-each)
for (int num : arr) {
    System.out.println(num);
}

// While loop with index
int i = 0;
while (i < arr.length) {
    System.out.println(arr[i]);
    i++;
}
```

**Multi-dimensional arrays:**
```java
// 2D array (matrix)
int[][] matrix = new int[3][3];      // 3x3 matrix
int[][] values = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

int elem = values[1][2];  // Row 1, Col 2 = 6
values[0][0] = 10;

// Jagged array (rows of different lengths)
int[][] jagged = new int[3][];
jagged[0] = new int[2];
jagged[1] = new int[3];
jagged[2] = new int[1];
```

**Bounds and errors:**
```java
int[] arr = {1, 2, 3};
int value = arr[5];  // ArrayIndexOutOfBoundsException
```

### ArrayList (Dynamic Size)

**Creation and basic operations:**
```java
import java.util.ArrayList;

ArrayList<String> fruits = new ArrayList<>();
fruits.add("apple");
fruits.add("banana");
fruits.add("cherry");

System.out.println(fruits.size());  // 3
System.out.println(fruits.get(0));  // "apple"
System.out.println(fruits.get(2));  // "cherry"
```

**Adding, removing, checking:**
```java
fruits.add(1, "date");       // Insert at index 1
fruits.remove(0);            // Remove "apple"
fruits.remove("banana");     // Remove by value

boolean has = fruits.contains("cherry");  // true
int index = fruits.indexOf("date");       // 0
```

**Iteration:**
```java
// Enhanced for loop
for (String fruit : fruits) {
    System.out.println(fruit);
}

// Traditional loop
for (int i = 0; i < fruits.size(); i++) {
    System.out.println(fruits.get(i));
}

// Iterator
Iterator<String> it = fruits.iterator();
while (it.hasNext()) {
    System.out.println(it.next());
}
```

**Important**: Don't modify list while iterating (except with iterator.remove()):
```java
// WRONG - ConcurrentModificationException
for (String fruit : fruits) {
    if (fruit.equals("apple")) {
        fruits.remove(fruit);  // Don't do this!
    }
}

// CORRECT - Use iterator
Iterator<String> it = fruits.iterator();
while (it.hasNext()) {
    String fruit = it.next();
    if (fruit.equals("apple")) {
        it.remove();  // Safe
    }
}
```

### HashSet (Unique Elements)

**Creation and operations:**
```java
import java.util.HashSet;

HashSet<String> colors = new HashSet<>();
colors.add("red");
colors.add("blue");
colors.add("green");
colors.add("red");       // Duplicate ignored

System.out.println(colors.size());        // 3
System.out.println(colors.contains("blue")); // true

colors.remove("blue");
System.out.println(colors.size());        // 2
```

**Iteration:**
```java
for (String color : colors) {
    System.out.println(color);  // Order not guaranteed
}

Set<String> immutable = new HashSet<>(colors);  // Copy
```

**Difference from ArrayList:**
- ArrayList: Ordered, allows duplicates, indexing
- HashSet: Unordered, no duplicates, fast lookup

### HashMap (Key-Value Pairs)

**Creation and operations:**
```java
import java.util.HashMap;

HashMap<String, Integer> scores = new HashMap<>();
scores.put("Alice", 95);
scores.put("Bob", 87);
scores.put("Charlie", 92);

System.out.println(scores.get("Alice"));    // 95
System.out.println(scores.size());          // 3
System.out.println(scores.containsKey("Bob")); // true
System.out.println(scores.containsValue(87));  // true

scores.remove("Bob");
scores.put("Alice", 100);  // Update value
```

**Iteration:**
```java
// Iterate over keys
for (String name : scores.keySet()) {
    System.out.println(name + ": " + scores.get(name));
}

// Iterate over values
for (Integer score : scores.values()) {
    System.out.println(score);
}

// Iterate over entries (more efficient)
for (Map.Entry<String, Integer> entry : scores.entrySet()) {
    String name = entry.getKey();
    Integer score = entry.getValue();
    System.out.println(name + ": " + score);
}
```

**Handling missing keys:**
```java
Integer score = scores.get("unknown");     // null
Integer score2 = scores.getOrDefault("unknown", 0);  // 0

scores.putIfAbsent("David", 80);  // Add only if not present
```

**Nested collections:**
```java
// Map of Lists
HashMap<String, ArrayList<String>> groups = new HashMap<>();
groups.put("Team A", new ArrayList<>());
groups.get("Team A").add("Alice");
groups.get("Team A").add("Bob");

// Map of Sets
HashMap<Integer, HashSet<String>> employeesByDept = new HashMap<>();
```

---

## Generics & Type Parameters

### Generic Classes

**Without generics (unsafe):**
```java
public class Box {
    private Object content;
    
    public void set(Object obj) {
        this.content = obj;
    }
    
    public Object get() {
        return content;
    }
}

Box box = new Box();
box.set("Hello");
String text = (String) box.get();  // Requires cast, can be unsafe
```

**With generics (type-safe):**
```java
public class Box<T> {
    private T content;
    
    public void set(T obj) {
        this.content = obj;
    }
    
    public T get() {
        return content;
    }
}

Box<String> stringBox = new Box<>();
stringBox.set("Hello");
String text = stringBox.get();  // No cast needed

Box<Integer> intBox = new Box<>();
intBox.set(42);
Integer number = intBox.get();
```

### Generic Methods

```java
public class ArrayUtils {
    public static <T> void printArray(T[] array) {
        for (T element : array) {
            System.out.println(element);
        }
    }
    
    public static <T> T getFirst(T[] array) {
        return array.length > 0 ? array[0] : null;
    }
    
    public static <T> boolean contains(T[] array, T target) {
        for (T element : array) {
            if (element.equals(target)) {
                return true;
            }
        }
        return false;
    }
}

String[] words = {"Hello", "World"};
ArrayUtils.printArray(words);
String first = ArrayUtils.getFirst(words);
```

### Type Inference (Diamond Operator)

```java
// Without type inference (verbose)
HashMap<String, Integer> map = new HashMap<String, Integer>();
ArrayList<String> list = new ArrayList<String>();

// With type inference (Java 7+, preferred)
HashMap<String, Integer> map = new HashMap<>();
ArrayList<String> list = new ArrayList<>();
```

### Bounded Type Parameters

**Upper bound (extends):**
```java
public class NumberBox<T extends Number> {
    private T value;
    
    public double toDouble() {
        return value.doubleValue();
    }
}

NumberBox<Integer> intBox = new NumberBox<>();  // OK
// NumberBox<String> strBox = new NumberBox<>();  // Error
```

**Multiple bounds:**
```java
public <T extends Number & Comparable<T>> T max(T a, T b) {
    return a.compareTo(b) > 0 ? a : b;
}
```

### Wildcards

**Unbounded wildcard:**
```java
public void printList(List<?> list) {
    for (Object obj : list) {
        System.out.println(obj);
    }
}

printList(new ArrayList<String>());
printList(new ArrayList<Integer>());
```

**Upper bound wildcard (? extends Type):**
```java
public Number getNumber(List<? extends Number> list) {
    return list.get(0);
}

getNumber(new ArrayList<Integer>());
getNumber(new ArrayList<Double>());
```

**Lower bound wildcard (? super Type):**
```java
public void addNumbers(List<? super Integer> list) {
    list.add(5);
}

addNumbers(new ArrayList<Number>());
addNumbers(new ArrayList<Integer>());
```

---

## Inheritance & Polymorphism

### extends Keyword

**Creating a subclass:**
```java
public class Animal {
    String name;
    
    public Animal(String name) {
        this.name = name;
    }
    
    public void eat() {
        System.out.println(name + " is eating.");
    }
    
    public void sleep() {
        System.out.println(name + " is sleeping.");
    }
}

public class Dog extends Animal {
    String breed;
    
    public Dog(String name, String breed) {
        super(name);  // Call parent constructor
        this.breed = breed;
    }
    
    @Override
    public void eat() {
        System.out.println(name + " is eating dog food.");
    }
    
    public void bark() {
        System.out.println(name + " says: Woof!");
    }
}

Dog dog = new Dog("Buddy", "Golden Retriever");
dog.eat();      // Overridden method
dog.sleep();    // Inherited method
dog.bark();     // Dog-specific method
```

### super Keyword

**Calling parent constructor:**
```java
public class Dog extends Animal {
    public Dog(String name) {
        super(name);  // Must be first line
    }
}
```

**Calling parent methods:**
```java
public class Dog extends Animal {
    @Override
    public void eat() {
        super.eat();  // Call parent's eat()
        System.out.println("...and wagging tail!");
    }
}
```

### Method Overriding

**Rules:**
- Same method name and parameters
- Same or more specific return type (covariant return type)
- Same or weaker access level
- Must not throw checked exceptions not thrown by parent

```java
public class Animal {
    public void move() {
        System.out.println("Animal moves");
    }
}

public class Bird extends Animal {
    @Override
    public void move() {
        System.out.println("Bird flies");
    }
}

public class Fish extends Animal {
    @Override
    public void move() {
        System.out.println("Fish swims");
    }
}

Animal[] animals = {new Bird(), new Fish(), new Animal()};
for (Animal a : animals) {
    a.move();  // Calls appropriate overridden method
}
// Output:
// Bird flies
// Fish swims
// Animal moves
```

### Polymorphism

**One interface, multiple implementations:**
```java
List<Animal> animals = new ArrayList<>();
animals.add(new Dog("Buddy"));
animals.add(new Bird("Tweety"));
animals.add(new Fish("Nemo"));

for (Animal animal : animals) {
    animal.eat();      // Polymorphic call
    animal.sleep();    // Polymorphic call
}
```

**Runtime type checking:**
```java
for (Animal animal : animals) {
    if (animal instanceof Dog) {
        Dog dog = (Dog) animal;
        dog.bark();
    } else if (animal instanceof Bird) {
        Bird bird = (Bird) animal;
        bird.sing();
    }
}
```

### Inheritance Hierarchy Issues

**Diamond Problem (with interfaces, not classes):**
```java
//     Animal
//    /      \
//   Dog    Cat
//    \      /
//   Both?  (Error - Java only allows single inheritance)

// Solution: Use interfaces (no state conflict)
public interface Swimmer {
    void swim();
}

public class Fish implements Swimmer {
    @Override
    public void swim() {
        System.out.println("Fish swims");
    }
}
```

---

## Interfaces & Abstract Classes

### Interfaces

**Define a contract (what classes must implement):**
```java
public interface Animal {
    void eat();
    void sleep();
    
    // Default method (Java 8+)
    default void move() {
        System.out.println("Moving...");
    }
    
    // Static method (Java 8+)
    static void info() {
        System.out.println("This is an animal interface");
    }
}

public class Dog implements Animal {
    @Override
    public void eat() {
        System.out.println("Dog eats dog food");
    }
    
    @Override
    public void sleep() {
        System.out.println("Dog sleeps");
    }
    
    // Inherits default move() method
}

Animal animal = new Dog();
animal.eat();
animal.move();  // Uses default implementation
```

**Multiple interfaces:**
```java
public interface Swimmer {
    void swim();
}

public interface Flyer {
    void fly();
}

public class Duck implements Swimmer, Flyer {
    @Override
    public void swim() {
        System.out.println("Duck swims");
    }
    
    @Override
    public void fly() {
        System.out.println("Duck flies");
    }
}
```

**Common interface patterns:**
- `Iterable<T>` - Supports for-each loop
- `Comparable<T>` - Natural ordering
- `Closeable` / `AutoCloseable` - Resource management
- Names often end in `-able` or `-ible`

### Abstract Classes

**Provide partial implementation, force subclasses to complete:**
```java
public abstract class Shape {
    String color;
    
    public Shape(String color) {
        this.color = color;
    }
    
    // Abstract method (no implementation)
    abstract double getArea();
    
    // Concrete method
    public void printColor() {
        System.out.println("Color: " + color);
    }
}

public class Circle extends Shape {
    double radius;
    
    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }
    
    @Override
    double getArea() {
        return Math.PI * radius * radius;
    }
}

Shape shape = new Circle("red", 5);
shape.printColor();
System.out.println("Area: " + shape.getArea());
```

**Cannot instantiate abstract class:**
```java
// Shape shape = new Shape("red");  // Error: cannot instantiate abstract class
Shape shape = new Circle("red", 5);  // OK
```

### Abstract vs. Interface

| Feature | Abstract Class | Interface |
|---------|---|---|
| Fields | Any access level, any type | `public static final` |
| Methods | Any implementation | Abstract or default |
| Constructors | Yes | No |
| Inheritance | Single class inheritance | Multiple interface implementation |
| Purpose | Partial implementation | Contract/specification |

---

## Equals, Hashing & Ordering

### equals() Method

**Default behavior (from Object):**
```java
public class Person {
    String name;
    int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

Person p1 = new Person("Alice", 25);
Person p2 = new Person("Alice", 25);

System.out.println(p1 == p2);         // false (different objects)
System.out.println(p1.equals(p2));    // false (default uses ==)
```

**Overriding equals():**
```java
public class Person {
    String name;
    int age;
    
    @Override
    public boolean equals(Object obj) {
        // Check if same object
        if (this == obj) return true;
        
        // Check if null or different type
        if (obj == null || !(obj instanceof Person)) return false;
        
        // Cast and compare fields
        Person other = (Person) obj;
        return this.name.equals(other.name) && this.age == other.age;
    }
}

Person p1 = new Person("Alice", 25);
Person p2 = new Person("Alice", 25);

System.out.println(p1.equals(p2));    // true (value equality)
```

**equals() contract (must be reflexive, symmetric, transitive):**
```java
// Reflexive: x.equals(x) is true
p1.equals(p1) // true

// Symmetric: if x.equals(y) then y.equals(x)
if (p1.equals(p2)) {
    p2.equals(p1)  // Must be true
}

// Transitive: if x.equals(y) and y.equals(z) then x.equals(z)
if (p1.equals(p2) && p2.equals(p3)) {
    p1.equals(p3)  // Must be true
}
```

### hashCode() Method

**Purpose**: Enable efficient storage in hash-based collections

```java
public class Person {
    String name;
    int age;
    
    @Override
    public int hashCode() {
        // Use prime number and all fields in equals()
        return 31 * name.hashCode() + age;
    }
    
    @Override
    public boolean equals(Object obj) {
        // ... implementation ...
    }
}
```

**hashCode() contract:**
- If `equals()` returns true, `hashCode()` must return same value
- If `hashCode()` returns same value, `equals()` may return false (collision)

```java
Map<Person, String> map = new HashMap<>();
Person p1 = new Person("Alice", 25);
map.put(p1, "Engineer");

Person p2 = new Person("Alice", 25);
System.out.println(map.get(p2));  // "Engineer" (equals & hashCode work together)
```

### Comparable Interface

**Implement to define natural ordering:**
```java
public class Person implements Comparable<Person> {
    String name;
    int age;
    
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    
    @Override
    public int compareTo(Person other) {
        // Compare by age first
        if (this.age != other.age) {
            return this.age - other.age;
        }
        // If ages equal, compare by name
        return this.name.compareTo(other.name);
    }
    
    @Override
    public String toString() {
        return name + " (" + age + ")";
    }
}

List<Person> people = Arrays.asList(
    new Person("Charlie", 30),
    new Person("Alice", 25),
    new Person("Bob", 25)
);

Collections.sort(people);  // Uses compareTo()
for (Person p : people) {
    System.out.println(p);
}
// Output:
// Alice (25)
// Bob (25)
// Charlie (30)
```

**compareTo() return value:**
- Negative: this < other
- Zero: this == other
- Positive: this > other

### Comparator Interface

**Custom comparisons without modifying class:**
```java
Comparator<Person> byName = new Comparator<Person>() {
    @Override
    public int compare(Person p1, Person p2) {
        return p1.name.compareTo(p2.name);
    }
};

Collections.sort(people, byName);  // Sort by name instead

// With lambda (Java 8+)
Collections.sort(people, (p1, p2) -> p1.name.compareTo(p2.name));
```

**Multiple sort criteria:**
```java
Comparator<Person> comparator = Comparator
    .comparingInt((Person p) -> p.age)           // Primary: age
    .thenComparing((Person p) -> p.name);        // Secondary: name

Collections.sort(people, comparator);
```

---

## File I/O & Resources

### Reading Files

**Using BufferedReader:**
```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public void readFile(String filename) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(filename));
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
    reader.close();  // Must close resource
}
```

**Using try-with-resources (Java 7+, preferred):**
```java
public void readFile(String filename) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }
    }  // reader automatically closed
}
```

**Common exceptions:**
- `FileNotFoundException` - File doesn't exist
- `IOException` - I/O error during reading
- `AccessDeniedException` - No permission

### Writing Files

**Using PrintWriter:**
```java
import java.io.PrintWriter;
import java.io.IOException;

public void writeFile(String filename) throws IOException {
    try (PrintWriter writer = new PrintWriter(filename)) {
        writer.println("Line 1");
        writer.println("Line 2");
        writer.println("Line 3");
    }
}
```

**Append mode:**
```java
public void appendFile(String filename, String content) throws IOException {
    try (PrintWriter writer = new PrintWriter(
            new FileWriter(filename, true))) {  // true = append
        writer.println(content);
    }
}
```

### File Class

**File operations:**
```java
import java.io.File;

File file = new File("data.txt");

if (file.exists()) {
    System.out.println("File length: " + file.length() + " bytes");
    System.out.println("Is directory: " + file.isDirectory());
    System.out.println("Absolute path: " + file.getAbsolutePath());
    
    file.delete();  // Delete file
}

File newFile = new File("output.txt");
newFile.createNewFile();  // Create new file
```

### Parsing Text

**Splitting strings:**
```java
String line = "Alice,25,Engineer";
String[] parts = line.split(",");
System.out.println(parts[0]);  // "Alice"
System.out.println(parts[1]);  // "25"
System.out.println(parts[2]);  // "Engineer"
```

**Parsing numbers:**
```java
try {
    int age = Integer.parseInt("25");
    double salary = Double.parseDouble("50000.50");
    long population = Long.parseLong("8000000");
} catch (NumberFormatException e) {
    System.out.println("Invalid number format");
}
```

---

## Standard Library Reference

### java.lang Package

#### Object Class

**Methods all objects inherit:**
```java
Object obj = new String("Hello");

// toString(): textual representation
System.out.println(obj.toString());  // "Hello"

// equals(): value equality (override to use)
String s1 = new String("Hello");
String s2 = new String("Hello");
System.out.println(s1.equals(s2));  // true

// hashCode(): hash value for collections
int hash = s1.hashCode();

// getClass(): runtime type
Class<?> cls = obj.getClass();
System.out.println(cls.getName());  // "java.lang.String"

// clone(): create copy (rarely used)
```

#### String Class

**Immutable text representation:**
```java
String s = "Hello";

// Length
int len = s.length();  // 5

// Character access
char first = s.charAt(0);  // 'H'

// Substring
String sub = s.substring(1, 4);  // "ell"

// Contains
boolean has = s.contains("ell");  // true

// Case conversion
String upper = s.toUpperCase();   // "HELLO"
String lower = s.toLowerCase();   // "hello"

// Trimming whitespace
String padded = "  Hello  ";
String trimmed = padded.trim();  // "Hello"
String leading = padded.stripLeading();  // "Hello  "
String trailing = padded.stripTrailing(); // "  Hello"

// Splitting
String[] words = "Hello,World,Java".split(",");
// ["Hello", "World", "Java"]

// Joining (Java 8+)
String joined = String.join("-", "a", "b", "c");  // "a-b-c"

// Finding index
int idx = s.indexOf("l");     // 2 (first occurrence)
int idx2 = s.lastIndexOf("l"); // 3 (last occurrence)

// Replacing
String replaced = s.replace("l", "L");  // "HeLLo"
String regex = s.replaceAll("l+", "L"); // "HeLo"

// Comparing
int cmp = s.compareTo("Hallo");  // > 0 (s is greater)
int cmp2 = s.compareToIgnoreCase("hello");  // 0 (equal ignoring case)

// Formatting
String msg = "Name: %s, Age: %d".formatted("Alice", 30);
// "Name: Alice, Age: 30"
```

#### Integer, Long, Double, Boolean (Wrappers)

**Convert between primitives and objects:**
```java
// Boxing (primitive to object)
Integer boxed = Integer.valueOf(42);
Long longValue = Long.valueOf(1000000000L);
Double doubleValue = Double.valueOf(3.14);

// Unboxing (object to primitive)
int primitive = boxed.intValue();  // 42
long prim = longValue.longValue();

// Auto-boxing (Java 5+)
Integer x = 10;      // Auto-boxed
int y = x;           // Auto-unboxed

// Parsing
int parsed = Integer.parseInt("42");      // 42
long parsed2 = Long.parseLong("1000000");
double parsed3 = Double.parseDouble("3.14");

try {
    int bad = Integer.parseInt("not a number");
} catch (NumberFormatException e) {
    System.out.println("Invalid format");
}

// Constants
System.out.println(Integer.MIN_VALUE);  // -2147483648
System.out.println(Integer.MAX_VALUE);  // 2147483647
System.out.println(Double.POSITIVE_INFINITY);
System.out.println(Double.NEGATIVE_INFINITY);

// Comparison
int cmp = Integer.compare(5, 3);  // > 0
```

#### Math Class

**Mathematical operations:**
```java
import java.lang.Math;

// Constants
Math.PI;      // 3.141592653589793
Math.E;       // 2.718281828459045

// Basic operations
Math.abs(-5);          // 5
Math.min(5, 3);        // 3
Math.max(5, 3);        // 5

// Power and roots
Math.pow(2, 3);        // 8.0
Math.sqrt(16);         // 4.0
Math.cbrt(8);          // 2.0

// Rounding
Math.floor(3.7);       // 3.0
Math.ceil(3.2);        // 4.0
Math.round(3.5);       // 4 (rounds to nearest, .5 rounds up)

// Trigonometry
Math.sin(0);           // 0.0
Math.cos(0);           // 1.0
Math.tan(0);           // 0.0

// Logarithms
Math.log(Math.E);      // 1.0 (natural log)
Math.log10(100);       // 2.0
```

#### System Class

**System operations:**
```java
import java.lang.System;

// Output/Input
System.out.println("To console");
System.err.println("To error stream");

// Line separator (platform-specific)
String newline = System.lineSeparator();  // "\n" or "\r\n"

// Environment variables
String path = System.getenv("PATH");

// Properties
String osName = System.getProperty("os.name");
String javaVersion = System.getProperty("java.version");

// Current time
long millis = System.currentTimeMillis();  // Milliseconds since epoch

// Exit program
// System.exit(0);  // Terminate with exit code 0

// Array copy
int[] src = {1, 2, 3, 4, 5};
int[] dest = new int[3];
System.arraycopy(src, 0, dest, 0, 3);  // Copy first 3 elements
```

#### IO Class (`java.lang.IO`) - Java 25 Course Usage

The course now requires familiarity with Java 25 simple I/O helpers.

Common calls used in labs:
```java
IO.print("Enter number: ");
String s = IO.readln();
IO.println("You entered: " + s);
```

Typical patterns:
- Console prompts + line-based input parsing
- Replacing beginner-level `System.out`/`Scanner` boilerplate in small programs
- Fast prototyping in compact source files with `void main()`

Notable exceptions in practical use:
- `NumberFormatException` when parsing invalid numeric input
- `IllegalArgumentException` when validating parsed values

### java.util Package

#### Collections Class

**Utility methods for collections:**
```java
import java.util.Collections;
import java.util.List;
import java.util.Arrays;

List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5, 9);

// Sorting
Collections.sort(numbers);  // [1, 1, 3, 4, 5, 9]

// Reversing
Collections.reverse(numbers);  // [9, 5, 4, 3, 1, 1]

// Shuffling
Collections.shuffle(numbers);  // Random order

// Finding min/max
int min = Collections.min(numbers);  // 1
int max = Collections.max(numbers);  // 9

// Searching (requires sorted list for binary search)
Collections.sort(numbers);
int index = Collections.binarySearch(numbers, 4);  // Position where 4 is found

// Creating immutable copies
List<String> original = Arrays.asList("a", "b", "c");
List<String> immutable = Collections.unmodifiableList(original);
// immutable.add("d");  // UnsupportedOperationException

// Other operations
Collections.replaceAll(numbers, 1, 0);  // Replace 1s with 0s
Collections.rotate(numbers, 2);         // Rotate by 2 positions
```

#### Arrays Class

**Array utilities:**
```java
import java.util.Arrays;

int[] arr = {3, 1, 4, 1, 5};

// Sorting
Arrays.sort(arr);  // [1, 1, 3, 4, 5]

// Searching (requires sorted array)
int index = Arrays.binarySearch(arr, 4);

// Converting to List
List<Integer> list = Arrays.asList(1, 2, 3);

// Copying
int[] copy = Arrays.copyOf(arr, arr.length);
int[] partial = Arrays.copyOfRange(arr, 1, 3);

// Comparing
int[] arr1 = {1, 2, 3};
int[] arr2 = {1, 2, 3};
boolean equal = Arrays.equals(arr1, arr2);  // true

// String representation
String str = Arrays.toString(arr);  // "[1, 1, 3, 4, 5]"

// Filling
int[] filled = new int[5];
Arrays.fill(filled, 7);  // [7, 7, 7, 7, 7]
```

#### Random Class

**Pseudo-random number generation:**
```java
import java.util.Random;

Random rand = new Random();

int randInt = rand.nextInt(10);        // 0-9
int randRange = rand.nextInt(10) + 1;  // 1-10
double randDouble = rand.nextDouble(); // 0.0-1.0 (exclusive)
boolean randBool = rand.nextBoolean();

// Seeding for reproducibility
Random seeded = new Random(42);
```

### java.io Package

#### IOException

**Checked exception for I/O operations:**
```java
import java.io.IOException;
import java.io.FileReader;

try {
    FileReader reader = new FileReader("missing.txt");
} catch (IOException e) {
    System.out.println("Could not read file");
}
```

#### FileReader & BufferedReader

**Reading text files efficiently:**
```java
import java.io.BufferedReader;
import java.io.FileReader;

try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        System.out.println(line);
    }
} catch (IOException e) {
    e.printStackTrace();
}
```

#### PrintWriter

**Writing text to files:**
```java
import java.io.PrintWriter;

try (PrintWriter writer = new PrintWriter("output.txt")) {
    writer.println("Hello");
    writer.println("World");
    writer.println(42);
} catch (IOException e) {
    e.printStackTrace();
}
```

---

## Testing with JUnit

### JUnit 5 Basics

**Setup (add dependency):**
```
org.junit.jupiter:junit-jupiter:5.9.0
```

**Basic test structure:**
```java
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CalculatorTest {
    Calculator calc = new Calculator();
    
    @Test
    public void testAddition() {
        int result = calc.add(5, 3);
        assertEquals(8, result);
    }
    
    @Test
    public void testDivisionByZero() {
        assertThrows(ArithmeticException.class, () -> {
            calc.divide(5, 0);
        });
    }
}
```

### Common Assertions

**Equality:**
```java
assertEquals(expected, actual);           // == for objects, .equals() for reference
assertNotEquals(unexpected, actual);
assertArrayEquals(expectedArray, actualArray);
```

**Boolean conditions:**
```java
assertTrue(condition);
assertFalse(condition);
```

**Null checks:**
```java
assertNull(obj);
assertNotNull(obj);
```

**Identity (reference equality):**
```java
assertSame(expected, actual);      // same object
assertNotSame(unexpected, actual);
```

**Exception testing:**
```java
assertThrows(Exception.class, () -> {
    // Code that should throw exception
    methodThatThrows();
});

assertDoesNotThrow(() -> {
    // Code that should not throw
    safeMethod();
});
```

### Parameterized Tests

**Multiple inputs, one test method:**
```java
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorTest {
    Calculator calc = new Calculator();
    
    @ParameterizedTest
    @CsvSource({
        "5, 3, 8",
        "0, 0, 0",
        "-5, 3, -2",
        "100, 200, 300"
    })
    public void testAddition(int a, int b, int expected) {
        assertEquals(expected, calc.add(a, b));
    }
}
```

### Test Lifecycle

**Before/after each test:**
```java
public class TestClass {
    List<String> list;
    
    @BeforeEach
    public void setUp() {
        list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
    }
    
    @AfterEach
    public void tearDown() {
        list = null;
    }
    
    @Test
    public void testSize() {
        assertEquals(2, list.size());
    }
}
```

**Before/after all tests (static methods):**
```java
@BeforeAll
public static void setUpClass() {
    System.out.println("Once before all tests");
}

@AfterAll
public static void tearDownClass() {
    System.out.println("Once after all tests");
}
```

---

## CheckThat Framework

### Purpose

CheckThat is a **structure verification framework**. Students read CheckThat tests to understand what they must implement, but **students do NOT write CheckThat tests themselves**. They write normal JUnit tests instead.

**When you see a CheckThat test:**
- It tells you the required class/method structure
- It verifies your code meets the specification
- It shows you what methods/fields are required
- **You only read it to understand requirements**

### Common CheckThat Patterns

#### Class Definition

```
theClass("ClassName")
  .withVisibility("public")
```

**Means:** Define a public class named `ClassName`

```java
public class ClassName {
    // ...
}
```

#### Methods

```
theClass("Person")
  .hasMethod("getName")
  .thatReturns("String")
  .withNoParams()
```

**Means:** Public class Person must have method `String getName()`

```java
public String getName() {
    return name;
}
```

#### Fields

```
theClass("Person")
  .hasField("name")
  .ofType("String")
  .thatIsNotModifiable()
```

**Means:** Field `name` of type String, declared as `final`

```java
private final String name;
```

#### Constructors

```
theClass("Person")
  .hasConstructor()
  .withArgs("String", "int")
```

**Means:** Constructor with String and int parameters

```java
public Person(String name, int age) {
    this.name = name;
    this.age = age;
}
```

#### Interfaces & Inheritance

```
theClass("Dog")
  .withParent("Animal")
  .withInterface("Comparable")
```

**Means:** Dog extends Animal and implements Comparable

```java
public class Dog extends Animal implements Comparable<Dog> {
    // ...
}
```

#### Special Requirements

**`TEXTUAL_REPRESENTATION`**
- Override `toString()` method

**`EQUALITY_CHECK`**
- Override both `equals()` and `hashCode()`

**`NATURAL_ORDERING`**
- Implement `Comparable<T>` with `compareTo()` method

**`STATIC` / `INSTANCE_LEVEL`**
- Static methods/fields vs instance methods/fields

### Complete CheckThat Token Atlas (Main Reference)

Use this as the canonical translation table from CheckThat language to Java code obligations.

#### Visibility and Modifiers

- `VISIBLE_TO_ALL` -> `public`
- `VISIBLE_TO_CLASSES` -> `protected`
- `VISIBLE_TO_NONE` -> `private`
- `VISIBLE_TO_PACKAGE` -> package-private (no visibility keyword)
- `NOT_MODIFIABLE` -> `final`
- `MODIFIABLE` -> no `final`
- `USABLE_WITHOUT_INSTANCE` -> `static`
- `INSTANCE_LEVEL` -> no `static`
- `FULLY_IMPLEMENTED` -> concrete class/method body exists
- `NOT_IMPLEMENTED` -> `abstract` class/method or intentionally unimplemented method contract

#### Core Entity Tokens

- `theClass` -> define a class
- `theClassWithParent` -> class with explicit `extends`
- `theInterface` -> define an interface
- `theEnum` -> define an enum
- `theParent` -> parent/superclass relationship is required
- `theOtherConstructor` -> constructor chaining through `this(...)`

#### Constructor and Field Clauses

- `hasDefaultConstructor` -> rely on implicit no-arg constructor (no explicit constructors declared)
- `hasConstructor` / `hasNoArgConstructor` -> explicit constructor requirement
- `withArgs` / `withNoArgs` -> constructor or method parameter list constraints
- `withArgsSimilarToFields` -> constructor parameters align with declared fields
- `withArgsAsInParent` -> constructor argument shape mirrors parent constructor
- `hasField` -> required field exists with exact name/type/modifier constraints
- `thatHasValue` / `withInitialValue` -> required field initializer value

#### Method and Signature Clauses

- `hasMethod` / `hasMethodWithNoParams` -> required method exists
- `withParams` / `withNoParams` -> method parameter constraints
- `thatReturns` / `thatReturnsNothing` -> method return type contract
- `withCases` -> method behavior typically expected across specified case categories
- `GETTER` / `SETTER`, `hasGetter` / `hasSetter` -> JavaBean naming and expected signatures:
    - `Type getX()`
    - `void setX(Type value)`

#### Inheritance, Interfaces, and Generics

- `INHERITED` / `thatIsInheritedFrom` / `thatHasParentClass` -> `extends` relationship
- `withParent` -> explicit parent class
- `withInterface` / `withInterfaces` / `thatImplements` -> `implements ...`
- `withTypeParameter` / `withTypeParameters` -> generic type parameters (`<T>`, `<K,V>`, ...)

#### Equality, Ordering, and Representation

- `TEXTUAL_REPRESENTATION` -> override `toString()`
- `EQUALITY_CHECK` -> override both `equals(Object)` and `hashCode()`
- `NATURAL_ORDERING` -> implement `Comparable<T>` and `compareTo(T)`

#### Exceptions and Enum Clauses

- `theCheckedException` -> extends `Exception` (not `RuntimeException`)
- `theUncheckedException` -> extends `RuntimeException`
- `canThrowException` / `thatCanRaise` / `thatThrows` / `thatThrowsCheckedException` -> `throws` clause or required thrown behavior
- `hasEnumElements` -> enum constants must match required set/order

#### Meta and Matcher Clauses You Will See

- `thatIs` / `thatHas` / `has` / `hasNo` / `thatHasNo` -> positive/negative property checks
- `thatCalls` -> required call-site usage in implementation
- `with` / `withAddtionalArgs` / `that` -> fluent matcher chaining parts
- `hasNoArgTestMethods` / `hasTestMethodWithParams` -> expected JUnit method shapes

#### Type String Notation Used by Tests

- `"Type [of T1 [to T2...]]: name"` -> typed declaration signature format used in tests
- `"vararg of T"` -> `T...`
- `"Map of K to V"` -> `Map<K, V>`

#### Practical Rule for Students

- You are not required to write CheckThat tests.
- You are required to decode these tokens and implement Java code that satisfies them exactly.
- When in doubt, copy the required signature/modifier/visibility literally from the failing structure test into your implementation.

### Reading CheckThat Output

When CheckThat test fails:
```
âœ— ClassName.methodName(String, int) should return boolean
âœ— Field x should be private final
âœ— ClassName should extend ParentClass
```

These errors tell you exactly what to fix. Look at the test file to see the exact requirement.

---

## Design Patterns & Idioms

### Getters and Setters

```java
public class Person {
    private String name;
    private int age;
    
    // Getter (accessor)
    public String getName() {
        return name;
    }
    
    // Setter (mutator) with validation
    public void setName(String name) {
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }
    
    public int getAge() {
        return age;
    }
    
    public void setAge(int age) {
        if (age >= 0 && age <= 150) {
            this.age = age;
        }
    }
}

// Usage
Person p = new Person();
p.setName("Alice");
System.out.println(p.getName());  // "Alice"
```

### Copy Constructors

```java
public class ArrayList<T> {
    T[] items;
    
    // Regular constructor
    public ArrayList() {
        items = new Object[10];
    }
    
    // Copy constructor
    public ArrayList(ArrayList<T> other) {
        this.items = new Object[other.items.length];
        System.arraycopy(other.items, 0, this.items, 0, other.items.length);
    }
}

ArrayList<String> original = new ArrayList<>();
ArrayList<String> copy = new ArrayList<>(original);  // Deep copy
```

### Factory Pattern

```java
public class Animal {
    String type;
    
    private Animal(String type) {  // Private constructor
        this.type = type;
    }
    
    // Factory methods
    public static Animal createDog() {
        return new Animal("Dog");
    }
    
    public static Animal createCat() {
        return new Animal("Cat");
    }
    
    public static Animal create(String type) {
        switch (type.toLowerCase()) {
            case "dog": return new Animal("Dog");
            case "cat": return new Animal("Cat");
            default: throw new IllegalArgumentException("Unknown animal");
        }
    }
}

Animal dog = Animal.createDog();
Animal cat = Animal.create("cat");
```

### Builder Pattern

```java
public class Person {
    String name;
    int age;
    String email;
    String phone;
    
    private Person(Builder builder) {
        this.name = builder.name;
        this.age = builder.age;
        this.email = builder.email;
        this.phone = builder.phone;
    }
    
    public static class Builder {
        String name;
        int age;
        String email;
        String phone;
        
        public Builder name(String name) {
            this.name = name;
            return this;
        }
        
        public Builder age(int age) {
            this.age = age;
            return this;
        }
        
        public Builder email(String email) {
            this.email = email;
            return this;
        }
        
        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }
        
        public Person build() {
            return new Person(this);
        }
    }
}

Person person = new Person.Builder()
    .name("Alice")
    .age(30)
    .email("alice@example.com")
    .build();
```

### Iterator Pattern

```java
public class MyList<T> implements Iterable<T> {
    private T[] items;
    private int size;
    
    @Override
    public Iterator<T> iterator() {
        return new MyIterator();
    }
    
    private class MyIterator implements Iterator<T> {
        int index = 0;
        
        @Override
        public boolean hasNext() {
            return index < size;
        }
        
        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return items[index++];
        }
        
        @Override
        public void remove() {
            // Remove current element
        }
    }
}

MyList<String> list = new MyList<>();
for (String item : list) {  // Uses iterator
    System.out.println(item);
}
```

### Undo/Redo Pattern

```java
public class Editor {
    private String currentState;
    private List<String> history = new ArrayList<>();
    
    public void setText(String text) {
        history.add(currentState);
        currentState = text;
    }
    
    public void undo() {
        if (!history.isEmpty()) {
            currentState = history.remove(history.size() - 1);
        }
    }
    
    public String getText() {
        return currentState;
    }
}

Editor editor = new Editor();
editor.setText("Hello");
editor.setText("Hello World");
editor.undo();
System.out.println(editor.getText());  // "Hello"
```

### Defensive Copying

```java
public class Team {
    private List<String> members;
    
    public Team(List<String> members) {
        // Copy constructor to avoid external modification
        this.members = new ArrayList<>(members);
    }
    
    public List<String> getMembers() {
        // Return copy to avoid external modification
        return new ArrayList<>(members);
    }
    
    public void setMembers(List<String> members) {
        // Copy argument to avoid external modification
        this.members = new ArrayList<>(members);
    }
}
```

---

## Lab Guide: Practical Progression

This section gives context for each lab based on the course progression.

### Lab 01: Main & Console I/O

**Topics**: String methods, console input/output, control flow

**Key Concepts:**
- `public static void main(String[] args)` and Java 25 `void main()` entry styles
- `System.out.println()` for output
- `System.in` and Scanner for input, and `IO.readln()`/`IO.println()` for Java 25 simple programs
- String methods: `length()`, `charAt()`, `substring()`, `split()`, `replace()`

**What You'll Build:**
String parsing, text manipulation, console interaction

### Lab 02: Object-Oriented Programming

**Topics**: Classes, objects, constructors, methods, transformations

**Key Concepts:**
- Design a class (fields, constructor, methods)
- Pass objects as parameters
- Transform state (e.g., shift, mirror coordinates)
- Parse command-line arguments

**What You'll Build:**
Custom classes, object creation and manipulation

### Lab 04-05: JUnit Testing

**Topics**: Unit testing, test structure, assertions, parameterization

**Key Concepts:**
- Write test methods with `@Test`
- Use assertions: `assertEquals()`, `assertTrue()`
- Parameterized tests with `@CsvSource`
- Run tests with JUnit runner

**What You'll Build:**
Comprehensive test suites, parameterized tests

### Lab 06: Arrays & Enums

**Topics**: Array manipulation, enum types, memory layout

**Key Concepts:**
- Array declaration and initialization
- Jagged arrays
- Enum types and methods
- Stack vs heap visualization

**What You'll Build:**
Array processing, enum usage

### Lab 07: File I/O & Exceptions

**Topics**: Reading/writing files, exception handling, resource management

**Key Concepts:**
- `BufferedReader` and `FileReader` for reading
- `PrintWriter` for writing
- try-catch-finally and try-with-resources
- Checked vs unchecked exceptions

**What You'll Build:**
File reading/writing, exception handling

### Lab 08: Collections - List, Set, Map

**Topics**: ArrayList, HashSet, HashMap, iteration patterns

**Key Concepts:**
- Add/remove from collections
- Iterate with for-each and Iterator
- HashMap key-value patterns
- Nested collections

**What You'll Build:**
Complex data structures, collection operations

### Lab 09: Generics

**Topics**: Generic classes, methods, type parameters, wildcards

**Key Concepts:**
- Generic class declaration `<T>`
- Type inference with `<>`
- Generic methods
- Bounded type parameters

**What You'll Build:**
Reusable generic data structures

### Lab 10: Inheritance

**Topics**: extends, super, method overriding, polymorphism

**Key Concepts:**
- Class inheritance
- Constructor chaining with `super()`
- Override methods with `@Override`
- Polymorphic calls

**What You'll Build:**
Class hierarchies, method overriding

### Lab 11: Interfaces & Equals

**Topics**: Interface implementation, equals(), hashCode(), Comparable

**Key Concepts:**
- Implement multiple interfaces
- Override equals() and hashCode()
- Implement Comparable for ordering
- Collections.sort()

**What You'll Build:**
Complex object comparisons, collections sorting

### Lab 12: Iterators & Advanced Collections

**Topics**: Iterator pattern, custom iterators, Collections utility methods

**Key Concepts:**
- Implement `Iterable` and `Iterator`
- Use `Collections.sort()`, `shuffle()`, `reverse()`
- Iterator.remove() during iteration
- Comparator for custom ordering

**What You'll Build:**
Custom iterables, advanced collection manipulation

---

## Compilation, Execution & Tools

### Compilation

**Basic compilation:**
```bash
javac HelloWorld.java
```

**With classpath (OS-specific separator):**

Windows CMD:
```bat
javac -cp .;checkthat.jar;junit.jar MyClass.java
```

Windows PowerShell (quote classpath because `;` is a command separator):
```powershell
javac -cp ".;checkthat.jar;junit.jar" MyClass.java
```

macOS/Linux (Terminal, bash/zsh):
```bash
javac -cp .:checkthat.jar:junit.jar MyClass.java
```

**Output directory:**
```bash
javac -d bin MyClass.java
# .class files go to bin/ directory
```

**Compile multiple files (portable approach):**
```bash
javac src/*.java
javac -d bin @sources.txt
```

Create `sources.txt` with one `.java` path per line (recursive list), then compile using `@sources.txt`.

**Run a compact source file directly (Java 25 simple style):**
```bash
java Hello.java
```

### Execution

**Basic execution:**
```bash
java HelloWorld
```

**With command-line arguments:**
```bash
java HelloWorld arg1 arg2 arg3
```

**With classpath (OS-specific separator):**

Windows CMD:
```bat
java -cp .;mylib.jar MyClass
```

Windows PowerShell:
```powershell
java -cp ".;mylib.jar" MyClass
```

macOS/Linux:
```bash
java -cp .:mylib.jar MyClass
```

**Fully qualified class names:**
```bash
java com.example.myapp.Main
```

If classes were compiled with `-d bin`, include that output root in classpath:
```bash
java -cp bin com.example.myapp.Main
```

**Simple source style execution examples:**
```bash
java SimpleProgram.java
java Greeting.java
```

### Classpath and Working Directory Rules (Critical)

- `.` means current working directory.
- `..` means parent directory.
- Classpath entries are resolved relative to where you run the command.
- Package execution must be started from classpath root (project root or `bin`, depending on compilation).

Examples:

Windows PowerShell with JAR in parent `lib` folder:
```powershell
java -cp ".;..\lib\checkthat.jar" my.pkg.Main
```

macOS/Linux with JAR in parent `lib` folder:
```bash
java -cp .:../lib/checkthat.jar my.pkg.Main
```

### Running Tests

**JUnit 5 with command line (OS-specific `-cp`):**

Windows CMD:
```bat
java -jar junit5all.jar execute -cp .;checkthat.jar -c MyTestClass -c MyOtherTest
```

Windows PowerShell:
```powershell
java -jar junit5all.jar execute -cp ".;checkthat.jar" -c MyTestClass -c MyOtherTest
```

macOS/Linux:
```bash
java -jar junit5all.jar execute -cp .:checkthat.jar -c MyTestClass -c MyOtherTest
```

**CheckThat verification:**

Windows CMD:
```bat
java -cp .;checkthat.jar checkthat MyStructureTest
```

Windows PowerShell:
```powershell
java -cp ".;checkthat.jar" checkthat MyStructureTest
```

macOS/Linux:
```bash
java -cp .:checkthat.jar checkthat MyStructureTest
```

### Tools

**Disassemble class files:**
```bash
javap MyClass
javap -c MyClass      # Show bytecode
javap -p MyClass      # Show private members
```

**JAR files (Java Archives):**
```bash
jar -cf mylib.jar *.class           # Create JAR
jar -tf mylib.jar                   # List contents
java -jar executable.jar            # Run if has main
```

---

## Common Errors & Debugging

### Compiler Errors

**Symbol not found:**
```
error: cannot find symbol
  symbol:   variable x
```
- Variable name typo
- Variable not declared
- Import missing
- Scope issue

**Type mismatch:**
```
error: incompatible types: int cannot be converted to String
```
- Casting needed
- Wrong parameter type
- Wrong return type

**Method not found:**
```
error: cannot find symbol
  symbol:   method name()
```
- Method doesn't exist
- Case sensitivity
- Wrong class

**Classpath issues:**
```
error: package not found
```
- Class file missing
- Not in classpath
- Wrong package name

### Runtime Errors

**NullPointerException:**
```
Exception in thread "main" java.lang.NullPointerException
  at MyClass.main(MyClass.java:15)
```
- Calling method on null object
- Array element is null
- Check for `null` before use

**ArrayIndexOutOfBoundsException:**
```
Exception in thread "main" java.lang.ArrayIndexOutOfBoundsException: 10
  at MyClass.main(MyClass.java:20)
```
- Accessing array[index] where index >= length
- Check array bounds before accessing

**ClassCastException:**
```
Exception in thread "main" java.lang.ClassCastException
```
- Casting to wrong type
- Check type with `instanceof` first

**NoSuchElementException:**
```
Exception in thread "main" java.util.NoSuchElementException
```
- Iterator has no more elements
- Check `hasNext()` before calling `next()`

### Debugging Techniques

**Print debugging:**
```java
System.out.println("x = " + x);
System.out.println("list size: " + list.size());
System.out.println("Reached here");
```

**Debugging workflow:**
1. Reproduce the error consistently
2. Narrow down location with print statements
3. Check variable values at that point
4. Compare expected vs actual
5. Fix root cause (often off-by-one, null, type)

**Common off-by-one errors:**
```java
// Wrong: loop stops at 4
for (int i = 0; i <= 4; i++) {  // 0, 1, 2, 3, 4 (5 iterations)
}

// Wrong: array out of bounds
for (int i = 0; i <= arr.length; i++) {  // Includes arr.length (too high)
    System.out.println(arr[i]);
}

// Correct
for (int i = 0; i < 5; i++) {  // 0, 1, 2, 3, 4
}

for (int i = 0; i < arr.length; i++) {  // 0 to length-1
    System.out.println(arr[i]);
}
```

---

## Best Practices & Performance

### Code Style

**Naming conventions:**
```java
int count;           // Variables: lowerCamelCase
String firstName;

static final int MAX_SIZE = 100;  // Constants: UPPER_SNAKE_CASE

class Person { }     // Classes: PascalCase
interface Comparable { }  // Interfaces: PascalCase

void calculateTotal() { }  // Methods: lowerCamelCase
```

**Indentation and formatting:**
```java
// 4 spaces per indentation level
public class Example {
    private int value;
    
    public void method() {
        if (value > 0) {
            System.out.println("positive");
        } else {
            System.out.println("non-positive");
        }
    }
}
```

### Null Handling

**Never trust external data:**
```java
// Bad: assumes parameter is not null
public void process(String input) {
    int length = input.length();  // NPE if input is null
}

// Good: check for null
public void process(String input) {
    if (input != null && !input.isEmpty()) {
        int length = input.length();
    }
}

// Good: use Objects utility (Java 7+)
import java.util.Objects;
public void process(String input) {
    Objects.requireNonNull(input, "input cannot be null");
    // Now safe to use input
}
```

### Resource Management

**Always close resources:**
```java
// Bad: resource leak
BufferedReader reader = new BufferedReader(new FileReader("file.txt"));
String line = reader.readLine();
// reader not closed if exception occurs

// Good: try-with-resources (Java 7+)
try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
    String line = reader.readLine();
}  // reader automatically closed
```

### Performance Considerations

**String concatenation:**
```java
// Bad: creates many intermediate String objects
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i + ", ";  // Slow
}

// Good: use StringBuilder
StringBuilder sb = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    sb.append(i).append(", ");
}
String result = sb.toString();
```

**Collection size checks:**
```java
// Bad: rebuilds set each call
if (mySet.size() > 0) {  // O(n) operation
    // ...
}

// Good: use isEmpty()
if (!mySet.isEmpty()) {  // O(1) operation
    // ...
}
```

**Avoid unnecessary object creation:**
```java
// Bad: creates Integer object (boxing)
Integer x = new Integer(10);
Integer y = new Integer(10);
x == y;  // false (different objects)

// Good: use primitive or value equality
int x = 10;
int y = 10;
x == y;  // true

Integer x = 10;  // Autoboxed
Integer y = 10;  // May use cached value
x.equals(y);  // true (use equals for objects)
```

### Avoiding Common Pitfalls

**Modifying collection during iteration:**
```java
// Bad: ConcurrentModificationException
for (String item : list) {
    if (item.equals("remove me")) {
        list.remove(item);  // Don't do this!
    }
}

// Good: use iterator
Iterator<String> it = list.iterator();
while (it.hasNext()) {
    if (it.next().equals("remove me")) {
        it.remove();
    }
}
```

**Comparing objects with `==` instead of `.equals()`:**
```java
// Bad: checks identity, not value
String s1 = new String("Hello");
String s2 = new String("Hello");
if (s1 == s2) {  // false
    System.out.println("Equal");
}

// Good: use equals()
if (s1.equals(s2)) {  // true
    System.out.println("Equal");
}
```

**Forgetting `break` in switch:**
```java
// Bad: falls through to next case
switch (day) {
    case 1:
        System.out.println("Monday");
        // Falls through!
    case 2:
        System.out.println("Tuesday");
        break;  // Finally breaks
}

// Good: add break
switch (day) {
    case 1:
        System.out.println("Monday");
        break;
    case 2:
        System.out.println("Tuesday");
        break;
}
```

### Testing Best Practices

**Test boundary values:**
```java
@Test
public void testBoundaryValues() {
    assertEquals(1, fibonacci(1));
    assertEquals(1, fibonacci(2));
    assertEquals(0, fibonacci(0));
}
```

**Test error conditions:**
```java
@Test
public void testInvalidInput() {
    assertThrows(IllegalArgumentException.class, () -> {
        fibonacci(-1);
    });
}
```

**Use descriptive test names:**
```java
// Good names
public void testAdditionWithPositiveNumbers() { }
public void testDivisionByZeroThrowsException() { }
public void testListRemoveElementAtIndex() { }

// Bad names
public void test1() { }
public void testMethod() { }
public void testStuff() { }
```

---

## Quick Reference

### Primitive Types Recap
| Type | Size | Range | Default |
|------|------|-------|---------|
| byte | 1 | -128 to 127 | 0 |
| short | 2 | -32K to 32K | 0 |
| int | 4 | â‰ˆ-2B to 2B | 0 |
| long | 8 | â‰ˆ-9E18 to 9E18 | 0L |
| float | 4 | IEEE 754 32-bit | 0.0f |
| double | 8 | IEEE 754 64-bit | 0.0d |
| boolean | 1 | true/false | false |
| char | 2 | 0 to 65535 | '\u0000' |

### Access Modifiers Recap
| Modifier | Class | Package | Subclass | World |
|----------|-------|---------|----------|-------|
| public | âœ“ | âœ“ | âœ“ | âœ“ |
| protected | âœ“ | âœ“ | âœ“ | âœ— |
| (package) | âœ“ | âœ“ | âœ— | âœ— |
| private | âœ“ | âœ— | âœ— | âœ— |

### Common Methods Quick Reference

**String:** `length()`, `charAt()`, `substring()`, `split()`, `equals()`, `indexOf()`, `toUpperCase()`, `toLowerCase()`, `replace()`, `contains()`, `startsWith()`, `endsWith()`, `trim()`, `formatted()`

**ArrayList:** `add()`, `remove()`, `get()`, `size()`, `contains()`, `clear()`, `isEmpty()`

**HashMap:** `put()`, `get()`, `remove()`, `containsKey()`, `keySet()`, `values()`, `entrySet()`, `size()`

**Math:** `abs()`, `min()`, `max()`, `pow()`, `sqrt()`, `floor()`, `ceil()`, `round()`

**Array:** `length`, `sort()`, `binarySearch()`, `equals()`, `toString()`, `copyOf()`

---

## Final Recommendations

### For Learning:
1. **Understand concepts first**, then practice implementing them
2. **Read others' code** to see patterns and idioms
3. **Debug strategically** - use print statements, check assumptions
4. **Test as you code** - write tests before or immediately after implementation
5. **Refactor** - clean up code, apply patterns, make it readable

### For Mastery:
1. Complete all lab exercises end-to-end
2. Write more tests than required - test edge cases
3. Study solutions and compare with yours
4. Implement each design pattern 2-3 times
5. Build small projects combining multiple concepts
6. Review compiler errors and understand each one
7. Practice until muscle memory forms

### Resources:
- **JDK Documentation**: https://docs.oracle.com/en/java/javase/25/
- **CheckThat Framework**: Recognizing test patterns is essential
- **Theory Slides**: In course materials under teaching/theory/slides/
- **Lab Exercises**: In teaching/labs/exercises/ with step-by-step guidance
- **Past Assignment Specs**: In grading/tasks/ - see patterns repeated

---

*Last updated: 2026*  
*For questions, review course materials or consult instructor*

---

## Cross-Semester High-Frequency Requirements

This section is derived from multi-semester task and test patterns.

### Reflection-Based Structure Checking (recognition-level)

You are not expected to write reflection-heavy graders, but you should recognize what these checks mean when reading tests.

Frequently encountered grader-side signatures:
- `public Object[] expectedMethods() throws Exception`
- `public Object[] expectedFields() throws Exception`
- `public void assertion() throws Exception`
- `public static Class<?> loadClass(String className) throws Exception`
- `public static Method loadMethod(Class<?> c, String name, Class<?>... params) throws Exception`

Typical intent:
- Verify exact method name, parameter list, return type
- Verify modifiers (`public/private/static/final`)
- Verify constructors and inheritance/interface requirements

### Most-Repeated Student-Level Method Signatures

Across many tasks, these are repeatedly required:
- `public static void main(String[] args)`
- `void main()` in compact Java 25 source style
- `public String toString()`
- `public boolean equals(Object other)`
- `public int hashCode()`
- `public int compareTo(T other)`
- `public String getName()` and related getters/setters

### Exception Patterns Seen Across Many Semesters

Most notable frequently used exceptions:
- `IllegalArgumentException` (invalid input)
- `RuntimeException` (generic unchecked wrapper)
- `IllegalStateException` (operation invalid in current state)
- `UnsupportedOperationException` (feature intentionally unavailable)
- `NoSuchElementException` (iterator/data exhaustion)
- `NumberFormatException` (parsing)
- `IOException` / `FileNotFoundException` (file operations)

Custom/domain exceptions appear regularly in assignments:
- e.g., `GameException`, `LogicException`, `InvalidBookException`, `WorkflowFormatException`

Practical rule of thumb:
- Use checked exceptions when caller action/recovery is expected.
- Use unchecked exceptions for programming errors or contract violations.

### Repeated Domain Families in Assignments

Common problem types over years:
- Games/simulations (UNO, Battleship, Bingo, board games)
- Commerce/admin (WebShop, Market, Bank, Library)
- Transport/logistics (Airport, Railway, Race timing)
- Workflow/agent-oriented tasks (newer semesters)

What this means for preparation:
- Practice model design (`class` + relationships)
- Practice parsing/validation and robust error handling
- Practice list/set/map transformations and query methods
- Practice tests first for core rules and edge cases

