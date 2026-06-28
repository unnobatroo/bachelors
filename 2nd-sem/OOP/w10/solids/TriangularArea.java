/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
public class TriangularArea implements IArea {
    private static TriangularArea instance;
    
    private TriangularArea() { }
    
    @Override
    public double area(double a) {
        return Math.sqrt(3.0) * a * a / 4.0;
    }
    
    public static TriangularArea getInstance() {
        if(instance == null) instance = new TriangularArea();
        return instance;
    }
}
