// ==========================================
// TASK 1: Array Methods
// ==========================================

// 1.a: Map each number to its square
const numbers = [5, 2, 15, -3, 6, -8, -2];
const squares = numbers.map((x) => x ** 2);
console.log("1.a Squares:", squares);

// 1.b: Find the smallest element using Infinity
const smallest = numbers.reduce((min, x) => Math.min(min, x), Infinity);
console.log("1.b Smallest:", smallest);

// 1.c: Find row index full of 0s (-1 if none)
const matrix = [
  [1, 0, 3],
  [0, 2, 0],
  [4, 5, 6],
  [0, 0, 0],
];
const zeroRowIdx = matrix.findIndex((row) => row.every((x) => x === 0));
console.log("1.c Zero row index:", zeroRowIdx);

// 1.d: Extract imdbID for movies made after 2010
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
  (item) => Number(item.Year) > 2010 && item.Type === "movie",
).map((item) => item.imdbID);

console.log("1.d Post-2010 movie IDs:", post2010Movies);

// ==========================================
// TASKS 2 & 3: DOM Tasks (Browser only)
// ==========================================
if (typeof document !== "undefined") {
  // ------------------------------------------
  // TASK 2: Color Sliders
  // ------------------------------------------
  const sliders = document.querySelectorAll('input[type="range"]');
  const [hueInput, satInput, lightInput] = sliders;
  const colorBtn = document.querySelector('button[type="button"]');
  const hslInput = document.querySelector('input[type="text"][readonly]');

  function getHslColor() {
    return `hsl(${hueInput.value}, ${satInput.value}%, ${lightInput.value}%)`;
  }

  // 2.a & 2.b: Set text & background color on button click
  colorBtn.addEventListener("click", () => {
    const hsl = getHslColor();
    hslInput.value = hsl; // 2.a
    document.body.style.backgroundColor = hsl; // 2.b
  });

  // 2.c: Immediately change background on slider movement
  sliders.forEach((slider) => {
    slider.addEventListener("input", () => {
      const hsl = getHslColor();
      hslInput.value = hsl;
      document.body.style.backgroundColor = hsl;
    });
  });

  // ------------------------------------------
  // TASK 3: Contact List (Single Event Handler)
  // ------------------------------------------
  const contactsContainer = document.querySelector("#contacts");

  contactsContainer.addEventListener("click", (e) => {
    // 3.b guard: If click wasn't on a button, do nothing
    if (!e.target.matches("button")) return;

    const btn = e.target;
    const section = btn.closest("section");

    // 3.a: Log button label and data-toggle attribute
    console.log(
      `Label: "${btn.innerText}", data-toggle: "${btn.dataset.toggle}"`,
    );

    // 3.b: Log the contact name
    const contactName = section.querySelector(".name").innerText;
    console.log(`Contact: ${contactName}`);

    // 3.c & 3.d: Show field matching data-toggle (email, address, phone)
    const fieldClass = btn.dataset.toggle;
    const fieldElement = section.querySelector(`.${fieldClass}`);
    if (fieldElement) {
      fieldElement.hidden = false;
    }
  });
}
