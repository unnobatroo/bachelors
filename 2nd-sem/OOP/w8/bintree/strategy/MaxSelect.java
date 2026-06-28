/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bintree.strategy;

/**
 *
 * @author bli
 */
public class MaxSelect implements IAction {
    private int max;
    
    public MaxSelect(int i) {
        max = i;
    }
    
    public int getMax() {
        return max;
    }
    
    @Override
    public boolean execute(Node node) {
        max = Math.max(max, node.getValue());
        return true; // always continue traversal
    }
}
