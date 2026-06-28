#include "canvas.h"
#include <stdio.h>
#include <stdlib.h>

Canvas init_canvas(int width, int height) {
  Canvas c;
  c.width = width;
  c.height = height;

  // Allocate array of row pointers
  c.pixels = (Pixel **)malloc(height * sizeof(Pixel *));
  if (c.pixels == NULL) {
    c.width = 0;
    c.height = 0;
    return c;
  }

  // Allocate each row
  for (int i = 0; i < height; i++) {
    c.pixels[i] = (Pixel *)malloc(width * sizeof(Pixel));
    if (c.pixels[i] == NULL) {
      // Free previously allocated rows
      for (int j = 0; j < i; j++) {
        free(c.pixels[j]);
      }
      free(c.pixels);
      c.width = 0;
      c.height = 0;
      c.pixels = NULL;
      return c;
    }
  }

  return c;
}

Canvas load_asset(const char *filename) {
  FILE *file = fopen(filename, "r");
  if (file == NULL) {
    Canvas c;
    c.width = 0;
    c.height = 0;
    c.pixels = NULL;
    return c;
  }

  int width, height;
  fscanf(file, "%d %d", &width, &height);

  Canvas c = init_canvas(width, height);
  if (c.pixels == NULL) {
    fclose(file);
    return c;
  }

  // Read pixel data
  for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
      int r, g, b;
      fscanf(file, "%d %d %d", &r, &g, &b);
      c.pixels[y][x].r = (unsigned char)r;
      c.pixels[y][x].g = (unsigned char)g;
      c.pixels[y][x].b = (unsigned char)b;
    }
  }

  fclose(file);
  return c;
}

void free_canvas(Canvas c) {
  if (c.pixels != NULL) {
    for (int i = 0; i < c.height; i++) {
      free(c.pixels[i]);
    }
    free(c.pixels);
  }
}

void invert_colors(Canvas *c) {
  if (c == NULL || c->pixels == NULL) {
    return;
  }

  for (int y = 0; y < c->height; y++) {
    for (int x = 0; x < c->width; x++) {
      c->pixels[y][x].r = 255 - c->pixels[y][x].r;
      c->pixels[y][x].g = 255 - c->pixels[y][x].g;
      c->pixels[y][x].b = 255 - c->pixels[y][x].b;
    }
  }
}

Canvas get_brightest_canvas(Canvas canvases[], int size) {
  if (size == 0) {
    Canvas c;
    c.width = 0;
    c.height = 0;
    c.pixels = NULL;
    return c;
  }

  int brightest_index = 0;
  double max_brightness = 0.0;

  for (int i = 0; i < size; i++) {
    Canvas c = canvases[i];
    if (c.pixels == NULL || c.width == 0 || c.height == 0) {
      continue;
    }

    long long sum = 0;
    for (int y = 0; y < c.height; y++) {
      for (int x = 0; x < c.width; x++) {
        sum += c.pixels[y][x].r;
        sum += c.pixels[y][x].g;
        sum += c.pixels[y][x].b;
      }
    }

    int total_pixels = c.width * c.height;
    double brightness = (double)sum / (total_pixels * 3);

    if (i == 0 || brightness > max_brightness) {
      max_brightness = brightness;
      brightest_index = i;
    }
  }

  return canvases[brightest_index];
}
