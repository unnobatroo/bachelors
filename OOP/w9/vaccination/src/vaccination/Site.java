/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vaccination;

/**
 *
 * @author bli
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

/**
 * Represents a vaccination site that manages patients and vaccine stock.
 * Includes methods to load initial data from files using Scanner.
 */
public class Site {

    private final String place;
    private final Set<Patient> patients; // Patients are unique so we store them in a set
    private final List<Vaccine> vaccines;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE; // YYYY-MM-DD

    public Site(String place) {
        this.place = Objects.requireNonNull(place, "Site place cannot be null");
        this.patients = new HashSet<>();
        this.vaccines = new ArrayList<>();
    }

    public void procure(Vaccine v) {
        if (v != null) {
            this.vaccines.add(v);
        }
    }

    public boolean register(Patient p) {
        if (p == null) {
            return false;
        }
        boolean added = this.patients.add(p);
        return added;
    }

    public boolean vaccinate(String patientTaj, String vaccineName) {
        Patient targetPatient = findPatientByTaj(patientTaj);

        if (targetPatient == null) {
            System.err.println("Vaccination failed: Patient with TAJ " + patientTaj + " not found/registered at site " + place);
            return false;
        }

        Vaccine vaccineToAdminister = findValidVaccine(vaccineName);

        if (vaccineToAdminister == null) {
            System.err.println("Vaccination failed: No valid dose of '" + vaccineName + "' available for patient " + targetPatient.getName() + " (TAJ: " + patientTaj + ") at site " + place);
            return false;
        }

        Vaccination newVaccination = new Vaccination(LocalDate.now(), vaccineToAdminister);
        targetPatient.addVaccination(newVaccination);
        vaccines.remove(vaccineToAdminister);

        System.out.println("Vaccinated patient " + targetPatient.getName() + " (TAJ: " + targetPatient.getTaj() + ") with " + vaccineToAdminister.getName() + " at site " + place + " on " + newVaccination.getDate());
        return true;
    }

    private Patient findPatientByTaj(String taj) {
        for (Patient p : patients) {
            if (p.getTaj().equals(taj)) {
                return p;
            }
        }
        return null;
    }

    private Vaccine findValidVaccine(String vaccineName) {
        LocalDate today = LocalDate.now();

        for (var v : vaccines) {
            if (v.getName().equals(vaccineName) && v.getValidity().isAfter(today)) {
                return v;
            }
        }
        return null;
    }

    public int countDouble() {
        int count = 0;
        for (Patient p : patients) {
            if (p.howMany() >= 2) {
                count++;
            }
        }
        return count;
    }

    public void read(String patientsPath, String vaccinePath) {
        readPatients(patientsPath);

        readVaccines(vaccinePath);        
    }

