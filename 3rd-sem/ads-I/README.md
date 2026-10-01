# ADS I

Synthesized from ELTE course materials (Tibor Ásványi, Kinga Pusztai, Gábor Pusztai, István Fekete), Niklaus Wirth's _Algorithms + Data Structures = Programs_, and CLRS. Optimized for GitHub Markdown.

## Module 1: Mathematical Foundations & Complexity Analysis

### 1. Core Definitions & Structogram Conventions

- **Algorithm Definition**: A well-defined computational procedure that takes input and produces output in a finite amount of time.
- **Optimization Goals**: Algorithm design primarily targets the minimization of time complexity (waiting time) and memory usage (such as cloud space costs).
- **Structogram Parameter Rules (ELTE Notation)**:
  - Global variables exist for the entire execution of the program and cannot be redefined locally.
  - Subprogram headers must follow the exact format: `Subprogram_Name ([formal parameter list]):[ReturnType]`.
  - **Value Passing**: `x : T` (input only).
  - **Reference Passing**: `&x : T` or `VAR x : T` (modified in place).
- **Stream Operations**: The `read(&x:𝒯)` function reads the next value; if successful, it returns `true` and stores the value in `x`, but if the end of input is reached, it returns `false` and `x` is undefined. `write(x:𝒯)` writes directly to the output stream.

### 2. Time & Space Complexity Framework

- **The Big-O Worst-Case ($O(g)$)**: If $f \in O(g)$, then $g$ represents the worst-case scenario; $f(n)$ will never grow faster than $g(n)$ multiplied by some constant $d$.
- **The Three Complexity Scenarios**:
  - **Best-Case ($mT(n)$)**: Minimum operations required across all inputs of size $n$.
  - **Average-Case ($AT(n)$)**: Expected value of operations assuming uniform probability.
  - **Worst-Case ($MT(n)$)**: Maximum guaranteed upper bound. $mT(n) \le AT(n) \le MT(n)$.
- **Lower Bound ($\Omega(g(n))$)**: $f(n) \in \Omega(g(n)) \iff \exists c > 0, n_0 : \forall n \ge n_0, c \cdot g(n) \le f(n)$.
- **Tight Bound ($\Theta(g(n))$)**: $f(n) \in \Theta(g(n)) \iff f(n) \in O(g(n)) \cap \Omega(g(n))$.

## Module 2: Memory & Linear Data Structures

### 1. Abstract Data Types (ADT) & Memory

- **ADT Definition**: An Abstract Data Type is a mathematical or informal structure combined with its valid operations. No single data structure works perfectly for all purposes, making it essential to understand their individual strengths and limitations.
- **ADT Implementation**: Divided into two distinct layers:
  1. **Representation**: Defines the underlying data structures and members.
  2. **Implementation**: Defines the concrete algorithms that execute the methods.
- **Files / Sequences**: Represented as homogeneous structures $s = \langle s_0, s_1, \dots, s_{n-1} \rangle$ where all elements share the same base type.
- **Array Packing & Utilization**: The utilization factor of packed arrays is calculated as $u = n \cdot s / \lceil n \cdot s \rceil$. While packing components into a single word saves space, it can counteract the gain by requiring inefficient partial word access in the compiled code.

### 2. Stacks & Queues

- **Reverse Polish Notation (RPN)**: A postfix representation of arithmetic expressions (e.g., `4 5 +` instead of `4 + 5`) that relies heavily on Stack algorithms for evaluation.
- **Palindrome Validation (The `Mirrored` Algorithm)**: Solved using a Stack and a boolean flag (e.g., `afterHash`). The algorithm pushes characters until a `#` separator is read, after which each incoming character must perfectly match the `pop()` result from the Stack to verify the mirror.

### 3. Linked List Architectures

- **Sentinel Tradeoffs**: Adding dummy header or trailer nodes reduces algorithmic edge-case complexity, but slightly increases memory usage.
- **Singly Linked Lists (S1L / H1L)**: S1L points directly to the first node, while H1L uses a dummy header. H1L structures are specifically utilized in algorithms like _Insertion sort of H1Ls_.
- **Circular Doubly Linked (C2L)**: Rings utilized heavily for sequence operations like `append`.

## Module 3: Comparison-Based Sorting Algorithms

