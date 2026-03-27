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

public class Department {
    private List<Product> stock = new ArrayList<>();

    public void addProduct(Product product) {
        stock.add(product);
    }

    public List<Product> getStock() {
        return stock;
    }

    public void removeProduct(Product product) {
        stock.remove(product);
    }
}