    /**
     * Loads patient data from a text file registers them. Line format: TAJ Name
     * (e.g., "123456789 Lionel Johnson"). Skips empty lines or lines causing
     * errors, printing a warning.
     *
     * @param path The path to the patient data file.
     */
    public void readPatients(String path) {
        System.out.println("Loading patients from: " + path);
        int lineNumber = 0;
        int processedCount = 0;
        int skippedCount = 0;
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(path)))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                // Split into TAJ (first word) and Name (rest of the line)
                String[] parts = line.split("\\s+", 2);

                if (parts.length < 2 || parts[0].isEmpty() || parts[1].isEmpty()) {
                    System.err.println("Warning line " + lineNumber + ": Invalid format '" + line + "', expected 'TAJ Name'. Skipping.");
                    skippedCount++;
                    continue;
                }

                String taj = parts[0];
                String name = parts[1];

                try {
                    Patient newPatient = new Patient(taj, name);
                    if (register(newPatient)) {
                        processedCount++;
                    } else {
                        System.err.println("Warning line " + lineNumber + ": Patient with TAJ '" + taj + "' already registered. Skipping duplicate entry.");
                        skippedCount++;
                    }
                } catch (IllegalArgumentException e) { // Catch errors from Patient constructor (e.g., empty name/taj after split)
                    System.err.println("Error processing patient line " + lineNumber + ": '" + line + "' - " + e.getMessage());
                    skippedCount++;
                } catch (Exception e) { // Catch other unexpected errors
                    System.err.println("Unexpected error on patient line " + lineNumber + ": '" + line + "' - " + e.getMessage());
                    skippedCount++;
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read patient file: " + path + " - " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred while loading patients: " + e.getMessage());
            e.printStackTrace(); // Print stack trace for unexpected errors
        } finally {
            System.out.println("Finished loading patients. Loaded: " + processedCount + ", Skipped/Duplicate: " + skippedCount);
        }
    }

    /**
     * Loads vaccine stock data from a text file using Scanner and procures
     * them. Each line format: VaccineType ValidityDate (e.g., "MediProduct
     * 2026-12-31"). Skips lines with incorrect format, unknown vaccine types,
     * or invalid dates.
     *
     * @param path The path to the vaccine stock file.
     */
    public void readVaccines(String path) {
        System.out.println("Loading vaccine stock from: " + path);
        int lineNumber = 0;
        int processedCount = 0;
        int skippedCount = 0;
        // Use try-with-resources with Scanner
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(path)))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] parts = line.split("\\s+", 2);

                if (parts.length != 2) {
                    System.err.println("Warning line " + lineNumber + ": Invalid format '" + line + "', expected 'VaccineType YYYY-MM-DD'. Skipping.");
                    skippedCount++;
                    continue;
                }

                String vaccineType = parts[0];
                String dateString = parts[1];
                LocalDate validityDate;

                try {
                    validityDate = LocalDate.parse(dateString, DATE_FORMATTER);
                } catch (DateTimeParseException e) {
                    System.err.println("Warning line " + lineNumber + ": Invalid date format '" + dateString + "' in line '" + line + "'. Skipping.");
                    skippedCount++;
                    continue;
                }

                Vaccine newVaccine = null;
                // Use equalsIgnoreCase for flexibility in vaccine type casing in file
                if (vaccineType.equalsIgnoreCase("MediProduct")) {
                    newVaccine = new MediProduct(validityDate);
                } else if (vaccineType.equalsIgnoreCase("MediPlus")) {
                    newVaccine = new MediPlus(validityDate);
                } else if (vaccineType.equalsIgnoreCase("MediPower")) {
                    newVaccine = new MediPower(validityDate);
                } else {
                    System.err.println("Warning line " + lineNumber + ": Unknown vaccine type '" + vaccineType + "'. Skipping.");
                    skippedCount++;
                    continue;
                }

                procure(newVaccine);
                processedCount++;
            }
        } catch (IOException e) {
            System.err.println("Failed to read vaccine stock file: " + path + " - " + e.getMessage());
        } catch (Exception e) { // Catch other unexpected errors
            System.err.println("An unexpected error occurred while loading vaccine stock: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Finished loading vaccine stock. Procured: " + processedCount + ", Skipped: " + skippedCount);
        }
    }

    /**
     * Reads vaccination data from a text file and processes it. Each line
     * format: TAJ VaccineName (e.g., "123456789 MediProduct"). Skips invalid
     * lines or lines with errors during processing.
     *
     * @param path The path to the vaccination data file.
     */
    public void vaccinateAllFromFile(String path) {
        System.out.println("Loading vaccination requests from: " + path);
        int lineNumber = 0;
        int processedCount = 0;
        int skippedCount = 0;

        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(path)))) {
            while (scanner.hasNextLine()) {
                lineNumber++;
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] parts = line.split("\\s+", 2);

                if (parts.length != 2) {
                    System.err.println("Warning line " + lineNumber + ": Invalid format '" + line + "', expected 'TAJ VaccineName'. Skipping.");
                    skippedCount++;
                    continue;
                }

                String taj = parts[0];
                String vaccineName = parts[1];

                try {
                    boolean success = vaccinate(taj, vaccineName);
                    if (success) {
                        processedCount++;
                    } else {
                        System.err.println("Warning line " + lineNumber + ": Failed to vaccinate patient with TAJ '" + taj + "' using vaccine '" + vaccineName + "'.");
                        skippedCount++;
                    }
                } catch (Exception e) {
                    System.err.println("Unexpected error on vaccination line " + lineNumber + ": '" + line + "' - " + e.getMessage());
                    skippedCount++;
                }
            }
        } catch (IOException e) {
            System.err.println("Failed to read vaccination file: " + path + " - " + e.getMessage());
        } catch (Exception e) { // Catch other unexpected errors
            System.err.println("An unexpected error occurred while loading vaccinations: " + e.getMessage());
            e.printStackTrace();
        } finally {
            System.out.println("Finished processing vaccinations. Processed: " + processedCount + ", Skipped: " + skippedCount);
        }
    }

    public Set<Patient> getRegisteredPatients() {
        return Collections.unmodifiableSet(patients);
    }

    public List<Vaccine> getAvailableVaccines() {
        return Collections.unmodifiableList(vaccines);
    }

    public String getPlace() {
        return place;
    }
}
