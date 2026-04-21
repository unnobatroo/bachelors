# Hospital model

This package models a small hospital workflow.

- [UML](design.puml)

### Design pattern: `Other example`

This package uses inheritance and linked objects, but it does not map cleanly to one of the three patterns above.

## Overview

- `Employee` is the base class.
- `Doctor`, `Nurse`, and `Staff` extend `Employee`.
- `Patient` stores its doctor and nurses.
- `Hospital` stores employees and patients.

### Key components

- Inheritance for shared employee state.
- Polymorphism for doctor and nurse behavior.
- Bidirectional links between `Doctor`/`Nurse` and `Patient`.

## Notes

`Patient.discharge()` assumes a doctor has already been assigned.