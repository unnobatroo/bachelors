# Dnd

In this exam, you will implement a DnD-style fighting system. You are provided with a dice library (`dice.h`) that handles dice-rolling operations. Your task is to implement the parsing logic and the fight logic.

You will work with files in the following format:

```
5
none       10
leather    11
studded    12
chainmail  16
plate      18
3
dagger     1 D4  2
longsword  1 D8  3
firebolt   1 D10 3
4
Hero     30 6 plate    longsword
Goblin   12 4 none     dagger
Mage     16 5 none     firebolt
Orc      20 5 leather  longsword
```

This consists of 3 blocks. Each block defines a list. It starts with a number indicating how many elements the list contains, followed by the elements themselves.

The **first block** defines armors. Each armor has an armor class given as an integer:

```
chainmail  16
```

This means a `chainmail` armor with an armor class of 16.

The **second block** defines attack types. Each attack type has a damage expression:

```
dagger     1 D4  2
```

For the dagger, this means `1d4 + 2`: roll one 4-sided die and add 2 to the result.

The **third block** defines creatures. Each creature has a health point (integer), an attack bonus (integer), an armor, and an attack type:

```
Hero     30 6 plate    longsword
```

Hero has 30 HP and an attack bonus of 6. He wears plate armor and fights with a longsword.

The creature list is interpreted as a list of pairs, each pair representing a fight.
In the first fight, **Hero fights Goblin**.
In the second fight, **Mage fights Orc**.

## Rules of the fight


Each fight is turn-based. In each turn there is an **attacker** and a **defender**, and the roles switch at the end of the turn.

* In the first turn, the first creature listed attacks, and the second defends.
* If the defender survives (HP > 0), they become the attacker on the next turn.

### Attack Roll

On its turn, the attacker makes an attack roll using a single d20 plus its attack bonus (`1d20 + attack_bonus`).
An attack **hits** if the result is **greater than or equal to** the defender’s armor class.

If the attack misses, roles simply switch.

### Damage

If the attack hits, damage is computed using the attacker’s attack type.
For example, Hero’s longsword deals `1d8 + 3`.

The defender’s HP is then reduced by this damage value.

If a creature’s HP reaches **0 or below**, the fight ends immediately, and the **attacker is the winner**.

## Your program

Your program should accept a filename as an argument. It should open the file, read its contents, and simulate each fight.
The program should output the **first letter of the winner** of each fight.

For example, if Hero defeats Goblin in the first fight, and Orc defeats Mage in the second fight, you should print:

```
ho
```

If you implement everything correctly, these letters will form a secret message.