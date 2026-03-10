#include "logic.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <time.h>

#ifndef _WIN32
#include <fcntl.h>
#include <termios.h>
#include <unistd.h>

int check_key(void) {
  struct termios old, new;
  int ch, oldf;

  tcgetattr(STDIN_FILENO, &old);
  new = old;

  new.c_lflag &= ~(ICANON | ECHO);
  tcsetattr(STDIN_FILENO, TCSANOW, &new);

  oldf = fcntl(STDIN_FILENO, F_GETFL, 0);
  fcntl(STDIN_FILENO, F_SETFL, oldf | O_NONBLOCK);

  ch = getchar();

  tcsetattr(STDIN_FILENO, TCSANOW, &old);
  fcntl(STDIN_FILENO, F_SETFL, oldf);

  if (ch != EOF) {
    ungetc(ch, stdin);
    return 1;
  }

  return 0;
}

char fetch_key(void) {
  struct termios old, new;
  char ch;

  tcgetattr(STDIN_FILENO, &old);
  new = old;

  new.c_lflag &= ~(ICANON | ECHO);
  tcsetattr(STDIN_FILENO, TCSANOW, &new);

  ch = getchar();

  tcsetattr(STDIN_FILENO, TCSANOW, &old);

  return ch;
}
#else
int check_key(void) { return kbhit(); }
char fetch_key(void) { return getch(); }
#endif

void start_bits(Bit b[], int n) {
  for (int i = 0; i < n; i++) {
    b[i].h = rand() % BREADTH;
    b[i].v = rand() % TALLNESS;

    b[i].dh = (rand() % 3) - 1;
    b[i].dv = (rand() % 3) - 1;

    if (b[i].dh == 0 && b[i].dv == 0) {
      b[i].dh = 1;
    }

    int r = rand() % 3;
    b[i].sym = (r == 0) ? '*' : (r == 1) ? '+' : '.';

    Color cols[] = {CYAN, YELLOW, MAGENTA, WHITE};
    b[i].col = cols[rand() % 4];
    b[i].live = 1;
  }
}

void renew_bits(Bit b[], int n) {
  for (int i = 0; i < n; i++) {
    b[i].h += b[i].dh;
    b[i].v += b[i].dv;

    if (b[i].h <= 0 || b[i].h >= BREADTH - 1) {
      b[i].dh *= -1;
    }

    if (b[i].v <= 0 || b[i].v >= TALLNESS - 1) {
      b[i].dv *= -1;
    }
  }
}

void show_intro(Bit b[], int n, int f) {
  clear_screen();

  for (int i = 0; i < BREADTH; i++) {
    draw_char(i, 0, '#', BLUE);
    draw_char(i, TALLNESS - 1, '#', BLUE);
  }

  for (int i = 0; i < TALLNESS; i++) {
    draw_char(0, i, '#', BLUE);
    draw_char(BREADTH - 1, i, '#', BLUE);
  }

  for (int i = 0; i < n; i++) {
    draw_char(b[i].h, b[i].v, b[i].sym, b[i].col);
  }

  char *t1 = "THANK YOU PROF. BRUNNER!";
  char *t2 = "for great explanations and after-lecture consultations";
  char *t3 = "Press [SPACE] to compile the game Snake...";
  char *s = (f % 10 < 5) ? "o7  o7  o7" : "o/  o/  o/";

  print_at((BREADTH - strlen(t1)) / 2, TALLNESS / 2 - 3, "%s", t1);
  print_at((BREADTH - strlen(t2)) / 2, TALLNESS / 2 - 1, "%s", t2);
  print_at((BREADTH - strlen(s)) / 2, TALLNESS / 2 + 2, "%s", s);
  print_at((BREADTH - strlen(t3)) / 2, TALLNESS - 3, "%s", t3);
}

void start_game(Beast *b, Spot *f) {
  b->len = 5;
  b->dh = 1;
  b->dv = 0;

  int sh = BREADTH / 2;
  int sv = TALLNESS / 2;

  for (int i = 0; i < b->len; i++) {
    b->segs[i].h = sh - i;
    b->segs[i].v = sv;
  }

  f->h = rand() % (BREADTH - 2) + 1;
  f->v = rand() % (TALLNESS - 2) + 1;
}

void show_game(Beast *b, Spot f, int s) {
  clear_screen();

  for (int i = 0; i < BREADTH; i++) {
    draw_char(i, 0, '#', BLUE);
    draw_char(i, TALLNESS - 1, '#', BLUE);
  }

  for (int i = 0; i < TALLNESS; i++) {
    draw_char(0, i, '#', BLUE);
    draw_char(BREADTH - 1, i, '#', BLUE);
  }

  print_at(2, 0, " IMPERATIVE SNAKE | Score: %d ", s);
  draw_char(f.h, f.v, 'C', RED);

  for (int i = 0; i < b->len; i++) {
    draw_char(b->segs[i].h, b->segs[i].v, (i == 0 ? 'O' : 'o'), GREEN);
  }
}
