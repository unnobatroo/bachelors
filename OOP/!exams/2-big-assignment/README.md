# 🛡️ Adventurer’s Guild

## 1. Description of the Exercise
In a world of adventure, a guild recruits adventurers to take on dangerous quests. Each adventurer arms themselves with weapons and faces formidable monsters to prove their strength. The guild manages members and a quest board, assigning adventurers to sequentially battle monsters.

Key features include:
* **Weapon Management**: Weapons (Swords, Bows, Staffs) have power boosts and durability.
* **State Lifecycle**: Weapons transition through **New**, **Used**, **Damaged**, and **Broken** states based on durability, affecting performance.
* **Combat Mechanics**: Damage is calculated using base power, rank, and weapon strength.
* **Progression**: Adventurers gain experience from defeated monsters, increasing their rank and power.
* **Repair Services**: Weapons can be restored to the New state by spending experience points.

## 2. System Diagrams

### 2.1 Use Case Diagram
The Use Case diagram illustrates the interactions between the **Adventurer** (Player), the **Guild Master** (System/Simulation), and the core functionalities like attempting quests, repairing weapons, and managing inventory.

### 2.2 Class Diagram
The system architecture follows an object-oriented design:
* **Abstract Weapon**: Parent to `Sword`, `Bow`, and `Staff`.
* **Entities**: `Adventurer`, `Monster`, `Guild`, and `Quest`.
* **Enum**: `Condition` (State) manages the 100%, 75%, and 0% power multipliers.

### 2.3 State Diagram: Weapon Lifecycle
This diagram tracks the transitions of a weapon:
1.  **New**: 100% durability, includes a 10% first-use bonus.
2.  **Used**: 50–99% durability, standard performance.
3.  **Damaged**: 1–49% durability, 75% power boost.
4.  **Broken**: 0% durability, unusable.
5.  **Repair**: A transition from any state back to **New** via experience expenditure.

## 3. Scenarios (Sequence Diagrams)

### 3.1 Scenario 1: Successful Quest Completion
This sequence models an adventurer equipping their strongest weapon, engaging in turn-based combat with a monster, dealing damage based on monster armor, gaining experience, and ranking up after the final victory.

### 3.2 Scenario 2: Equipment Failure
This sequence demonstrates the mid-battle degradation of a weapon. As durability falls below 50% (Damaged) or reaches 0 (Broken), the power boost drops, potentially causing the adventurer to fail the quest if they cannot overcome the monster's armor.

## 4. Implementation Details

### 4.1 Technical Specifications
* **Language**: Java.
* **Documentation**: Javadoc comments are included for all classes and non-getter/setter methods.
* **Testing**: Unit tests (JUnit) are provided for core logic (Combat calculation, Durability reduction, Enum state transitions).
* **Input**: The simulation reads data from `adventurers.txt`, `weapons.txt`, `quests.txt`, and `monsters.txt`.

### 4.2 Probabilistic Logic
Per the assignment guidelines, the following rates were implemented:
* **Sword Critical**: 20% chance to double damage at the cost of 2× durability.
* **Staff Spell**: 25% chance to halve monster strength.
* **Repair Success**: Defined as a fixed cost of `Experience = Weapon Power * 0.5`.

## 5. How to Run
1. Ensure the `.txt` input files are in the root directory.
2. Compile the project.
3. Run the `Simulation` class to see the Guild in action.