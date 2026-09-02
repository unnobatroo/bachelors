package weapons;

/**
 * Weapon lifecycle state based on durability.
 */
public enum Condition {
    NEW(1.0f, 100), USED(1.0f, 50), DAMAGED(0.75f, 1), BROKEN(0.0f, 0);

    private final float powerMultiplier;
    private final int minDurability;

    Condition(float powerMultiplier, int minDurability) {
        this.powerMultiplier = powerMultiplier;
        this.minDurability = minDurability;
    }

    /**
     * Maps a durability value to its state.
     */
    public static Condition fromDurability(int durability) {
        if (durability > 0 && durability <= 100) {
            for (Condition c : values()) {
                if (durability >= c.minDurability)
                    return c;
            }
        }
        return BROKEN;
    }

    public float getPowerMultiplier() {
        return powerMultiplier;
    }

    public int getMinDurability() {
        return minDurability;
    }
}
