/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
public class Cube extends Regular {
    private static int count = 0;
    
    public Cube(double size) {
        super(size);
        ++count;
    }
    
    @Override
    protected double multiplier() {
        return 1.0;
    }
    
    public static int getCount() {
        return count;
    }
}
