/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starships;

/**
 * The type Landing.
 *
 * @author bli
 */
public class Landing extends Starship {
    /**
     * Instantiates a new Landing.
     *
     * @param n the n
     * @param a the a
     * @param s the s
     * @param m the m
     */
    public Landing(String n, int a, int s, int m) {
        super(n, a, s, m);
    }

    @Override
    public double firePower() {
        return marines;
    }
}
