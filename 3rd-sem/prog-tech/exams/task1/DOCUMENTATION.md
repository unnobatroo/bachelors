# Capitaly Simulation

## Task

A simplified Capitaly board game on a cyclic track of fields. Three field types:

- **Property** — if unowned, the player may buy it for 1000. If the player already owns it and it has no house, they may build one for 4000. If another player owns it, the visitor pays rent: 500 without a house, 2000 with one.
- **Service** — the player pays a fixed fee to the bank.
- **Lucky** — the player receives a fixed reward from the bank.

Every player starts at position 0 with 10000. Each round, every living player rolls the dice and moves forward. What a player buys depends on its strategy:

- **Greedy** — buys and builds whenever it can afford it.
- **Careful** — buys or builds only if the cost is at most half of its current balance.
- **Tactical** — skips every second opportunity it could afford.

A player who cannot pay a fee or rent goes bankrupt: their properties become unowned again (houses demolished) and they stop playing. The program runs a given number of rounds, then prints each player's strategy, status, balance, position, and properties.

## Design

OOP principles:
- **Encapsulation** — all state is private; other classes go through methods (`pay`, `receiveMoney`, `eliminate`), and `getProperties()` returns an unmodifiable list.
- **Abstraction** — `Field` and `Player` are abstract; `Dice` is an interface.
- **Inheritance** — `PropertyField`/`ServiceField`/`LuckyField` extend `Field`; `GreedyPlayer`/`CarefulPlayer`/`TacticalPlayer` extend `Player`.
- **Polymorphism** — the game iterates over `Field` and `Player` without knowing concrete types; `apply()` and `wantsToSpend()` dispatch to the right implementation. Each player subclass implements its own buying rule.

Patterns:
- **Factory** — `FieldFactory` and `PlayerFactory` turn input tokens into objects and reject unknown types, keeping construction out of `GameLoader`.
- `Dice` is a functional interface: `Dice.random()` rolls 1–6; `Dice.fromList(...)` cycles a fixed sequence so games can be reproduced and tested.

`GameLoader` reads and validates the input file (fields, players, optional dice rolls) and throws `InvalidInputException` on malformed data.

## Class diagram

```mermaid
classDiagram
    class Dice {
        <<interface>>
        +roll() int
        +random()$ Dice
        +fromList(List~Integer~)$ Dice
    }
    note for Dice "random() rolls 1-6; fromList() repeats a fixed sequence so games are reproducible"

    class Field {
        <<abstract>>
        -int id
        +apply(Player)*
    }
    class PropertyField {
        -Player owner
        -boolean hasHouse
        +reset() void
        +describe() String
    }
    class ServiceField {
        -int cost
    }
    class LuckyField {
        -int reward
    }

    class Player {
        <<abstract>>
        -String name
        -String strategyName
        -int money
        -int position
        -boolean alive
        -List~PropertyField~ properties
        +move(int, int) void
        +pay(int) void
        +receiveMoney(int) void
        +eliminate() void
        +wantsToBuyProperty(int) boolean
        +wantsToBuildHouse(int) boolean
        #wantsToSpend(int)* boolean
    }
    class GreedyPlayer
    class CarefulPlayer
    class TacticalPlayer {
        -boolean skipNextChance
    }

    class CapitalyGame {
        -List~Field~ fields
        -List~Player~ players
        -Dice dice
        +playRound() void
        +playRounds(int) void
        +printPlayersState() void
    }
    note for CapitalyGame "each round: every living player rolls, moves, lands; stops early if <=1 player remains"

    class GameLoader {
        +loadFromFile(String)$ GameData
    }
    class FieldFactory {
        +createField(int, String, Scanner)$ Field
    }
    class PlayerFactory {
        +createPlayer(String, String)$ Player
    }
    class InvalidInputException {
        +InvalidInputException(String)
    }
    class Main {
        +main(String[])$ void
    }
    class TestRunner {
        -int passed$
        -int failed$
        +main(String[])$ void
        -assertTrue(String, boolean)$ void
        -assertEquals(String, Object, Object)$ void
        -testGreedyStrategy()$ void
        -testCarefulStrategy()$ void
        -testTacticalStrategy()$ void
        -testFactories()$ void
        -testLuckyField()$ void
        -testServiceField()$ void
        -testPropertyFieldPurchaseAndRent()$ void
        -testPropertyFieldHouseAndElimination()$ void
        -testCyclicMovement()$ void
        -testGameLoaderValid()$ void
        -testGameLoaderInvalid()$ void
        -testFullSimulationScenario()$ void
    }
    note for TestRunner "46 white-box and black-box checks: player strategies, factories, field mechanics, bankruptcy, file loading, and simulation"

    Field <|-- PropertyField
    Field <|-- ServiceField
    Field <|-- LuckyField
    Player <|-- GreedyPlayer
    Player <|-- CarefulPlayer
    Player <|-- TacticalPlayer
    Player o-- PropertyField
    PropertyField --> Player : owner
    CapitalyGame o-- Field
    CapitalyGame o-- Player
    CapitalyGame o-- Dice
    GameLoader ..> FieldFactory
    GameLoader ..> PlayerFactory
    GameLoader ..> InvalidInputException : throws
    Main ..> GameLoader : loads
    Main ..> CapitalyGame : runs
    Main ..> Dice : creates
    TestRunner ..> CapitalyGame : tests
    TestRunner ..> GameLoader : tests
    TestRunner ..> Player : tests
    TestRunner ..> Field : tests
    TestRunner ..> InvalidInputException : tests
```

