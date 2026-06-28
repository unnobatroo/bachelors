package highway.pricing;

import highway.TripRecord;

public final class StandardStrategy implements PricingStrategy {
    private static final StandardStrategy INSTANCE = new StandardStrategy();

    public static StandardStrategy getInstance() {
        return INSTANCE;
    }

    private StandardStrategy() {

    }

    @Override
    public float calculateFee(TripRecord tripRecord) {
        return tripRecord.getDistKm() * tripRecord.getVehicle().getMultiplier();
    }
}
