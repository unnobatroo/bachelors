package string.utils;

public class Main {
    public static void main(String[] args) {
        // So you’ll have a chain of four mains calling four other mains.
        string.utils.main.Main.main(args);
    }
}
