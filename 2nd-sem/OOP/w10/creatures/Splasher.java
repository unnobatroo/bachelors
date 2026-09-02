/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creatures;

/**
 *
 * @author bli
 */
public class Splasher extends Creature {
    public Splasher(String name, int health) {
        super(name, health);
    }
    
    @Override
    protected ITerrain traverse(ITerrain t) {
        return t.change(this);
    }
}
