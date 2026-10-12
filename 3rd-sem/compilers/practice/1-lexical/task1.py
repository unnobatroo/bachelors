# =========================================================
# 1. IDENTIFIER VALIDATOR (Checks ELTE Canvas Lexical_2 rule)
# =========================================================
def is_valid_id(name):
    prefix = ""
    digits = ""

    # Separate letters/underscores from digits
    for char in name:
        if char.isalpha() or char == "_":
            prefix += char
        elif char.isdigit():
            digits += char

    if not prefix:
        return False  # Must have a prefix

    if not digits:
        return True  # Pure text ID is valid

    first_digit = int(digits[0])
    digits_after = len(digits) - 1

    # Even start -> Even count after | Odd start -> Odd count after
    if first_digit % 2 == 0:
        return digits_after % 2 == 0
    else:
        return digits_after % 2 != 0


# =========================================================
# 2. LEXER (Updated for WHILE language operator :=)
# =========================================================
def lex(code):
    tokens = []
    i = 0
    length = len(code)

    while i < length:
        char = code[i]

        # Skip whitespace
        if char.isspace():
            i += 1

        # WHILE Assignment Operator: ':='
        elif code[i : i + 2] == ":=":
            tokens.append(("T_ASSIGN", ":="))
            i += 2

        # Variable Identifiers
        elif char.isalpha() or char == "_":
            name = ""
            while i < length and (code[i].isalnum() or code[i] == "_"):
                name += code[i]
                i += 1

            if is_valid_id(name):
                tokens.append(("T_ID", name))
            else:
                print(f"LEXICAL ERROR: Invalid identifier '{name}'")
                return None

        # Numbers
        elif char.isdigit():
            num = ""
            while i < length and code[i].isdigit():
                num += code[i]
                i += 1
            tokens.append(("T_NUM", int(num)))

        # Operators and Semicolon (+, ;)
        elif char in "+;":
            tokens.append((f"T_{char}", char))
            i += 1

        else:
            i += 1  # Skip unknown characters

    return tokens


# =========================================================
# 3. PARSER (Grammar Rule: T_ID T_ASSIGN T_NUM T_ADD T_NUM T_SEMICOLON)
# =========================================================
def parse(tokens):
    # Match rule: ID := NUM + NUM ;
    var_name = tokens.pop(0)[1]  # Get variable ID
    tokens.pop(0)  # Consume ':='
    left_num = tokens.pop(0)[1]  # Get left number
    tokens.pop(0)  # Consume '+'
    right_num = tokens.pop(0)[1]  # Get right number
    tokens.pop(0)  # Consume ';'

    # Abstract Syntax Tree (AST) node
    return {"Target": var_name, "Value": left_num + right_num}


# =========================================================
# 4. EXECUTOR (Evaluates and stores variable in Memory)
# =========================================================
# Symbol Table / Variable Storage
memory = {}


def run(code):
    print(f"\nWHILE Code: '{code}'")
    tokens = lex(code)

    if tokens:
        print("Tokens:    ", tokens)
        ast = parse(tokens)

        # Store in symbol table memory
        memory[ast["Target"]] = ast["Value"]
        print(f"Memory:     {ast['Target']} := {ast['Value']}")


# =========================================================
# SAMPLE INPUTS (WHILE Language Syntax)
# =========================================================

# --- VALID INPUTS ---
run("var_0 := 10 + 20;")  # Valid: '0' (Even start, 0 after)
run("var_012 := 5 + 5;")  # Valid: '012' (Even start, 2 after)
run("x12 := 100 + 50;")  # Valid: '12' (Odd start, 1 after)
run("res := 1 + 2;")  # Valid: no digits in ID

print("\n--- FINAL SYMBOL TABLE ---")
print(memory)

# --- INVALID INPUTS (Lexical Errors) ---
run("var_01 := 10 + 20;")  # Invalid: '01' (Even start, 1 after)
run("x1 := 10 + 20;")  # Invalid: '1' (Odd start, 0 after)
