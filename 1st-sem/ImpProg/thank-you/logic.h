#ifndef LOGIC_H
#define LOGIC_H

#include "terminal_draw.h"

#define BREADTH 60
#define TALLNESS 20
#define MAX_LEN 100
#define MAX_BITS 50

typedef struct {
  int h, v;
} Spot;

typedef struct {
  int h, v;
  int dh, dv;
  char sym;
  Color col;
  int live;
} Bit;

typedef struct {
  Spot segs[MAX_LEN];
  int len;
  int dh, dv;
} Beast;

int check_key(void);
char fetch_key(void);

void start_bits(Bit b[], int n);
void renew_bits(Bit b[], int n);
void show_intro(Bit b[], int n, int f);

void start_game(Beast *b, Spot *f);
void show_game(Beast *b, Spot f, int s);

#endif
