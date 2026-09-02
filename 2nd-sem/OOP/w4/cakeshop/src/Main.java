package cakeshop;

import cakeshop.Cake;

public class Main {
    public static void main(String[] args) {
        // Create a CakeShop instance
        CakeShop shop = new CakeShop();

        // Register cakes
        shop.registerCake(new Cake("Chocolate Cake", "Brown", 1.5, 20.0));
        shop.registerCake(new Cake("Vanilla Cake", "White", 1.2, 18.0));
        shop.registerCake(new Cake("Strawberry Cake", "Pink", 1.3, 22.0));
        shop.registerCake(new Cake("Red Velvet Cake", "Red", 1.7, 25.0));
        shop.registerCake(new Cake("Lemon Cake", "Yellow", 1.1, 15.0));

        // Display initial statistics
        System.out.println("Total cakes available: " + shop.calculateTotalWorth());
        System.out.println("Total cakes sold: " + shop.totalSoldCakes());

        // Find the first cake with a specific color
        Cake pinkCake = shop.findFirstCakeWithColor("Pink");
        if (pinkCake != null) {
            System.out.println("First pink cake found: " + pinkCake.name);
        } else {
            System.out.println("No pink cakes available.");
        }

        // Find the largest cake
        Cake largestCake = shop.findLargestCake();
        if (largestCake != null) {
            System.out.println("Largest cake: " + largestCake.name + ", Weight: " + largestCake.weight);
        }

        // Find the cheapest cake
        Cake cheapestCake = shop.findCheapestCake();
        if (cheapestCake != null) {
            System.out.println("Cheapest cake: " + cheapestCake.name + ", Price: " + cheapestCake.cost);
        }

        // Sell a cake
        if (shop.sellCake(pinkCake)) {
            System.out.println("Sold: " + pinkCake.name);
        } else {
            System.out.println("Cake not available for sale.");
        }

        // Display statistics after selling
        System.out.println("Updated total cakes available: " + shop.calculateTotalWorth());
        System.out.println("Total cakes sold: " + shop.totalSoldCakes());
    }
}
