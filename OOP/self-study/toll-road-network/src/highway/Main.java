package highway;

import java.time.LocalDateTime;

import highway.pricing.EcoPeakStrategy;
import highway.pricing.GreenWeekendStrategy;
import highway.pricing.StandardStrategy;
import highway.vehicles.HeavyTruck;
import highway.vehicles.Motorcycle;
import highway.vehicles.PassengerCar;
import highway.vehicles.Vehicle;

public class Main {
    public static void main(String[] args) {
        HighwaySystem.setName("Khiva Express");

        // register global pricing strategy
        HighwaySystem.setPricingStrategy(StandardStrategy.getInstance());

        // create segments
        TollSegment s1 = new TollSegment("A1");
        TollSegment s2 = new TollSegment("B2");
        HighwaySystem.addTollSegment(s1);
        HighwaySystem.addTollSegment(s2);

        // create vehicles
        Vehicle matiz = new PassengerCar("01A777AA");
        Vehicle zil = new HeavyTruck("90348ABB");
        Vehicle kawasaki = new Motorcycle("50J620JA");

        // add trips
        s1.addTripRecord(new TripRecord(10f, matiz, LocalDateTime.of(2026, 5, 25, 10, 0))); // off-peak weekday
        s1.addTripRecord(new TripRecord(5f, zil, LocalDateTime.of(2026, 5, 25, 8, 0))); // peak weekday
        s2.addTripRecord(new TripRecord(20f, kawasaki, LocalDateTime.of(2026, 5, 24, 12, 0))); // weekend

        System.out.println("=== Pricing: Standard ===");
        System.out.printf("Segment %s revenue: %.2f\n", s1.getName(),
                s1.getTotalRevenue(HighwaySystem.getPricingStrategy()));
        System.out.printf("Segment %s revenue: %.2f\n", s2.getName(),
                s2.getTotalRevenue(HighwaySystem.getPricingStrategy()));
        TollSegment most = HighwaySystem.getMostProfitableSegment();
        System.out.println("Most profitable (standard): " + (most != null ? most.getName() : "none"));

        System.out.println("\n=== Pricing: EcoPeak ===");
        HighwaySystem.setPricingStrategy(EcoPeakStrategy.getInstance());
        System.out.printf("Segment %s revenue: %.2f\n", s1.getName(),
                s1.getTotalRevenue(HighwaySystem.getPricingStrategy()));

        System.out.println("\n=== Pricing: GreenWeekend ===");
        HighwaySystem.setPricingStrategy(GreenWeekendStrategy.getInstance());
        System.out.printf("Segment %s revenue: %.2f\n", s2.getName(),
                s2.getTotalRevenue(HighwaySystem.getPricingStrategy()));

        System.out.println("\nEco-friendly checks:");
        System.out.println(s1.getName() + " strictly eco-friendly? " + s1.isStrictlyEcoFriendly());
        System.out.println(s2.getName() + " strictly eco-friendly? " + s2.isStrictlyEcoFriendly());
    }
}
