import java.util.ArrayList;
import java.util.List;

public class Hospital {
    public final List<Employee> employees;
    public List<Patient> patients;

    public Hospital() {
        this.employees = new ArrayList<>();
        this.patients = new ArrayList<>();
    }

    public void employ(Employee employee) {
        if (!employees.contains(employee)) {
            employees.add(employee);
        }

        employee.addHospital(this);
    }

    public void heal(Patient patient) {
        if (!patients.contains(patient)) {
            patients.add(patient);
        }
    }

    public void assign(Doctor doctor, Patient patient) {
        if (!employees.contains(doctor)) {
            doctor.treat(patient);
        } else {
            System.out.println("Fake doctor!");
        }
    }

    public void assign(Nurse nurse, Patient patient) {
        if (!employees.contains(nurse)) {
            nurse.takeCare(patient);
        }
    }
}
