# File system

This package models a file system tree.

- [UML](design.puml)

### Design pattern: `Composite`

Intended for organising objects into tree structures to represent part-whole hierarchies. It allows clients to treat individual objects (leaves) and compositions of objects (subtrees) uniformly.

## Overview

- Component: an abstract class or interface shared by all objects in the tree.
- Leaf: represents individual elements with no children.
- Composite: contains children and delegates behavior to them.

### Key components

- `Registration` is the base type.
- `File` is a leaf with a fixed size.
- `Folder` stores other `Registration` objects and calculates its size from them.
- `FileSystem` is the demo entry point.

## Notes

Files and folders share the same base type, so the code can treat them the same way.