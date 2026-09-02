#include "matrix.h"

Matrix *init_matrix(int n, int m) {
  if (n < 1 || m < 1) {
    return NULL;
  }

  Matrix *mat = (Matrix *)malloc(sizeof(Matrix));
  if (mat == NULL) {
    return NULL;
  }

  mat->rows = n;
  mat->cols = m;

  mat->data = (int *)malloc(n * m * sizeof(int));

  if (mat->data == NULL) {
    free(mat);
    return NULL;
  }

  for (int i = 0; i < n * m; i++) {
    mat->data[i] = 0;
  }

  return mat;
}

void free_matrix(Matrix *mat) {
  if (mat == NULL) {
    return;
  }

  free(mat->data);
  free(mat);
}

int *get_element(Matrix *mat, int row, int col) {
  if (mat == NULL) {
    return NULL;
  }

  if (row < 0 || row >= mat->rows || col < 0 || col >= mat->cols) {
    fprintf(stderr, "index (%d, %d) is out of bounds!\n", row, col);
    return NULL;
  }

  return &(mat->data[row * mat->cols + col]);
}

Matrix *multiply_matrices(Matrix *a, Matrix *b) {
  if (a == NULL || b == NULL) {
    return NULL;
  }

  if (a->cols != b->rows) {
    return NULL;
  }

  Matrix *result = init_matrix(a->rows, b->cols);

  if (result == NULL) {
    return NULL;
  }

  for (int i = 0; i < a->rows; i++) {
    for (int j = 0; j < b->cols; j++) {
      int sum = 0;

      for (int k = 0; k < a->cols; k++) {
        sum += a->data[i * a->cols + k] * b->data[k * b->cols + j];
      }

      result->data[i * result->cols + j] = sum;
    }
  }

  return result;
}

void print_matrix(Matrix *mat) {
  if (mat == NULL) {
    return;
  }

  printf("matrix (%dx%d):\n", mat->rows, mat->cols);

  for (int i = 0; i < mat->rows; i++) {
    printf("[ ");

    for (int j = 0; j < mat->cols; j++) {
      printf("%3d ", *get_element(mat, i, j));
    }

    printf("]\n");
  }

  printf("\n");
}