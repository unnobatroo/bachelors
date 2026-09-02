#include "matrix.h"
#include <stdio.h>

int main() {
  Matrix *m1 = init_matrix(2, 3);
  Matrix *m2 = init_matrix(3, 2);

  if (m1 && m2) {
    int val = 1;

    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 3; j++) {
        *get_element(m1, i, j) = val++;
      }
    }

    val = 7;

    for (int i = 0; i < 3; i++) {
      for (int j = 0; j < 2; j++) {
        *get_element(m2, i, j) = val++;
      }
    }

    print_matrix(m1);
    print_matrix(m2);

    Matrix *result = multiply_matrices(m1, m2);

    if (result) {
      printf("product (M1 × M2) -> ");
      print_matrix(result);
      free_matrix(result);
    }
  }

  free_matrix(m1);
  free_matrix(m2);
  return 0;
}