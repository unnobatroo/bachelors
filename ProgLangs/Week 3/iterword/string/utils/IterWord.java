package string.utils;

// Add a string.utils.IterWord class to the IterLetter exercise above.
public class IterWord {
    private final String[] param;
    private int index;

    // The constructor of this class takes a string as parameter.
    public IterWord(String param) {
        if (param == null) {
            throw new IllegalArgumentException();
        }

        this.param = param.split(" ");
        this.index = 0;
    }

    // The printNext() method prints the next word of this string to the standard
    // output (on a new line).
    public void printNext() {
        if (index >= param.length) {
            System.out.println();
        } else {
            System.out.println(param[index]);
            index++;
        }
    }

    // Also add a reset() and a hasNext() method that are similar to those in the
    // original exercise.
    public void reset() {
        index = 0;
    }

    public boolean hasNext() {
        return index < param.length;
    }

}
