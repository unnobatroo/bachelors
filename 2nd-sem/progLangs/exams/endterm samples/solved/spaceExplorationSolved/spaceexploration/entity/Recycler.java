package spaceexploration.entity;

import java.util.List;
import java.util.ArrayList;

import spaceexploration.model.Ore;
import spaceexploration.model.Debris;
import spaceexploration.model.Resource;
import spaceexploration.model.Type;

public class Recycler {
    public List<Resource> releaseResources(int n) {
        List<Resource> resources = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            Type t = Type.values()[i % 3];
            if (i % 2 == 0) resources.add(new Ore((200+i) + "-" + t.toString().substring(0, 2), t));
            else resources.add(new Debris((200+i) + "-" + t.toString().substring(0, 2), t));
        }
        return resources;
    }
}
