# https://coddy.tech/visualize/searching/binary-search
def bSearch(arr, key, start, end):
    lo = start
    hi = end

    while lo < hi:
        mid = (lo + hi) // 2
        if arr[mid] <= key:
            lo = mid + 1
        else:
            hi = mid

    return hi
