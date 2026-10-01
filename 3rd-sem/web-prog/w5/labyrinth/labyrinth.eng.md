# Web programming home project -- The magic maze

![](motto.png) 

Once upon a time, there was a king, who had a beautiful daughter. The princess had a lot of wooer, and a king was struggling whom to choose. Finally an idea came into his mind. The castle had a magical maze, where the rooms can change their positions. There are treasures hidden in the rooms, and whichever wooer collects his treasures first, he wins the princess's hand.

![](maze.png)

## How to play the game

The rooms int **maze** are symbolized by a 7x7 square grid cells. For each room we know that which wall has a door. If there are two doors in the contact wall of two neighbouring rooms, you can go from one room to another. The even rows and columns in the grid can be slided, the other rooms are fixed throughout the game. By sliding the rooms in the grid, doors and passages open through which players can move in the maze. By cleverly sliding the rooms players try to find their way to the treasures. The first player to find all their treasures and return to the starting square is the winner.

<table>
<tr>
<td>

![Fix elements](fix.png)

</td>
<td>

![Before sliding](before.png)

</td>
</tr>
</table>

_Note: there is a lightblue stripe on the right image and the next two images. It appears "accidentally" because of the extra room. Showing this is not an expectation, in fact it is an ugly solution!_

**At the beginning of the game** we put the rooms in random order and in random direction to the free fields of the game board. There should be one room remaining. During the game we will always use the extra room to slide the rest of the rooms. In the game you have to find up to 24 treasures. These are randomly placed on the board so that only one treasure can be in a room and can not be placed in the corner. For each treasure there is a card. We shuffle these treasure cards and divide them evenly among the players and revealing the top card. Playing pieces are placed in separate corners of the board.

<table>
<tr>
<td>

![Sliding a row](shift.png)

</td>
<td>

![After sliding](after.png)

</td>
</tr>
</table>

**During the game** each player has to find his/her revealed treasure card (that can be seen by others).  The player tries to get to the room showing the same treasure as on this card. For this the player needs to
1. move the maze, and after that
2. move the playing piece.

**Moving the maze** is done as follows: the player inserts the extra room into the game board where one of the arrows shows the slidable rows and columns, until another room is pushed out of the maze on the opposite side. The only exception: The room cannot be inserted back into the board at the same place where it was pushed out. So you cannot undo the previous player's move. If the room you push out has a playing piece on it, put this piece on the opposite side of the board on the room that was just placed. The treasures always move with the rooms.

Once you have moved the maze, you can **move your playing piece**. You can occupy any square that you can move your piece to directly, without interruption. You can move your playing piece as far as you like. Or, you can leave your playing piece where it is. There can be more than one playing piece in one room: playing pieces do not hit each other. If you are unable to get to the treasure you are searching for, you can move your playing piece into a position that gives you a good starting point for your next turn. Once you find the treasure you are looking for, reveal your next treasure card. On your next turn,
find your way to this treasure on the game board.

Now it’s the next player’s turn. This player inserts the extra room into the game board before moving their playing piece, and so on. 

The **game is over** as soon as a player has found all their treasure cards and returned their playing piece to its starting position. The first player to do this is the winner.

## Realizing the game

### Start screen

Create a start screen, where the game parameters can be set:
- number of players (1-4) -- defaults to 2
- number of treasure cards per player (1-(24/number of players)) -- defaults to 2

By pressing a Start button, show the game board.

Pressing another button show the game instructions.

Attention! These are not separate pages, but revealed or hidden panels. The whole game should be on one HTML page!

### Game board preparation

The game board should contain the following items:
- the board itself, which is a 7x7 grid, on which there are
  - fix rooms (see figure)
  - movable rooms randomly rotated and placed
    - 13 straight
    - 15 bend
    - 6 T-piece
  - treasures on room pieces (moving together with the room)
    - at most one treasure in one room
    - no treasure in the corner
    - a treasure can be represented by a color, number, custom image (ring, jewel)
  - playing pieces in the corners (represented by a color, number, or image)
  - the extra room
- player information
  - number of the player
  - the actual treasure card
  - found/all treasures
  - actual player indication

Indicate somehow the player who turns.

### Moving the maze

The extra room has to be slided in one of the even rows or columns, from one side. Ensure that the extra room can be rotated by 90 degree (e.g. on right click). Ensure that the extra room can be slided into a movable row or column, e.g. by clicking on an arrow. (Pro tip: if you hover the arrows put the extra room there, right click to rotate, left click to slide the row.) During sliding everything is moving with the rooms: treasures, playing pieces. If a playing piece is fallen on the other side, put it on the other side, on the inserted room. Show the sliding by animating the rooms.

### Moving the playing piece

After moving the maze, the actual player can move its playing pieces. Highlight those rooms that can be reached from that point, including the actual room. By clicking on such a highlighted room the playing piece is moved to its new position. If the new room contains the needed treasure, then that treasure card is completed, reveal the next treasure card. If every needed treasure has been found and the playing piece is staying on its starting position, the game ends.

