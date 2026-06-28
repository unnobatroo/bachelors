package highway.pricing;

import java.time.DayOfWeek;

import highway.TripRecord;
import highway.vehicles.Vehicle;

public final class GreenWeekendStrategy implements PricingStrategy {
    private static final GreenWeekendStrategy INSTANCE = new GreenWeekendStrategy();

    private GreenWeekendStrategy() {
    }

    public static GreenWeekendStrategy getInstance() {
        return INSTANCE;
    }

    @Override
    public float calculateFee(TripRecord tripRecord) {
        Vehicle vehicle = tripRecord.getVehicle();
        float baseFee = tripRecord.getDistKm() * vehicle.getMultiplier();
        DayOfWeek dayOfWeek = tripRecord.getDateTime().getDayOfWeek();

        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            return baseFee * vehicle.getWeekendMultiplier();
        } else {
            return baseFee;
        }
    }
}
