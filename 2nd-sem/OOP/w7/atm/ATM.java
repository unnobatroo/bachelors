import java.util.Scanner;

public class ATM {
    private boolean checkPass(Card card, String usrPass) {
        return card.getPass().equals(usrPass);
    }

    private boolean checkBalance(Card card, float usrAmount) {
        return card.getAcc() >= usrAmount;
    }

    public boolean accWithdrawal(Card card, Scanner sc) {
        System.out.println("Enter your password: ");
        String enteredPass = sc.nextLine();

        if (!checkPass(card, enteredPass)) {
            System.out.println("Wrong password.");
            return false;
        }

        System.out.println("Nice! Now enter the amount you want to withdraw: ");
        float amount = sc.nextFloat();
        sc.nextLine();

        if (!checkBalance(card, amount)) {
            System.out.println("Insufficient balance.");
            return false;
        }

        card.modifyBalance(-amount);
        System.out.println("Alright! " + amount + " has been removed from your account.");
        System.out.println("Current balance: " + card.getAcc());
        return true;
    }
}
