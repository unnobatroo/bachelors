# Arrays vs. HashSets in Java

### What is an array?
An **Array** is like a row of lockers in a hallway. Every locker is exactly the same size and they are numbered sequentially. Because they are right next to each other in memory, the computer can find `locker[500]` instantly just by calculating the distance from the start.

**How it works:**
1.  You tell Java: `String[] names = new String[5];`
2.  Java reserves 5 "slots" of memory in a single row.
3.  Access is $O(1)$ (instant) because the address is just `start + (index * size)`.

```java
// Array Example
String[] workers = new String[3];
workers[0] = "Alice";
workers[1] = "Bob";
// workers[3] = "Charlie"; // BOOM! ArrayIndexOutOfBoundsException
```

### What is a hash?
To understand a **HashSet**, you have to understand **Hashing**. A **Hash Function** is like a "Digital Blender." You throw an object (like a String "Alice") into the blender, and it spits out a specific number (a "Hash Code").

**The Magic Trick:**
* If you blend "Alice" 1,000 times, you get the **exact same number** every time.
* If you blend "Alic**e**" (lowercase), you get a **completely different** number.

**How a HashSet works:**
1.  You call `set.add("Alice")`.
2.  Java runs "Alice" through the blender to get a number (e.g., `42`).
3.  It goes straight to "Locker 42" and puts Alice there.
4.  Next time you ask `set.contains("Alice")`, it blends "Alice" again, gets `42`, and checks only that one spot!

This makes searching for an item nearly instant, even if you have a million items. 🚀

```java
// HashSet Example
HashSet<String> set = new HashSet<>();
set.add("Alice");
set.add("Alice"); // This does nothing; Alice is already there!
System.out.println(set.size()); // Prints: 1
```

| Feature | Array | HashSet |
| :--- | :--- | :--- |
| **What is it?** | A contiguous block of memory for elements of the same type. | A collection that uses a "hash table" to store unique elements. |
| **Size** | **Fixed**: Once you create it, you can't change the length. | **Dynamic**: It grows automatically as you add items. |
| **Ordering** | **Ordered**: Elements stay in the exact index (0, 1, 2...) you put them in. | **Unordered**: There is no guarantee of internal order. |
| **Duplicates** | **Allowed**: You can have ten "A"s in a row. | **Forbidden**: Only unique elements are stored. |