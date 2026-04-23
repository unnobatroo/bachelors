# Object relationships

### Dependency: "using"

A dependency occurs when one class temporarily uses another. This is a short-term
connection. Class A depends on Class B if an object of Class A receives an object of Class B as a
method parameter or creates one locally to call its methods.

```java
class Teacher {
    void evaluate(Answer ans) { /* logic */ }
}

class Student {
    // dependency: student "needs" a teacher during the exams
    void takeExam(Teacher t) {
        t.evaluate(new Answer());
    }
}
```

### Association: "long-term"

**Association** represents a lasting connection where objects continuously send messages to each other. It describes
multiple relationships between objects. For example, a `Person` can write many `Books`,
and a `Book` can have multiple authors. These often have "multiplicity" (like 1-to-many) and "navigability" (which
object can find the other).

```java
class Author {
    private String name;
    private final List<Book> books = new ArrayList<>(); // association: an author "knows" their books

    void writeBook(Book b) {
        books.add(b);
    }
}

class Book {
    private String title;
    private final List<Author> authors = new ArrayList<>();
}
```

### Aggregation

A weak "part-of" link. The part can exist without the whole.

```java
class Playground {
    private final Slide slide;

    public Playground(Slide s) {
        this.slide = s;
    }
}
```

### Composition

A strong "part-of" link. The part cannot exist without its whole

```java
class Book {
    private final List<Page> pages;

    public Book() {
        this.pages = new ArrayList<>();
        pages.add(new Page(1));
    }
}
```

### Inheritance: "is-a"

Inheritance is used for generalisation - creating a parent, or specialisation - creating a
child. A child class, or a subclass, inherits all public and protected fields and methods from its
parent, base class.

```java
class Point {
    protected double x, y, z; // protected so subclasses can see them

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

class Sphere extends Point {
    private final double radius;

    public Sphere(double x, double y, double z, double r) {
        super(x, y, z); // "super" calls point's constructor
        this.radius = r;
    }
}
```