#ifndef TERMINAL_DRAW_H
#define TERMINAL_DRAW_H

#include <stdarg.h>

typedef enum Color {
  BLACK = 30,
  RED = 31,
  GREEN = 32,
  YELLOW = 33,
  BLUE = 34,
  MAGENTA = 35,
  CYAN = 36,
  WHITE = 37,
} Color;

#define NUM_COLORS 8

// Only the prototypes (signatures) remain here
void clear_screen(void);
void get_screen_size(int *width, int *height);
void draw_char(int x, int y, char c, Color color);
void print_at(int x, int y, const char *format, ...);
void sleep_ms(int milliseconds);

#endif
