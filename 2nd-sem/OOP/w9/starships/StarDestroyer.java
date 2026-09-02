/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package starships;

/**
 * The type Star destroyer.
 *
 * @author bli
 */
public class StarDestroyer extends Starship {
    /**
     * Instantiates a new Star destroyer.
     *
     * @param n the n
     * @param a the a
     * @param s the s
     * @param m the m
     */
    public StarDestroyer(String n, int a, int s, int m) {
        super(n, a, s, m);
    }

    @Override
    public double firePower() {
        return armor / 2.0;
    }
}
