/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package vaccination;

/**
 *
 * @author bli
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Site site = new Site("Terra Vaccination Center");
        site.read("patients.txt", "vaccines.txt");
        site.vaccinateAllFromFile("requests.txt");
        
        System.out.println("Patients, their vaccinations, and their effectiveness in 10 days, and today:");
        for (var patient : site.getRegisteredPatients()) {
            System.out.println("\t" + patient.getTaj() + " " + patient.getName());
            for (var vaccination : patient.getVaccinations()) {
                var vaccine = vaccination.getVaccine();
                System.out.println("\t\t" + vaccine.getName() + " 10 days: " + vaccine.effect(10)
                + " today: " + vaccination.effect());
            }
        }
    }

}
