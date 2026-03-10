main :: IO ()
{--
Neptun: VIHVFL
Name: Jaloliddin Ismailov
--}

-- Write a function doubleEven that takes a list of integers
-- and returns a list of integers where each even number is doubled
-- and each odd number is removed.
-- hint : filter first and then map

f :: [Int] -> [Int]
f xs = map (* 2) (filter even xs)

main = print (f [1, 2, 3, 4, 5, 6, 7, 8, 9]) -- [4,8,12,16]
-- main = print (f [-2, -10, 3, -5, 6, 9]) -- [-4,-20,12]
-- main = print (f [-10, -2, 0, 2, 4, 6, 8, 10]) -- [-20,-4,0,4,8,12,16,20]