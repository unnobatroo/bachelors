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
 * Abstract superclass of operators
 */
public abstract class Operator extends Token {
    private final char op;
    
    protected Operator(char o) {
        this.op = o;
    }
    
    public abstract int evaluate(int leftValue, int rightValue);
    
    public int priority() {
        return 3;
    }

    @Override
    public String toString() {
        return String.valueOf(op);
    }    

}
