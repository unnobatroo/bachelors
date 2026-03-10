#include <stdio.h>
#include <stdlib.h>

// Write two functions that call each other using mutual recursion. One of them,
// let’s call it a, divides the value received as a parameter by 2, then calls
// function b with the result if it is greater than 0. Function b subtracts 1
// from the received value, then calls a with the decreased value if it is still
// greater than 0. Ask the user for the initial number and count how many a–b
// iterations occur until it reaches 0 (store the counter as a global variable).

int iteration_count = 0;

void a(int n);
void b(int n);

void a(int n)
{
    if (n <= 0)
    {
        return;
    }

    int next_val = n / 2;

    if (next_val > 0)
    {
        iteration_count++;
        b(next_val);
    }
}

void b(int n)
{
    if (n <= 0)
    {
        return;
    }

    int next_val = n - 1;

    if (next_val > 0)
    {
        a(next_val);
    }
}

int main(void)
{
    int initial_number;

    if (scanf("%d", &initial_number) != 1)
    {
        fprintf(stderr, "Invalid input.\n");
        return 1;
    }

    if (initial_number <= 0)
    {
        return 0;
    }

    a(initial_number);

    printf("Total 'a-b' iterations completed: %d\n", iteration_count);

    return 0;
}
