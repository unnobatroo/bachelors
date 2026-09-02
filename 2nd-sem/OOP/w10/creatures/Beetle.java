/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creatures;

/**
 *
 * @author bli
 */
public class Beetle extends Creature {
    public Beetle(String name, int health) {
        super(name, health);
    }
    
    @Override
    protected ITerrain traverse(ITerrain t) {
        return t.change(this);
    }
}
