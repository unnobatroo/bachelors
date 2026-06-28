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

---

# Assignment: astronaut simulation (50 points)

This is a **fictional** scenario inspired by space exploration, but **not** modelling real space missions. If you are familiar with space exploration, please note that the program is intentionally different from reality.

The task is to implement Java classes and interfaces to model and collect various resources and debris, and to simulate the activities of astronauts.

The fields of each class have getters that return the value of those fields, **unless otherwise specified** in the assignment.

---

## 1. raw materials: `Resource`, `Ore`, `Debris` (15 + 3*2 + 4T points)

### The class `Resource`

The **`Resource`** class is an **abstract** template representing resources and implementing the **`Tagged`** interface. For the class to work, the **`Type`** enumeration (`METAL`, `CRYSTAL`, `GAS`) will be required.

1. **Instructor**:  
   - Have exactly one constructor that expects the **`position`** (`String`) and **`type`** (`Type`) parameters.  
   - Set the values of the corresponding fields.  
   - If **`position`** is `zero` or less than 4 characters, throw **`IllegalArgumentException`** (empty or with arbitrary message).  
   - All `Resources` are initially stable (**`isStable = true`**) and untagged (**`tagged = false`**).  

2. **Methods**:  
   - **`toString()`** returns text of the following form: 
 ```
 Position: <position value>, Type: <type value>, isStable: <isStable value>
 ``` 
 Example: `Position: P12-4X, Type: METAL, isStable: true`
   - For the **`equals()`** method, two `Resources` must be equal if:
     - They have the same **`isStable`** state, **and**  
     - **`position`** has the same first four characters, **and**  
     - **`type`** are the same.  
     **`hashCode()`** must be consistent with **`equals()`**.  
   - **`extendPosition(String extension)`**: abstract method that extends the position (the exact implementation is left to subclasses).  
   - **`collect()`**: also an abstract method describing the collection of the raw material (the concrete implementation is in the subclasses).  

### The ‘Ore’ and ‘Debris’ subdivisions

#### `Ore`
- In the constructor (after calling parent) **`isStable = false`** should be the default value (so the ore is initially not stable).  
- **`extendPosition()`** simply appends the resulting `extension` string to the end of the existing `position`.  
- The logic of the **`collect()`** method:  
  - If the ore is **not** stable **and** not tagged, return `null` (cannot be collected).  
  - If it is stable or tagged (or both), the position (`position`) is completed at the end of the collection:
    - **`#FOUND`** ending if `(position.length() + type.name().length())` is e.g. between **8 and 12** (pre-decision on which interval to look for).  
    - **`#REJECT`** ending in all other cases.  
  - The return value should be the modified position. If failed, then `null`.  

- For the **`tag()`** method:  
  - On tagging (`tagged = true`), the stability is also changed (if `false` so far, `true`, and vice versa).  
  - Each retagging should also change the stability (hence toggling effect).

#### 'Debris
- In the constructor, via `Resource`, it is set to **`isStable = true`** (default).
- **`extendPosition()`** does the same as `Ore`: appends the passed `extension` string to the end of `position`.
- For the **`collect()`** method:
  - Only performs a position change if the fragment (**`Debris`**) is **tagged** (`tagged == true`). Then the position is appended with a **`#DISCARD`** ending and returned with this value.
  - If not tagged, it returns `null`.
- The **`setStable()`** method shall not change stability: `Debris` shall always remain stable.
- The **`tag()`** method should only allow `tagged = true` once. Once tagged, do not modify `tagged` and `isStable` more than once.

### Tests in the `ResourceTest` class (examples for testing Ore)

1. **`testCollect()`**:
   - If the `Ore` is **not** stable and **not** tagged, `collect()` returns `null`.
   - If **stable **or** tagged, the collection is successful: the position is `#FOUND` or `#REJECT` with the above conditions.

2. **`testTag()`**:
   - Initially untagged (`tagged = false`).
   - Once `tag()` method is called, `tagged = true` and `isStable` is changed (e.g. `false`→`true`).
   - On retagging, change again (`true`→`false`), and so on.

3. **`testEquals()`**:  
   - Two `Ore` instances are equal if they have the same first four characters, the same `type`, and the same `isStable`.  
   - The `hashCode()` must be consistent with `equals()`.  

4. **`testText()`**:  
   - Check the return value of `toString()`.  

---

## 2. Astronauts and waste management: `Astronaut`, `Recycler`, `InvalidResourceException` (8 + 5 + 1 + 0T points)

### `Astronaut` class

The **`Astronaut`** class models an astronaut collecting resources on a given planet.

