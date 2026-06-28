/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
public abstract class Regular extends Solid {
    private static int count = 0;
    
    protected Regular(double size) {
        super(size);
        ++count;
    }
    
    @Override
    public double volume() {
        return size * size * size * multiplier();
    }
    
    protected abstract double multiplier();
    
    public static int getCount() {
        return count;
    }
}
