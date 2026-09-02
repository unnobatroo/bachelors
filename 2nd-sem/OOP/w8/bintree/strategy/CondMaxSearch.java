/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bintree.strategy;

/**
 *
 * @author bli
 */
public class CondMaxSearch implements IAction {
    private boolean found;
    private int max;
    
    public CondMaxSearch() {
        found = false;
    }
    
    public boolean isFound() {
        return found;
    }
    
    public int getMax() {
        return max;
    }
    
    @Override
    public boolean execute(Node node) {
        if (node.isInternal()) {
            if (!found) {
                found = true;
                max = node.getValue();
            } else {
                max = Math.max(max, node.getValue());
            }
        }
        return true; // always continue traversal
    }
}
