package videogamestore;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Game class behavior.
 */
class GameTest {

    /**
     * Verifies that the most popular game is returned.
     */
    @Test
    void findMostPopularReturnsMax() {
        Game g1 = new Game("Alpha", Category.ACTION, 12, 4.5f, 2.0f, 0, true);
        Game g2 = new Game("Beta", Category.PUZZLE, 8, 8.0f, 3.0f, 0, true);
        Game g3 = new Game("Gamma", Category.SIMULATION, 10, 6.5f, 1.5f, 0, true);

        Game mostPopular = Game.findMostPopular(List.of(g1, g2, g3));

        assertNotNull(mostPopular);
        assertEquals("Beta", mostPopular.getTitle());
    }

    /**
     * Verifies genre filtering and popularity ordering.
     */
    @Test
    void browseGenreFiltersAndSorts() {
        Game g1 = new Game("Alpha", Category.ACTION, 12, 4.5f, 2.0f, 0, true);
        Game g2 = new Game("Beta", Category.ACTION, 12, 9.0f, 2.0f, 0, true);
        Game g3 = new Game("Gamma", Category.PUZZLE, 12, 8.0f, 2.0f, 0, true);

        List<Game> results = Game.browseGenre(List.of(g1, g2, g3), Category.ACTION);

        assertEquals(2, results.size());
        assertEquals("Beta", results.get(0).getTitle());
        assertEquals("Alpha", results.get(1).getTitle());
    }

    /**
     * Verifies age filtering and popularity ordering.
     */
    @Test
    void browseAgeFiltersAndSorts() {
        Game g1 = new Game("Alpha", Category.ACTION, 12, 4.5f, 2.0f, 0, true);
        Game g2 = new Game("Beta", Category.ACTION, 18, 9.0f, 2.0f, 0, true);
        Game g3 = new Game("Gamma", Category.PUZZLE, 10, 8.0f, 2.0f, 0, true);

        List<Game> results = Game.browseAge(List.of(g1, g2, g3), 12);

        assertEquals(2, results.size());
        assertEquals("Gamma", results.get(0).getTitle());
        assertEquals("Alpha", results.get(1).getTitle());
    }

    /**
     * Verifies rental status changes and damage threshold handling.
     */
    @Test
    void rentMarksAvailabilityAndDamageAfterThreshold() {
        Game game = new Game("Alpha", Category.ACTION, 12, 4.5f, 2.0f, 20, true);

        assertTrue(game.rent(2));
        assertEquals(21, game.getTotalRentsCount());
        assertTrue(game.isDamaged());
        assertFalse(game.isAvailable());

        assertFalse(game.rent(2));
    }

    /**
     * Verifies late fee calculation and availability on return.
     */
    @Test
    void returnGameCalculatesLateFeeAndUpdatesAvailability() {
        Game game = new Game("Alpha", Category.ACTION, 12, 4.5f, 2.0f, 0, false);
        float revenueBefore = Game.getTotalRevenue();

        float total = game.returnGame(3, 5);

        assertEquals(14.0f, total, 0.0001f);
        assertEquals(revenueBefore + total, Game.getTotalRevenue(), 0.0001f);
        assertTrue(game.isAvailable());
    }
}
