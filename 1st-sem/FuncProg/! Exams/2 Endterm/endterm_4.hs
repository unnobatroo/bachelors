{-# LANGUAGE ParallelListComp #-}

-- main :: IO ()

data Team = Team {teamName :: String, teamID :: Int}

data Player = Player {playerID :: Int, playerName :: String, playerTeam :: Team, playerScores :: [Int]}

team1 = Team {teamName = "Team A", teamID = 1}

team2 = Team {teamName = "Team B", teamID = 2}

team3 = Team {teamName = "Team C", teamID = 3}

team0 = Team {teamName = "Team C", teamID = 3}

player1 = Player {playerID = 1, playerName = "Alice", playerTeam = team1, playerScores = [10, 20, 30]}

player2 = Player {playerID = 2, playerName = "Bob", playerTeam = team1, playerScores = [15, 25, 35]}

player3 = Player {playerID = 3, playerName = "Charlie", playerTeam = team2, playerScores = [12, 22, 32]}

player4 = Player {playerID = 4, playerName = "David", playerTeam = team2, playerScores = [18, 28, 38]}

player5 = Player {playerID = 5, playerName = "Eve", playerTeam = team3, playerScores = [14, 24, 34]}

player6 = Player {playerID = 6, playerName = "Frank", playerTeam = team3, playerScores = [16, 26, 36]}

player7 = Player {playerID = 7, playerName = "John", playerTeam = team2, playerScores = [12, 22, 32]}

player8 = Player {playerID = 8, playerName = "Peter", playerTeam = team2, playerScores = [10, 5, 5]}

-- 1. 10p *******************************************

-- You are given a number n.
-- Use a parallel generator to pair up the first n matching
-- Uppercase and Lowercase letters.
-- Parallel list comprehension must be used.
-- Non-parallel solutions are also accepted but then max is 8 points.
-- Example:
--    gen1 3 = [('A','a'),('B','b'),('C','c')]

gen1 :: Int -> [(Char, Char)]
gen1 lim = take lim [(upper, lower) | upper <- ['A'..'Z'] | lower <- ['a'..'z']]
-- gen1 lim = take lim (zip ['A'..'Z'] ['a'..'z']) -- bez dolboyobskih comprehensions

-- gen1 0 -- []
-- gen1 3 -- [('A','a'),('B','b'),('C','c')]
-- gen1 8 -- [('A','a'),('B','b'),('C','c'),('D','d'),('E','e'),('F','f'),('G','g'),('H','h')]

-- 2. 10p *******************************************

-- Write a function which takes a list of points (x, y) tuple
-- and a list of motion vector (dx, dy) tuple,
-- and shift all the points by the corresponding motion vector.
-- Parallel list comprehension must be used.
-- Non-parallel solutions are also accepted but then max is 8 points.

-- shiftPoints :: [(Int, Int)] -> [(Int, Int)] -> [(Int, Int)]

-- main = print $ shiftPoints [(1, 2), (3, 4), (5, 6)] [(1, 1), (2, 2), (3, 3)]
-- Expected output: [(2, 3), (5, 6), (8, 9)]
-- main = print $ shiftPoints [(1, 2), (3, 4), (5, 6)] [(-1, -1), (-2, -2), (-3, -3)]
-- Expected output: [(0, 1), (1, 2), (2, 3)]
-- main = print $ shiftPoints [(1, 2), (3, 4), (5, 6)] [(0, 0), (0, 0), (0, 0)]
-- Expected output: [(1, 2), (3, 4), (5, 6)]
-- main = print $ shiftPoints [(1, 2), (3, 4), (5, 6)] [(1, -1), (-2, 2), (3, -3)]
-- Expected output: [(2, 1), (1, 6), (8, 3)]
-- main = print $ shiftPoints [(1, 2), (3, 4)] [(1, 1)]
-- Expected output: [(2, 3)]

-- 3. 20p *******************************************

-- Giving a list of players, find the team with the top total score.
-- Return the id of the top team.

-- topTeam :: [Player] -> Int

-- main = print $ topTeam [player1, player2, player3, player4, player5, player6] -- 3
-- main = print $ topTeam [player1, player2, player3, player4] -- 2
-- main = print $ topTeam [player1] -- 1
-- main = print $ topTeam [] -- "No Team!"

-- 4. 10p *******************************************

-- Given a binary tree of Player records, write a function to extract
-- all players who belong to a specific Team.
-- Return a list of tuples that contain the player name and ID.

data Tree a = Node a (Tree a) (Tree a) | Leaf
  deriving (Show)

tree :: Tree Player
tree =
  Node
    player1
    ( Node
        player2
        Leaf
        (Node player3 Leaf Leaf)
    )
    ( Node
        player4
        (Node player5 Leaf Leaf)
        (Node player6 Leaf Leaf)
    )

-- playersFromTeam :: Team -> Tree Player -> [(Int, String)]

-- main = print (playersFromTeam team1 tree) -- [(1,"Alice"),(2,"Bob")]
-- main = print (playersFromTeam team2 tree) -- [(3,"Charlie"),(4,"David")]
-- main = print (playersFromTeam team3 tree)  -- [(5,"Eve"),(6,"Frank")]
-- main = print (playersFromTeam team0 tree) -- [(5,"Eve"),(6,"Frank")]

-- 5. 10p *******************************************

-- We would like to keep the height of trees under 3.
-- Write a function, which takes a tree of integers, and
-- trim out part the branches that grows beyond the depth of 3.
-- (Leaf does not count as branch).
-- e.g. Before trim
--        j
--       / \
--      b   l
--     /   / \
--    d   e   c
--       / \
--      m   f
--     / \
--    o   g
--   /
--  n
-- After trim
--        j
--       / \
--      b   l
--     /   / \
--    d   e   c

-- trim :: Tree Integer -> Tree Integer

-- main = print $ trim (Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 (Node 7 Leaf Leaf) Leaf))(Node 3(Node 6(Node 8(Node 9 Leaf Leaf) Leaf) Leaf) Leaf))
--     -- Expected output: Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 Leaf Leaf)) (Node 3 (Node 6 Leaf Leaf) Leaf)
-- main = print $ trim (Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 Leaf Leaf)) (Node 3 (Node 6 Leaf Leaf)  Leaf))
--     -- Expected output: Node 1 (Node 2 (Node 4 Leaf Leaf) (Node 5 Leaf Leaf)) (Node 3 (Node 6 Leaf Leaf) Leaf)
-- main = print $ trim (Node 1 (Node 2 Leaf Leaf) (Node 3 Leaf Leaf))
--     -- Expected output: Node 1 (Node 2 Leaf Leaf) (Node 3 Leaf Leaf)
-- main = print $ trim (Node 1 Leaf Leaf)
--     -- Expected output: Node 1 Leaf Leaf
-- main = print $ trim Leaf
-- Expected output: Leaf

