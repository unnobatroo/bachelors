import check.*;
import static check.Use.*;

import module org.junit.jupiter;

@BeforeAll
public static void init() {
    Use.theClass("cipher.CaesarCipher")
       .that(hasUsualModifiers());
}

@Test
public void fieldShift() {
    it.hasField("shift: int")
      .that(hasUsualModifiers())
      .thatHasNo(GETTER, SETTER);
}

@Test
public void constructor() {
    it.hasConstructor(withArgsLikeAllFields())
      .that(hasUsualModifiers());
}

@Test
public void methodEncrypt() {
    it.hasMethod("encrypt", withParams("text: String"))
      .that(hasUsualModifiers())
      .thatReturns("String");
}

@Test
public void methodDecrypt() {
    it.hasMethod("decrypt", withParams("text: String"))
      .that(hasUsualModifiers())
      .thatReturns("String");
}

void main() {}


