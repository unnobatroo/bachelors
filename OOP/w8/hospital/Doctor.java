package hospital;

import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author mekigelashvili
 */
public class Doctor extends Employee {

    private List<Patient> patients;

    public Doctor(String id, String name) {
        super(id, name);
        this.patients = new ArrayList<>();
    }

    public void treat(Patient patient) {
        if (!this.patients.contains(patient)) {
            this.patients.add(patient);
            patient.setDoctor(this);
        }
    }

    public boolean discharge(Patient patient) {
        return this.patients.remove(patient);
    }

    public List<Patient> getPatients() {
        return patients;
    }

    @Override
    public String toString() {
        return "Doctor{" + "name=" + name + ", patients=" + patients + '}';
    }

}
