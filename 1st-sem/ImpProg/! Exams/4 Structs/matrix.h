#ifndef MATRIX_H
#define MATRIX_H

#include <stdio.h>
#include <stdlib.h>

typedef struct {
  int rows;
  int cols;
  int *data;
} Matrix;

Matrix *init_matrix(int n, int m);
void free_matrix(Matrix *mat);
int *get_element(Matrix *mat, int row, int col);
Matrix *multiply_matrices(Matrix *a, Matrix *b);
void print_matrix(Matrix *mat);

#endif