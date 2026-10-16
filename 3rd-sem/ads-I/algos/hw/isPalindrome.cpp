#include <ios>
#include <iostream>
#include <stack>
#include <string>

/// Is the text a chain of mirrored `w#w^R` segments?
/// `"ab#ba"` -> true, `"ab#a"` -> false.
/// Recursive; `i` is the read offset — call with one argument.
bool isMirrored(const std::string& txt, int i = 0);

// Usage: ./isPalindrome "ab#ba" -> prints true/false
int main(int argc, char* argv[]) {
    if (argc != 2) {
        return 1;
    }

    std::cout << std::boolalpha << isMirrored(argv[1]) << "\n";
    return 0;
}

bool isMirrored(const std::string& txt, int i) {
    int n = txt.length();

    if (i == n) {
        return true; // base case: reached the end of the text
    }

    std::stack<char> st; // left side (w) of the current segment

    while (i < n && txt[i] != '#') {
        st.push(txt[i]);
        ++i;
    }

    if (i == n) {
        return false; // end of string without a '#'
    }

    ++i; // skip the '#'

    while (!st.empty()) {
        if (i == n || txt[i] != st.top()) {
            return false; // right side ended early or mismatches
        }
        st.pop();
        ++i;
    }

    return isMirrored(txt, i); // check the next segment
}
