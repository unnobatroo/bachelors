#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int product(int arg1, int arg2);
int sum(int arg1, int arg2);
int diff(int arg1, int arg2);
int quotient(int arg1, int arg2);

int main(void) {
  int arg1, arg2;
  char operation;

  while (scanf("%d %c %d/n", &arg1, &operation, &arg2) == 3) {
    int res = 0;
    switch (operation) {
    case '/':
      res = quotient(arg1, arg2);
    case '+':
      res = sum(arg1, arg2);
    case '-':
      res = diff(arg1, arg2);
    case '*':
      res = product(arg1, arg2);
    default:
      return 1;
    }

    fprintf(stdout, "%d %c %d = %d", arg1, operation, arg2, res);
  }

  return 0;
}

int product(int arg1, int arg2) { return arg1 * arg2; }

int sum(int arg1, int arg2) { return arg1 + arg2; }

int diff(int arg1, int arg2) { return arg1 - arg2; }

int quotient(int arg1, int arg2) { return (int)arg1 / arg2; }