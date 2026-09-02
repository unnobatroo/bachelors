#include "circles_and_squares.h"
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

bool is_inside(Square *square, Circle *circle) {
  // square's info
  int sq_x = square->x_coord;
  int sq_y = square->y_coord;
  int sq_l = square->side_length / 2;

  // circle's info
  int cr_x = circle->x_coord;
  int cr_y = circle->y_coord;
  int cr_r = circle->radius;

  // check for X coordinate:
  if (sq_x - sq_l <= cr_x - cr_r && sq_x + sq_l >= cr_x + cr_r) {
    // check for Y coordinate:
    if (sq_y - sq_l <= cr_y - cr_r && sq_y + sq_l >= cr_y + cr_r) {
      return true;
    }
  }

  return false;
}

Circle *get_circles_inside(Circle *circles_arr, int size_circles_arr,
                           Square *square, int *circles_count) {

  Circle *fit_cr_arr = NULL;
  int fit_count = 0;

  for (int i = 0; i < size_circles_arr; ++i) {
    if (is_inside(square, &circles_arr[i])) {
      ++fit_count;
      Circle *temp = (Circle *)realloc(fit_cr_arr, fit_count * sizeof(Circle));

      if (temp == NULL) {
        free(fit_cr_arr);

        // reset the count because of an error:
        *circles_count = 0;

        fprintf(stderr, "Error allocating memory during realloc.");
        return NULL;
      }

      fit_cr_arr = temp;

      // copy the fitting circle into the newly created slot:
      fit_cr_arr[fit_count - 1] = circles_arr[i];
    }
  }

  *circles_count = fit_count;

  return fit_cr_arr;
}

Circle *get_smallest_circle_of_color(Circle *circles_arr, int size_circles_arr,
                                     Colour *colour) {
  Circle *smallest = NULL;
  for (int i = 0; i < size_circles_arr; ++i) {
    if (circles_arr[i].colour == *colour) {
      if (smallest == NULL || circles_arr[i].radius < smallest->radius) {
        smallest = &circles_arr[i];
      }
    }
  }

  return smallest;
}
