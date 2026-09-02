import Distribution.Simple.Program.HcPkg (list)
import Data.Char (toUpper, isAlpha, isSpace)
import Text.XHtml.Frameset (p)
import Data.Bool (bool)

{-- JALOLIDDIN ISMAILOV VIHVFL by this YOU DECLARE this FILE is 
YOUR OWN SOLUTION for functional
programming midterm --}

{- 1 ===========================
Given a positive integer and a digit, write a function that removes all digits 
in the number that are smaller than the given digit.
Example: For the number 4572 and the digit 5, 
the resulting number should be 57, as all digits smaller than 5 are removed.
-}

changeNumber :: Int -> Int -> Int
changeNumber number treshold =
    let filteredStr = [x | x <- show number, x >= head (show treshold)]
    in
    if null filteredStr
        then 0
        else read filteredStr :: Int

--main = print(changeNumber 45347 4) -- -4547
--main  = print(changeNumber 4572 5) -- 57
--main = print(changeNumber 523189 3) -- 5389


{- 2 ===========================
Write a function that takes a list and replaces
all the zeroes with their respective positions within that list.
Example:
zeroes2Index [0, 10, 0, 0, 0] -> [0,10,2,3,4]
-}

zeroes2Index :: [Int] -> [Int]
zeroes2Index xs = [if v == 0 then i else v | (i, v) <- zip [0..] xs]

--main = print (zeroes2Index [0, 10, 0, 0, 0]) -- [0,10,2,3,4]
--main = print (zeroes2Index [0, 1, 2, 5, 2, 6, 0]) -- [0,1,2,5,2,6,6]
--main = print (zeroes2Index [0, 0, 0, 0, 0]) -- [0,1,2,3,4]
--main = print (zeroes2Index [5, 0, 7, 0, 1, 2, 0, 3]) -- [5,1,7,3,1,2,6,3]


{- 3 ===========================
Write a function that takes a list of integers 
and groups consecutive duplicate elements together.
Example:
groupConsecutive [1,2,2,3,3,3,4] -> [[1], [2, 2], [3, 3, 3], [4]]
-}

--groupConsecutive :: [Int] -> [[Int]]

--main = print(groupConsecutive [1, 2, 2, 3, 3, 3, 4])      -- [[1],[2,2],[3,3,3],[4]]
--main = print(groupConsecutive [5, 5, 5, 5])               -- [[5,5,5,5]]
--main = print(groupConsecutive [1, 2, 3, 4])               -- [[1],[2],[3],[4]]
--main = print(groupConsecutive [1, 1, 2, 2, 3, 4, 3, 4])   -- [[1,1],[2,2],[3,3],[4,4]]


{- 4 ===========================
Write a function that takes a list of tuples (Int, Int) and 
returns a list of tuples where each tuple contains the absolute
value of the difference of the two elements of the original tuple 
as the first element, and the original tuple as the second element.
Example:
tupleDifferences [(3, 5), (10, 2), (4, 4)] -> [(2, (3, 5)), (8, (10, 2)), (0, (4, 4))]
-}

--tupleDifferences :: [(Int, Int)] -> [(Int, (Int, Int))]

--main = print(tupleDifferences [(3,5),(10,2),(4,4)]) 
-- [(2,(3,5)),(8,(10,2)),(0,(4,4))]
--main = print(tupleDifferences [(2,4),(-10,2),(4,-4),(0,0)]) 
-- [(2,(2,4)),(12,(-10,2)),(8,(4,-4)),(0,(0,0))]
--main = print(tupleDifferences [(3,5),(15,15),(-4,-9),(88,-99)]) 
-- [(2,(3,5)),(0,(15,15)),(5,(-4,-9)),(187,(88,-99))]


{- 5 ===========================
Write a function that takes a list of Integers, lst, 
and returns a list of Integers that is just like lst,
except that each odd element of lst
is replaced by the square of that element.
-}

squareOdds :: [Integer] -> [Integer]
squareOdds xs = [if even x then x else x * x | x <- xs]

