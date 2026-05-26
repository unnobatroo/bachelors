package highway;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import highway.pricing.PricingStrategy;
import highway.vehicles.Vehicle;

public final class HighwaySystem {
    private static String name;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        HighwaySystem.name = name;
    }

    private static final List<TollSegment> tollSegments = new ArrayList<>();
    private static final HashMap<Vehicle, LocalDateTime> regVehicles = new HashMap<>();

    public static void addVehicle(Vehicle vehicle){
        HighwaySystem.regVehicles.put(vehicle, LocalDateTime.now());
    }

    public static void addTollSegment(TollSegment segment) {
        HighwaySystem.tollSegments.add(segment);
    }

    private static PricingStrategy pricingStrategy;

    public static PricingStrategy getPricingStrategy() {
        return pricingStrategy;
    }

    public static void setPricingStrategy(PricingStrategy pricingStrategy) {
        HighwaySystem.pricingStrategy = pricingStrategy;
    }

    public HighwaySystem(String name) {
        HighwaySystem.name = name;
    }

    public HighwaySystem(String name, PricingStrategy pricingStrategy) {
        HighwaySystem.name = name;
        HighwaySystem.pricingStrategy = pricingStrategy;
    }

    public static TollSegment getMostProfitableSegment() {
        return tollSegments.stream()
                .max(Comparator.comparingDouble(seg -> seg.getTotalRevenue(pricingStrategy)))
                .orElse(null);
    }
}
