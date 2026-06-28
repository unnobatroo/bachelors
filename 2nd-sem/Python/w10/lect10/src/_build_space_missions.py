"""Build the lecture's expanded space_missions.csv.

The goal is a teaching dataset (~160 rows) that supports all four workhorse
charts in lecture 10:

* compare / rank: missions per agency
* change over time: yearly mission counts per agency, 1957-2025
* distribution: skewed cost distribution with a long flagship tail
* relationship: cost vs duration vs distance per agency

Real missions are seeded for recognisability; the rest are deterministic
fillers so each year and agency has enough mass to plot.

Run from the lecture src directory:

    cd 10_AI_ML_DS_2/src
    uv run python _build_space_missions.py
"""

from __future__ import annotations

import csv
import random
from dataclasses import dataclass
from pathlib import Path


HERE = Path(__file__).resolve().parent
OUT = HERE / "space_missions.csv"
SEED = 7

DESTINATIONS = {
    "Low Earth Orbit": 500,
    "Geostationary Orbit": 35_786,
    "Moon": 384_400,
    "L2 Point": 1_500_000,
    "Sun": 150_000_000,
    "Mercury": 91_000_000,
    "Venus": 41_000_000,
    "Mars": 225_000_000,
    "Jupiter": 778_000_000,
    "Saturn": 1_400_000_000,
    "Pluto": 5_900_000_000,
    "Asteroid Bennu": 300_000_000,
    "Asteroid Ryugu": 300_000_000,
    "Comet 67P": 500_000_000,
    "Interstellar Space": 23_500_000_000,
    "Deep Space": 150_000_000,
    "ISS": 408,
}

CLASSES = (
    "crewed",
    "satellite",
    "orbiter",
    "lander",
    "rover",
    "flyby",
    "sample-return",
    "telescope",
    "station",
)


@dataclass(frozen=True)
class Mission:
    mission_name: str
    agency: str
    launch_year: int
    duration_days: float
    crew_size: int
    destination: str
    mission_class: str
    success: bool
    cost_million_usd: float


# --- Seed list of recognisable real missions -------------------------------

