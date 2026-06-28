// Create the class zoo.animal.Panda.
package zoo.animal;

public class Panda {
    // This class stores the panda’s name, age, and country. Represent them with
    // appropriately typed and named fields.
    private String name;
    private int age;
    private Country oriCountry;

    // The class has two constructors.
    // One describes a newborn panda. It takes and stores contents for the other two
    // fields.
    public Panda(String name, Country oriCountry) {
        this.name = name;
        this.oriCountry = oriCountry;
    }

    // The other constructor takes two parameters, but not the name. The name is
    // chosen as N years old foundling from C where N and C are the age and the
    // country, respectively.
    public Panda(int age, Country oriCountry) {
        this.name = age + " years old foundling from " + oriCountry;
        this.oriCountry = oriCountry;
        this.age = age;
    }

    // The class also contains the method happyBirthday().
    // It prints the panda’s name, country, and age which is increased by one.
    // The method also takes a number (limitYear), and if the panda is older than
    // that, then it is repatriated to the People's Republic of China.
    public void happyBirthday(int limitYear) {
        if (age >= limitYear) {
            IO.println("Too sad :( The cutie should go back to " + oriCountry);
        }
        age++;
        IO.println(name);
    }
}
