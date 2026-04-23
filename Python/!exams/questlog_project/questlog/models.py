from dataclasses import dataclass, field
from enum import Enum
from typing import Dict


class InventoryAction(str, Enum):
    """Supported inventory mutations.

    Using an enum avoids magic strings and keeps command handling explicit.
    """

    ADD = "add"
    USE = "use"


@dataclass(slots=True)
class Quest:
    """Represents a single quest and the resources it needs."""

    name: str
    items: Dict[str, int] = field(default_factory=dict)

    @classmethod
    def from_dict(cls, data: dict) -> "Quest":
        """Create a quest from JSON payload while normalizing quantities."""
        raw_items = data.get("items", {})
        normalized_items = {str(item): int(qty) for item, qty in raw_items.items()}
        return cls(name=str(data["name"]), items=normalized_items)

    def __str__(self) -> str:
        lines = [f"- {item}: {qty}" for item, qty in self.items.items()]
        return f"Quest: {self.name}\nRequired Items:\n" + "\n".join(lines)


@dataclass(slots=True)
class Inventory:
    """Holds player inventory and enforces stock-related rules."""

    items: Dict[str, int] = field(default_factory=dict)

    @classmethod
    def from_dict(cls, data: dict) -> "Inventory":
        """Create an inventory from JSON data and normalize quantity types."""
        normalized = {str(item): int(qty) for item, qty in data.items()}
        return cls(items=normalized)

    def apply(self, action: InventoryAction, item_name: str, quantity: int) -> None:
        """Apply one inventory operation.

        This single method keeps mutation logic in one place and makes command flow
        easier to follow for new programmers.
        """
        self._validate_quantity(quantity)

        if action == InventoryAction.ADD:
            self.items[item_name] = self.get_quantity(item_name) + quantity
            return

        # USE action: check availability before deducting.
        current_stock = self.get_quantity(item_name)
        if current_stock < quantity:
            raise ValueError(f"Cannot USE {quantity} {item_name}, only {current_stock} available.")
        self.items[item_name] = current_stock - quantity

    def get_quantity(self, item_name: str) -> int:
        """Return current stock of one item; missing item counts as zero."""
        return int(self.items.get(item_name, 0))

    def to_dict(self) -> Dict[str, int]:
        """Return a serializable dictionary copy."""
        return dict(self.items)

    @staticmethod
    def _validate_quantity(quantity: int) -> None:
        if not isinstance(quantity, int):
            raise ValueError("Quantity must be an integer.")
        if quantity < 0:
            raise ValueError("Quantity must be non-negative.")
