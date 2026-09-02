-- Jaloliddin Ismailov VIHVFL

{-# LANGUAGE ParallelListComp #-}
import Distribution.TestSuite (TestInstance(name))

data Team = Team {teamName :: String, teamID :: Int}
data Player = Player {playerID :: Int, playerName :: String, playerTeam :: Team, playerScores :: [Int]}
data Univ = Univ {uniName :: String, uniID :: Int} deriving (Show, Eq)
data Student = Student {neptunID :: Int, studentName :: String, uni :: Univ, grades :: [Int]} deriving (Show)

-- Teams:
team1 = Team {teamName = "Team A", teamID = 1}
team2 = Team {teamName = "Team B", teamID = 2}
team3 = Team {teamName = "Team C", teamID = 3}
team0 = Team {teamName = "Team C", teamID = 3}

-- Players:
player1 = Player {playerID = 1, playerName = "Alice", playerTeam = team1, playerScores = [10, 20, 30]}
player2 = Player {playerID = 2, playerName = "Bob", playerTeam = team1, playerScores = [15, 25, 35]}
player3 = Player {playerID = 3, playerName = "Charlie", playerTeam = team2, playerScores = [12, 22, 32]}
player4 = Player {playerID = 4, playerName = "David", playerTeam = team2, playerScores = [18, 28, 38]}
player5 = Player {playerID = 5, playerName = "Eve", playerTeam = team3, playerScores = [14, 24, 34]}
player6 = Player {playerID = 6, playerName = "Frank", playerTeam = team3, playerScores = [16, 26, 36]}
player7 = Player {playerID = 6, playerName = "Frank", playerTeam = team3, playerScores = [30, 20, 10]}

-- Universities:
univ1 = Univ {uniName = "University A", uniID = 1}
univ2 = Univ {uniName = "University B", uniID = 2}
univ3 = Univ {uniName = "University C", uniID = 3}

-- Students:
student1 = Student {neptunID = 101, studentName = "Alice", uni = univ1, grades = [85, 90, 78]}
student2 = Student {neptunID = 102, studentName = "Bob", uni = univ1, grades = [88, 92, 80]}
student3 = Student {neptunID = 103, studentName = "Charlie", uni = univ2, grades = [75, 85, 82]}
student4 = Student {neptunID = 104, studentName = "David", uni = univ2, grades = [90, 87, 91]}
student5 = Student {neptunID = 105, studentName = "Eve", uni = univ3, grades = [80, 83, 88]}
student6 = Student {neptunID = 106, studentName = "Frank", uni = univ3, grades = [85, 89, 84]}

{-4- 10p
A university wants to update its name. Given a university, a new name as a string,
and a list of students, update the university name for all students associated
with that university to the new name.
Example:
uni1 "ELTE" [student1, student2, student3] ->
 [Student {neptunID = 101, studentName = "Alice", uni = Univ {uniName = "ELTE", uniID = 1}, grades = [85, 90, 78]},
  Student {neptunID = 102, studentName = "Bob", uni = Univ {uniName = "ELTE", uniID = 1}, grades = [88, 92, 80]},
  Student {neptunID = 103, studentName = "Charlie", uni = Univ {uniName = "University B", uniID = 2}, grades = [75, 85, 82]}]
  student1 and student2 have their uniName updated to "ELTE", because they are associated with uni1,
  student3 remains unchanged.
-}

renameUniversity :: Univ -> String -> [Student] -> [Student]
renameUniversity oldUni newUniName = map updateStudent
  where
    newUni = oldUni {uniName = newUniName}

    updateStudent student =
      if uni student == oldUni
        then student {uni = newUni}
        else student

-- renameUniversity univ3 "Corvinus" [student6]
-- [Student {neptunID = 106, studentName = "Frank", uni = Univ {uniName = "Corvinus", uniID = 3}, grades = [85,89,84]}]

-- renameUniversity univ1 "ELTE" [student1, student2, student3]
-- [Student {neptunID = 101, studentName = "Alice", uni = Univ {uniName = "ELTE", uniID = 1}, grades = [85,90,78]},
--  Student {neptunID = 102, studentName = "Bob", uni = Univ {uniName = "ELTE", uniID = 1}, grades = [88,92,80]},
--  Student {neptunID = 103, studentName = "Charlie", uni = Univ {uniName = "University B", uniID = 2}, grades = [75,85,82]}]

-- renameUniversity univ2 "BME" [student3, student4, student6, student5]
-- [Student {neptunID = 103, studentName = "Charlie", uni = Univ {uniName = "BME", uniID = 2}, grades = [75,85,82]},
--  Student {neptunID = 104, studentName = "David", uni = Univ {uniName = "BME", uniID = 2}, grades = [90,87,91]},
--  Student {neptunID = 106, studentName = "Frank", uni = Univ {uniName = "University C", uniID = 3}, grades = [85,89,84]},
--  Student {neptunID = 105, studentName = "Eve", uni = Univ {uniName = "University C", uniID = 3}, grades = [80,83,88]}]

-----------------------------------------------------
{-1- 10p
Write a function which takes 3 lists of integers v1, v2 and v3,
multiply v2 and v3 then add the result to v1.
Parallel list comprehension must be used.
Non-parallel solutions are also accepted but then max is 8 points.
-}

mulAdA :: [Int] -> [Int] -> [Int] -> [Int]
mulAdA v1 v2 v3 = [n1 + n2 * n3 | (n1, n2, n3) <- triples]
  where
    triples = zip3 v1 v2 v3

-- main = print $ mulAdA [1, 2, 3] [4, 5, 6] [7, 8, 9]    -- [29,42,57]
-- main = print $ mulAdA [1, 0, 3] [4, 0, 6] [7, 8, 0]    -- [29,0,3]
-- main = print $ mulAdA [1, -2, 3] [-4, 5, -6] [7, -8, 9]    -- [-27,-42,-51]
-- main = print $ mulAdA [0, 0, 0] [0, 0, 0] [0, 0, 0]    -- [0,0,0]
-- main = print $ mulAdA [1, 2] [3, 4, 5] [6, 7, 8]    -- [19,30]

-----------------------------------------------------

{-2- 10p
Write a function that takes two lists of numbers and produces a tuple of two lists:
a. A list where each element is the product of corresponding elements from the input lists.
b. A list where each element is the division (if defined) of corresponding elements.
Note: If the lists are of different lengths, processing is only up to the shorter list.
Parallel list comprehension must be used.
Non-parallel solutions are also accepted but then max is 8 points.
-}

elementWise :: [Double] -> [Double] -> ([Double], [Double])
elementWise v1 v2 = ([a * b | (a, b) <- tuples], [a / b | (a, b) <- tuples])
  where
    tuples = zip v1 v2

-- main = print (elementWise [1.0, 2.0, 3.0] [4.0, 5.0, 6.0])
-- ([4.0, 10.0, 18.0], [0.25, 0.4, 0.5])
-- main = print (elementWise [1.0, 2.0, 3.0, 4.0] [0.0, 0.0, 0.0, 2.0, 0.0, 3.0, 4.0, 5.0, 6.0])
-- ([0.0,0.0,0.0,8.0],[0.5,0.6666666666666666,0.75,0.8])
-- main = print (elementWise [] [1.0, 2.0, 3.0]) -- ([], [])

-----------------------------------------------------

{-3- 10p
Write a function that takes two lists of tuples as input.
Each tuple contains an Integer and a Boolean value. For
each pair of tuples at the same index in the two lists:
If the Boolean values in both tuples are True, add the
integers from the tuples. Otherwise, subtract the integer
in the second tuple from the integer in the first tuple.
The function should return the sum of all these computed
values. Parallel list comprehension must be used. Non-parallel
solutions are also accepted but then max is 8 points.
Example:
processTuples [(1, True), (2, False), (6,false)] [(3,True), (4,True), (5,False)]
        (1, True) (3, True) -> 1+3 = 4
        (2, False) (4, True) -> 2-4 = -2
        (6, False) (5, False) -> 6-5 = 1
        Result = 4 + -2 + 1 = 3
-}

processTuples :: [(Int, Bool)] -> [(Int, Bool)] -> Int
processTuples list1 list2 = sum [if bool1 && bool2 then num1 + num2 else num1 - num2 | ((num1, bool1), (num2, bool2)) <- pairs]
  where
    pairs = zip list1 list2

-- main = print $ processTuples [(1, True), (2, False), (6,False)] [(3,True), (4,True), (5,False)] -- 3
-- main = print $ processTuples [(4, True), (7, True), (1, False)] [(2, True), (5, False), (3, True)] -- 6
-- main = print $ processTuples [(0, False), (10, True), (8, False)] [(5, False), (3, True), (2, False)] -- 14
-- main = print $ processTuples [(1, True), (3, False), (5, False)] [(1, True), (3, False), (5, True)] -- 2

-----------------------------------------------------

{-5- 10p
Write a function that lists all students who have at least one grade
greater than 90. Write the names of these students.
-}

students = [student1, student2, student3, student4, student5, student6]

outstandingStudents :: [Student] -> [String]
outstandingStudents students =
  [ studentName student
  | student <- students,
    any (>90) (grades student)
  ]


-- outstandingStudents students -- ["Bob","David"]

-----------------------------------------------------

{-6- 10p
Given a binary tree, swap its right and left sub-trees.
       1                 1
      / \      -->      / \
     2   3             3   2
    / \   \           /   / \
   4   5   6         6   4   5
           /\       /\
          7  8     7  8
-}

data Tree a = Node a (Tree a) (Tree a)
              | Leaf
              deriving (Show)

swapSubTree :: Tree a -> Tree a
swapSubTree Leaf = Leaf
swapSubTree (Node x left right) = Node x (swapSubTree right) (swapSubTree left)

-- swapSubTree (Node 99 (Node 3 Leaf Leaf) (Node (-2) Leaf Leaf))
-- Node 99 (Node (-2) Leaf Leaf) (Node 3 Leaf Leaf)

-- swapSubTree  (Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 Leaf Leaf)) (Node 3 Leaf (Node 6 (Node 7 Leaf Leaf) (Node 8 Leaf Leaf))))
-- Node 1 (Node 3 Leaf (Node 6 (Node 7 Leaf Leaf) (Node 8 Leaf Leaf))) (Node 2 (Node 4 Leaf Leaf) (Node 5 Leaf Leaf))

