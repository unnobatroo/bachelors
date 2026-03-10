#ifndef CANVAS_H
#define CANVAS_H

typedef struct {
  unsigned char r;
  unsigned char g;
  unsigned char b;
} Pixel;

typedef struct {
  int width;
  int height;
  Pixel **pixels;
} Canvas;

Canvas init_canvas(int width, int height);
Canvas load_asset(const char *filename);
void free_canvas(Canvas c);
void invert_colors(Canvas *c);
Canvas get_brightest_canvas(Canvas canvases[], int size);

#endif
