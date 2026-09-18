# Regular Expressions - Practice Sheet

# 1. Integral number

> **Exercise:** Create a regular expression for an integer containing **at least one digit** from `0` to `9`.

### Regex

```regex id="8hf4ji"
[0-9]+
```

**Meaning:** one or more digits.

### Should match

```text id="0qzdr8"
0
7
42
00045
123456
```

### Should NOT match

```text id="tu64kh"
(empty string)
abc
12a
+4
4.2
```

# 2. Integer without leading zeros

> **Exercise:** Create an integer that **cannot start with leading zeros**, except for `0` itself.

### Regex

```regex id="t5qm0k"
0|[1-9][0-9]*
```

**Idea:** there are **two cases**:

- exactly `0`
- a digit from `1-9`, followed by zero or more digits

### Should match

```text id="yrk9xh"
0
1
7
10
42
905
```

### Should NOT match

```text id="16hf30"
00
01
007
042
abc
```

# 3. Signed integer

> **Exercise:** Create an integer that can **optionally** begin with `+` or `-`.

### Regex

```regex id="t61qhv"
[+-]?[0-9]+
```

**Idea:** `[+-]?` means the sign is **optional**, while `[0-9]+` means at least one digit is required.

### Should match

```text id="8tkmfy"
0
42
+42
-42
+0
-999
```

### Should NOT match

```text id="ts7nr8"
+
-
++42
+-42
42+
4.2
```

# 4. Fractional number

> **Exercise:** Create a fractional number containing a **decimal point** and at least one digit before it.

### Regex

```regex id="749wij"
[0-9]+\.[0-9]+
```

**Important:** `.` is special in regex, so `\.` means an **actual decimal point**.

### Should match

```text id="few6if"
0.0
1.5
42.123
0004.20
```

### Should NOT match

```text id="xbhz0n"
.5
42
42.
abc
4,2
```

# 5. Identifier

> **Exercise:** The identifier must start with a **lowercase letter**. Afterwards it may contain lowercase letters, uppercase letters, digits, or `_`.

### Regex

```regex id="93d2gw"
[a-z][A-Za-z0-9_]*
```

**Structure:**

`[a-z]` = required first character
`[A-Za-z0-9_]*` = zero or more allowed characters afterwards

### Should match

```text id="wzccsn"
a
hello
hello42
hello_World
a_B2_test
```

### Should NOT match

```text id="7mcnrt"
Hello
42hello
_hello
ABC
```

# 6. Identifier - version 2

> **Exercise:** Same rules as before, but the **last character cannot be `_`**.

### Regex

```regex id="u7o5vn"
[a-z]([A-Za-z0-9_]*[A-Za-z0-9])?
```

The final character, if there is one after the first letter, must belong to:

```regex id="4dzqij"
[A-Za-z0-9]
```

So `_` cannot be last.

### Should match

```text id="79aud4"
a
abc
a_b
hello42
hello_World2
```

### Should NOT match

```text id="z5gucr"
a_
hello_
_test
Hello
```

# 7. Identifier - version 3

> **Exercise:** Two `_` characters **cannot be next to each other**. `a_a_a` is valid, while `a__a` is not.

### Regex

```regex id="28ekur"
[a-z]([A-Za-z0-9]|_[A-Za-z0-9])*_?
```

The important idea is:

> Whenever `_` occurs inside the identifier, require it to be followed by a **non-underscore character**.

### Should match

```text id="zk34pq"
a
abc
a_b
a_b_c
hello_World42
a_
```

### Should NOT match

```text id="n73n4v"
a__b
a___b
__abc
a_b__c
```

# 8. Complex identifier

> **Exercise:** The first part contains only **letters and `_`**. The second part contains only **digits**.

### Regex

```regex id="369pww"
[A-Za-z_]+[0-9]+
```

Think of it as:

**first part** + **second part**

```text id="2027nb"
letters/_   digits
```

### Should match

```text id="o0kof2"
abc123
hello42
ABC_123
_test_999
abc_DEF42
```

### Should NOT match

```text id="6srbnn"
123abc
abc
123
abc1def2
```

# 9. Complex identifier - version 2

> **Exercise:** If the first part **starts and ends with `_`**, it may contain only **uppercase letters**. Otherwise, it may contain only **lowercase letters**. Afterwards comes the numeric part.

The main idea is to split this into **two separate cases**:

```text id="x36a4k"
SPECIAL_CASE | NORMAL_CASE
```

### Special case examples

