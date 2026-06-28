/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vaccination;

import java.time.LocalDate;

/**
 *
 * @author bli
 */

/**
 * Abstract representation of a vaccine type.
 * Concrete implementations would represent specific vaccines.
 */
public abstract class Vaccine {

    protected String name;
    protected LocalDate validity;

    /**
     * Constructor for Vaccine.
     * @param name The name of the vaccine.
     * @param validity The expiry date of this vaccine dose.
     */
    protected Vaccine(String name, LocalDate validity) {
        this.name = name;
        this.validity = validity;
    }

    public String getName() {
        return name;
    }

    public LocalDate getValidity() {
        return validity;
    }

    /**
     * Abstract method to calculate the effect based on days passed.
     * The specific calculation will depend on the concrete vaccine type.
     * @param days Number of days (since vaccination)
     * @return An integer representing the effect (interpretation depends on subclass).
     */
    public abstract int effect(int days);

    @Override
    public String toString() {
        return "Vaccine{" +
               "name='" + name + '\'' +
               ", validity=" + validity +
               '}';
    }
}