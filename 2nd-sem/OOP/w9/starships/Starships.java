/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package starships;

/**
 * The type Starships.
 *
 * @author bli
 */
public class Starships {

    /**
     * Main.
     *
     * @param args the command line arguments
     */
    static void main(String[] args) {
        SolarSystem solarSystem = new SolarSystem();
        solarSystem.read("planets.txt", "starships.txt", "assignments.txt");

        System.out.println("Unprotected planets: " + solarSystem.unprotected());
        System.out.println("Ship with max firepower: " + solarSystem.maxFirePower());
        System.out.println("Total shield strength on Terra: " + solarSystem.findPlanetByName("Terra").totalShield());

        System.out.println();
        System.out.println("Reassignment: Eisenstein is reassigned to Terra");
        Planet terra = solarSystem.findPlanetByName("Terra");
        Starship eisenstein = solarSystem.findShipByName("Eisenstein");
        eisenstein.protect(terra);
        System.out.println("Unprotected planets: " + solarSystem.unprotected());
        System.out.println("Total shield strength on Terra: " + solarSystem.findPlanetByName("Terra").totalShield());
    }

}
