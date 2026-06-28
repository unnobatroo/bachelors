# Toll Road Network (Simulation and Optimization)

A national highway authority manages multiple **toll segments** across the country's highway network. Drivers register
their **vehicles** with the highway system to travel on these segments. The same vehicle can travel through multiple
toll segments on different days. For every vehicle, we know its license plate number and the record of its trips through
each toll segment.

A **trip record** includes the toll segment where the trip occurred, the vehicle that made the trip, the distance
traveled (in km), and the timestamp (date and time). The possible vehicle classes allowed on these segments are *
*Motorcycles**, **Passenger Cars**, and **Heavy Trucks**.

### Calculation Rule

The toll fee of a trip is calculated as the product of the distance traveled and a base vehicle class multiplier.
However, to manage traffic congestion, reduce pollution, and encourage off-peak driving, the base rate is dynamically
altered depending on the current **Toll Collection Strategy**. The system supports three different pricing strategies:

1. **Standard Flat Strategy:** The toll is simply the distance multiplied by the base vehicle multiplier. (Multipliers:
   Heavy Trucks: 4, Passenger Cars: 2, Motorcycles: 1).
2. **Eco-Peak Strategy:** If the trip happens during peak hours (07:00–09:00 or 16:00–18:00), a $1.5\times$ congestion
   surcharge is applied to all vehicles. If it occurs outside peak hours, Heavy Trucks get a $10\%$ discount to
   encourage off-peak logistics.
3. **Green Weekend Strategy:** On weekends (Saturdays and Sundays), Passenger Cars and Motorcycles receive a $50\%$
   discount to promote tourism, while Heavy Trucks are heavily penalized with a $2\times$ toll fee to discourage weekend
   freight traffic.

### Expected Analytical Queries

Your model must be able to resolve the following queries dynamically based on the active pricing strategy:

* **Query 1:** *Which is the most profitable toll segment?* Find the toll segment where the total revenue generated from
  all vehicle trips combined is the highest under the *currently active pricing strategy*.
* **Query 2:** *Is a toll segment strictly eco-friendly?* Check if a given toll segment had *at least one heavy truck
  trip* recorded during peak hours (enabling monitoring of congestion goals).

### Design Constraints and Instructions

1. **Strategy Pattern Requirement:** Avoid violating the **Open-Closed Principle (OCP)**. Do not use complex
   `switch-case` or `if-else` branches inside the trip or segment classes to evaluate toll fees based on time or
   strategy type. Instead, encapsulate the toll calculation algorithms into a `PricingStrategy` interface/abstract class
   hierarchy (the Strategy Pattern), making the billing engine easily interchangeable at runtime.
2. **Subclassing and Singleton Patterns:** The vehicle classes (Motorcycle, Passenger Car, Heavy Truck) should be
   modeled using inheritance. If any specific vehicle class or pricing strategy contains no unique instance-specific
   state data, it should be implemented using the **Singleton Design Pattern** to minimize memory footprint.
3. **Encapsulation:** Ensure all class attributes are strictly private or protected. Data collection processing (like
   calculating sums or searching fields) must rely on object collaboration via runtime polymorphism rather than exposing
   raw arrays or collections to the main program loop.

## Required Deliverables

### 1. Class Diagram

Produce a complete structural Class Diagram representing your object-oriented design. The diagram must explicitly
feature:

* All domain classes (`TollNetwork`, `TollSegment`, `TripRecord`, `Vehicle` along with its subclasses).
* The **Strategy Design Pattern** structure, showing the `PricingStrategy` interface/abstract class and its concrete
  strategy implementations (`StandardStrategy`, `EcoPeakStrategy`, `GreenWeekendStrategy`).
* Proper association, aggregation, or composition lines with correct multiplicities (e.g., `0..*` or `1`).
* Visibility modifiers (`-` for private, `+` for public) for all essential methods and attributes.
* **Singleton** annotations (`<<singleton>>`) where applicable.

### 2. Sequence Diagrams

Produce behavioral Sequence Diagrams illustrating object interactions and the flow of control at runtime. You must
provide diagrams for:

* **The Toll Calculation Process:** Show how a call originating from the `TollNetwork` or `TollSegment` coordinates with
  a `TripRecord`, delegates the logic via runtime polymorphism to the active `PricingStrategy`, and how the strategy
  queries the `Vehicle` subclass to compute the precise toll fee.
* **Resolution of Query 1 (Most Profitable Segment):** Show the loop/interaction sequence where the central coordinator
  iterates through the collections, showing how polymorphism ensures that changing the active strategy yields a
  different computational flow without altering the structural code of the segments or trips.