-- JALOLIDDIN ISMAILOV, VIHVFL

{-# LANGUAGE ParallelListComp #-}

data Univ = Univ {uniName :: String, uniID :: Int} deriving (Show, Eq)

data Student = Student {neptunID :: Int, studentName :: String, uni :: Univ, grades :: [Int]} deriving (Show)

univ1 = Univ {uniName = "University A", uniID = 1}

univ2 = Univ {uniName = "University B", uniID = 2}

univ3 = Univ {uniName = "University C", uniID = 3}

student1 = Student {neptunID = 101, studentName = "Alice", uni = univ1, grades = [85, 90, 78]}

student2 = Student {neptunID = 102, studentName = "Bob", uni = univ1, grades = [88, 92, 80]}

student3 = Student {neptunID = 103, studentName = "Charlie", uni = univ2, grades = [75, 85, 82]}

student4 = Student {neptunID = 104, studentName = "David", uni = univ2, grades = [90, 87, 91]}

student5 = Student {neptunID = 105, studentName = "Eve", uni = univ3, grades = [80, 83, 88]}

student6 = Student {neptunID = 106, studentName = "Frank", uni = univ3, grades = [85, 89, 84]}


-- ************************************************
-- 1. 10p
-- Write a program that generates a list of tuples (x, y, f(x, y)), where:
--    x is taken from the first list (list1).
--    y is taken from the second list (list2).
--    f(x,y) is given as an input
-- Traverse list1 and list2 simultaneously, ensuring that each 
-- pair (x, y) corresponds to the same position in the lists.
-- Parallel list comprehension must be used.
-- Non-parallel solutions are also accepted but then max is 8 points.

--applyFunction :: (Int -> Int -> Int) -> [Int] -> [Int] -> [(Int, Int, Int)]

--main = print $ applyFunction (\x y -> x^2 + 2*x*y + y^2) [4,5,6] [1,2,3] 
-- [(4,1,25), (5,2,49), (6,3,81)]
--main = print $ applyFunction (\x y -> x `mod` y) [10, 25, 30] [3, 7, 9] 
-- [(10,3,1),(25,7,4),(30,9,3)]
--main = print $ applyFunction (\x y -> abs (x - y)) [8, 12, 16] [5, 10, 20] 
-- [(8,5,3),(12,10,2),(16,20,4)]


-- ************************************************

-- 2. 10p
-- You are given a number n.
-- Use a parallel generator to pair up the first n numbers 
-- divisible by 3 with the ones divisible by 5.
-- Non-parallel solutions are also accepted but then max is 8 points.

--gen2 :: Int -> [(Int, Int)]

--main = print (gen2 0) -- []
--main = print (gen2 3) -- [(3,5),(6,10),(9,15)]
--main = print (gen2 8) -- [(3,5),(6,10),(9,15),(12,20),(15,25),(18,30),(21,35),(24,40)]


-- ************************************************

-- 3. 10p
-- You are given a student, a character, and a number. 
-- If the student'student name contains the given character
-- and the university'student id matches the given number, 
-- increase the student'student neptunID by 100.

rec1 :: Student -> Char -> Int -> Student
rec1 student character number
    | uniID (uni student) == number && character `elem` studentName student = student {neptunID = neptunID student + 100}
    | otherwise = student

-- rec1 student1 'c' 1
-- Student {neptunID = 201, studentName = "Alice", uni = Univ {uniName = "University A", uniID = 1}, grades = [85,90,78]}
-- rec1 student1 'x' 1
-- Student {neptunID = 101, studentName = "Alice", uni = Univ {uniName = "University A", uniID = 1}, grades = [85,90,78]}
-- rec1 student2 'o' 2
-- Student {neptunID = 102, studentName = "Bob", uni = Univ {uniName = "University A", uniID = 1}, grades = [88,92,80]}
-- rec1 student2 'o' 1 
-- Student {neptunID = 202, studentName = "Bob", uni = Univ {uniName = "University A", uniID = 1}, grades = [88,92,80]}


-- ************************************************

-- 4. 20p This question has two parts. 
-- Part 1 - Given a list of students, determine their average grade.
-- Part 2 - Given a specific university and a list of students. 
-- Filter out the students who are not enrolled at the given university. 
-- Return a tuple containing:
   --     1. The name of the university.
   --     2. The average grade of the students attending that university.
-- If no students are enrolled in the university, the average grade should be 0.0.

-- Part 1
studentsAvg :: [Student] -> Double
studentsAvg students 
    | countOfGrades == 0 = 0.0
    | otherwise = fromIntegral sumOfGrades / fromIntegral countOfGrades
    where
        allGrades = concatMap grades students
        sumOfGrades = sum allGrades
        countOfGrades = length allGrades

-- studentsAvg [] -- 0.0
-- studentsAvg [student1] -- 84.33333333333333
-- studentsAvg [student2, student4, student5] -- 86.55555555555556

-- Part 2
averageGradeForUniversity :: Univ -> [Student] -> (String, Double)
averageGradeForUniversity uniInst students = (uniName uniInst, gradesAvg)
    where
        relevantStudents = filter (\s -> uniID (uni s) == uniID uniInst) students
        gradesAvg = studentsAvg relevantStudents

-- averageGradeForUniversity univ1 []
-- ("University A", 0.0)

-- averageGradeForUniversity univ3 [student5]
-- ("University C", 83.66666666666667)

-- averageGradeForUniversity univ1 [student5]
-- ("University A", 0.0)

-- averageGradeForUniversity univ2 [student1, student2, student3, student4, student5, student6]
-- ("University B", 85.0)


-- ************************************************

data Tree a = Node a (Tree a) (Tree a)
            | Leaf
            deriving (Show)

tree1 :: Tree Int
tree1 = Node 0
            (Node 1
                (Node 3 Leaf Leaf)
                (Node 4 Leaf Leaf))
            (Node 2
                (Node 5 Leaf Leaf)
                (Node 6 Leaf Leaf))

tree2 :: Tree Int
tree2 = Node 0
            (Node 1
                (Node 5 Leaf (Node 8 Leaf Leaf))
                Leaf)
            (Node 2 Leaf Leaf)

tree3 :: Tree Int
tree3 = Node 4
            Leaf
            (Node 2
                (Node 1 Leaf Leaf)
                (Node 3 Leaf Leaf))


-- ************************************************

-- 5. 10p
-- Find the left-most node value of a binary tree.
-- For leaves should be error with "tree is empty".

tr1 :: Tree a -> a
tr1 Leaf = error "tree is empty"
tr1 (Node val Leaf right) = val
tr1 (Node val left right) = tr1 left

-- tr1 tree1 -- 3
-- tr1 tree2 -- 5
-- tr1 tree3 -- 4
-- tr1 (Leaf :: Tree Int) -- tree is empty


-- ************************************************

-- 6. 10p
-- You are given a tree and a function.
-- Apply this function to every node of the tree.

tr2 :: Tree a -> (a -> a) -> Tree a
tr2 Leaf f = Leaf
tr2 (Node val left right) f = Node (f val) (tr2 left f) (tr2 right f)

-- tr2 tree2 (+100)
-- Node 100 (Node 101 (Node 105 Leaf (Node 108 Leaf Leaf)) Leaf) (Node 102 Leaf Leaf)

-- tr2 tree2 (*5)
-- Node 0 (Node 5 (Node 25 Leaf (Node 40 Leaf Leaf)) Leaf) (Node 10 Leaf Leaf)

-- tr2 tree3 (\x -> (x `mod` 2) + 50 )
-- Node 50 Leaf (Node 50 (Node 51 Leaf Leaf) (Node 51 Leaf Leaf))


-- ************************************************

-- 7. 10p
-- Given a binary tree where each node contains an integer value, 
-- modify the tree as follows: For each node, calculate the sum 
-- of its direct children (left and right).
-- If the sum of the direct children is negative, set the node'student value to -1 
-- (meaning update the parent node'student value to -1).
-- Otherwise, leave the node'student value unchanged.
-- Leaf nodes remain unchanged, as they don't have any children. 
-- Consider leaves' value 0.
--    Example: 
--      10              -1      parent node updated to -1, since -5 + -3 = -8
--     /  \      ->    /  \
--    -5  -3         -5   -3

changeTree :: Tree Int -> Tree Int
changeTree Leaf = Leaf
changeTree (Node val left right) = Node newVal newLeft newRight
    where
        getValue :: Tree Int -> Int
        getValue Leaf = 0
        getValue (Node val _ _) = val

        newLeft = changeTree left
        newRight = changeTree right
        childSum = getValue newLeft + getValue newRight
        newVal = if childSum < 0 then -1 else val

-- changeTree Leaf -- Leaf
-- changeTree (Node 3 Leaf Leaf) -- Node 3 Leaf Leaf
-- changeTree (Node 10 (Node (-5) Leaf Leaf) (Node (-6) Leaf Leaf)) -- Node (-1) (Node (-5) Leaf Leaf) (Node (-6) Leaf Leaf)

-- changeTree (Node 5 (Node (10) (Node 1 Leaf Leaf) (Node (-1) Leaf Leaf)) (Node 3 Leaf (Node (-8) Leaf Leaf)))
-- Expected Output: Node 5 
--                       (Node (10) 
--                           (Node 1 Leaf Leaf) 
--                           (Node (-1) Leaf Leaf))
--                       (Node (-1)
--                           Leaf 
--                           (Node (-8) Leaf Leaf))


-- ************************************************

-- 8. 10p
-- Implement the Eq instance for an integer Binary Search Tree.
-- Two binary search trees are equal if they have exactly the same node values.

-- instancing for eq 
instance Eq a => Eq (Tree a) where
    Leaf == Leaf = True
    (Node v1 l1 r1) == (Node v2 l2 r2) = (v1 == v2) && (l1 == l2) && (r1 == r2)
    _ == _ = False

tree00,tree11,tree22,tree33:: Tree Int
tree00 = Node 10 (Node 5 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf)) (Node 15 (Node 12 (Node 11 Leaf Leaf) Leaf) (Node 20 Leaf Leaf))
tree11 = Node 10 (Node 5 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf)) (Node 15 (Node 12 Leaf Leaf) (Node 20 Leaf Leaf))
tree22 = Node 10 (Node 5 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf)) (Node 15 (Node 12 Leaf Leaf) (Node 20 Leaf Leaf))
tree33 = Node 10 (Node 5 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf)) (Node 12 (Node 11 Leaf Leaf) (Node 15 Leaf (Node 20 Leaf Leaf)))

-- tree11 == tree22 -- True
-- tree00 == tree33 -- True
-- tree11 == tree33 -- False


-- ************************************************

-- 9. 10p
-- Write a custom Show instance for a the data type Sport. 
-- The instance should format the output to display the name of the sport 
-- and its associated information in the form "Sport_Name: <data>".
-- For example:
-- If the sport is Football with the associated data "Played by 22", the output should be "Football: Played by 22".
-- If the sport is Voleyball with the associated data True, the output should be "Voleyball: True".
-- If the sport is Basketball with the associated data 5, the output should be "Basketball: 5".

data Sport a = Football a | Voleyball a | Basketball a

-- instancing for show 
instance Show a => Show (Sport a) where
    show (Football value) = "Football: " ++ show value
    show (Voleyball value) = "Volleyball: " ++ show value
    show (Basketball value) = "Basketball: " ++ show value

-- Football "Played by 22" -- Football: "Played by 22"
-- Basketball 5 -- Basketball: 5
-- Voleyball True -- Voleyball: True
-- Basketball [1,2,3,4] -- Basketball: [1,2,3,4]

-- ************************************************