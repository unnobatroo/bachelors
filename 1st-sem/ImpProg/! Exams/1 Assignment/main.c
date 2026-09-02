#include "dice.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

// TODO: define the struct called Armor with the following fields
// - id: character array of 20
// - armor_class: an integer
struct Armor {
  char id[21];
  int armor_class;
};

// TODO: define the struct called AttackType with the following fields
// - id: character array of 20
// - dice_count: should be an integer
// - dice_type: use the DiceType enum from dice.h
// - damage_bonus: an other integer
struct AttackType {
  char id[21];
  int dice_count;
  DiceType dice_type;
  int damage_bonus;
};

// TODO: a struct called Creature with the following fields
// - id: character array of 20
// - hp: an integer holding the health points of the creature
// - attack_bonus: an other integer
// - armor: a field with type Armor
// - attack_type: a field with type AttackType
struct Creature {
  char id[21];
  int hp;
  int attack_bonus;
  struct Armor armor;
  struct AttackType attack_type;
};

// convert string to dicetype
DiceType parse_dice_enum(char *str) {
  if (strcmp(str, "D4") == 0)
    return D4;
  if (strcmp(str, "D6") == 0)
    return D6;
  if (strcmp(str, "D8") == 0)
    return D8;
  if (strcmp(str, "D10") == 0)
    return D10;
  if (strcmp(str, "D12") == 0)
    return D12;
  if (strcmp(str, "D20") == 0)
    return D20;
  if (strcmp(str, "D100") == 0)
    return D100;
  return D4;
}

// TODO: implement the following function that takes a file handle and parses
// input in the format <id> <armor_class>. It should use the fscanf function for
// parsing and return an Armor. For example:
//
//   "leather 11"
//
// means a leather armor (id) with armor class 11. The id of the armor doesn't
// have whitespace characters in it.
struct Armor parse_armor(FILE *fp) {
  struct Armor a;
  fscanf(fp, "%20s %d", a.id, &a.armor_class);
  return a;
}

// TODO: implement the following function that takes a file handle and parses
// the input in <id> <dice_count> <dice_type> <bonus> format. It should use the
// fscanf function for parsing and return an AttackType. For example:
//
//   "dagger 1 D4 2"
//
// means a dagger (id) with damage defined as 1d4+2. The id of the attack type
// doesn't have whitespace characters in it.
struct AttackType parse_attack_type(FILE *fp) {
  struct AttackType at;
  char dice_str[10];
  fscanf(fp, "%20s %d %9s %d", at.id, &at.dice_count, dice_str,
         &at.damage_bonus);
  at.dice_type = parse_dice_enum(dice_str);
  return at;
}

// TODO: implement the following function that takes a file handle and parses
// input in the format of <id> <hp> <attack_bonus> <armor_id> <attack_type_id>.
//
// The function should use fscanf to parse the line and return a Creature.
// For example the line
//
//   "Hero   30 6 leather    dagger"
//
// translates to a creature with id 'Hero' having 30 hp, and an attack bonus
// of 6. He is equipped with leather armor and a dagger. You can suppose that an
// armor with the id 'leather' is provided in the armors array, as well as an
// attack type with the id 'dagger'. You need to look them up by their ids; you
// can use the `strcmp` function for that. The id of the creature doesn't
// contain whitespace characters.
struct Creature parse_creature(FILE *fp, struct Armor armors[], int armor_count,
                               struct AttackType attack_types[],
                               int attack_type_count) {
  struct Creature c;
  char armor_id[21];
  char attack_type_id[21];

  fscanf(fp, "%20s %d %d %20s %20s", c.id, &c.hp, &c.attack_bonus, armor_id,
         attack_type_id);

  // search armors
  for (int i = 0; i < armor_count; i++) {
    if (strcmp(armors[i].id, armor_id) == 0) {
      c.armor = armors[i];
      break;
    }
  }

  // search attacktypes
  for (int i = 0; i < attack_type_count; i++) {
    if (strcmp(attack_types[i].id, attack_type_id) == 0) {
      c.attack_type = attack_types[i];
      break;
    }
  }

  return c;
}

// TODO: using the `roll` function from dice.h implement the fight between
// two creatures as follows.
// - The fight is turn-based. c1 attacks first, then c2, and they
//   alternate until one of them is reduced to 0 hit points or below.
// - On its turn, the attacker makes an attack roll using a single D20 dice and
//   its own attack bonus. (Use the provided roll function).
// - The attack hits if the attack roll is greater than or equal to the
//   defender's armor class.
// - On a hit, damage is computed from the attacker's AttackType using the
//   dice rolling function again, but this time you 'roll' with the values
//   stored in the AttackType (dice type, count and damage_bonus).
//   The defender's hp is then reduced by this damage value.
// - If, after taking damage, a creature's hp reaches 0 the fight ends
// immediately
//   and the attacking creature is considered the winner.
// - The function returns the pointer to the winner.
struct Creature *fight(struct Creature *c1, struct Creature *c2) {
  struct Creature *attacker = c1;
  struct Creature *defender = c2;

  while (1) {
    int attack_roll = roll(D20, 1, attacker->attack_bonus);

    if (attack_roll >= defender->armor.armor_class) {
      int damage = roll(attacker->attack_type.dice_type,
                        attacker->attack_type.dice_count,
                        attacker->attack_type.damage_bonus);

      defender->hp -= damage;

      if (defender->hp <= 0) {
        return attacker;
      }
    }

    struct Creature *temp = attacker;
    attacker = defender;
    defender = temp;
  }
}
// TODO: main should take a filename argument, open the file, parse
// its contents and simulate the fights. Print out the first character
// of the winner of each fight.
int main(int argc, char *argv[]) {
  if (argc < 2) {
    printf("Usage: %s <filename>\n", argv[0]);
    return 1;
  }

  FILE *fp = fopen(argv[1], "r");
  if (!fp) {
    perror("Error opening file");
    return 1;
  }

  // parse armors
  int armor_count;
  if (fscanf(fp, "%d", &armor_count) != 1)
    return 1;
  struct Armor *armors = malloc(sizeof(struct Armor) * armor_count);
  for (int i = 0; i < armor_count; i++) {
    armors[i] = parse_armor(fp);
  }

  // parse attacktypes
  int attack_type_count;
  if (fscanf(fp, "%d", &attack_type_count) != 1)
    return 1;
  struct AttackType *attack_types =
      malloc(sizeof(struct AttackType) * attack_type_count);
  for (int i = 0; i < attack_type_count; i++) {
    attack_types[i] = parse_attack_type(fp);
  }

  // parse creatures
  int creature_count;
  if (fscanf(fp, "%d", &creature_count) != 1)
    return 1;
  struct Creature *creatures = malloc(sizeof(struct Creature) * creature_count);
  for (int i = 0; i < creature_count; i++) {
    creatures[i] = parse_creature(fp, armors, armor_count, attack_types,
                                  attack_type_count);
  }

  // simulate fights
  for (int i = 0; i < creature_count; i += 2) {
    if (i + 1 >= creature_count)
      break;

    struct Creature *winner = fight(&creatures[i], &creatures[i + 1]);
    printf("%c", winner->id[0]);
  }
  printf("\n");

  // FREE MALLOCS
  free(armors);
  free(attack_types);
  free(creatures);
  fclose(fp);

  return 0;
}