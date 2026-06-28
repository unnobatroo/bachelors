/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
public abstract class Prismatic extends Solid {
    protected IArea baseArea;
    protected double height;
    private static int count = 0;
    
    protected Prismatic(double size, double height) {
        super(size);
        this.height = height;
        ++count;
    }
    
    @Override
    public double volume() {
        return baseArea.area(size) * height;
    }
    
    public static int getCount() {
        return count;
    }
}
