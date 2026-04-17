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
 * Class of addition operator
 */
public class OperatorAdd extends Operator {
    private static OperatorAdd instance;
    
    private OperatorAdd() {
        super('+');
    }
    
    @Override
    public int evaluate(int leftValue, int rightValue) {
        return leftValue + rightValue;
    }
    
    @Override
    public int priority() {
        return 1;
    }
    
    public static OperatorAdd getInstance() {
        if (instance == null) {
            instance = new OperatorAdd();
        }
        return instance;
    }
}
