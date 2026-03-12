package string.utils.main;

import string.utils.IterWord;

public class WordMain {
    public static void main(String[] args) {
        // The last one takes two command line arguments.
        if (args.length < 4) {
            throw new IllegalArgumentException("Expected 4 args: n, text, m, k");
        }

        // The code takes the first one (a number n), and it calls printNext n times with
        // the second arg as a parameter.
        int n = Integer.parseInt(args[0]);
        IterWord iter = new IterWord(args[1]);
        int m = Integer.parseInt(args[2]);
        int k = Integer.parseInt(args[3]);

        // In your mains, invoke this before and after the restart, and just before main finishes.
        for (int i = 0; i < n; i++) {
            if (i == m) {
                iter.hasNext();
                iter.reset();
                iter.hasNext();
            }
            iter.printNext();
        }

        // Once it's called, the following printNext calls will start printing from the
        // beginning of the text again.
        if (m >= n) {
            iter.hasNext();
            iter.reset();
            iter.hasNext();
        }

        // In your mains, make the iteration restart once after m printouts, and have k
        // printouts after that.
        for (int i = 0; i < k; i++) {
            iter.printNext();
        }

        iter.hasNext();
    }
}
