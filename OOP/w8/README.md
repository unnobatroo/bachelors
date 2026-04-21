# Design patterns

### Singleton

Used when a class should have at most one instance. In this project, it is used for tokens that don't hold unique state, like operators and parentheses, to reduce the memory footprint.

- [polish/notation/README.md](polish/notation/README.md)

### Strategy

Defines a family of algorithms, encapsulates each one, and makes them interchangeable. The document applies this to tree traversals where different actions (like printing, summing, or searching) are implemented as "strategies" passed to a traversal method.

- [bintree/README.md](bintree/README.md)

### Composite

Intended for organising objects into tree structures to represent part-whole hierarchies. It allows clients to treat individual objects (leaves) and compositions of objects (subtrees) uniformly.

- [filesystem/README.md](filesystem/README.md)

### Other Example

- [hospital/README.md](hospital/README.md)

This package uses inheritance and linked objects, but it does not map cleanly to one of the three patterns above.

## Classwork

[Hospital package](hospital/) and also [08_design_demo.pdf](08_design_demo.pdf)