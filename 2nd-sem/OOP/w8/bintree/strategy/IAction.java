/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package bintree.strategy;

/**
 *
 * @author bli
 */
public interface IAction {
    boolean execute(Node node); // return true to continue traversal, false to stop
}
