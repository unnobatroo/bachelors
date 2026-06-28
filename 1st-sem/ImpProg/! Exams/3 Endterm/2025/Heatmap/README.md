# Temperature Map

Create a lightweight temperature-map engine that stores a 2D temperature grid
**entirely in memory** using **dynamic allocation**. Because the map size is
only known at runtime, you **shoudn't** use fixed-size arrays like
`double data[100][100]`. Instead, you must allocate memory based on the exact
width and height read from the input file.

A provided **main.c** program will load a selected region and display it as a
colored ASCII map. You need to implement the required library functions in
**temperature.h** and **temperature.c** so that main.c can compile and run.

Download the Temperature folder, and write your code inside this folder. Upon
finishing the exam, upload a zip file of this folder.

You must split your code into three files:

- **temperature.h**: struct definitions, constants, function declarations
- **temperature.c**: function implementations
- **main.c**: provided (do not edit unless absolutely necessary)

## File Format

Each map file is a plain text file:

```
width height
then height lines, each containing width doubles
```

The temperature data came from actual Hungarian weather sensors. To account for
the areas outside the observed region, we use **-999.0** to represent missing
data. In the functions below, you will need to treat this specially, for example
when computing the maximum or minimum temperature, these values need to be
ignored.

## The Header File `temperature.h` **(5 points)**

- Make sure to use the header guards.
- Define a struct named `TemperatureMap`, it must contain:
  - `int width`
  - `int height`
  - `double **data` (accessed as `data[y][x]`)
- Define a constant `TEMPERATUREMAP_MISSING` with the value -999.0
- Create function declarations as described below.

## The Implementation File `temperature.c`

Separate the program into compilation units: **main.c**, **temperature.c**, and
**temperature.h**. **(4 points)**

Implement the following functions:

### 1) `TemperatureMap temperaturemap_create(int width, int height)` **(10 points)**

- Allocate a 2D array using an **array-of-arrays** layout.
- On allocation failure, return an **invalid map** with a size of `0 x 0` and
  data set to `NULL`.
- Make sure to avoid memory leak. **(4 points)**

Data is represented as follows:

- First, there is a top level list with one entry per row. This has the type of
  `double**`.
- The length of the top level list equals to `height`.
- Each row is a list of temperature values, this is dynamically allocated, so
  has the type of `double *`.
- The length of each row equals to `width`.

A 3×3 temperature map looks like this:

    ```
           double**           width
      ^ ┌────────────┐    <───────────>
      │ │            │    ┌───┬───┬───┐
      │ │ double*  ──┼──> │ t │ t │ t │ double[3]
      │ │            │    └───┴───┴───┘
    h │ ├────────────┤
    e │ │            │    ┌───┬───┬───┐
    i │ │ double*  ──┼──> │ t │ t │ t │ double[3]
    g │ │            │    └───┴───┴───┘
    h │ ├────────────┤
    t │ │            │    ┌───┬───┬───┐
      │ │ double*  ──┼──> │ t │ t │ t │ double[3]
      │ │            │    └───┴───┴───┘
      v └────────────┘
    ```

**Alternative solution**

You may want to represent the map as a 2-dimensional array with the size of
100x100.

**However, using this representation, the points for dynamic memory managment
(allocation, checks, cleanup) can't be scored.**

### 2) `TemperatureMap temperaturemap_free(TemperatureMap map)` **(5 points)**

- Free all allocated memory (rows and row pointer array)
- After freeing, return an **invalid map**.

### 3) `bool temperaturemap_is_valid(TemperatureMap map)` **(2 points)**

- This function returns true if the temperature map is valid, i.e. the width
  and height are greater or equal than zero, and the `data` pointer is not
  `NULL`.

### 3) `TemperatureMap temperaturemap_read_from_file(const char *filepath)` **(10 points)**

- Open the file
- Read `width` and `height`
- Create a map using `temperaturemap_create(width, height)`
- Read `width * height` values into `map.data[y][x]`
- In case of errors, free everything and return an invalid map.
- You can assume that the file has the right format.

### 4) `double temperaturemap_min(TemperatureMap map)` and `double temperaturemap_max(TemperatureMap map)` **(5-5 points)**

- Return the minimum / maximum **temperature value** in the map. Ignore
  `TEMPERATUREMAP_MISSING` values from the computation.
- If the map is invalid, or there are no valid temperature values in it, return
  `TEMPERATUREMAP_MISSING`.

### 5) `TemperatureMap temperaturemap_average3x3(TemperatureMap in)` **(10 points)**

Create a new map where each cell is replaced with the average of its **3×3
neighborhood** as follows.

- If the input map is invalid or a new map cannot be allocated, return an
  invalid map.
- Otherwise, the result must have the same width/height as input.

For each value at (x,y) in the input do the following.

- If value is TEMPERATUREMAP_MISSING, the output map should have
  TEMPERATUREMAP_MISSING in that location.
- Otherwise take the values around (x,y) including itself. In the middle of the
  map this will be 9 values, the value at (x,y) and the 8 values surrounding it.
- At the edge and corners of the map, there are less values, e.g. in the top
  left corner there are only 4 values to deal with.
- Ignore TEMPERATUREMAP_MISSING values from this set.
- The value in the output (x,y) should be the average of the remaining valid
  values.

The idea is to 'smooth' out the temperature values in stored in a map.
