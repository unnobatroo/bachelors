#include "logic.h"
#include "terminal_draw.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

int main(void) {
  srand(time(NULL));

  Bit bits[MAX_BITS];
  Beast bst;
  Spot fd;

  int f = 0;
  int m = 0;
  int go = 1;
  int s = 0;

  start_bits(bits, MAX_BITS);

  while (m == 0) {
    if (check_key()) {
      char k = fetch_key();

      if (k == ' ')
        m = 1;
      if (k == 'q')
        return 0;
    }

    renew_bits(bits, MAX_BITS);
    show_intro(bits, MAX_BITS, f);

    f++;
    sleep_ms(100);
  }

  start_game(&bst, &fd);

  while (go) {
    if (check_key()) {
      char k = fetch_key();

      if ((k == 'w' || k == 'W') && bst.dv != 1) {
        bst.dh = 0;
        bst.dv = -1;
      }
      if ((k == 's' || k == 'S') && bst.dv != -1) {
        bst.dh = 0;
        bst.dv = 1;
      }
      if ((k == 'a' || k == 'A') && bst.dh != 1) {
        bst.dh = -1;
        bst.dv = 0;
      }
      if ((k == 'd' || k == 'D') && bst.dh != -1) {
        bst.dh = 1;
        bst.dv = 0;
      }
      if (k == 'q')
        go = 0;
    }

    for (int i = bst.len - 1; i > 0; i--) {
      bst.segs[i] = bst.segs[i - 1];
    }

    bst.segs[0].h += bst.dh;
    bst.segs[0].v += bst.dv;

    if (bst.segs[0].h <= 0 || bst.segs[0].h >= BREADTH - 1 ||
        bst.segs[0].v <= 0 || bst.segs[0].v >= TALLNESS - 1) {
      go = 0;
    }

    for (int i = 1; i < bst.len; i++) {
      if (bst.segs[0].h == bst.segs[i].h && bst.segs[0].v == bst.segs[i].v) {
        go = 0;
      }
    }

    if (bst.segs[0].h == fd.h && bst.segs[0].v == fd.v) {
      s += 10;
      if (bst.len < MAX_LEN)
        bst.len++;

      fd.h = rand() % (BREADTH - 2) + 1;
      fd.v = rand() % (TALLNESS - 2) + 1;
    }

    show_game(&bst, fd, s);
    sleep_ms(100);
  }

  clear_screen();

  char *m1 = "GAME OVER";
  char *m2 = "Final Score: %d";
  char *m3 = "Thanks for making my first-ever university semester great!";
  char score_buf[50];

  sprintf(score_buf, m2, s);

  print_at((BREADTH - strlen(m1)) / 2, TALLNESS / 2, "%s", m1);
  print_at((BREADTH - strlen(score_buf)) / 2, TALLNESS / 2 + 1, "%s",
           score_buf);
  print_at((BREADTH - strlen(m3)) / 2, TALLNESS / 2 + 3, "%s", m3);
  print_at(0, TALLNESS, "");

  return 0;
}
