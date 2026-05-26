package highway.pricing;

import highway.TripRecord;

public final class EcoPeakStrategy implements PricingStrategy {
    private static final EcoPeakStrategy INSTANCE = new EcoPeakStrategy();

    public static EcoPeakStrategy getInstance() {
        return INSTANCE;
    }

    private EcoPeakStrategy() {

    }

    @Override
    public float calculateFee(TripRecord tripRecord) {
        float baseFee = tripRecord.getDistKm() * tripRecord.getVehicle().getMultiplier();
        int hour = tripRecord.getDateTime().getHour();

        if ((hour >= 7 && hour < 9) || (hour >= 16 && hour < 18)) {
            return baseFee * 1.5f;
        } else {
            return baseFee - baseFee * 0.1f;
        }
    }
}
