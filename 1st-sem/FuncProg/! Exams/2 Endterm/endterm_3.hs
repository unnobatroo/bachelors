{-# LANGUAGE ParallelListComp #-}
{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}

{-# HLINT ignore "Eta reduce" #-}

data Team = Team {teamName :: String, teamID :: Int} deriving (Show)

data Player = Player {playerID :: Int, playerName :: String, playerTeam :: Team, playerScores :: [Int]} deriving (Show)

data Univ = Univ {uniName :: String, uniID :: Int} deriving (Show)

data Student = Student {neptunID :: Int, studentName :: String, uni :: Univ, grades :: [Int]}

team1 = Team {teamName = "Team A", teamID = 1}

team2 = Team {teamName = "Team B", teamID = 2}

team3 = Team {teamName = "Team C", teamID = 3}

team0 = Team {teamName = "Team D", teamID = 0}

player1 = Player {playerID = 1, playerName = "Alice", playerTeam = team1, playerScores = [10, 20, 30]}

player2 = Player {playerID = 2, playerName = "Bob", playerTeam = team1, playerScores = [15, 25, 35]}

player3 = Player {playerID = 3, playerName = "Charlie", playerTeam = team2, playerScores = [12, 22, 32]}

player4 = Player {playerID = 4, playerName = "David", playerTeam = team2, playerScores = [18, 28, 38]}

player5 = Player {playerID = 5, playerName = "Eve", playerTeam = team3, playerScores = [14, 24, 34]}

player6 = Player {playerID = 6, playerName = "Frank", playerTeam = team3, playerScores = [16, 26, 36]}

univ1 = Univ {uniName = "University A", uniID = 1}

univ2 = Univ {uniName = "University B", uniID = 2}

univ3 = Univ {uniName = "University C", uniID = 3}

student1 = Student {neptunID = 101, studentName = "Alice", uni = univ1, grades = [85, 90, 78]}

student2 = Student {neptunID = 102, studentName = "Bob", uni = univ1, grades = [88, 92, 80]}

student3 = Student {neptunID = 103, studentName = "Charlie", uni = univ2, grades = [75, 85, 82]}

student4 = Student {neptunID = 104, studentName = "David", uni = univ2, grades = [90, 87, 91]}

student5 = Student {neptunID = 105, studentName = "Eve", uni = univ3, grades = [80, 83, 88]}

student6 = Student {neptunID = 106, studentName = "Frank", uni = univ3, grades = [85, 89, 84]}

student7 = Student {neptunID = 103, studentName = "Charlie1", uni = univ2, grades = [75, 59, 82]}

student8 = Student {neptunID = 105, studentName = "Eve1", uni = univ3, grades = [80, 9, 8]}

-------------------------------------------------------
{- 1. 10p
Write a function which takes 2 lists of integers of the same length,
and calculates the dot product of these two vectors.

A dot product is the sum of the products of the corresponding
entries of the two lists.

Parallel list comprehension must be used.
Non-parallel solutions are also accepted but then max is 8 points.
-}

dotProd :: [Int] -> [Int] -> Int
dotProd list1 list2 = sum (zipWith (*) list1 list2)

-- dotProd [1, 2, 3] [4, 5, 6] -- 32
-- dotProd [-1, -2, -3] [4, -5, 6] -- -12
-- dotProd [0, 0, 0] [1, 2, 3] -- 0
-- dotProd [7] [3] -- 21
-- dotProd [1, 2, 3, 4, 5] [6, 7, 8, 9, 10] -- 130

-------------------------------------------------------

{- 2. 10p
We have a bulb, which has 2 switches connected to it serially.
The switch is controlled by a stream of signals, where 1 means on,
and 0 means off. The bulb is on if both switches are on.

Write a function which takes in 2 streams of signals (string of '0's and '1's)
and output the list of states of the bulb over the time ("On" or "Off").

Parallel list comprehension must be used.
Non-parallel solutions are also accepted but then max is 8 points.
-}

lightStates :: String -> String -> [String]
lightStates str1 str2 = zipWith checkState str1 str2
  where
    checkState c1 c2
      | c1 == '1' && c2 == '1' = "On"
      | otherwise = "Off"

-- lightStates "1100" "1010" -- ["On", "Off", "Off", "Off"]
-- lightStates "1111" "1111" -- ["On", "On", "On", "On"]
-- lightStates "0000" "1111" -- ["Off", "Off", "Off", "Off"]
-- lightStates "1010" "0101" -- ["Off", "Off", "Off", "Off"]

-------------------------------------------------------

{- 3. 10p
Filter out the list of students who failed at least one course.
A student fails a course if the grade is lower than 60.
Return a list of Student name and their failing scores.
-}

failingStudents :: [Student] -> [(String, [Int])]
failingStudents students = [(studentName student, failures) | student <- students, let failures = filter (< 60) (grades student), not (null failures)]

-- failingStudents [student1, student2, student7, student4, student8, student6]
-- [("Charlie1",[59]),("Eve1",[9,8])]
-- failingStudents [student1, student2, student3] -- []
-- failingStudents [] -- []

-------------------------------------------------------

{- 4. 30p
Find the Player with the Highest Score in a Team.
Write a function that takes a Team and a list of Player records,
and finds the players with the highest total score in that team. If there are
no players in the given team return empty list.
To obtain the solution for this task, you will need:
a. Filter players belonging to the given team. - 5pts
b. Find the highest total score among players. - 5pts
c. Calculate the maximum score from a list of players. - 5pts
d. Select players with the highest score. - 5pts
e. Calculate the total score of a player. - 5pts
f. Put together, finding players with top scores. - 5pts
-}

-- topScorersInTeam :: Team -> [Player] -> [Player]

-- main = print (topScorersInTeam team1 [player1, player2, player3, player4, player5, player6])
-- [Player {playerID = 2, playerName = "Bob", playerTeam = Team {teamName = "Team A", teamID = 1}, playerScores = [15,25,35]}]
-- main =    print (topScorersInTeam team2 [player1, player2, player3, player4, player5, player6])
-- [Player {playerID = 4, playerName = "David", playerTeam = Team {teamName = "Team B", teamID = 2}, playerScores = [18,28,38]}]
-- main =    print (topScorersInTeam team3 [player1, player2, player3, player4, player5, player6])
-- [Player {playerID = 6, playerName = "Frank", playerTeam = Team {teamName = "Team C", teamID = 3}, playerScores = [16,26,36]}]
-- main =    print (topScorersInTeam team0 [])
-- []

-------------------------------------------------------

data Tree a = Leaf | Node a (Tree a) (Tree a) deriving (Show)

treeExample1 :: Tree Int
treeExample1 =
  Node
    5
    (Node 3 (Node 7 Leaf Leaf) (Node 2 Leaf Leaf))
    (Node 10 (Node 6 Leaf Leaf) (Node 1 Leaf Leaf))

treeExample2 :: Tree Int
treeExample2 =
  Node
    8
    (Node 3 (Node 12 Leaf Leaf) (Node 2 Leaf Leaf))
    (Node 15 (Node 5 Leaf Leaf) (Node 20 Leaf Leaf))

-------------------------------------------------------

{- 5. 10p
Write a function that takes a tree of integers and returns
a tuple of two integers. The first integer is the sum of the
even values in the tree and the second integer is the sum of
the odd values in the tree. Leaf counts as 0.
-}

-- evenSumOddSum :: Tree Int -> (Int, Int)

-- evenSumOddSum Leaf -- (0,0)
-- evenSumOddSum (Node 11 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf)) -- (0,21)
-- evenSumOddSum treeExample1 -- (18,16)
-- evenSumOddSum treeExample2 --(42,23)
-- evenSumOddSum (Node 10 (Node 8 (Node 7 (Node 11 Leaf Leaf) Leaf) (Node 6 Leaf Leaf)) (Node 5 Leaf (Node 7 Leaf Leaf))) -- (24,30)
-- evenSumOddSum (Node 1 (Node 2 (Node 2 Leaf Leaf) (Node 2 Leaf Leaf)) (Node 5 (Node 3 (Node 1 Leaf Leaf) Leaf) (Node 4 Leaf Leaf))) -- (10,10)

-------------------------------------------------------

{- 6. 10p
Given an integer and a binary tree, write a function replaceNodes
that modifies the nodes in a binary tree with the following rules:
Replace the value with -1 if it is divisible by n.
Replace the value with 0 if it is greater than n.
Retain the original value otherwise.
-}

-- replaceNodes :: Int -> Tree Int -> Tree Int

-- replaceNodes 5 treeExample1
-- Node (-1) (Node 3 (Node 0 Leaf Leaf) (Node 2 Leaf Leaf)) (Node (-1) (Node 0 Leaf Leaf) (Node 1 Leaf Leaf))
-- main = print $ replaceNodes 5 treeExample2
-- Node 0 (Node 3 (Node 0 Leaf Leaf) (Node 2 Leaf Leaf)) (Node (-1) (Node (-1) Leaf Leaf) (Node (-1) Leaf Leaf))

-------------------------------------------------------

{- 7. 10p
Implement a custom Show instance for the Student record,
which prints an introduction for the student in the format of:
"I am <studentName> from <uni>. My average score is <average of grades>."
If his grades > 90, add the sentence: "I am a very good student.".
-}

-- Implementing the Show instance for Student

-- main = print Student {neptunID = 106, studentName = "Frank", uni = univ3, grades = [95, 99, 84]}
-- I am Frank from University C. My average score is 92.66666666666667. I am a very good student.
-- main = print Student {neptunID = 106, studentName = "Bob", uni = univ3, grades = [85, 89, 84]}
-- I am Bob from University C. My average score is 86.0.

-------------------------------------------------------

{- 8. 10p
Implement the Eq instance for Product. Two products are considered equal if:
they have the same name, the same price, and the same quantity in stock.

Implement the Eq instance for Customer. Two customers are considered equal if:
They have the same name, the same contact email, and the same order history.
-}

product1 = Product "Laptop" 999.99 10

product2 = Product "Smartphone" 499.99 20

product3 = Product "Headphones" 199.99 50

customer1 = Customer "Alice" "alice@example.com" [product1, product2]

customer2 = Customer "Bob" "bob@example.com" [product2, product3]

customer3 = Customer "Alice" "alice@example.com" [product1, product2]

data Product = Product
  { productName :: String,
    price :: Double,
    quantity :: Int
  }

data Customer = Customer
  { customerName :: String,
    contactEmail :: String,
    orderHistory :: [Product]
  }

-- Implementing the Eq instance for Product
instance Eq Product where
  p1 == p2 =
    productName p1 == productName p2 &&
    price p1 == price p2 &&
    quantity p1 == quantity p2

-- main = print (product1 == product2)  -- Expected output: False
-- main = print (product1 == product1)  -- Expected output: True

-- Implementing the Eq instance for Customer
instance Eq Customer where
  c1 == c2 =
    customerName c1 == customerName c2 &&
    contactEmail c1 == contactEmail c2 &&
    orderHistory c1 == orderHistory c2

-- main = print (customer1 == customer2)  -- Expected output: False
-- main = print (customer1 == customer3)  -- Expected output: True

-------------------------------------------------------