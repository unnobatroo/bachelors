/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vaccination;

/**
 *
 * @author bli
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Represents a patient registered for vaccination, identified by TAJ and having a name.
 */
public class Patient {

    private final String name;
    private final String taj; // Unique identifier (Hungarian Social Security Number)
    private final List<Vaccination> vaccinations;

    /**
     * Creates a new Patient.
     * @param taj The patient's unique TAJ identifier. Must not be null or empty.
     * @param name The patient's name. Must not be null or empty.
     */
    public Patient(String taj, String name) {
        this.taj = Objects.requireNonNull(taj, "TAJ cannot be null");
        this.name = Objects.requireNonNull(name, "Name cannot be null");
        if (taj.trim().isEmpty()) {
            throw new IllegalArgumentException("TAJ cannot be empty");
        }
         if (name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.vaccinations = new ArrayList<>();
    }

    public String getTaj() {
        return taj;
    }

    public String getName() {
        return name;
    }

    /**
     * Adds a vaccination record to the patient's history.
     * @param vaccination The vaccination record to add.
     */
    public void addVaccination(Vaccination vaccination) {
        if (vaccination != null) {
             this.vaccinations.add(vaccination);
        }
    }

    /**
     * Returns an unmodifiable view of the patient's vaccination history.
     * @return An unmodifiable list of Vaccinations.
     */
    public List<Vaccination> getVaccinations() {
        return Collections.unmodifiableList(vaccinations);
    }

    /**
     * Calculates how many vaccinations the patient has received.
     * @return The number of vaccinations.
     */
    public int howMany() {
        return vaccinations.size();
    }

    /**
     * Equals method based *only* on the unique TAJ identifier.
     * This ensures patients are considered the same in Sets if their TAJ matches.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return taj.equals(patient.taj); // Equality based solely on TAJ
    }

    /**
     * HashCode method based *only* on the unique TAJ identifier.
     */
    @Override
    public int hashCode() {
        return Objects.hash(taj); // Hash code based solely on TAJ
    }

    @Override
    public String toString() {
        return "Patient{" +
               "taj='" + taj + '\'' +
               ", name='" + name + '\'' +
               ", vaccinationCount=" + howMany() +
               '}';
    }
}