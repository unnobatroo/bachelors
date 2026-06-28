/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bintree.strategy;

/**
 *
 * @author bli
 */
public class LinearSearch implements IAction {
    private int value;
    private boolean found;
    
    public LinearSearch() {
        found = false;
    }
    
    public int getValue() {
        return value;
    }
    
    public boolean isFound() {
        return found;
    }
    
    @Override
    public boolean execute(Node node) {
        if (node.getValue() % 2 == 0) {
            value = node.getValue();
            found = true;
            return false; // stop traversal
        }
        return true; // continue traversal
    }
}
