package highway;

import java.time.LocalDateTime;

import highway.pricing.EcoPeakStrategy;
import highway.pricing.GreenWeekendStrategy;
import highway.pricing.StandardStrategy;
import highway.vehicles.HeavyTruck;
import highway.vehicles.Motorcycle;
import highway.vehicles.PassengerCar;
import highway.vehicles.Vehicle;

public class TestRunner {
    private static int failed = 0;

    private static void assertEquals(String name, double expected, double actual) {
        if (Math.abs(expected - actual) < 0.001) {
            System.out.println("PASS: " + name);
        } else {
            System.out.printf("FAIL: %s expected=%.3f actual=%.3f\n", name, expected, actual);
            failed++;
        }
    }

    private static void assertTrue(String name, boolean cond) {
        if (cond) {
            System.out.println("PASS: " + name);
        } else {
            System.out.println("FAIL: " + name);
            failed++;
        }
    }

    public static void main(String[] args) {
        HighwaySystem.setPricingStrategy(StandardStrategy.getInstance());

        TollSegment s1 = new TollSegment("A1-test");
        TollSegment s2 = new TollSegment("B2-test");
        HighwaySystem.addTollSegment(s1);
        HighwaySystem.addTollSegment(s2);

        Vehicle car = new PassengerCar("01A777AA");
        Vehicle truck = new HeavyTruck("99B123CD");
        Vehicle moto = new Motorcycle("55C000BB");

        TripRecord t1 = new TripRecord(10f, car, LocalDateTime.of(2026, 5, 25, 10, 0));
        TripRecord t2 = new TripRecord(5f, truck, LocalDateTime.of(2026, 5, 25, 8, 0));
        TripRecord t3 = new TripRecord(20f, moto, LocalDateTime.of(2026, 5, 24, 12, 0));

        s1.addTripRecord(t1);
        s1.addTripRecord(t2);
        s2.addTripRecord(t3);

        // Standard expectations
        double s1Std = s1.getTotalRevenue(StandardStrategy.getInstance()); // 10*2 + 5*4 = 20 + 20 = 40
        double s2Std = s2.getTotalRevenue(StandardStrategy.getInstance()); // 20*1 = 20
        assertEquals("s1 standard revenue", 40.0, s1Std);
        assertEquals("s2 standard revenue", 20.0, s2Std);

        TollSegment most = HighwaySystem.getMostProfitableSegment();
        assertEquals("most profitable segment is s1 (by name length check)", 1.0, most == s1 ? 1.0 : 0.0);

        // EcoPeak expectations (per implementation)
        double s1Eco = s1.getTotalRevenue(EcoPeakStrategy.getInstance());
        // car off-peak: 10*2 = 20 -> -10% = 18; truck peak: 5*4=20 -> *1.5 = 30; total = 48
        assertEquals("s1 eco revenue", 48.0, s1Eco);

        double s2Green = s2.getTotalRevenue(GreenWeekendStrategy.getInstance());
        // moto weekend multiplier 0.5 -> 20 * 0.5 = 10
        assertEquals("s2 green-weekend revenue", 10.0, s2Green);

        // Eco-friendly checks
        assertTrue("s1 not strictly eco-friendly", !s1.isStrictlyEcoFriendly()); // had truck at peak
        assertTrue("s2 strictly eco-friendly", s2.isStrictlyEcoFriendly());

        if (failed == 0) {
            System.out.println("ALL TESTS PASSED");
            System.exit(0);
        } else {
            System.out.println(failed + " TEST(S) FAILED");
            System.exit(2);
        }
    }
}
