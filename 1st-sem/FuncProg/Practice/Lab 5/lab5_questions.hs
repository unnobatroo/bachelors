{-# OPTIONS_GHC -Wno-unrecognised-pragmas #-}
{-# HLINT ignore "Avoid lambda using `infix`" #-}
main :: IO ()
main = print 0


---- Higher order functions

-- Earlier exemples rewritten with higher order functions: map, foldr, filter, takeWhile. 
-- Operations with lists: write functions for the followings



-- 1. Keep the head of every sublist (assume sublists are not empty).
-- e.g. [[1, 2, 3], [3, 4], [5, 7, 8, 9]] -> [1, 3, 5]

heads :: [[Int]] -> [Int]
heads x = undefined

--main = print(heads [[1, 2, 3], [3, 4], [5, 7, 8, 9]]) -- [1,3,5]



-- 2. Add 100 to the numbers of a list.

g :: Int -> Int
g x = x + 100

add100 :: [Int] -> [Int]
add100 x = undefined

add100' :: [Int] -> [Int]
add100' x = undefined

--main = print(add100 [1..8]) -- [101,102,103,104,105,106,107,108]
--main = print(add100' [1..8]) -- [101,102,103,104,105,106,107,108]



-- 3. Triple the elements of a list.

triples :: [Int] -> [Int]
triples = map (\a -> a * 3)

--main = print(triples [1..5]) -- [3,6,9,12,15]

triples2 :: [Int] -> [Int]
triples2 x = undefined

--main = print(triples2 [1..5]) -- [3,6,9,12,15]



-- 4. Check if the numbers of a list are odd.

isoddnrs :: [Int] -> [Bool]
isoddnrs = map odd

isoddnrs2 :: [Int] -> [Bool]
isoddnrs2 x = undefined

--main = print(isoddnrs [1..5]) -- [True,False,True,False,True]
--main = print(isoddnrs2 [1..5]) -- [True,False,True,False,True]



-- 5. Compute the cube of the numbers of a list.

cubes :: [Int] -> [Int]
cubes = map (^3)

--main = print(cubes [1..10]) -- [1,8,27,64,125,216,343,512,729,1000]
--main = print(cubes []) -- []

cubes2 :: [Int] -> [Int]
cubes2 x = undefined -- \x -> x ^ 3 


--main = print(cubes2 [1..10]) -- [1,8,27,64,125,216,343,512,729,1000]

cubes3 :: [Int] -> [Int]
cubes3 x = undefined

--cubes3 x = map (^3) x -- \x -> x^3  same without space

--main = print(cubes3 [1..10]) -- [1,8,27,64,125,216,343,512,729,1000]



-- do not confuse cubes of number with powers of 3 with !!!
powersof3 :: [Int] -> [Int]
powersof3 x = undefined

--main = print(powersof3 [1..10]) -- [3,9,27,81,243,729,2187,6561,19683,59049]


powersof33 :: [Int] -> [Int]
powersof33 x = undefined  -- \x -> 3^x   -- prefix notation
--powersof33 x = map ((^)3) x -- same without space

--main = print(powersof33 [1..10]) 
--[3,9,27,81,243,729,2187,6561,19683,59049]



-- 6. Delete the last element of each sublist of a list.
-- E.g. for [[1,2,3],[5,6],[],[7,8,9,10]] -> [[1,2],[5],[],[7,8,9]]

lastdel :: [[Int]] -> [[Int]]
lastdel = map init

--main = print(lastdel [[1,2,3],[5,6],[7,8,9,10]]) -- [[1,2],[5],[7,8,9]]



-- 7. Insert 0 in front of every sublist of a list.
-- E.g. for [[1,2,3],[5,6],[],[7,8,9,10]] the result is 
-- [[0,1,2,3],[0,5,6],[0],[0,7,8,9,10]]

ins0 :: [[Int]] -> [[Int]]
ins0 x = undefined   -- \x -> [0] ++ x

--main = print(ins0 [[1,2,3],[5,6],[],[7,8,9,10]]) -- [[0,1,2,3],[0,5,6],[0],[0,7,8,9,10]]



ins0' :: [[Int]] -> [[Int]]
ins0' x = undefined    -- \x -> [0] ++ x


--main = print(ins0' [[1,2,3],[5,6],[],[7,8,9,10]]) -- [[0,1,2,3],[0,5,6],[0],[0,7,8,9,10]]


-- insert 0 at the end!!

ins02 :: [[Int]] -> [[Int]]
ins02 x = undefined  -- \x -> x ++ [0]

--main = print(ins02 [[1,2,3],[5,6],[],[7,8,9,10]]) 
-- [[1,2,3,0],[5,6,0],[0],[7,8,9,10,0]]



-- 8. Compute the squares of the elements of a list using map.
-- Then compute squares for y list of list.
-- [1, 2, 3] -> [1, 4, 9]

sq :: Int -> Int
sq x = x*x

sqrs :: [Int] -> [Int]
sqrs x = undefined

--main = print(sqrs [1..10]) -- [1,4,9,16,25,36,49,64,81,100]



-- 9. Write a function for the square of every element of a list in a sublists.
-- [[1,2],[3,4,5,6],[],[7,8]]  ->  [[1,4],[9,16,25,36],[],[49,64]]  
-- hint: map in map

sqrs_lists :: [[Int]] -> [[Int]]
sqrs_lists y = undefined

--main = print(sqrs_lists [[1,2],[3,4,5,6],[],[7,8]]) -- [[1,4],[9,16,25,36],[],[49,64]]


-- Same as 8. and 9. with lambda expression.

sq_lambda :: [Int] -> [Int]
sq_lambda y = undefined

--main = print(sq_lambda [1..10]) -- [1,4,9,16,25,36,49,64,81,100]


sqrs_lambda :: [[Int]] -> [[Int]]
sqrs_lambda x = undefined

--main = print(sqrs_lambda [[1,2],[3,4,5,6],[],[7,8]]) -- [[1,4],[9,16,25,36],[],[49,64]]



-- 10. Replicate n>0 times the element of a list e.g. n=3 [3..6] ->
-- [[3,3,3],[4,4,4],[5,5,5],[6,6,6]]

rep :: Int -> Int -> [Int]
rep 0 x = []
rep n x = x : rep (n-1) x

--main = print(rep 3 7) -- [7,7,7]

rep1 :: Int -> Int -> [Int]
rep1 = replicate   -- built-in replicate

--main = print(rep1 3 7) -- [7,7,7] 

f5 :: Int -> [Int] -> [[Int]]
--f5 n x = map (\ x = rep n x) x
f5 n x = undefined   -- partial parameterization !!!

--main = print(f5 3 [3..6]) -- [[3,3,3],[4,4,4],[5,5,5],[6,6,6]]

f51 :: Int -> [Int] -> [[Int]]
f51 n x = undefined

--main = print(f51 3 [3..6]) -- [[3,3,3],[4,4,4],[5,5,5],[6,6,6]]

f52 :: Int -> [Int] -> [[Int]]
f52 n x = undefined

--main = print(f52 3 [3..6]) -- [[3,3,3],[4,4,4],[5,5,5],[6,6,6]]



-- review foldr
-- main = print(foldr (+) 1 [4,5,6])  --(4 + (5 + (6 + 1))) 16


-- 11.  Add the numbers from 1..N (N positive and not 0) using foldr.

addn :: Int -> Int
addn n = undefined

--main = print(addn 5) -- 15
--main = print(addn 0) -- 0
--main = print(addn (-2))  -- 0
--main = print(addn 10) --55



-- review filter
-- 12. Compute the double of the positive elements of a list [1, 2, -2, 3, -4] -> [2, 4, 6]
-- hints: first filter it then use map 

f20 :: [Int] -> [Int]
f20 x = undefined   -- \x -> 0 < x


--main = print(f20 [1, 2, (-2), 3, (-4)]) -- [2, 4, 6]

f202 :: [Int] -> [Int]
f202 x = undefined

--main = print(f202 [1, 2, (-2), 3, (-4)]) -- [2, 4, 6]



-- 13. Filter the elements smaller then n, e.g. n=3 [1,5,3,2,1,6,4,3,2,1] -> [1,2,1,2,1]

f7 :: Int -> [Int] -> [Int]
--f7 n x = filter ((>) n) x 
f7 n x = undefined  -- both are good

--main = print(f7 3 [1,5,3,2,1,6,4,3,2,1])  -- [1,2,1,2,1]



-- 14. Write a function that keeps the integers of a list up to the first 0 encounterred 
-- and then divides by 2 every element [1, 2, -2, 3, 0, -4] -> [0, 1, -1, 1]
-- hints: use takeWhile then map

f3 :: [Int] -> [Int]
f3 x = undefined -- (/=) 0 x     

--main = print(f3 [1, 2, (-2), 3, 0, (-4)]) -- [0, 1, -1, 1]

f32 :: [Int] -> [Int]
f32 x = undefined -- 0 /= x

--main = print(f32 [1, 2, (-2), 3, 0, (-4)]) -- [0, 1, -1, 1]



-- 15. Insert the sum of elements of the sublist as last element in every sublist of a list.

insLast :: [Int] -> [Int]
insLast list = list ++ [sum list]

insSum :: [[Int]] -> [[Int]]
insSum lists = undefined

insSum2 :: [[Int]] -> [[Int]]
insSum2 lists = undefined

--main = print(insSum [[1,2], [3,4,5], [6,5,9,7], [], [8]]) -- [[1,2,3],[3,4,5,12],[6,5,9,7,27],[0],[8,8]]
--main = print(insSum2 [[1,2], [3,4,5], [6,5,9,7], [], [8]]) -- [[1,2,3],[3,4,5,12],[6,5,9,7,27],[0],[8,8]]