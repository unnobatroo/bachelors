# Canvas

Create a lightweight graphics engine that can handle images entirely in memory. Because the hardware is limited, you cannot use standard fixed-size 2D arrays (like `pixel[100][100]`). Instead, you must build a system that allocates memory dynamically based on the exact width and height required at runtime.

You also need to implement a file parser to load assets from disk into this in-memory representation. The rendering of the loaded images will be done by the provided **main.c** file.

Download the Canvas folder, and write your code inside this folder. Upon finishing the exam upload a zip file of this folder.

You must split your code into three files:

- **canvas.h**: All structure definitions, constants, and function declarations.
- **canvas.c**: The implementation of all functions.
- **main.c**: This file is provided for you already inside the canvas folder. The file consists of the functions `print_canvas()` and another function to load all the assets (images). There is a menu program to test the solution. NOTE: Don't edit this file if it is not needed.

## The Header File canvas.h **(5 Points)**

- Define a struct named `Pixel` containing three `unsigned char` members: `r`, `g`, `b`.
- Define a struct (or typedef) named `Canvas`. It must contain:
  - `int width`
  - `int height`
  - `Pixel **pixels` - It can alternatively be a 2-dimensional array for less points. See the details at `init_canvas()` function below.
- Make sure to use the header guards.
- Create function declarations as described below.

## The Implementation File canvas.c

Separate the program to 2 compilation units, joined with the header file: **main.c**, **canvas.c**, **canvas.h**. **(4 points)**

Create the following functions:

1. `Canvas init_canvas(int width, int height)`  **(10 Points)**

    - Declare a `Canvas` object.
    - Allocate memory for the "image" (using the `Pixel` struct, the height, and the width).
    - Return the initialized `Canvas`.
    - If the memory allocation was not successful, then return an empty canvas with a size of 0x0.
    - Make sure to avoid memory leak. **(4 points)**

    To represent the image, use an array-of-arrays layout: a `Pixel**` where each element (`Pixel*`) points to a row containing width pixels. In other words, you allocate:

    - One array of `Pixel*` elements: pointers for each row ("height" number of elements represented vertically)
    - For each separate row, an array of `Pixel` values of size "width" (represented horizontally)

    The following figure represents a 3x3 image:

    ```
           Pixel**                     width
      ^ ┌────────────┐    <─────────────────────────────>
      │ │            │    ┌─────────┬─────────┬─────────┐
      │ │ Pixel* p ──┼──> │ (r,g,b) │ (r,g,b) │ (r,g,b) │  Pixel[3]
      │ │            │    └─────────┴─────────┴─────────┘
    h │ ├────────────┤
    e │ │            │    ┌─────────┬─────────┬─────────┐
    i │ │ Pixel* p ──┼──> │ (r,g,b) │ (r,g,b) │ (r,g,b) │  Pixel[3]
    g │ │            │    └─────────┴─────────┴─────────┘
    h │ ├────────────┤
    t │ │            │    ┌─────────┬─────────┬─────────┐
      │ │ Pixel* p ──┼──> │ (r,g,b) │ (r,g,b) │ (r,g,b) │  Pixel[3]
      │ │            │    └─────────┴─────────┴─────────┘
      v └────────────┘
    ```

    **Alternative solution:**

    You may want to represent the canvas as a 2-dimensional array with the size of 10x10. In this case you'll be able to read only **cat.txt** and **dog.txt**. However, using this representation, the points for dynamic memory allocation can't be scored.

2. `Canvas load_asset(const char *filename)`  **(10 Points)**

    - The first line will represent (Width, Height).
    - Call `init_canvas()` to create an empty canvas with the width and height values read from the file.
    - The rest of the lines in the file are a Width x Height matrix, where every element of the matrix consists of 3 numbers that represent a `Pixel` (`r`, `g`, and `b`). Read this matrix and save it in the pixels field of the canvas.
    - Return the filled canvas.

3. `void free_canvas(Canvas c)`  **(5 Points)**

    - Free the Pixels and the Canvas objects.

4. `void invert_colors(Canvas* c)`  **(10 Points)**

    - Iterate over the entire image (the pixels field) of a Canvas.
    - Invert every pixel. The inversion formula is: new_color = 255 - old_color.
    - Do this for `r`, `g`, and `b` components.

5. `Canvas get_brightest_canvas(Canvas canvases[], int size)` **(12 Points)**

    - Return the brightest image (Canvas) from the array canvases.
    - The brightest canvas is the canvas in which the average brightness is the highest.
    - The average brightness of a canvas is: ∑(r+g+b)/(total number of pixels * 3)

# Requirements

- For solving the task, only https://cppreference.com/ can be used. No electric device is allowed besides the lab machine. All electric device must be in your bag and your bag must be besides the wall of the room. When we find an electric device on you, or ask to empty pockets and you're not willing to, then it results the failure of the subject. We are using IP address monitoring. If any other account is involved than yours, then it results the failure of the sibject.
- The submitted code must compile. In case of compiler error the solution scores 0 points.
- Strive for nice and elegant coding conventions. Use proper indentation, and avopy copy-paste codes.
- If the task doesn't specify requirements on the way of solution, then it can be done arbitrarily.
