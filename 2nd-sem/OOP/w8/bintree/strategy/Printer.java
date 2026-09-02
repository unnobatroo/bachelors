/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bintree.strategy;

/**
 *
 * @author bli
 */
public class Printer implements IAction {
    @Override
    public boolean execute(Node node) {
        System.out.print(" " + node.getValue() + " ");
        return true; // always continue traversal
    }
}
