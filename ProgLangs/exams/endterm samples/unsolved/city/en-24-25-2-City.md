# City
The task is to create a simplified city simulation program using the following subtasks. The functional tests should be included in the `tests` package.

## `Citizen` class and its auxiliary classes (1 + 2 + 8 = 11 points)
The `Person` interface has only one method, which will be implemented in later classes.
The type `Expertise` specifies the different types of expertise of a citizen.
The constructor of the class `Citizen` initializes the data members `name`, `expertise` and `knowledge`.
- The `work` method is overridden, here just increment `knowledge` by 1.
- The `getExpertiseModifier` method returns expertise modifier values. Return 2 for `INTERMEDIATE`, 3 for `EXPERT`, 5 for `MASTER`, and 0 otherwise.
In addition, the class `Citizen` shall not be instantiatable.

## Classes `Chef`, `Artist` and `Scientist` (5 + 6 + 7 = 18 points)
All three classes must be descendants of the Citizen class.

### The 'Chef' class (5 points)
The chef has only one new integer type data member, `cookedFoodCount`, this should not be visible from the outside.
The class constructor initializes the data members appropriately, `cookedFoodCount` should initially be 0.
- The `work` method should be redefined and should work as follows:
    1. increment `knowledge` by 1.
    2. check that the chef's expertise is not `STUDENT`. If not, increment the value of the `cookedFoodCount` data member by the return value of the `getExpertiseModifier` method.
    3. Otherwise, do not do anything with the `cookedFoodCount` data member.
- The textual representation of the class gives the name of the chef, his/her knowledge and how much food he/she has already prepared. Example texts for chef:\
    Chef Example; expertise: STUDENT; knowledge: 0; cooked food count: 0\
    Chef Example; expertise: INTERMEDIATE; knowledge: 1; cooked food count: 2

### The `Artist` class (6 points)
The artist should have a class-level data member of integer type named `WORK_TO_FINISH_ART`, which tells how long it will take to complete an artwork. Its value should be 3.
In addition, there should be two new integer type data members, `popularity` and `artProgress`, both not externally visible.
The constructor of the class initializes the data members appropriately, with `popularity` and `artProgress` initially set to 0.
- The `isArtFinished` method should return whether the artwork is finished. To do this, use the `WORK_TO_FINISH_ART` and `artProgress` data members.
- The `work` method should be redefined to work as follows:
    1. increment `knowledge` and `artProgress` by 1.
    2. check if the artwork is completed or if the artist's expertise is not `STUDENT`. If both conditions are true, increment the value of `popularity` by the return value of the `getExpertiseModifier` method, and then reset `artProgress` to 0.
    3. If the artwork is completed but the artist's expertise is `STUDENT`, then just reset `artProgress` to 0.
- The text representation of the class gives the artist's name, skill, reputation and whether an artwork is in progress. The expected texts for an artist are:\
    Artist Example; expertise: STUDENT; knowledge: 0; popularity: 0; creation of an art piece isn't in progress\
    Artist Example; expertise: EXPERT; knowledge: 1; popularity: 0; creation of an art piece is in progress\
    Artist Example; expertise: MASTER; knowledge: 3; popularity: 5; creation of an art piece isn't in progress

### The `Scientist` class (7 points)
The scientist has only one new data member, `inventionsList`, which should also not be visible from the outside.
The class constructor initializes the data members appropriately.
- The `generateInvention` which generates a new invention. The identifier will be text for simplicity, consisting of the word `Invention` and a number incremented by one relative to the current size of the list `inventionsList`.
- The `work` method will be redefined to work as follows:
    1. increment `knowledge` by 1.
    2. check that the expertise of the scientist is not `STUDENT`. If not, generate `getExpertiseModifier` method return value set of inventions and add these new inventions to the `inventionsList` list.
    3. Otherwise, do nothing with `inventionsList`.
- The text representation of the class specifies the name of the scientist, his/her knowledge, and that he/she has already made an invention. Example texts for a scientist:\
    Scientist Example; expertise: STUDENT; knowledge: 0; amount of inventions made: 0\
    Scientist Example; expertise: EXPERT; knowledge: 1; amount of inventions made: 3

## Testing of `Chef`, `Artist` and `Scientist` classes (2 + 2 + 2 = 6 points)
For all three classes, the correctness of the textual representation should be checked. To do this, two tests must be written for each of the three classes, one testing a `STUDENT` proficiency and one not so proficient. For the latter test, you can choose an arbitrary level of expertise.

## Class `City` and its subclasses (9 + 1 = 10 points)
The `EmptyCitizensListException` is a simple exception type.
The constructor of the `City` class initializes only the `citizens` and `cityResources` data members. The latter should have a value of 0 assigned to the keys `cookedFoodCount`, `knowledge`, `popularity` and `inventionCount`.
- The `addCitizen` method stores the citizen specified in the parameter in the `citizens` data member.
- The `getAllResources` method iterates through all citizens and retrieves their given data and knowledge according to their type. At the end of the method, the resulting totals are stored in the `cityResources` data member, where the corresponding amount is stored for each resource type.
(Hint: There is a specific keyword for type checking)
- The operation of the `workAll` method is as follows:
    1. if there are no citizens in the city, throw our `EmptyCitizensListException`.
    2. otherwise, run the method over all citizens and call the `work` method on them.
    3. at the very end, call the `getAllResources` method.
The `City` class should also support an equality check, whereby two cities are equal if their `cityResources` values are equal.

## Testing the `City` class (2 + 3 = 5 points)
We need to create two test cases: one to check the exception handling of the `workAll` method, and the other to test the correctness of the equality test.
The latter test case should do the following:
1. prepare two separate cities.
2. assign one of each of the three citizen types to each city, with the same expertise. (Be careful of the peculiarities of the reference type pass here!)
3. call the `workAll` method on one of the cities.
4. Test the equality.
5. Then call the `workAll` method on the other city.
6. And check the equality again.