package text.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

public class CharacterStatisticsTest {
    static CharacterStatistics chs;

    @BeforeAll
    public static void init() {
        chs = new CharacterStatistics("Hello world!");
    }

    @Test
    public void countChars() {
        assertEquals(3, chs.getCount('l'));
    }

    @Test
    public void checkTheMaths() {
        assertEquals(" (1) !(1) r(1) d(1) e(1) w(1) H(1) l(3) o(2)", chs.toString());
    }
}
