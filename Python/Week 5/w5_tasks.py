def is_leap_year() -> None:
    """ Write a function that determines whether the number is a leap year from the
        standard input. A leap year is any year divisible by four, except those
        divisible by a hundred. However, those divisible by four hundred will also be
        leap years. (DO NOT use if in the solution!)"""
    year = int(input("Enter a year to check: "))
    print((year % 4 == 0 and year % 100 != 0) or year % 400 == 0)

def print_primes_sum(start: int, end: int) -> None:
    """Write a function that calculates the sum of all prime numbers in given range."""
    def is_prime(num: int) -> bool:
        if num < 2:
            return False
        divs = [i for i in range(2, num) if num % i == 0]
        return len(divs) == 0

    print(sum(i for i in range(start, end) if is_prime(i)))

def print_nested_dicts_count(dictionary: dict) -> None:
    """Write a function that counts the total number of keys in a nested dictionary."""
    def count_keys(d: dict) -> int:
        count = len(d)
        for value in d.values():
            if isinstance(value, dict):
                count += count_keys(value)
        return count

    print(count_keys(dictionary))

if __name__ == "__main__":
    # is_leap_year()

    print_primes_sum(2, 10)

    sample_dict = {'a': 1, 'b': {'c': 2, 'd': {'e': 3}}}
    print_nested_dicts_count(sample_dict)
