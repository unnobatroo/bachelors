const task1 = document.querySelector("#task1");
const task2 = document.querySelector("#task2");
const task3 = document.querySelector("#task3");
const task4 = document.querySelector("#task4");

const game = ["XXOO", "O OX", "OOO "];

const allChars = game.join("");

// a. Output whether all rows have the same length to the element with ID task1
task1.textContent = game.every((arr_row) => arr_row.length === game[0].length);

// b. Output whether the first row only contains X and O characters to the element with ID task2
task2.textContent = /^[XO]+$/.test(game[0]);

// c. Output the number of X and O characters to the element with ID task3
let xCount = 0;
let oCount = 0;
for (const char of allChars) {
  if (char === "X") xCount++;
  else if (char === "O") oCount++;
}
task3.textContent = `X/O=${xCount}/${oCount}`;

// d. Output the index of any row that contains three consecutive Xs
// or three consecutive Os to the element with ID task4
// (You may assume that at least one such row exists)
task4.textContent
