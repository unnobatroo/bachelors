**Format of the `params.txt` file:** `SourceBase`, `TargetBase`, `Number1`, `Number2`, ...

```shell
2, 8, 10, 1011          # Example 1: Binary 10 -> Octal 2 (Valid)
16, 10, F, 1A           # Example 2: Hex F -> Decimal 15 (Valid)
2, 8, 3, 1011           # Example 3: Number 3 is NOT in Base 2! (Invalid)
10, 2, 5, 10            # Example 4: Decimal 5 -> Binary 101 (Valid)
```