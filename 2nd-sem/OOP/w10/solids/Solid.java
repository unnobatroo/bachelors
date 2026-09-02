/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solids;

/**
 *
 * @author bli
 */
import java.util.Scanner;

public abstract class Solid {
    protected double size;
    // We need this also in the subclasses as we would like to
    // know the number of pieces separately for each kind of Solid
    private static int count = 0;
    
    protected Solid(double size) {
        this.size = size;
        ++count;
    }
    
    public abstract double volume();
    
    public static int getCount() { 
        return count; 
    }
    
    public static class UnknownShapeException extends Exception { }
    
    public static Solid create(Scanner scanner) {
        if (!scanner.hasNext()) return null;
        
        String type = scanner.next();
        double size = scanner.nextDouble();
        
        try {
            // Using modern Java switch expression with arrows
            return switch (type) {
                case "Cube" -> new Cube(size);
                case "Sphere" -> new Sphere(size);
                case "Tetrahedron" -> new Tetrahedron(size);
                case "Octahedron" -> new Octahedron(size);
                case "Cylinder" -> {
                    double height = scanner.nextDouble();
                    yield new Cylinder(size, height);
                }
                case "SquarePrism" -> {
                    double height = scanner.nextDouble();
                    yield new SquarePrism(size, height);
                }
                case "TriangularPrism" -> {
                    double height = scanner.nextDouble();
                    yield new TriangularPrism(size, height);
                }
                case "Cone" -> {
                    double height = scanner.nextDouble();
                    yield new Cone(size, height);
                }
                case "SquarePyramid" -> {
                    double height = scanner.nextDouble();
                    yield new SquarePyramid(size, height);
                }
                default -> throw new UnknownShapeException();
            };
            
        } catch (UnknownShapeException e) {
            return null;
        }
    }
}