REAL: list[Mission] = [
    # Cold-war era
    Mission("Sputnik 1", "USSR", 1957, 0.08, 0, "Low Earth Orbit", "satellite", True, 0.5),
    Mission("Sputnik 2", "USSR", 1957, 162, 0, "Low Earth Orbit", "satellite", True, 0.6),
    Mission("Explorer 1", "NASA", 1958, 113, 0, "Low Earth Orbit", "satellite", True, 1.0),
    Mission("Vanguard 1", "NASA", 1958, 5840, 0, "Low Earth Orbit", "satellite", True, 1.5),
    Mission("Pioneer 1", "NASA", 1958, 1.5, 0, "Moon", "flyby", False, 2.0),
    Mission("Luna 1", "USSR", 1959, 4, 0, "Moon", "flyby", True, 3.0),
    Mission("Luna 2", "USSR", 1959, 1.5, 0, "Moon", "lander", True, 3.5),
    Mission("Luna 3", "USSR", 1959, 18, 0, "Moon", "flyby", True, 4.0),
    Mission("Mercury-Redstone 3", "NASA", 1961, 0.02, 1, "Low Earth Orbit", "crewed", True, 50.0),
    Mission("Vostok 1", "USSR", 1961, 0.07, 1, "Low Earth Orbit", "crewed", True, 20.0),
    Mission("Friendship 7", "NASA", 1962, 0.2, 1, "Low Earth Orbit", "crewed", True, 60.0),
    Mission("Mariner 2", "NASA", 1962, 130, 0, "Venus", "flyby", True, 60.0),
    Mission("Vostok 6", "USSR", 1963, 3, 1, "Low Earth Orbit", "crewed", True, 25.0),
    Mission("Voskhod 2", "USSR", 1965, 1, 2, "Low Earth Orbit", "crewed", True, 45.0),
    Mission("Mariner 4", "NASA", 1964, 230, 0, "Mars", "flyby", True, 80.0),
    Mission("Gemini 4", "NASA", 1965, 4, 2, "Low Earth Orbit", "crewed", True, 110.0),
    Mission("Luna 9", "USSR", 1966, 7, 0, "Moon", "lander", True, 25.0),
    Mission("Surveyor 1", "NASA", 1966, 45, 0, "Moon", "lander", True, 70.0),
    Mission("Apollo 8", "NASA", 1968, 6, 3, "Moon", "crewed", True, 2500.0),
    Mission("Apollo 11", "NASA", 1969, 8, 3, "Moon", "crewed", True, 25400.0),
    Mission("Apollo 12", "NASA", 1969, 10, 3, "Moon", "crewed", True, 20000.0),
    Mission("Apollo 13", "NASA", 1970, 6, 3, "Moon", "crewed", False, 25400.0),
    Mission("Venera 7", "USSR", 1970, 120, 0, "Venus", "lander", True, 40.0),
    Mission("Luna 16", "USSR", 1970, 12, 0, "Moon", "sample-return", True, 60.0),
    Mission("Mariner 9", "NASA", 1971, 350, 0, "Mars", "orbiter", True, 137.0),
    Mission("Salyut 1", "USSR", 1971, 175, 0, "Low Earth Orbit", "station", True, 100.0),
    Mission("Apollo 15", "NASA", 1971, 12, 3, "Moon", "crewed", True, 23000.0),
    Mission("Apollo 17", "NASA", 1972, 12, 3, "Moon", "crewed", True, 18000.0),
    Mission("Pioneer 10", "NASA", 1972, 11000, 0, "Interstellar Space", "flyby", True, 150.0),
    Mission("Skylab", "NASA", 1973, 2249, 9, "Low Earth Orbit", "station", True, 2200.0),
    Mission("Pioneer 11", "NASA", 1973, 11000, 0, "Interstellar Space", "flyby", True, 150.0),
    Mission("Mariner 10", "NASA", 1973, 510, 0, "Mercury", "flyby", True, 100.0),
    Mission("Venera 9", "USSR", 1975, 140, 0, "Venus", "lander", True, 25.0),
    Mission("Apollo-Soyuz", "NASA", 1975, 9, 3, "Low Earth Orbit", "crewed", True, 245.0),
    Mission("Viking 1", "NASA", 1975, 2300, 0, "Mars", "lander", True, 1000.0),
    Mission("Viking 2", "NASA", 1975, 1300, 0, "Mars", "lander", True, 1000.0),
    Mission("Voyager 1", "NASA", 1977, 17167, 0, "Interstellar Space", "flyby", True, 865.0),
    Mission("Voyager 2", "NASA", 1977, 17167, 0, "Interstellar Space", "flyby", True, 895.0),
    Mission("Salyut 6", "USSR", 1977, 1764, 0, "Low Earth Orbit", "station", True, 250.0),
    # 1980s
    Mission("STS-1 Columbia", "NASA", 1981, 2, 2, "Low Earth Orbit", "crewed", True, 450.0),
    Mission("Venera 13", "USSR", 1981, 365, 0, "Venus", "lander", True, 40.0),
    Mission("Salyut 7", "USSR", 1982, 3216, 0, "Low Earth Orbit", "station", True, 350.0),
    Mission("STS-7 Challenger", "NASA", 1983, 6, 5, "Low Earth Orbit", "crewed", True, 380.0),
    Mission("IRAS", "NASA", 1983, 300, 0, "Low Earth Orbit", "telescope", True, 240.0),
    Mission("Vega 1", "USSR", 1984, 720, 0, "Comet 67P", "flyby", True, 60.0),
    Mission("STS-51L Challenger", "NASA", 1986, 0.005, 7, "Low Earth Orbit", "crewed", False, 380.0),
    Mission("Mir Core", "USSR", 1986, 5511, 0, "Low Earth Orbit", "station", True, 500.0),
    Mission("Phobos 2", "USSR", 1988, 250, 0, "Mars", "orbiter", False, 60.0),
    Mission("Magellan", "NASA", 1989, 1499, 0, "Venus", "orbiter", True, 1500.0),
    Mission("Galileo", "NASA", 1989, 5000, 0, "Jupiter", "orbiter", True, 1390.0),
    # 1990s — international era begins
    Mission("Hubble Space Telescope", "NASA/ESA", 1990, 12410, 0, "Low Earth Orbit", "telescope", True, 4700.0),
    Mission("Ulysses", "NASA/ESA", 1990, 6204, 0, "Sun", "orbiter", True, 750.0),
    Mission("Mars Observer", "NASA", 1992, 330, 0, "Mars", "orbiter", False, 813.0),
    Mission("SOHO", "ESA", 1995, 10000, 0, "Sun", "orbiter", True, 1000.0),
    Mission("Mars Pathfinder", "NASA", 1996, 267, 0, "Mars", "rover", True, 280.0),
    Mission("Mars Global Surveyor", "NASA", 1996, 6800, 0, "Mars", "orbiter", True, 720.0),
    Mission("Cassini-Huygens", "NASA/ESA", 1997, 7309, 0, "Saturn", "orbiter", True, 3260.0),
    Mission("ISS Zarya", "NASA/Roscosmos", 1998, 9999, 0, "Low Earth Orbit", "station", True, 9000.0),
    Mission("ISS Expedition 1", "NASA/Roscosmos", 2000, 136, 3, "ISS", "crewed", True, 150000.0),
    Mission("Mars Odyssey", "NASA", 2001, 8500, 0, "Mars", "orbiter", True, 297.0),
    # 2000s — diversification
    Mission("Spirit Rover", "NASA", 2003, 2200, 0, "Mars", "rover", True, 400.0),
    Mission("Opportunity Rover", "NASA", 2003, 5300, 0, "Mars", "rover", True, 400.0),
    Mission("Mars Express", "ESA", 2003, 7500, 0, "Mars", "orbiter", True, 410.0),
    Mission("Hayabusa", "JAXA", 2003, 2592, 0, "Asteroid Bennu", "sample-return", True, 200.0),
    Mission("Rosetta", "ESA", 2004, 4500, 0, "Comet 67P", "orbiter", True, 1400.0),
    Mission("MESSENGER", "NASA", 2004, 4000, 0, "Mercury", "orbiter", True, 450.0),
    Mission("Cartosat-1", "ISRO", 2005, 4500, 0, "Low Earth Orbit", "satellite", True, 35.0),
    Mission("New Horizons", "NASA", 2006, 3462, 0, "Pluto", "flyby", True, 700.0),
    Mission("Chang'e 1", "CNSA", 2007, 494, 0, "Moon", "orbiter", True, 187.0),
    Mission("Chandrayaan-1", "ISRO", 2008, 312, 0, "Moon", "orbiter", True, 95.0),
    Mission("Kepler", "NASA", 2009, 3500, 0, "Deep Space", "telescope", True, 600.0),
    # 2010s — commercial + Asian agencies expand
    Mission("Falcon 9 maiden", "SpaceX", 2010, 0.02, 0, "Low Earth Orbit", "satellite", True, 60.0),
    Mission("Dragon C1", "SpaceX", 2010, 0.5, 0, "Low Earth Orbit", "satellite", True, 80.0),
    Mission("Juno", "NASA", 2011, 2000, 0, "Jupiter", "orbiter", True, 1100.0),
    Mission("Curiosity Rover", "NASA", 2011, 4380, 0, "Mars", "rover", True, 2500.0),
    Mission("Dragon CRS-1", "SpaceX", 2012, 18, 0, "ISS", "satellite", True, 130.0),
    Mission("Chang'e 3", "CNSA", 2013, 1500, 0, "Moon", "lander", True, 290.0),
    Mission("Mangalyaan", "ISRO", 2013, 3000, 0, "Mars", "orbiter", True, 74.0),
    Mission("Gaia", "ESA", 2013, 3500, 0, "L2 Point", "telescope", True, 1000.0),
    Mission("Hayabusa2", "JAXA", 2014, 2190, 0, "Asteroid Ryugu", "sample-return", True, 150.0),
    Mission("LISA Pathfinder", "ESA", 2015, 600, 0, "L2 Point", "telescope", True, 600.0),
    Mission("ExoMars TGO", "ESA/Roscosmos", 2016, 3200, 0, "Mars", "orbiter", True, 1500.0),
    Mission("OSIRIS-REx", "NASA", 2016, 2500, 0, "Asteroid Bennu", "sample-return", True, 800.0),
    Mission("Falcon Heavy demo", "SpaceX", 2018, 0.04, 0, "Deep Space", "satellite", True, 90.0),
    Mission("InSight", "NASA", 2018, 1400, 0, "Mars", "lander", True, 830.0),
    Mission("Chang'e 4", "CNSA", 2018, 1900, 0, "Moon", "lander", True, 170.0),
    Mission("BepiColombo", "ESA/JAXA", 2018, 2500, 0, "Mercury", "orbiter", True, 2000.0),
    Mission("Parker Solar Probe", "NASA", 2018, 2500, 0, "Sun", "orbiter", True, 1500.0),
    Mission("TESS", "NASA", 2018, 2000, 0, "Deep Space", "telescope", True, 200.0),
    Mission("Chandrayaan-2", "ISRO", 2019, 1800, 0, "Moon", "lander", False, 140.0),
    Mission("Crew Dragon Demo-2", "SpaceX", 2020, 64, 2, "ISS", "crewed", True, 1500.0),
    Mission("Tianwen-1", "CNSA", 2020, 687, 0, "Mars", "orbiter", True, 800.0),
    Mission("Perseverance Rover", "NASA", 2020, 730, 0, "Mars", "rover", True, 2700.0),
    Mission("Solar Orbiter", "ESA", 2020, 1500, 0, "Sun", "orbiter", True, 1500.0),
    Mission("Chang'e 5", "CNSA", 2020, 24, 0, "Moon", "sample-return", True, 200.0),
    Mission("DART", "NASA", 2021, 300, 0, "Asteroid Bennu", "flyby", True, 330.0),
    Mission("Lucy", "NASA", 2021, 4380, 0, "Asteroid Bennu", "flyby", True, 989.0),
    Mission("James Webb Telescope", "NASA/ESA", 2021, 1095, 0, "L2 Point", "telescope", True, 10000.0),
    Mission("Artemis I", "NASA", 2022, 25, 0, "Moon", "crewed", True, 4100.0),
    Mission("CAPSTONE", "NASA", 2022, 365, 0, "Moon", "satellite", True, 30.0),
    Mission("Chandrayaan-3", "ISRO", 2023, 42, 0, "Moon", "lander", True, 75.0),
    Mission("JUICE", "ESA", 2023, 4000, 0, "Jupiter", "orbiter", True, 1600.0),
    Mission("Psyche", "NASA", 2023, 1000, 0, "Asteroid Bennu", "orbiter", True, 985.0),
    Mission("SLIM", "JAXA", 2023, 80, 0, "Moon", "lander", True, 120.0),
    Mission("Europa Clipper", "NASA", 2024, 200, 0, "Jupiter", "orbiter", True, 4250.0),
    Mission("Hera", "ESA", 2024, 730, 0, "Asteroid Bennu", "orbiter", True, 380.0),
    Mission("Crew-9", "SpaceX", 2024, 150, 4, "ISS", "crewed", True, 350.0),
    Mission("Polaris Dawn", "SpaceX", 2024, 5, 4, "Low Earth Orbit", "crewed", True, 200.0),
    Mission("Artemis II", "NASA", 2026, 10, 4, "Moon", "crewed", True, 4500.0),
]


