Here is a cheat sheet summarizing the essential Haskell functions and concepts from your examples.

## 1. Character Functions (from `Data.Char`)
| Function | Type (Implicit) | Description | Example | Result |
| :--- | :--- | :--- | :--- | :--- |
| `toUpper` | `Char -> Char` | Converts a character to **uppercase**. | `toUpper 'a'` | `'A'` |
| `toLower` | `Char -> Char` | Converts a character to **lowercase**. | `toLower 'A'` | `'a'` |
| `isUpper` | `Char -> Bool` | Checks if a character is **uppercase**. | `isUpper 'A'` | `True` |
| `isLower` | `Char -> Bool` | Checks if a character is **lowercase**. | `isLower 'a'` | `True` |

## 2. Numeric Functions
| Function | Type (Implicit) | Description | Example | Result |
| :--- | :--- | :--- | :--- | :--- |
| `div` | `Int -> Int -> Int` | **Integer division**. | `div 10 3` | `3` |
| `rem` | `Int -> Int -> Int` | **Remainder** (modulus). | `rem 10 3` | `1` |
| `even` | `Integral a => a -> Bool` | Checks if a number is **even**. | `even 4` | `True` |
| `odd` | `Integral a => a -> Bool` | Checks if a number is **odd**. | `odd 3` | `True` |
| `gcd` | `Integral a => a -> a -> a` | **Greatest Common Divisor**. | `gcd 12 15` | `3` |
| `lcm` | `Integral a => a -> a -> a` | **Least Common Multiple**. | `lcm 4 5` | `20` |
| `abs` | `Num a => a -> a` | **Absolute value**. | `abs (-5)` | `5` |
| `max` | `Ord a => a -> a -> a` | **Maximum** of two numbers. | `max 3 5` | `5` |
| `min` | `Ord a => a -> a -> a` | **Minimum** of two numbers. | `min 3 5` | `3` |
| `fromIntegral` | `Integral a, Num b => a -> b` | Converts an **integral** to a **numeric** type (e.g., `Float`, `Double`). | `fromIntegral 1` | `1.0` |

## 3. String & List Functions
(Note: A **String** is a list of characters: `String` $\equiv$ `[Char]`)

### Basic String/List Operations
| Function/Operator | Description | Example | Result |
| :--- | :--- | :--- | :--- |
| `show` | Converts a value to its **String** representation. | `show 123` | `"123"` |
| `++` | **Concatenates** two strings or lists. | `"Hello, " ++ "World!"` | `"Hello, World!"` |
| `length` | Returns the **length** of a string or list. | `length "Hello"` | `5` |
| `!!` | Returns the element at a **specific index** (0-based). | `[1, 2, 3] !! 1` | `2` |

### List Access & Manipulation
| Function | Description | Example | Result |
| :--- | :--- | :--- | :--- |
| `head` | Returns the **first** element. | `head [1,2,3]` | `1` |
| `tail` | Returns the list **without its first** element. | `tail [1,2,3]` | `[2,3]` |
| `init` | Returns the list **without its last** element. | `init [1,2,3]` | `[1,2]` |
| `last` | Returns the **last** element. | `last [1,2,3]` | `3` |
| `null` | Checks if a list is **empty**. | `null []` | `True` |
| `reverse` | Returns the **reversed** list. | `reverse [1,2,3]` | `[3,2,1]` |
| `take n` | Returns the **first $n$ elements**. | `take 3 [1..5]` | `[1,2,3]` |
| `drop n` | Returns the list **without the first $n$ elements**. | `drop 2 [1..5]` | `[3,4,5]` |
| `maximum` | Returns the **maximum** element. | `maximum [1,5,3]` | `5` |
| `minimum` | Returns the **minimum** element. | `minimum [1,5,3]` | `1` |
| `sum` | Returns the **sum** of all elements. | `sum [1..5]` | `15` |
| `product` | Returns the **product** of all elements. | `product [1..5]` | `120` |
| `concat` | **Flattens** a list of lists. | `concat [[1,2], [3]]` | `[1,2,3]` |
| `elem x` | Checks if element **$x$ is in the list**. | `elem 3 [1..5]` | `True` |
| `replicate n x` | Creates a list with element **$x$ replicated $n$ times**. | `replicate 3 'a'` | `"aaa"` |
| `words` | **Splits** a string into a list of words. | `words "Hello World"` | `["Hello","World"]` |
| `unwords` | **Joins** a list of words into a single string. | `unwords ["H", "W"]` | `"H W"` |

