import java.util.ArrayList;
import java.util.List;

public class Doctor extends Employee {
    private final List<Patient> patients;

    public Doctor(String id, String name) {
        super(id, name);
        patients = new ArrayList<>();
    }

    public void treat(Patient patient) {
        if (!patients.contains(patient)) {
            patients.add(patient);
            patient.setDoctor(this);
        }
    }

    public boolean discharge(Patient patient) {
        patient.setDoctor(null);
        return patients.remove(patient);
    }
}
