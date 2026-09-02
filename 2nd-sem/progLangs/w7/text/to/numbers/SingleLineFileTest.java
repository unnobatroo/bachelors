package text.to.numbers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class SingleLineFileTest {
    @Test
    void testFileDoesNotExist() {
        try {
            SingleLineFile.addNumbersSingleLine("non_existent.txt");
            fail("Should have thrown IOException");
        } catch (IOException e) {
        }
    }

    @Test
    void testEmptyFile() {
        try {
            SingleLineFile.addNumbersSingleLine("mt.txt");
            fail("Should have thrown IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Empty file", e.getMessage());
        } catch (IOException e) {
            fail("Shouldn't throw IOException if file exists");
        }
    }

    @Test
    void testValidFile() throws IOException {
        int result = SingleLineFile.addNumbersSingleLine("valid.txt");
        assertEquals(-117, result);
    }
}
