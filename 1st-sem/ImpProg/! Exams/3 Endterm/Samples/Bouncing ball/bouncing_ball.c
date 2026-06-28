#include "terminal_draw.h"
#include <stdbool.h>

/*
TODO: Create Position structure
 * Should contain:
 * - x coordinate (integer)
 * - y coordinate (integer)
 */

typedef struct {
  int x;
  int y;
} Position;

/*
TODO: Create Ball structure
 * Should contain:
 * - Position (struct Position)
 * - Fill color (enum Color from terminal_draw.h: BLACK=30, RED=31, GREEN=32,
 * YELLOW=33, BLUE=34, MAGENTA=35, CYAN=36, WHITE=37)
 * - Fill character (character)
 */

typedef struct {
  Position pos;
  enum Color colour;
  char fill_char;
} Ball;

/*
TODO: Implement draw_hline function
 * Parameters:
 * - Starting x position
 * - Starting y position
 * - Length of the line
 * - Character to draw with
 * - Color to draw with
 * Should use draw_char() from terminal_draw.h to draw a horizontal line
 */

// MUST INPUT VALID (WORKING) VARIABLES!!!
void draw_hline(int x, int y, int line_length, char character, Color colour) {
  for (int i = 0; i < line_length; ++i) {
    draw_char(x + i, y, character, colour);
  }
}

/*
TODO: Implement draw_vline function
 * Parameters:
 * - Starting x position
 * - Starting y position
 * - Length of the line
 * - Character to draw with
 * - Color to draw with
 * Should use draw_char() from terminal_draw.h to draw a vertical line
 */

// MUST INPUT VALID (WORKING) VARIABLES!!!
void draw_vline(int x, int y, int line_length, char character, Color colour) {
  for (int i = 0; i < line_length; ++i) {
    draw_char(x, y + i, character, colour);
  }
}

/*
TODO: Implement drawBall function
 * Parameters:
 * - Pointer to Ball struct
 * Should draw the ball in this exact pattern, centered at ball's position:
 *    XXX
 *   XXXXX
 *    XXX
 * Where X is the ball's fillChar and should be drawn using the ball's fillColor
 * Use draw_char() or draw_hline() to implement this
 */

// MUST INPUT VALID (WORKING) VARIABLES!!!
void drawBall(Ball *ball) {
  char c = ball->fill_char;
  Color colour = ball->colour;
  int x = ball->pos.x;
  int y = ball->pos.y;

  draw_hline(x - 1, y - 1, 3, c, colour);
  draw_hline(x - 2, y, 5, c, colour);
  draw_hline(x - 1, y + 1, 3, c, colour);
}

/*
TODO: Implement getNextColor function
 * Parameters:
 * - Current color (enum Color from terminal_draw.h)
 * Returns:
 * - Next color in sequence (cycling through all available colors)
 * Note: Use NUM_COLORS (defined as 8) for cycling through colors
 */

Color getNextColor(Color curr_colour) {
  int curr_idx = curr_colour - BLACK;
  int next_idx = ++curr_idx % NUM_COLORS;
  return (Color)(next_idx + BLACK);
}

/*
TODO: Implement getNextFillChar function
 * Parameters:
 * - Current character
 * Returns:
 * - Should alternate between 'O' and 'X'
 */

char getNextFillChar(char c) { return (c == 'O') ? 'X' : 'O'; }

/*
TODO: Implement isCollidingX function
 * Parameters:
 * - Pointer to Ball struct
 * - Screen width
 * Returns:
 * - true if ball is colliding with left or right border
 * Note: Consider the full width of the ball pattern (5 characters wide)
 * The ball's position is at its center
 */

bool isCollidingX(Ball *ball, int screenWidth) {
  if (ball->pos.x - 2 <= 0) {
    return true;
  }

  if (ball->pos.x + 2 >= screenWidth - 1) {
    return true;
  }

  return false;
}

/*
TODO: Implement isCollidingY function
 * Parameters:
 * - Pointer to Ball struct
 * - Screen height
 * Returns:
 * - true if ball is colliding with top or bottom border
 * Note: Consider the full height of the ball pattern (3 characters tall)
 * The ball's position is at its center
 */

bool isCollidingY(Ball *ball, int screenHeight) {
  if (ball->pos.y - 1 <= 0) {
    return true;
  }

  if (ball->pos.y + 1 >= screenHeight - 1) {
    return true;
  }

  return false;
}

int main(void) {
  int screenWidth, screenHeight;
  get_screen_size(&screenWidth, &screenHeight);

  // TODO: Initialize ball structure
  // - Position should be center of screen (screenWidth/2, screenHeight/2)
  // - Initial color should be RED
  // - Initial fill character should be 'O'

  Ball ball;
  ball.pos.x = screenWidth / 2;
  ball.pos.y = screenHeight / 2;
  ball.colour = RED;
  ball.fill_char = 'X';

  // TODO: Initialize dx and dy for ball movement
  // These control the direction and speed of the ball
  // Initially the ball should move one position right and down initially each
  // frame.

  int dx = 1;
  int dy = 1;

  while (1) {
    clear_screen();

    // TODO: Draw blue border around the screen
    // - Use draw_hline() for top and bottom borders with '-' character
    // - Use draw_vline() for left and right borders with '|' character
    // - All border elements should be BLUE color

    draw_hline(0, 0, screenWidth, '-', RED);
    draw_hline(0, screenHeight - 1, screenWidth, '-', YELLOW);
    draw_vline(0, 1, screenHeight - 2, '|', BLUE);
    draw_vline(screenWidth - 1, 1, screenHeight - 2, '|', MAGENTA);

    // TODO: Draw the ball at its current position using drawBall()
    drawBall(&ball);

    // TODO: Update ball position using dx and dy
    ball.pos.x += dx;
    ball.pos.y += dy;

    // TODO: Handle horizontal collisions
    // - Use isCollidingX() to detect
    // - On collision: reverse dx and change ball color using getNextColor()

    if (isCollidingX(&ball, screenWidth)) {
      dx = -dx;
      ball.colour = getNextColor(ball.colour);
    }

    // TODO: Handle vertical collisions
    // - Use isCollidingY() to detect
    // - On collision: reverse dy and change fill char using getNextFillChar()

    if (isCollidingY(&ball, screenHeight)) {
      dy = -dy;
      ball.fill_char = getNextFillChar(ball.fill_char);
    }

    // Debugging help - uncomment these lines if needed:
    // print_at(2, 2, "Ball position   x, y: %3d, %3d", ball.pos.x, ball.pos.y);
    // print_at(2, 3, "Window width, height: %3d, %3d", screenWidth,
    // screenHeight);

    sleep_ms(30); // Control animation speed (1000ms = 1 second)
  }

  return 0;
}
