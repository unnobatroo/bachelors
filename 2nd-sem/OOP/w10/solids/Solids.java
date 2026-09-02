/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package solids;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author bli
 */

public class Solids {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(new File("solids.txt"))) {
            List<Solid> solids = new ArrayList<>();
            
            Solid solid;            
            while ((solid = Solid.create(scanner)) != null) {
                solids.add(solid);
            }
            
            statistics();
            System.out.println("Volumes:");
            for (Solid s : solids) {
                System.out.printf("%s : %.2f%n", 
                    s.getClass().getSimpleName(), 
                    s.volume());
            }
            
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + e.getMessage());
        }
    }
    
    private static void statistics() {
        System.out.printf("\nall shapes: %d\n", Solid.getCount());
        System.out.printf(" regulars: %d :\tspheres: %d, cubes: %d, tetrahedrons: %d, octahedron: %d\n",
            Regular.getCount(), Sphere.getCount(), Cube.getCount(), Tetrahedron.getCount(), Octahedron.getCount());
        System.out.printf(" prismatics: %d:\tcylinders: %d, squarePrisms: %d, triangularPrisms: %d\n",
            Prismatic.getCount(), Cylinder.getCount(), SquarePrism.getCount(), TriangularPrism.getCount());
        System.out.printf(" pyramidals: %d:\tcones: %d, squarePyramids: %d\n",
            Pyramidal.getCount(), Cone.getCount(), SquarePyramid.getCount());
    }
}
