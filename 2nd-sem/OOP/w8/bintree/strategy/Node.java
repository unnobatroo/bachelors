/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bintree.strategy;

/**
 *
 * @author bli
 */
public class Node {

    private int value;
    private Node left;
    private Node right;

    public Node(int v) {
        value = v;
        left = null;
        right = null;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public boolean isInternal() {
        return !isLeaf();
    }

    public boolean preOrder(IAction todo) {
        if (!todo.execute(this)) {
            return false;
        }
        if (left != null && !left.preOrder(todo)) {
            return false;
        }
        if (right != null && !right.preOrder(todo)) {
            return false;
        }
        return true;
    }

    public boolean inOrder(IAction todo) {
        if (left != null && !left.inOrder(todo)) {
            return false;
        }
        if (!todo.execute(this)) {
            return false;
        }
        if (right != null && !right.inOrder(todo)) {
            return false;
        }
        return true;
    }

    public boolean postOrder(IAction todo) {
        if (left != null && !left.postOrder(todo)) {
            return false;
        }
        if (right != null && !right.postOrder(todo)) {
            return false;
        }
        return todo.execute(this);
    }

    public void print() {
        System.out.print("(");
        System.out.print(this.value);
        if (left != null) {
            System.out.print(" ");
            left.print();
        }
        if (right != null) {
            System.out.print(" ");
            right.print();
        }
        System.out.print(")");
    }
}
