# Determine the greatest common divisor of two integers.

# INPUT
a = int(input("a: "))
b = int(input("b: "))

# PROCESSING
a, b = max(a, b), min(a, b)
while b != 0:
    temp = b
    b = a % b
    a = temp

# OUTPUT
print(f"GCD: {a}")
