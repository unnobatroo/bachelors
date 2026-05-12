# Programming languages Java Exam, 2024.05.

## Conditions

Do the following **right now**: make sure that no communication device is available to you.

- **Put away** phones, headphones, tablets etc.
- **Close** all chat programs, mail clients etc.
- **Keep these things off/away** during the exam.

During and after the exam.

- If you're found cheating (e.g. giving or receiving help or using forbidden materials) during or after the exam, you are not getting graded this semester.
- You are forbidden from sharing any part of your exam solution until the day after the exam.
- You are allowed to use the Java API documentation and the JUnit documentation. You may download them from Canvas.
- About the code.
    - Whenever a name is specifically given, use that name exactly.
    - Follow good practices.

Submitting.

- When the time is nearly up (with about 10 minutes left to go), zip the project that you created and upload it into Canvas.
	- In the context menu, you can use "Küldés/Tömörített mappa" to create the zip.
	- You don't need to pack the `jar` or `.class` files.



## Basic exercise - SimpleChess

In the following, we implement a simple chess-playing program.
Create the program according the description find in the structural tests (`PiecesSuite` and the classes it refers to).

**Important! The code have to compile and every test have to run correctly for the maximum points!**

## Helper Classes - Utils ( 3 points )

The `Colors` enumeration type (1p) implies the colours of the chess figures. The possible values are: light figures -> `LIGHT` and dark figures -> `DARK`


The `IllegalMoveException` is a custom exception class (1p), which can indicate the different invalid steps in the game. It is able to store and give back messages, which describes the committed mistake.

The `Actions` interface (1p) summarizes the expected methods from chess figures. All figures are able to move onto a specific field (`move()`), if the step is correct. To be ensured this, the following methods are needed:
- `checkMove()`: Checks that the path leading to the field is correct (we make an adequate step with the figure). It can throw `IllegalMoveException`.
- `checkPath()`: Checks whether there is an obstacle (other figure) on the path leading to the field. It can throw `IllegalMoveException`.
- `checkField()`: It gives back the chess figure positioning on the given field, if there is one.



## The elements of the chess - Pieces ( 18 points )

We realize only 4 kinds of chess figure (`Figure`) in the simplified chess: the leader/queen (`Queen`), the bastion (`Rook`), the runner (`Bishop`), and the pawn (`Pawn`)

![Alt text](figures.png "the chess figures")

Each single chess figure derives from the abstract `Figure` class. Every figure has color and column, row values as actual position on the chess board. If the figure is not on the board yet, let the values of the latter two fields be 0, 0 respectively.
(Inspect the pictures below for the indexing of the chess fields.)

`checkMove()` only the specific figure instance can check the correctness of a step, so this method will be implemented only in the child classes.

`checkField()` (1p) It gives back the chess figure positioning on the given field, if there is one. If there is no chess piece present, it gives back `null` value. 
Every figure has to know the rest of the chess pieces to check the status of a field, because of this the method gets the chessboard in the form of a `HashMap`. The key is a specific field of the chessboard, whereon the figure is, and the value is the figure itself. (Comment: we store only those fields where a figure actually stands. Only a single figure can stand on a field, at the same time.)

`checkPath()` (3p) checks whether there is an obstacle(other figure) on the path leading to the field. To solve this, move the figure step by step closer to the goal, and when it hits another chess piece before the end field, then throw one `IllegalMoveException`-t with an informative message.
Let us pay attention to not modify the inner state of the figure meanwhile, that is do not move the figure away in case of an invalid route.
Hint: The change of the coordinates in every step are calculatable from the sign of the difference of the start and end field.

The `move()` (7p) method moves the figure onto the given field, if it is possible/correct. Only the chess pieces on the board can make a move. The chess piece may move according to the rules of the figure, and not allowed to jump over another figure. If the opponent has a figure on the goal field, then it will be captured, this means the opponents figure removed from the board and our figure gets to his place. It is not possible to capture own figures. The `Pawn` has exceptional motion/capture rules (see under), pay attention to this specially. Also do not forget to refresh the HashMap representing the board! The method will throw an `IllegalMoveException` with the appropriate message in case of every false move. This method is responsible for the exception handling, do not propagate the exceptions to higher level. The state of the board remains the same in case of a false move, all figures stay in place. In case of a successful step the `move()` method returns with value 1, in case of unsuccessful step it returns with 0.
Hint: It is very helpful, if we print the change of the figure position to the terminal in its setter method, so we can check what's happening with the figure.

