/*  In the file `FindInTextBefore.java`, the method `findFirstIdx`
    receives a text and two positions. It returns the index where
    the first occurrence of the specified substring is found. */

void main(){
    IO.println(findFirstIdxIgnoreSpaces("Ja lo li ddi n", 2, 4));
    IO.println(findFirstIdxIgnoreCase("J A lolI DdI n", 2, 4) + "\n");
    IO.println(verifyIgnoreSpaces("He l l o Worl d !", 0, 0));
    IO.println(verifyIgnoreCase("VA y BlyA coB AL t JalA p!", 0, 0));
}

/*  1. Variant: `findFirstIdxIgnoreSpaces` – ignore spaces.
    - Hint: replace spaces with the empty text.
    - Hint: do not rewrite the entire code – make use of the
            previous version instead: call it. */
int findFirstIdxIgnoreSpaces(String txt, int begin, int end){
    return txt.indexOf(txt.replaceAll(" ", "").substring(begin, end));
}

/*  2. Variant: `findFirstIdxIgnoreCase` – in addition to ignoring
                spaces, also ignore differences between uppercase
                and lowercase letters.
    - Hint: convert the text to lowercase before checking. */
int findFirstIdxIgnoreCase(String txt, int begin, int end){
    return findFirstIdxIgnoreSpaces(txt.toLowerCase(), begin, end);
}
/*  3. For each of the above methods, write a corresponding
       `verifyMMM` method (e.g., `verifyFindFirstIdxIgnoreCase`).
    - The parameters should be the same, and it should call the
      corresponding method.
    - Instead of printing the result, the method checks whether
      it is correct: verify that the given substring is indeed found
      at the returned position.
    - It returns a boolean value.
    - Testing guidelines: - When verifying a program part, try every
      significantly different case at least once. - Here, two possibilities
      exist: either the substring occurs earlier in the text, or the first
      found index is exactly at the given substring. - As before, we assume
      that all input is valid and that valid output can always be produced.
*/
boolean verifyIgnoreSpaces(String txt, int begin, int end){
    return txt.indexOf(txt.replaceAll(" ", "").substring(begin, end)) == findFirstIdxIgnoreSpaces(txt, begin, end);
}

boolean verifyIgnoreCase(String txt, int begin, int end){
    return txt.indexOf(txt.toLowerCase().replaceAll(" ", "").substring(begin, end)) == findFirstIdxIgnoreCase(txt, begin, end);
}