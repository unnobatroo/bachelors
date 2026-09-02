{-- WRITE 
NAME 
AND 
NEPTUN CODE 
HERE by this YOU DECLARE this FILE is 
YOUR OWN SOLUTION for functional 
programming midterm  --}



{- 1 ***********************
Write a function sumOfSquaresOfDigits that takes an integer 
and returns the sum of the squares of its digits.
In case of negatives, ignore the sign.
--}

--sumOfSquaresOfDigits :: Int -> Int

--main =  print(sumOfSquaresOfDigits 123)    -- 1^2 + 2^2 + 3^2 = 14
--main =  print(sumOfSquaresOfDigits 405)    -- 4^2 + 0^2 + 5^2 = 41
--main =  print(sumOfSquaresOfDigits (-56))  -- 5^2 + 6^2 = 61 (ignoring the negative sign)
--main =  print(sumOfSquaresOfDigits 0)      -- 0^2 = 0
--main =  print(sumOfSquaresOfDigits 999)    -- 9^2 + 9^2 + 9^2 = 243


{- 2 ***********************
Write a function, that checks if a given integer is prime.
A prime number is only divisible by 1 and itself.
-}

--isPrime :: Int -> Bool

--main = print(isPrime 5)  -- True
--main = print(isPrime 4)  -- False
--main = print(isPrime 13) -- True


{- 3 ***********************
Given a list of integers and a specific integer, 
calculate how many times that integer is followed 
in the list by an odd number, two positions away,
i.e. second neighbour to the right is odd.
Example: 
If the list is [2,3,4,7,2,1,3,5] and the specific integer is 2, 
the output should be 1 because 2 is followed by 
an odd number two positions after it only once ([2,1,3] part).
-}

--countOddGap :: [Int] -> Int -> Int

--main = print(countOddGap [2, 3, 4, 7, 2, 1, 3, 5] 2) -- 1
--main = print(countOddGap [3,7,4,1,4,9,5,3,4,4,8,1] 4) -- 2
--main = print(countOddGap [1,2,3,4,5,6,4,8,8,2,3,1,2,3,3,3,3] 3) -- 3


{- 4 ***********************
Write a function which takes an Integer and returns true if the number is 
all ones in binary representation, false otherwise.
All ones mean all the digits in the binary representation of the number are 1.
For example, 7 is 111 in binary representation, so it is all ones. 
55 is 110111 in binary representation, so it is not all ones.

You can convert the number to binary in the following way:
For example, converting 7:
    For 1st digit: 7 / 2 = 3 remainder 1 -> 1st digit is 1
    For 2nd digit: 3 / 2 = 1 remainder 1 -> 2nd digit is 1
    For 3rd digit: 1 / 2 = 0 remainder 1 -> 3rd digit is 1
    So, 7 is 111 in binary representation.
-}

--isAllOnes :: Int -> Bool

--main = print (isAllOnes 7) -- True
--main = print (isAllOnes 55) -- False
--main = print (isAllOnes 15) -- True
--main = print (isAllOnes 127) -- True


{- 5 ***********************
Write a function which takes a predicate, p, a function f, 
and a list lst, and returns a list that is just like lst, 
but in which every element x that satisfies p is replaced 
by f applied to x (an element x satisfies p if (p x) is True).
-}

--selectiveMap :: (a -> Bool) -> (a -> a) -> [a] -> [a]

--main = print $ selectiveMap odd (\x -> x*x) [] -- []
--main = print $ selectiveMap odd (\x -> 6000) [2,4,6,8,10] -- [2,4,6,8,10]
--main = print $ selectiveMap odd (\x -> 6000) [3,1,-5] -- [6000,6000,6000]
--main = print $ selectiveMap even (\x -> x*x) [1,2,3,4,5] -- [1,4,3,16,5]
--main = print $ selectiveMap (\c -> c == 'q') (\x -> 'u') "quip" -- "uuip"
--main = print $ selectiveMap (== True) not [True,False,True] -- [False,False,False]


{- 6 ***********************
Write a function that takes a string and changes every vowel to O.
-}

--f1 :: String -> String
    
--main = print(f1 "Apple is my favourite electronics company") 
-- "OpplO Os my fOvOOrOtO OlOctrOnOcs cOmpOny"
--main = print(f1 "cad!ng luiks se giad") 
-- "cOd!ng lOOks sO gOOd"


{- 7 ***********************
Given two sorted equally long lists and a number n, 
create a new list which zips the elements of the two lists together.
Only zip the two elements together if their absolute difference is equal to n.
Make sure to remove the duplicates from the resulting list.
Example:
absDiff [2,1,4,2] [5, 3, 1, 5] 3
  |2-5| = 3 which is equal to n=3, so this makes a good pair
  |1-3| = 2 which is NOT equal to n, so omit this pair
  |4-1| = 3 which is equal to 3, so this makes a good pair
  |2-5| = 5 which is equal to 3, so this makes a good pair
  The pairs are [(2,5),(4,1),(2,5)], do not forget to remove the duplicates!
  After removing duplicates, the final list is: [(4,1),(2,5)]
-}

--absDiff :: [Int] -> [Int] -> Int -> [(Int, Int)]