Show the move by animation.

Attention! The default expected behaviour is to move to the possible neighbouring room. For extra points you can offer all the accessible rooms (graph traversal).

### Game over

At the end of the game, show the winner player number and then be able to start a new game.

### Save the game

For extra points make it possible to save the actual state of the game to the local storage of the browser. On the start screen show if such a game save exists, and make it possible to load the saved game.

## Further expectations

**Design is important.** Your submission doesn't have to be really pretty and filled with frills, but it should look nice on a screen of at least 1024×768 pixels; the grid should contain square-shaped cells. You can use minimalistic design, custom CSS with extra graphical elements or a CSS framework.

There's no mandatory **technology** for displaying the game: you can use `table`s, `div`s, and `canvas` freely. Criteria for function and presentation isn't set in stone, there's flexibility in the grading as long as your game is playable well and the tasks described above work in some way.


## Help

First, construct the needed game elements. If you do not use canvas, this means that create the HTML and CSS static prototype of the game. Experiment and create the necessary elements:
- how do you realize the grid layout?
  - table?
  - absolutely positioned elements?
  - flexbox?
  - CSS grid?
- how do you display a room?
  - doors? (passages)
  - how do you rotate the room?
- how do you put a treasure on a room to be visible?
  - text?
  - number?
  - colour?
  - image?
- how do you place the playing pieces in a room?
  - what if there are multiple piece in one room?
- how do you solve the animation of rows and columns with the chosen layout technique?
  - during experimentation trigger it e.g. by hovering the table
- how do you show the arrows around the table?
- where do you put the extra room?
  - separate place?
  - or will it always stay where it was pushed during sliding?
- how do you display the player informations?
- how do you implement the start screen?

For these you do not have to program, just use HTML and CSS.

Next, think about the necessary data to describe the game, and what data structure is needed for them!
- the game board
  - positions of rooms
  - room type (straight, bend, T-shape)
  - room rotation
- positions of treasures
- initial and actual positions of playing pieces
- treasure cards in the players' "hand"
- extra room
- etc

What operations are needed on those data?
- how do you solve the sliding of the extra room? (shifting elements in row and column)?
- highlighting the accessible rooms?
- different validations?
- completing a treasure card
- revealing a new treasure card
- etc

What events do you have to work with?
- event types
- source element?
- listening element?
- bubbling and delegation?

The preparation phase is very important in the game, generating the game board, rotating the rooms randomly, putting them on the board, dividing the treasure cards to players. After that there are two important steps:
1. what events are needed for moving the maze?
2. how to move the playing piece?

They are repeated in the game.

We do not see everything in a bigger task. You dont need to divide the HTML and CSS work to steps, you can design the whole interface in one go. However, when developing JavaScript, it's better to take small steps. Solve one thing at once, and make it work!

## Example Prototype

