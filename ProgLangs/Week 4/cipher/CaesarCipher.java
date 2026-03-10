package cipher;

// In cipher.CaesarCipher, implement the Caesar cipher for the English alphabet in the following way.
public class CaesarCipher {
    // Its constructor takes the shift value and stores it.
    int shift;

    public CaesarCipher(int shift) {
        this.shift = shift;
    }

    // Its method encrypt() takes a text and returns its encrypted version.
    public String encrypt(String txt) {

        // You may use the + operator to construct the result text. An even better
        // solution is to use StringBuilder.
        StringBuilder result = new StringBuilder();

        // Each letter is encrypted separately.
        for (char temp_char : txt.toCharArray()) {
            // If the character is not a letter (not a character between 'a' and 'z'), it is
            // kept unchanged. Otherwise, it is shifted by shift positions.
            if (temp_char >= 'a' && temp_char <= 'z') {
                // Attention: shifts can be negative and wrap around: 'c' shifted by -5 becomes
                // 'x'.
                int shiftedPosition = temp_char - 'a' + shift;
                while (shiftedPosition < 0) {
                    shiftedPosition = shiftedPosition + 26;
                }
                while (shiftedPosition >= 26) {
                    shiftedPosition = shiftedPosition - 26;
                }
                // Note that after shifting, you’ll have to use the conversion
                // (char)shiftedChar.
                result.append((char) ('a' + shiftedPosition));
            } else {
                result.append(temp_char);
            }
        }
        return result.toString();
    }

    // Its method decrypt() is the opposite of encrypt().
    public String decrypt(String txt) {
        StringBuilder result = new StringBuilder();
        for (char temp_char : txt.toCharArray()) {
            if (temp_char >= 'a' && temp_char <= 'z') {
                int position = temp_char - 'a';
                int shiftedPosition = position - shift;
                while (shiftedPosition < 0) {
                    shiftedPosition = shiftedPosition + 26;
                }
                while (shiftedPosition >= 26) {
                    shiftedPosition = shiftedPosition - 26;
                }
                char shiftedChar = (char) ('a' + shiftedPosition);
                result.append(shiftedChar);
            } else {
                result.append(temp_char);
            }
        }
        return result.toString();
    }
}
