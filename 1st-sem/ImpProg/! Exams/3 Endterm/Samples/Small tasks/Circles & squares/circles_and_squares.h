#include <stdbool.h>

#ifndef CIRCLES_AND_SQUARES
#define CIRCLES_AND_SQUARES

typedef enum Colour { GREEN, PINK, ORANGE, RED, BLUE } Colour;

typedef struct ColourMap {
  const char *name;
  Colour value;
} ColourMap;

typedef struct Square {
  int x_coord, y_coord;
  Colour colour;
  int side_length;
} Square;

typedef struct Circle {
  int x_coord, y_coord;
  Colour colour;
  int radius;
} Circle;

bool is_inside(Square *square, Circle *circle);
Circle *get_circles_inside(Circle *circles_arr, int size_circles_arr,
                           Square *square, int *circles_count);
Circle *get_smallest_circle_of_color(Circle *circles_arr, int size_circles_arr,
                                     Colour *colour);

#endif // CIRCLES_AND_SQUARES
