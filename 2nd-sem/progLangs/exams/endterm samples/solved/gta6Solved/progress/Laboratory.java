package progress;

import java.util.Map;
import java.util.HashMap;

import player.Granny;
import progress.upgrades.Upgrade;
import progress.upgrades.util.PurchaseException;

public class Laboratory {
    private Map<Upgrade, Boolean> upgrades;
    public Laboratory(Upgrade... upgrades) {
        this.upgrades = new HashMap<>();
        for (Upgrade u : upgrades) this.upgrades.put(u, false);
    }
    public void purchaseUpgrade(Granny granny, Upgrade upgrade) throws PurchaseException {
        if (!upgrades.containsKey(upgrade) | upgrades.get(upgrade))
            throw new PurchaseException("Upgrade not available!");
        if (upgrade.isAffordable(granny)) {
            granny.setMoney(granny.getMoney() - upgrade.getPrice());
            upgrades.put(upgrade, true);
        }
    }
    public boolean hasPurchasableUpgrade(Granny granny) {
        boolean anyPurchasable = false;
        for (Upgrade u : upgrades.keySet()) {
            if (upgrades.get(u)) continue;
            try {
                anyPurchasable = u.isAffordable(granny);
            } catch (PurchaseException e){
                System.out.println(e.getMessage());
            }
        }
        return anyPurchasable;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Upgrade u : upgrades.keySet()) {
            if (!sb.isEmpty()) sb.append(System.lineSeparator());
            sb.append("Upgrade ").append(u.getName()).append(" with price: ")
                .append(u.getPrice()).append(" - level ").append(u.getRequiredLevel())
                .append(" needed (").append(upgrades.get(u) ? "PURCHASED" : "AVAILABLE").append(")");
        }
        return sb.toString();
    }
}
