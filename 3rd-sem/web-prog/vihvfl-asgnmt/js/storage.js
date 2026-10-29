"use strict";

// saves + leaderboards in localStorage
class Store {
  static SAVES_KEY = "namelessKing.saves";
  static SCORES_KEY = "namelessKing.scores";
  static MAX_SCORES = 10;

  static #read(key, fallback) {
    try {
      return JSON.parse(localStorage.getItem(key)) ?? fallback;
    } catch {
      return fallback;
    }
  }

  // one save slot per player+level
  static saves() {
    return Store.#read(Store.SAVES_KEY, []);
  }

  static saveGame({ player, levelId, cells, elapsed }) {
    const saves = Store.saves().filter(
      (s) => !(s.player === player && s.levelId === levelId),
    );
    saves.push({
      id: `${player}|${levelId}`,
      player,
      levelId,
      cells,
      elapsed,
      savedAt: Date.now(),
    });
    localStorage.setItem(Store.SAVES_KEY, JSON.stringify(saves));
  }

  static deleteSave(id) {
    const saves = Store.saves().filter((s) => s.id !== id);
    localStorage.setItem(Store.SAVES_KEY, JSON.stringify(saves));
  }

  // per lvl (fastest first)

  static scores() {
    return Store.#read(Store.SCORES_KEY, {});
  }

  static scoresFor(levelId) {
    return Store.scores()[levelId] ?? [];
  }

  static addScore(levelId, { name, time }) {
    const scores = Store.scores();
    const list = scores[levelId] ?? [];
    list.push({ name, time, at: Date.now() });
    list.sort((a, b) => a.time - b.time);
    scores[levelId] = list.slice(0, Store.MAX_SCORES);
    localStorage.setItem(Store.SCORES_KEY, JSON.stringify(scores));
  }
}
