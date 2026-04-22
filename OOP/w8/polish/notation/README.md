# Polish notation

This package parses arithmetic expressions in infix form, converts them to postfix form, and evaluates the result.

- [UML](PolishNotation.svg)

### Design pattern: `Singleton`

Used when a class should have at most one instance. In this project, it is used for tokens that don't hold unique state, like operators and parentheses, to reduce the memory footprint.

## Overview

- Private constructor: prevents direct instantiation from outside the class.
- Static instance: a class-level member that holds the single instance.
- Static accessor method: `getInstance()` returns the instance, creating it only if it doesn't exist yet.

### Key components

- `OperatorAdd`, `OperatorSub`, `OperatorMul`, `OperatorDiv`, `LeftP`, and `RightP` are singletons.
- `Token.readToken()` creates the right token from the input.
- `Expression` converts infix input to postfix and evaluates it.

## Notes

The code supports integers, `+`, `-`, `*`, `/`, and parentheses.