-- 6. 10p *******************************************

-- You are given a tree of char. You want to check if the tree is a palindrome.
-- In order to check it, you need to convert the tree to a string traversing inorder
-- and check if the string is a palindrome. Leaf is an empty string.
-- For example, the below tree is a palindrome when reading inorder forward
-- results in the String: "madam" and it is the same as reading backward: "madam".
--
--        d
--      /   \
--     m     a
--    / \   / \
--    L  a  L  m
--      / \   / \
--      L  L  L  L

-- isPaliTree :: Tree Char -> Bool

-- main = print $ isPaliTree (Node 'd' (Node 'm' Leaf (Node 'a' Leaf Leaf)) (Node 'a' Leaf (Node 'm' Leaf Leaf))) -- True
-- main = print $ isPaliTree (Node 'a' (Node 'a' (Node 'b' Leaf Leaf) (Node 'n' Leaf Leaf)) (Node 'n' Leaf (Node 'a' Leaf Leaf))) -- False
-- main = print $ isPaliTree (Node 'e' (Node 'a' (Node 'r' Leaf Leaf) (Node 'c' Leaf Leaf)) (Node 'a'  (Node 'c' Leaf Leaf) (Node 'r' Leaf Leaf))) -- True
-- main = print $ isPaliTree (Node 'c' (Node 'b' (Node 'a' Leaf Leaf) Leaf) (Node 'd' (Node 'c' Leaf Leaf) (Node 'a' Leaf Leaf))) -- False