--main = print $ squareOdds [] -- []
--main = print $ squareOdds [3] -- [9]
--main = print $ squareOdds [4,3] -- [4,9]
--main = print $ squareOdds [1,2,3,4,5,6] -- [1,2,9,4,25,6]
--main = print $ squareOdds [3,22,3] -- [9,22,9]


{- 6 ===========================
Write a function that takes a function and a list,
and applies the function to each element
along with its index in the list.
-}

mapWithIndex :: (Int -> a -> b) -> [a] -> [b]
mapWithIndex f (x:xs) = helperFunction 0 (x:xs)
    where
        helperFunction _ [] = []
        helperFunction idx (x:xs) = f idx x : helperFunction (idx + 1) xs

--main = print (mapWithIndex (\i x -> show i ++ ":" ++ x) ["a", "b", "c"]) 
-- ["0:a", "1:b", "2:c"]
--main = print (mapWithIndex (\i x -> i + 1) [5, 10, 15])                  
-- [1, 2, 3]
--main = print (mapWithIndex (\i x -> (i, x)) [True, False])              
-- [(0, True), (1, False)]
--main = print (mapWithIndex (\i x -> x + i) [10, 20, 30])                 
-- [10, 21, 32]


{- 7 ===========================
Write a function, that takes a list of tuples where 
each tuple contains a name and an age.
The function returns a pair of lists: one containing 
all the names and another containing all the ages.
-}

extractNamesAndAges :: [(String, Int)] -> ([String], [Int])
extractNamesAndAges = unzip

--main = print(extractNamesAndAges [("Alice", 30), ("Bob", 25), ("Charlie", 35)])
-- (["Alice", "Bob", "Charlie"], [30, 25, 35])
--main = print(extractNamesAndAges [("John", 40), ("Doe", 28)])
-- (["John", "Doe"], [40, 28])
--main = print(extractNamesAndAges []) -- ([], [])


{- 8 ===========================
Given a string s, your task is to return another string such that 
even-indexed and odd-indexed characters of s are grouped and 
the groups are space-separated. Even-indexed group comes as first, 
followed by a space, and then by the odd-indexed part.
Examples:
input:    "meirdmt" => "midt erm"
           |||||||      |||| |||
indices:   0123456      0246 135
-}

sortMyString :: String -> String
sortMyString str = evenIdx ++ " " ++ oddIdx
    where
        indexedChars = zip [0..] str
        oddIdx = [c | (i, c) <- indexedChars, odd i]
        evenIdx = [c | (i, c) <- indexedChars, even i]

--main = print (sortMyString "01234567") -- "0246 1357"
--main = print (sortMyString "caonrsrweecrt!") -- "correct answer!"
--main = print (sortMyString "meirdmt") -- "midt erm"
--main = print (sortMyString "FPurnocgtriaomnianlg") -- "Functional Programing"


{- 9 ===========================
Write a function that takes a list of strings and returns a single string.
Every vowel should be changed to "O" (uppercase o) and every consonant should be 
changed to "l" (lowercase L), special characters are unchanged,
a space should be added after every substring and a "." at the end.
Example: ["Hi", "how", "are", "you"] -- "lO lOl OlO lOO ."
-}

f5 :: [String] -> String
f5 listOfStrings =
    let transformedSubstrings = map transformString listOfStrings
    in unwords transformedSubstrings ++ " ."

transformChar :: Char -> Char
transformChar ch
    | isVowel ch = 'O'
    | isConsonant ch = 'l'
    | otherwise = ch

isVowel :: Char -> Bool
isVowel ch = upperCh `elem` "AEIOU"
    where upperCh = toUpper ch

isConsonant :: Char -> Bool
isConsonant ch = isAlpha ch && not (isVowel ch)

transformString :: String -> String
transformString = map transformChar

--main = print(f5 ["Hi", "how", "are", "you"])
-- "lO lOl OlO lOO ."
--main = print(f5 ["functional", "Programming", "is", "nice", "&", "very", "fun", "$$ :)"]) 
-- "lOlllOOlOl llOllOllOll Ol lOlO & lOll lOl $$ :) ."


