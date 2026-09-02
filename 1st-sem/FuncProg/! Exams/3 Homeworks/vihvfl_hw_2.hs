{- NAME: Jaloliddin Ismailov
Neptun: VIHVFL -}

data Country = Country
  { countryName :: String,
    capital :: String
  }
  deriving (Show)

data Continent = Continent
  { contName :: String,
    countries :: [Country]
  }
  deriving (Show)

-- Countries:
macedonia = Country {countryName = "Macedonia", capital = "Skopje"}

hungary = Country {countryName = "Hungary", capital = "Budapest"}

spain = Country {countryName = "Spain", capital = "Madrid"}

brazil = Country {countryName = "Brazil", capital = "Brasilia"}

chile = Country {countryName = "Chile", capital = "Santiago"}

argentina = Country {countryName = "Argentina", capital = "Buenos Aires"}

china = Country {countryName = "China", capital = "Beijing"}

india = Country {countryName = "India", capital = "New Delhi"}

japan = Country {countryName = "Japan", capital = "Tokyo"}

uzbekistan = Country {countryName = "Uzbekistan", capital = "Tashkent"}

-- Continents:
europe = Continent {contName = "Europe", countries = [macedonia, hungary, spain]}

asia = Continent {contName = "Asia", countries = [china, india, japan]}

southAmerica = Continent {contName = "South America", countries = [argentina, brazil, chile]}

centralAsia = Continent {contName = "Central Asia", countries = [uzbekistan]}

-- List of continents:
allContinents :: [Continent]
allContinents = [europe, asia, centralAsia, southAmerica]

{- Task1: Record
Write a function that takes a String (a list of characters) and
a list of Continents and returns a list of tuples (Capital name,
Country name, Continent name) for all capitals that start with
any of the characters in the given String. -}

f1 :: String -> [Continent] -> [(String, String, String)]
f1 lettersList allContinentsList =
  [ (capital country, countryName country, contName continent)
    | continent <- allContinentsList,
      country <- countries continent,
      head (capital country) `elem` lettersList
  ]

{- Test cases:
f1 "SB" allContinents ->
  [("Skopje", "Macedonia", "Europe"), ("Budapest", "Hungary", "Europe"),
  ("Brasilia", "Brazil", "South America"), ("Santiago", "Chile", "South America"),
  ("Buenos Aires", "Argentina", "South America")]
f1 "M" allContinents -> [("Madrid", "Spain", "Europe")]
f1 "T" allContinents -> [("Tokyo", "Japan", "Asia"), ("Tashkent", "Uzbekistan", "Central Asia")]
f1 "" allContinents -> [] -}

data Tree a where
  Leaf :: Tree a
  Node :: a -> (Tree a) -> (Tree a) -> Tree a
  deriving (Show)

-- Trees:
tree1 = Node 15 (Node 5 Leaf Leaf) (Node 10 Leaf Leaf)

tree2 = Node 10 (Node 3 Leaf Leaf) (Node 4 Leaf Leaf)

tree3 =
  Node
    12
    (Node 2 Leaf Leaf)
    (Node 4 Leaf (Node 6 Leaf Leaf))

tree4 =
  Node
    21
    (Node 6 (Node 2 Leaf Leaf) (Node 4 Leaf Leaf))
    (Node 15 (Node 7 Leaf Leaf) (Node 8 Leaf Leaf))

tree5 =
  Node
    30
    (Node 10 (Node 4 Leaf Leaf) (Node 6 Leaf Leaf))
    (Node 5 Leaf (Node 5 Leaf Leaf))

tree6 =
  Node
    50
    (Node 20 (Node 5 Leaf Leaf) (Node 15 Leaf Leaf))
    (Node 25 Leaf (Node 10 Leaf (Node 10 Leaf Leaf)))

tree7 =
  Node
    40
    (Node 10 Leaf Leaf)
    ( Node
        30
        (Node 10 (Node 5 Leaf Leaf) (Node 5 Leaf Leaf))
        Leaf
    )

{- Task2: Tree
Define a function f2 :: Tree Int -> Int that returns the
number of nodes in the tree satisfying the following property:
The value stored at the node is equal to the sum of all values
in its left and right subtrees combined (i.e., not just the
immediate children, but the entire subtree under each child). -}

sumAndCount :: Tree Int -> (Int, Int)
sumAndCount Leaf = (0, 0)
sumAndCount (Node value left right) = (totalSum, totalCount)
  where
    (leftSum, leftCount) = sumAndCount left
    (rightSum, rightCount) = sumAndCount right

    propertyHolds = value == (leftSum + rightSum)
    currentCount = if propertyHolds then 1 else 0
    totalCount = leftCount + rightCount + currentCount
    totalSum = value + leftSum + rightSum

f2 :: Tree Int -> Int
f2 tree = snd (sumAndCount tree)

{- Test cases:
f2 tree1 -> 1
f2 tree2 -> 0
f2 tree3 -> 1
f2 tree4 -> 2
f2 tree5 -> 2
f2 tree6 -> 2
f2 tree7 -> 1 -}