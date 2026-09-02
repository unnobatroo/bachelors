------------------

-- Classes and Instances

------------------


-- Show class

------------------

-- Show is called a type class
-- converting values to readable strings
-- Customized intances of Show class
-- show :: Show a => a -> String
-- this means that show is a function from a to String
-- for any type a, provided that a has a Show instance


data Days = Mon | Tue | Wed | Thu | Fri | Sat | Sun

instance Show Days where
  show :: Days -> String
  show Mon = "M"
  show Tue = "T"
  show Wed = "W"
  show Thu = "T"
  show Fri = "f"
  show Sat = "S"
  show Sun = "s"

--main = print (Mon, Sun) -- (M,s)


data Exp = Num Int | Add Exp Exp | Mul Exp Exp

instance Show Exp where
  show (Num n) = show n
  show (Add e1 e2) = "(" ++ show e1 ++ "+" ++ show e2 ++ ")"
  show (Mul e1 e2) = "(" ++ show e1 ++ "*" ++ show e2 ++ ")"

exp1, exp2 :: Exp
exp1 = Add (Mul (Num 2) (Num 2)) (Num 3)
exp2 = Mul (Num 2) (Add (Num 2) (Num 3))

--main = print (exp1, exp2) -- (((2*2)+3),(2*(2+3)))

-- in ghci, do:
-- :i Show


data Multiple = Multiple Int Int Int -- deriving Show -- error duplicate instance

-- writing a custom printing function
printMultiple :: Multiple -> String
printMultiple (Multiple a b c) = show a ++ "*" ++ show b ++ "*" ++ show c

m1, m2 :: Multiple
m1 = Multiple 1 2 3
m2 = Multiple 4 5 6
--main = print $ (printMultiple m1,printMultiple m2)  -- ("1*2*3","4*5*6")


-- write Show instance for the custom types
-- show :: a -> String

instance Show Multiple where
  show x@(Multiple a b c) = printMultiple x

--main = print $ m1 -- 1*2*3
--main = print $ show m1 ++ " " ++ show m2 -- "1*2*3 4*5*6"



-- Q type for rational values and its instance operations
-- rational numbers: numerator and denominator

data Q = Q { num :: Int , denom :: Int } -- deriving Show

-- simplify makes the numerator and denominator relative primes and
-- the denominator positive 
-- there is only one such representation of a rational number

simplify (Q { num , denom }) =
  Q { num = sgn * abs num `div` n , denom = abs denom `div` n }
  where
    n = gcd (abs num) (abs denom)
    sgn | num>0 && denom>0 || num<0 && denom<0 = 1
        | num<0 && denom>0 || num>0 && denom<0 = -1
        | num == 0 && denom /= 0               = 0
        | denom == 0                           = error "denominator cannot be 0"

-- to make printing work, we need to write "deriving Show" above, or
-- define our own Show instance

instance Show Q where
  show :: Q -> String
  show r = show (num r) ++ "/" ++ show (denom r)

--main = print $ Q 4 6 -- 4/6
--main = print $ simplify $ Q 4 6 -- 2/3
--main = print $ Q (-1) 2 -- -1/2
--main = print $ Q 2 3 -- 2/3
--main = print $ Q 2 8 -- 2/8


------------------


-- Num typeclass

------------------

-- ghci> :i Num

instance Num Q where
  (+) :: Q -> Q -> Q
  Q a b + Q c d = simplify $ Q (a*d + c*b) (b*d)
  (*) :: Q -> Q -> Q
  Q a b * Q c d = simplify $ Q (a*c) (b*d)
  abs :: Q -> Q
  abs (Q a b)   = Q (abs a) (abs b)
  fromInteger :: Integer -> Q
  fromInteger a = Q (fromInteger a) 1
  negate :: Q -> Q
  negate (Q a b) = simplify $ Q (-a) b
  signum :: Q -> Q
  signum         = flip Q 1 . signum . num . simplify
  
--main = print $ Q 1 3 + Q 4 3 -- 5/3
--main = print $ Q 5 3 * Q 2 3 -- 10/9
--main = print $ abs $ Q 1 3 -- 1/3
--main = print $ abs $ Q 1 (-3) -- 1/3
--main = print (fromInteger 3 :: Q) -- 3/1
--main = print $ negate (Q 1 2) -- -1/2
--main = print $ Q 1 3 - Q 2 3 -- -1/3
--main = print $ signum $ Q 1 3 -- 1/1
--main = print $ signum $ Q (-1) 3 -- -1/1
--main = print $ signum $ Q 0 3 -- 0/1


