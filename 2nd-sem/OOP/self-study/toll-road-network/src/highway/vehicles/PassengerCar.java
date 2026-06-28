package highway.vehicles;

public class PassengerCar extends Vehicle {
    public PassengerCar(String licensePlate) {
        super(licensePlate);
    }

    @Override
    public int getMultiplier() {
        return 2;
    }

    @Override
    public float getWeekendMultiplier() {
        return 0.5f;
    }
}
