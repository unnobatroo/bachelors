#include "terminal_draw.h"

struct Coordinate
{
  int x, y;
};

typedef struct Coordinate Coordinate;

struct Snake
{
  Coordinate* body;
  int length;
};

typedef struct Snake Snake;

Snake init_snake(int length)
{
  Snake snake;

  snake.body = malloc(length * sizeof(Coordinate));
  snake.length = length;

  for (int i = 0; i < length; ++i)
  {
    snake.body[length - i - 1].x = i;
    snake.body[length - i - 1].y = 0;
  }

  return snake;
}

void move_snake(Snake snake, char direction)
{
  int head_x = snake.body[0].x;
  int head_y = snake.body[0].y;

  switch (direction)
  {
    case 'w': head_y--; break;
    case 'a': head_x--; break;
    case 's': head_y++; break;
    case 'd': head_x++; break;
  }

  for (int i = snake.length - 1; i > 0; --i)
    snake.body[i] = snake.body[i - 1];

  snake.body[0].x = head_x;
  snake.body[0].y = head_y;
}

void draw_snake(Snake snake)
{
  for (int i = 0; i < snake.length; ++i)
    draw_char(snake.body[i].x, snake.body[i].y, '0', GREEN);

  draw_char(snake.body[0].x, snake.body[0].y, '8', YELLOW);
}

void dest_snake(Snake snake)
{
  free(snake.body);
}

int main()
{
  Snake snake = init_snake(5);

  char movement[] = "dddddddssssddddwwwaaaaa";

  for (int i = 0; movement[i] != '\0'; ++i)
  {
    clear_screen();

    draw_snake(snake);
    move_snake(snake, movement[i]);

    sleep_ms(250);
  }

  dest_snake(snake);
}