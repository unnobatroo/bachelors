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
 * Class of division operator
 */
public class OperatorDiv extends Operator {
    private static OperatorDiv instance;
    
    private OperatorDiv() {
        super('/');
    }
    
    @Override
    public int evaluate(int leftValue, int rightValue) {
        return leftValue / rightValue;
    }
    
    @Override
    public int priority() {
        return 2;
    }
    
    public static OperatorDiv getInstance() {
        if (instance == null) {
            instance = new OperatorDiv();
        }
        return instance;
    }
}
