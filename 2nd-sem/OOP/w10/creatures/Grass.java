/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creatures;

/**
 *
 * @author bli
 */
public class Grass implements ITerrain {
    private static Grass instance = null;
    
    private Grass() { }
    
    public static Grass getInstance() {
        if (instance == null) {
            instance = new Grass();
        }
        return instance;
    }
    
    @Override
    public ITerrain change(Beetle p) {
        p.modifyHealth(-2);
        return Sand.getInstance();
    }
    
    @Override
    public ITerrain change(Splasher p) {
        p.modifyHealth(-2);
        return Swamp.getInstance();
    }
    
    @Override
    public ITerrain change(Greenie p) {
        p.modifyHealth(1);
        return this;
    }
}
