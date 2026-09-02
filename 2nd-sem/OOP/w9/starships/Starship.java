/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starships;

/**
 *
 * @author bli
 */

/**
 * Abstract base class for all starships
 */
public abstract class Starship {
    /**
     * The Name.
     */
    protected String name;
    /**
     * The Shield.
     */
    protected int shield;
    /**
     * The Armor.
     */
    protected int armor;
    /**
     * The Marines.
     */
    protected int marines;
    protected Planet planet;

    /**
     * Instantiates a new Starship.
     *
     * @param n the n
     * @param a the a
     * @param s the s
     * @param m the m
     */
    public Starship(String n, int a, int s, int m) {
        this.name = n;
        this.armor = a;
        this.shield = s;
        this.marines = m;
        this.planet = null;
    }

    /**
     * Protect.
     *
     * @param p the p
     */
    public void protect(Planet p) {
        if (planet != null) {
            planet.leave(this);
        }
        planet = p;
        planet.arrive(this);
    }

    /**
     * Leave.
     */
    public void leave() {
        if (planet == null) {
            throw new IllegalStateException("Attempting to leave planet but not at a planet!");
        }
        planet.leave(this);
        planet = null;
    }

    /**
     * Fire power double.
     *
     * @return the double
     */
    public abstract double firePower();

    @Override
    public String toString() {
        return "Starship{" + "name=" + name + ", shield=" + shield + ", armor=" + armor + ", marines=" + marines + '}';
    }

    /**
     * Gets shield.
     *
     * @return the shield
     */
    public int getShield() {
        return shield;
    }
}
