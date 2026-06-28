/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package store;

/**
 *
 * @author famil
 */
import java.util.Arrays;
import java.util.List;

public class Shopping {
    public static void main(String[] args) {
        Store store = new Store();

        // Add products to the departments
        store.addGroceryProduct(new Product("apple", 50));
        store.addGroceryProduct(new Product("banana", 30));
        
        store.addTechnicalProduct(new Product("laptop", 800));
        store.addTechnicalProduct(new Product("laptop", 750));  // Cheaper option
        store.addTechnicalProduct(new Product("phone", 500));

        // Customer shopping list
        List<String> shoppingList = Arrays.asList("apple", "laptop", "phone");

        Customer customer = new Customer(shoppingList);
        customer.purchase(store);

        // Display items in the cart
        for (Product product : customer.getCart()) {
            System.out.println("Bought: " + product.getName() + " Price: " + product.getPrice());
        }
    }
}
