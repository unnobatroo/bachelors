/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creatures;

/**
 *
 * @author bli
 */
import java.util.List;

public abstract class Creature {
    private final String name;
    protected int health;
    
    public String getName() {
        return name;
    }
    
    public void modifyHealth(int e) {
        health += e;
    }
    
    public boolean isAlive() {
        return health > 0;
    }
    
    protected Creature(String name, int health) {
        this.name = name;
        this.health = health;
    }
    
    public void race(List<ITerrain> course) {
        for (int j = 0; isAlive() && j < course.size(); j++) {
            course.set(j, traverse(course.get(j)));
        }
    }
    
    protected abstract ITerrain traverse(ITerrain ground);
}
