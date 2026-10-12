import search


# https://coddy.tech/visualize/sorting/selection-sort?view=array&speed=0.5&size=20
def selection_sort(arr):
    arr_len = len(arr)

    for i in range(arr_len - 1):
        arr_min_idx = i

        for j in range(i + 1, arr_len):
            if arr[arr_min_idx] > arr[j]:
                arr_min_idx = j

        arr[i], arr[arr_min_idx] = arr[arr_min_idx], arr[i]


# https://coddy.tech/visualize/sorting/insertion-sort?view=bars&speed=0.5&size=20
def insertion_sort(arr):
    for i in range(1, len(arr)):
        key = arr[i]
        j = i - 1

        while j >= 0 and arr[j] > key:
            arr[j + 1] = arr[j]
            j -= 1

        arr[j + 1] = key


def binary_insertion_sort(arr):
    arr_len = len(arr)

    for i in range(1, arr_len):
        x = arr[i]

        pos = search.bSearch(arr, x, 0, i)

        for j in range(i, pos, -1):
            arr[j] = arr[j - 1]

        arr[pos] = x


if __name__ == "__main__":
    import random
    import time

    sorts = [
        ("selection sort", selection_sort),
        ("insertion sort", insertion_sort),
        ("binary insertion sort", binary_insertion_sort),
    ]

    try:
        while True:
            for i, (name, _) in enumerate(sorts, 1):
                print(f"{i}) {name}")
            print("q) quit")

            choice = input("pick a sort: ").strip().lower()
            if choice == "q":
                break
            if not choice.isdigit() or not 1 <= int(choice) <= len(sorts):
                print("invalid choice\n")
                continue

            name, func = sorts[int(choice) - 1]
            n = int(input("array size [10000]: ").strip() or 10000)
            arr = [random.randint(1, 1_000_000) for _ in range(n)]

            t = time.process_time()
            func(arr)
            print(f"{name}: sorted {n} items in {time.process_time() - t:.3f}s\n")

    except (EOFError, KeyboardInterrupt):
        pass
