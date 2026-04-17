/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package polish.notation;

/**
 *
 * @author bli
 */

/**
 * Class of left parentheses
 */
public class LeftP extends Token {
    private static LeftP instance;
    
    private LeftP() {}
    
    public static LeftP getInstance() {
        if (instance == null) {
            instance = new LeftP();
        }
        return instance;
    }

    @Override
    public String toString() {
        return "(";
    }
        
}
