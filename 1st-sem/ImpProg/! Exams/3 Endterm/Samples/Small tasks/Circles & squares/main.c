#include "circles_and_squares.h"
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

const ColourMap colour_lookup[] = {{"red", RED},
                                   {"green", GREEN},
                                   {"blue", BLUE},
                                   {"orange", ORANGE},
                                   {"pink", PINK}};

int main(int argc, char *argv[]) {
  if (argc != 2) {
    fprintf(stderr, "Usage: %30s <file path>.\n", argv[0]);
    return 1;
  }

  FILE *f = fopen(argv[1], "r");
  if (f == NULL) {
    fprintf(stderr, "Error opening the file: %s\n", argv[1]);
    return 1;
  }

  int num_circles;
  if (fscanf(f, "%d", &num_circles) != 1) {
    fprintf(stderr, "Error reading number of circles from file.\n");
    fclose(f);
    return 1;
  }

  Circle *circles_arr = (Circle *)malloc(sizeof(Circle) * num_circles);
  if (circles_arr == NULL) {
    fprintf(stderr, "Error with memory allocation.\n");
    fclose(f);
    return 1;
  }

  char colour_str[10];
  int lookup_size = sizeof(colour_lookup) / sizeof(colour_lookup[0]);

  for (int i = 0; i < num_circles; ++i) {
    if (fscanf(f, "%d %d %u %9s", &circles_arr[i].x_coord,
               &circles_arr[i].y_coord, &circles_arr[i].radius,
               colour_str) == 4) {

      int found = 0;
      for (int j = 0; j < lookup_size; ++j) {
        if (strcmp(colour_str, colour_lookup[j].name) == 0) {
          circles_arr[i].colour = colour_lookup[j].value;
          found = 1;
          break;
        }
      }

      if (!found) {
        fprintf(stderr,
                "Error: unknown color name '%s' in file at circle %d.\n",
                colour_str, i);
        free(circles_arr);
        fclose(f);
        return 1;
      }

    } else {
      fprintf(stderr, "Error reading circle data at line %d.\n", i + 2);
      free(circles_arr);
      fclose(f);
      return 1;
    }
  }

  // --- START FUNCTION USAGE & TESTING ---

  // 1. TEST: get_smallest_circle_of_color (for RED)
  Colour target_color = RED;
  printf("\n--- Test 1: Finding smallest RED circle ---\n");

  Circle *smallest_red =
      get_smallest_circle_of_color(circles_arr, num_circles, &target_color);

  if (smallest_red != NULL) {
    printf("Smallest RED circle found: Center(%d, %d), Radius: %d\n",
           smallest_red->x_coord, smallest_red->y_coord, smallest_red->radius);
  } else {
    printf("No RED circles found.\n");
  }

  // 2. TEST: get_circles_inside
  // Define a square centered at (0, 0) with a side length of 4.
  // This square should contain the BLUE circle (0, 0, radius 1) from sample.txt
  Square test_square = {0, 0, RED, 4};
  int circles_count = 0;
  printf("\n--- Test 2: Finding circles inside Square (0, 0, side 4) ---\n");

  Circle *inside_circles = get_circles_inside(circles_arr, num_circles,
                                              &test_square, &circles_count);

  printf("Found %d circle(s) inside the square:\n", circles_count);
  for (int i = 0; i < circles_count; ++i) {
    // reverse lookup to print the color name
    char *colour_name = "UNKNOWN";
    for (int j = 0; j < lookup_size; ++j) {
      if (inside_circles[i].colour == colour_lookup[j].value) {
        colour_name = (char *)colour_lookup[j].name;
        break;
      }
    }
    printf("- center(%d, %d), radius: %d, colour: %s\n",
           inside_circles[i].x_coord, inside_circles[i].y_coord,
           inside_circles[i].radius, colour_name);
  }

  // free the memory allocated by get_circles_inside
  if (inside_circles != NULL) {
    free(inside_circles);
  }

  // --- END FUNCTION USAGE & TESTING ---

  // FINAL CLEANUP: Free the array loaded from the file and close the file
  // stream
  free(circles_arr);
  fclose(f);

  return 0;
}
