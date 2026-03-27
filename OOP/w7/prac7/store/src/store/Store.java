/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package store;
/**
 *
 * @author famil
 */
public class Store {
    private Department grocery = new Department();
    private Department technical = new Department();

    public void addGroceryProduct(Product p) {
        grocery.addProduct(p);
    }
    
    public void addTechnicalProduct(Product p) {
        technical.addProduct(p);
    }
    
    public Department getGrocery() {
        return grocery;
    }

    public Department getTechnical() {
        return technical;
    }
}
