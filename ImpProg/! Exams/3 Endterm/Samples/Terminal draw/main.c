#include "terminal_draw.h"
#include <stdbool.h>

struct Rectangle {
  int x, y;
  int width, height;
};

typedef struct Rectangle Rectangle;

void draw_rectangle(Rectangle r, Color c) {
  for (int x = 0; x < r.width; ++x)
    draw_char(x + r.x, r.y, 'x', c);

  for (int x = 0; x < r.width; ++x)
    draw_char(x + r.x, r.y + r.height - 1, 'x', c);

  for (int y = 0; y < r.height; ++y)
    draw_char(r.x, y + r.y, 'x', c);

  for (int y = 0; y < r.height - 1; ++y)
    draw_char(r.x + r.width - 1, y + r.y, 'x', c);
}

bool is_inside(Rectangle r, int x, int y) {
  return x > r.x && x < r.x + r.width - 1 && y > r.y && y < r.y + r.height - 1;
}

void draw_border(int width, int height) {
  for (int x = 0; x < width; ++x)
    draw_char(x, 0, '-', GREEN);
  for (int x = 0; x < width; ++x)
    draw_char(x, height - 1, '-', GREEN);
  for (int y = 1; y < height - 1; ++y)
    draw_char(0, y, '|', GREEN);
  for (int y = 1; y < height - 1; ++y)
    draw_char(width - 1, y, '|', GREEN);
}

int main() {
  int width, height;
  get_screen_size(&width, &height);

  Rectangle r1;
  r1.x = 5;
  r1.y = 3;
  r1.width = 10;
  r1.height = 4;

  while (1) {
    clear_screen();
    draw_border(width, height);
    draw_rectangle(r1, RED);

    for (int x = 0; x < width; ++x) {
      for (int y = 0; y < height; ++y) {
        if (is_inside(r1, x, y)) {
          draw_char(x, y, '*', BLUE);
        }
      }
    }
    sleep_ms(500);
  }
}