-- swapSubTree (Node 3 Leaf Leaf) -- Node 3 Leaf Leaf

{- swapSubTree ( Node "root"
                (Node "left"
                    (Node "left-left" Leaf Leaf)
                    (Node "left-right" Leaf Leaf))
                (Node "right"
                    Leaf
                    (Node "right-right" Leaf Leaf)))
-}
-- Node "root" (Node "right" Leaf (Node "right-right" Leaf Leaf)) (Node "left" (Node "left-left" Leaf Leaf) (Node "left-right" Leaf Leaf))

-----------------------------------------------------

{-7- 10p
The ExpressionTree data structure is a binary tree used to represent arithmetic expressions.
  It consists of two types of nodes:
  1. Operand: A leaf node that holds a value (e.g., a number or variable).
  2. Operator: An internal node that holds an operator (e.g., +, -, *, /) and has two children,
     which are themselves ExpressionTree nodes.
Write  an "evaluate“ function which takes an ExpressionTree and returns
a string representation of the arithmetic expression in infix notation,
with parentheses to denote the order of operations.
  e.g.
      +
     / \
    10 20     = (10 + 20)

            +
          /   \
         *    *
        / \  / \
      10  9 -   5
           / \
          3  4        = (10 * 9) + ((3 - 4) * 5)
-}

