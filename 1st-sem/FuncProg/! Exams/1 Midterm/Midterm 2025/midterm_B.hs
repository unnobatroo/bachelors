{-# LANGUAGE ParallelListComp #-}
import Data.Char
import Data.List
import Distribution.Simple.Glob (fileGlobMatches)

-- 1. //////////////////////////////////////////////
-- Write a function that takes a target digit n (from 0 to 9) 
-- and a non-negative integer, and returns how many times n 
-- appears in the digits of that number.

countN :: Int -> Int -> Int
countN digit number = length [ch | ch <- show number, ch == intToDigit digit]

-- countN 3 1234334  -- 3
-- countN 5 55555555 -- 8
-- countN 1 0        -- 0
-- countN 0 1010100  -- 4


-- 2. //////////////////////////////////////////////
-- Write a function that computes the absolute difference 
-- between the factorials of two given non-negative integers.

factorialDiff :: Int -> Int -> Int
factorialDiff a b = abs (factorial a - factorial b)

factorial :: Int -> Int
factorial n = product [1..n]

-- factorialDiff 5 3 -- 114
-- factorialDiff 4 6 -- 696
-- factorialDiff 0 0 -- 0
-- factorialDiff 0 3 -- 5


-- 3. //////////////////////////////////////////////
-- Given a list, keep elements at even positions (starts from 0) 
-- and remove elements at odd positions, and then double the 
-- elements. E.g. [1,2,3,4] -> [2,6]

keepEvenIndices :: [Int] -> [Int]
keepEvenIndices nums = [num * 2 | (num, idx) <- tuplesList, even idx]
  where
    tuplesList = zip nums [0..]

-- keepEvenIndices [1,2,3,4]        -- [2,6]
-- keepEvenIndices [5,6,7,8,9]      -- [10,14,18]
-- keepEvenIndices [10,20,30,40,50] -- [20,60,100]
-- keepEvenIndices []               -- []


-- 4. //////////////////////////////////////////////
-- Create a function that takes a list of non-empty lists of integers 
-- and sums up the middle numbers. If it has even number of items 
-- then the middle item is first item of second half.
-- E.g. if 4 items then 3rd is middle, if 6 items then 4th is middle
-- if just one number in list then it is the middle number.
-- [[1,2,3],[5,7,5,3],[1]] -> 2 + 5 + 1 = 8
-- [[1,2,3,4],[5,7,6,5,3],[2]] -> 3 + 6 + 2 = 11

midSum :: [[Int]] -> Int
midSum numsLists = sum [numsList !! (length numsList `div` 2) | numsList <- numsLists]

-- midSum [[1,2,3],[5,7,5,3],[1]]      -- 8
-- midSum [[1,2,3,4],[5,7,6,5,3],[2]]  -- 11
-- midSum [[1..10],[1..11],[2,10],[1]] -- 23


-- 5. //////////////////////////////////////////////
-- Define a function that takes a list of lists of strings [[String]].
-- For every sublist that contains exactly two strings [sa, sb],
-- produce a string "sa sb!" by inserting a space between the 
-- two words and an exclamation mark at the end.
-- Sublists that do not have exactly two strings should be ignored.

joinPairs :: [[String]] -> [String]
joinPairs stringsLists = [str1 ++ " " ++ str2 ++ "!" | [str1, str2] <- stringsLists]

-- joinPairs [["hello","world"],["foo"],["good","morning"],[]]      -- ["hello world!","good morning!"]
-- joinPairs [["one","two"],["three","four"]]                       -- ["one two!","three four!"]
-- joinPairs [["single"],[],["a","b","c", "d", "e", "f"],["x","y"]] -- ["x y!"]
-- joinPairs []                                                     -- []
-- joinPairs [[], [], []]                                           -- []


-- 6. //////////////////////////////////////////////
-- Given n, generate all the numbers from 1 up to n inclusive 
-- that are divisible by 3 or by 5.

divisible35 :: Int -> [Int]
divisible35 lim = [n | n <- [1..lim], n `mod` 3 == 0 || n `mod` 5 == 0]

-- divisible35 15 -- [3,5,6,9,10,12,15]
-- divisible35 4  -- [3]
-- divisible35 1  -- []
-- divisible35 30 -- [3,5,6,9,10,12,15,18,20,21,24,25,27,30]


-- 7. //////////////////////////////////////////////
-- Write a function that takes a list of integers and a function f.
-- Apply f to each element but only keep results that are positive.
-- E.g. f x = x - 3 and [5,2,8,3] -> after applying f [2,-1,5,0] -> 
-- keep only positives, non-zero values -> [2,5]

positiveResults :: (Int -> Int) -> [Int] -> [Int]
positiveResults f xs = [num | num <- map f xs, num > 0]

-- positiveResults (\x -> x - 3) [5,2,8,3]     -- [2,5]
-- positiveResults (\x -> x * 2 - 10) [3,6,8]  -- [2,6]
-- positiveResults (\x -> x - 5) [1,2,3,4,5,6] -- [1]
-- positiveResults (\x -> -x) [1,2,3]          -- []
-- positiveResults (\x -> x + 1) []            -- []


-- 8. //////////////////////////////////////////////
-- Given 2 points by (x,y) coordinates, write a function that 
-- computes the Euclidean distance between two 2D points:
-- the square root of the sum of the squares of the 
-- x-coordinates difference and y-coordinates difference.

distance :: (Float, Float) -> (Float, Float) -> Float
distance (x1, y1) (x2, y2) = sqrt ((x2 - x1)**2 + (y2 - y1)**2)

-- distance (1.0, 2.0) (4.0, 6.0)   -- 5.0
-- distance (0.0, 0.0) (3.0, 4.0)   -- 5.0
-- distance (-1.0, -2.0) (1.0, 2.0) -- 4.472136
-- distance (0.0, 0.0) (0.0, 0.0)   -- 0.0


-- 9. //////////////////////////////////////////////
-- Write a function that takes a list of integers and groups 
-- consecutive numbers that have the same parity (all even or all odd).
-- [1,3,5,2,4,6,7,9] should produce: [[1,3,5],[2,4,6],[7,9]]
-- [2,4,1,3,5] should produce: [[2,4],[1,3,5]]
-- [] should produce: []

groupByParity :: [Int] -> [[Int]]
-- groupByParity numsList = [[x | x <- numsList, even x], [x | x <- numsList, odd x]]
groupByParity = groupBy (\a b -> even a == even b)

-- groupByParity [1,3,5,2,4,6,7,9] -- [[1,3,5],[2,4,6],[7,9]]
-- groupByParity [2,4,1,3,5]       -- [[2,4],[1,3,5]]
-- groupByParity [])               -- []
-- groupByParity [10]              -- [[10]]
-- groupByParity [1..5]            -- [[1],[2],[3],[4],[5]]


-- 10. //////////////////////////////////////////////
-- Write a function that takes 3 lists and creates a new list:
-- takes all numbers from list1 and takes only those numbers 
-- from list2 that do not appear in list3.

-- E.g. [1..10] [1..10] [2,4,5,3,2,5,11] 
-- list1 -> take all items so [1..10]
-- list2 and list3 -> list2 has 2,3,4,5 which are also in list3
-- so from list2 remove 2,3,4,5 -> [1,6,7,8,9,10]
-- final result -> [1,2,3,4,5,6,7,8,9,10,1,6,7,8,9,10]

threeLists :: [Int] -> [Int] -> [Int] -> [Int]
threeLists list1 list2 list3 = list1 ++ filter (`notElem` list3) list2

-- threeLists [1..10] [1..10] [2,4,5,3,2,5,11] -- [1,2,3,4,5,6,7,8,9,10,1,6,7,8,9,10]
-- threeLists [1..5] [2,4,5,6] [2,5,6]         -- [1,2,3,4,5,4]
-- threeLists [1,4] [5,7,6,5] []               -- [1,4,5,7,6,5]
-- threeLists [1,4] [] []                      -- [1,4]
-- threeLists [] [] []                         -- []