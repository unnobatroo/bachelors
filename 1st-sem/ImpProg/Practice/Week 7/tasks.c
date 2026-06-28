#include <stdio.h>

int sum_array(const int *array_ptr, int size);

int main() {
    const int arr[] = {1, 2, 3, 4, 5};
    const int *p_arr = arr;
    const int len = sizeof(arr) / sizeof(arr[0]);
    int idx = 0;
    int sum = 0;

    while (idx < len) {
        sum += *p_arr;
        idx++;
    }

    printf("%d\n", sum);
    return 0;
}

/* Write a C function that calculates the sum of an array's elements,
taking a pointer to the array and its size as parameters. */
int sum_array(const int *array_ptr, int size) {
    int sum = 0;
    for (int i = 0; i < size; i++) {
        sum += *(array_ptr + i);
    }
    return sum;
}
