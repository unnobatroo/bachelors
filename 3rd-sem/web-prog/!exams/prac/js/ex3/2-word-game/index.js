// ========= Utility functions =========
function random(a, b) {
  return Math.floor(Math.random() * (b - a + 1)) + a;
}

// ========= Selected elements =========
const inputGuess = document.querySelector("#inputGuess");
const form = document.querySelector("form");
const tableGuesses = document.querySelector("#guesses");
const divTheWord = document.querySelector("details > div");
const spanError = document.querySelector("#error");
const btnGuess = document.querySelector("form > button");
const divEndOfGame = document.querySelector("#end-of-game");
const btnRestart = document.querySelector("#restart");

// ========= Solution =========

// a. (1 pt)
// Choose a random word from the array wordList
// and display it in the div divTheWord that is inside
// the details element. (You can use this to view the solution
// while testing.)

const targetWord = wordList[random(0, wordList.length - 1)];
divTheWord.textContent = targetWord;

form.addEventListener("submit", e => {
  // b. (1 pt)
  // Currently the form on the page is submitted when the user presses ENTER
  // or clicks the button. Prevent this behaviour using JavaScript.
  // Instead of submitting the form, select the text in the text field
  // and delete the content of the span element of the form.
  e.preventDefault();
  inputGuess.select();
  spanError.textContent = "";

  // c. (1 pt)
  // When the form is submitted, check that the entered word consists of exactly 5 characters.
  // If not, then display an error message (e.g. "The length of the word is not 5!")
  // in the span element of the form.
  if (inputGuess.value.length !== 5) {
    spanError.textContent = "The length of the word is not 5!";
    return;
  }

  // d. (1 pt)
  // When the form is submitted, check that the entered work is in the array wordList.
  // If not, then display an error message (e.g. "The word is not considered acceptable!")
  // in the span element of the form.

  if (!wordList.includes(inputGuess.value)) {
    spanError.textContent = "The word is not considered acceptable!";
    return;
  }

  // e. (1 pt)
  // Calculate how many letters of the guessed word are in the correct position
  // and output the number to the console.
  const matches = [...inputGuess.value].filter((letter, idx) => letter === targetWord[idx]);
  console.log(matches.length)

  // f. (1 pt)
  // Insert the guessed word and the calculated number as the first row of the table.
  const row = tableGuesses.insertRow(0);
  row.insertCell(0).textContent = inputGuess.value;
  row.insertCell(1).textContent = matches.length;

  // g. (1 pt)
  // If the guess is correct, apply the style class of correct to the row
  // and display the div end-of-game.
  if (matches === 5) {
    row.classList.add("correct");
    form.style. // no idea what to write in here
    show(divEndOfGame);
    return;
  }
});