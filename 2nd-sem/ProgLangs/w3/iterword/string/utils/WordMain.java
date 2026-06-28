package string.utils;

public class WordMain {
    public static void main(String[] args) {
        // So you'll have a chain of four mains calling four other mains.
        string.utils.main.WordMain.main(args);
    }
}
