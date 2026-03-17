# Parent Class (Superclass)
class Animal:
    def __init__(self, name: str):
        self.name = name

    def speak(self):
        print(f"{self.name} makes a generic animal sound.")

    def eat(self):
        print(f"{self.name} is eating.")

    def sleep(self):
        print(f"{self.name} is sleeping.")

# Child Class (Subclass)
class Dog(Animal):
    def __init__(self, name: str, breed: str):
        # Call the parent class's constructor to initialize the 'name' attribute
        super().__init__(name)
        self.breed = breed  # Add a new attribute specific to Dog

    # 1. Method Overriding: This method REPLACES the parent's version.
    def speak(self):
        print(f"{self.name}, the {self.breed}, says Woof!")

    # 2. Method Extension: This method ADDS to the parent's version.
    def eat(self):
        super().eat()  # Call the parent's eat() method first
        print(f"As a {self.breed}, {self.name} enjoys dog food.")

    # 3. New Child-Specific Method: This behavior is unique to Dog.
    def wag_tail(self):
        print(f"{self.name} wags its tail excitedly!")

# --- Using the classes ---
my_dog = Dog("Fido", "Golden Retriever")

# Calling the overridden method
print("--- Overriding Example (speak) ---")
my_dog.speak()
# Output: Fido, the Golden Retriever, says Woof!

# Calling the extended method
print("\n--- Extension Example (eat) ---")
my_dog.eat()
# Output:
# Fido is eating.
# As a Golden Retriever, Fido enjoys dog food.

# Calling the inherited (untouched) method from the parent
print("\n--- Inherited Example (sleep) ---")
my_dog.sleep()
# Output: Fido is sleeping.

# Calling the new child-specific method
print("\n--- New Method Example (wag_tail) ---")
my_dog.wag_tail()
# Output: Fido wags its tail excitedly!