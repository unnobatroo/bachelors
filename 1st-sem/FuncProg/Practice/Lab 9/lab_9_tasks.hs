module ExTree where

import Data.List (sort)

main :: IO ()
-- Definition of a binary tree
data Tree a = Node a (Tree a) (Tree a)
            | Leaf
            deriving (Show)
-- Binary tree: https://www.geeksforgeeks.org/binary-tree-data-structure/

tree1 :: Tree Int
tree1 = Node 7 Leaf Leaf
-- Tree1 see link: http://graphonline.ru/en/?graph=RDODcKkbEjpzIbIh

tree2 :: Tree Int
tree2 = Node 0 
            (Node 1 
                (Node 3 Leaf Leaf) 
                (Node 4 Leaf Leaf))
            (Node 2 
                (Node 5 Leaf Leaf) 
                (Node 6 Leaf Leaf))

-- Tree2 see link: http://graphonline.ru/en/?graph=apYgfCbqYeaQRHNL

tree3 :: Tree Int
tree3 = Node 0 
            (Node 1 
                (Node 3 Leaf (Node 8 Leaf Leaf)) 
                Leaf)
            (Node 2 Leaf Leaf)
-- Tree3 see link: http://graphonline.ru/en/?graph=YMMkGtZycajcoXEU

-- ! Count the number of nodes in a tree (non node)
sizeT :: Tree a -> Int
sizeT Leaf = 0
sizeT (Node x le ri) = 1 + sizeT le + sizeT ri
-- sizeT (Node _ le ri) = 1 + sizeT le + sizeT ri

-- Node x le ri 
-- x : a (value/key), le : Tree a (left subtree), ri : Tree a (right subtree)

{-
=1 + sizeT (Node 1 (Node 3 Leaf Leaf) (Node 4 Leaf Leaf)) 
   + sizeT (Node 2 (Node 5 Leaf Leaf) (Node 6 Leaf Leaf))
=1 + 1 + sizeT (Node 3 Leaf Leaf) + sizeT (Node 4 Leaf Leaf)
   + 1 + sizeT (Node 5 Leaf Leaf) + sizeT (Node 6 Leaf Leaf)
=1 + 1 + (1 + sizeT Leaf + sizeT Leaf) + (1 + sizeT Leaf + sizeT Leaf)
   + 1 + (1 + sizeT Leaf + sizeT Leaf) + (1 + sizeT Leaf + sizeT Leaf)
=1 + 1 + (1+0+0) + (1+0+0)
   + 1 + (1+0+0) + (1+0+0)
=7
-}
-- main = print $ sizeT tree1 -- 1
-- main = print $ sizeT tree2 -- 7
-- main = print $ sizeT tree3 -- 5

-- ! Calculate the depth of a tree
depthT :: Tree a -> Int
depthT Leaf = 0
depthT (Node _ le ri) = 1 + max (depthT le) (depthT ri) 

-- main = do 
--        print $ depthT tree1 -- 1
--        print $ depthT tree2 -- 3
--        print $ depthT tree3 -- 4


-- ! Given a tree with key of type Int, find the sum of its nodes (leaf is 0)
sumT :: Tree Int -> Int
sumT Leaf = 0
sumT (Node x left right) = x + sumT left + sumT right

-- main = do 
--        print $ sumT tree1 -- 7
--        print $ sumT tree2 -- 21
--        print $ sumT tree3 -- 14

treea :: Tree Int
treea = Node 2 
            (Node 1 Leaf Leaf) 
            (Node 3 Leaf Leaf)


treeb :: Tree Int
treeb = Node 4 
            (Node 2
                (Node 1 Leaf Leaf) 
                (Node 3 Leaf Leaf)) 
            Leaf


-- a. Inorder traversal: Left, Root, Right
inorder :: Tree a -> [a]
inorder Leaf = []
inorder (Node x left right) = inorder left ++ [x] ++ inorder right

-- main = print $ inorder treea -- [1,2,3] -- inorder: []++[1]++[]++[2]++[]++[3]++[] = [1,2,3]
-- main = print $ inorder treeb -- [1,2,3,4] -- inorder: []++[1]++[]++[2]++[]++[3]++[]++[4]++[] = [1,2,3,4]

