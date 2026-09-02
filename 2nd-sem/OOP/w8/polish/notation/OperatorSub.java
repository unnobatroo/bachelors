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
 * Class of subtraction operator
 */
public class OperatorSub extends Operator {
    private static OperatorSub instance;
    
    private OperatorSub() {
        super('-');
    }
    
    @Override
    public int evaluate(int leftValue, int rightValue) {
        return leftValue - rightValue;
    }
    
    @Override
    public int priority() {
        return 1;
    }
    
    public static OperatorSub getInstance() {
        if (instance == null) {
            instance = new OperatorSub();
        }
        return instance;
    }
}
