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
