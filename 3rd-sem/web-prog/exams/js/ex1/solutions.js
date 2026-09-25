// a. Assign (map) its square value to each item in numbers array. (0.5 point)
const numbers = [5, 2, 15, -3, 6, -8, -2];
const squares = numbers.map((x) => (x, x ** 2));
console.log("\n--- Task a ---\n", squares);

// b. Find the smallest element of the numbers array! (You may use the Infinity value in JavaScript.) (0.5 point)
const smallest = numbers.reduce((min, x) => Math.min(min, x), Infinity);
console.log("\n--- Task b ---\n", smallest);

// c. Give that row idx of the matrix, which row is full of 0 value!
// If there is no such row, write -1 to the console! (1 point)
const matrix = [
  [1, 0, 3],
  [0, 2, 0],
  [4, 5, 6],
  [0, 0, 0],
];

const idx = matrix.findIndex((row) => row.every((x) => x === 0));
console.log("\n--- Task c ---\n", idx);

// d. Give those IMDB identifiers (imdbID) from the searchResults list,
// that belong to movies that are post-2010 (year field) and their type (Type) is movie. (1 point)
const searchResults = {
  Search: [
    {
      Title: "The Hobbit: An Unexpected Journey",
      Year: "2012",
      imdbID: "tt0903624",
      Type: "movie",
    },
    {
      Title: "The Hobbit: The Desolation of Smaug",
      Year: "2013",
      imdbID: "tt1170358",
      Type: "movie",
    },
    {
      Title: "The Hobbit: The Battle of the Five Armies",
      Year: "2014",
      imdbID: "tt2310332",
      Type: "movie",
    },
    {
      Title: "The Hobbit",
      Year: "1977",
      imdbID: "tt0077687",
      Type: "movie",
    },
    {
      Title: "Lego the Hobbit: The Video Game",
      Year: "2014",
      imdbID: "tt3584562",
      Type: "game",
    },
    {
      Title: "The Hobbit",
      Year: "1966",
      imdbID: "tt1686804",
      Type: "movie",
    },
    {
      Title: "The Hobbit",
      Year: "2003",
      imdbID: "tt0395578",
      Type: "game",
    },
    {
      Title: "A Day in the Life of a Hobbit",
      Year: "2002",
      imdbID: "tt0473467",
      Type: "movie",
    },
    {
      Title: "The Hobbit: An Unexpected Journey - The Company of Thorin",
      Year: "2013",
      imdbID: "tt3345514",
      Type: "movie",
    },
    {
      Title: "The Hobbit: The Swedolation of Smaug",
      Year: "2014",
      imdbID: "tt4171362",
      Type: "movie",
    },
  ],
  totalResults: "51",
  Response: "True",
};

const post2k10 = searchResults.Search.filter(
  (x) => Number(x.Year) > 2010 && x.Type === "movie",
).map((x) => x.imdbID);
console.log("\n--- Task d ---\n", post2k10);
