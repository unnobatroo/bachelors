main :: IO ()
{- Your neptun code : VIHVFL -}

{-
  Write a function takeUntilZero that takes a list of Int and
  returns all elements from the beginning of the list up to,
  but not including, the first 0.
  If there is no 0, return the whole list.

  Examples:
    [5,4,3,0,9,8]   → [5,4,3]
    [1,2,3]         → [1,2,3]
    [0,1,2,3]       → []
    []              → []
-}
takeUntilZero :: [Int] -> [Int]
takeUntilZero = takeWhile (/= 0)

main = print (takeUntilZero [5, 4, 3, 0, 9, 8]) -- [5,4,3]
-- main = print (takeUntilZero [1,2,3])        -- [1,2,3]
-- main = print (takeUntilZero [0,1,2,3])      -- []
-- main = print (takeUntilZero [])             -- []