-- b. Preorder traversal: Root, Left, Right
preorder :: Tree a -> [a]
preorder Leaf = []
preorder (Node x left right) = [x] ++ preorder left ++ preorder right

-- main = print $ preorder treea -- [2,1,3] -- preorder: [2]++[1]++[]++[]++[3]++[]++[] = [2,1,3]
-- main = print $ preorder treeb -- [4,2,1,3] 

-- c. Postorder traversal: Left, Right, Root
postorder :: Tree a -> [a]
postorder Leaf = []
postorder (Node x left right) = postorder left ++ postorder right ++ [x]
-- main = print $ postorder treea -- [1,3,2] -- postorder: []++[]++[1]++[]++[]++[3]++[2] = [1,3,2]
-- main = print $ postorder treeb -- [4,2,1,3] 

-- The difference between the three is where the Root is placed
-- main = do 
--        print $ inorder treea -- [1,2,3] 
--        print $ preorder treea -- [2,1,3]
--        print $ postorder treea -- [1,3,2]

-- main = do 
--        print $ inorder treeb -- [1,2,3,4] 
--        print $ preorder treeb -- [4,2,1,3]
--        print $ postorder treeb -- [1,3,2,4]

-- Exercise
-- 1. Check if every element from the list is in the tree
-- task1 :: Tree Int -> [Int] -> Bool

-- main = do
--     print $ task1 tree2 [1..4] --True
--     print $ task1 tree2 [1..10] --False
--     print $ task1 tree3 [1..10] --False

-- 2. Count occurrences of a given number in the tree
-- Example tree with five 3's
tree3fiveTimes :: Tree Int
tree3fiveTimes = Node 3 (Node 3 Leaf (Node 3 Leaf (Node 2 Leaf Leaf))) 
                       (Node 3 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf))

-- Two implementations
--task2 :: Tree Int -> Int -> Int

--task21 :: Tree Int -> Int -> Int

-- main = do
--     print $ task2 tree3fiveTimes 3 -- 5
--     print $ task2 tree2 (-10) -- 0

-- 6. Create list of tuples (node, left child, right child) for odd numbers

extractN :: Tree Int -> Int
extractN Leaf = -1
extractN (Node x _ _) = x

--task3 :: Tree Int -> [(Int, Int, Int)]

-- main = do
--     print $ task3 tree3 --[(1,3,-1),(3,-1,8)]
--     print $ task3 tree2 --[(1,3,4),(3,-1,-1),(5,-1,-1)]

-- 7. Search for a value in the tree
searchT :: Tree Int -> Int -> Bool
searchT Leaf _ = False
searchT (Node x left right) target
    | x == target = True
    | otherwise   = searchT left target || searchT right target

-- main = do
--     print $ searchT tree2 10 --False
--     print $ searchT tree2 1 --True

-- 8. Given a tree and an integer. Find all the nodes that are equal to the 
--    integer and give the sum of their direct children. (Leaf count as 0).
exNode :: Tree Int -> Int
exNode Leaf = 0
exNode (Node x _ _) = x -- It's similar to extractN, but leaf is counted as 0.

--f8 :: Tree Int -> Int -> Int

-- main =  print $ f8 (Node 2 Leaf Leaf) 3  -- 0
-- main =  print $ f8 (Node 3 (Node 1 Leaf Leaf) (Node 1 Leaf Leaf)) 3  -- 2
-- main =  print $ f8 (Node 1 (Node 0 Leaf Leaf) (Node 2 Leaf Leaf)) 1  -- 2
-- main =  print $ f8 (Node 2 (Node 1 Leaf Leaf) (Node 2 (Node 3 Leaf Leaf) (Node 1 Leaf Leaf))) 2 -- 7
-- main =  print $ f8 (Node 2 (Node 1 Leaf Leaf) (Node 2 Leaf (Node 1 Leaf Leaf))) 2 -- 4

-- 9. Replace nodes equal to n with 0
--replace :: Int -> Tree Int -> Tree Int

treec :: Tree Int
treec = Node 4 (Node 3 (Node 1 Leaf Leaf) (Node 3 Leaf Leaf)) 
              (Node 6 (Node 3 Leaf Leaf) (Node 7 Leaf Leaf))