> **Interactive Resource:** [VisuAlgo's Sorting Sandbox](https://visualgo.net/en/sorting)

### 1. Elementary Sorts ($\Theta(n^2)$)

- **Why learn simple sorts?** Straight algorithms (like Bubble and Insertion Sort) are easier to understand, simpler to implement into code, and can actually be faster than advanced algorithms when $n$ is very small.
- **Selection Sort**: Unstable. $C(n) \in \Theta(n^2)$ in all cases.
- **Bubble Sort**: Stable. Improved variants track the boundary index of the last swap performed.
- **Insertion Sort**: Stable. Efficient for nearly sorted arrays ($mT(n) \in \Theta(n)$).

### 2. Divide-and-Conquer & Hybrid Sorts

- **Merge Sort (`mergeSort`)**: Stable. The standard structogram logic `mergeSort(A : T[n])` initializes an auxiliary array $B[0 \dots n)$ by copying $A$, then recursively calls `ms(B, A)` to sort the data non-decreasingly back into $A$.
- **File Merging Algorithms**: For massive datasets, algorithms like `BalancedMerge` and `NaturalMerge` are used to distribute and combine sequences across file riders via `copyrun` and `mergerun` subroutines.
- **Timsort (The Modern Standard)**:
  - Developed by Tim Peters in 2002, Timsort is the default sorting engine in Python (`list.sort()`), Java (`Arrays.sort()`), Android, and the V8 JavaScript engine.
  - It solves the "Sorting Trilemma" by achieving high average-case speed, strict stability, and $O(n)$ adaptability to partially sorted data.
  - **Mechanisms**: It synergizes Insertion Sort (for small _Minruns_) and Merge Sort. It utilizes a **Run Stack** to enforce balanced merges, strictly maintaining $O(n \log n)$ upper bounds. It also employs **Galloping Mode** to skip repetitive 1-by-1 comparisons when one run holds a long streak of smaller elements.

## Module 4: Trees & Binary Search Trees (BST)

> **Interactive Resource:** [USFCA BST Visualizer](https://www.cs.usfca.edu/~galles/visualization/BST.html)

### 1. Tree Terminology & Architectures

- **Root**: The unique topmost node and starting point for traversals.
- **Internal Node**: A node with at least one child, facilitating branching.
- **Leaf**: A terminal node representing an endpoint with absolutely no children.
- **Subtree**: A specific node and all its descendants.
- **Sibling**: Nodes that share the exact same parent and reside at the same depth.
- **Node Implementations**: A basic `Node<T>` contains `Key`, `Left`, and `Right` pointers. Advanced variants like `Node3<T>` also maintain an explicit `Parent` pointer.
- **Parenthesised Textual Form**: Binary trees can be mapped to text as `( LeftSubtree Root RightSubtree )`. To make nested structures readable, bracket types rotate by level: $l \bmod 4 = 0 \implies \{ \}$, $1 \implies [ ]$, $2 \implies ( )$, $3 \implies \langle \rangle$.

### 2. Traversals & State Tracking

- **Preorder Traversal**: Evaluates Root $\to$ Left $\to$ Right. Global state can be tracked across independent branches during recursion by passing variables (like `level` and `max`) as reference parameters, updating `max` as the algorithm drills deeper into the tree.
- **Inorder Traversal**: Evaluates Left $\to$ Root $\to$ Right. Prints a BST in strictly sorted ascending order.
- **Advanced Tree Variants**: The ELTE curriculum extends into Multiway B-Trees and Priority Search Trees for specialized database indexing.

## Module 5: Hashing & Key Transformations

> **Interactive Resource:** [VisuAlgo Hash Table Sandbox](https://visualgo.net/en/hashtable)

- **Key Transformations**: Described deeply in Niklaus Wirth's literature, hashing requires the careful mathematical choice of a hash function alongside a robust collision handling mechanism.
- **Collision Resolution Strategies**:
  - _Separate Chaining_: Table slots store pointers to linked lists.
  - _Linear Probing_: Suffers from Primary Clustering.
  - _Double Hashing_: The optimal open addressing method where probe step size is determined by a secondary hash function.

### Essential External References & Textbooks

1. **"Introduction to Algorithms" (CLRS)**: The global standard algorithm bible from which ELTE derives its $\Theta(n)$ build-heap mathematical proofs.
2. **"Algorithms + Data Structures = Programs" by Niklaus Wirth**: The classic text underlying the course's approach to invariants, structured sequences, file merging, and packed array representations.
3. **GeeksForGeeks & Wikipedia**: Excellent for rapid syntax review.
4. **CS50 Shorts (Harvard)**: Highly recommended for visualizing S1L and H1L pointer logic.

_For an excellent visual breakdown of the Heap Sort algorithm taught in this module, see this video:_
[Abdul Bari's Heap Sort Explanation](https://www.youtube.com/watch?v=0Jae4ApidS4)
This lecture breaks down the exact binary max-heap construction and array sinking (`heapify`) logic required to understand the ELTE priority queue structograms.
