# Console drawing

To solve this task, the `terminal_draw.h` library module is available, which provides services for console drawing. The module contains the following elements:

```.c
typedef enum Color {
  BLACK = 30,
  RED = 31,
  GREEN = 32,
  YELLOW = 33,
  BLUE = 34,
  MAGENTA = 35,
  CYAN = 36,
  WHITE = 37
} Color;
```

- `void clear_screen()`: This function clears the entire contents of the terminal window.
- `void get_screen_size(int* width, int* height)`: This function sets the values of the variables referenced by the specified pointers to the number of characters that can be printed in one row and one column, respectively, based on the size of the terminal window.
- `void draw_char(int x, int y, char c, Color color)`: The function prints the specified character in the specified color at the (*x*, *y*) coordinate. The point (0, 0) indicates the upper left corner of the window.
- `sleep_ms(int milliseconds)`: Suspends the program for the specified milliseconds.

# Particle movement

In this task, you have to draw particles that move in a random direction on the screen and bounce back at the edge of the screen. If two particles collide (i.e., end up in the same position), they change their color. However, they do not change direction in the event of a collision.

To solve this problem, create the following types and operations:

- `struct Particle`: A structure representing a particle that stores the *x* and *y* coordinates as well as the direction of the particle's movement. The direction is indicated by the variables *dx* and *dy*, whose possible values ââare -1, 0, 1. For example, if the value of *dx* is 1, the particle moves one position to the right at each step. Or if the value of *dy* is -1, it moves up one position. The particle also has a color. This is encoded by a variable of type `Color`, which can be accessed from `terminal_draw.h`. **(3 points)**
- `draw_border()`: It receives the width and height of the screen as a parameter, and draws a frame of the corresponding size in blue as follows **(5 points)**:

```
-----------
|         |
|         |
|         |
-----------
```
(Note: In Windows terminals there are some glitches sometimes related to the screen size. You could try printing a fixed size 10x20 board during the development)

- `random_in_range()`: This function receives an interval as a parameter, and then returns a random integer falling within this interval. The `rand()` function declared in `stdlib.h` is available for this, which returns a non-negative random number. **(3 points)**
- `random_particle()`: This function receives the width and height of the screen as a parameter, and then creates a particle falling into this window. The position and direction of the particle are random. When generating the direction of movement, consider the instructions in the `struct Particle` section. **(5 points)**
- `draw_particles()`: This function takes an array of particles and the size of the array as a parameter. The function draws all the particles in the array as `'*'` characters at the appropriate position with the color of the particle. **(5 points)**
- `move_particles()`: This function also takes the array of particles with its size, as well as the width and height of the screen. The function's task is to move the particles in the direction specified by their `dx` and `dy` data members. If the particle hits a wall, it bounces off the wall at a 45 degree angle. For example, if the particle is moving upwards (i.e. `dy` was -1) and the particle reaches the upper wall, it starts downwards, so `dy` is changed to 1. **(7 points)**
- `is_colliding()`: This function takes the array of particles and its size as a parameter. The function also takes an integer. If the coordinate of the particle at this index in the array is the same as the coordinate of any other particle, the function returns true, otherwise it returns false. **(7 points)**
- `change_colors()`: This function receives the array of particles and its size as parameters. If the position of a particle is the same as the position of another particle, its color will change. **(6 points)**

At the beginning of the main program, ask the user for an integer, then create an array in heap memory that can hold so many particles. Fill the array with randomly generated particles. **(5 points)**

In the rest of the program, create a loop that performs the following steps in each iteration: **(4 points)**

1. Clear screen
2. Draw border
3. Draw particles
4. Move particles
5. Check for particle collisions (with color change)
6. Wait 300ms

The loop should perform 100 iterations, then the program should end.

## Expectations for the program

- No auxiliary tools should be used for the solution, except for the C reference ([cppreference.com](https://cppreference.com/)).
- The final version of the program must be functional. Compile and run! Code that does not compile is worth 0 points!
- Do not use global variables! Only macros are allowed!
- Strive for nice, clear coding, use indentation, avoid code repetition!
- Don't take up unnecessary memory,