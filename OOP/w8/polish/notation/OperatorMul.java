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
 * Class of multiplication operator
 */
public class OperatorMul extends Operator {
    private static OperatorMul instance;
    
    private OperatorMul() {
        super('*');
    }
    
    @Override
    public int evaluate(int leftValue, int rightValue) {
        return leftValue * rightValue;
    }
    
    @Override
    public int priority() {
        return 2;
    }
    
    public static OperatorMul getInstance() {
        if (instance == null) {
            instance = new OperatorMul();
        }
        return instance;
    }
}
