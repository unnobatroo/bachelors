# Design patterns

Design patterns so far - these from w8, and here are today's patterns:

### So far

- [Singleton](../w8/polish/notation/README.md)
- [Strategy](../w8/bintree/strategy/README.md)
- [Composite](../w8/filesystem/README.md)

## Today

### Singleton

Used for objects that should have only one shared instance. In this project, the terrain types and the reusable base-area helpers are singletons.

- [creatures/Grass.java](creatures/Grass.java)
- [creatures/Sand.java](creatures/Sand.java)
- [creatures/Swamp.java](creatures/Swamp.java)
- [solids/CircleArea.java](solids/CircleArea.java)
- [solids/SquareArea.java](solids/SquareArea.java)
- [solids/TriangularArea.java](solids/TriangularArea.java)

### Factory method

The solid reader creates the correct concrete shape from input data instead of exposing that logic to the caller.

- [solids/Solid.java](solids/Solid.java)

### Template method

The race loop is fixed in the base class, while each creature decides how to handle one terrain step.

- [creatures/Creature.java](creatures/Creature.java)
- [creatures/Greenie.java](creatures/Greenie.java)
- [creatures/Beetle.java](creatures/Beetle.java)
- [creatures/Splasher.java](creatures/Splasher.java)

### Double dispatch / Visitor style

Creatures and terrain cooperate through overloaded `change(...)` methods, so the effect depends on both runtime types.

- [creatures/ITerrain.java](creatures/ITerrain.java)
- [creatures/Grass.java](creatures/Grass.java)
- [creatures/Sand.java](creatures/Sand.java)
- [creatures/Swamp.java](creatures/Swamp.java)

### Inheritance hierarchy

The solid shapes are organised as a base class with specialised families for regular, prismatic, and pyramidal solids.

- [solids/Solid.java](solids/Solid.java)
- [solids/Regular.java](solids/Regular.java)
- [solids/Prismatic.java](solids/Prismatic.java)
- [solids/Pyramidal.java](solids/Pyramidal.java)
