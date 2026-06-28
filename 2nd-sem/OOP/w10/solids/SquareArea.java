/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
public class SquareArea implements IArea {
    private static SquareArea instance;
    
    private SquareArea() { }
    
    @Override
    public double area(double a) {
        return a * a;
    }
    
    public static SquareArea getInstance() {
        if(instance == null) instance = new SquareArea();
        return instance;
    }
}
