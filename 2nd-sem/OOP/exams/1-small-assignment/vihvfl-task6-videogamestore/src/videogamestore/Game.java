package videogamestore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a video game that can be rented from the store.
 */
public class Game {
    private static float totalRevenue = 0.0f;

    private final String title;
    private final Category genre;
    private final int ageRating;
    private final float popularityScore;
    private boolean damagedStatus;
    private final float rentalRate;
    private int totalRentsCount;
    private boolean isAvailable;

    /**
     * Creates a new game instance with its initial state.
     */
    public Game(String title,
            Category genre,
            int ageRating,
            float popularityScore,
            float rentalRate,
            int totalRentsCount,
            boolean isAvailable) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title must be provided.");
        }
        if (genre == null) {
            throw new IllegalArgumentException("Genre must be provided.");
        }
        if (ageRating < 0) {
            throw new IllegalArgumentException("Age rating cannot be negative.");
        }
        if (popularityScore < 0.0f) {
            throw new IllegalArgumentException("Popularity score cannot be negative.");
        }
        if (rentalRate < 0.0f) {
            throw new IllegalArgumentException("Rental rate cannot be negative.");
        }
        if (totalRentsCount < 0) {
            throw new IllegalArgumentException("Total rents count cannot be negative.");
        }
        this.title = title;
        this.genre = genre;
        this.ageRating = ageRating;
        this.popularityScore = popularityScore;
        this.rentalRate = rentalRate;
        this.totalRentsCount = totalRentsCount;
        this.damagedStatus = totalRentsCount > 20;
        this.isAvailable = isAvailable && !this.damagedStatus;
    }

    /**
     * Finds the most popular game based on popularity score.
     */
    public static Game findMostPopular(List<Game> games) {
        if (games == null || games.isEmpty()) {
            return null;
        }
        Game mostPopular = null;
        for (Game game : games) {
            if (game == null) {
                continue;
            }
            if (mostPopular == null || game.popularityScore > mostPopular.popularityScore) {
                mostPopular = game;
            }
        }
        if (mostPopular != null) {
            System.out.println("The most popular game right now is " + mostPopular.title);
        }
        return mostPopular;
    }

    /**
     * Returns games in a specific genre sorted by popularity (descending).
     */
    public static List<Game> browseGenre(List<Game> games, Category genre) {
        if (games == null || genre == null) {
            return List.of();
        }
        System.out.println("Here are all the games in " + genre + " sorted by their popularity:");
        List<Game> results = new ArrayList<>();
        for (Game game : games) {
            if (game != null && game.genre == genre) {
                results.add(game);
            }
        }
        results.sort(Comparator.comparingDouble(Game::getPopularityScore).reversed());
        return results;
    }

    /**
     * Returns games suitable for the given age, sorted by popularity (descending).
     */
    public static List<Game> browseAge(List<Game> games, int age) {
        if (games == null || age < 0) {
            return List.of();
        }
        System.out.println("At your age of " + age + ", the following games are the most popular:");
        List<Game> results = new ArrayList<>();
        for (Game game : games) {
            if (game != null && game.ageRating <= age) {
                results.add(game);
            }
        }
        results.sort(Comparator.comparingDouble(Game::getPopularityScore).reversed());
        return results;
    }

    /**
     * Attempts to rent the game for a given number of hours.
     */
    public boolean rent(int hours) {
        if (hours <= 0) {
            return false;
        }
        if (damagedStatus) {
            System.out.println("Can't do it, the game is damaged :(");
            return false;
        }
        if (!isAvailable) {
            System.out.println("Can't do it, the game is not available right now.");
            return false;
        }
        System.out.println("Here is your game!");
        totalRentsCount++;
        damagedStatus = totalRentsCount > 20;
        isAvailable = false;
        return true;
    }

    /**
     * Returns the game and calculates the total charge, including late fees.
     */
    public float returnGame(int promisedHours, int realHours) {
        if (promisedHours <= 0 || realHours <= 0) {
            return 0.0f;
        }
        float totalCharge = realHours * rentalRate;
        if (realHours > promisedHours) {
            System.out.println("You're late! Here's your total:");
            totalCharge = (promisedHours + (realHours - promisedHours) * 2.0f) * rentalRate;
        } else {
            System.out.println("Alright! Here is your total:");
        }
        totalRevenue += totalCharge;
        if (!damagedStatus) {
            isAvailable = true;
        }
        return totalCharge;
    }

    /**
     * Returns the total revenue from all rentals.
     */
    public static float getTotalRevenue() {
        return totalRevenue;
    }

    public String getTitle() {
        return title;
    }

    public Category getGenre() {
        return genre;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public float getPopularityScore() {
        return popularityScore;
    }

    public boolean isDamaged() {
        return damagedStatus;
    }

    public float getRentalRate() {
        return rentalRate;
    }

    public int getTotalRentsCount() {
        return totalRentsCount;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
}