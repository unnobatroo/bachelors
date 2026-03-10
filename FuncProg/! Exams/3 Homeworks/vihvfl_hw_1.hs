main :: IO ()
{-
NAME: Jaloliddin Ismailov
Neptun code: VIHVFL
-}

{-
Task: Bingo Checker

In this homework, you will implement a bingo checker for a dynamic-sized bingo matrix.
A bingo matrix is represented as a list of lists of integers, where each integer
is either 1 (indicating a marked cell) or 0 (indicating an unmarked cell).

A bingo occurs when any row, column, or diagonal is completely filled with 1's.
The size of the bingo matrix can vary, but it will always be a n x n matrix.

The task is divided into four subtasks. Each subtask requires you to implement a specific function.
You can use any number of helper functions as needed. Each subtask worth 25 points.
You have to defend your solution in person after the submission deadline.
-}

{-
Subtask 1. checkRow
This function takes a single row of the bingo matrix and returns True if all cells in that row
are 1's, and False otherwise.
-}

checkRow :: [Int] -> Bool
checkRow = all (== 1)

{-Test cases-}
-- main = do
--   print (checkRow [1, 1, 1]) -- True
--   print (checkRow [1, 0, 1]) -- False
--   print (checkRow [1, 0, 1, 0, 1]) -- False
--   print (checkRow [1, 1, 1, 1, 1]) -- True

{-
Subtask 2: transpose
This function transposes the bingo matrix, converting rows into columns and vice versa.
This will help in checking columns for bingo.
-}

transpose :: [[Int]] -> [[Int]]
transpose [] = []
transpose ([] : _) = []
transpose xxs = map head xxs : transpose (map tail xxs)

{-Test cases-}
-- main = do
--   print (transpose [[1, 1], [0, 0]]) -- [[1,0],[1,0]]
--   print (transpose [[1, 0, 1], [0, 1, 0], [1, 0, 1]]) -- [[1,0,1],[0,1,0],[1,0,1]]
--   print (transpose [[1, 0, 0], [1, 0, 0], [1, 1, 1]]) -- [[1,1,1],[0,0,1],[0,0,1]]
--   print (transpose [[1]]) -- [[1]]

{-
Subtask 3: checkDiagonal
This function checks both the main diagonal (from the top-left to the bottom-right)
and the anti-diagonal (from the top-right to the bottom-left) of the bingo matrix.
It returns True if either diagonal is completely filled with 1's, and False otherwise.
-}

checkDiagonal :: [[Int]] -> Bool
checkDiagonal matrix =
  let n = length matrix - 1
      mainDiag = [row !! i | (i, row) <- zip [0..] matrix]
      antiDiag = [row !! (n - i) | (i, row) <- zip [0..] matrix]
   in checkRow mainDiag || checkRow antiDiag

{-Test cases-}
-- main = do
--   print (checkDiagonal [[1, 0, 0], [0, 1, 0], [0, 0, 1]]) -- True
--   print (checkDiagonal [[1, 0, 0], [0, 0, 0], [0, 0, 1]]) -- False
--   print (checkDiagonal [[1, 0, 0, 1], [0, 0, 1, 0], [1, 1, 1, 0], [1, 1, 1, 1]]) -- True
--   print (checkDiagonal [[1]]) -- True

{-
Subtask 4: isBingo
This function checks the entire bingo matrix to determine if there is a bingo.
It should utilize the previous functions to check all rows, columns, and diagonals.
It returns True if there is a bingo, and False otherwise.
-}

isBingo :: [[Int]] -> Bool
isBingo matrix = any checkRow matrix || any checkRow (transpose matrix) || checkDiagonal matrix

-- {-Test cases-}
mat1 =
  [ [1, 0],
    [1, 1]
  ]

mat2 =
  [ [1, 0, 1],
    [0, 0, 1],
    [1, 0, 1]
  ]

mat3 =
  [ [1, 0, 0, 0, 0],
    [0, 1, 0, 0, 0],
    [0, 0, 1, 0, 0],
    [0, 0, 0, 1, 0],
    [0, 0, 0, 0, 1]
  ]

mat4 =
  [ [0, 0, 0, 0, 0],
    [1, 1, 1, 1, 1],
    [0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0]
  ]

mat5 =
  [ [0, 0, 1, 0, 0],
    [0, 0, 1, 0, 0],
    [0, 0, 1, 0, 0],
    [0, 0, 1, 0, 0],
    [0, 0, 1, 0, 0]
  ]

mat6 =
  [ [0, 0, 1, 0, 1],
    [1, 0, 1, 1, 0],
    [0, 0, 1, 0, 0],
    [0, 0, 0, 0, 0],
    [1, 0, 1, 0, 0]
  ]

main = do
  print (isBingo mat1) -- True
  print (isBingo mat2) -- True
  print (isBingo mat3) -- True
  print (isBingo mat4) -- True
  print (isBingo mat5) -- True
  print (isBingo mat6) -- False