------------------


-- Eq typeclass

------------------

-- ghci> :i Eq

-- it is enough to compare the components of the simplified numbers directly
-- or cross multiplication

instance Eq Q where
  (==) :: Q -> Q -> Bool
  u == v = a == c && b == d
    where
      Q a b = simplify u
      Q c d = simplify v

--main = print $ Q 1 3 == Q 3 9 -- True
--main = print $ Q 1 3 == Q 2 3 -- False
--main = print $ Q 0 3 == Q 0 2 -- True


------------------


-- Ord typeclass

------------------

-- ghci> :i Ord
-- ghci> :t compare
-- ghci> :i Ordering

-- because d and c are positive, when a/b > c/d is equivalent to a*d > c*b

instance Ord Q where
  compare :: Q -> Q -> Ordering
  compare u v = compare (a * d) (c * b)
    where
      Q a b = simplify u
      Q c d = simplify v

--main = print $ Q 2 100 > Q 1 100 -- True
--main = print $ Q 2 100 > Q 1 50 -- False
--main = print $ Q 2 100 == Q 1 50 -- True


------------------


-- More examples:

-- Num for Bool

-- we treat False as 0, True as 1, and addition leaves only the
-- remainder (also called modulo 2 addition)

instance Num Bool where
  False + b = b
  True  + b = not b
  False * b = False
  True  * b = b
  abs    b = b
  signum b = b
  negate b = b
  fromInteger = odd

--main = print (1 :: Bool) -- True
--main = print (2 :: Bool) -- False
--main = print (3 :: Bool) -- True
--main = print (4 :: Bool) -- False
--main = print (1 + 2 :: Bool) -- True


------------------


-- instances for trees

data Tree a = Node a (Tree a) (Tree a) | Leaf deriving Show

data BTree a = BNode a (BTree a) (BTree a) | BLeaf

-- this is a conditional instance, it relies on Show a
-- if the base type a has Show instance then it can build 
-- show instance for (BTree a)

instance Show a => Show (BTree a) where
  show BLeaf = "L"
  show (BNode a t1 t2) = "(N " ++ show a ++ " " ++ show t1 ++ " " ++ show t2 ++ ")"

--main = print $ BNode 1 (BNode 2 BLeaf BLeaf) BLeaf -- (N 1 (N 2 L L) L)
--main = print $ BNode 1 BLeaf (BNode 2 BLeaf BLeaf) -- (N 1 L (N 2 L L))
--main = print (BLeaf :: BTree Int) -- L


instance Eq a => Eq (Tree a) where
  Leaf         == Leaf            = True
  Node a t1 t2 == Node a1 t3 t4 = a == a1 && t1 == t3 && t2 == t4
  _            == _               = False

{-
 1                    
/ \ 
L   2 
    / \
    L  L

   2
  / \
 1   L
/\
L L
-}


--main = print $ Node 1 (Node 2 Leaf Leaf) Leaf == Node 1 (Node 2 Leaf Leaf) Leaf -- True
--main = print $ Node 1 (Node 2 Leaf Leaf) Leaf == Node 1 (Node 3 Leaf Leaf) Leaf -- False
--main = print $ (Node 1 Leaf (Node 2 Leaf Leaf) == Leaf )-- False
--main = print $ (Leaf :: Tree Int) == (Leaf :: Tree Int) -- True


------------------


-- Functor typeclass

------------------

-- ghci> :i Functor
-- ghci> :t fmap

-- map :: (a -> b) -> [a] -> [b]

mapTree :: (a -> b) -> Tree a -> Tree b
mapTree f Leaf = Leaf
mapTree f (Node a t1 t2) = Node (f a) (mapTree f t1) (mapTree f t2)

-- main = print $ mapTree (+1) (Node 1 (Node 2 Leaf Leaf) Leaf)
-- Node 2 (Node 3 Leaf Leaf) Leaf

-- Functor t means that we can define a map-like function for the t type

instance Functor Tree where
  fmap = mapTree

--main = print $ fmap (+1) (Node 1 (Node 2 Leaf Leaf) Leaf)
-- Node 2 (Node 3 Leaf Leaf) Leaf


------------------