{- 10 ===========================
Given a list of lists, keep only those sublists that 
have more positive numbers than negative numbers. 
If a sublist is empty, it should be removed. 
-}

keepMorePositives :: [[Int]] -> [[Int]]
keepMorePositives = filter p
    where
        p :: [Int] -> Bool
        p [] = False
        p xs = countPositives xs > countNegatives xs

        countPositives :: [Int] -> Int
        countPositives = length . filter (> 0)

        countNegatives :: [Int] -> Int
        countNegatives = length . filter (< 0)

--main = print (keepMorePositives [[1, -2, 3], [-1, -3], [4, 5, -6], [], [0, 1, 2]]) 
-- [[1,-2,3],[4,5,-6],[0,1,2]]
--main = print (keepMorePositives [[-1, 2, -3], [-4, -5], [0, -6, 7, -8]]) -- []
--main = print (keepMorePositives [[1, 2, -1], [4, 5, 6], [3, -2, 1, 1]])
-- [[1,2,-1],[4,5,6],[3,-2,1,1]]
--main = print (keepMorePositives [[0, 1, -1, 2], [3, 0, -4], [5, 5, -2, 0]])
-- [[0,1,-1,2],[5,5,-2,0]]
--main = print (keepMorePositives [[], [], []]) -- []


{- 11 ===========================
You are waiting at a bus station. You have a list of bus information. 
Each bus is represented as a tuple with a String representing the destination 
and a tuple (Int, Int) representing the arrival time to the destination 
in 24-hour format (hours, minutes).
Write a function which takes a list of tuples with String and (Int, Int) 
and your destination and returns the earliest arrival time to your destination.
If the bus to your destination is not in the list, give the error 
message "No bus to the destination".
-}

-- earliestBus :: [(String, (Int, Int))] -> String -> (Int, Int)

--main = print (earliestBus [("Keleti pályaudvar", (10, 30)), ("Deák Ferenc tér", (8, 15)), ("Deák Ferenc tér", (9, 30)), ("Deák Ferenc tér", (8, 45)), ("Kálvin tér", (11, 0))] "Deák Ferenc tér") 
-- (8,15)
--main = print (earliestBus [("Keleti pályaudvar", (10, 30)), ("Deák Ferenc tér", (8, 15)), ("Keleti pályaudvar", (10, 0)), ("Deák Ferenc tér", (9, 30)), ("Kálvin tér", (11, 0)), ("Keleti pályaudvar", (11, 45))] "Keleti pályaudvar") 
-- (10,0)
--main = print (earliestBus [("Keleti pályaudvar", (10, 30)), ("Deák Ferenc tér", (8, 15)), ("Deák Ferenc tér", (9, 30)), ("Kálvin tér", (11, 0))] "Kálvin tér") 
-- (11,0)
--main = print (earliestBus [("Széll Kálmán tér", (6, 30)), ("Széll Kálmán tér", (6, 15)), ("Széll Kálmán tér", (7, 0)), ("Keleti pályaudvar", (8, 0))] "Széll Kálmán tér") 
-- (6,15)


{- 12 ===========================
Write a function that takes a list of integers representing students' 
exam scores. The function should perform the following tasks:
Filter out scores that are below 0 or above 100 (considered invalid).
Calculate the average of the valid scores.
Count how many students scored above the average.
Return a tuple containing the average score and the count 
of students who scored above the average.
-}

analyzeScores :: [Int] -> (Double, Int)
analyzeScores scoresList =
    let
        validScores = [score | score <- scoresList, score >= 0 && score <= 100]
        n = length validScores
        avg = if n == 0
                then 0.0
                else fromIntegral (sum validScores) / fromIntegral n

        countAboveAvg = length (filter (\score -> fromIntegral score > avg) validScores)
    in
        (avg, countAboveAvg)

--main = print(analyzeScores [85, 92, 100, 67, -10, 120, 75]) -- (83.8, 3)
--main = print(analyzeScores [-5, 150, 200]) -- (NaN, 0) -- NaN is an error value for non-numeric values
--main = print(analyzeScores [50, 70, 90]) -- (70.0, 1)
--main = print(analyzeScores []) -- (0.0, 0)