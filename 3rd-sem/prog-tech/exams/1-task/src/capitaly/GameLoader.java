package capitaly;

import capitaly.factory.FieldFactory;
import capitaly.factory.PlayerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class GameLoader {

    public record GameData(
        List<Field> fields,
        List<Player> players,
        List<Integer> diceRolls
    ) {}

    public static GameData loadFromFile(String filePath)
        throws FileNotFoundException, InvalidInputException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new FileNotFoundException("File not found: " + filePath);
        }

        try (Scanner scanner = new Scanner(file)) {
            if (!scanner.hasNextInt()) {
                throw new InvalidInputException(
                    "Expected number of fields at the beginning of the file."
                );
            }
            int fieldCount = scanner.nextInt();
            if (fieldCount <= 0) {
                throw new InvalidInputException(
                    "Field count must be greater than 0, got: " + fieldCount
                );
            }

            List<Field> fields = new ArrayList<>(fieldCount);
            for (int i = 0; i < fieldCount; i++) {
                if (!scanner.hasNext()) {
                    throw new InvalidInputException(
                        "Expected field definition at index " + i
                    );
                }
                String type = scanner.next();
                fields.add(FieldFactory.createField(i, type, scanner));
            }

            if (!scanner.hasNextInt()) {
                throw new InvalidInputException(
                    "Expected number of players after field definitions."
                );
            }
            int playerCount = scanner.nextInt();
            if (playerCount <= 0) {
                throw new InvalidInputException(
                    "Player count must be greater than 0, got: " + playerCount
                );
            }

            List<Player> players = new ArrayList<>(playerCount);
            for (int i = 0; i < playerCount; i++) {
                if (!scanner.hasNext()) {
                    throw new InvalidInputException(
                        "Expected player name at index " + i
                    );
                }
                String name = scanner.next();
                if (!scanner.hasNext()) {
                    throw new InvalidInputException(
                        "Expected player strategy for player: " + name
                    );
                }
                String strategy = scanner.next();
                players.add(PlayerFactory.createPlayer(name, strategy));
            }

            List<Integer> diceRolls = new ArrayList<>();
            while (scanner.hasNextInt()) {
                int roll = scanner.nextInt();
                if (roll <= 0) {
                    throw new InvalidInputException(
                        "Dice roll must be positive, got: " + roll
                    );
                }
                diceRolls.add(roll);
            }

            return new GameData(fields, players, diceRolls);
        } catch (NoSuchElementException e) {
            throw new InvalidInputException(
                "Unexpected end of file or malformed format: " + e.getMessage()
            );
        }
    }
}
