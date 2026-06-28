#include <locale.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>

#include "temperature.h"

typedef struct {
    const char *name;
    const char *file;
} Region;

static const Region regions[] = {
    {"Hungary", "maps/hungary.txt"},
    {"Great Hungarian Plain", "maps/great_hungarian_plain.txt"},
    {"Little Hungarian Plain", "maps/little_hungarian_plain.txt"},
    {"Transdanubian Hills", "maps/transdanubian_hills.txt"},
    {"Transdanubian Mountains", "maps/transdanubian_mountains.txt"},
    {"North Hungarian Mountains", "maps/north_hungarian_mountains.txt"},
    {"Western Borderland", "maps/western_borderland.txt"}};

enum { REGION_COUNT = sizeof(regions) / sizeof(regions[0]) };

static inline double fabs(double d)
{
    return d < 0.0 ? -d : d;
}

static bool is_close(double a, double b, double eps)
{
    if (a == TEMPERATUREMAP_MISSING || b == TEMPERATUREMAP_MISSING) {
        return false;
    }

    return fabs(a - b) < eps;
}

static bool is_valid_map(TemperatureMap map)
{
    return map.width >= 0 && map.height >= 0 && map.data != NULL;
}

typedef struct {
    int r, g, b;
    bool valid;
} RGBColor;

static RGBColor get_rgb_color(double v, double vmin, double vmax)
{
    if (v == TEMPERATUREMAP_MISSING) {
        return (RGBColor){0, 0, 0, false};
    }

    double t = 0.5;
    if (vmax != vmin) {
        t = (v - vmin) / (vmax - vmin);
    }

    if (t < 0.0) {
        t = 0.0;
    }
    if (t > 1.0) {
        t = 1.0;
    }

    /* Linear gradient: blue -> cyan -> green -> yellow -> red */
    int r = (int)(255 * t);
    int g = (int)(255 * (1.0 - fabs(t - 0.5) * 2.0));
    int b = (int)(255 * (1.0 - t));

    return (RGBColor){r, g, b, true};
}

/* Looping reader: always returns a number in [1..max]. */
static int read_menu_choice(int max)
{
    int choice = 0;

    for (;;) {
        if (scanf("%d", &choice) != 1) {
            int c;
            while ((c = getchar()) != '\n' && c != EOF) {
                /* discard */
            }
            printf("Invalid input, please enter a number.\n");
            continue;
        }

        if (choice < 1 || choice > max) {
            printf("Wrong choice, try again!\n");
            continue;
        }

        return choice;
    }
}


static void print_map(TemperatureMap map, int region_index)
{
    double min_t = temperaturemap_min(map);
    double max_t = temperaturemap_max(map);

    printf("\n--- %s temperature map, min: %.2f, max: %.2f ---\n",
           regions[region_index].name, min_t, max_t);

    for (int y = 0; y < map.height; y += 2) {
        int has_bottom = (y + 1 < map.height);

        for (int x = 0; x < map.width; x++) {
            double top_t = map.data[y][x];
            double bot_t =
                has_bottom ? map.data[y + 1][x] : TEMPERATUREMAP_MISSING;

            RGBColor fg = get_rgb_color(top_t, min_t, max_t);
            RGBColor bg = get_rgb_color(bot_t, min_t, max_t);

            double eps = 0.01;

            bool is_max =
                is_close(max_t, top_t, eps) || is_close(max_t, bot_t, eps);
            bool is_min =
                is_close(min_t, top_t, eps) || is_close(min_t, bot_t, eps);

            if (is_max) {
                printf("\033[1;97mH\033[0m");
            } else if (is_min) {
                printf("\033[1;97mL\033[0m");
            } else if (fg.valid && bg.valid) {
                printf("\033[38;2;%d;%d;%dm\033[48;2;%d;%d;%dm#\033[0m", fg.r,
                       fg.g, fg.b, bg.r, bg.g, bg.b);
            } else if (fg.valid) {
                printf("\033[38;2;%d;%d;%dm\"\033[0m", fg.r, fg.g, fg.b);
            } else if (bg.valid) {
                printf("\033[38;2;%d;%d;%dm,\033[0m", bg.r, bg.g, bg.b);
            } else {
                printf(" ");
            }
        }

        printf("\n");
    }
}

static void handle_region(int region_index)
{
    TemperatureMap map =
        temperaturemap_read_from_file(regions[region_index].file);

    if (!is_valid_map(map)) {
        printf("Failed to load map file: %s\n", regions[region_index].file);
        map = temperaturemap_free(map);
        return;
    }

    for (;;) {
        print_map(map, region_index);

        printf("\n=== %s ===\n", regions[region_index].name);
        printf("1) Apply average filter (3x3)\n");
        printf("2) Back to regions\n");
        printf("Your choice: ");

        int choice = read_menu_choice(2);

        if (choice == 2) {
            break;
        } else if (choice == 1) {
            for (int i = 0; i < 10; i++) {
                TemperatureMap avg = temperaturemap_average3x3(map);
                if (!is_valid_map(avg)) {
                    printf("Failed to create averaged map.\n");
                    continue;
                }

                map = temperaturemap_free(map);
                map = avg;
            }
        }
    }

    map = temperaturemap_free(map);
}

int main(void)
{
    for (;;) {
        printf("\n=== Regions ===\n");
        for (int i = 0; i < REGION_COUNT; i++) {
            printf("%d) %s\n", i + 1, regions[i].name);
        }
        printf("%d) Exit\n", REGION_COUNT + 1);
        printf("Your choice: ");

        int choice = read_menu_choice(REGION_COUNT + 1);
        if (choice == REGION_COUNT + 1) {
            break;
        }

        handle_region(choice - 1);
    }

    return 0;
}
