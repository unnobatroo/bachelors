import sys

# 1. Check if the correct number of arguments was provided.
if len(sys.argv) != 4:
    print("Usage: python3 calc.py <num1> <operator> <num2>")
    sys.exit(1)  # Exit the script with a non-zero status to indicate an error.

# 2. Access arguments. Remember they are all strings.
num1_str = sys.argv[1]
op = sys.argv[2]
num2_str = sys.argv[3]

# 3. Convert string arguments to numbers, with error handling.
try:
    num1 = float(num1_str)
    num2 = float(num2_str)
except ValueError:
    print("Error: Both numbers must be valid numeric values.")
    sys.exit(1)

# 4. Perform the calculation based on the operator.
if op == "+":
    result = num1 + num2
elif op == "-":
    result = num1 - num2
elif op == "*":
    result = num1 * num2
elif op == "/":
    result = num1 / num2
else:
    print(f"Error: Unknown operator '{op}'")
    sys.exit(1)

print(f"Result: {result}")
