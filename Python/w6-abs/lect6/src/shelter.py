class Dog:
    def __init__(self, name, age): self.name, self.age = name, age

class Person:
    def __init__(self, name, pet=None): self.name, self.pet = name, pet  # can CONTAIN a Dog

class Shelter:
    def __init__(self, name, dogs): self.name, self.dogs = name, dogs
    def adopt(self, person, dog):
        self.dogs.remove(dog)          # OBJECTS INTERACT
        person.pet = dog               # container relationship
        return dog                     # METHOD RETURNS an object

# Demo: pass OBJECTS as parameters; build from simple parts
fido_dog = Dog("Fido", 2)
luna_dog = Dog("Luna", 1)
dogs = [ fido_dog, luna_dog]
shelter = Shelter("City Shelter", dogs)
alice = Person("Alice")

adopted = shelter.adopt(alice, dogs[0])  # pass objects to a method
print(adopted.name, "->", alice.name)    # Fido -> Alice
print([d.name for d in shelter.dogs])    # ['Luna']
