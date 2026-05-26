package highway.pricing;

import highway.TripRecord;

public interface PricingStrategy {
    public float calculateFee(TripRecord tripRecord);
}
