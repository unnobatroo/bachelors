import java.util.ArrayList;

public class Nurse extends Employee {
    private final ArrayList<Patient> patients;

    public Nurse(String id, String name) {
        super(id, name);
        patients = new ArrayList<>();
    }

    public void takeCare(Patient patient) {
        if (!patients.contains(patient)) {
            patients.add(patient);
            patient.addNurse(this);
        }
    }

    public boolean leave(Patient patient) {
        patient.removeNurse(this);
        return patients.remove(patient);
    }
}
