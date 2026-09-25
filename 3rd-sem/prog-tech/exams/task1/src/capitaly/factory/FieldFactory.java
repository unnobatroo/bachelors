package capitaly.factory;

import capitaly.Field;
import capitaly.InvalidInputException;
import capitaly.LuckyField;
import capitaly.PropertyField;
import capitaly.ServiceField;
import java.util.Scanner;

public class FieldFactory {

    public static Field createField(int id, String type, Scanner scanner)
        throws InvalidInputException {
        return switch (type.toLowerCase()) {
            case "property", "p" -> new PropertyField(id);
            case "service", "s" -> {
                if (!scanner.hasNextInt()) {
                    throw new InvalidInputException(
                        "Service field at index " +
                            id +
                            " requires an integer cost."
                    );
                }
                yield new ServiceField(id, scanner.nextInt());
            }
            case "lucky", "l" -> {
                if (!scanner.hasNextInt()) {
                    throw new InvalidInputException(
                        "Lucky field at index " +
                            id +
                            " requires an integer reward."
                    );
                }
                yield new LuckyField(id, scanner.nextInt());
            }
            default -> throw new InvalidInputException(
                "Unknown field type: " + type
            );
        };
    }
}
