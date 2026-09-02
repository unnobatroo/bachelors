#include "task_manager.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX_TASK_LEN 256

int list_tasks(const char *filename) {
  FILE *file_ptr;
  char line[MAX_TASK_LEN];
  int task_count = 0;
  int file_not_empty = 0;

  file_ptr = fopen(filename, "r");

  if (file_ptr == NULL) {
    printf("\n[Your to-do list is empty]\n");
    return 0;
  }

  while (fgets(line, sizeof(line), file_ptr) != NULL) {
    size_t len = strlen(line);
    if (len > 0 && line[len - 1] == '\n') {
      line[len - 1] = '\0';
      len--;
    }
    
    if (len > 0) {
      file_not_empty = 1;
      break;
    }
  }

  if (!file_not_empty) {
    printf("\n[Your to-do list is empty]\n");
    fclose(file_ptr);
    return 0;
  }

  rewind(file_ptr);

  printf("\nYOUR TASKS:\n");

  while (fgets(line, sizeof(line), file_ptr) != NULL) {
    size_t len = strlen(line);
    if (len > 0 && line[len - 1] == '\n') {
      line[len - 1] = '\0';
    }

    if (line[0] != '\0') {
      task_count = task_count + 1;
      printf("%d. %s\n", task_count, line);
    }
  }
  
  if (fclose(file_ptr) != 0) {
    perror("Error while closing file after reading");
    return -1;
  }

  return 0;
}

int add_task(const char *filename) {
  FILE *file_ptr;
  char task_name[MAX_TASK_LEN];
  
  printf("Enter task: ");

  if (fgets(task_name, sizeof(task_name), stdin) == NULL) {
    fprintf(stderr, "Error while reading input.\n");
    return -1;
  }

  size_t len = strlen(task_name);
  if (len > 0 && task_name[len - 1] == '\n') {
    task_name[len - 1] = '\0';
  }

  if (task_name[0] == '\0') {
    printf("Task not added, empty input.\n");
    return 0;
  }
  
  file_ptr = fopen(filename, "a");
  if (file_ptr == NULL) {
    perror("Error while opening file for appending.");
    return -1;
  }

  if (fprintf(file_ptr, "%s\n", task_name) < 0) {
    perror("Error while writing task to file.");
    fclose(file_ptr);
    return -1;
  }
  
  printf("Success!\n");

  if (fclose(file_ptr) != 0) {
    perror("Error closing file after writing");
    return -1;
  }

  return 0;
}