-- 7. 10p *******************************************

-- Define a binary tree BTree type of BNode and BLeaf
-- and create a custom Show instance that outputs
-- the tree in a specific format. The format is as follows:
-- The output for each leaf node should be Leaf: value.
-- The output for each node should be Node: (left subtree) -> value -> (right subtree).
-- You should format the tree such that:
-- Leaf nodes display only their value, prefixed with Leaf:.
-- Node values should display the left and right subtree recursively,
-- using the format described above.

-- writing BTree

-- instancing show

-- t1, t2 :: BTree Int
-- t1 = BNode (BNode (BLeaf 1) 2 (BLeaf 3)) 4 (BLeaf 5)
-- t2 = BNode (BNode (BLeaf 6) 20 (BLeaf 5)) 10 (BNode (BLeaf 1) 8 (BLeaf 2))

-- main = print t1
-- Node: (Node: (Leaf: 1) -> 2 -> (Leaf: 3)) -> 4 -> (Leaf: 5)
-- main = print (BNode (BLeaf 1) 10 (BLeaf 2))
-- Node: (Leaf: 1) -> 10 -> (Leaf: 2)
-- main = print t2
-- Node: (Node: (Leaf: 6) -> 20 -> (Leaf: 5)) -> 10 -> (Node: (Leaf: 1) -> 8 -> (Leaf: 2))
-- main = print (BLeaf 3) -- Leaf: 3

-- 8. 10p *******************************************

-- Implement a custom Show instance for the Player record,
-- where it prints the player in the format of:
-- I am <playerName> from <playerTeam>, my average score is <average of scores> which is <x>.
-- x = "bad" if average < 20, otherwise "quite good".

-- instancing show for players

instance Show Player where
  show :: Player -> String
  show p =
    "I am "
      ++ playerName p
      ++ " from "
      ++ teamName (playerTeam p)
      ++ ". My average score is "
      ++ show avg
      ++ ", which is "
      ++ opinion
    where
      avg = fromIntegral (sum (playerScores p)) / fromIntegral (length (playerScores p))
      opinion
        | avg < 20 = "bad."
        | otherwise = "quite good."

-- print player1
-- I am Alice from Team A. My average score is 20.0, which is quite good

-- print [player1, player2, player3]
-- [I am Alice from Team A. My average score is 20.0, which is quite good.,I am Bob from Team A. My average score is 25.0, which is quite good.,I am Charlie from Team B. My average score is 22.0, which is quite good.]

-- print [player7, player8]
-- [I am John from Team B. My average score is 22.0, which is quite good.,I am Peter from Team B. My average score is 6.666666666666667, which is bad.]

-- 9. 10p *******************************************

-- This question has two parts, 5p each.
-- Part 1 - Write an instance Eq to determine whether two players are equal.
-- Two players are equal if they have the same total score, and they are
-- in the same team.
-- Part 2 - Write an instance Eq to determine whether two teams are equal.
-- Two teams are equal if they have the same id and name.

-- Part 1: Eq instancing for players

instance Eq Player where
  (==) :: Player -> Player -> Bool
  p1 == p2 =
    teamID (playerTeam p1) == teamID (playerTeam p2)
      && sum (playerScores p1) == sum (playerScores p2)

-- player1 == player2    -- False
-- player3 == player7    -- True

-- Part 2: Eq instance for Student

-- team3 == team0    -- True
-- team1 == team2    -- False