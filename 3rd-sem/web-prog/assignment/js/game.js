"use strict";

// one running game: model + view + timer + mouse
class Game {
  constructor(playerName, level, cells = null, elapsed = 0) {
    this.playerName = playerName;
    this.level = level;
    this.board = new Board(level, cells);
    this.view = new BoardView(document.querySelector("#board"));
    this.timer = new GameTimer((s) => {
      document.querySelector("#hud-time").textContent = GameTimer.format(s);
    });
    this.finished = false;
    this.drag = null; // current drag gesture
    this.elapsed = elapsed;

    this.view.build(this.board);
    this.#bindBoardEvents();
    this.#revalidate();
  }

  start() {
    this.timer.start(this.elapsed);
  }

  #bindBoardEvents() {
    const el = this.view.container;
    el.addEventListener("mousedown", (e) => this.#onMouseDown(e));
    el.addEventListener("mouseover", (e) => this.#onMouseOver(e));
    el.addEventListener("contextmenu", (e) => e.preventDefault());
    el.addEventListener("dragstart", (e) => e.preventDefault());
    el.addEventListener("keydown", (e) => this.#onKeyDown(e));
    document.addEventListener("mouseup", () => {
      this.drag = null;
    });
  }

  #cellFromEvent(e) {
    const el = e.target.closest(".cell");
    if (!el) return null;
    return [Number(el.dataset.row), Number(el.dataset.col)];
  }

  #onMouseDown(e) {
    if (this.finished || (e.button !== 0 && e.button !== 2)) return;
    const pos = this.#cellFromEvent(e);
    if (!pos) return;
    const [r, c] = pos;
    if (this.board.isHouse(r, c)) return;

    // first cell hit picks paint vs erase
    const current = this.board.get(r, c);
    const toggle = e.button === 0 ? CellState.WALL : CellState.MARKED;
    const state = current === toggle ? CellState.UNDECIDED : toggle;
    this.drag = { state, visited: new Set(), button: e.button };
    this.#applyDrag(r, c);
  }

  #onMouseOver(e) {
    if (!this.drag || this.finished) return;
    const mask = this.drag.button === 0 ? 1 : 2;
    if (!(e.buttons & mask)) {
      this.drag = null;
      return;
    }
    const pos = this.#cellFromEvent(e);
    if (pos) this.#applyDrag(pos[0], pos[1]);
  }

  #applyDrag(r, c) {
    const key = Board.key(r, c);
    if (this.drag.visited.has(key) || this.board.isHouse(r, c)) return;
    this.drag.visited.add(key);

    const current = this.board.get(r, c);

    if (this.drag.button === 2 && current === CellState.WALL) return;
    if (current === this.drag.state) return;

    document.querySelector("#save-feedback").hidden = true;
    this.board.set(r, c, this.drag.state);
    this.view.refreshCell(r, c);
    this.#revalidate();
  }

  // Enter/Space toggles wall, M toggles property mark - same as mouse buttons
  #onKeyDown(e) {
    if (this.finished) return;
    const pos = this.#cellFromEvent(e);
    if (!pos) return;
    const [r, c] = pos;
    if (this.board.isHouse(r, c)) return;

    const button = e.key === "Enter" || e.key === " " ? 0
      : e.key === "m" || e.key === "M" ? 2 : null;
    if (button === null) return;
    e.preventDefault();

    const toggle = button === 0 ? CellState.WALL : CellState.MARKED;
    const state = this.board.get(r, c) === toggle ? CellState.UNDECIDED : toggle;
    this.drag = { state, visited: new Set(), button };
    this.#applyDrag(r, c);
    this.drag = null;
  }

  #revalidate() {
    const { errors, solved } = new BoardAnalyzer(this.board).analyze();
    this.view.showErrors(errors);
    if (solved) this.#win();
  }

  #win() {
    this.finished = true;
    this.timer.stop();
    Store.addScore(this.level.id, {
      name: this.playerName,
      time: this.timer.elapsed,
    });
    Store.deleteSave(`${this.playerName}|${this.level.id}`);

    document.querySelector("#victory-text").textContent =
      `${this.playerName} solved the ${this.level.name} level ` +
      `in ${GameTimer.format(this.timer.elapsed)}.`;
    document.querySelector("#victory-overlay").hidden = false;
    document.querySelector("#btn-victory-menu").focus();
  }
}
