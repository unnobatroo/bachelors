/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vaccination;

/**
 *
 * @author bli
 */
import java.time.LocalDate;

/**
 * Concrete implementation of Vaccine: MediPlus.
 */
public class MediPlus extends Vaccine {

    /**
     * Constructor for MediPlus vaccine.
     * Uses "MediPlus" as the default name.
     *
     * @param validity The expiry date of this vaccine dose/batch.
     */
    public MediPlus(LocalDate validity) {
        super("MediPlus", validity);
    }

    /**
     * Calculates the effect of the MediPlus vaccine.
     *
     * @param days Number of days (e.g., since vaccination). Must be non-negative.
     * @return The calculated effect (days + 10). Returns 10 if days is negative (interpreting days=0).
     */
    @Override
    public int effect(int days) {
         if (days < 0) {
             System.err.println("Warning: days parameter should be non-negative for effect calculation. Using 0.");
             days = 0;
         }
        return Math.min(days + 10, 50);
    }
}