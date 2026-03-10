package string.utils;

// Create the class string.utils.IterLetter.
public class IterLetter {

    private final String text;
    private int index;

    // Its constructor takes a String parameter.
    public IterLetter(String parameter) {
        // If it is null, throw an IllegalArgumentException.
        if (parameter == null) {
            throw new IllegalArgumentException();
        }
        this.text = parameter;
        this.index = 0;
    }

    // In this class, create the method printNext().
    public void printNext() {
        // The first call to the method prints the first letter of the text to the
        // standard output on a single line. The second call prints the second letter
        // and so on.
        if (index < text.length()) {
            // Open the Java API documentation for String and find the method that gives you
            // the first letter.
            System.out.println(text.charAt(index));
            index++;
        } else {
            // After all the letters of the string have been printed, the method prints an
            // empty line on further calls.
            System.out.println();
        }
    }

    public void reset() {
        index = 0;
    }

    public boolean hasNext() {
        return index < text.length();
    }
}