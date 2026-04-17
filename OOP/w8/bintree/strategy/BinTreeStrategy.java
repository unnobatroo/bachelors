/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bintree.strategy;

import java.util.Scanner;

/**
 *
 * @author bli
 */

public class BinTreeStrategy {
    public static void main(String[] args) {
        BinTree t = new BinTree();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Give the elements of the tree, one on each line, end with 0: ");
        int i;
        while ((i = Integer.parseInt(scanner.nextLine())) != 0) {
            t.randomInsert(i);
        }
        
        t.print();
        System.out.println();
        
        Printer print = new Printer();
        System.out.print("\nPreorder traversal: ");
        t.preOrder(print);
        
        System.out.print("\nInorder traversal: ");
        t.inOrder(print);
        
        System.out.print("\nPostorder traversal:");
        t.postOrder(print);
        
        System.out.println();
        
        Summation sum = new Summation();
        t.preOrder(sum);
        System.out.println("\nSum of internal nodes: " + sum.getSum());
        
        try {
            MaxSelect max1 = new MaxSelect(t.getRoot());
            t.preOrder(max1);
            System.out.println("\nMaximum of elements: " + max1.getMax());
        } catch (BinTree.NoRootException e) {
            System.out.println("Empty tree.");
        }
        
        LinearSearch search = new LinearSearch();
        t.preOrder(search);
        if (search.isFound()) {
            System.out.println("\nFirst even number: " + search.getValue());
        } else {
            System.out.println("\nNo even numbers");
        }
        
        CondMaxSearch max2 = new CondMaxSearch();
        t.preOrder(max2);
        System.out.print("\nMaximum of internal elements: ");
        if (max2.isFound()) System.out.println(max2.getMax());
        else System.out.println("none");
        
        scanner.close();
    }
}
