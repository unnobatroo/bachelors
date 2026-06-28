/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package creatures;

/**
 *
 * @author bli
 */
public class Sand implements ITerrain {
    private static Sand instance = null;
    
    private Sand() { }
    
    public static Sand getInstance() {
        if (instance == null) {
            instance = new Sand();
        }
        return instance;
    }
    
    @Override
    public ITerrain change(Greenie p) {
        p.modifyHealth(-2);
        return this;
    }
    
    @Override
    public ITerrain change(Beetle p) {
        p.modifyHealth(3);
        return this;
    }
    
    @Override
    public ITerrain change(Splasher p) {
        p.modifyHealth(-5);
        return this;
    }
}