## 4. Higher-Order Functions
| Function | Type (Implicit) | Description | Example | Result |
| :--- | :--- | :--- | :--- | :--- |
| `map` | `(a -> b) -> [a] -> [b]` | Applies a function to **each element** in a list. | `map (+1) [1, 2]` | `[2, 3]` |
| `filter` | `(a -> Bool) -> [a] -> [a]` | **Filters** elements based on a **predicate** (boolean function). | `filter even [1..4]` | `[2, 4]` |
| `foldl` | `(a -> b -> a) -> a -> [b] -> a` | **Reduces** a list from the **left** using an initial value (`a`). | `foldl (+) 0 [1, 2, 3]` | `6` |
| `foldr` | `(a -> b -> b) -> b -> [a] -> b` | **Reduces** a list from the **right** using an initial value (`b`). | `foldr (-) 0 [1, 2, 3]` | `1 - (2 - (3 - 0)) = 2` |
| `takeWhile` | `(a -> Bool) -> [a] -> [a]` | Takes elements **while** the predicate holds (from the start). | `takeWhile (< 5) [1..10]` | `[1, 2, 3, 4]` |
| `dropWhile` | `(a -> Bool) -> [a] -> [a]` | **Drops** elements **while** the predicate holds (from the start). | `dropWhile (< 5) [1..10]` | `[5, 6, 7, 8, 9, 10]` |
| `all` | `(a -> Bool) -> [a] -> Bool` | Checks if **all** elements satisfy the predicate. | `all even [2, 4]` | `True` |
| `any` | `(a -> Bool) -> [a] -> Bool` | Checks if **any** element satisfies the predicate. | `any odd [2, 4, 7]` | `True` |

## 5. Tuple Functions
| Function/Operator | Description | Example | Result |
| :--- | :--- | :--- | :--- |
| `(,)` | Creates a **tuple**. | `(1, 'a')` | `(1, 'a')` |
| `fst` | Returns the **first** element of a 2-tuple. | `fst (1, 'a')` | `1` |
| `snd` | Returns the **second** element of a 2-tuple. | `snd (1, 'a')` | `'a'` |
| `zip` | Combines two lists into a list of **pairs (tuples)**. | `zip [1, 2] ['a', 'b']` | `[(1, 'a'), (2, 'b')]` |
| `unzip` | Separates a list of pairs into **two lists**. | `unzip [(1, 'a'), (2, 'b')]` | `([1, 2], ['a', 'b'])` |
| `zipWith` | Combines two lists using a **binary function**. | `zipWith (+) [1, 2] [4, 5]` | `[5, 7]` |

## 6. List Comprehension

### General Format
$$[ \text{expression} \mid \text{generator}_1, \text{generator}_2, \dots, \text{filter}_1, \dots ]$$

| Concept | Syntax | Description | Example | Result |
| :--- | :--- | :--- | :--- | :--- |
| **Simple** | `[x * 2 | x <- [1..10]]` | Generates a list, applying an expression to each element. | `[x*2 | x<-[1,2]]` | `[2, 4]` |
| **Filtered** | `[x * 2 | x <- [1..10], even x]` | Filters elements (`even x`) before applying the expression. | `[x | x<-[1..4], even x]` | `[2, 4]` |
| **Nested** | `[(x, y) | x <- [1..3], y <- ['a', 'b']]` | Uses multiple generators to create a **cartesian product**. | `[(x,y) | x<-[1,2], y<-['a','b']]` | `[(1,'a'),(1,'b'),(2,'a'),(2,'b')]` |
| **Parallel** | `[x + y | x <- [1..3] \| y <- [4..6]]` | **Requires `{-# LANGUAGE ParallelListComp #-}`**. Combines corresponding elements from generators (like `zipWith`). | `[x+y | x<-[1,2] \| y<-[4,5]]` | `[5, 7]` |