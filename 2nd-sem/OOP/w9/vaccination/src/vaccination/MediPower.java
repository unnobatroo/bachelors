package vaccination;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author bli
 */
import java.time.LocalDate;

/**
 * Concrete implementation of Vaccine: MediPower.
 */
public class MediPower extends Vaccine {

    /**
     * Constructor for MediPower vaccine.
     * Uses "MediPower" as the default name.
     *
     * @param validity The expiry date of this vaccine dose/batch.
     */
    public MediPower(LocalDate validity) {
        super("MediPower", validity);
    }

    /**
     * Calculates the effect of the MediPower vaccine.
     *
     * @param days Number of days (e.g., since vaccination). Must be non-negative.
     * @return The calculated effect (2^days). Returns 1 if days is negative.
     */
    @Override
    public int effect(int days) {
         if (days < 0) {
             return 1;
         }
        double result = Math.min(Math.pow(2, days), 50);
        return (int) result;
    }
}