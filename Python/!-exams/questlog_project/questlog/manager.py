import json
import os
from typing import List, Dict, Tuple

from .models import Quest, Inventory


class QuestLogManager:
    """
    Central controller that loads data, runs commands, and saves state.
    """

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
        try:
            with open(path, "r", encoding="utf-8") as f:
                return json.load(f)
        except FileNotFoundError:
            # Provide a helpful message; re-raise to make failure explicit to caller.
            raise FileNotFoundError(
                f"Config file not found at '{path}'. Please create config.json per README.")
        except json.JSONDecodeError as e:
            raise ValueError(f"Config file '{path}' is not valid JSON: {e}")

    def _ensure_paths(self) -> None:
        # Ensure directories for data and reports exist.
        data_dir = os.path.dirname(self.inventory_file) or "data"
        reports_dir = os.path.dirname(self.report_file) or "reports"
        if data_dir and not os.path.exists(data_dir):
            os.makedirs(data_dir, exist_ok=True)
        if reports_dir and not os.path.exists(reports_dir):
            os.makedirs(reports_dir, exist_ok=True)

    def _load_data(self) -> None:
        # Load quests
        try:
            with open(self.quest_file, "r", encoding="utf-8") as f:
                quest_dicts = json.load(f)
            self.quests = [Quest(q["name"], q.get("items", {})) for q in quest_dicts]
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
            self.inventory = Inventory(inv_items)
        except FileNotFoundError:
            self.inventory = Inventory({})
        except json.JSONDecodeError as e:
            raise ValueError(f"Inventory file '{self.inventory_file}' is not valid JSON: {e}")

    def _save_inventory(self) -> None:
        try:
            with open(self.inventory_file, "w", encoding="utf-8") as f:
                json.dump(self.inventory.to_dict(), f, indent=2)
        except FileNotFoundError:
            raise FileNotFoundError(
                f"Inventory file path '{self.inventory_file}' is invalid. Check config.json.")

    # ---------------------------
    # Quest and Inventory Logic
    # ---------------------------
    def list_quests(self) -> List[str]:
        return [q.name for q in self.quests]

    def find_quest(self, quest_name: str) -> Quest:
        for q in self.quests:
            if q.name.lower() == quest_name.lower():
                return q
        raise ValueError(f"Quest '{quest_name}' not found.")

    def plan(self) -> List[str]:
        completable = []
        for q in self.quests:
            if self._can_complete(q):
                completable.append(q.name)
        return completable

    def _can_complete(self, quest: Quest) -> bool:
        for item, needed in quest.items.items():
            if self.inventory.get_quantity(item) < int(needed):
                return False
        return True

    def gap(self, quest_name: str) -> Dict[str, int]:
        quest = self.find_quest(quest_name)
        missing: Dict[str, int] = {}
        for item, needed in quest.items.items():
            have = self.inventory.get_quantity(item)
            if have < int(needed):
                missing[item] = int(needed) - have
        return missing

    def complete_quest(self, quest_name: str) -> None:
        quest = self.find_quest(quest_name)
        # Check pre-conditions
        if not self._can_complete(quest):
            missing = self.gap(quest_name)
            details = ", ".join([f"{k}: {v}" for k, v in missing.items()])
            raise ValueError(
                f"Cannot complete '{quest.name}'. Missing items -> {details if details else 'none'}")
        # Consume
        for item, qty in quest.items.items():
            self.inventory.use_item(item, int(qty))
        self._save_inventory()

    def add_inventory(self, item_name: str, quantity: int) -> None:
        if quantity is None:
            raise ValueError("Quantity is required.")
        if not isinstance(quantity, int):
            raise ValueError("Quantity must be an integer.")
        if quantity < 0:
            raise ValueError("Quantity must be non-negative.")
        self.inventory.add_item(item_name, quantity)
        self._save_inventory()

    def use_inventory(self, item_name: str, quantity: int) -> None:
        if quantity is None:
            raise ValueError("Quantity is required.")
        if not isinstance(quantity, int):
            raise ValueError("Quantity must be an integer.")
        if quantity < 0:
            raise ValueError("Quantity must be non-negative.")
        self.inventory.use_item(item_name, quantity)
        self._save_inventory()

    # ---------------------------
    # Batch Processing
    # ---------------------------
    def process_batch(self, filepath: str) -> Tuple[int, int]:
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
            cmd = parts[0].upper()
            try:
                if cmd == "ADD" and len(parts) == 3:
                    item = parts[1]
                    qty = int(parts[2])
                    self.add_inventory(item, qty)
                    report_lines.append(f"SUCCESS: {raw}")
                    successes += 1
                elif cmd == "USE" and len(parts) == 3:
                    item = parts[1]
                    qty = int(parts[2])
                    self.use_inventory(item, qty)
                    report_lines.append(f"SUCCESS: {raw}")
                    successes += 1
                else:
                    report_lines.append(f"ERROR: Unknown command '{cmd}'.")
                    errors += 1
            except ValueError as ve:
                # Error like insufficient stock or invalid qty
                msg = str(ve)
                if cmd in ("ADD", "USE"):
                    # align with sample: include original raw or a clear message
                    if cmd == "USE" and "Cannot USE" in msg:
                        # keep the exact message format expected
                        report_lines.append(f"ERROR: {msg}")
                    else:
                        report_lines.append(f"ERROR: {msg}")
                else:
                    report_lines.append(f"ERROR: {msg}")
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
            group = argv[0].lower()

            if group == "plan":
                completable = self.plan()
                print("Completable Quests:")
                for name in completable:
                    print(f"- {name}")
                return 0

            if group == "quest":
                if len(argv) < 2:
                    print("Usage: quest [list|view|gap|complete] ...")
                    return 1
                action = argv[1].lower()
                if action == "list":
                    names = self.list_quests()
                    for idx, name in enumerate(names, start=1):
                        print(f"{idx}. {name}")
                    return 0
                elif action == "view" and len(argv) >= 3:
                    quest_name = self._join_quoted(argv[2:])
                    q = self.find_quest(quest_name)
                    print(str(q))
                    return 0
                elif action == "gap" and len(argv) >= 3:
                    quest_name = self._join_quoted(argv[2:])
                    missing = self.gap(quest_name)
                    print(f"Missing items for '{quest_name}':")
                    for item, qty in missing.items():
                        print(f"- {item}: {qty}")
                    return 0
                elif action == "complete" and len(argv) >= 3:
                    quest_name = self._join_quoted(argv[2:])
                    self.complete_quest(quest_name)
                    print(f"Successfully completed '{quest_name}'. Inventory has been updated.")
                    return 0
                else:
                    print("Invalid quest command or arguments.")
                    return 1

            if group == "inventory":
                if len(argv) < 2:
                    print("Usage: inventory [add|use|process] ...")
                    return 1
                action = argv[1].lower()
                if action in ("add", "use"):
                    if len(argv) < 4:
                        print(f"Usage: inventory {action} \"<item_name>\" <quantity>")
                        return 1
                    item_name = self._strip_quotes(argv[2])
                    try:
                        qty = int(argv[3])
                    except ValueError:
                        print("Quantity must be an integer.")
                        return 1
                    if action == "add":
                        self.add_inventory(item_name, qty)
                        print(f"Successfully added {qty} of '{item_name}'.")
                        return 0
                    else:
                        self.use_inventory(item_name, qty)
                        print(f"Successfully used {qty} of '{item_name}'.")
                        return 0
                elif action == "process":
                    if len(argv) < 3:
                        print("Usage: inventory process <filepath>")
                        return 1
                    filepath = argv[2]
                    self.process_batch(filepath)
                    print(f"Batch processing complete. See '{self.report_file}' for details.")
                    return 0
                else:
                    print("Invalid inventory command.")
                    return 1

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