# --- Filler missions to give each year/agency enough plotting mass ---------

# How many filler launches (per agency, per period) to add on top of REAL.
# These are deterministic with seed=7 so the dataset is reproducible.

FILLER_PROFILE: dict[str, list[tuple[int, int, int]]] = {
    # agency -> (start_year, end_year, total_filler_count)
    "NASA":      [(1958, 1969, 14), (1970, 1989, 18), (1990, 2009, 14), (2010, 2025, 14)],
    "USSR":      [(1959, 1989, 30)],
    "Roscosmos": [(1992, 2025, 14)],
    "ESA":       [(1995, 2025, 14)],
    "JAXA":      [(2003, 2025, 8)],
    "ISRO":      [(2005, 2025, 8)],
    "CNSA":      [(2007, 2025, 12)],
    "SpaceX":    [(2010, 2025, 18)],
    "Rocket Lab": [(2018, 2025, 6)],
}


def _filler_destination(rng: random.Random, agency: str) -> tuple[str, str, float]:
    """Pick a (destination, class, base_cost) tuple consistent with the agency."""
    if agency == "SpaceX":
        choice = rng.choices(
            [
                ("Low Earth Orbit", "satellite", 70),
                ("Low Earth Orbit", "satellite", 90),
                ("Geostationary Orbit", "satellite", 110),
                ("ISS", "satellite", 130),
            ],
            weights=[5, 4, 2, 2],
        )[0]
        return choice
    if agency == "Rocket Lab":
        return rng.choices(
            [
                ("Low Earth Orbit", "satellite", 7),
                ("Geostationary Orbit", "satellite", 14),
            ],
            weights=[4, 1],
        )[0]
    if agency in {"USSR", "Roscosmos"}:
        return rng.choices(
            [
                ("Low Earth Orbit", "satellite", 20),
                ("Low Earth Orbit", "crewed", 80),
                ("Geostationary Orbit", "satellite", 60),
                ("Moon", "orbiter", 110),
                ("Venus", "orbiter", 60),
                ("Mars", "orbiter", 90),
            ],
            weights=[5, 2, 2, 2, 2, 1],
        )[0]
    if agency == "NASA":
        return rng.choices(
            [
                ("Low Earth Orbit", "satellite", 90),
                ("Low Earth Orbit", "telescope", 320),
                ("Low Earth Orbit", "crewed", 450),
                ("Moon", "orbiter", 180),
                ("Mars", "orbiter", 320),
                ("Mars", "lander", 800),
                ("Geostationary Orbit", "satellite", 240),
                ("Deep Space", "telescope", 350),
            ],
            weights=[3, 2, 2, 2, 2, 1, 2, 1],
        )[0]
    if agency == "ESA":
        return rng.choices(
            [
                ("Low Earth Orbit", "satellite", 130),
                ("Geostationary Orbit", "satellite", 220),
                ("Mars", "orbiter", 400),
                ("L2 Point", "telescope", 700),
                ("Sun", "orbiter", 600),
            ],
            weights=[3, 2, 2, 2, 1],
        )[0]
    if agency == "JAXA":
        return rng.choices(
            [
                ("Low Earth Orbit", "satellite", 80),
                ("Geostationary Orbit", "satellite", 150),
                ("Moon", "orbiter", 220),
                ("Venus", "orbiter", 240),
            ],
            weights=[3, 2, 2, 1],
        )[0]
    if agency == "ISRO":
        return rng.choices(
            [
                ("Low Earth Orbit", "satellite", 30),
                ("Geostationary Orbit", "satellite", 80),
                ("Moon", "orbiter", 100),
                ("Mars", "orbiter", 90),
            ],
            weights=[4, 2, 2, 1],
        )[0]
    if agency == "CNSA":
        return rng.choices(
            [
                ("Low Earth Orbit", "satellite", 70),
                ("Low Earth Orbit", "crewed", 320),
                ("Geostationary Orbit", "satellite", 130),
                ("Moon", "orbiter", 180),
                ("Mars", "orbiter", 700),
            ],
            weights=[4, 1, 2, 2, 1],
        )[0]
    raise ValueError(f"unknown agency: {agency}")


