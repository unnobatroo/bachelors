#include <stdio.h>

int main() {
    int a[] = { 1, 3, 3, 7 };
    int b[] = { 9, 8, 7, 6 };
    int result = 0;

    // TODO
    for (int i = 0; i < sizeof(a) / sizeof(a[0]); i++){
        result += a[i] * b[i];
    }

    printf("Result: %d", result);
    return 0;
}