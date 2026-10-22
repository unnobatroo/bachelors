import random

# arr = random.sample(range(1, 1 << 31), 10000)


def sum_dnq(arr, left=0, right=None):
    if right is None: right = len(arr) - 1
    if left == right: return arr[left]

    mid = (left + right) // 2

    return sum_dnq(arr, left, mid) + sum_dnq(arr, mid + 1, right)

# print(
#     "Congrats! You're not dolboyob." if exp := sum(arr) == (act := sum_dnq(arr)
#     else f"Jalalabad is waiting for you my friend!\n{exp} was the real result, while you calculated {act}"
# )


def count_dnq(arr, left=0, right=None):
    if right is None: right = len(arr) - 1
    if left == right: return 1

    mid = (left + right) // 2

    return count_dnq(arr, left, mid) + count_dnq(arr, mid + 1, right)

# print("Nice" if len(arr) == count_dnq(arr) else "Pizdes")


def max_dnq(arr, left=0, right=None):
    if right is None: right = len(arr) - 1
    if left == right: return arr[left]

    mid = (left + right) // 2

    left_max = max_dnq(arr, left, mid)
    right_max = max_dnq(arr, mid + 1, right)

    if right_max > left_max: return right_max

    return left_max

# print(
#     "No need for a ticket to Jalalabad." if exp := max(arr)) == (act := max_dnq(arr)
#     else f"Time to open Aviasales.ru!\n{exp} was the real result, while you calculated {act}"
# )


def bs_dnq(arr, target, left=0, right=None):
    if right is None: right = len(arr) - 1
    if left > right: return False

    mid = (left + right) // 2

    if arr[mid] == target: return True

    elif arr[mid] > target: return bs_dnq(arr, target, left, mid - 1)
    elif arr[mid] < target: return bs_dnq(arr, target, mid + 1, right)

target = int(input("Which number are we searching today? "))
nums = sorted(random.sample(range(1, 1 << 31), 1000000))

print(
    "Nicely done!" if (exp := bs_dnq(nums, target)) == (act := target in nums)
    else f"Off target.\nYour answer was {exp} while it must have been {act}"
)
