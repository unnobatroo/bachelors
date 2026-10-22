"use strict";

// the three fixed boards [row, col, people]
const LEVELS = [
  {
    id: "easy",
    name: "Easy",
    rows: 5,
    cols: 5,
    houses: [
      [0, 0, 1],
      [0, 2, 5],
      [2, 1, 1],
      [3, 3, 5],
      [4, 0, 1],
    ],
  },
  {
    id: "medium",
    name: "Medium",
    rows: 7,
    cols: 7,
    houses: [
      [0, 0, 12],
      [0, 6, 4],
      [3, 0, 1],
      [3, 2, 1],
      [5, 0, 7],
      [5, 5, 3],
    ],
  },
  {
    id: "hard",
    name: "Hard",
    rows: 10,
    cols: 10,
    houses: [
      [0, 0, 12],
      [0, 7, 7],
      [2, 4, 2],
      [4, 0, 5],
      [4, 4, 1],
      [4, 6, 2],
      [4, 9, 1],
      [6, 4, 1],
      [6, 6, 1],
      [6, 8, 1],
      [7, 0, 13],
      [7, 5, 1],
      [8, 7, 4],
    ],
  },
];
