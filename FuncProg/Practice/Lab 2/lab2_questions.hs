import Data.Char (isLower, isUpper, ord, toLower, toUpper)
import Data.Fixed (mod')

main :: IO ()
main = print ("Hello world")


-- Negate a Boolean
b :: Bool -> Bool
b x = not x

-- b True  -- False
-- b False -- True

-- Negate the negation of a Boolean
b2 :: Bool -> Bool
b2 x = undefined

-- main = print (b True)      -- False
-- main = print (b False)     -- True

-- Logical AND (&&)
logicalAnd :: Bool -> Bool -> Bool
logicalAnd x y = x && y

-- main = print (logicalAnd True False)  -- Output: False

-- Logical OR (||)

logicalOr :: Bool -> Bool -> Bool
logicalOr x y = x || y

-- main = print (logicalOr True False) -- True


-- Print Hello world! using 3 strings input.

-- greeting :: String -> String -> String -> String

-- main = print( greeting "hello" " " "world" ) -- "hello world"

---------------------------------------------------------------------------------------
---- Define a function maxi with two arguments that delivers the maximum of the two.

-- maxi :: Int -> Int -> Int

-- main = print( maxi 34 56 ) -- 56

---------------------------------------------------------------------------------------
---- Quadruple a number.

-- quad :: Int -> Int
-- main = print( quad 5 ) -- 20

---------------------------------------------------------------------------------------
---- Compute the sursolid (the fifth power) of a number.

-- sursolid :: Int -> Int

-- main = print( sursolid 2 )  -- 32
-- main = print( sursolid 3  ) -- 243

---------------------------------------------------------------------------------------
---- Check if a number is odd. -- odd or even
---- use rem or `rem`

-- isoddnr :: Int -> Bool

-- main = print(isoddnr 5) -- True
-- main = print(isoddnr 6) -- False

-- isoddnr2 :: Int -> Bool

-- main = print(isoddnr 6)

---------------------------------------------------------------------------------------
---- Check if a number is multiple of 10.

-- ismult10 :: Int -> Bool

-- main = print( ismult10 20 ) -- True
-- main = print( ismult10 201 ) -- False

---------------------------------------------------------------------------------------
---- Write a function which returns true if a is divisible by b.

-- divBy :: Int -> Int -> Bool

-- main = print (divBy 10 2) -- True
-- main = print (divBy 10 3) -- False
-- main = print (divBy 10 0) -- "Division by 0"

-- Difference between mod and rem
-- (-7) mod 3 returns 2 because the result takes the sign of the divisor (3), which is positive.
-- (-7) rem 3 returns -1 because the result takes the sign of the dividend (-7), which is negative.

---------------------------------------------------------------------------------------
---- Write a function which returns true if a is divisible by b or vice versa. Fill in the blanks

-- divAny :: Int -> Int -> Bool
-- divAny a b = a ___ b == 0 ___ b ___ a == 0

-- main = print (divAny 44 11) -- True
-- main = print (divAny 44 110) -- False

---------------------------------------------------------------------------------------

---- Write a function that takes two arguments, say n and x, and computes their power,
-- in 2 versions - with recursion and without recursion.

-- power :: Int -> Int -> Int

-- main = print( power 2 5 ) -- 32

-- powerrec :: Int -> Int -> Int

-- main = print (powerrec 2 0) --  1
-- main = print( powerrec 2 4 ) -- 16

{-
powerrec 2 4
2 * powerrec 2 3
2 * 2 * powerrec 2 2
2 * 2 * 2 * powerrec 2 1
2 * 2 * 2 * 2 * powerrec 2 0
2 * 2 * 2 * 2 * 1
16
-}

---------------------------------------------------------------------------------------

---- Write a function that takes an integer n and returns the result of multiplying
-- n by itself n times (meaning n power n), then decrease n by 1 after
-- until n reaches 1 and sum up the obtained powers.
-- n^n + (n-1)^(n-1) + ... + 1^1 + 0

sumpowers :: Int -> Int
sumpowers x
  | x == 1 = 1
  | otherwise = x ^ x + sumpowers (x - 1)

-- sumpowers 5 = 5^5 + 4^4 + 3^3 + 2^2 + 1^1

-- main = print(sumpowers 5) -- 3413
-- main = print(sumpowers (-34)) -- Negative number
-- main = print(sumpowers 9) -- 405071317

---------------------------------------------------------------------------------------
---- Sum of squares
-- Compute the sum of the squares of numbers from 1 to n.
-- n^2 + (n-1)^2 + ... + 1^2 + 0

squareSum :: Int -> Int
squareSum x
    | x == 0 = 0
    | otherwise = x * x + squareSum(x - 1)

-- main = print(squareSum 5) -- 55
-- main = print(squareSum 0) -- 0
-- main = print(squareSum 100) -- 338350
-- main = print(squareSum (-10)) --  "Negative number"

---------------------------------------------------------------------------------------
---- Given a positive integer, find the sum of the odd numbers up to that number starting from 1.

-- sumOdd :: Int -> Int

-- main = print (sumOdd 5) -- 9
-- main = print (sumOdd 21) -- 121
-- main = print (sumOdd 10)  -- 25 = 9+7+5+3+1
-- main = print (sumOdd (-13)) -- "Negative or zero number"

---------------------------------------------------------------------------------------
---- Compute for a given positive n the sum of 2i*(2i+1), for i from 1 to n. E.g. for n=3 the sum is 68.

-- f :: Int -> Int

-- main = print( f 0 ) -- 0
-- main = print( f 3 ) -- 68    6*7 + 4*5 + 2*3 + 0 = 42 + 20 + 6 + 0 = 68

---------------------------------------------------------------------------------------
---- Write GetLastPositive function
-- Returns the number decreased by the last digit if positive, otherwise returns -1.

-- getLastPositive :: Int -> Int

-- main = print( getLastPositive 5856)   -- 5850
-- main = print( getLastPositive 689255) -- 689250
-- main = print( getLastPositive 0)      -- 0
-- main = print( getLastPositive 8)      -- 0
-- main = print( getLastPositive (-8554)) -- -1

---------------------------------------------------------------------------------------
---- Convert digit to string
-- Convert an integer from 0 to 5 into a word, otherwise return "Not less or equal to 5".

digitToString :: Int -> String
digitToString x
    | x == 0 = "Zero"

-- main = print( digitToString 3) -- "Three"
-- main = print( digitToString 8) -- "Not less or equal to 5"
-- main = print( digitToString (-1)) -- "Not less or equal to 5"

---------------------------------------------------------------------------------------
---- Average of 5 numbers
-- Compute the average of 5 numbers.

av5 :: Int -> Int -> Int -> Int -> Int -> Double
av5 a b c d e = fromIntegral (a + b + c + d + e) / 5

-- main = print( av5 1 2 3 4 5) -- 3.0
-- main = print(av5 3 5 7 9 10) -- 6.8

---------------------------------------------------------------------------------------
---- Odd-even operation
-- Return the product if both numbers are odd, sum if both are even, otherwise return 0.

-- oddEven :: Int -> Int -> Int

-- main = print(oddEven 474 8983) -- 0
-- main = print(oddEven 6 6) -- 12
-- main = print(oddEven 7 7) -- 49

---------------------------------------------------------------------------------------
---- Are numbers sorted?
-- Check if 5 numbers are sorted in increasing order.

-- isSorted :: Int -> Int -> Int -> Int -> Int -> Bool

-- main = print(isSorted 1 1 1 1 1) -- True
-- main = print(isSorted 1 2 3 4 5) -- True
-- main = print(isSorted 4 3 2 1 0) -- False

---------------------------------------------------------------------------------------
---- Transform days into years, weeks, and days.
-- Convert the number of days into a string of years, weeks, and days.
-- use show to transform integer to string

-- transform :: Int -> String

-- main = print(transform 375) -- "1 years 1 weeks 3 days"
-- main = print(transform 365) -- "1 years 0 weeks 0 days"
-- main = print(transform 1050) -- "2 years 45 weeks 5 days"
-- main = print(transform 2500) -- "6 years 44 weeks 2 days"

---------------------------------------------------------------------------------------