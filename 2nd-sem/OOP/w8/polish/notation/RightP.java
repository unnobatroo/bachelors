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
 * Class of right parentheses
 */
public class RightP extends Token {
    private static RightP instance;
    
    private RightP() {}
    
    public static RightP getInstance() {
        if (instance == null) {
            instance = new RightP();
        }
        return instance;
    }

    @Override
    public String toString() {
        return ")";
    }
    
}
