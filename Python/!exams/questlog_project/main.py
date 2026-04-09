import shlex
import sys

from questlog import QuestLogManager

PROMPT = "> "


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
                    manager._save_inventory()  # persist latest state
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
            manager._save_inventory()
        except Exception:
            pass
    return 0


def main():
    manager = QuestLogManager()

    args = sys.argv[1:]
    if not args:
        print("No command provided. See README for usage.")
        sys.exit(1)

    # Mode selection
    first = args[0].lower()
    if first == "manage":
        code = run_interactive(manager)
        sys.exit(code)
    else:
        # Single command mode: pass through to manager
        exit_code = manager.execute(args)
        sys.exit(exit_code)


if __name__ == "__main__":
    main()
