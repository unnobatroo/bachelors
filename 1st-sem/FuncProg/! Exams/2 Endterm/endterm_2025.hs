-- LECTURE 90 ENDTERM
-- WRITE YOUR NAME AND NEPTUN HERE !!!

-----------------------------------------------------------

data Univ = Univ {uniName :: String, uniID :: Int}
  deriving (Show, Eq)

data Student = Student
  { neptunID :: Int,
    studentName :: String,
    uni :: Univ,
    grades :: [Int]
  }
  deriving (Show)

univ1 = Univ {uniName = "University A", uniID = 1}

univ2 = Univ {uniName = "University B", uniID = 2}

univ3 = Univ {uniName = "University C", uniID = 3}

-- Students
student1 = Student {neptunID = 101, studentName = "Alice", uni = univ1, grades = [85, 90, 78]}

student2 = Student {neptunID = 102, studentName = "Bob", uni = univ1, grades = [88, 92, 80]}

student3 = Student {neptunID = 103, studentName = "Charlie", uni = univ2, grades = [75, 85, 82]}

student4 = Student {neptunID = 104, studentName = "David", uni = univ2, grades = [90, 87, 91]}

student5 = Student {neptunID = 105, studentName = "Eve", uni = univ3, grades = [80, 83, 88]}

student6 = Student {neptunID = 106, studentName = "Frank", uni = univ3, grades = [85, 89, 84]}

student7 = Student {neptunID = 100, studentName = "Brian Hood", uni = univ3, grades = [5, 9, 4]}

studentsAll :: [Student]
studentsAll = [student1, student2, student3, student4, student5, student6]

-----------------------------------------------------------

{- 1.
A university wants to reward students by adding a bonus to
each grade. Write a function that takes a bonus amount and
a Student, and returns an updated Student where:
  - Each grade is increased by the bonus.
  - No grade can be higher than 100.
E.g. bonusStudent 10 of student1 who has grades [85,90,78]
  after update grades are: [95,100,88]
-}

bonusStudent :: Int -> Student -> Student
bonusStudent n st = st {grades = map (\x -> min 100 (x + n)) $ grades st}

-- main = print $ bonusStudent 10 student1
-- Student {neptunID = 101, studentName = "Alice", uni = Univ {uniName = "University A", uniID = 1}, grades = [95,100,88]}
-- main = print $ bonusStudent 5 student3
-- Student {neptunID = 103, studentName = "Charlie", uni = Univ {uniName = "University B", uniID = 2}, grades = [80,90,87]}
-- main = print $ bonusStudent 20 student5
-- Student {neptunID = 105, studentName = "Eve", uni = Univ {uniName = "University C", uniID = 3}, grades = [100,100,100]}
-- main = print $ bonusStudent 0 student7
-- Student {neptunID = 100, studentName = "Brian Hood", uni = Univ {uniName = "University C", uniID = 3}, grades = [5,9,4]}

-----------------------------------------------------------

{- 2.
Write a function that, given a university and a list of students,
returns the names of all students enrolled to that university.
E.g. studentsFromUniversity univ1 studentsAll returns ["Alice","Bob"]
-}

studentsFromUniversity :: Univ -> [Student] -> [String]
studentsFromUniversity u list = [studentName x | x <- list, uni x == u]

-- main = print $ studentsFromUniversity univ1 studentsAll -- ["Alice","Bob"]
-- main = print $ studentsFromUniversity univ2 studentsAll -- ["Charlie","David"]
-- main = print $ studentsFromUniversity univ3 studentsAll -- ["Eve","Frank"]
-- main = print $ studentsFromUniversity univ1 [] -- []

-----------------------------------------------------------

{- 3.
You are given a list of students and a university name.
Calculate the average grades of students grouped by their university.
The function returns a tuple containing a list of student names
from the specified university and the calculated average grade.
If there are no students from the specified university,
return an empty list and an average grade of 0.0.
-}

f1 :: [Student] -> String -> ([String], Double)
f1 studentsList targetUniName = (studentNames, averageGrade)
  where
    filteredStudents = filter (\student -> uniName (uni student) == targetUniName) studentsList

    studentNames = map studentName filteredStudents

    allGrades = concatMap grades filteredStudents
    totalSum = sum allGrades

    gradesCount = length allGrades

    averageGrade
      | gradesCount > 0 = fromIntegral totalSum / fromIntegral gradesCount
      | otherwise = 0.0

