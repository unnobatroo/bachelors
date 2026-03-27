import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        Card card = new Card(1000f, "1234");
        ATM atm = new ATM();

        if (!atm.accWithdrawal(card, sc)) {
            System.out.println("What a pity!");
        }
    }
}