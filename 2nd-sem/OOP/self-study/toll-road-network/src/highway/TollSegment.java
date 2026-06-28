package highway;

import java.util.ArrayList;
import java.util.List;

import highway.pricing.PricingStrategy;

public class TollSegment {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private final List<TripRecord> tripRecords;

    public TollSegment(String name) {
        this.name = name;
        this.tripRecords = new ArrayList<>();
    }

    public TollSegment(String name, List<TripRecord> tripRecords) {
        this.name = name;
        this.tripRecords = tripRecords;
    }

    public void addTripRecord(TripRecord record) {
        this.tripRecords.add(record);
    }

    public float getTotalRevenue(PricingStrategy strategy) {
        float total = 0;
        for (TripRecord rec : tripRecords) {
            total += rec.getTollFee(strategy);
        }
        return total;
    }

    public boolean hadHeavyTruckTripDuringPeak() {
        for (TripRecord rec : tripRecords) {
            int hour = rec.getDateTime().getHour();
            boolean isPeak = (hour >= 7 && hour < 9) || (hour >= 16 && hour < 18);
            if (isPeak && rec.getVehicle().isHeavyWeight()) {
                return true;
            }
        }
        return false;
    }

    public boolean isStrictlyEcoFriendly() {
        return !hadHeavyTruckTripDuringPeak();
    }
}
