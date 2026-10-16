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
        if (id < 0) {
            throw new InvalidInputException(
                "Field id cannot be negative, got: " + id
            );
        }
        if (type == null || type.isBlank()) {
            throw new InvalidInputException("Field type cannot be empty.");
        }
        if (scanner == null) {
            throw new InvalidInputException("Scanner cannot be null.");
        }

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
                int cost = scanner.nextInt();
                if (cost <= 0) {
                    throw new InvalidInputException(
                        "Service field cost at index " +
                            id +
                            " must be positive, got: " +
                            cost
                    );
                }
                yield new ServiceField(id, cost);
            }
            case "lucky", "l" -> {
                if (!scanner.hasNextInt()) {
                    throw new InvalidInputException(
                        "Lucky field at index " +
                            id +
                            " requires an integer reward."
                    );
                }
                int reward = scanner.nextInt();
                if (reward <= 0) {
                    throw new InvalidInputException(
                        "Lucky field reward at index " +
                            id +
                            " must be positive, got: " +
                            reward
                    );
                }
                yield new LuckyField(id, reward);
            }
            default -> throw new InvalidInputException(
                "Unknown field type: " + type
            );
        };
    }
}