-- f1 studentsAll "University A" -- (["Alice","Bob"], 85.5)
-- f1 studentsAll "University B" -- (["Charlie","David"], 85.0)
-- f1 studentsAll "University C" -- (["Eve","Frank"], 84.83333333333333)
-- f1 studentsAll "University that does not exist" -- ([], 0.0)

-----------------------------------------------------------

{- 4.
You are given a list of students and a score threshold.
Each student has a list of scores.
Return a list containing only those students' name
whose each score is more than the given threshold.
-}

f3 :: [Student] -> Int -> [String]
f3 students threshold = map studentName (filter (all (> threshold) . grades) students)
-- f3 studentsAll 80 -- ["David","Frank"]
-- f3 studentsAll 85 -- ["David"]
-- f3 studentsAll 90 -- []
-- f3 studentsAll 70 -- ["Alice","Bob","Charlie","David","Eve","Frank"]

-----------------------------------------------------------

-- Definition of a binary tree

data Tree a = Leaf | Node a (Tree a) (Tree a)
  deriving (Show)

tree1 :: Tree Int
tree1 =
  Node
    5
    ( Node
        3
        (Node 1 Leaf Leaf)
        (Node 4 Leaf Leaf)
    )
    ( Node
        8
        Leaf
        (Node 10 Leaf Leaf)
    )

tree2 :: Tree Int
tree2 =
  Node
    7
    ( Node
        2
        Leaf
        (Node 3 Leaf Leaf)
    )
    ( Node
        9
        (Node 1 Leaf Leaf)
        Leaf
    )

-----------------------------------------------------------

{- 5.
Count the number of even branch nodes in a binary tree.
A branch node is a Node whose left and right children
are both leaves. The empty tree (Leaf) has 0 branch nodes.
E.g.
       5
     /   \
    3     8
   / \    /\
  1   4  L 10
 /\  /\    /\
 L L L L   L L   branch nodes are: 1, 4, 10 -> 4, 10 even, result 2
-}

countBN :: Tree Int -> Int
countBN Leaf = 0
countBN (Node val Leaf Leaf)
  | odd val = 0
  | even val = 1
countBN (Node val left right) = countBN left + countBN right

-- countBN Leaf -- 0
-- countBN (Node 3 Leaf Leaf) -- 0
-- countBN tree1 -- 2
-- countBN tree2 -- 0

-----------------------------------------------------------

{- 6.
Write a function that, given a level k (starting from 0 at the root)
and a tree of integers, computes the sum of all node values of level k.
Root is at level 0, its children are at level 1 and so on.
If the tree is empty or there is no node at that level, return 0.
E.g. (tree1):
      5            level 0: [5]      sumAtLevel 0 tree1 = 5
     / \           level 1: [3,8]    sumAtLevel 1 tree1 = 3+8 = 11
    3   8          level 2: [1,4,10] sumAtLevel 2 tree1 = 1+4+10 = 15
   / \   \
  1   4   10
-}

sumAtLevel :: Int -> Tree Int -> Int
sumAtLevel _ Leaf = 0
sumAtLevel lvl (Node val left right)
  | lvl < 0 = 0
  | lvl == 0 = val
  | otherwise = sumAtLevel (lvl - 1) left + sumAtLevel (lvl - 1) right

-- sumAtLevel 0 tree1 -- 5
-- sumAtLevel 1 tree1 -- 11
-- sumAtLevel 2 tree1 -- 15
-- sumAtLevel 3 tree1 -- 0
-- sumAtLevel 1 tree2 -- 11
-- sumAtLevel 2 tree2 -- 4
-- sumAtLevel 2 (Leaf :: Tree Int) -- 0

-----------------------------------------------------------

{- 7.
You are given a tree, a predicate, and a function.
Return a tree where each node's value is transformed
by the function only if it satisfies the predicate.
-}

f5 :: Tree a -> (a -> Bool) -> (a -> a) -> Tree a
f5 Leaf _ _ = Leaf
f5 (Node val left right) predicate f = Node newVal newLeft newRight
  where
    newVal = if predicate val then f val else val
    newLeft = f5 left predicate f
    newRight = f5 right predicate f

-- f5 (Node 0 (Node 1 (Node 3 Leaf Leaf) (Node 4 Leaf Leaf)) (Node 2 (Node 5 Leaf Leaf) (Node 6 Leaf Leaf))) even (*2)
-- Node 0 (Node 1 (Node 3 Leaf Leaf) (Node 8 Leaf Leaf)) (Node 4 (Node 5 Leaf Leaf) (Node 12 Leaf Leaf))

