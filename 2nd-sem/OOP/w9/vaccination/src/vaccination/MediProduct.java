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
 * Concrete implementation of Vaccine: MediProduct.
 */
public class MediProduct extends Vaccine {

    /**
     * Constructor for MediProduct vaccine.
     * Uses "MediProduct" as the default name.
     *
     * @param validity The expiry date of this vaccine dose/batch.
     */
    public MediProduct(LocalDate validity) {
        super("MediProduct", validity);
    }

    /**
     * Calculates the effect of the MediProduct vaccine.
     *
     * @param days Number of days (e.g., since vaccination). Must be non-negative.
     * @return The calculated effect (2 * days). Returns 0 if days is negative (interpreting days as 0).
     */
    @Override
    public int effect(int days) {
        if (days < 0) {
             System.err.println("Warning: days parameter should be non-negative for effect calculation.");
             return 0;
        }
        return Math.min(2 * days, 50);
    }
}