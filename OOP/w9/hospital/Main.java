public class Main {
    static void main(String[] args) {
        Hospital h = new Hospital();
        Doctor d = new Doctor("D1", "Gregory House");
        Nurse n1 = new Nurse("N1", "N1");
        Nurse n2 = new Nurse("N2", "N2");
        Staff s1 = new Staff("S1", "S1");

        h.employ(d);
        h.employ(n1);
        h.employ(n2);
        h.employ(s1);

        Patient p1 = new Patient("Clark Kent");
        h.heal(p1);

        h.assign(d, p1);
        h.assign(n1, p1);
        h.assign(n2, p1);

        System.out.println("The doctor of " + p1.getName() + " is " + d.getName() + ". My condolences!");
    }
}
