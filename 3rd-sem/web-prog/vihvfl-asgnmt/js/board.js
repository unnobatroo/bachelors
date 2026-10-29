"use strict";

// cell states the player can set; houses come from the level
const CellState = Object.freeze({
  UNDECIDED: 0,
  WALL: 1,
  MARKED: 2, // "this is land, not wall"
});

class Board {
  constructor(level, cells = null) {
    this.level = level;
    this.rows = level.rows;
    this.cols = level.cols;
    this.cells =
      cells ||
      Array.from({ length: this.rows }, () =>
        new Array(this.cols).fill(CellState.UNDECIDED),
      );

    this.houseAt = new Map(); // "r,c" -> people
    for (const [r, c, size] of level.houses) {
      this.houseAt.set(Board.key(r, c), size);
    }
  }

  static key(r, c) {
    return `${r},${c}`;
  }

  inBounds(r, c) {
    return r >= 0 && r < this.rows && c >= 0 && c < this.cols;
  }
  isHouse(r, c) {
    return this.houseAt.has(Board.key(r, c));
  }
  get(r, c) {
    return this.cells[r][c];
  }
  set(r, c, value) {
    this.cells[r][c] = value;
  }

  // 4-way neighbours, in bounds
  *neighbours(r, c) {
    for (const [dr, dc] of [
      [-1, 0],
      [1, 0],
      [0, -1],
      [0, 1],
    ]) {
      if (this.inBounds(r + dr, c + dc)) yield [r + dr, c + dc];
    }
  }

  // serializes to "00120..." for saves
  serialize() {
    return this.cells.map((row) => row.join("")).join("");
  }

  static deserializeCells(data, rows, cols) {
    const cells = [];
    for (let r = 0; r < rows; r++) {
      const row = [];
      for (let c = 0; c < cols; c++) {
        const v = Number(data[r * cols + c]);
        row.push(v === CellState.WALL || v === CellState.MARKED ? v : CellState.UNDECIDED);
      }
      cells.push(row);
    }
    return cells;
  }
}
