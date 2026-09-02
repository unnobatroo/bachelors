{--   JALOLIDDIN ISMAILOV VIHVFL
by this YOU DECLARE this FILE is 
YOUR OWN SOLUTION for functional 
programming midterm --}


-- 1. --------------------------------
-- Write a function that takes a non-negative integer and returns 
-- the number of odd digits in that integer.
-- If the input is negative, return an error message 
-- as a string "Negative number error".
-- Example: 
-- countOdds 1234 -- 2
-- countOdds 987654321 -- 5

-- countOdds :: Int -> Int

--main = print(countOdds 1234) -- 2
--main = print(countOdds 0) -- 0
--main = print(countOdds 987654321) -- 5
--main = print(countOdds (-5678)) -- "Negative number error"


-- 2. --------------------------------
-- Write a function that takes two lists of integers 
-- and returns a list of integers that appear in both lists, 
-- ensuring each element appears only once in the result, 
-- regardless of how many times it appears in the input lists.
-- Example:
-- commonElements [1, 2, 3, 4, 5] [3, 4, 5, 6, 7]  -- Output: [3, 4, 5]
-- commonElements [10, 20, 30] [30, 40, 10, 50]    -- Output: [10, 30]
-- commonElements [1, 1, 2, 3] [2, 2, 3, 4]        -- Output: [2, 3]

--commonElements :: [Int] -> [Int] -> [Int]

--main = print (commonElements [1, 2, 3, 4, 5] [3, 4, 5, 6, 7])  -- Output: [3, 4, 5]
--main = print (commonElements [10, 20, 30] [30, 40, 10, 50])   -- Output: [10, 30]
--main = print (commonElements [1, 1, 2, 3] [2, 2, 3, 4])       -- Output: [2, 3]


-- 3. --------------------------------
-- Write a function prefixes that takes a list and returns 
-- a list of all prefixes of the original list.
-- Example:
-- prefixes [1, 2, 3]  output: [[1], [1, 2], [1, 2, 3]]

--prefixes :: [a] -> [[a]]

--main = print (prefixes [1, 2, 3])      -- Output: [[1], [1, 2], [1, 2, 3]]
--main = print (prefixes "abc")          -- Output: [["a"], ["a", "b"], ["a", "b", "c"]]
--main = print (prefixes [42, 43])       -- Output: [[42], [42, 43]]
--main = print (prefixes [5])            -- Output: [[5]]


-- 4. --------------------------------
-- Write a function which takes input of 2 matrices (list of lists)
-- of the same size, and output the sum of the 
-- squared differences of corresponding elements.
-- Example:
-- [[1, 2]
--  [4, 5]]
-- [[2, 3]
--  [5, 6]]
-- = (2-1)^2 + (3-2)^2 + (5-4)^2 + (6-5)^2= 4

--ssd :: [[Int]] -> [[Int]] -> Int

--main = print (ssd [[1, 2], [4, 5]] [[2, 3], [5, 6]]) -- 4
--main = print (ssd [[0, 0], [0, 0]] [[0, 0], [0, 0]]) -- 0
--main = print (ssd [[1, 1], [1, 1]] [[2, 2], [2, 2]]) -- 4
--main = print (ssd [[-1, -2], [-3, -4]] [[1, 2], [3, 4]]) -- 120


-- 5. --------------------------------
-- Write a function that takes a list of tuples of integers
-- and returns the sum of all the elements in all the tuples.

--sumPairs :: [(Int, Int)] -> Int

--main = print (sumPairs [(1, 2), (3, 4), (5, 6)]) -- 21
--main = print (sumPairs [(7, 8)]) -- 15
--main = print (sumPairs []) -- 0
--main = print (sumPairs [(1, -2), (-3, 0), (5, -6)]) -- -5


-- 6. --------------------------------
-- Given two lists of integers, calculate the sum of the elements 
-- at odd indices in the first list and the sum of the elements 
-- at even indices in the second list. Return these sums as a tuple.
-- Example: For the lists [1, 2, 3, 4] and [5, 6, 7, 8], 
-- the sum of the elements at odd indices in the first list is 2 + 4 = 6, and 
-- the sum of the elements at even indices in the second list is 5 + 7 = 12. 
-- The function should return the tuple (6, 12).

--indexSums :: [Int] -> [Int] -> (Int,Int)

--main = print (indexSums [1, 2, 3, 4] [5, 6, 7, 8]) -- (6,12)
--main = print (indexSums [3,5,6,7,8] [9,7,4,2,5]) -- (12,18)


-- 7. --------------------------------
-- Write a function that, given a list of integers, returns 
-- all pairs of numbers (a, b) from the list where a is divisible by b.
-- Each pair should be unique, meaning that (a, b) is considered the 
-- same as (b, a) and should only appear once.
-- The order of the output does not matter if it is correct.

--divisiblePairs :: [Int] -> [(Int, Int)]

--main = print (divisiblePairs [2, 3, 4, 6])       -- Output: [(4,2),(6,2),(6,3)]
--main = print (divisiblePairs [10, 5, 2, 1])      -- Output: [(10,5),(10,2),(10,1),(5,1),(2,1)]
--main = print (divisiblePairs [12, 3, 4, 6])      -- Output: [(12,3),(12,4),(12,6),(6,3)]


-- 8. --------------------------------
-- Write a function that takes a list of numbers and counts 
-- how many times a negative number is followed directly by a positive number.
-- Example: 
  -- [-1, 2, -3, -4, 5, 6, -7, 8, -9]
  -- -1 is followed by 2 (1 occurrence)
  -- -3 is followed by -4 (not counted)
  -- -4 is followed by 5 (2 occurrences)
  -- -7 is followed by 8 (3 occurrences)
  -- -9 is followed by nothing (not counted)
  -- result: 3

