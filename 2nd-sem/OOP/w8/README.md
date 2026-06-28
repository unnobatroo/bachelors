# Design patterns

- [Lecture slides](06-patterns-stacks-trees.pdf)

### Singleton

Used when a class should have at most one instance. In this project, it is used for tokens that don't hold unique state, like operators and parentheses, to reduce the memory footprint.

- [polish/notation/README.md](polish/notation/README.md)

### Strategy

Defines a family of algorithms, encapsulates each one, and makes them interchangeable. The document applies this to tree traversals where different actions (like printing, summing, or searching) are implemented as "strategies" passed to a traversal method.

- [bintree/strategy/README.md](bintree/strategy/README.md)

### Composite

Intended for organising objects into tree structures to represent part-whole hierarchies. It allows clients to treat individual objects (leaves) and compositions of objects (subtrees) uniformly.

- [filesystem/README.md](filesystem/README.md)

## Classwork

- [hospital/README.md](hospital/README.md)

This package uses inheritance and linked objects, but it does not map cleanly to one of the three patterns above.

### Other designs

- [08_design_demo.pdf](08_design_demo.pdf)