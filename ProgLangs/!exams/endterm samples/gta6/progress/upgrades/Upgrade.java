package progress.upgrades;

import progress.upgrades.util.Purchasable;
import progress.upgrades.util.PurchaseException;
import player.Granny;

public class Upgrade implements Purchasable {
    private String name;
    private double price;
    private int requiredLevel;
    public Upgrade(String name, double price, int requiredLevel) {
        this.name = name;
        this.price = price;
        this.requiredLevel = requiredLevel;
    }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getRequiredLevel() { return requiredLevel; }
    @Override
    public boolean isAffordable(Granny granny) throws PurchaseException {
        if (granny.getLevel() < requiredLevel) throw new PurchaseException("Level not high enough!");
        if (granny.getMoney() < price) throw new PurchaseException("Not enough money!");        
        return true;
    }
}
