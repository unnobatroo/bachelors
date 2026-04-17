/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hospital;

/**
 *
 * @author mekigelashvili
 */
public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital();
        Doctor doctor1 = new Doctor("D11", "Dr. Carter");
        Nurse nurse1 = new Nurse("N10", "Carol");
        Nurse nurse2 = new Nurse("N11", "James");
        Staff staff1 = new Staff("S1", "Joe");
      
        hospital.employ(doctor1);
        hospital.employ(nurse1);
        hospital.employ(nurse2);
        hospital.employ(staff1);      
        
        Patient patient1 = new Patient("Anne");
        hospital.heal(patient1);
        
        hospital.assign(doctor1, patient1);
        hospital.assign(nurse1, patient1);
        hospital.assign(nurse2, patient1);
        
        System.out.println("Doctor of " + patient1 + " is " + patient1.getDoctor());
        System.out.println("Nurses of " + patient1 + " are " + patient1.getNurses());
        
    }
}
