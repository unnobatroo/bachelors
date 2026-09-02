#include "terminal_draw.h"
#include <stdio.h>
#include <stdlib.h>

typedef struct Particle {
  int x_coord;
  int y_coord;
  int x_direction;
  int y_direction;
  Color colour;
} Particle;

void draw_borders(int width, int height);
int random_in_range(int lowest, int highest);
Particle random_particle(int width, int height);
void draw_particles(Particle *particles, int count);
void move_particles(Particle *particles, int count, int width, int height);
int is_colliding(Particle *particles, int count, int index);
void change_colors(Particle *particles, int count);

int main(void) {

  int screen_width, screen_height;
  get_screen_size(&screen_width, &screen_height);

  int num_particles = 0;

  if (scanf("%d", &num_particles) != 1 || num_particles <= 0) {
    fprintf(stderr,
            "Error: Invalid number of particles entered.\n");
    return 1;
  }

  Particle *particles = (Particle *)malloc(num_particles * sizeof(Particle));
  if (particles == NULL) {
    fprintf(stderr, "Failed to allocate memory for particles.\n");
    return 1;
  }

  for (int i = 0; i < num_particles; ++i) {
    particles[i] = random_particle(screen_width, screen_height);
  }

  clear_screen();

  for (int iteration = 0; iteration < 100; ++iteration) {
    clear_screen();
    draw_borders(screen_width, screen_height);
    draw_particles(particles, num_particles);
    move_particles(particles, num_particles, screen_width, screen_height);
    change_colors(particles, num_particles);
    sleep_ms(300);
  }

  free(particles);
  return 0;
}

int random_in_range(int lowest, int highest) {
  if (lowest > highest) {
    int temp = lowest;
    lowest = highest;
    highest = temp;
  }
  long long range = (long long)highest - lowest + 1;
  if (range <= 0)
    return lowest;

  return (int)((rand() % range) + lowest);
}

void draw_borders(int width, int height) {
  for (int i = 0; i < width; ++i) {
    draw_char(i, 0, '-', BLUE);
    draw_char(i, height - 1, '-', BLUE);
  }
  for (int i = 1; i < height - 1; ++i) {
    draw_char(0, i, '|', BLUE);
    draw_char(width - 1, i, '|', BLUE);
  }
}

// Creates a particle with random position, direction, and color
Particle random_particle(int width, int height) {
  Particle p;

  p.x_coord = random_in_range(1, width - 2);
  p.y_coord = random_in_range(1, height - 2);

  p.x_direction = random_in_range(-1, 1);
  p.y_direction = random_in_range(-1, 1);

  // Color: Must be one of the valid Color enum values (BLACK to WHITE)
  p.colour = (Color)random_in_range(BLACK, WHITE);
  return p;
}

// Draws all particles in the array
void draw_particles(Particle *particles, int count) {
  for (int i = 0; i < count; ++i) {
    draw_char(particles[i].x_coord, particles[i].y_coord, '*',
              particles[i].colour);
  }
}

void move_particles(Particle *particles, int count, int width, int height) {
  for (int i = 0; i < count; ++i) {
    int next_x = particles[i].x_coord + particles[i].x_direction;
    int next_y = particles[i].y_coord + particles[i].y_direction;

    if (next_x <= 0 || next_x >= width - 1) {
      particles[i].x_direction *= -1;
      next_x = particles[i].x_coord + particles[i].x_direction;
    }

    if (next_y <= 0 || next_y >= height - 1) {
      particles[i].y_direction *= -1; // Reverse Y direction
      next_y = particles[i].y_coord + particles[i].y_direction;
    }

    particles[i].x_coord = next_x;
    particles[i].y_coord = next_y;
  }
}

int is_colliding(Particle *particles, int count, int index) {
  for (int i = 0; i < count; ++i) {
    if (i != index) { // Do not check the particle against itself
      if (particles[i].x_coord == particles[index].x_coord &&
          particles[i].y_coord == particles[index].y_coord) {
        return 1;
      }
    }
  }
  return 0; // No collision
}

// Changes the color of a particle if it is colliding
void change_colors(Particle *particles, int count) {
  for (int i = 0; i < count; ++i) {
    if (is_colliding(particles, count, i)) {
      int next_color_index =
          (particles[i].colour - BLACK + 1) % NUM_COLORS;
      particles[i].colour = (Color)(next_color_index + BLACK);
    }
  }
}
