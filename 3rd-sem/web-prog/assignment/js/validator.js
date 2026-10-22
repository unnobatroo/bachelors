"use strict";

const ErrorKind = Object.freeze({
  TOO_SMALL: "too-small",
  TOO_LARGE: "too-large",
  MULTI_HOUSE: "multi-house",
  WALL_BLOCK: "wall-block",
});

class BoardAnalyzer {
  constructor(board) {
    this.board = board;
  }

  // flood fill into connected regions matching pred
  regions(pred) {
    const b = this.board;
    const seen = Array.from({ length: b.rows }, () =>
      new Array(b.cols).fill(false),
    );
    const regions = [];

    for (let r = 0; r < b.rows; r++) {
      for (let c = 0; c < b.cols; c++) {
        if (seen[r][c] || !pred(r, c)) continue;
        const cells = [];
        const houses = [];
        const stack = [[r, c]];
        seen[r][c] = true;
        while (stack.length) {
          const [cr, cc] = stack.pop();
          cells.push([cr, cc]);
          if (b.isHouse(cr, cc)) houses.push([cr, cc]);
          for (const [nr, nc] of b.neighbours(cr, cc)) {
            if (!seen[nr][nc] && pred(nr, nc)) {
              seen[nr][nc] = true;
              stack.push([nr, nc]);
            }
          }
        }
        regions.push({ cells, houses });
      }
    }
    return regions;
  }

  flagRegion(errors, cells, kind) {
    for (const [r, c] of cells) errors.set(Board.key(r, c), kind);
  }

  // can it still grow?
  #canGrow(region) {
    const b = this.board;
    return region.cells.some(([r, c]) =>
      [...b.neighbours(r, c)].some(
        ([nr, nc]) => b.get(nr, nc) === CellState.UNDECIDED,
      ),
    );
  }

  // {errors: cellKey -> kind, solved}
  analyze() {
    const b = this.board;
    const errors = new Map();
    const isProperty = (r, c) =>
      b.get(r, c) === CellState.MARKED || b.isHouse(r, c);
    const propertyRegions = this.regions(isProperty);

    for (const region of propertyRegions) {
      if (region.houses.length > 1) {
        this.flagRegion(errors, region.cells, ErrorKind.MULTI_HOUSE);
      } else if (region.houses.length === 1) {
        const [hr, hc] = region.houses[0];
        const needed = b.houseAt.get(Board.key(hr, hc));

        if (region.cells.length > needed) {
          this.flagRegion(errors, region.cells, ErrorKind.TOO_LARGE);
        } else if (region.cells.length < needed && !this.#canGrow(region)) {
          this.flagRegion(errors, region.cells, ErrorKind.TOO_SMALL);
        }
      }
    }

    let hasWallBlock = false;
    for (let r = 0; r < b.rows - 1; r++) {
      for (let c = 0; c < b.cols - 1; c++) {
        const quad = [
          [r, c],
          [r + 1, c],
          [r, c + 1],
          [r + 1, c + 1],
        ];

        if (quad.every(([qr, qc]) => b.get(qr, qc) === CellState.WALL)) {
          this.flagRegion(errors, quad, ErrorKind.WALL_BLOCK);
          hasWallBlock = true;
        }
      }
    }

    const solved =
      !hasWallBlock &&
      this.#allCellsDecided() &&
      propertyRegions.every(
        (r) =>
          r.houses.length === 1 &&
          r.cells.length === b.houseAt.get(Board.key(...r.houses[0])),
      ) &&
      this.regions((r, c) => b.get(r, c) === CellState.WALL).length === 1;

    return { errors, solved };
  }

  #allCellsDecided() {
    const b = this.board;
    for (let r = 0; r < b.rows; r++) {
      for (let c = 0; c < b.cols; c++) {
        if (!b.isHouse(r, c) && b.get(r, c) === CellState.UNDECIDED)
          return false;
      }
    }
    return true;
  }
}
