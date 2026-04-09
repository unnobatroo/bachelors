import java.util.ArrayList;
import java.util.List;

public abstract class Employee {
    protected String id;
    protected String name;
    protected List<Hospital> hospitals;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
        this.hospitals = new ArrayList<>();
    }

    public void addHospital(Hospital hospital) {
        if (!hospitals.contains(hospital)) {
            hospitals.add(hospital);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Hospital> getHospitals() {
        return hospitals;
    }
}
