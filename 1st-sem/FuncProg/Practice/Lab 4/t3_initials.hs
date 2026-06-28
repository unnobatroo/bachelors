import Data.Time.Clock.TAI (taiClock)
main :: IO()

-- 3 ----------------------------------------------------------------------
-- Write a function to convert a list of a person's names into initials (first letter sepparated by a '.').

initials :: [String] -> String
initials [] = ""
initials [name] = [head name]
initials (name:names) = head name : '.' : initials names

-- main = print (initials ["Sam", "Harris"]) -- "S.H."
main = print (initials ["Howard", "Phillips", "Lovecraft"]) -- "H.P.L."