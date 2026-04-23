import json
import os
from enum import Enum
from typing import Dict, List, Tuple

from .models import Inventory, InventoryAction, Quest


class CommandGroup(str, Enum):
    """Top-level command groups supported by the CLI."""

    QUEST = "quest"
    INVENTORY = "inventory"
    PLAN = "plan"


class QuestCommand(str, Enum):
    """Valid subcommands for `quest ...`."""

    LIST = "list"
    VIEW = "view"
    GAP = "gap"
    COMPLETE = "complete"


class InventoryCommand(str, Enum):
    """Valid subcommands for `inventory ...`."""

    ADD = "add"
    USE = "use"
    PROCESS = "process"


class BatchCommand(str, Enum):
    """Commands accepted inside batch files."""

    ADD = "ADD"
    USE = "USE"


class QuestLogManager:
    """Central controller that loads data, runs commands, and saves state."""

    def __init__(self, config_path: str = "config.json"):
        self.config_path = config_path
        self.config = self._load_config(config_path)
        self.quest_file = self.config.get("quest_file", "data/quests.json")
        self.inventory_file = self.config.get("inventory_file", "data/inventory.json")
        self.report_file = self.config.get("report_file", "reports/report.txt")

        self.quests: List[Quest] = []
        self.inventory = Inventory({})

        self._ensure_paths()
        self._load_data()

    # ---------------------------
    # Setup & Persistence Helpers
    # ---------------------------
    def _load_config(self, path: str) -> Dict:
        """Load config and fail with friendly messages if file is invalid."""
        try:
            with open(path, "r", encoding="utf-8") as f:
                return json.load(f)
        except FileNotFoundError:
            raise FileNotFoundError(f"Config file not found at '{path}'. Please create config.json per README.")
        except json.JSONDecodeError as e:
            raise ValueError(f"Config file '{path}' is not valid JSON: {e}")

    def _ensure_paths(self) -> None:
        """Create data/report directories if they do not exist yet."""
        data_dir = os.path.dirname(self.inventory_file) or "data"
        reports_dir = os.path.dirname(self.report_file) or "reports"
        if data_dir and not os.path.exists(data_dir):
            os.makedirs(data_dir, exist_ok=True)
        if reports_dir and not os.path.exists(reports_dir):
            os.makedirs(reports_dir, exist_ok=True)

    def _load_data(self) -> None:
        """Load quests and inventory from disk."""
        # Load quests
        try:
            with open(self.quest_file, "r", encoding="utf-8") as f:
                quest_dicts = json.load(f)
            self.quests = [Quest.from_dict(q) for q in quest_dicts]
        except FileNotFoundError:
            self.quests = []
        except json.JSONDecodeError as e:
            raise ValueError(f"Quests file '{self.quest_file}' is not valid JSON: {e}")

        # Load inventory
        try:
            with open(self.inventory_file, "r", encoding="utf-8") as f:
                inv_items = json.load(f)
            if not isinstance(inv_items, dict):
                raise ValueError("Inventory JSON must be an object of item: quantity.")
            self.inventory = Inventory.from_dict(inv_items)
        except FileNotFoundError:
            self.inventory = Inventory({})
        except json.JSONDecodeError as e:
            raise ValueError(f"Inventory file '{self.inventory_file}' is not valid JSON: {e}")

    def save_inventory(self) -> None:
        """Persist inventory state to disk."""
        try:
            with open(self.inventory_file, "w", encoding="utf-8") as f:
                json.dump(self.inventory.to_dict(), f, indent=2)
        except FileNotFoundError:
            raise FileNotFoundError(f"Inventory file path '{self.inventory_file}' is invalid. Check config.json.")

    def _save_inventory(self) -> None:
        self.save_inventory()

    # ---------------------------
    # Quest and Inventory Logic
    # ---------------------------
    def list_quests(self) -> List[str]:
        """Return quest names in stored order."""
        return [q.name for q in self.quests]

    def find_quest(self, quest_name: str) -> Quest:
        """Find quest by name, case-insensitive."""
        for q in self.quests:
            if q.name.lower() == quest_name.lower():
                return q
        raise ValueError(f"Quest '{quest_name}' not found.")

    def plan(self) -> List[str]:
        """Return quests that can be completed with current inventory."""
        return [q.name for q in self.quests if self._can_complete(q)]

    def _can_complete(self, quest: Quest) -> bool:
        """Check whether inventory satisfies all requirements for one quest."""
        for item, needed in quest.items.items():
            if self.inventory.get_quantity(item) < int(needed):
                return False
        return True

    def gap(self, quest_name: str) -> Dict[str, int]:
        """Return missing quantities needed to complete a quest."""
        quest = self.find_quest(quest_name)
        missing: Dict[str, int] = {}
        for item, needed in quest.items.items():
            have = self.inventory.get_quantity(item)
            if have < int(needed):
                missing[item] = int(needed) - have
        return missing

    def complete_quest(self, quest_name: str) -> None:
        """Consume required items if quest is completable; otherwise raise error."""
        quest = self.find_quest(quest_name)
        # Check pre-conditions
        if not self._can_complete(quest):
            missing = self.gap(quest_name)
            details = ", ".join([f"{k}: {v}" for k, v in missing.items()])
            raise ValueError(f"Cannot complete '{quest.name}'. Missing items -> {details if details else 'none'}")
        # Consume
        for item, qty in quest.items.items():
            self.inventory.apply(InventoryAction.USE, item, int(qty))
        self.save_inventory()

    def add_inventory(self, item_name: str, quantity: int) -> None:
        """Increase stock for an item."""
        self.inventory.apply(InventoryAction.ADD, item_name, quantity)
        self.save_inventory()

    def use_inventory(self, item_name: str, quantity: int) -> None:
        """Decrease stock for an item if enough quantity is available."""
        self.inventory.apply(InventoryAction.USE, item_name, quantity)
        self.save_inventory()

    # ---------------------------
    # Batch Processing
    # ---------------------------
    def process_batch(self, filepath: str) -> Tuple[int, int]:
        """Execute ADD/USE commands from file and write a report."""
        successes = 0
        errors = 0
        lines: List[str] = []
        try:
            with open(filepath, "r", encoding="utf-8") as f:
                lines = [line.strip() for line in f if line.strip()]
        except FileNotFoundError:
            raise FileNotFoundError(f"Batch file '{filepath}' not found.")

        report_lines: List[str] = []
        for raw in lines:
            parts = raw.split()
            if not parts:
                continue
            cmd_raw = parts[0].upper()

            try:
                cmd = BatchCommand(cmd_raw)
            except ValueError:
                report_lines.append(f"ERROR: Unknown command '{cmd_raw}'.")
                errors += 1
                continue

            try:
                if len(parts) != 3:
                    raise ValueError(f"Invalid batch command format: '{raw}'.")

                item = parts[1]
                qty = int(parts[2])

                if cmd == BatchCommand.ADD:
                    self.add_inventory(item, qty)
                else:
                    self.use_inventory(item, qty)

                report_lines.append(f"SUCCESS: {raw}")
                successes += 1
            except ValueError as ve:
                report_lines.append(f"ERROR: {ve}")
                errors += 1
            except Exception as e:
                report_lines.append(f"ERROR: {e}")
                errors += 1

        # Write report
        with open(self.report_file, "w", encoding="utf-8") as rf:
            rf.write("\n".join(report_lines))

        return successes, errors

    # ---------------------------
    # Command Router (for CLI)
    # ---------------------------
    def execute(self, argv: List[str]) -> int:
        """
        Execute a single command based on argv tokens (excluding program name).
        Returns an exit code (0 success, non-zero for handled errors).
        """
        try:
            if not argv:
                print("No command provided. See README for usage.")
                return 1

            # App mode handled outside: 'manage' is consumed by main loop
            # Here we handle quest/inventory/plan commands
            raw_group = argv[0].lower()
            try:
                group = CommandGroup(raw_group)
            except ValueError:
                print("Unknown command group. Use 'quest', 'inventory', or 'plan'.")
                return 1

            if group == CommandGroup.PLAN:
                return self._execute_plan()

            if group == CommandGroup.QUEST:
                return self._execute_quest(argv)

            if group == CommandGroup.INVENTORY:
                return self._execute_inventory(argv)

            print("Unknown command group. Use 'quest', 'inventory', or 'plan'.")
            return 1
        except FileNotFoundError as fe:
            print(str(fe))
            return 1
        except ValueError as ve:
            print(str(ve))
            return 1
        except Exception as e:
            # Catch-all to ensure app doesn't crash unexpectedly
            print(f"An unexpected error occurred: {e}")
            return 1

    def _execute_plan(self) -> int:
        completable = self.plan()
        print("Completable Quests:")
        for name in completable:
            print(f"- {name}")
        return 0

    def _execute_quest(self, argv: List[str]) -> int:
        if len(argv) < 2:
            print("Usage: quest [list|view|gap|complete] ...")
            return 1

        try:
            action = QuestCommand(argv[1].lower())
        except ValueError:
            print("Invalid quest command or arguments.")
            return 1

        if action == QuestCommand.LIST:
            for idx, name in enumerate(self.list_quests(), start=1):
                print(f"{idx}. {name}")
            return 0

        if len(argv) < 3:
            print("Invalid quest command or arguments.")
            return 1

        quest_name = self._join_quoted(argv[2:])

        if action == QuestCommand.VIEW:
            print(str(self.find_quest(quest_name)))
            return 0

        if action == QuestCommand.GAP:
            missing = self.gap(quest_name)
            print(f"Missing items for '{quest_name}':")
            for item, qty in missing.items():
                print(f"- {item}: {qty}")
            return 0

        if action == QuestCommand.COMPLETE:
            self.complete_quest(quest_name)
            print(f"Successfully completed '{quest_name}'. Inventory has been updated.")
            return 0

        print("Invalid quest command or arguments.")
        return 1

    def _execute_inventory(self, argv: List[str]) -> int:
        if len(argv) < 2:
            print("Usage: inventory [add|use|process] ...")
            return 1

        try:
            action = InventoryCommand(argv[1].lower())
        except ValueError:
            print("Invalid inventory command.")
            return 1

        if action == InventoryCommand.PROCESS:
            if len(argv) < 3:
                print("Usage: inventory process <filepath>")
                return 1
            self.process_batch(argv[2])
            print(f"Batch processing complete. See '{self.report_file}' for details.")
            return 0

        if len(argv) < 4:
            print(f"Usage: inventory {action.value} \"<item_name>\" <quantity>")
            return 1

        item_name = self._strip_quotes(argv[2])
        try:
            qty = int(argv[3])
        except ValueError:
            print("Quantity must be an integer.")
            return 1

        if action == InventoryCommand.ADD:
            self.add_inventory(item_name, qty)
            print(f"Successfully added {qty} of '{item_name}'.")
            return 0

        if action == InventoryCommand.USE:
            self.use_inventory(item_name, qty)
            print(f"Successfully used {qty} of '{item_name}'.")
            return 0

        print("Invalid inventory command.")
        return 1

    @staticmethod
    def _join_quoted(parts: List[str]) -> str:
        """
        Join tokens that may come in as ["\"Goblin", "Camp\""] into a clean string: Goblin Camp
        Also handles already-joined names.
        """
        joined = " ".join(parts).strip()
        return QuestLogManager._strip_quotes(joined)

    @staticmethod
    def _strip_quotes(s: str) -> str:
        s = s.strip()
        if len(s) >= 2 and ((s[0] == '"' and s[-1] == '"') or (s[0] == "'" and s[-1] == "'")):
            return s[1:-1]
        return s
