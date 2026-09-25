package capitaly;

import capitaly.factory.FieldFactory;
import capitaly.factory.PlayerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.Scanner;

public class TestRunner {

    private static int passed = 0;
    private static int failed = 0;

    private static void assertTrue(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.err.println("[FAIL] " + testName);
            failed++;
        }
    }

    private static void assertEquals(
        String testName,
        Object expected,
        Object actual
    ) {
        if (expected == null ? actual == null : expected.equals(actual)) {
            assertTrue(testName, true);
        } else {
            System.err.println(
                "[FAIL] " +
                    testName +
                    " - expected: " +
                    expected +
                    ", got: " +
                    actual
            );
            failed++;
        }
    }

    public static void main(String[] args) {
        System.out.println("Running Capitaly test suite...\n");

        testGreedyStrategy();
        testCarefulStrategy();
        testTacticalStrategy();
        testFactories();
        testLuckyField();
        testServiceField();
        testPropertyFieldPurchaseAndRent();
        testPropertyFieldHouseAndElimination();
        testCyclicMovement();
        testGameLoaderValid();
        testGameLoaderInvalid();
        testFullSimulationScenario();

        System.out.printf("%n%d passed, %d failed%n", passed, failed);
        if (failed > 0) {
            System.exit(1);
        }
    }

    private static void testGreedyStrategy() {
        Player player = new GreedyPlayer("G1");
        assertEquals("strategy name", "Greedy", player.getStrategyName());
        assertTrue(
            "greedy buys when it can afford the 1000 price",
            player.wantsToBuyProperty(1000)
        );
        assertTrue(
            "greedy builds when it can afford the 4000 price",
            player.wantsToBuildHouse(4000)
        );

        player.pay(9500); // leaves 500
        assertTrue(
            "greedy cannot buy with 500 left",
            !player.wantsToBuyProperty(1000)
        );
        assertTrue(
            "greedy cannot build with 500 left",
            !player.wantsToBuildHouse(4000)
        );
    }

    private static void testCarefulStrategy() {
        Player player = new CarefulPlayer("C1"); // starts with 10000
        assertEquals("strategy name", "Careful", player.getStrategyName());
        assertTrue(
            "careful buys: 1000 <= 10000/2",
            player.wantsToBuyProperty(1000)
        );
        assertTrue(
            "careful builds: 4000 <= 10000/2",
            player.wantsToBuildHouse(4000)
        );

        player.pay(3000); // 7000 left, half is 3500
        assertTrue(
            "careful refuses build: 4000 > 7000/2",
            !player.wantsToBuildHouse(4000)
        );
        assertTrue(
            "careful still buys: 1000 <= 7000/2",
            player.wantsToBuyProperty(1000)
        );

        player.pay(5500); // 1500 left, half is 750
        assertTrue(
            "careful refuses buy: 1000 > 1500/2",
            !player.wantsToBuyProperty(1000)
        );
    }

    private static void testTacticalStrategy() {
        Player player = new TacticalPlayer("T1");
        assertEquals("strategy name", "Tactical", player.getStrategyName());
        assertTrue(
            "tactical takes 1st chance",
            player.wantsToBuyProperty(1000)
        );
        assertTrue(
            "tactical skips 2nd chance",
            !player.wantsToBuyProperty(1000)
        );
        assertTrue(
            "tactical takes 3rd chance",
            player.wantsToBuyProperty(1000)
        );
        assertTrue(
            "tactical skips 4th chance (house)",
            !player.wantsToBuildHouse(4000)
        );
        assertTrue(
            "tactical takes 5th chance (house)",
            player.wantsToBuildHouse(4000)
        );
    }

    private static void testFactories() {
        try {
            Field prop = FieldFactory.createField(
                0,
                "Property",
                new Scanner("")
            );
            assertTrue(
                "factory creates PropertyField",
                prop instanceof PropertyField
            );

            Field serv = FieldFactory.createField(
                1,
                "Service",
                new Scanner("500")
            );
            assertTrue(
                "factory creates ServiceField",
                serv instanceof ServiceField
            );
            assertEquals(
                "service cost parsed",
                500,
                ((ServiceField) serv).getCost()
            );

            Field luck = FieldFactory.createField(
                2,
                "Lucky",
                new Scanner("300")
            );
            assertTrue(
                "factory creates LuckyField",
                luck instanceof LuckyField
            );
            assertEquals(
                "lucky reward parsed",
                300,
                ((LuckyField) luck).getReward()
            );

            assertTrue(
                "factory creates GreedyPlayer",
                PlayerFactory.createPlayer("G", "Greedy") instanceof
                    GreedyPlayer
            );
            assertTrue(
                "factory creates CarefulPlayer",
                PlayerFactory.createPlayer("C", "Careful") instanceof
                    CarefulPlayer
            );
            assertTrue(
                "factory creates TacticalPlayer",
                PlayerFactory.createPlayer("T", "Tactical") instanceof
                    TacticalPlayer
            );
        } catch (Exception e) {
            assertTrue("factory test threw: " + e.getMessage(), false);
        }
    }

    private static void testLuckyField() {
        Player player = new GreedyPlayer("P1");
        new LuckyField(1, 500).apply(player);
        assertEquals("lucky field pays out", 10500, player.getMoney());
    }

    private static void testServiceField() {
        Player player = new GreedyPlayer("P1");
        new ServiceField(1, 2000).apply(player);
        assertEquals("service fee deducted", 8000, player.getMoney());

        new ServiceField(2, 9000).apply(player);
        assertTrue(
            "unaffordable service bankrupts the player",
            !player.isAlive()
        );
    }

    private static void testPropertyFieldPurchaseAndRent() {
        PropertyField prop = new PropertyField(1);
        Player p1 = new GreedyPlayer("P1");
        Player p2 = new CarefulPlayer("P2");

        prop.apply(p1);
        assertEquals("buyer pays 1000", 9000, p1.getMoney());
        assertEquals("buyer becomes owner", p1, prop.getOwner());
        assertEquals("property added to player", 1, p1.getProperties().size());

        prop.apply(p2);
        assertEquals("visitor pays 500 rent", 9500, p2.getMoney());
        assertEquals("owner receives 500 rent", 9500, p1.getMoney());
    }

    private static void testPropertyFieldHouseAndElimination() {
        PropertyField prop = new PropertyField(1);
        Player owner = new GreedyPlayer("Owner");
        Player visitor = new CarefulPlayer("Visitor");

        prop.apply(owner); // buys for 1000
        prop.apply(owner); // builds house for 4000
        assertTrue("house built", prop.hasHouse());
        assertEquals("owner paid 5000 total", 5000, owner.getMoney());

        visitor.pay(9000); // 1000 left, house rent is 2000
        prop.apply(visitor);
        assertTrue("visitor bankrupt on unaffordable rent", !visitor.isAlive());
        assertEquals(
            "owner collects the remaining 1000",
            6000,
            owner.getMoney()
        );
    }

    private static void testCyclicMovement() {
        Player player = new GreedyPlayer("Runner");
        player.move(3, 5);
        assertEquals("moves 3 fields", 3, player.getPosition());
        player.move(4, 5);
        assertEquals("wraps around: (3+4) % 5 = 2", 2, player.getPosition());
    }

    private static void testGameLoaderValid() {
        try {
            GameLoader.GameData data = GameLoader.loadFromFile(
                "sample_game.txt"
            );
            assertEquals("field count", 6, data.fields().size());
            assertEquals("player count", 3, data.players().size());
            assertEquals("dice rolls", 12, data.diceRolls().size());
        } catch (Exception e) {
            assertTrue("valid file should load without exceptions", false);
        }
    }

    private static void testGameLoaderInvalid() {
        boolean caught = false;
        try {
            GameLoader.loadFromFile("non_existent_file.txt");
        } catch (FileNotFoundException e) {
            caught = true;
        } catch (Exception ignored) {
        }
        assertTrue("missing file -> FileNotFoundException", caught);

        try {
            File temp = File.createTempFile("bad_game", ".txt");
            temp.deleteOnExit();
            Files.writeString(
                temp.toPath(),
                "2\nUnknownField\nProperty\n1\nP Greedy"
            );
            caught = false;
            try {
                GameLoader.loadFromFile(temp.getAbsolutePath());
            } catch (InvalidInputException e) {
                caught = true;
            }
            assertTrue("unknown field type -> InvalidInputException", caught);
        } catch (Exception e) {
            assertTrue("temp file setup failed", false);
        }
    }

    private static void testFullSimulationScenario() {
        try {
            GameLoader.GameData data = GameLoader.loadFromFile(
                "sample_bankruptcy.txt"
            );
            new CapitalyGame(
                data.fields(),
                data.players(),
                Dice.fromList(data.diceRolls())
            ).playRounds(2);
            assertEquals(
                "David ends with 100",
                100,
                data.players().get(0).getMoney()
            );
            assertEquals(
                "Eva ends with 100",
                100,
                data.players().get(1).getMoney()
            );
        } catch (Exception e) {
            assertTrue("simulation should run without exceptions", false);
        }
    }
}
