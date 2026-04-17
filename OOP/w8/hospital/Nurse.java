package hospital;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author mekigelashvili
 */
public class Nurse extends Employee {

    public ArrayList<Patient> patients;

    public Nurse(String id, String name) {
        super(id, name);
        this.patients = new ArrayList<>();

    }

    public void takeCare(Patient patient) {
        if (!this.patients.contains(patient)) {
            this.patients.add(patient);
            patient.addNurse(this);
        }
    }
    
    public boolean leave(Patient patient) {
        patient.removeNurse(this);
        return patients.remove(patient);
    }

    @Override
    public String toString() {
        return "Nurse{" + "name=" + name + ", patients=" + patients + '}';
    }
}
