package highway.vehicles;

public class Motorcycle extends Vehicle {
    public Motorcycle(String licensePlate) {
        super(licensePlate);
    }

    @Override
    public int getMultiplier() {
        return 1;
    }

    @Override
    public float getWeekendMultiplier() {
        return 0.5f;
    }
}