```text id="q3ybfv"
_ABC_123
_HELLO_42
```

### Normal case examples

```text id="gmehzq"
abc123
hello42
```

### Should NOT match

```text id="mxzpbz"
_abc_123
_HEllo_42
ABC123
```

> We should derive this one together rather than memorize the finished expression.

# 10. Complex identifier - version 3

> **Exercise:** If the numeric part starts with an **even digit**, its total number of digits must be **even**. If it starts with an **odd digit**, its total number of digits must be **odd**.

The restriction is on the **number of digits**, not on whether every digit is even or odd.

### Useful building blocks

**Even first digit:**

```regex id="9qb3y4"
[02468]
```

**Odd first digit:**

```regex id="i8i8b7"
[13579]
```

**Exactly two arbitrary digits:**

```regex id="zqzkru"
[0-9][0-9]
```

### Regex

```regex id="jxns2o"
[02468][0-9]([0-9][0-9])*|[13579]([0-9][0-9])*
```

The key trick is:

> Adding digits in **pairs** preserves whether the total length is odd or even.

# 11. Time in `hh:mm`

> **Exercise:** Recognize valid times where `hh` is `00..23` and `mm` is `00..59`.

### Regex

```regex id="t7v2oi"
([01][0-9]|2[0-3]):[0-5][0-9]
```

Think about the hour as **two cases**:

- `[01][0-9]` gives `00..19`
- `2[0-3]` gives `20..23`

Minutes are simpler:

```regex id="lph4vm"
[0-5][0-9]
```

which gives `00..59`.

### Should match

```text id="8c8grc"
00:00
01:05
09:30
12:59
19:20
20:00
23:59
```

### Should NOT match

```text id="fo1413"
24:00
25:10
12:60
99:99
1:30
12:5
```

# 12. Line comment

> **Exercise:** Recognize a comment starting with `//` and ending at the **end of the line**.

### Regex

```regex id="2c9ay9"
//[^\n]*
```

The important part is:

```regex id="qnn95z"
[^\n]
```

which means:

> any character **except newline**

Then `*` means we may have **zero or more** such characters.

### Should match

```text id="m2czy3"
//
// hello
//hello world
//x = 42;
```

# 13. Multiline comment - version 1

> **Exercise:** The comment starts with `/*`, ends with `*/`, and inside it **only letters** are allowed.

### Regex

```regex id="d9yluk"
/\*[A-Za-z]*\*/
```

Think of it as three pieces:

**opening** + **content** + **closing**

### Should match

```text id="g9bdtg"
/**/
/*hello*/
/*HelloWorld*/
```

### Should NOT match

```text id="kwc3ny"
/*hello world*/
/*abc123*/
/*abc_def*/
```

# 14. Multiline comment - version 2

> **Exercise:** Anything may appear inside the comment **except `*`**.

### Regex

```regex id="w8lhts"
/\*[^*]*\*/
```

The new idea is:

```regex id="5s0htf"
[^*]
```

which means:

> **any character except `*`**

### Should match

```text id="a836zh"
/**/
/*hello*/
/*hello world*/
/*123 + abc*/
```

### Should NOT match

```text id="luqhe1"
/*hello*world*/
```

# 15. Multiline comment - version 3

> **Exercise:** Even `*` may occur inside the comment, but the comment must stop at the **first `*/`**.

For example:

```text id="wxq74h"
/* hello * there ** test */
```

should be accepted.

But:

```text id="ot97pf"
/* first */ something /* second */
```

must recognize the first comment as ending immediately after:

```text id="qi2bym"
/* first */
```

The important observation is:

> `*` itself is **not forbidden**.
> The special sequence `*/` is what terminates the comment.

This is the hardest exercise on the sheet, so we should derive it slowly.

# Mental checklist

When solving a regex exercise, ask:

**1. What must the first character look like?**

**2. What may come afterwards?**

**3. How many times may it occur?**

- `*` = **zero or more**
- `+` = **one or more**
- `?` = **optional**

**4. Are there multiple cases?**

Use:

```regex id="khj9jm"
A|B
```

**5. Is something forbidden?**

Think about:

```regex id="wjlevk"
[^...]
```

**6. Does odd/even length matter?**

Try building characters in **pairs**.

**7. Are there opening and closing symbols?**

Split it into:

```text id="8gocxq"
OPENING + BODY + CLOSING
```

Most importantly:

> **Do not invent the entire regex at once.**

Go from:

**English requirement → small rules → regex pieces → combine them**
