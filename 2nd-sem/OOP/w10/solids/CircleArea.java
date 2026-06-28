/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
public class CircleArea implements IArea {
    private static CircleArea instance;
    
    private CircleArea() { }
    
    @Override
    public double area(double r) {
        return Math.PI * r * r;
    }
    
    public static CircleArea getInstance() {
        if(instance == null) instance = new CircleArea();
        return instance;
    }
}
