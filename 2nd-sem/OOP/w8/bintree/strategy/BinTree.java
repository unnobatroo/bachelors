/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bintree.strategy;

/**
 *
 * @author bli
 */
import java.util.Random;
import java.util.Date;

public class BinTree {
    public static class NoRootException extends Exception {
        public NoRootException() {
            super();
        }
    }
    
    private Node root;
    private final Random rand;
    
    public int getRoot() throws NoRootException {
        if (root == null) throw new NoRootException();
        return root.getValue();
    }
    
    public boolean isEmpty() {
        return root == null;
    }
    
    public BinTree() {
        root = null;
        rand = new Random(new Date().getTime());
    }
    
    public void randomInsert(int e) {
        if (root == null) {
            root = new Node(e);
        } else {
            Node r = root;
            boolean l = rand.nextInt(2) != 0;
            while (l ? r.getLeft() != null : r.getRight() != null) {
                if (l) r = r.getLeft();
                else r = r.getRight();
                l = rand.nextInt(2) != 0;
            }
            
            if (l) r.setLeft(new Node(e));
            else r.setRight(new Node(e));
        }
    }
    
    public boolean preOrder(IAction todo) {
        return root != null && root.preOrder(todo);
    }
    
    public boolean inOrder(IAction todo) {
        return root != null && root.inOrder(todo);
    }
    
    public boolean postOrder(IAction todo) {
        return root != null && root.postOrder(todo);
    }
    
    public void print() {
        if (root != null) root.print();
    }
}
