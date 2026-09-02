{- Progress Task 1 -}

{- My Neptun code: VIHVFL -}

{-
    Create a function squareOrDouble that takes an integer and returns:
        If the given number is even, return the square of the number.h
        If the given number is odd, return double the number.
-}

squareOrDouble :: Int -> Int
squareOrDouble x = if even x then x*x else x*2

-- Test cases:
-- main = print (squareOrDouble 5) -- 10  (Since 5 is odd, return 5 * 2 = 10)
-- main = print (squareOrDouble 8) -- 64  (Since 8 is even, return 8 * 8 = 64)
-- main = print (squareOrDouble 0) -- 0 (Since 0 is even, return 0 * 2 = 0)