package videogamestore;

import java.util.List;

/**
 * Demo entry point for the video game rental store model.
 */
public class Main {
    /**
     * Runs a small demo of the Game API.
     */
    public static void main(String[] args) {
        Game g1 = new Game("GTA VI", Category.ACTION, 12, 7.8f, 2.5f, 5, true);
        Game g2 = new Game("Mortal Kombat XI", Category.PUZZLE, 8, 9.1f, 1.8f, 3, true);
        Game g3 = new Game("FIFA 26", Category.SIMULATION, 10, 8.3f, 2.2f, 12, true);

        List<Game> games = List.of(g1, g2, g3);

        Game.findMostPopular(games);
        Game.browseGenre(games, Category.PUZZLE);
        Game.browseAge(games, 10);

        if (g1.rent(4)) {
            g1.returnGame(4, 6);
        }

        System.out.println("Total revenue so far: " + Game.getTotalRevenue());
    }
}
