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

import { worldList } from "./words.js";

divTheWord.textContent = worldList[random(0, worldList.length - 1)]