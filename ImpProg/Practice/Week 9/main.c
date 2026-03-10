#include "math_utills.h"
#include <stdio.h>

int main(void)
{
    int n;
    scanf("%d", &n);
    unsigned long long a = factorial(n);
    printf("%llu", a);
}