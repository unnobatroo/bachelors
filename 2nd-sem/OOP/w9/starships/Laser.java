/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starships;

/**
 * The type Laser.
 *
 * @author bli
 */
public class Laser extends Starship {
    /**
     * Instantiates a new Laser.
     *
     * @param n the n
     * @param a the a
     * @param s the s
     * @param m the m
     */
    public Laser(String n, int a, int s, int m) {
        super(n, a, s, m);
    }

    @Override
    public double firePower() {
        return shield;
    }
}
