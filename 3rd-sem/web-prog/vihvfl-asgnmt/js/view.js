"use strict";

// renders the board
class BoardView {
  constructor(container) {
    this.container = container;
    this.cellEls = [];
    this.errorCells = new Set();
  }

  build(board) {
    this.board = board;
    this.cellEls = [];
    this.errorCells = new Set();
    this.container.innerHTML = "";
    this.container.style.gridTemplateColumns = `repeat(${board.cols}, 1fr)`;

    for (let r = 0; r < board.rows; r++) {
      const row = [];
      for (let c = 0; c < board.cols; c++) {
        const el = document.createElement("div");
        el.className = "cell";
        el.dataset.row = r;
        el.dataset.col = c;
        el.setAttribute("role", "button");
        el.tabIndex = 0;
        if (board.isHouse(r, c)) {
          el.classList.add("house");
          const num = document.createElement("span");
          num.className = "num";
          num.textContent = board.houseAt.get(Board.key(r, c));
          el.append(num);
        }
        this.container.append(el);
        row.push(el);
      }
      this.cellEls.push(row);
    }
    this.refreshAll();
  }

  refreshAll() {
    for (let r = 0; r < this.board.rows; r++) {
      for (let c = 0; c < this.board.cols; c++) this.refreshCell(r, c);
    }
  }

  #describe(el, r, c) {
    const b = this.board;
    let text;
    if (b.isHouse(r, c)) {
      text = `house, ${b.houseAt.get(Board.key(r, c))} inhabitants`;
    } else {
      const state = { 0: "undecided", 1: "wall", 2: "marked property" }[b.get(r, c)];
      text = `row ${r + 1}, column ${c + 1}: ${state}`;
    }
    if (el.classList.contains("invalid")) text += ", invalid";
    el.setAttribute("aria-label", text);
  }

  refreshCell(r, c) {
    const el = this.cellEls[r][c];
    el.classList.toggle("wall", this.board.get(r, c) === CellState.WALL);
    el.classList.toggle("marked", this.board.get(r, c) === CellState.MARKED);
    this.#describe(el, r, c);
  }

  // flag error cells, unflag the ones that got fixed
  showErrors(errors) {
    for (const key of this.errorCells) {
      if (!errors.has(key)) {
        const [r, c] = key.split(",").map(Number);
        this.cellEls[r][c].classList.remove("invalid");
        this.cellEls[r][c].removeAttribute("title");
        this.#describe(this.cellEls[r][c], r, c);
      }
    }
    for (const [key, kind] of errors) {
      const [r, c] = key.split(",").map(Number);
      const el = this.cellEls[r][c];
      el.classList.add("invalid");
      el.title = BoardView.ERROR_TEXT[kind];
      this.#describe(el, r, c);
    }
    this.errorCells = new Set(errors.keys());
  }

  static ERROR_TEXT = {
    "too-small": "Property is too small and cannot grow any further",
    "too-large": "Property is larger than the house number",
    "multi-house": "More than one house shares a property",
    "wall-block": "Walls may not fill a whole 2×2 block",
  };
}

// screen switching
function showScreen(id) {
  for (const el of document.querySelectorAll(".screen")) {
    el.hidden = el.id !== id;
  }
}
