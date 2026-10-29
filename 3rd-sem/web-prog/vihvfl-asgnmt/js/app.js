"use strict";

// menu wiring + game flow
class App {
  constructor() {
    this.game = null;

    this.$name = document.querySelector("#player-name");
    this.$nameError = document.querySelector("#name-error");
    this.$levelOptions = document.querySelector("#level-options");
    this.$saveList = document.querySelector("#save-list");
    this.$noSaves = document.querySelector("#no-saves");
    this.$leaderboards = document.querySelector("#leaderboards");
    this.$saveFeedback = document.querySelector("#save-feedback");
  }

  init() {
    this.#renderLevelOptions();
    this.#renderSaves();
    this.#renderLeaderboards();
    this.#bindEvents();

    showScreen("screen-start");
  }

  #bindEvents() {
    document.querySelector("#start-form").addEventListener("submit", (e) => {
      e.preventDefault();
      this.#startNewGame();
    });
    this.$name.addEventListener("input", () => {
      if (this.$name.value.trim() !== "") this.$nameError.hidden = true;
    });

    const rules = document.querySelector("#rules-overlay");
    document.querySelector("#btn-rules").addEventListener("click", () => {
      rules.hidden = false;
    });
    document.querySelector("#btn-rules-close").addEventListener("click", () => {
      rules.hidden = true;
    });
    rules.addEventListener("click", (e) => {
      if (e.target === e.currentTarget) rules.hidden = true;
    });

    document
      .querySelector("#btn-save")
      .addEventListener("click", () => this.#saveGame());
    document
      .querySelector("#btn-menu")
      .addEventListener("click", () => this.#toMenu());
    document
      .querySelector("#btn-victory-menu")
      .addEventListener("click", () => {
        document.querySelector("#victory-overlay").hidden = true;
        this.#toMenu();
      });
  }

  // start screen

  #renderLevelOptions() {
    this.$levelOptions.innerHTML = "";
    for (const [i, level] of LEVELS.entries()) {
      const label = document.createElement("label");
      label.className = "level-option";
      const input = document.createElement("input");
      input.type = "radio";
      input.name = "level";
      input.value = level.id;
      input.checked = i === 0;
      const card = document.createElement("span");
      card.className = "level-card";
      card.textContent = level.name;
      const size = document.createElement("small");
      size.textContent = `${level.rows}×${level.cols}`;
      card.append(size);
      label.append(input, card);
      this.$levelOptions.append(label);
    }
  }

  #renderSaves() {
    this.$saveList.innerHTML = "";
    const saves = Store.saves();
    this.$noSaves.hidden = saves.length > 0;

    for (const save of saves) {
      const level = LEVELS.find((l) => l.id === save.levelId);
      const li = document.createElement("li");
      li.className = "save-item";

      const desc = document.createElement("span");
      desc.className = "save-desc";
      desc.textContent =
        `${save.player} — ${level ? level.name : save.levelId}, ` +
        `${GameTimer.format(save.elapsed)}, saved ${new Date(save.savedAt).toLocaleString()}`;

      li.append(
        desc,
        this.#button("Load", () => this.#loadGame(save), "btn-load"),
        this.#button("Delete", () => {
          Store.deleteSave(save.id);
          this.#renderSaves();
        }, "btn-danger"),
      );
      this.$saveList.append(li);
    }
  }

  #renderLeaderboards() {
    this.$leaderboards.innerHTML = "";
    for (const level of LEVELS) {
      const box = document.createElement("div");
      box.className = "board-scores";
      const h = document.createElement("h3");
      h.textContent = level.name;
      const ol = document.createElement("ol");
      const scores = Store.scoresFor(level.id);
      if (scores.length === 0) {
        const li = document.createElement("li");
        li.textContent = "No entries yet";
        li.className = "muted";
        ol.append(li);
      } else {
        for (const s of scores) {
          const li = document.createElement("li");
          li.textContent = `${s.name} — ${GameTimer.format(s.time)}`;
          ol.append(li);
        }
      }
      box.append(h, ol);
      this.$leaderboards.append(box);
    }
  }

  #button(text, onClick, variant = "") {
    const btn = document.createElement("button");
    btn.type = "button";
    btn.className = `btn ${variant}`;
    btn.textContent = text;
    btn.addEventListener("click", onClick);
    return btn;
  }

  // game flow

  #selectedLevel() {
    const id = document.querySelector('input[name="level"]:checked').value;
    return LEVELS.find((l) => l.id === id);
  }

  #startNewGame() {
    const name = this.$name.value.trim();
    this.$nameError.hidden = name !== "";
    if (name === "") {
      this.$name.focus();
      return;
    }
    this.#launch(new Game(name, this.#selectedLevel()));
  }

  #loadGame(save) {
    const level = LEVELS.find((l) => l.id === save.levelId);
    if (!level) return;
    const cells = Board.deserializeCells(save.cells, level.rows, level.cols);
    this.$name.value = save.player;
    this.#launch(new Game(save.player, level, cells, save.elapsed));
  }

  #launch(game) {
    if (this.game) this.game.timer.stop();
    this.game = game;
    document.querySelector("#hud-player").textContent = game.playerName;
    document.querySelector("#hud-level").textContent =
      `${game.level.name} (${game.level.rows}×${game.level.cols})`;
    document.querySelector("#victory-overlay").hidden = true;
    showScreen("screen-game");
    game.start();
  }

  #saveGame() {
    if (!this.game || this.game.finished) return;
    Store.saveGame({
      player: this.game.playerName,
      levelId: this.game.level.id,
      cells: this.game.board.serialize(),
      elapsed: this.game.timer.elapsed,
    });
    this.$saveFeedback.hidden = false;
  }

  #toMenu() {
    if (this.game) this.game.timer.stop();
    this.game = null;
    this.$saveFeedback.hidden = true;
    this.#renderSaves();
    this.#renderLeaderboards();
    showScreen("screen-start");
  }
}

new App().init();
