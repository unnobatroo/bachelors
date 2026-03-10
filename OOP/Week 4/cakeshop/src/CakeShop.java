package cakeshop;

import java.util.ArrayList;
import java.util.List;

class CakeShop {

    private final List<Cake> cakes;
    private int sold;

    public CakeShop() {
        this.cakes = new ArrayList<>();
        this.sold = 0;
    }

    public void registerCake(Cake c) {
        cakes.add(c);
    }

    public boolean sellCake(Cake c) {
        if (cakes.remove(c)) {
            sold++;
            return true;
        }
        return false;
    }

    public Cake findFirstCakeWithColor(String color) {
        for (Cake cake : cakes) {
            if (cake.color.equals(color)) {
                return cake;
            }
        }
        return null;
    }

    public Cake findLargestCake() {
        if (cakes.isEmpty()) {
            return null;
        }

        Cake largest = cakes.get(0);
        for (Cake cake : cakes) {
            if (cake.weight > largest.weight) {
                largest = cake;
            }
        }
        return largest;
    }

    public Cake findCheapestCake() {
        if (cakes.isEmpty()) {
            return null;
        }

        Cake cheapest = cakes.get(0);
        for (Cake cake : cakes) {
            if (cake.cost < cheapest.cost) {
                cheapest = cake;
            }
        }
        return cheapest;
    }

    public int countCake(String color) {
        int count = 0;
        for (Cake cake : cakes) {
            if (cake.color.equals(color)) {
                count++;
            }
        }
        return count;
    }

    public double calculateTotalWorth() {
        double total = 0.0;
        for (Cake cake : cakes) {
            total += cake.cost;
        }
        return total;
    }

    public int totalSoldCakes() {
        return sold;
    }
}