The inner representation of the figures stores the positions column, row data in `int`-s. If we want to address a figure on the board we communicate it in a form like "a2", where the first letter is the column, and the second is the row of the board. The HashMap works with keys like this.
The class has some helper functions: the `fieldName()` which one gives back the figures own position and the `fieldName(int, int)` which one gives back the given position in a string format. (Hint: the ASCII code of the 'a' is 97, if necessary) (1p)

The figures are capable to give textual representation: colour and type of the figure, and the data of the actual chess field. (1p)

In the next classes the `checkMove()` method is responsible for the correct steps. It throws an `IllegalMoveException` in case of a wrong move, with the appropriate informations.
The `Bishop` class represents the runners, which may make steps only diagonally on the board. (1p)
The `Rook` class represents the bastions, which may make steps only horizontally or vertically. (1p)
The `Queen` may makes steps diagonally, horizontally, or vertically. (1p)
The `Pawn` has special movement. It can step only one field toward the opponent's direction (light "upwards", dark "downwards") or in case of capture one field diagonally forward (henceforward toward the opponent's direction). (Comment: the pawn has an opportunity to move two fields forward from the base line in the classic chess, but you **don't have to** implement this.) (2p)


## FigureTest ( 4 points )

Check the program with `FigureTest` functional tester class. We do not mind the size of the board so far.
Let us place some bishops, rooks, queens on the chess board represented by a HashMap in the first test method. Make attempts to do some correct and some invalid steps (E.g. step onto an invalid field, try jump over another figure, capture a figure with the same colour etc.) Verify that the position of the figure changes in case of a valid move only. (2p)
One the second test examine the motion of the pawns: They step only toward the correct direction (side or back move is not allowed). They may step only one field, no more, if the field is empty (capture is not allowed). They may make a diagonal step only if they capture an opposite figure. (2p)


## Match ( 23 points )

The `Match` class stores the chess pieces in a `HashMap` called `figures`, where the key is the stringified position of the figure, since there is only one figure simultaneously on a field. (Pl. c=1,r=2 => "a2") The format of a step consists of 4 `int` values: starting column, starting row, goal column, goal row. This data is stored in an `ArrayList`. Likewise, the steps are stored in an `ArrayList` called `moves`. Now, the dimension of the board will be important (nxn): `size` field.

The `Match()` constructor (11p) initialize the game, which will be considerable work.
It must process the given file in the parameter list.
The file consists of three parts: all parts begin with a header starting with a '_' character.
- The dimension of the board can be found in the first part. Only an 5x5, to keep it simple.
- The starting figures are listed as colour, type and position accordingly in the second part. The values are separated with ';'. Let us prepare ourselves, that there may be some figures with invalid type or position, which ones may not get onto the board. It is enough to signal the wrong cases on the terminal with some description text only, there is no need for proper exception handling here.
- The third part contains the steps in a text format. E.g.: "c1c4" means, that the figure (if present) on field c1 moves to field c4.

The `play()` method (11p) is going through the steps and they will be executed if they are correct.
The light and dark player move in turns in the chess. The light player starts. The other player may come next if the previous executed a successful move. The unsuccessful steps will be skipped (we display them on the terminal only).
It is necessary to check, that there is a figure on the starting field, and the figure does not leave the chess board. 
Furthermore, there is a special event: if a `Pawn` reaches the opponent's first row (in case of light player it the 5th row in our example, in case of dark one it is the 1st row), then it will turns into a `Queen` on the end of the step.

The `checkCoord()` is a helper method (1p), which examines, that the received value is in a suitable range (`true`), or not (`false`) (E.g.: It is a correct row or column value for the chess board).


## MatchTest ( 2 points )

Let us create a functional tester, which calls the methods of the `Match` class, to simulate a chess match, based on the `Chess5x5.txt` file. Let us check a few things: their new position of the figures, which moved, are correct; some field really became empty etc.

The setup at the beginning:
![Alt text](setup_begin.png "start")

And at the end:
![Alt text](setup_end.png "end")
