# Determine whether a given number is a palindrome.
# Only arithmetical operations are allowed.

# INPUT
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
