// ==========================================
// TASK 1.a
// ==========================================
const numbers = [5, 2, 15, -3, 6, -8, -2];
const squares = numbers.map((x) => x ** 2);
console.log(squares);

// ==========================================
// TASK 1.b
// ==========================================
const smallest = numbers.reduce((min, x) => Math.min(min, x), Infinity);
console.log(smallest);

// ==========================================
// TASK 1.c
// ==========================================
const matrix = [
  [1, 0, 3],
  [0, 2, 0],
  [4, 5, 6],
  [0, 0, 0],
];
const zeroRowIdx = matrix.findIndex((row) => row.every((x) => x === 0));
console.log(zeroRowIdx);

// ==========================================
// TASK 1.d
// ==========================================
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
    { Title: "The Hobbit", Year: "1977", imdbID: "tt0077687", Type: "movie" },
    {
      Title: "Lego the Hobbit: The Video Game",
      Year: "2014",
      imdbID: "tt3584562",
      Type: "game",
    },
    { Title: "The Hobbit", Year: "1966", imdbID: "tt1686804", Type: "movie" },
    { Title: "The Hobbit", Year: "2003", imdbID: "tt0395578", Type: "game" },
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
const post2010Movies = searchResults.Search.filter(
  (x) => Number(x.Year) > 2010 && x.Type === "movie",
).map((x) => x.imdbID);
console.log(post2010Movies);

// ==========================================
// TASKS 2 & 3 (Browser DOM environment)
// ==========================================
if (typeof document !== "undefined") {
  // ==========================================
  // TASK 2.a
  // ==========================================
  const sliders = document.querySelectorAll('input[type="range"]');
  const [hue, sat, light] = sliders;
  const colorBtn = document.querySelector('button[type="button"]');
  const hslInput = document.querySelector('input[type="text"][readonly]');

  colorBtn.addEventListener("click", () => {
    hslInput.value = `hsl(${hue.value}, ${sat.value}%, ${light.value}%)`;
  });

  // ==========================================
  // TASK 2.b
  // ==========================================
  colorBtn.addEventListener("click", () => {
    document.body.style.backgroundColor = `hsl(${hue.value}, ${sat.value}%, ${light.value}%)`;
  });

  // ==========================================
  // TASK 2.c
  // ==========================================
  sliders.forEach((slider) => {
    slider.addEventListener("input", () => {
      document.body.style.backgroundColor = `hsl(${hue.value}, ${sat.value}%, ${light.value}%)`;
    });
  });

  // ==========================================
  // TASK 3.a
  // ==========================================
  const contacts = document.querySelector("#contacts");

  contacts.addEventListener("click", (e) => {
    if (e.target.matches("button")) {
      console.log(e.target.innerText, e.target.dataset.toggle);
    }
  });

  // ==========================================
  // TASK 3.b
  // ==========================================
  contacts.addEventListener("click", (e) => {
    if (e.target.matches("button")) {
      const name = e.target.closest("section").querySelector(".name").innerText;
      console.log(name);
    }
  });

  // ==========================================
  // TASK 3.c
  // ==========================================
  contacts.addEventListener("click", (e) => {
    if (e.target.matches("button")) {
      e.target.closest("section").querySelector(".email").hidden = false;
    }
  });

  // ==========================================
  // TASK 3.d
  // ==========================================
  contacts.addEventListener("click", (e) => {
    if (e.target.matches("button")) {
      const field = e.target.dataset.toggle;
      e.target.closest("section").querySelector(`.${field}`).hidden = false;
    }
  });
}
