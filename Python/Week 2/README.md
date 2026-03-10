## Logical programming pipeline (IPO: input-process-output model)

```python
# INPUT
a = 10
b = 20

# PROCESSING
total = a + b
avg = total / 2

# PRINT
print(total)
print(avg)
```

### Input

- Hardcoded
- Get from CLI (`scanf()`)
- File (`.xslx`, `.csv` )
- Database
- Network
- Other app-s

### Output

- Printed on CLI
- Into a file
- Database
- Network
- Other app-s

### Types

In C, type is determined in the compilation. Python on the other hand doesn’t give a single fuck about it and realises something is wrong only during the runtime.

## Processing

### Operators

- Arithmetical: `+`, `-`, `/` …
- Logical: `and`, `or`, …
- Comparison: `==` ,

## Tasks

### Palindrome

```python
#   Determine whether a given number is a palindrome.
#   Only arithmetical operations are allowed.

#   INPUT
num = int(input("Enter your number here: "))
init_num = num
rev_num = 0

# PROCESSING
while num > 0:
    last_dig = num % 10                 # Grab the last digit
    rev_num = rev_num * 10 + last_dig   # Build the reversed number
    num = num // 10                     # Remove the last digit from original

# OUTPUT
print("YEPPIE!" if rev_num == init_num else "No :(")
```

### Amicable numbers

When we look for divisors of a number, they always come in *pairs*. If you find one divisor, you've automatically found another by dividing the total by that first number.

Let's use the number `100` as an example:

- `1 * 100 = 100`
- `2 * 50 = 100`
- `4 * 25 = 100`
- `5 * 20 = 100`
- `10 * 10 = 100`

Notice how the divisors "meet" in the middle at `10` (the square root). If we kept looking for numbers after `10`, like `20`, we would just be finding the same pairs we already found (`20 * 5`). By stopping at the square root, we save the computer a lot of work. If we want to check if `1,000,000` is amicable, checking every number would take a million steps. Using the square root limit, we only need to check `1,000` steps!

```python
# Determine whether two integers are amicable. Two integers are amicable
# if the sum of the divisors of one smaller than itself is equal to the other,
# and vice versa. E.g.: 220 and 284.

# INPUT
init_num1 = num1 = int(input("num1: "))
init_num2 = num2 = int(input("num2: "))

sum1 = 1
sum2 = 1

# PROCESSING
for i in range(2, int(num1 ** 0.5) + 1):
    if num1 % i == 0:
        sum1 += i
        if i != num1 // i:
            sum1 += num1 // i
                        
for i in range(2, int(num2 ** 0.5 ) + 1):
    if num2 % i == 0:
        sum2 += i
        if i != num2 // i:
            sum2 += num2 // i

# OUTPUT  
print("YEPPIE!" if sum2 == num1 and sum1 == num2 else "No :(")
```

## Homework

[w2 lab tasks.md](attachment:a2aa942a-cd9b-420b-b3e0-728705e52f55:w2_lab_tasks.md)

- [ ]  10
- [x]  11
    
    ```python
    # Print how many even and odd digits an integer number has.
    
    # INPUT
    a = input("a: ").lstrip('-')
    odds = 0
    evens = 0
    
    # PROCESSING
    for letter in a:
        if int(letter) % 2 == 0:
            evens += 1 
        else:
            odds += 1
    
    # OUTPUT
    print(f"The sum of odds in the number {a} is {odds} and the sum evens is {evens}.")
    ```
    
- [x]  12
    
    ```python
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
    ```