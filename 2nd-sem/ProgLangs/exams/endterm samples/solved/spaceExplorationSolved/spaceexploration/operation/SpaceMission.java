package spaceexploration.operation;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.Set;

import spaceexploration.entity.Astronaut;
import spaceexploration.entity.Recycler;
import spaceexploration.exception.InvalidResourceException;
import spaceexploration.model.Resource;
import spaceexploration.model.Type;

public class SpaceMission {
    private List<Astronaut> astronauts;
    private List<Resource> missionResources;
    public SpaceMission(int numAstronauts) {
        if (numAstronauts % 3 != 0) throw new IllegalArgumentException();
        astronauts = new ArrayList<>();
        for (int i = 0; i < numAstronauts; i++) {
            astronauts.add(new Astronaut("U" + (i+1), Type.values()[i % 3]));
        }
        missionResources = new ArrayList<>();
    }
    public void registerResources(List<Resource> resources) {
        for (Resource r : resources) {
            if (r.getType() == Type.METAL) {
                registerResource(r);
            }
        }
    }
    public void registerResource(Resource resource) {
        if (missionResources.contains(resource)) return;
        missionResources.add(resource);
    }
    public void prepareMission(int n) throws InvalidResourceException {
        List<Resource> resources = new Recycler().releaseResources(n);
        if (resources.size() == 0 || resources.get(0).getType() != Type.METAL && resources.get(n/2).getType() != Type.METAL && resources.get(n-1).getType() != Type.METAL)
            throw new InvalidResourceException("No METAL resources found");
        registerResources(resources);
    }
    public Set<Resource> conductMission() {
        Set<Resource> collected = new HashSet<>(); 
        for (Astronaut a : astronauts) {
            a.tagResources(missionResources);
            for (Resource r : new ArrayList<>(missionResources)) {
                if (a.tryToCollect(r)) {
                    collected.add(r);
                    missionResources.remove(r);
                } else if (a.getSpecialityType() == Type.GAS) {
                    try {
                        a.forceInsert(r);
                        collected.add(r);
                    } catch (InvalidResourceException e) {}
                    missionResources.remove(r);
                }
            }
        }
        return collected;
    }
}
