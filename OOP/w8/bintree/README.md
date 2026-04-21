# Binary tree

This package builds a binary tree and runs different operations on it.

- [UML](design.puml)

### Design pattern: `Strategy`

Defines a family of algorithms, encapsulates each one, and makes them interchangeable. The document applies this to tree traversals where different actions (like printing, summing, or searching) are implemented as "strategies" passed to a traversal method.

## Overview

- Strategy Interface: defines a common method like `execute(Node node)`.
- Concrete strategies: implement the specific algorithm.

### Key components

- `BinTree` owns the tree.
- `Node` stores the recursive structure.
- `IAction` is the strategy interface.
- `Printer`, `Summation`, `MaxSelect`, `LinearSearch`, and `CondMaxSearch` are the concrete actions.

## Notes

The traversal code stays the same, but the action changes through `IAction`.