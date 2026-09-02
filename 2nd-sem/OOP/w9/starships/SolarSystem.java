/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starships;

/**
 *
 * @author bli
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * SolarSystem containing planets
 */
public class SolarSystem {

    private final List<Planet> planets;
    private final List<Starship> ships;

    /**
     * Instantiates a new Solar system.
     */
    public SolarSystem() {
        this.planets = new ArrayList<>();
        this.ships = new ArrayList<>();
    }

    /**
     * Populates the solar system from text files
     *
     * @param planetsFile     Path to the planets data file
     * @param shipsFile       Path to the ships data file
     * @param assignmentsFile Path to the ship-planet assignments file
     */
    public void read(String planetsFile, String shipsFile, String assignmentsFile) {
        // Read planets from file
        readPlanets(planetsFile);

        // Read ships and store them temporarily
        readShips(shipsFile);

        // Assign ships to planets
        assignShips(assignmentsFile, ships);
    }

    private void readPlanets(String fileName) {
        // this is a try-with-resources
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            while (scanner.hasNext()) {
                String planetName = scanner.next();
                Planet planet = new Planet(planetName);
                planets.add(planet);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading planets file: " + e.getMessage());
        }
    }

    private List<Starship> readShips(String fileName) {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {
            // Read each ship
            while (scanner.hasNext()) {
                String type = scanner.next();
                String name = scanner.next();
                int armor = scanner.nextInt();
                int shield = scanner.nextInt();
                int marines = scanner.nextInt();

                Starship ship = switch (type) {
                    case "StarDestroyer" -> new StarDestroyer(name, armor, shield, marines);
                    case "Landing" -> new Landing(name, armor, shield, marines);
                    case "Laser" -> new Laser(name, armor, shield, marines);
                    default -> {
                        throw new IllegalStateException("Illegal ship type in input file.");
                    }
                };

                ships.add(ship);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading ships file: " + e.getMessage());
        }
        return ships;
    }

    /**
     * Assigns ships to planets based on an assignments file
     */
    private void assignShips(String fileName, List<Starship> ships) {
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(fileName)))) {

            while (scanner.hasNext()) {
                String shipName = scanner.next();
                String planetName = scanner.next();

                Starship ship = findShipByName(shipName);
                Planet planet = findPlanetByName(planetName);

                if (ship != null && planet != null) {
                    ship.protect(planet);
                    System.out.println("Assigned " + shipName + " to protect " + planetName);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error reading assignments file: " + e.getMessage());
        }
    }

    /**
     * Find a ship by name in a list of ships
     *
     * @param name the name
     * @return the starship
     */
    public Starship findShipByName(String name) {
        for (Starship ship : ships) {
            if (ship.name.equals(name)) {
                return ship;
            }
        }
        return null;
    }

    /**
     * Find a planet by name
     *
     * @param name the name
     * @return the planet
     */
    public Planet findPlanetByName(String name) {
        for (Planet planet : planets) {
            if (planet.getName().equals(name)) {
                return planet;
            }
        }
        return null;
    }

    /**
     * Max fire power starship.
     *
     * @return the starship
     */
    public Starship maxFirePower() {
        Starship maxShip = null;
        double maxPower = 0;
        for (Planet planet : planets) {
            Starship ship = planet.maxFirePower();
            if (ship != null) {
                double power = ship.firePower();
                if (maxShip == null || power > maxPower) {
                    maxPower = power;
                    maxShip = ship;
                }
            }
        }
        return maxShip;
    }

    /**
     * Unprotected list.
     *
     * @return the list
     */
    public List<Planet> unprotected() {
        List<Planet> unprotectedPlanets = new ArrayList<>();
        for (Planet planet : planets) {
            if (planet.numShips() == 0) {
                unprotectedPlanets.add(planet);
            }
        }
        return unprotectedPlanets;
    }

    /**
     * Gets planets.
     *
     * @return the planets
     */
    public List<Planet> getPlanets() {
        return new ArrayList<>(planets);
    }
}
