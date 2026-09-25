# Simulate circuit bookings: reserve capacity on every link in a route,
# then return that capacity when the demand ends.
import json
import sys
from decimal import Decimal


def simulate(network):
    # A link has one shared capacity in both directions: A-B is also B-A.
    free = {
        frozenset(link["points"]): link["capacity"]
        for link in network["links"]
    }
    simulation = network["simulation"]
    demands = simulation["demands"]
    active = {}  # Demand index -> booked links; repeated endpoint pairs stay separate.
    event = 0

    for time in range(simulation["duration"] + 1):
        # Release first so a demand ending now can make room for one starting now.
        # Copy the items because we remove finished bookings during the loop.
        for index, links in list(active.items()):
            demand = demands[index]
            if demand["end-time"] != time:
                continue

            for link in links:
                free[link] += demand["demand"]
            del active[index]

            start, end = demand["end-points"]
            event += 1
            print(f"{event}. demand deallocation: {start}<->{end} st:{time}")

        for index, demand in enumerate(demands):
            if demand["start-time"] != time:
                continue

            start, end = demand["end-points"]
            amount = demand["demand"]
            result = "unsuccessful"

            # Try the supplied routes in order; an undirected route also works backwards.
            for circuit in network["possible-circuits"]:
                if {circuit[0], circuit[-1]} != {start, end}:
                    continue

                # Consecutive nodes describe the links that this route must reserve.
                links = [
                    frozenset((a, b)) for a, b in zip(circuit, circuit[1:])
                ]
                if not all(link in free and free[link] >= amount for link in links):
                    continue

                # Check the WHOLE route before changing anything: no partial bookings.
                for link in links:
                    free[link] -= amount
                active[index] = links
                result = "successful"
                break

            # Failed requests are logged once and never added to active bookings.
            event += 1
            print(f"{event}. demand allocation: {start}<->{end} st:{time} - {result}")


if __name__ == "__main__":
    if len(sys.argv) != 2:
        sys.exit("Usage: python3 client.py <json-file>")

    with open(sys.argv[1], encoding="utf-8") as file:
        # Keep decimal capacities exact so rounding cannot reject a valid booking.
        simulate(json.load(file, parse_float=Decimal))
