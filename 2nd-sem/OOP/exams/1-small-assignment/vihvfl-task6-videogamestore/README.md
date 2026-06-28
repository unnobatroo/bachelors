## Common requirements for the assignments

The documentation should include:

- the description of the exercise (copied from here)
- the class diagram (designed using Umbrello, [draw.io](http://draw.io/), plantUML, etc.)

The code should include:

- javadoc comments for each class and method (except getters and setters)
- unit tests for each of the important methods (not getters or setters)

## Assignment 6 - Video Game Rental Store

This project targets JDK 25.

A video game rental store manages a collection of video games, each with distinct attributes that determine its availability and demand.

### Game Attributes

Every game in the inventory includes the following data:

- **Title**: The name of the game.
- **Genre**: The category (e.g., action, puzzle, or simulation).
- **Age Rating**: Indicates suitability for players.
- **Popularity Score**: Based on previous rentals.
- **Damaged Status**: Indicating whether the game is still functional.
- **Rental Price**: The cost per hour.
- **Total Rent Count**: Tracks how many times the game has been rented.
- **Availability Status**: Shows whether it is currently rented or in stock.

### Rental Pricing and Penalties

The store enforces a structured pricing system to maintain revenue and ensure timely returns:

| **Charge Type** | **Rate Calculation** |
| --- | --- |
| **Standard Rental** | Fixed price per hour * total hours rented. |
| **Late Return Fine** | 2 * the hourly rental rate for each overdue hour |

### Customer Preferences & Management

Customers visiting the store may have different preferences when selecting a game. Some may **look for the most popular game based on past rentals**, while others may **search for a game within a particular genre** or **browse games suitable for their age group**. The store must efficiently manage game availability to ensure that customers can find the games they want.

### Damage and Retirement Policy

Games eventually sustain wear and tear through use:

- **Every time a game is rented, its total rent count increases.**
- **A game is considered damaged and permanently removed from circulation if it has been rented more than 20 times**, marking it as too worn out for further use.

### System Organization and Revenue

To maintain an organized rental system, the store tracks the number of games rented, their due dates, and applies late fees if they are returned past the deadline.

Additionally, **the system keeps records of the total revenue from rentals, ensuring that the store can analyze which genres and titles are most profitable**. Over time, games that are damaged are removed from circulation. By keeping detailed records of rental statistics, customer preferences, and game performance, the store ensures that only high-demand and properly functioning games remain available for rent, creating a better player experience.

### Class Diagram

The PlantUML class diagram is available in [design.puml](design.puml).

### Running the Demo

Compile and run the demo entry point:

```sh
javac --release 25 -d out src/videogamestore/*.java
java -cp out videogamestore.Main
```

### Running Tests

The tests use JUnit 5. The helper script downloads the standalone JUnit runner and executes the tests:

```sh
./run_tests.sh
```