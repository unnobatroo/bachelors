# Determine whether two integers are amicable. Two integers are amicable
# if the sum of the divisors of one smaller than itself is equal to the other,
# and vice versa. E.g.: 220 and 284.

def proper_divisor_sum(n):
    if n <= 1:
        return 0

    return 1 + sum(
        i + (n // i if i != n // i else 0)
        for i in range(2, int(n ** 0.5) + 1)
        if n % i == 0
    )

# INPUT
num1 = int(input("num1: "))
num2 = int(input("num2: "))

sum1 = proper_divisor_sum(num1)
sum2 = proper_divisor_sum(num2)

print("YEPPIE!" if sum1 == num2 and sum2 == num1 else "No :(")
