import Data.Time.Clock.TAI (taiClock)
main :: IO()

-- 1 ----------------------------------------------------------------------
-- Given a list of Ints, remove the element at the given position.

-- remElemAt :: Int -> [Int] -> [Int]

-- main = print (remElemAt 6 [1..7]) -- [1,2,3,4,5,6]
-- main = print (remElemAt 2 [1..7]) -- [1,2,4,5,6,7]
-- main = print (remElemAt 9 [1..7]) -- [1,2,3,4,5,6,7]



-- 2 ----------------------------------------------------------------------
-- Switch places the first and last element of a 3 element list.

--reorder :: [String] -> [String]

-- main = print (reorder ["tail", "body", "head"])              -- ["head", "body", "tail"]
-- main = print (reorder ["tails", "body", "heads"] )           -- ["heads", "body", "tails"]
-- main = print (reorder ["ground", "rainbow", "sky"])          -- ["sky", "rainbow", "ground"]



-- 3 ----------------------------------------------------------------------
-- Write a function to convert a list of a person's names into initials (first letter sepparated by a '.').

initials :: [String] -> String
initials [] = ""
initials [name] = [head name]
initials (name:names) = head name : '.' : initials names

-- main = print (initials ["Sam", "Harris"]) -- "S.H."
main = print (initials ["Howard", "Phillips", "Lovecraft"]) -- "H.P.L."



-- 4 ----------------------------------------------------------------------
-- Given a list of integers, find the minimum of a list (assume the list is not empty).
minimum1 :: [Int] -> Int
minimum1 [x] = x
minimum1 (x : y : xs)
    | x < y = minimum1 (x : xs)
    | otherwise = minimum1 (y : xs)

-- [1,0,3,4,5]
-- minimum1 [0,3,4,5]
-- minimum1 [0,4,5]
-- minimum1 [0,5]
-- minimum1 [0]

-- main = print (minimum1 [1..5])        -- 1
-- main = print (minimum1 [10,9,8,7,6])  -- 6
-- main = print (minimum1 [8,6,4,10,12]) -- 4

minimum2 :: [Int] -> Int
minimum2 [x] = x
minimum2 (x : xs) = min x (minimum2 xs)

-- minimum2 [1,2,0,-1] -> min 1 (min 2 (min 0 (-1)))
-- minimum2 [1,2,0,-1] -> min 1 (min 2 -1)
-- minimum2 [1,2,0,-1] -> min 1 -1
-- minimum2 [1,2,0,-1] -> -1

-- main = print (minimum2 [1..5])        -- 1
-- main = print (minimum2 [10,9,8,7,6])  -- 6
-- main = print (minimum2 [8,6,4,10,12]) -- 4

minimum3 :: [Int] -> Int
minimum3 x = minimum x

-- main = print (minimum3 [1..5]) -- 1



-- 5 ----------------------------------------------------------------------
-- Print the max and min number of a string

maxmin :: [Int] -> String
maxmin l = auxMaxMin (tail l) (head l) (head l)

--auxMaxMin :: [Int] -> Int -> Int -> String

-- main = print (maxmin [4,6,2,1,9,63,-134,566]) -- "max = 566, min = -134"
-- main = print (maxmin [-52, 56, 30, 29, -54, 0, -110]) -- "max = 56, min = -110"
-- main = print (maxmin [5]) -- "max = 5, min = 5"



-- 6 ----------------------------------------------------------------------
-- Check if a list of Booleans contains a True value.

--ifOneTrue :: [Bool] -> Bool

--  False || False || False || False

-- main = print (ifOneTrue [False, False, False]) -- False

ifOneTrue' :: [Bool] -> Bool
ifOneTrue' xs = or xs

-- main = print (ifOneTrue' [False, False, False]) -- False



-- 7 ----------------------------------------------------------------------
-- Check if all elements of a list of Booleans are True.

--ifAllTrue :: [Bool] -> Bool

--  True && False && True && True

-- main = print (ifAllTrue [True, False, True]) -- False

ifAllTrue' :: [Bool] -> Bool
ifAllTrue' xs = and xs

-- main = print (ifAllTrue' [True, False, True]) -- False



-- 8 ----------------------------------------------------------------------
-- Delete every second element from a list.

--del2 :: [Int] -> [Int]

-- main = print (del2 [1..10]) -- [1,3,5,7,9]
-- main = print (del2 [1..11]) -- [1,3,5,7,9,11]



-- 9 ----------------------------------------------------------------------
-- Check if a list contains 2 equal elements one after the other
-- (they can be anywhere in the list) and count such equalitites
-- for [1,2,2,3,4,3,3,2,4,5,5,5] is 4 for [1 .. 5] is 0.

--fe :: [Int] -> Int

-- main = print (fe [1,2,2,2,3,4,3,3,2,4,5,5,5,5,5,5,5,5,5]) -- 11