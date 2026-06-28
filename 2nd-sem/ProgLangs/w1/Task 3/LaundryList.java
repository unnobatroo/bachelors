/*  Based on: InputWithIO{4,5}. The main program LaundryList is parameterised
    so that words form a washing list. For example: 3, trousers, 6, pants,
    9, T-shirts, 100 neckties. Simply pass the argument to the following methods. */

import java.util.Set;
import java.util.HashSet;

void main() {
    Map<String, Integer> clothesList = new HashMap<>();
    Set<String> duplicates = new HashSet<>();

    String things[] = IO.readln("Ex.: 3, trousers, 6, pants, 9, T-shirts...\n").split("[,\\s]+");

    hasAllGoodNames(things, clothesList, duplicates);

    IO.println("Total items: " + getItemCount(clothesList));
    IO.println("Total count of jeans: " + getCount("jeans", clothesList));

    if (hasDuplicate(duplicates)) {
        IO.println("Duplicates detected!");
    }
}

/*
 * hasAllGoodNames – check whether the input is valid: each name must be
 * non-empty. There is a built-in method specialised for this check. Write
 * the solution both with and without using it.
 */
void hasAllGoodNames(String things[], Map<String, Integer> clothesList, Set<String> duplicates) {
    Set<String> seen = new HashSet<>();
    try {
        for (int i = 1; i < things.length; i += 2) {
            String clothingName = things[i];

            if (clothingName == null || clothingName.trim().isEmpty()) {
                IO.println("Error: Empty clothing name found!");
                continue;
            }

            if (!seen.add(clothingName)) {
                duplicates.add(clothingName);
            }
            
            int count = Integer.parseInt(things[i - 1]);
            clothesList.merge(clothingName, count, Integer::sum);
        }
    } catch (NumberFormatException e) {
        IO.println("Not a number!");
    } catch (IllegalArgumentException e) {
        IO.println(e.getMessage());
    }
}

/*
 * getItemCount – return how many different clothing items are listed in
 * total. (In the example: 4).
 */
int getItemCount(Map<String, Integer> clothesList) {
    return clothesList.size();
}

/*
 * getKindCount – return how many kinds of clothing items appear in the
 * description. (In the example: 4). When counting kinds, do not include
 * duplicates – only count each clothing type once.
 */
int getKindCount(Map<String, Integer> clothesList) {
    return getItemCount(clothesList);
}

/*
 * getCount – receives the name of a clothing type as its first parameter.
 * Return the total number of that type in the laundry list. How should
 * the code be written if the clothing type appears at most once? How should
 * the code be written if the clothing type may appear multiple times?
 */

int getCount(String clothingType, Map<String, Integer> clothesList) {
    return clothesList.getOrDefault(clothingType, 0);
}

/*
 * hasDuplicate – check whether any clothing type appears more than once.
 */
boolean hasDuplicate(Set<String> duplicates) {
    return !duplicates.isEmpty();
}