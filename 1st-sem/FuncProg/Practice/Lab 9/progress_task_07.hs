-- 1. Define two record types:
-- Team with fields for name (String) and teamID (Int).
-- Player with fields for name (String), score (Int), and team (Team).
-- Hint: Use `deriving (Show)` to make your records printable.

data Team = Team
    { teamName :: String
    , teamID   :: Int
    } deriving (Show)

data Player = Player
    { playerName  :: String
    , playerScore :: Int
    , playerTeam  :: Team
    } deriving (Show)


-- 2. Create actual values for a team and a player with the following details:
-- - Team: Name "Red Warriors", ID 401
-- - Player: Name "Alice", Score 50, Team is Red Warriors.

team1 :: Team
team1 = Team
    { teamName = "Red Warriors"
    , teamID   = 401
    }

player1 :: Player
player1 = Player
    { playerName  = "Alice"
    , playerScore = 50
    , playerTeam  = team1
    }


-- 3. Write a function to increase a player's score by 10 if it is below a specific threshold.

adjustScore :: Player -> Int -> Player
adjustScore p threshold =
    if playerScore p < threshold
        then p { playerScore = playerScore p + 10 }
        else p

-- Example usage to demonstrate both cases:
main :: IO ()
main = do
    putStrLn "--- Test 1: Score below threshold (50 < 60) ---"
    print $ adjustScore player1 60

    putStrLn "\n--- Test 2: Score equal to threshold (50 < 50) ---"
    print $ adjustScore player1 50

    putStrLn "\n--- Test 3: Score above threshold (50 < 40) ---"
    print $ adjustScore player1 40