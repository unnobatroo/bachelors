/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starships;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bli
 */

/**
 * Planet class that can have ships stationed at
 */
public class Planet {
    private final String name;
    private final List<Starship> ships;

    /**
     * Instantiates a new Planet.
     *
     * @param n the n
     */
    public Planet(String n) {
        this.name = n;
        this.ships = new ArrayList<>();
    }

    /**
     * Arrive.
     *
     * @param s the s
     */
    public void arrive(Starship s) {
        if (s == null) {
            throw new IllegalArgumentException("Null Starship attempted to arrive!");
        }

        if (s.planet == this) {
            ships.add(s);
        } else {
            throw new IllegalStateException("Starship attempted to arrive but is not assigned to this planet!");
        }
    }

    /**
     * Leave.
     *
     * @param s the s
     */
    public void leave(Starship s) {
        if (s == null) {
            throw new IllegalArgumentException("Null Starship attempted to leave!");
        }

        if (s.planet == this && ships.contains(s)) {
            ships.remove(s);
        } else {
            throw new IllegalStateException("Starship attempted to leave but is not on this planet!");
        }
    }

    /**
     * Num ships int.
     *
     * @return the int
     */
    public int numShips() {
        return ships.size();
    }

    /**
     * Total shield double.
     *
     * @return the double
     */
    public double totalShield() {
        double total = 0;
        for (Starship ship : ships) {
            total += ship.getShield();
        }
        return total;
    }

    /**
     * Max fire power starship.
     *
     * @return the starship
     */
    public Starship maxFirePower() {
        Starship maxShip = null;
        double maxPower = 0;
        for (Starship ship : ships) {
            double power = ship.firePower();
            if (maxShip == null || power > maxPower) {
                maxShip = ship;
                maxPower = power;
            }
        }
        return maxShip;
    }

    @Override
    public String toString() {
        return "Planet{" + "name=" + name + '}';
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

}