--main = print (absDiff [1..5] [5..10] 4) --[(1,5),(2,6),(3,7),(4,8),(5,9)]
--main = print (absDiff [] [] 2) --[]
--main = print (absDiff [2,1,4,2] [5, 3, 1, 5] 3) --[(4,1),(2,5)]
--main = print (absDiff [3,2,4] [5,5,5] 2) --[(3,5)]


{- 8 ***********************
Write a function that takes a list of tuples, where each tuple 
contains a student's name and a list of their scores.
The function should return a list of tuples, with each tuple 
containing the student's name and their average score.
-}

--averageScores :: [(String, [Int])] -> [(String, Double)]

--main = print (averageScores [("Alice", [10, 20, 30]), ("Bob", [20, 30, 40])]) 
--  [("Alice", 20.0), ("Bob", 30.0)]
--main = print (averageScores [("Charlie", [100]), ("David", [50])]) 
--  [("Charlie", 100.0), ("David", 50.0)]
--main = print (averageScores [("Eve", []), ("Frank", [90, 80])]) 
--  [("Eve", NaN), ("Frank", 85.0)]
--main = print (averageScores [("George", [10, 20, 30, 40]), ("Hannah", [5])]) 
--  [("George", 25.0), ("Hannah", 5.0)]
--main = print (averageScores []) --  []


{- 9 ***********************
Write the function that takes an integer and a list of tuples,
returns only the tuples where the sum of elements are 
greater than the given integer.
-}

--filterTupleSum :: Int -> [(Int, Int)] -> [(Int, Int)]

--main = print (filterTupleSum 5 [(2, 3), (1, 4), (5, 0), (2, 2)]) -- [(2,2)]
--main = print (filterTupleSum 10 [(2, 3), (1, 4), (5, 0), (2, 2)]) -- [(2,3),(1,4),(5,0),(2,2)]
--main = print (filterTupleSum 6 [(3, 3), (2, 4), (1, 5), (6, 0)]) -- []
--main = print (filterTupleSum (-1) [(2, -3), (0, -1), (1, 1), (-1, 0)]) -- [(1,1)]
--main = print (filterTupleSum 7 []) -- []


{- 10 ***********************
Given a list of integers and a target number n, write a function that 
finds all unique pairs of integers within the list where the 
first element is less than n, the second element is greater than 
or equal to n, and the sum of the two elements is even.
Example: For the list [1, 3, 5, 8, 10, 12, 14, 16, 18] and target number 10, 
the pairs that meet these conditions are 
[(1,13),(3,13),(5,13),(8,10),(8,12),(8,14),(8,16),(8,18)]
-}

--findPairs :: [Int] -> Int -> [(Int, Int)] 

-- main = print(findPairs [1, 3, 5, 8, 10, 12, 13, 14, 16, 18] 10)
-- [(1,13),(3,13),(5,13),(8,10),(8,12),(8,14),(8,16),(8,18)]
-- main = print (findPairs [2, 4, 6, 7, 9, 11, 13, 15 ,16, 17] 9) 
-- [(2,16),(4,16),(6,16),(7,9),(7,11),(7,13),(7,15),(7,17)]


{- 11 ***********************
Write a function that takes three parameters:
two integers, start and end, and a Boolean value.
The function should generate a list of the squares or cubes of all integers 
in the inclusive range from start to end that are either even or odd, 
depending on the Boolean value.
If value is True, the function should return the squares of even integers.
If value is False, the function should return the cubes of odd integers.
The function should return an empty list if start is greater than end.
-}

--generateFiltered :: Int -> Int -> Bool -> [Int]

--main = print(generateFiltered 1 10 True) -- [4,16,36,64,100]
--main = print(generateFiltered 1 10 False) -- [1,27,125,343,729]
--main = print(generateFiltered (-4) 4 True) -- [16,4,0,4,16]
--main = print(generateFiltered (-4) 4 False) -- [-27,-1,1,27]
--main = print(generateFiltered 5 1 True) -- []


{- 12 ***********************
-- Every bird in the library has a Letter code (Char). 
See the `birdsName` function: 
-}

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

-- Here is a list of possible birds' names for the new Letter code.
possibleBirds :: [String]
possibleBirds = ["Hummingbird","Dove","Dickcissel","Why Bird","Eagle","Lark"]

{-  
Write a function that generates all new letter codes and 
their possible name pairs for the letters that are not in current lib.
Example: in the second test case, B,K,M are in current lib, but the 
new letters Y could be code for any birds in the 
list `possibleBirds`, so we generate all the pairs.
-}

--newGuess :: String -> [(Char, String)]

--main = print (newGuess "BCKMS")  
-- [] -- all of the letter codes are in currentLib.
--main = print (newGuess "BKMY")   
--  [('Y',"Hummingbird"),('Y',"Dove"),('Y',"Dickcissel"),('Y',"Why Bird"),('Y',"Eagle"),('Y',"Lark")]
--main = print (newGuess "BSLKMYE") 
{-
[('L',"Hummingbird"),('L',"Dove"),('L',"Dickcissel"),('L',"Why Bird"),('L',"Eagle"),('L',"Lark"),
('Y',"Hummingbird"),('Y',"Dove"),('Y',"Dickcissel"),('Y',"Why Bird"),('Y',"Eagle"),('Y',"Lark"),
('E',"Hummingbird"),('E',"Dove"),('E',"Dickcissel"),('E',"Why Bird"),('E',"Eagle"),('E',"Lark")] 
-}