The above screenshots were made [from this HTML and CSS prototype](https://jsbin.com/kesehonuba/edit?html,css,output). Look at and investigate it! You can find good examples how to draw the passages with CSS classnames. The grid was made by an HTML table. The extra room was created by adding a new cell to the row, but - as it turned out - was not a good solution, because a new column was added to the table as well, that is why a lightblue column appears on the right. There is a small experiment on animations by hovering the table. Note, that you do not have to use this prototype, but you can find interesting ideas in it.


## Grading

The assignment is worth 20 points. There is a set of minimum requirements, without those, the assignment is not acceptable. The extra tasks are worth 5 points, so in total, you can get 25 points if you do everything.

### Minimum requirements (8 points)

- Other: The `README.md` file from the *Other requerements* section is filled with your data and included with your solution (0 point)
- Game board: The game board appears (0 point)
- Game board: The fix elements appear in a 7x7 grid (1 point)
- Game board: In the same grid the movable elements appear randomly rotated and randomly placed (2 points)
- Game board: There are at least 3 treasures in the grid (except the corners (0,5 point)
- Game board: There is at least 1 playing piece in the grid (0,5 point)
- Moving maze: The extra room can be slided into a slidable row or column. The rooms change well. (2 points)
- Moving playing piece: We can move the playing piece to one of the possible neighbouring room (2 points)

### Basic tasks (12 points)

- Start screen: setting the number of players (0,5 point)
- Start screen: setting the number of treasure cards per player (0,5 point)
- Start screen: clicking the start button the game board appears (0,5 point)
- Start screen: the game instructions can be seen (0,5 point)
- Game board: treasures appear in accordance with the settings (0,5 point)
- Game board: playing pieces appear in the corners (0,5 point)
- Game board: extra room appears (0,5 point)
- Game board: player informations appear (0,5 point)
- Game board: the actual player is indicated (0,5 point)
- Moving maze: extra room can be rotated before sliding (0,5 point)
- Moving maze: sliding the rooms is done with animation (1 point)
- Moving maze: treasures stays in the same rooms as they were initially placed (0,5 point)
- Moving maze: fallen playing piece moves to the other side (0,5 point)
- Moving playing piece: highlighting the accessible rooms (0,5 point)
- Moving playing piece: moving is done with animation (1 point)
- Moving playing piece: if we reach the needed treasure, the player information changes correctly (0,5 point)
- Moving playing piece: if every treasure is collected and we step to the initial position, the game is over (0,5 point)
- Moving playing piece: multiple playing piece in one room appear correctly (0,5 point)
- Game over: clicking a button we can start the game from the beginning (0,5 point)
- Other: Demanding design (1 point)
- **Missing the deadline by a week (-3 points)**
- **Missing the deadline by two weeks (-6 points)**
- **Missing the deadline by more than two weeks (rejected assignment and no grade)**

### Extra tasks (extra 5 point)

- Moving playing piece: highlighting all accessible rooms (3 point)
- Save game: during the game the actual state can be saved (0,5 point)
- Save game: if there is a saved game, this information appears on the start screen (0,5 point)
- Save game: the saved can be loaded from the start screen (1 point)

### Other requirements

- The assignment should be compressed into an archive containing all necessary files AND the `README.md` file in the root folder of the program, and uploaded to Canvas by the deadline.
- You **cannot** use any external, third-party JavaScript libraries.
- The `README.md` file has the following requirements:
  - You fill in your own data at the start of the file (marked by `< >` signs)
  - You mark all (partyally) finished subtasks with an `x` in place of the space between the square brackets `[ ]` in the file.

```txt
<student's name>
<student's NEPTUN code>
Web-programming - JavaScript home assignment
This solution was submitted by the stundent named above for a Web-programming assignment.

Hereby I declare that the solution is my own work. I did not copy or use solutions from a third party. I did not share this solution with fellow students and I did not publish it. 

According to the Academic Regulations for Students (Eötvös Loránd University Organisational and Operational Regulations – Volume 2, Section 74/C), a student purpoting the intellectual property of others as their own [...] is committing a disciplinary offence.

The worst result of a disciplinary offence can be the expulsion of the student.


Minimum requirements (8 points)

[ ] Other: The `README.md` file from the *Other requerements* section is filled with your data and included with your solution (0 point)
[ ] Game board: The game board appears (0 point)
[ ] Game board: The fix elements appear in a 7x7 grid (1 point)
[ ] Game board: In the same grid the movable elements appear randomly rotated and randomly placed (2 points)
[ ] Game board: There are at least 3 treasures in the grid (except the corners (0,5 point)
[ ] Game board: There is at least 1 playing piece in the grid (0,5 point)
[ ] Moving maze: The extra room can be slided into a slidable row or column. The rooms change well. (2 points)
[ ] Moving playing piece: We can move the playing piece to one of the possible neighbouring room (2 points)

Basic tasks (12 points)

[ ] Start screen: setting the number of players (0,5 point)
[ ] Start screen: setting the number of treasure cards per player (0,5 point)
[ ] Start screen: clicking the start button the game board appears (0,5 point)
[ ] Start screen: the game instructions can be seen (0,5 point)
[ ] Game board: treasures appear in accordance with the settings (0,5 point)
[ ] Game board: playing pieces appear in the corners (0,5 point)
[ ] Game board: extra room appears (0,5 point)
[ ] Game board: player informations appear (0,5 point)
[ ] Game board: the actual player is indicated (0,5 point)
[ ] Moving maze: extra room can be rotated before sliding (0,5 point)
[ ] Moving maze: sliding the rooms is done with animation (1 point)
[ ] Moving maze: treasures stays in the same rooms as they were initially placed (0,5 point)
[ ] Moving maze: fallen playing piece moves to the other side (0,5 point)
[ ] Moving playing piece: highlighting the accessible rooms (0,5 point)
[ ] Moving playing piece: moving is done with animation (1 point)
[ ] Moving playing piece: if we reach the needed treasure, the player information changes correctly (0,5 point)
[ ] Moving playing piece: if every treasure is collected and we step to the initial position, the game is over (0,5 point)
[ ] Moving playing piece: multiple playing piece in one room appear correctly (0,5 point)
[ ] Game over: clicking a button we can start the game from the beginning (0,5 point)
[ ] Other: Demanding design (1 point)
[ ] **Missing the deadline by a week (-3 points)**
[ ] **Missing the deadline by two weeks (-6 points)**
[ ] **Missing the deadline by more than two weeks (rejected assignment and no grade)**

Extra tasks (extra 5 point)

[ ] Moving playing piece: highlighting all accessible rooms (3 point)
[ ] Save game: during the game the actual state can be saved (0,5 point)
[ ] Save game: if there is a saved game, this information appears on the start screen (0,5 point)
[ ] Save game: the saved can be loaded from the start screen (1 point)
```

A submission without a properly filled `README.md` file is not eligible to be graded!
