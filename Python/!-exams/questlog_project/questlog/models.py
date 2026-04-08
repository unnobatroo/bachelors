import json

class Quest:
    """
    Class representing a single quest.
    Holds the name of the quest and its required items with their quantities.
    """
    def __init__(self, name, items):
        self.name = name
        self.items = items  # Dictionary of item: quantity

    def __str__(self):
        return f"Quest: {self.name}\nRequired Items:\n" + "\n".join([f"- {item}: {qty}" for item, qty in self.items.items()])

class Inventory:
    """
    Class managing the inventory of items.
    Handles adding, using, and checking the availability of items.
    """
    def __init__(self, items=None):
        if items is None:
            items = {}
        self.items = items

    def add_item(self, item_name, quantity):
        """Adds a specified quantity to an item."""
        if quantity < 0:
            raise ValueError("Quantity to add must be non-negative.")
        if item_name in self.items:
            self.items[item_name] += quantity
        else:
            self.items[item_name] = quantity

    def use_item(self, item_name, quantity):
        """Reduces the quantity of an item. Raises ValueError if insufficient stock."""
        if quantity < 0:
            raise ValueError("Quantity to use must be non-negative.")
        if item_name not in self.items or self.items[item_name] < quantity:
            current_stock = self.items.get(item_name, 0)
            raise ValueError(f"Cannot USE {quantity} {item_name}, only {current_stock} available.")
        self.items[item_name] -= quantity

    def get_quantity(self, item_name):
        """Returns the current quantity of an item."""
        return self.items.get(item_name, 0)

    def to_dict(self):
        """Returns the dictionary representation of the inventory."""
        return self.items
