package text.to.numbers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class SingleLineFile {
    // req: let ioexc propagate if file doesn't exist
    public static int addNumbersSingleLine(String fileAddress) throws IOException {
        int sum = 0;

        // req: use try-with-resources to close input
        // req: use buffdrdr to read the line in one go
        try (BufferedReader r = new BufferedReader(new FileReader(fileAddress))) {
            String line = r.readLine();

            // req: throw illargcexc if file is empty
            if (line == null) {
                throw new IllegalArgumentException("Empty file");
            }

            // req: use sc to parse the line
            try (Scanner sc = new Scanner(line)) {
                // ... by the help of hasNext()
                while (sc.hasNext()) {
                    if (sc.hasNextInt()) {
                        sum += sc.nextInt();
                    } else {
                        // req: if a word isn't an int print it to syserr
                        System.err.println(sc.next());
                    }
                }
            }
        }
        return sum;
    }
}