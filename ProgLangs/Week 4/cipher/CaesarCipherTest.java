package cipher;

// In cipher.CaesarCipherTest, test the following.
public class CaesarCipherTest {

    public static void main(String[] args) {
        CaesarCipherTest tester = new CaesarCipherTest();

        tester.testNoShiftEncrypt();
        System.out.println("✓ testNoShiftEncrypt passed");

        tester.testNoShiftDecrypt();
        System.out.println("✓ testNoShiftDecrypt passed");

        tester.testEncryptEmpty();
        System.out.println("✓ testEncryptEmpty passed");

        tester.testEncryptSingleLetter();
        System.out.println("✓ testEncryptSingleLetter passed");

        tester.testEncryptLongerTexts();
        System.out.println("✓ testEncryptLongerTexts passed");

        tester.testEncryptNonLowercase();
        System.out.println("✓ testEncryptNonLowercase passed");

        tester.testEncryptThenDecrypt();
        System.out.println("✓ testEncryptThenDecrypt passed");

        tester.testDecryptThenEncrypt();
        System.out.println("✓ testDecryptThenEncrypt passed");

        tester.testEncryptByShiftThenNegativeShift();
        System.out.println("✓ testEncryptByShiftThenNegativeShift passed");

        tester.testEncryptByNegativeShiftThenShift();
        System.out.println("✓ testEncryptByNegativeShiftThenShift passed");

        tester.testDecryptByShiftThenNegativeShift();
        System.out.println("✓ testDecryptByShiftThenNegativeShift passed");

        tester.testDecryptByNegativeShiftThenShift();
        System.out.println("✓ testDecryptByNegativeShiftThenShift passed");
    }

    // noShift: encrypting or decrypting with a shift value of 0 returns the
    // original text
    public void testNoShiftEncrypt() {
        CaesarCipher cipher = new CaesarCipher(0);
        String text = "hello world";
        assert cipher.encrypt(text).equals(text) : "No shift encrypt failed";
    }

    public void testNoShiftDecrypt() {
        CaesarCipher cipher = new CaesarCipher(0);
        String text = "hello world";
        assert cipher.decrypt(text).equals(text) : "No shift decrypt failed";
    }

    // encryptBy: test encrypting specific texts with specific shift values

    // the empty text (write it as "" into the textBlock)
    public void testEncryptEmpty() {
        CaesarCipher cipher = new CaesarCipher(5);
        String text = "";
        assert cipher.encrypt(text).equals("") : "Empty text encryption failed";
    }

    // a text with a single letter
    public void testEncryptSingleLetter() {
        CaesarCipher cipher = new CaesarCipher(3);
        assert cipher.encrypt("a").equals("d") : "Single letter encryption failed";
        assert cipher.encrypt("z").equals("c") : "Single letter wrap around failed";
    }

    // a couple of longer texts
    public void testEncryptLongerTexts() {
        CaesarCipher cipher1 = new CaesarCipher(3);
        assert cipher1.encrypt("abc").equals("def") : "abc with shift 3 failed";
        assert cipher1.encrypt("xyz").equals("abc") : "xyz with shift 3 failed";

        CaesarCipher cipher2 = new CaesarCipher(5);
        assert cipher2.encrypt("hello").equals("mjqqt") : "hello with shift 5 failed";

        CaesarCipher cipher3 = new CaesarCipher(-5);
        assert cipher3.encrypt("hello").equals("czggj") : "hello with shift -5 failed";
    }

    // texts containing non-lowercase characters
    public void testEncryptNonLowercase() {
        CaesarCipher cipher = new CaesarCipher(1);
        assert cipher.encrypt("a1b2c3").equals("b1c2d3") : "Non-lowercase characters failed";
        assert cipher.encrypt("hello world!").equals("ifmmp xpsme!") : "Spaces and punctuation failed";
        assert cipher.encrypt("abc123xyz").equals("bcd123yza") : "Mixed text failed";
    }

    // inverseOperation: apply two operations in sequence that are the opposites of
    // each other,
    // and get the original text back

    // encrypt then decrypt with the same shift value
    public void testEncryptThenDecrypt() {
        CaesarCipher cipher = new CaesarCipher(7);
        String original = "the quick brown fox";
        String encrypted = cipher.encrypt(original);
        String decrypted = cipher.decrypt(encrypted);
        assert decrypted.equals(original) : "Encrypt then decrypt failed";
    }

    // decrypt then encrypt with the same shift value
    public void testDecryptThenEncrypt() {
        CaesarCipher cipher = new CaesarCipher(7);
        String original = "the quick brown fox";
        String decrypted = cipher.decrypt(original);
        String encrypted = cipher.encrypt(decrypted);
        assert encrypted.equals(original) : "Decrypt then encrypt failed";
    }

    // encrypt by shift then encrypt by -shift
    public void testEncryptByShiftThenNegativeShift() {
        CaesarCipher cipher1 = new CaesarCipher(10);
        CaesarCipher cipher2 = new CaesarCipher(-10);
        String original = "hello world";
        String step1 = cipher1.encrypt(original);
        String result = cipher2.encrypt(step1);
        assert result.equals(original) : "Encrypt by shift then -shift failed";
    }

    // encrypt by -shift then encrypt by shift
    public void testEncryptByNegativeShiftThenShift() {
        CaesarCipher cipher1 = new CaesarCipher(-10);
        CaesarCipher cipher2 = new CaesarCipher(10);
        String original = "hello world";
        String step1 = cipher1.encrypt(original);
        String result = cipher2.encrypt(step1);
        assert result.equals(original) : "Encrypt by -shift then shift failed";
    }

    // decrypt by shift then decrypt by -shift
    public void testDecryptByShiftThenNegativeShift() {
        CaesarCipher cipher1 = new CaesarCipher(10);
        CaesarCipher cipher2 = new CaesarCipher(-10);
        String original = "hello world";
        String step1 = cipher1.decrypt(original);
        String result = cipher2.decrypt(step1);
        assert result.equals(original) : "Decrypt by shift then -shift failed";
    }

    // decrypt by -shift then decrypt by shift
    public void testDecryptByNegativeShiftThenShift() {
        CaesarCipher cipher1 = new CaesarCipher(-10);
        CaesarCipher cipher2 = new CaesarCipher(10);
        String original = "hello world";
        String step1 = cipher1.decrypt(original);
        String result = cipher2.decrypt(step1);
        assert result.equals(original) : "Decrypt by -shift then shift failed";
    }
}
