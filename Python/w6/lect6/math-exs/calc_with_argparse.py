import argparse

VALID_OPERATORS = ("+", "-", "*", "/")


def main():
    parser = argparse.ArgumentParser(
        description="Simple calculator: compute num1 operator num2.",
    )
    parser.add_argument(
        "num1",
        type=float,
        help="First number",
    )
    parser.add_argument(
        "operator",
        choices=VALID_OPERATORS,
        help="Operator: +, -, *, /",
    )
    parser.add_argument(
        "num2",
        type=float,
        help="Second number",
    )
    args = parser.parse_args()

    num1, op, num2 = args.num1, args.operator, args.num2

    if op == "+":
        result = num1 + num2
    elif op == "-":
        result = num1 - num2
    elif op == "*":
        result = num1 * num2
    elif op == "/":
        result = num1 / num2
    else:
        raise ValueError(f"Unknown operator '{op}'")

    print(f"Result: {result}")


if __name__ == "__main__":
    main()
