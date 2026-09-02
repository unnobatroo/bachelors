/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package store;

/**
 *
 * @author famil
 */
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final List<String> shoppingList;
    private final List<Product> cart;

    public Customer(List<String> shoppingList) {
        this.cart = new ArrayList<>();
        this.shoppingList = shoppingList;
    }

    public void purchase(Store store) {
        for (String item : shoppingList) {
            Product product = search(item, store.getGrocery());
            if (product != null) {
                buy(product, store.getGrocery());
            }
        }

        for (String item : shoppingList) {
            Product product = searchCheap(item, store.getTechnical());
            if (product != null) {
                buy(product, store.getTechnical());
            }
        }
    }

    private Product search(String name, Department department) {
        for (Product p : department.getStock()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    private Product searchCheap(String name, Department department) {
        Product cheapest = null;
        for (Product p : department.getStock()) {
            if (p.getName().equals(name)) {
                if (cheapest == null || p.getPrice() < cheapest.getPrice()) {
                    cheapest = p;
                }
            }
        }
        return cheapest;
    }

    private void buy(Product product, Department department) {
        department.removeProduct(product);
        cart.add(product);
    }

    public List<Product> getCart() {
        return cart;
    }
}