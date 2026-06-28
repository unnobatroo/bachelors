-- WRITE NAME AND NEPTUN CODE HERE !!!!

import Data.List

main :: IO()

{-1.
Write a function that takes an integer n and sum the squares 
of even numbers and subtract the sum of squares of odd numbers,
in the range of 1 to n.
Example:
sumAlternatingSquares 5  -- 10  (2^2 + 4^2 - 1^2 - 3^2 - 5^2 = 10)
sumAlternatingSquares 10 -- 220 (2^2 + 4^2 + 6^2 + 8^2 + 10^2 - 1^2 - 3^2 - 5^2 - 7^2 - 9^2 = 55)
-}

--sumAlternatingSquares :: Int -> Int

--main = print (sumAlternatingSquares 5)   -- Output: -15
--main = print (sumAlternatingSquares 10)  -- Output: 55
--main = print (sumAlternatingSquares 7)   -- Output: -28

------------------------------------------------

{-2.
Given a positive n, generate a list of all Pythagorean triples (a,b,c) 
consisting of numbers between 1 and n (a,b,c should be less than n),
and co-prime (their greatest common divisor is 1).
A Pythagorean triple has the sum of squares of two numbers equal to 
the square of third number. Example: (4,3,5) -> (3^2 + 4^2 = 5^2)
There should be no duplicates, eg. (3,4,5) (4,3,5) and (5,3,4) are duplicates,
and a,b,c should be in increasing order.
-}

--f4 :: Int -> [(Int, Int, Int)]

--main = print (f4 10) --[(3,4,5)]
--main = print (f4 30) --[(3,4,5),(5,12,13),(8,15,17),(7,24,25),(20,21,29)]
--main = print (f4 1) -- []

------------------------------------------------

{-3.
Write a function to create a string in the following way:
'S' and 's' are special character in the string. 
Whenever there are three more characters after 'S' or 's', 
replace them using this formula: "Sabc" = "acbc", see tests.
If more special letters following each other, use them to continue 
processing the string. If the number of characters after 'S' or 's' 
is less than 3, no changes applied.
-}

--computeS :: String -> String

--main = print (computeS "Sgu" )  -- "Sgu"
--main = print (computeS "Sfgu" )  -- "fugu"
--main = print (computeS "cSguka")  --"cgkuka"
--main = print (computeS "SSguSab" )  --"uuguSab"
-- main = print (computeS "sfguSfguxyzSfguSfgu") --"fugufuguxyzfugufugu"

------------------------------------------------

{-4.
Write a function that takes a starting number, an ending, and 
an increment number. It generates a string with numbers starting from 
the initial value, adding the increment repeatedly until the next 
number would be greater than the ending number.
Example: 
start = 2 and end = 12 and increment = 2, the output is "2 4 6 8 10 12 "
start = 1, end = 10, increment = 2, the output is "1 3 5 7 9 "
start = 10, end = 1, increment = 5 the output should be ""
-}

--generateSumString :: Int -> Int -> Int -> String

--main = print (generateSumString 1 10 2) -- Expected: "1 3 5 7 9 "
--main = print (generateSumString 0 15 5)  -- Expected: "0 5 10 15 "
--main = print (generateSumString 10 10 2) -- Expected: "10 "
--main = print (generateSumString 11 10 5) -- Expected: ""

------------------------------------------------

{-5.
Given a list of points ("pointname",(x,y)) name-coordinates and another point p,
return the point which is closest to p in a string containing the name of both. 
Print error "Empty list!" in case the list is empty.
Example:
distanceCalc [("p1", (-1, -1)), ("p2", (2, 2)), ("p3", (-2, 4))] ("c1", (2, 0))
    First measure the distance from p1 to c1, which in this case is: 3.16
    Next you do the same but for p2 to c1, which equals: 2.0
    Last you do it again but for p3 to c1, which equals: 5.65
Get the minimum of the distances and return a string which contains the 
name of the closest point and the name of the given point: 
"p2 is the closest to point c1"
Hint: 
To get the distance between two points use the following formula:
sqrt((x2-x1)^2+(y2-y1)^2)
-}

--dist :: (Int, Int) -> (Int, Int) -> Double

--distanceCalc :: [(String, (Int, Int))] -> (String, (Int, Int)) -> String