data ExpressionTree a = Operand a | Operator a (ExpressionTree a) (ExpressionTree a) deriving (Show)

evaluate :: ExpressionTree String -> String
evaluate (Operand a) = a
evaluate (Operator op left right) = "(" ++ evaluate left ++ op ++ evaluate right ++ ")"

-- evaluate (Operator "+" (Operator "*" (Operand "10") (Operand "9")) (Operator "*" (Operator "-" (Operand "3") (Operand "4")) (Operand "5")))
-- "((10*9)+((3-4)*5))"
-- evaluate (Operator "+" (Operand "10") (Operand "20"))
-- "(10+20)"
-- evaluate (Operator "*" (Operator "+" (Operand "1") (Operand "2")) (Operator "-" (Operand "4") (Operand "3")))
-- "((1+2)*(4-3))"
-- evaluate (Operand "2") -- "2"

-----------------------------------------------------

{-8- 10p
Define an algebraic data type Color to represent the three primary colors:
Red, Blue, Yellow, and the derived colors Purple, Orange, and Green.
Derived colors are the colors we can obtain from mixing the primary colors.
Here is how you can obtain each.
    Combination of Red and Blue or vice-versa creates Purple
    Combination of Red and Yellow or vice-versa creates Orange
    Combination of Blue and Yello or vice versa creates Green
Write a function combineColors that takes two colors as input and returns
the resulting derived color if they can be combined.
If the two colors are the same, return the same color.
If the combination is unsupported, print an error "Unsupported combo".
-}

