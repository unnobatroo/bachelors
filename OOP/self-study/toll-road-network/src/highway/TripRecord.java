package highway;

import highway.pricing.PricingStrategy;
import highway.vehicles.Vehicle;
import java.time.LocalDateTime;

public class TripRecord {
    private final float distKm;
    private final LocalDateTime dateTime;
    private final Vehicle vehicle;

    public TripRecord(float distKm, Vehicle vehicle) {
        this(distKm, vehicle, LocalDateTime.now());
    }

    public TripRecord(float distKm, Vehicle vehicle, LocalDateTime dateTime) {
        this.distKm = distKm;
        this.vehicle = vehicle;
        this.dateTime = dateTime;
    }

    public float getDistKm() {
        return distKm;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public float getTollFee(PricingStrategy strategy) {
        return strategy.calculateFee(this);
    }
}
