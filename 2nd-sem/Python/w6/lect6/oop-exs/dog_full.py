class Dog:
    """Data + behavior bundled: name, age, energy, hunger, tricks."""

    def __init__(self, name, age):
        self.name = name
        self.age = age
        self.energy = 80  # 0–100
        self.hunger = 20  # 0–100 (higher = hungrier)
        self.tricks = set()

    def walk(self, km=1):
        self.energy = max(0, self.energy - 15 * km)
        self.hunger = min(100, self.hunger + 10 * km)
        print(f"{self.name} enjoyed a {km} km walk.")

    def feed(self, grams=100):
        self.hunger = max(0, self.hunger - grams // 10)
        self.energy = min(100, self.energy + grams // 20)
        print(f"{self.name} ate {grams}g of food.")

    def learn(self, trick):
        self.tricks.add(trick)
        print(f"{self.name} learned {trick}!")

    def birthday(self):
        self.age += 1
        print(f"Happy Birthday, {self.name}! Now {self.age}.")

    def status(self):
        return (
            f"age={self.age}, energy={self.energy}, "
            f"hunger={self.hunger}, tricks={sorted(self.tricks)}"
        )


# Demo: methods update the same object's internal state
mochi = Dog("Mochi", 2)
print(mochi.status())  # age=2, energy=80, hunger=20, tricks=[]
mochi.walk(2)  # energy↓, hunger↑
mochi.feed(150)  # hunger↓, energy↑
mochi.learn("roll over")  # new capability stored
mochi.birthday()  # age changes
print(mochi.status())  # age=3, energy=57, hunger=25, tricks=['roll over']
