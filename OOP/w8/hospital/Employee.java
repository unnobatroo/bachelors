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
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Hospital> getHospitals() {
        return hospitals;
    }    
}
