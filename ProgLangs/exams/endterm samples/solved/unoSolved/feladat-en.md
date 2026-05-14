
# Programming languages Java ZH, 2023.06.06.

## Conditions

- The solution to the problem must be completed independently, without the help of others.
    - Communication is allowed only with the instructor.
    - The completed solution may not be shared with others (e.g. uploaded to a forum or public version control system) not only until the end of the ZH, but also until the end of the day of the ZH.
    - The Java API and the JUnit documentation can be used to create the solution. This can be downloaded and unpacked from Canvas.
- The completed solution must be uploaded to Canvas in **zip** format.
    - The `zip` contains the source file(s). Do not include `jar` files.
    - At the end of the **ZH, you should reserve about 10 minutes** to clean up the code, make it compilable, compress it, submit it.

## Basic task

We are creating a version of the UNO card game.
The structure of the program should be as described in the structural tester (`UnoTestSuite` and its referenced classes).

During the game it is assumed that all data/inputs are correct.

The cards (`Card`) have a given colour, type and value.
The latter only matters if the type of card is `VALUE`, otherwise it is assumed to be set to `0`; value cards are numbered `1-9`.

- The constructor simply takes these values.
- A card should be written as `GREEN 9` if it is a numeric card, and as `RED REVERSE`, `YELLOW SKIP` otherwise
- Two cards are considered equal if all their data elements are the same.
	- Hint: the companion to this operation must also be written, for which the `Objects.hash` method is useful.
- A card is playable over another card (`isPlayableOver`) if...
	- a special (non-value) card is playable only on a value card of the corresponding suit.
	- a value card is playable for a matching suit or matching number.

A player (`Player`) stores his name, his cards in sequence (`hand`) and the reference of the game played.

- A player is written out as `Player P2: 1=GREEN REVERSE 2=RED 4 3*=BLUE 5 4=RED 5 5=RED SKIP 6*=GREEN 3 `
	- Here the asterisk indicates that the card can be played on the active card of the game.

The game works by players taking turns to name which card they want to play.
If the card cannot be played, they draw one from the deck.
Control of which card is chosen is handled by the `InputSource` class.

- Its constructor gets whether it is in interactive mode.
	- If so, it will read from the standard input: `br = new BufferedReader(new InputStreamReader(System.in))`
	- If not, it will receive the line number of the selected cards in sequence.
- The call `getNextInput` will return the line number of the next card.
	- In interactive mode, a text is read using `br`.
		- If the text `done` is received, then the constant `DONE` is output.
		- Otherwise, it is assumed that an integer was received and this is output.
	- Otherwise, the received values are returned in the method line.
		- If they are exhausted, then the constant `DONE` is returned.

## The game

When the game is started (main program of the `Game` class), the `Game` class is instantiated:
6 cards per player, interactive `InputSource` and the names of the players are command line parameters.

- If there are not at least two players, the constructor throws `NotEnoughPlayersException`.
	- To the parent class, it should pass `Only 1 players were given`
- The first parameter of the constructor specifies how many cards are initially given to the players.
- The constructor creates a new deck (`initDeck`).
	- For each suit: one of each number card (with values `1-9`).
	- For each suit: two of each special card.
	- Shuffle the deck: `Collections.shuffle(deck)`.
	- If the game is non-interactive, shuffle the deck so that it always sets up the same way: `Collections.shuffle(deck, new java.util.Random(12345))`.
- The constructor should set the players (`initPlayers`).
	- The player cards should be drawn from the deck using the `drawCards` method.
- The active card (`currentCard`) should be drawn from the deck.
	- The `drawCards` method must be used for this as well.
- The variables `currentPlayerIdx`, `isForward`, `isOn` should be set as appropriate.

The game is on as long as 'isOn' is true. A move is made by calling `playNext`. This works as follows.

1. Determine who the next player is (`getNextPlayerIdx`).
	- Note where the turn is going, and whether we are just "crossing the line".
	- Use the `interactiveMsg` method to print information about this and all other important moves.
		- Here, for example, we write out the player (with his cards) so that the user can decide which card to choose.
		- This method does not print anything in non-interactive mode.
1. The new player becomes the active one (`currentPlayerIdx`).
1. `InputSource` is used to produce the serial number of the chosen card.
	a. If it is not playable, the player draws a card from the deck (`currentPlayerDrawCard`).
	a. If playable, it is removed from the player's hand and becomes the active card. Also, if it has a special effect, it is triggered (`useCardEffect`).
1. If there are no cards left in the player's hand, he wins; `isOn` is set to false.

Special effects of cards by type:

`REVERSE`: the order is reversed
`SKIP`: the next player is eliminated
`TAKE`: the next player is out and draws a card

## Non-interactive testing

Add a testing method to the `uno.GameTest` class.
It creates a non-interactive `InputSource` driven game with three players, 2 cards each, in `1 0 1` steps.
Let's try the following.

1. Initially, the active card is `RED TAKE`.
1. Initially, the three players' cards are `GREEN SKIP, RED 1`, `RED 3, BLUE 8`, `BLUE REVERSE, RED 8`
1. call the `playNext` method three times, and after each step, check the next one.
	- The active player index is correct.
	- The active card is indeed the last card played.
	- The played card is out of the active player's cards.
	- The value of the game `isOn` is true.
1. If the `playNext` method is called again, `isOn` is false.
