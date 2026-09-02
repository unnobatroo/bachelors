/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
public class SquarePyramid extends Pyramidal {
    private static int count = 0;
    
    public SquarePyramid(double size, double height) {
        super(size, height);
        baseArea = SquareArea.getInstance();
        ++count;
    }
    
    public static int getCount() {
        return count;
    }
}
