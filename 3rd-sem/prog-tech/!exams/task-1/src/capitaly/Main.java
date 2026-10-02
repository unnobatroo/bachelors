package capitaly;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String filePath;
        int rounds;

        try (Scanner scanner = new Scanner(System.in)) {
            if (args.length >= 1) {
                filePath = args[0];
            } else {
                System.out.print("Enter game file path: ");
                filePath = scanner.nextLine().trim();
            }

            if (args.length >= 2) {
                rounds = Integer.parseInt(args[1]);
            } else {
                System.out.print("Enter number of rounds to simulate: ");
                String line = scanner.nextLine().trim();
                rounds = Integer.parseInt(line);
            }

            if (rounds <= 0) {
                throw new InvalidInputException(
                    "Number of rounds must be greater than 0."
                );
            }

            GameLoader.GameData gameData = GameLoader.loadFromFile(filePath);

            Dice dice = gameData.diceRolls().isEmpty()
                ? Dice.random()
                : Dice.fromList(gameData.diceRolls());

            CapitalyGame game = new CapitalyGame(
                gameData.fields(),
                gameData.players(),
                dice
            );
            game.playRounds(rounds);
            game.printPlayersState();
        } catch (NumberFormatException e) {
            System.err.println("Error: rounds must be an integer");
        } catch (FileNotFoundException | InvalidInputException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