-- f5 (Node 0  (Node 1  (Node 3 Leaf (Node 8 Leaf Leaf)) Leaf) (Node 2 Leaf Leaf)) (>1) (+10)
-- Node 0 (Node 1 (Node 13 Leaf (Node 18 Leaf Leaf)) Leaf) (Node 12 Leaf Leaf)

-- f5 (Node "a" (Node "bb" Leaf Leaf) (Node "ccc" Leaf Leaf)) (\s -> length s == 2) (++"!")
-- Node "a" (Node "bb!" Leaf Leaf) (Node "ccc" Leaf Leaf)

-- f5 Leaf odd (+1)
-- Leaf

-----------------------------------------------------------

{- 8.
Create a custom data type MyTree with 2 constructors: MyNode
and MyLeaf, such as the given samples are correct. Write Show
instance for the defined type that displays the tree in the
following format: [left subtree]--Node value--[right subtree].
E.g. (MyNode 5 (MyNode 3 MyLeaf MyLeaf) (MyNode 7 MyLeaf MyLeaf))
should be displayed as: [[L]--3--[L]]--5--[[L]--7--[L]]
where "L" is a leaf.
--}
data MyTree = MyLeaf | MyNode Int MyTree MyTree

myTree1 = MyNode 5 (MyNode 3 MyLeaf MyLeaf) (MyNode 7 MyLeaf MyLeaf)

myTree2 = MyNode 1 (MyNode 2 (MyNode 4 MyLeaf MyLeaf) (MyNode 5 MyLeaf MyLeaf)) (MyNode 3 (MyNode 6 MyLeaf MyLeaf) (MyNode 7 MyLeaf MyLeaf))

myTree3 = MyNode 10 (MyNode 20 (MyNode 30 MyLeaf MyLeaf) MyLeaf) (MyNode 40 MyLeaf MyLeaf)

-- Show
instance Show MyTree where
  show MyLeaf = "[L]"
  show (MyNode val left right) = "[" ++ show left ++ "]" ++ "--" ++ show val ++ "--" ++ "[" ++ show right ++ "]"

-- myTree1 -- [[L]--3--[L]]--5--[[L]--7--[L]]
-- myTree2 -- [[[L]--4--[L]]--2--[[L]--5--[L]]]--1--[[[L]--6--[L]]--3--[[L]--7--[L]]]
-- myTree3 -- [[[L]--30--[L]]--20--[L]]--10--[[L]--40--[L]]

-----------------------------------------------------------

{- 9.
Define an enumeration type `TrafficLight` with values:
Red, Yellow, Green.
1. Implement `Show` instance printing for Red -> "Stop",
for Yellow -> "Wait", for Green -> "Go".
2. Implement `Eq` instance, all values are equal to themselves.
3. Write a function `nextLight` that changes a colour like:
Red -> Green -> Yellow -> Red.
-}

data TrafficLight = Green | Yellow | Red deriving (Enum, Bounded)

-- Show
instance Show TrafficLight where
  show Red = "Stop"
  show Yellow = "Wait"
  show Green = "Go"

-- Eq
instance Eq TrafficLight where
  a == b = show a == show b

nextLight :: TrafficLight -> TrafficLight
nextLight colour
  | colour == maxBound = minBound
  | otherwise = succ colour

-- Red -- Stop
-- show Green -- "Go"
-- Red == Red -- True
-- Yellow == Green -- False
-- nextLight Red -- Go
-- nextLight (nextLight Green) -- Stop

-----------------------------------------------------------

{- 10.
Define the 'Number' type with 3 constructors:
 'NInt' is the constructor for an Int Number.
 'NFloat' is the constructor for a Float Number.
 'Invalid' does not have data associated to it.
Write a function that adds 10 to a Number value
and displays it as a string. For Invalid print
"Invalid input".
-}

-- Number

-- add10 function
-- add10 :: Number -> String

-- add10 (NInt 20) -- "30"
-- add10 (NFloat 0.0) -- "10.0"
-- add10 Invalid -- "Invalid input"
-- add10 (NInt 33), add10 (NFloat 3.3), add10 Invalid) -- ("43","13.3","Invalid input")

-----------------------------------------------------------