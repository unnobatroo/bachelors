package cakeshop;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CakeShopTest {
    private CakeShop createShopWithSampleData() {
        CakeShop shop = new CakeShop();
        shop.registerCake(new Cake("Chocolate", "Brown", 1.5, 20.0));
        shop.registerCake(new Cake("Vanilla", "White", 1.2, 18.0));
        shop.registerCake(new Cake("Starwberry", "Pink", 1.3, 22.0));
    }

    @Test
    void sellCake_existingCake_returnsTrueAndIncrementsSold() {
        
    }
}
