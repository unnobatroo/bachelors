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
 * Represents an operand (numeric value) in an arithmetic expression.
 */
public class Operand extends Token {

    private final int val;

    public Operand(int v) {
        this.val = v;
    }

    /**
     * Gets the numeric value of this operand.
     */
    public int value() {
        return val;
    }

    @Override
    public String toString() {
        return String.valueOf(val);
    }

}