--countNegativePositivePairs :: [Int] -> Int

--main = print (countNegativePositivePairs [])                               -- 0
--main = print (countNegativePositivePairs [-1, 2, -3, -4, 5, 6, -7, 8, -9]) -- 3
--main = print (countNegativePositivePairs [1, -2, 3, -4, -5, 6])            -- 2
--main = print (countNegativePositivePairs [-1, -2, -3, -4])                -- 0
--main = print (countNegativePositivePairs [10, -5, -6, -7, -8, 9])         -- 1


-- 9. --------------------------------
-- You are given a shopping list where each item is represented 
-- by a tuple consisting of its price (Int) and its category (String). 
-- The tax rate depends on its category.
-- Write a function canAfford that takes the shopping list and 
-- an available amount of money (Int). The function returns True 
-- if the total cost (after tax applied) of all items in the shopping list 
-- does not exceed the available amount of money. Otherwise, it should return False.
-- The tax rates are as follows. You can use getTaxRate to get the tax rate 
-- for a specific category.
-- Food: 5%, Electronics: 15%, Clothing: 10%, 
-- Other: 8% (default rate for any unspecified category)

-- Tax rates for each category !! USE THIS FUNCTION
getTaxRate :: String -> Double
getTaxRate "food" = 0.05    -- 5% tax on food
getTaxRate "electronics" = 0.15   -- 15% tax on electronics
getTaxRate "clothing" = 0.10      -- 10% tax on clothing
getTaxRate _ = 0.08               -- Default 8% tax for other categories

--canAfford :: [(Int, String)] -> Int -> Bool

--main = print (canAfford [(100, "food"), (200, "electronics"), (50, "clothing")] 400)  -- True
-- 100 * 1.05 + 200 * 1.15 + 50 * 1.10 = 105 + 230 + 55 = 390 < 400
--main = print (canAfford [(100, "food"), (200, "electronics"), (50, "clothing")] 300)  -- False
-- 100 * 1.05 + 200 * 1.15 + 50 * 1.10 = 105 + 230 + 55 = 390 > 300
--main = print (canAfford [(100, "food"), (80, "clothing")] 200)  -- True
-- 100 * 1.05 + 80 * 1.10 = 105 + 88 = 193 < 200
--main = print (canAfford [(100, "food"), (200, "electronics"), (400, "furniture")] 760)  -- False
-- 100 * 1.05 + 200 * 1.15 + 400 * 1.08 = 105 + 230 + 432 = 767 > 760


-- 10. --------------------------------
-- Write a function, which generates all perfect numbers up to a given integer n.
-- A perfect number is equal to the sum of its proper divisors.
-- (e.g., 6 is perfect because 1 + 2 + 3 = 6).

--perfectNumbers :: Int -> [Int]

--main = print(perfectNumbers 30) -- [6, 28]
--main = print(perfectNumbers 500) -- [6, 28, 496]


-- 11. --------------------------------
-- Given a list of lists of integers and a number n, change the list by 
-- the range of minimum to maxiumum.
-- [3,2,6] min is 2 and max is 6 so the new list is: [2,3,4,5,6]
-- Afterwards count how many lists are equal to the same sum as n.
-- Example:
-- cntSums [[1,4,2,6], [8,4,2,4], [2,4,2], []] 9
--  [1,4,2,6] turns into [1,2,3,4,5,6], which has a sum of 21, which is not equal to 9
--  [8,4,2,4] turns into [2,3,4,5,6,7,8], which has a sum of 35, not equal to 9
--  [2,4,2]  turns into [2,3,4], which has a sum of 9 which equals 9
--  [] turns into 0, which does not equal n
-- returns 1 as only one changed list has the same sum as 9

--cntSums :: [[Int]] -> Int -> Int

--main = print (cntSums [[1,4,2,6], [8,4,2,4], [2,4,2], []] 9) -- 1
--main = print (cntSums [[], [3,2,1], [4,6]] 0) -- 1
--main = print (cntSums [] 0) -- 0


-- 12. --------------------------------
-- Every bird in the library has a Letter code (Char). See the `birdsName` function:
birdsName :: Char -> String
birdsName 'B' = "Bluebird"
birdsName 'C' = "Cardinal"
birdsName 'S' = "Starling"
birdsName 'K' = "Kestrel"
birdsName 'M' = "Mockingbird"
birdsName _ = error "You just discovered a new bird!"

-- Our current lib collected five letter codes
currentLib :: [Char]
currentLib = ['B','C','S','K','M']

-- Implement the `extendBLib` function which takes a String 
-- (the string of letter codes) as input, then return the tuple (Char, String) 
-- for each letter code containing the letter code of the bird and 
-- the bird's name in a list. (See: test case 1)
-- You can get the bird's name by function `birdsName`.
-- When there is a new letter code and we do not know what is 
-- the name of the bird, return a tuple containing the new letter code, 
-- and "To be named!". (See: test case 2)

--extendBLib :: [Char] -> [(Char, String)]

--main = print (extendBLib "BCKMS")  
-- [('B',"Bluebird"),('C',"Cardinal"),('K',"Kestrel"),('M',"Mockingbird"),('S',"Starling")]
--main = print (extendBLib "BKMY")   
-- [('B',"Bluebird"),('K',"Kestrel"),('M',"Mockingbird"),('Y',"To be named!")]
--main = print (extendBLib "BSLKMY") 
-- [('B',"Bluebird"),('S',"Starling"),('L',"To be named!"),('K',"Kestrel"),('M',"Mockingbird"),('Y',"To be named!")]