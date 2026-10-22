def isPalindrome(txt: str, idx: int = 0) -> bool:
    if idx == len(txt): return True

    st = []

    while idx < len(txt) and txt[idx] != "#":
        st.append(txt[idx])
        idx += 1

    if idx < len(txt): idx += 1
    else: return False

    while idx < len(txt) and st and txt[idx] == st[-1]:
        st.pop()
        idx += 1

    return isPalindrome(txt, idx) if not st else False
