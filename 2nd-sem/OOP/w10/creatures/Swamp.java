/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creatures;

/**
 *
 * @author bli
 */
public class Swamp implements ITerrain {
    private static Swamp instance = null;
    
    private Swamp() { }
    
    public static Swamp getInstance() {
        if (instance == null) {
            instance = new Swamp();
        }
        return instance;
    }
    
    @Override
    public ITerrain change(Greenie p) {
        p.modifyHealth(-1);
        return Grass.getInstance();
    }
    
    @Override
    public ITerrain change(Beetle p) {
        p.modifyHealth(-4);
        return Grass.getInstance();
    }
    
    @Override
    public ITerrain change(Splasher p) {
        p.modifyHealth(6);
        return this;
    }
}
