import shlex
import sys
from enum import Enum

from questlog import QuestLogManager

PROMPT = "> "


class AppMode(str, Enum):
    """Top-level execution modes."""

    MANAGE = "manage"
    SINGLE_COMMAND = "single_command"


def run_interactive(manager: QuestLogManager) -> int:
    print("Entering interactive mode. Type 'exit' to quit.")
    try:
        while True:
            try:
                raw = input(PROMPT)
            except EOFError:
                # End of input stream -> exit gracefully
                break
            if raw is None:
                continue
            cmd = raw.strip()
            if not cmd:
                continue
            if cmd.lower() == "exit":
                # Save and exit
                try:
                    manager.save_inventory()  # persist latest state
                except Exception:
                    pass
                print("Inventory saved. Goodbye!")
                return 0
            # Parse with shlex to support quoted strings
            try:
                tokens = shlex.split(cmd)
            except ValueError as ve:
                print(f"Invalid input: {ve}")
                continue
            # Dispatch to manager
            manager.execute(tokens)
    except KeyboardInterrupt:
        print("\nExiting...")
        try:
            manager.save_inventory()
        except Exception:
            pass
    return 0


def _detect_mode(args: list[str]) -> AppMode:
    """Determine whether to run interactive or single-command mode."""
    if args and args[0].lower() == AppMode.MANAGE.value:
        return AppMode.MANAGE
    return AppMode.SINGLE_COMMAND


def main() -> None:
    manager = QuestLogManager()

    args = sys.argv[1:]
    if not args:
        print("No command provided. See README for usage.")
        sys.exit(1)

    mode = _detect_mode(args)

    if mode == AppMode.MANAGE:
        code = run_interactive(manager)
        sys.exit(code)

    # Single command mode: pass through to manager.
    exit_code = manager.execute(args)
    sys.exit(exit_code)


if __name__ == "__main__":
    main()
