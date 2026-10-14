#include <ios>
#include <iostream>
#include <string>
#include <stack>

bool isMirrored(const std::string &txt, int i = 0);

int main(int argc, char *argv[]) {
    if (argc != 2) {
        return 1;
    }

    std::cout << std::boolalpha << isMirrored(argv[1]) << "\n";
    return 0;
}

bool isMirrored(const std::string &txt, int i) {
    int n = txt.length();

    if (i == n) {
        return true; // base case: reached the end of the text stream after processing all segments
    }

    std::stack<char> st; // store characters of the left side (w) of the current segment

    while (i < n && txt[i] != '#') {
        st.push(txt[i]); // push characters onto the stack until reaching '#' or end of string
        ++i;
    }

    if (i == n) {
        return false; // reached the end of string without encountering '#'
    }

    ++i; // advance past the '#' symbol

    while (!st.empty()) {
        if (i == n || txt[i] != st.top()) {
            return false; // input ends prematurely or current character mismatches stack top
        }

        st.pop(); // pop and match characters against the right side (w^R)
        ++i;
    }

    return isMirrored(txt, i); // process remaining text
}
