#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct Box {
  int weight;
  struct Box *next;
} Box;

Box *top;

void initialize();
bool is_empty();
int peek();
void push(int weight);
int pop();
void print_stack();

int main(void) {
  initialize();
  printf("Is initialised.\n");
  printf("Is empty? %s\n", is_empty() ? "Yes." : "No.");

  push(10);
  printf("\nPushed 10.\n");
  push(50);
  printf("Pushed 50.\n");
  push(30);
  printf("Pushed 30.\n");

  printf("\nIs empty? %s\n", is_empty() ? "Yes." : "No.");

  printf("\nThe weight of the top box is: %d\n", peek());

  while (!is_empty())
    pop();

  return 0;
}

void initialize() { top = NULL; }

bool is_empty() { return top == NULL; }

int peek() {
  if (is_empty()) {
    fprintf(stderr, "Error: empty stack.\n");
    exit(EXIT_FAILURE);
  }
  return top->weight;
}

void push(int weight) {
  Box *new_box = (Box *)malloc(sizeof(Box));

  if (new_box == NULL) {
    fprintf(stderr, "Error: malloc() failed.\n");
    exit(EXIT_FAILURE);
  }

  new_box->weight = weight;
  new_box->next = top;
  top = new_box;
}

int pop() {
  if (is_empty()) {
    fprintf(stderr, "Error: empty stack.\n");
    exit(EXIT_FAILURE);
  }

  Box *old = top;
  top = old->next;
  int weight = old->weight;
  free(old);
  return weight;
}

void print_stack() { Box *curr;}