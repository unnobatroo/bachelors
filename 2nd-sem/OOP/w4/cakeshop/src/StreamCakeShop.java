package cakeshop;

import java.util.ArrayList;
import java.util.List;

class StreamCakeShop {

    private final List<Cake> cakes;
    private int sold;

    public StreamCakeShop() {
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
        return cakes.stream()
                .filter(c -> c.color.equals(color))
                .findFirst()
                .orElse(null);
    }

    public Cake findLargestCake() {
        return cakes.stream()
                .max((c1, c2) -> Double.compare(c1.weight, c2.weight))
                .orElse(null);
    }

    public Cake findCheapestCake() {
        return cakes.stream()
                .min((c1, c2) -> Double.compare(c1.cost, c2.cost))
                .orElse(null);
    }

    public long countCake(String color) {
        return cakes.stream()
                .filter(c -> c.color.equals(color))
                .count();
    }

    public double calculateTotalWorth() {
        return cakes.stream()
                .mapToDouble(c -> c.cost)
                .sum();
    }

    public int totalSoldCakes() {
        return sold;
    }
}