- **Constructor**:  
  - The parametric constructor sets the **`unitId`** (astronaut ID) and **`specialityType`** (`Type`) fields, and creates an **empty** list for `resources`.  
  - There should also be an empty constructor that works with `unitId = `U1`, `specialityType = METAL`, and also creates an empty list.

- **Methods**:
  1. **`getResources()`**: return a **protected copy** of the `resources` list (for example, a new list with the same elements as the original, but not the same object).
  2. **``tagResources(List<Resource>)`**: tag the returned list according to the following rules:
     - If the specialty of the astronaut is **`METAL`**, then **all** resources are tagged.
     - If the astronaut's specialty is **`CRYSTAL`**, then **only stable** resources are tagged.
     - If the astronaut's speciality is **`GAS`**, then **nothing** is labelled.  
  3. **`tryToCollect(Resource resource)`**:
     - If `resource.getType()` ≠ `specialityType`, return `false`.
     - If it matches, call `resource.collect()`. If it is not `null`, add it to the `resources` list and return `true`.
     - If it is `null`, tag the resource (`resource.tag()`) and return `false` (according to the base code of the task); but based on your description, it can be extended to make a second attempt. 
  4. **`forceInsert(Resource resource)`**:
     - If `resource.getType()` ≠ `specialityType`, throw **`InvalidResourceException`**-t `“Wrong Type”` message.
     - If it matches, first `collect()`.
       - If `collect()` `null`, `tag()` and try again.
       - If on the second attempt the position changes to ``#DISCARD``, throw **`InvalidResourceException`` with ``Got Debris``.
       - Otherwise, add it to the `resources` list.

### `Recycler` class

The **`Recycler`** “scatters” resources on a planet:

- **`releaseResources(int n)`**: return `n` pieces of `Resource` such that:
  - The `Resource` instances are alternately `Ore` and `Debris` (even indexed → `Ore`, odd indexed → `Debris`).
  - The `position` increments one by one starting from 200: e.g. `“200-ME”`, `“201-CR”`, `“202-GA”`, etc. Decide the short code `“ME”`, `‘CR’`, `“GA”` based on `Type`.
  - The `type` should change cyclically `METAL → CRYSTAL → GAS → METAL → ...`
  - `Ore` instances are inherently unstable (`isStable = false`), `Debris` instances are stable (`isStable = true`).
- Example `n = 4`:
  1. `Position: 200-ME, Type: METAL, isStable=false` (Ore)
  2. `Position: 201-CR, Type: CRYSTAL, isStable=true` (Debris)
  3. `Position: 202-GA, Type: GAS, isStable=false` (Ore)
  4. `Position: 203-ME, Type: METAL, isStable=true` (Debris)

---

## Expedition 3: `SpaceMission` (12 + 1 point + 3*2T bonus)

The **`SpaceMission`** class models a space mission where astronauts collect resources.

1. **Constructor**:
   - Gets `numAstronauts` as a parameter. If it is not divisible by 3, throw **`IllegalArgumentException``.
   - Create an `astronauts` list of this size. The specialties are cyclically output in the following order: `METAL`, `CRYSTAL`, `GAS`, then again `METAL`, etc. The identifiers are `“U1”`, `“U2”`, ...

2. **`registerResources(List<Resource>)`**:
   - Take only resources of type **`METAL`** and add them to the list `missionResources` (check if you don't already have them from before).

3. **`registerResource(Resource)`**:
   - If not already in the `missionResources` list (based on `equals()`), add it.

4. **`prepareMission(int n)`**:
   - Creates a `Recycler` instance and calls the `releaseResources(n)` method to get `n` resources.
   - Checks if there is **METAL** type **at least** in the first (`[0]`), middle (`[n/2]`) and last (`[n-1]`) elements. If no METAL is found in any of these locations, throw **`InvalidOperationException`** with `“No METAL resources found”`.
   - The resulting list is passed to `registerResources()`.

5. **`conductMission()`**:
   - Requests all astronauts (`astronauts`) in turn to tag resources with `tagResources(missionResources)`.
   - Then, each astronaut tries to collect the items of `missionResources` with `tryToCollect()`.
   - If the specialty is `GAS` and `tryToCollect()` fails, then `forceInsert()` is tried. If it throws an exception (`#DISCARD`), the resource is dropped.
   - At the end, it returns a `HashSet` for successfully collected resources.

### Functional testing

- **``testPrepareMission()`**: test whether it throws `InvalidOperationException` if the requested positions do not have a METAL type.
- **``testConductMission()`**: test if the collection is successful as expected and if the returned `HashSet` really contains the correct resources.

---