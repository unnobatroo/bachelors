/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package hospital;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mekigelashvili
 */
public class Hospital {

    public List<Employee> employees;
    public List<Patient> patients;

    public Hospital() {
        this.employees = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    public void heal(Patient patient) {
        if (!patients.contains(patient)) {
            this.patients.add(patient);
        }
    }

    public void employ(Employee e) {
        if (!employees.contains(e)) {
            employees.add(e);
            e.hospitals.add(this);
        }
    }

    public void assign(Doctor doctor, Patient patient) {
        if (employees.contains(doctor)) {
            doctor.treat(patient);
        } else {
            System.out.println("Doctor does not work at this hospital.");
        }
    }

    public void assign(Nurse nurse, Patient patient) {
        if (employees.contains(nurse)) {
            nurse.takeCare(patient);
        } else {
            System.out.println("Nurse does not work at this hospital.");
        }
    }
    
    public boolean discharge(Patient patient) {
        return patient.discharge();
    }

}
