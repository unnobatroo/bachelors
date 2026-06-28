{- Progress Task -}

{- Your neptun code : VIHVFL -}

{-
    Write a function which takes a String and a list of Integers.
    If the string is "head" return the list containg only its head,
    if the string is "tail" return the list without its head,
    otherwise return the original list.
    The list contains at least one element.
-}

f :: String -> [Int] -> [Int]
f command list =
    if command == "head" then take 1 list
    else if command == "tail" then drop 1 list
    else list

{- Test Cases -}
-- main = print (f "head" [1,2,3,4,5]) -- [1]
-- main = print (f "tail" [1,2,3,4,5]) -- [2,3,4,5]
-- main = print (f "apple" [1,2,3,4,5]) -- [1,2,3,4,5]
-- main = print (f "head" [42]) -- [42]
-- main = print (f "tail" [42]) -- []