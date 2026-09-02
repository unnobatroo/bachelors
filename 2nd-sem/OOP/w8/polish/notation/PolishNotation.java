/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package polish.notation;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 * Main program for transforming an arithmetic expression from infix form to
 * postfix form (Reverse Polish Notation), and then evaluating it.
 * 
 * @author bli
 */
public class PolishNotation {
    
    /**
     * Main entry point of the program.
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            char choice;
            
            do {
                System.out.println("\nGive me an arithmetic expression!\n");
                String input = scanner.nextLine().trim();
                
                try {
                    Expression exp = new Expression(input);
                    int result = exp.evaluate();
                    
                    System.out.println("value: " + result);
                    
                } catch (ExpressionProcessingException e) {
                    System.out.println(e.getMessage());
                }
                
                System.out.print("\nDo you continue? Y/N ");
                choice = scanner.next().toLowerCase().charAt(0);
                scanner.nextLine(); // consume newline
                
            } while (choice != 'n');
        }
    }
    
}
