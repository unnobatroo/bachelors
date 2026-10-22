"use strict";

// ticks once a second
class GameTimer {
  constructor(onTick) {
    this.elapsed = 0;
    this.onTick = onTick;
    this.intervalId = null;
  }

  start(seconds = 0) {
    this.elapsed = seconds;
    this.stop();
    this.onTick(this.elapsed);
    this.intervalId = setInterval(() => {
      this.elapsed += 1;
      this.onTick(this.elapsed);
    }, 1000);
  }

  stop() {
    if (this.intervalId !== null) {
      clearInterval(this.intervalId);
      this.intervalId = null;
    }
  }

  static format(seconds) {
    const m = Math.floor(seconds / 60);
    const s = String(seconds % 60).padStart(2, "0");
    return `${m}:${s}`;
  }
}