-- main =  print $ replace 3 treec 

-- 10. Add "_over18" to names of persons over 18
data Person = Person {
    name :: String,
    birthday :: (Int, Int, Int)
} deriving (Show)

-- Sample trees for testing
t1 :: Tree Person
t1 = Node (Person "hh" (2001,11,22)) Leaf Leaf

t2 :: Tree Person
t2 = Node (Person "hh" (2007,11,22)) 
         (Node (Person "hr" (2001,11,21)) Leaf Leaf)
         (Node (Person "ht" (2001,11,23)) Leaf Leaf)

t3 :: Tree Person
t3 = Node (Person "hh" (1999,11,22))
         (Node (Person "hr" (2007,11,21))
              (Node (Person "hh" (2008,11,22)) Leaf Leaf)
              (Node (Person "hh" (1998,11,22)) Leaf Leaf))
         (Node (Person "ht" (2009,11,23)) Leaf Leaf)

-- main = print t1 
-- main = print t2
-- main = print t3

over18 :: (Int, Int, Int) -> Bool
over18 (year, _, _)
    | year >= 2006 = False
    | otherwise = True

addString :: Person -> Person
addString p = p { name = name p ++ "_over18" }

--updateName :: Tree Person -> Tree Person

-- main = print $ updateName t1
-- main = print $ updateName t2
-- main = print $ updateName t3

-- 11. Binary Search Tree check
-- This definition is the same as the binary tree
data BST a = BSTNode a (BST a) (BST a) 
           | BSTLeaf
           deriving (Show)

-- https://www.geeksforgeeks.org/binary-search-tree-data-structure/
--isBST :: BST Int -> Bool

-- Inorder
treeToList :: BST a -> [a]
treeToList BSTLeaf = []
treeToList (BSTNode x left right) = treeToList left ++ [x] ++ treeToList right

-- Sample BSTs for testing
bst1 :: BST Int
bst1 = BSTNode 1 BSTLeaf 
       (BSTNode 20 
           (BSTNode 3 
               (BSTNode 3 BSTLeaf BSTLeaf)
               (BSTNode 4 BSTLeaf 
                   (BSTNode 12 
                       (BSTNode 5 BSTLeaf BSTLeaf)
                       BSTLeaf)))
           (BSTNode 45 
               (BSTNode 34 
                   (BSTNode 22 BSTLeaf BSTLeaf)
                   BSTLeaf)
               (BSTNode 112 
                   (BSTNode 53 BSTLeaf BSTLeaf)
                   BSTLeaf)))

bst2 :: BST Int
bst2 = BSTNode 1 BSTLeaf 
       (BSTNode 20 
           (BSTNode 7 BSTLeaf 
               (BSTNode 12 
                   (BSTNode 12 
                       (BSTNode 9 BSTLeaf BSTLeaf)
                       BSTLeaf)
                   BSTLeaf))
           BSTLeaf)

bst3 :: BST Int
bst3 = BSTNode 1 BSTLeaf 
       (BSTNode 20 
           (BSTNode 3 
               (BSTNode 9 BSTLeaf BSTLeaf)
               (BSTNode 4 BSTLeaf 
                   (BSTNode 1 
                       (BSTNode 8 BSTLeaf BSTLeaf)
                       BSTLeaf)))
           (BSTNode 45 
               (BSTNode 34 
                   (BSTNode 22 BSTLeaf BSTLeaf)
                   BSTLeaf)
               (BSTNode 112 
                   (BSTNode 53 BSTLeaf BSTLeaf)
                   BSTLeaf)))

bst4 :: BST Int
bst4 = BSTNode 1 BSTLeaf 
       (BSTNode 2 
           (BSTNode 7 BSTLeaf 
               (BSTNode 12 
                   (BSTNode 12 
                       (BSTNode 8 BSTLeaf BSTLeaf)
                       BSTLeaf)
                   BSTLeaf))
           BSTLeaf)

-- Test BST checking
testBST :: [Bool]
testBST = map isBST [bst1, bst2, bst3, bst4, BSTLeaf]  -- [True,True,False,False,True]

-- main = print $ testBST