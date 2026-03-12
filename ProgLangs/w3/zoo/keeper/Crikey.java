// Write zoo.keeper.Crikey.main to test the above functionality
package zoo.keeper;

import zoo.animal.Panda;
import zoo.animal.Country;

public class Crikey {
    public static void main(String... args) {
        Panda lu = new Panda(45, Country.IRAQ);

        lu.happyBirthday(99);
    }
}
