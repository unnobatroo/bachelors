package spaceexploration.entity;

import java.util.List;
import java.util.ArrayList;

import spaceexploration.model.Type;
import spaceexploration.exception.InvalidResourceException;
import spaceexploration.model.Resource;

public class Astronaut {
    private String unitId;
    private Type specialityType;
    private List<Resource> resources;
    public Astronaut(String unitId, Type specialityType) {
        this.unitId = unitId;
        this.specialityType = specialityType;
        resources = new ArrayList<>();
    }
    public Astronaut() { this("U1", Type.METAL); }
    public Type getSpecialityType() { return specialityType; }
    public List<Resource> getResources() { return new ArrayList<>(resources); }
    public void tagResources(List<Resource> resourceList) {
        if (specialityType == Type.GAS) return;
        for (Resource r : resourceList) {
            if (specialityType == Type.METAL || r.isStable()) r.tag();
        }
    }
    public boolean tryToCollect(Resource resource) {
        if (resource.getType() != specialityType) return false;
        if (resource.collect() == null) {
            resource.tag();
            return false;
        }
        resources.add(resource);
        return true;
    }
    public void forceInsert(Resource resource) throws InvalidResourceException {
        if (resource.getType() != specialityType) throw new InvalidResourceException("Wrong Type");
        String s = resource.collect();
        if (s == null) {
            resource.tag();
            s = resource.collect();
        }
        if (s != null && s.endsWith("#DISCARD")) throw new InvalidResourceException("Got Debris");
        resources.add(resource);
    }
}
