# Conditions for the final exam

No aids or software tools (e.g. brought in) other than those provided by the instructors may be used during the final.

- ✅📃 The Java API reference documentation may be used when writing the programming assignment.
- ❌💻 Use of your own computer is not allowed.
- ✅🪄 Use of IDE-like smart code completion functions is not allowed. If you use e.g. VS Code, check and disable such extensions.
- ✅📜 In addition, blank paper and pen may be used when writing the theory question paper.
- ⚠️ All forms of communication are prohibited. Even the suspicion of it should be avoided.

**Exception**: supervising instructors may be consulted. If there is any question about the rules or the interpretation of the assignment, it is better to ask them immediately than to explain afterwards because of a possible misunderstanding.

### Some examples of what can be done to break the previous rules:
- ❌✒️ Initiating arbitrary messaging on any device/platform.
- ❌🤐 Exchange ideas with other listeners.
- ❌📱 Using a mobile phone, tablet, smartwatch or other similar device for any purpose (e.g. not as a watch). These must be completely removed when the ZH starts.
- ❌🤖 Use of artificial intelligence.
- ❌💣 Preparing software or hardware for cheating purposes.
- 🚾 Restroom use must be reported to the supervising instructors and only one person may be in the restroom at a time.

# Museum (50 points)

The task is to create a simplified museum simulation program, with the help of the following subtasks.

## 1. Base Components: RelicType, Relic (14 points)

### `Relic` class and its supporting classes (1 + 9 = 10 points)

The `RelicType` class defines the possible types of a relic.  
The constructor in the `Relic` class should initialize the `relicName` and `relicType` fields.  
The `Relic` class should also support an equality check, whereby two relics are considered equal if both of the the above-mentioned fields are equal.

### Testing the `Relic` class (1 + 3 = 4 points)

- `relicConstructorTest` tests that the class's constructor initializes its fields properly.
- `relicEqualityCheckTest` is a ParameterizedTest that should test the equality check of the `Relic` class.

## 2. Tourists: VisitingException, Visitor, Tourist (17 points)

### `Tourist` class and its supporting classes (2 + 3 + 5 = 10 points)

The `VisitingException` is a simple exception type.  
The only method in the `Visitor` interface will be implemented in the `Tourist` class.  
The constructor in the `Tourist` class should initialize the `touristName`, `favouriteRelicType` and `visitedRelics` fields. `visitedRelics` should initially be empty. 

- The `getVisitedRelicsCount` method should return the number of relics the tourist has visited.  
- The `visitRelic` method should work as follows:  
    - If the parameter `Relic`'s type is not equal to the `favouriteRelicType` field, OR the relic has already been visited by the tourist, then the method should throw a `VisitingException`.  
    - Otherwise, the relic should be added to the `visitedRelics` list.  
- The class's textual representation should provide the tourist's name and list all the names of the visited relics, separated by commas.  
    *Example text, if no relics have been visited:*  
    Tourist Example hasn't visited any relics yet.  
    *Example text, if one relic has been visited:*  
    Tourist Example visited the following relic(s): Example Relic  
    *Example text, if more than one relic has been visited:*  
    Tourist Example visited the following relic(s): Example Relic, Example Relic2

### Testing the `Tourist` class (1 + 2 + 2 + 2 = 7 points)

- `visitRelicTest` tests whether a `Relic` is stored after using the `visitRelic` method.  
- `visitRelicInvalidRelicTypeTest` tests whether the proper exception is thrown if a tourist tried to visit a relic that's not their favourite type of relic.  
- `visitRelicDuplicateVisitTest` tests whether the proper exception is thrown if a tourist tried to visit a relic they've already visited.  
- `textualRepresentationTest` tests whether the textual representation is correct for 0, 1 and more than one visited relic.  

### 3. Museum (19 points)

### `Museum` class (9 points)

The museum should have a class-level data member of integer type named `TICKET_PRICE` that indicates the price of a ticket. Its value should be 50.  
The constructor in the `Museum` class should initialize the `visitingTourists` and `museumData` fields. The latter should be initilized with the keys "income" and "popularity", both set to 0.

- The `calculateMuseumData` method should calculate and store in the `museumData` field the new "income" and "popularity" values as follows:
    - The "income" should be the count of the tourists multiplied by the `TICKET_PRICE`.
    - The "popularity" should be the count of all the relics visited by the tourists. Relics that occur multiple times are counted multiple times (so there is no need to check for duplicates).
- The `addVisitingTourist` method should add the tourist received as a parameter to the appropriate field, then recalculate data of the museum using the `calculateMuseumData` method.  
- In the `allTouristsVisitRelic` method, only those tourists should visit the relic received as a parameter whose favourite relic type matches the relic's type.  
 Then recalculate data of the museum using the `calculateMuseumData` method.  
- The `getMuseumIncome` and `getMuseumPopularity` methods should return the value in the `museumData` field using the appropriate key.  

### Testing the `Museum` class (5 + 5 = 10 points)

- The testing of the `museumIncomeTest` method should be as follows:
    1. Create an instance of a `Museum`, then create an instance of a `Tourist` with parameters of your choice.
    2. Check whether the museum's income is 0.
    3. Add your created tourist to the museum.
    4. Check the museum's income again.
- The testing of the `museumPopularityTest` method should be as follows:
    1. Create an instance of a `Museum`, then create an instance of a `Tourist` with parameters of your choice. After that, create two `Relic` instances: one whose type matches the created tourist's favourite relic type and one whose type does not.
    2. Check whether the museum's popularity is 0.
    3. Add your created tourist to the museum.
    4. Check the museum's popularity again.
    5. Now use the appropriate method with one of the created relics.
    6. Check the museum's popularity once again.
    7. Now use the appropriate method with the other created relics.
    8. And check the museum's popularity one last time.