data Color = Red | Blue | Yellow | Purple | Orange | Green
  deriving (Show, Eq)

-- combineColors :: Color -> Color -> Color

-- main = print $ combineColors Red Blue -- Purple
-- main = print $ combineColors Blue Red -- Purple
-- main = print $ combineColors Yellow Red -- Orange
-- main = print $ combineColors Blue Blue -- Blue
-- main = print $ combineColors Red Green -- Unsupported combo

-----------------------------------------------------

{-9- 10p
Create an instance of show to:
a. The "Player" type in the following format: - 5pt
[Player {ID: <ID value>, Name: <name value>, Team: <team>, Scores: [scores...]}]
Ex: [Player {ID: 2, Name: "Bob", Team: "playerTeam = Team {teamName = "Team A", teamID = 1}", Scores: [15,25,35]}]
b. The "Team" type in the following format: - 5pts
Team {teamName = <teamName value>, teamID = <teamID value>}
Ex: Team {teamName = "Team A", teamID = 1}
-}

-- instancing show for players

-- main = print [player1, player2]
-- [Player {ID: 1, Name: "Alice", Team: "playerTeam = Team {teamName = "Team A", teamID = 1}", Scores: [10,20,30]},
--  Player {ID: 2, Name: "Bob", Team: "playerTeam = Team {teamName = "Team A", teamID = 1}", Scores: [15,25,35]}]
-- main = print player5
-- Player {ID: 5, Name: "Eve", Team: "playerTeam = Team {teamName = "Team C", teamID = 3}", Scores: [14,24,34]}

-- instancing show for teams

-- main = print [team1, team2, team3]
-- [playerTeam = Team {teamName = "Team A", teamID = 1},
--  playerTeam = Team {teamName = "Team B", teamID = 2},
--  playerTeam = Team {teamName = "Team C", teamID = 3}]
-- main = print team2
-- playerTeam = Team {teamName = "Team B", teamID = 2}

-----------------------------------------------------

{-10- 10p
Implement the Eq instance for the Player record.
Two players are equal if they have the same
total score and came from different teams.
-}

-- instancing eq for players

-- main = print $ player1 == player2 -- False
-- main = print $ player1 == player7 -- True
-- main = print $ player1 == player3 -- False
-- main = print $ player1 == player4 -- False

-----------------------------------------------------
