import Data.Time.Clock.TAI (taiClock)
main :: IO()

-- 1 ----------------------------------------------------------------------
-- Given a list of Ints, remove the element at the given position.

remElemAt :: Int -> [Int] -> [Int]

main = print (remElemAt 6 [1..7]) -- [1,2,3,4,5,6]
-- main = print (remElemAt 2 [1..7]) -- [1,2,4,5,6,7]
-- main = print (remElemAt 9 [1..7]) -- [1,2,3,4,5,6,7]