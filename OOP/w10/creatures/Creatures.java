/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package creatures;

/**
 *
 * @author bli
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Creatures {
    
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            // Populating creatures
            int n = Integer.parseInt(reader.readLine());
            List<Creature> creatures = new ArrayList<>();
            
            for (int i = 0; i < n; i++) {
                String line = reader.readLine();
                if (line != null) {
                    String[] tokens = line.trim().split("\\s+");
                    char ch = tokens[0].charAt(0);
                    String name = tokens[1];
                    int p = Integer.parseInt(tokens[2]);
                    
                    Creature creature = switch (ch) {
                        case 'G' -> new Greenie(name, p);
                        case 'D' -> new Beetle(name, p);
                        case 'S' -> new Splasher(name, p);
                        default -> throw new IllegalArgumentException("Unknown creature type: " + ch);
                    };
                    
                    creatures.add(creature);
                }
            }
            
            int m = Integer.parseInt(reader.readLine());
            List<ITerrain> course = new ArrayList<>();
            
            for (int j = 0; j < m; j++) {
                char c = (char) reader.read();
                ITerrain court = switch (c) {
                    case 'g' -> Grass.getInstance();
                    case 's' -> Sand.getInstance();
                    case 'm' -> Swamp.getInstance();
                    default -> throw new IllegalArgumentException("Unknown ground type: " + c);
                };
                
                course.add(court);
            }
            
            
            System.out.println("Creatures still alive at the end of the race:");
            // Competition
            for (Creature creature : creatures) {
                creature.race(course);
                if (creature.isAlive()) {
                    System.out.println(creature.getName());
                }
            }
            
        } catch (IOException e) {
            System.out.println(e);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