## State machine diagram

### Property field lifecycle

```mermaid
stateDiagram-v2
    [*] --> Unowned: Initial board setup

    Unowned --> OwnedWithoutHouse: Player buys property for 1000
    OwnedWithoutHouse --> OwnedWithHouse: Owner builds house for 4000

    OwnedWithoutHouse --> Unowned: Owner bankrupt (properties reset)
    OwnedWithHouse --> Unowned: Owner bankrupt (house demolished & reset)

    OwnedWithoutHouse --> OwnedWithoutHouse: Opponent visits (pays 500 rent)
    OwnedWithHouse --> OwnedWithHouse: Opponent visits (pays 2000 rent)
```

## Sequence diagram

The following sequence illustrates a single simulation round in `CapitalyGame`, highlighting movement and polymorphic interaction with fields:

```mermaid
sequenceDiagram
    autonumber
    actor User
    participant Main
    participant GameLoader
    participant CapitalyGame
    participant Player
    participant Dice
    participant Field as PropertyField / ServiceField / LuckyField

    User->>Main: run(filePath, rounds)
    Main->>GameLoader: loadFromFile(filePath)
    GameLoader-->>Main: GameData(fields, players, diceRolls)
    Main->>CapitalyGame: playRounds(totalRounds)

    loop Each Round (while living players > 1)
        loop Each Player
            CapitalyGame->>Player: isAlive()
            alt Player is active
                CapitalyGame->>Dice: roll()
                Dice-->>CapitalyGame: steps
                CapitalyGame->>Player: move(steps, boardSize)
                Player-->>CapitalyGame: newPosition

                CapitalyGame->>Field: apply(player)

                alt LuckyField
                    Field->>Player: receiveMoney(reward)
                else ServiceField
                    Field->>Player: pay(cost)
                    opt Cannot afford fee
                        Player->>Player: eliminate()
                    end
                else PropertyField (Unowned)
                    Field->>Player: wantsToBuyProperty(1000)
                    opt Player chooses to buy
                        Player->>Player: pay(1000)
                        Player->>Field: setOwner(player)
                    end
                else PropertyField (Owned by visitor)
                    opt No house
                        Field->>Player: wantsToBuildHouse(4000)
                        opt Player chooses to build
                            Player->>Player: pay(4000)
                            Player->>Field: setHasHouse(true)
                        end
                    end
                else PropertyField (Owned by opponent)
                    Field->>Player: pay(rent)
                    alt Can afford rent
                        Field->>Player: owner.receiveMoney(rent)
                    else Cannot afford rent
                        Player->>Field: owner.receiveMoney(remainingMoney)
                        Player->>Player: eliminate()
                    end
                end
            end
        end
    end

    CapitalyGame->>Main: printPlayersState()
```

## Testing

`TestRunner` contains 46 checks and exits non-zero on failure.

White-box:
- Each player type's buying rules at different balances, including tactical's alternating skip.
- Factory creation of every field type and player type, including argument parsing.
- Property mechanics: buying, rent payment, house building, upgraded rent, and bankruptcy (remaining balance goes to the owner, properties reset).
- Service fee deduction and bankruptcy on an unaffordable fee; lucky field payout.

Black-box:
- Board wrap-around via modulo movement.
- File loading: a valid file, a missing file (`FileNotFoundException`), and an unknown field type (`InvalidInputException`).
- A full multi-round game on `sample_bankruptcy.txt` with fixed dice, checking final balances.

## Build and Run

All compilation and execution is handled via `run.sh`:

```bash
./run.sh                          # Compiles & runs default game (sample_game.txt, 5 rounds)
./run.sh <file_path> <rounds>     # Compiles & runs custom input file and rounds
./run.sh test                     # Compiles & runs the test suite
./run.sh build                    # Compiles without running
./run.sh clean                    # Cleans the compiled classes (bin/)
```
