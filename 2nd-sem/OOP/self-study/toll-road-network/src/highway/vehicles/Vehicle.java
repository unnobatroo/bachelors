package highway.vehicles;

public abstract class Vehicle {
    private final String licensePlate;

    protected Vehicle(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public abstract int getMultiplier();

    public float getOffPeakMultiplier() {
        return 1.0f;
    }

    public float getWeekendMultiplier() {
        return 1.0f;
    }

    public boolean isHeavyWeight() {
        return false;
    }
}
