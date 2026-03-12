def print_chess_board():
    """Draw an 8×8 empty chessboard using nested for loops.
    Print [] for dark fields and two spaces for light fields.
    Use divisibility by two to decide the field."""

    str = ""
    for i in range(8):
        for j in range(8):
            if (i + j) % 2 == 0:
                str += "[]"
            else:
                str += "  "
        str += "\n"
    print(str)

import math

def is_prime(a):
    """Read a number and determine whether it is prime. Use a for
    loop and stop the loop early using break when a divisor is
    found."""
    for i in range (2, int(math.sqrt(a)) + 1):
        if a % i == 0:
            return False
    return True

if __name__ == "__main__":
    print_chess_board()

    # print all prime numbers up to 100 using for loops.
    for i in range(1,100):
        if is_prime(i):
            print(f"AHA! The number -{i}- is prime.")
