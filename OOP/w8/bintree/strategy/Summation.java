/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bintree.strategy;

/**
 *
 * @author bli
 */
public class Summation implements IAction {
    private int sum;
    
    public Summation() {
        sum = 0;
    }
    
    public int getSum() {
        return sum;
    }
    
    @Override
    public boolean execute(Node node) {
        if (node.isInternal()) sum += node.getValue();
        return true; // always continue traversal
    }
}
