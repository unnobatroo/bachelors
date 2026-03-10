from itertools import groupby
from math import isqrt

def print_highest_score(students):
    """Prints a student with the highest score"""
    result = max(students, key=lambda student: student[1])
    print(f"Highest score: {result[0]} with {result[1]} points")

def print_rle(values):
    """Compresses data by replacing consecutive repeated values
    with a value-count pair"""
    encoded = [(value, len(list(group))) for value, group in groupby(values)]
    print(f"RLE: {encoded}")

def print_101(values):
    """Finds the middle element in a list - the one with equal
    numbers of greater and smaller elements"""
    if len(values) != 101:
        print("List must have exactly 101 elements")
        return

    sorted_values = sorted(values)

    middle = sorted_values[50]
    print(f"Middle: {middle}")

    smaller_count = sum(1 for value in values if value < middle)
    print(f"Smaller count: {smaller_count}")

    greater_count = sum(1 for value in values if value > middle)
    print(f"Greater count: {greater_count}")

def print_weighted(lst1, lst2):
    """Calculates weighted sum"""
    result = sum(value1 * value2 for value1, value2 in zip(lst1, lst2))
    print(f"Weighted sum: {result}")

def proper_divisor_sum(n):
    """Helper function for print_amicable_pair()"""
    if n < 2:
        return 0

    total = 1

    for divisor in range(2, isqrt(n) + 1):
        if n % divisor == 0:
            total += divisor
            pair_divisor = n // divisor
            if pair_divisor != divisor:
                total += pair_divisor

    return total

def print_amicable_pair(values):
    """Determines whether a given integer list contains two amicable numbers.
    Two numbers are amicable if the sum of their proper divisors equals the other"""

    values = [num for num in dict.fromkeys(values) if num > 1]
    sums = {num: proper_divisor_sum(num) for num in values}
    values_set = set(values)

    pairs = []
    seen = set()

    for a in values:
        b = sums[a]
        if b != a and b in values_set and sums[b] == a:
            pair = tuple(sorted([a, b]))
            if pair not in seen:
                pairs.append(pair)
                seen.add(pair)

    if pairs:
        print(f"Amicable pairs: {pairs}")
    else:
        print("No amicable pairs found")

if __name__ == "__main__":
    print_rle([1, 1, 1, 2, 3, 3, 3, 3, 4, 4, 4, 1, 1])
    print_101(list(range(1, 102)))
    print_weighted(list(range(1, 10)), list(range(10, 20)))
    print_amicable_pair([10, 220, 5, 284, 12])
    print_highest_score([("Anna", 80), ("Bob", 92), ("Eva", 88), ("Jaloliddin", -83)])
