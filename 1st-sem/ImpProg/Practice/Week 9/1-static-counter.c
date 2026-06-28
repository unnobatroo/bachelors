#include <stdio.h>

// Write a function called void visit_counter(void) that prints how many times it has been called.
// The function should take no parameters and return nothing.
// Use a static local variable to keep track of the number of calls.
// Write a main function that calls visit_counter() in a loop 5 times.
// What would happen if you removed the static keyword? What would the program print, and why?

void visit_counter(void)
{
    static int n_times_called = 0;
    n_times_called++;
    printf("%d", n_times_called);
}

int main(void)
{
    visit_counter();
    printf("\n");
    visit_counter();
}