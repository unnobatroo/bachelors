public class Card {
    float acc;
    String pass;

    public Card(float acc, String pass) {
        this.acc = acc;
        this.pass = pass;
    }

    public float getAcc() {
        return acc;
    }

    public String getPass() {
        return pass;
    }

    public void modifyBalance(float a) {
        acc += a;
    }
}