--main = print (distanceCalc [("p1", (0, 0))] ("p2", (4, 0))) 
-- "p1 is the closest to point p2"
--main = print (distanceCalc [("p1", (-1, -1)), ("p2", (2, 2)), ("p3", (-2, 4))] ("c1", (2, 0))) 
-- "p2 is the closest to our point c1"
--main = print (distanceCalc [("p1", (2, -5)), ("p2", (3, -3)), ("p1", (1, 3))] ("k1", (0, 0))) 
-- "p1 is the closest to our point k1"
--main = print (distanceCalc [] ("n1", (5, -2))) 
-- Empty list!

------------------------------------------------

{-6.
Given a list of integers, return the length of the longest increasing by one 
contiguous subsequence. 
Eg. [1,2,2,3,4] Output: 3 ([2,3,4] is the longest increasing subsequence)
-}

--t1 :: [Int] -> Int

--main = print (t1 [1, 2, 2, 3, 4]) -- 3
--main = print (t1 [1..20]) -- 20
--main = print (t1 [5, 4, 3, 2, 1]) -- 1
--main = print (t1 [1,2,1,2,3,1,1,1,2,3,4,5,6]) -- 6
--main = print (t1 []) -- 0

------------------------------------------------

{-7.
Given a list of integers, return the product of all prime numbers found at odd indices.  
Input: [2, 3, 5, 7, 11] 
Output: 21 (Only 3 and 7 are at odd indices and prime, 3 * 7 = 21)
-}

--t3 :: [Int] -> Int

--main = print (t3 [2, 3, 5, 7, 11]) -- 21
--main = print (t3 [2, 3, 5, 7, 11, 13]) -- 273
--main = print (t3 [4, 6, 8, 10, 12]) -- 1
--main = print (t3 [2, 2, 3, 4, 5, 6, 7, 8, 11]) -- 2
--main = print (t3 []) -- 1

------------------------------------------------

{-8.
Write a function makeNegative that takes two lists.
Make all numbers of the first given list negative.
Then eliminate these values from the second list. 
0 stays 0.
-}

--makeNeg :: [Int] -> [Int]

-- main = print (makeNeg [0]) -- [0]
-- main = print (makeNeg [1,2,3]) -- [-1,-2,-3]
-- main = print (makeNeg [1,-2,4,5,-3,-5]) -- [-1,-2,-4,-5,-3,-5]

--makeNegative :: [Int] -> [Int] -> [Int]

-- main = print (makeNegative [0] [0]) -- []
-- main = print (makeNegative [] []) -- []
-- main = print (makeNegative [1,2,3] [-1,3,2,-3,20,1])  -- [3,2,20,1]
-- main = print (makeNegative [1,-2,4,5,-3,-5] [1,2,3,-1,-2,3,-10,5,8,-6,-4,4]) -- [1,2,3,3,-10,5,8,-6,4]
-- main = print (makeNegative [1..5] [1..5]) -- [1,2,3,4,5]

------------------------------------------------

{-9. 20 points
Given a list of tuples containing employees' names, ages, and salaries, 
1. What is the median salary of the employees? 5p
(If there is an even number of employees, return the average of the two middle salaries.)
2. If the employer increases the salaries by 10% for employees older than 50, 
what will be the total new salary? 5p
3. Write in a list the names of those who can not get salary increase. 5p
4. Return the list of names of employees who are younger than 30 and earn more than 250. 5p
-}


--median :: [Double] -> Double

--medianSalary :: [(String, Int, Int)] -> Double

--main = print(medianSalary[("John", 23, 200), ("Bob", 60, 700), ("Anna", 38, 427), ("Joe", 36, 289), ("Doe", 22, 384), ("Marie", 55, 572), ("Lucy", 37, 400)]) --400.0

--newPay  :: [(String, Int, Int)] -> Double

--main = print(newPay [("John", 23, 200), ("Bob", 60, 700), ("Anna", 38, 427), ("Joe", 36, 289),("Doe", 22, 384), ("Marie", 55, 572), ("Lucy", 37, 400)]) --127.2

--namesNoinc :: [(String, Int, Int)] -> [String]

--main = print(namesNoinc [("John", 23, 200), ("Bob", 60, 700), ("Anna", 38, 427), ("Joe", 36, 289),("Doe", 22, 384), ("Marie", 55, 572), ("Lucy", 37, 400)]) --127.2
-- ["John","Anna","Joe","Doe","Lucy"]

--namesYounger30 :: [(String, Int, Int)] -> [String]

--main = print(namesYounger30[("John", 23, 200), ("Bob", 60, 700), ("Anna", 38, 427), ("Joe", 36, 289),("Doe", 22, 384), ("Marie", 55, 572), ("Lucy", 37, 400)]) --["Doe"]

------------------------------------------------

