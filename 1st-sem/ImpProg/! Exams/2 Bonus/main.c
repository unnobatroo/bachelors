#include "task_manager.h"
#include <stdio.h>
#include <stdlib.h>

void display_menu() {
  printf("\n");
  printf("1. List tasks\n");
  printf("2. Add task\n");
  printf("3. Exit\n");
}

int main(int argc, char *argv[]) {
  if (argc != 2) {
    fprintf(stderr, "Usage: %s <filename>", argv[0]);
    return EXIT_FAILURE;
  }

  const char *database_filename = argv[1];
  int selection;
  int running = 1;

  while (running == 1) {
    display_menu();

    if (scanf("%d", &selection) != 1) {
      fprintf(stderr, "Please enter a number.\n");
      while (getchar() != '\n')
        ;
      continue;
    }

    while (getchar() != '\n')
      ;

    switch (selection) {
    case 1:
      list_tasks(database_filename);
      break;

    case 2:
      add_task(database_filename);
      break;

    case 3:
      running = 0;
      break;

    default:
      fprintf(stderr, "Please choose either 1, 2, or 3.\n");
      break;
    }
  }

  return EXIT_SUCCESS;
}