def _filler_duration(rng: random.Random, mission_class: str) -> float:
    base = {
        "satellite": (180, 4000),
        "crewed": (3, 200),
        "orbiter": (1000, 6000),
        "lander": (10, 800),
        "rover": (1500, 5000),
        "flyby": (200, 4000),
        "sample-return": (1000, 4000),
        "telescope": (1500, 9000),
        "station": (2000, 5000),
    }
    lo, hi = base.get(mission_class, (200, 1000))
    return float(round(rng.uniform(lo, hi), 1))


def _maybe_failure(rng: random.Random) -> bool:
    """Most missions succeed; ~9 % fail to keep the success column meaningful."""
    return rng.random() > 0.09


def _filler_cost(rng: random.Random, base: float) -> float:
    """Wiggle the base cost with a log-normal-ish factor."""
    factor = max(0.45, rng.lognormvariate(0.0, 0.45))
    return float(round(base * factor, 1))


def make_filler(rng: random.Random) -> list[Mission]:
    out: list[Mission] = []
    counter: dict[str, int] = {}
    for agency, periods in FILLER_PROFILE.items():
        for start, end, count in periods:
            years = end - start + 1
            for i in range(count):
                year = start + (i * years) // count
                year = max(start, min(end, year + rng.randint(-1, 1)))
                dest, mission_class, base_cost = _filler_destination(rng, agency)
                duration = _filler_duration(rng, mission_class)
                crew = 0
                if mission_class == "crewed":
                    crew = rng.choice([1, 2, 3, 4])
                cost = _filler_cost(rng, base_cost)
                idx = counter.get(agency, 0) + 1
                counter[agency] = idx
                short = "".join(p[0] for p in agency.replace("/", " ").split())
                name = f"{short}-{mission_class[:3].upper()}-{idx:02d}"
                out.append(
                    Mission(
                        mission_name=name,
                        agency=agency,
                        launch_year=year,
                        duration_days=duration,
                        crew_size=crew,
                        destination=dest,
                        mission_class=mission_class,
                        success=_maybe_failure(rng),
                        cost_million_usd=cost,
                    )
                )
    return out


def main() -> None:
    rng = random.Random(SEED)
    missions = REAL + make_filler(rng)
    missions.sort(key=lambda m: (m.launch_year, m.agency, m.mission_name))

    fields = [
        "mission_name",
        "agency",
        "launch_year",
        "duration_days",
        "crew_size",
        "destination",
        "mission_class",
        "success",
        "distance_km",
        "cost_million_usd",
    ]
    with OUT.open("w", newline="") as fh:
        writer = csv.writer(fh)
        writer.writerow(fields)
        for m in missions:
            writer.writerow(
                [
                    m.mission_name,
                    m.agency,
                    m.launch_year,
                    m.duration_days,
                    m.crew_size,
                    m.destination,
                    m.mission_class,
                    "True" if m.success else "False",
                    DESTINATIONS[m.destination],
                    m.cost_million_usd,
                ]
            )
    print(f"wrote {OUT.relative_to(HERE.parent)} with {len(missions)} rows")


if __name__ == "__main__":
    main()
