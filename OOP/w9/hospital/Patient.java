import java.util.List;

public class Patient {
    private String name;
    private Doctor doctor;
    private List<Nurse> nurses;

    public Patient(String name) {
        this.name = name;
    }

    public void addNurse(Nurse nurse) {
        if (!nurses.contains(nurse)) {
            nurses.add(nurse);
        }
    }

    public void removeNurse(Nurse nurse) {
        nurses.remove(nurse);
    }

    public void discharge() {
        boolean l = doctor.discharge(this);

        for (var n : nurses) {
            l = l && n.leave(this);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
