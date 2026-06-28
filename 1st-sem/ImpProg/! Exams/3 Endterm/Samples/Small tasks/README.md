# 📐 Circles and Squares (Programming Exercise 1)

This exercise focuses on defining custom types, implementing geometric logic, dynamic memory allocation, and file I/O.

## I. Type Definitions (10 points)

1.  **Enumeration Type (5 points):**
      * Create an enumeration type to represent colors.
      * Name at least **3 colors** in this type.
2.  **Shape Structure Types (5 points):**
      * Create two structures: one for **circles** and one for **squares**.
      * Both structures must store:
          * **Center coordinates** ($x$, $y$) as **integers**.
          * The **color**, given by the enumeration type above.
      * The Circle structure must additionally store the **radius** (integer).
      * The Square structure must additionally store the **side length** (integer).

## II. Required Functions (30 points)

1.  **`is_inside` Function (5 points):**
      * Create a function that takes a **square** and a **circle** as parameters.
      * The function returns a **logical value (boolean)** indicating whether the circle is completely **inside** the square.
      * *Example:* Circle $(x=1, y=2, r=3)$ is inside Square $(x=2, y=0, l=11)$, but not inside Square $(x=2, y=0, l=2)$.
2.  **`get_circles_inside` Function (10 points):**
      * Create a function that takes an **array of circles** and a **square** as parameters.
      * The function should return an **array on the heap** containing only the circles from the input array that are inside the given square.
      * The size of the returned array must be passed back to the caller through a **pointer parameter**.
      * **Crucially:** Ensure there are **no memory leaks**.
3.  **`get_smallest_circle_of_color` Function (10 points):**
      * Create a function that takes an **array of circles** and a **color** as parameters.
      * The function returns a **pointer** to the smallest circle (by radius) of that specified color found in the array.
      * If no circle of that color is found, return a **NULL pointer**.
4.  **Translation Unit Separation (5 points):**
      * Place all the above functions in a **separate translation unit** (e.g., a `.c` file and a `.h` file).
      * Use the **"include guard"** idiom in the header file.

## III. Main Program Logic (10 points)

The program should be executed with a filename as a command-line argument: `./my_program circles.txt`.

  * **File Format:**
      * The first line is a number $N$, indicating the number of subsequent lines.
      * The next $N$ lines describe circles with their $x, y$ coordinates and radius, respectively. The color is initially ignored.
      * *Example:*
        ```text
        3
        1 2 3
        0 0 10
        -2 5 9
        ```
  * **Tasks:**
    1.  Read the circle data from the file.
    2.  Read the data (center $x, y$ coordinates, side length $l$) of **one square** from the **keyboard**.
    3.  Print the **number of circles** inside the square to the screen.

## IV. Bonus Task (Optional, +5 points)

  * Indicate the color of the circles in the input file and read those too.
      * *Example File Format:*
        ```text
        3
        1 2 3 red
        0 0 10 green
        -2 5 9 blue
        ```

# ✍️ Text Replacement Program (Programming Exercise 2)

This exercise focuses on string manipulation, structures, and function design.

## I. Replacement Structure and Functions

1.  **`Replacement` Structure:**

      * Create a structure named `Replacement` with two components: `from` (character) and `to` (character).

2.  **`replace()` Function (In-Place Modification):**

      * Create a function named `replace()`.
      * Parameters: A **string** and an **array of `Replacement` objects**.
      * The function must modify **all characters of the string in-place**, based on the replacement pairs.
      * *"In-place"* means the original string passed by the caller is changed.
      * *Example:*
          * Input: `"This is the input text"`
          * Replacements: `('t', 'T'), ('i', 'I'), (' ', '_')`
          * Modified Input (in-place): `"ThIs_Is_The_InpuT_TexT"`

3.  **`getReplaced()` Function (New String Return):**

      * Create a function named `getReplaced()`.
      * Parameters: A **string** and an **array of `Replacement` objects**.
      * The function must return a **new string** whose characters are changed according to the replacements.
      * The value of the **parameter string should not change**.
      * **Crucially:** Ensure there are **no memory leaks**.

## II. Main Program and Structure

  * In the `main()` function, write **test code** to thoroughly test both `replace()` and `getReplaced()` functions.
  * Read the input texts from the **standard input**.
  * **Separate the program into multiple translation units:**
      * One containing the `main` function.
      * The other containing the transformer functions (`replace`, `getReplaced`).
  * Use the **"header guard"** idiom.

# 🔢 Textual Calculator (Programming Exercise 3)

This exercise involves simulating a simple machine/calculator using an array for memory slots and text-based command processing.

## I. Calculator Setup

  * The calculator has **10 memory slots** with identifiers from 0 to 9.
  * The initial value of all slots is **0**.
  * The array of slots must be allocated on the **heap memory**.
  * **Crucially:** Ensure there are **no memory leaks**.

## II. Required Command Operations

Implement the following operations as functions, each receiving the array of slots as a parameter, along with the required arguments.

| Command | Action | Parameters (Slot Array + Others) | Example | Meaning |
| :--- | :--- | :--- | :--- | :--- |
| `read` | Read a value to a slot. | `(value, slot_number)` | `read 10 1` | Slot \#1 becomes 10. |
| `print` | Print the value of a slot. | `(slot_number)` | `print 9` | Prints the value of Slot \#9. |
| `add` | Addition | `(operand_slot_1, operand_slot_2, result_slot)` | `add 1 2 9` | Slot \#9 = Slot \#1 + Slot \#2. |
| `sub` | Subtraction | `(operand_slot_1, operand_slot_2, result_slot)` | `sub 3 4 5` | Slot \#5 = Slot \#3 - Slot \#4. |
| `mul` | Multiplication | `(operand_slot_1, operand_slot_2, result_slot)` | `mul 3 1 6` | Slot \#6 = Slot \#3 $\times$ Slot \#1. |
| `div` | Integer Division | `(operand_slot_1, operand_slot_2, result_slot)` | `div 7 8 0` | Slot \#0 = Slot \#7 / Slot \#8 (integer division). |

## III. Additional Functions

  * Create a function for computing the **sum** of all numbers in the slots.
  * Create a function for computing the **average** of all numbers in the slots.
      * Both functions get the array of slots as a parameter.

## IV. Main Program and Structure

  * Write **test code** in the `main()` function that:
    1.  Performs a sequence of the operations listed in the table (e.g., `read`, `add`, `mul`, `print`).
    2.  Tests the `sum` and `average` functions.
  * Place the functions (`read`, `print`, `add`, etc., and `sum`, `average`) into a **separate translation unit**.
  * Use the **"include guard"** idiom.