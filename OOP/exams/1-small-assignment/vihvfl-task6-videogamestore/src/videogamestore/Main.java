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
        // ── Inventory ────────────────────────────────────────────────────────
        Game gta      = new Game("GTA VI",            Category.ACTION,     12, 7.8f, 2.5f,  5, true);
        Game mk       = new Game("Mortal Kombat XI",  Category.PUZZLE,      8, 9.1f, 1.8f,  3, true);
        Game fifa     = new Game("FIFA 26",            Category.SIMULATION, 10, 8.3f, 2.2f, 12, true);
        Game hades    = new Game("Hades II",           Category.ACTION,     16, 9.4f, 2.0f,  0, true);
        Game tetris   = new Game("Tetris Effect",      Category.PUZZLE,      3, 7.2f, 1.5f, 20, true);
        Game stardew  = new Game("Stardew Valley",     Category.SIMULATION,  3, 8.9f, 1.8f,  7, true);

        List<Game> games = List.of(gta, mk, fifa, hades, tetris, stardew);

        // ── 1. Most popular game ─────────────────────────────────────────────
        header("1. Most Popular Game");
        Game top = Game.findMostPopular(games);
        if (top != null) {
            System.out.printf("   → %s  (score: %.1f)%n", top.getTitle(), top.getPopularityScore());
        }

        // ── 2. Browse by genre ───────────────────────────────────────────────
        header("2. Browse by Genre: ACTION");
        List<Game> actionGames = Game.browseGenre(games, Category.ACTION);
        printGames(actionGames);

        header("3. Browse by Genre: PUZZLE");
        List<Game> puzzleGames = Game.browseGenre(games, Category.PUZZLE);
        printGames(puzzleGames);

        // ── 3. Browse by age ─────────────────────────────────────────────────
        header("4. Browse by Age: 10");
        List<Game> ageAppropriate = Game.browseAge(games, 10);
        printGames(ageAppropriate);

        // ── 4. Rental flow ───────────────────────────────────────────────────
        header("5. Rental Flow");
        System.out.println("   Renting \"" + gta.getTitle() + "\" for 4 hours...");
        if (gta.rent(4)) {
            System.out.println("   Returning after 6 hours (2 hours late)...");
            float charge = gta.returnGame(4, 6);
            System.out.printf("   Charge: $%.2f%n", charge);
        }

        // ── 5. Warn-out / damage scenario ────────────────────────────────────
        header("6. Wear-and-Tear Scenario");
        System.out.println("   \"" + tetris.getTitle() + "\" has been rented "
                + tetris.getTotalRentsCount() + " times already.");
        System.out.println("   Trying to rent it one more time...");
        boolean rented = tetris.rent(2);
        System.out.println("   Rented: " + rented
                + " | Damaged: " + tetris.isDamaged()
                + " | Available: " + tetris.isAvailable());

        // ── Summary ──────────────────────────────────────────────────────────
        header("Summary");
        System.out.printf("   Total store revenue: $%.2f%n", Game.getTotalRevenue());
    }

    private static void header(String title) {
        System.out.println();
        System.out.println("┌─ " + title + " " + "─".repeat(Math.max(0, 50 - title.length())));
    }

    private static void printGames(List<Game> games) {
        if (games.isEmpty()) {
            System.out.println("   (none)");
            return;
        }
        for (int i = 0; i < games.size(); i++) {
            Game g = games.get(i);
            System.out.printf("   %d. %-24s  score: %.1f  age: %d+  rate: $%.2f/hr%n",
                    i + 1, g.getTitle(), g.getPopularityScore(),
                    g.getAgeRating(), g.getRentalRate());
        }
    }
}
