# Lesson 6: Data Types, Arrays, and Pointers

## ⚠️ Reminder

Don’t forget to use the **-Wall -Wextra** flags when compiling C! If stuck, the [C reference](https://en.cppreference.com/w/c) is your friend.

## Data Types

### Character Types
* **Character literals:** How we can print them using the `%c` format specifier and their ASCII values using `%d`.
* See how we can use ASCII values to implement some built-in `ctype.h` functions ourselves (e.g., `islower`, `toupper`, etc.).
* If we have time: implement the **Caesar cipher**: shift each letter by a fixed number of positions in the alphabet (e.g., shift by 3: A -> D, B -> E, …, X -> A, Y -> B, Z -> C).

## Arrays

### Demo / Follow Along
* Learn how we can query the **length of an array** using `sizeof(array) / sizeof(array[0])`.
* Learn about **multi-dimensional arrays (2D arrays)**.
    * How to declare and initialize a 2D array? Do not hardcode the size (use the `sizeof` trick).
    * How are 2D arrays represented? (row-major `[row][col]` vs column-major order)
    * How to iterate over a 2D array using **nested loops**?
* *Do not use variable-length arrays (VLAs).*

### Exercises (possibly for activity points)
1.  Write a program that finds the **maximum value** in an array of integers.
    * How would you change it to find the **minimum**?
    * Change it to also print the **index** of the maximum value.
2.  Write a program that reads a string (character array) and **counts its length**.
    * *Do not use `strlen` from `string.h`.*
3.  Write a program that **swaps the largest and smallest element** in an array of integers.
    * For example, if the input array is `{3, 5, 1, 4, 2}`, the output should be `{3, 1, 5, 4, 2}`.

## Pointers

### Demo / Follow Along
* *For later lessons: pointer arithmetic, multidimensional case, returning pointers from functions.*
* **Introduction to pointers**
    * What is a pointer?
    * The **address-of operator (`&`)** and **dereference operator (`*`)**.
    * Change the value of a variable using a pointer.
    * **NULL** pointers.
    * Create **pointers to pointers** (e.g., `int **pp`), and to different data types (e.g., `char *pc`, `float *pf`). Compare their sizes and how they can be used.
* **Functions**: motivation, how they can be used, signature (parameter and return types), scope of variables.
    * **Example**: write a function that **swaps two integers using pointers**. (Why do we need pointers here?)
    * How to **pass arrays to functions** using pointers (2 common ways).

### Exercises (possibly for activity points)
1.  Write a function that takes a pointer to an integer array and its size, and returns the **sum of its elements**.
2.  **Question**: Can you have a pointer that points to itself? What would its type be?