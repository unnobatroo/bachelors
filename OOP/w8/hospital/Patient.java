/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mekigelashvili
 */
public class Patient {
    private String name;
    private Doctor doctor;
    private List<Nurse> nurses;
    
    public Patient(String name) {
        this.name = name;
        this.nurses = new ArrayList<>();
    }
    
    public boolean discharge() {
        boolean l = doctor.discharge(this);
        for (var n : nurses) {
            l = l && n.leave(this);
        }
        return l;
    }

    public void addNurse(Nurse n) {
        if (!nurses.contains(n)) {
            nurses.add(n);
        }
    }
    
    public void removeNurse(Nurse n) {
        nurses.remove(n);
    }

    public List<Nurse> getNurses() {
        return nurses;
    }
        
    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Patient{" + "name=" + name + '}';
    }        
    
}
