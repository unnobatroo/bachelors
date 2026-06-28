package highway.vehicles;

public class HeavyTruck extends Vehicle {
    public HeavyTruck(String licensePlate) {
        super(licensePlate);
    }

    @Override
    public int getMultiplier() {
        return 4;
    }

    @Override
    public float getOffPeakMultiplier() {
        return 0.9f;
    }

    @Override
    public float getWeekendMultiplier() {
        return 2.0f;
    }

    @Override
    public boolean isHeavyWeight() {
        return true;
    }
}
