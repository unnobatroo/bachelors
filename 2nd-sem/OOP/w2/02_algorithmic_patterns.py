#!/usr/bin/env python3

input: list[int] = [1, 2, 5, 8, 3, 4, 1]
# input = []

# Summation
s = sum(input)
print(f"Sum: {s}")

s = sum(e for e in input if e % 2 == 0)
print(f"Sum of even numbers: {s}")

# Counting
c = sum(1 for e in input if e % 2 == 0)
print(f"Count of even numbers: {c}")

# Linear search
is8 = 8 in input
print(f"Is there an 8? {is8}")

first_even = next((e for e in input if e % 2 == 0), None)
if first_even:
    print(f"The first even number: {first_even}")
else:
    print("No even numbers")

# Maximum selection

try:
    m = max(input)
    print(f"Maximum element: {m}")
except ValueError:
    print("Empty list")

# Conditional maximum search

try:
    m = max(e for e in input if e % 2 == 1)
    print(f"Maximum odd element: {m}")
except ValueError:
    print("No odd numbers")
