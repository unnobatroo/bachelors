#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
  char *name;
  int quantity;
} Fruit;

static Fruit fruits[100] = {0};
static int list_size = sizeof(fruits) / sizeof(Fruit);

Fruit *find_a_fruit(char *fruit_name);

int main(int argc, char *argv[]) {
  if (argc != 2) {
    fprintf(stderr, "Usage: %s <filename>\n", argv[0]);
    return 1;
  }

  FILE *f = fopen(argv[1], "r+");
  return 0;
}

Fruit *find_a_fruit(char *fruit_name) {
  for (int i = 0; i < list_size; ++i) {
    if (strcmp(fruit_name, fruits[i].name) == 0) {
      return &fruits[i];
    }
  }

  return NULL;
}