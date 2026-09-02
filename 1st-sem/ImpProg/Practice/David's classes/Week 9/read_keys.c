// gcc -W -Wall -Wextra -pedantic week4.c
#include <stdio.h>
#include <sys/select.h>
#include <termios.h>
#include <unistd.h>

static struct termios oldt, newt;

void init_terminal(void) {
  tcgetattr(STDIN_FILENO, &oldt);
  newt = oldt;
  newt.c_lflag &= ~(ICANON | ECHO);
  tcsetattr(STDIN_FILENO, TCSANOW, &newt);
}

void reset_terminal(void) { tcsetattr(STDIN_FILENO, TCSANOW, &oldt); }

// Returns the pressed key, or -1 if none pressed
int readkey(void) {
  struct timeval tv = {0L, 0L};
  fd_set fds;
  FD_ZERO(&fds);
  FD_SET(STDIN_FILENO, &fds);

  int r = select(STDIN_FILENO + 1, &fds, NULL, NULL, &tv);
  if (r > 0)
    return getchar();
  return -1;
}

int main(void) {
  init_terminal();
  printf("Press keys (WASD or space). Press 'q' to quit.\n");

  while (1) {
    int c = readkey();
    if (c == -1) {
      usleep(10000); // optional, 10ms sleep to avoid busy loop
      continue;
    }
    if (c == 'q')
      break;
    printf("Pressed: %c\n", c);
  }

  reset_terminal();
  return 0;
}
