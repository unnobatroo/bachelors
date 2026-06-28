/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
public abstract class Pyramidal extends Prismatic {
    private static int count = 0;
    
    protected Pyramidal(double size, double height) {
        super(size, height);
        ++count;
    }
    
    @Override
    public double volume() {
        return baseArea.area(size) * height / 3.0;
    }
    
    public static int getCount() {
        return count;
    }
}
