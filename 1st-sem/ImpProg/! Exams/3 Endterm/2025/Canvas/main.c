#include "canvas.h"
#include <stdio.h>
#include <stdlib.h>

void print_canvas(Canvas c) {
  printf("\n--- Canvas (%dx%d) ---\n", c.width, c.height);

  for (int y = 0; y < c.height; y++) {
    for (int x = 0; x < c.width; x++) {
      Pixel p = c.pixels[y][x];
      printf("\033[38;2;%d;%d;%dm", p.r, p.g, p.b);
      printf("@@");
      printf("\033[0m");
    }
    printf("\n");
  }
  printf("---------------------\n");
}

Canvas *load_all_canvases() {
  int capacity = 4;
  Canvas *canvas_array = malloc(capacity * sizeof(Canvas));
  Canvas c = load_asset("assets/cat.txt");
  canvas_array[0] = c;
  c = load_asset("assets/dog.txt");
  canvas_array[1] = c;
  c = load_asset("assets/fish.txt");
  canvas_array[2] = c;
  c = load_asset("assets/star.txt");
  canvas_array[3] = c;
  return canvas_array;
}

int main() {
  int choice = 0;
  Canvas *canvases = load_all_canvases();
  for (;;) {
    printf("\n=== Menu ===\n");
    printf("1) Print an image\n");
    printf("2) Invert an image\n");
    printf("3) Get the brightest image\n");
    printf("4) Exit\n");
    printf("Your choice: ");
    scanf("%d", &choice);

    if (choice == 4) {
      for (int i = 0; i < 4; i++) {
        free_canvas(canvases[i]);
      }
      free(canvases);
      break;
    }

    if (!canvases) {
      printf("No images are loaded. Put images in ./assets and restart.\n");
      continue;
    }

    switch (choice) {
    case 1: {
      printf("Which image do you want to print?\n");
      printf("0 : Cat\n");
      printf("1 : Dog\n");
      printf("2 : Fish\n");
      printf("3 : Star\n");
      printf("Your choice: ");
      scanf("%d", &choice);
      if (choice < 0 || choice > 3) {
        printf("Invalid choice, please try again!\n");
        break;
      }
      print_canvas(canvases[choice]);
      break;
    }
    case 2: {
      printf("Which image do you want to invert?\n");
      printf("0 : Cat\n");
      printf("1 : Dog\n");
      printf("2 : Fish\n");
      printf("3 : Star\n");
      printf("Your choice: ");
      scanf("%d", &choice);
      if (choice < 0 || choice > 3) {
        printf("Invalid choice, please try again!\n");
        break;
      }
      invert_colors(&canvases[choice]);
      print_canvas(canvases[choice]);
      break;
    }
    case 3: {
      Canvas brightest = get_brightest_canvas(canvases, 4);
      print_canvas(brightest);
      break;
    }
    default:
      printf("Wrong choice, please try again!\n");
      break;
    }
  }

  return 0;
}
