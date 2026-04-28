/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vaccination;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 *
 * @author bli
 */
/**
 * Represents a single vaccination event for a patient.
 */
public class Vaccination {

    private final LocalDate date; // Date the vaccination was administered
    private final Vaccine vaccine; // The specific vaccine used (type and batch info like validity)

    /**
     * Creates a new Vaccination record.
     *
     * @param date The date the vaccination occurred.
     * @param vaccine The specific vaccine dose administered.
     */
    public Vaccination(LocalDate date, Vaccine vaccine) {
        this.date = Objects.requireNonNull(date, "Vaccination date cannot be null");
        this.vaccine = Objects.requireNonNull(vaccine, "Vaccine for vaccination cannot be null");
    }

    // Extra method not in the class diagram
    /**
     * Calculate the current effect of the vaccine today
     * @return 
     */
    public int effect() {
        LocalDate today = LocalDate.now();
        long days = ChronoUnit.DAYS.between(date, today);
        return vaccine.effect((int) days);
    }

    public LocalDate getDate() {
        return date;
    }

    public Vaccine getVaccine() {
        return vaccine;
    }

    @Override
    public String toString() {
        return "Vaccination{"
                + "date=" + date
                + ", vaccine=" + vaccine.getName()
                + // Displaying name for simplicity
                '}';
    }
}
