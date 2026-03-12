## `String` in Python

### Creating a string

Strings are basically arrays with `char`s.

```python
# '' and "" are treated the same way - they create a string:
str1 = "hello world!"
str2 = 'I SAID HELLO WORLD!'
```

### Accessing elements

```python
str1[0] # "h"
str1[-1] # goes from the other side of the array, "d"

# main logic: arr[begin:end:step], where :end: is not included
str1[2:5:1] # "llo"
```

### Operators

**Arithmetical**

```python
str1 + str2 # concatenate, "hello world!I SAID HELLO WORLD!"
str1 * 3 # duplicate, "hello world!hello world!hello world!"
```

**Logical**

```python
'aa' > 'ab' # compares ASCII values, False
'aa' != 'aa' # False

ord('a') # to get an ASCII value
```

**Membership**

```python
if 'a' not in str1:
	return michael_jordan
else:
	return lebron_james # <- the output
```

Loops in Python are a bit different than the ones in C.

## Homework

- [x]  Read a number and determine whether it is prime. Use a `for` loop and stop the loop early using `break` when a divisor is found.
    
    ```python
    import math
    
    def is_prime(a):
        for i in range (2, int(math.sqrt(a)) + 1):
            if a % i == 0:
                return False
        return True
    ```
    
- [x]  Print all prime numbers up to 100 using `for` loops.
    
    ```python
    for i in range(1,100):
            if is_prime(i):
                print(f"AHA! The number -{i}- is prime.")
    ```
    
- [x]  Draw an 8×8 empty chessboard using nested `for` loops. Print `[]` for dark fields and two spaces for light fields. Use divisibility by two to decide the field.
    
    ```python
    def print_chess_board():
        str = ""
        for i in range(8):
            for j in range(8):
                if (i + j) % 2 == 0:
                    str += "[]"
                else:
                    str += "  "
            str += "\n"
        print(str)
    ```