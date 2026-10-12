# ADS I

Synthesized from ELTE course materials (Tibor Ásványi, Kinga Pusztai, Gábor Pusztai, István Fekete), Niklaus Wirth's _Algorithms + Data Structures = Programs_, and CLRS. Optimized for GitHub Markdown.

## Module 1: Mathematical Foundations & Complexity Analysis

### 1. Core Definitions & Structogram Conventions

- **Algorithm Definition**: A well-defined computational procedure that takes input and produces output in a finite amount of time[cite: 4].
- **Optimization Goals**: Algorithm design primarily targets the minimization of time complexity (waiting time) and memory usage (such as cloud space costs)[cite: 4].
- **Structogram Parameter Rules (ELTE Notation)**:
  - Global variables exist for the entire execution of the program and cannot be redefined locally[cite: 7].
  - Subprogram headers must follow the exact format: `Subprogram_Name ([formal parameter list]):[ReturnType]`[cite: 7].
  - **Value Passing**: `x : T` (input only, value copied)[cite: 7].
  - **Reference Passing**: `&x : T` or `VAR x : T` (modified in place)[cite: 7].
- **Stream Operations**: The `read(&x:𝒯)` function reads the next value; if successful, it returns `true` and stores the value in `x`, but if the end of input is reached, it returns `false` and `x` is undefined[cite: 7]. `write(x:𝒯)` writes directly to the output stream[cite: 7].
- **Visual Tooling**: [Structorizer Guide & Nassi–Shneiderman Editor](https://structorizer.fischersunds.de/) aligns with the structured flowchart and boxed diagram conventions used in the ELTE curriculum.

### 2. Time & Space Complexity Framework

- **The Big-O Worst-Case ($O(g)$)**: If $f \in O(g)$, then $g$ represents the worst-case scenario; $f(n)$ will never grow faster than $g(n)$ multiplied by some constant $d$[cite: 4].
- **The Three Complexity Scenarios**:
  - **Best-Case ($mT(n)$)**: Minimum operations required across all inputs of size $n$[cite: 4].
  - **Average-Case ($AT(n)$)**: Expected value of operations assuming uniform probability[cite: 4].
  - **Worst-Case ($MT(n)$)**: Maximum guaranteed upper bound, where $mT(n) \le AT(n) \le MT(n)$[cite: 4].
- **Lower Bound ($\Omega(g(n))$)**: $f(n) \in \Omega(g(n)) \iff \exists c > 0, n_0 : \forall n \ge n_0, c \cdot g(n) \le f(n)$[cite: 4].
- **Tight Bound ($\Theta(g(n))$)**: $f(n) \in \Theta(g(n)) \iff f(n) \in O(g(n)) \cap \Omega(g(n))$[cite: 4].

> **Video Lecture:**
>
> - [Abdul Bari: Asymptotic Notations Big Oh, Omega, Theta](https://www.youtube.com/watch?v=A03oI0znAoc) – Step-by-step graphical and mathematical definitions of asymptotic bounds.

## Module 2: Memory & Linear Data Structures

### 1. Abstract Data Types (ADT) & Memory

- **ADT Definition**: An Abstract Data Type is a mathematical or informal structure combined with its valid operations[cite: 4]. No single data structure works perfectly for all purposes, making it essential to understand their individual strengths and limitations[cite: 4].
- **ADT Implementation**: Divided into two distinct layers[cite: 4]:
  1. **Representation**: Defines the underlying data structures and members[cite: 4].
  2. **Implementation**: Defines the concrete algorithms that execute the methods[cite: 4].
- **Files / Sequences**: Represented as homogeneous structures $s = \langle s_0, s_1, \dots, s_{n-1} \rangle$ where all elements share the same base type[cite: 1, 4].
- **Array Packing & Utilization**: The utilization factor of packed arrays is calculated as $u = n \cdot s / \lceil n \cdot s \rceil$[cite: 4]. While packing components into a single word saves space, it can counteract the gain by requiring inefficient partial word access in the compiled code[cite: 4].

### 2. Stacks & Queues

- **Reverse Polish Notation (RPN)**: A postfix representation of arithmetic expressions (e.g., `4 5 +` instead of `4 + 5`) that relies on Stack operations for evaluation[cite: 4, 7].
  - [Computerphile: Reverse Polish Notation and The Stack](https://www.youtube.com/watch?v=7ha78yWRDlE) – Demonstration of operator precedence handling and postfix execution.
- **Palindrome Validation (The `Mirrored` Algorithm)**: Solved using a Stack and a state tracking flag (such as `afterHash` or `mode`)[cite: 4, 5]. Pushes incoming characters until a `#` delimiter is encountered, then verifies each succeeding character against `pop()` operations[cite: 5].

### 3. Linked List Architectures

- **Sentinel Tradeoffs**: Adding dummy header or trailer nodes eliminates special edge cases (e.g., insertion into an empty list), at the cost of slight additional memory[cite: 4, 9].
- **Singly Linked Lists (S1L / H1L)**: S1L points directly to the first data node, while H1L incorporates a dummy header[cite: 4, 9]. H1L structures are specifically utilized in algorithms like _Insertion sort of H1Ls_[cite: 4].
- **Circular Doubly Linked Lists (C2L)**: Head-sentinel rings optimized for constant-time tail appends and bidirectional node splicing[cite: 4, 9].

> **Recommended Resources:**
>
> - **Interactive Sandbox:** [VisuAlgo Linked List Simulator](https://visualgo.net/en/list)
> - **Video (S1L):** [CS50 Shorts: Singly-Linked Lists](https://www.youtube.com/watch?v=zQI3FyWm144) – Pointer operations and edge-case handling.
> - **Video (C2L / DLL):** [CS50 Shorts: Doubly-Linked Lists](https://www.youtube.com/watch?v=FHMPswJDCvU) – Bidirectional pointer manipulation.

## Module 3: Comparison-Based Sorting Algorithms

> **Interactive Resource:** [VisuAlgo's Sorting Sandbox](https://visualgo.net/en/sorting)

### 1. Elementary Sorts ($\Theta(n^2)$)

- **Why learn simple sorts?** Straight algorithms (like Bubble and Insertion Sort) are easier to implement, carry minimal overhead, and often outperform divide-and-conquer algorithms when $n$ is very small[cite: 4].
- **Selection Sort**: Unstable[cite: 4]. Total comparisons $C(n) \in \Theta(n^2)$ across all input cases[cite: 4].
  - [CS50 Shorts: Selection Sort](https://www.youtube.com/watch?v=3hH8kTHFw2A)
- **Bubble Sort**: Stable[cite: 4]. Optimized implementations monitor swap boundary indices to terminate early on sorted inputs ($mT(n) \in \Theta(n)$)[cite: 4].
  - [CS50 Shorts: Bubble Sort](https://www.youtube.com/watch?v=RT-hUXUWQ2I)
- **Insertion Sort**: Stable[cite: 4]. Highly efficient for nearly sorted arrays ($mT(n) \in \Theta(n)$)[cite: 4].
  - [CS50 Shorts: Insertion Sort](https://www.youtube.com/watch?v=O0VbBkUvriI)

### 2. Divide-and-Conquer & Hybrid Sorts

- **Merge Sort (`mergeSort`)**: Stable[cite: 4]. The standard structogram logic `mergeSort(A : T[n])` allocates an auxiliary array $B[0 \dots n)$ by copying $A$, then recursively calls `ms(B, A)` to sort the elements back into $A$[cite: 4].
  - [Abdul Bari: Merge Sort Algorithm](https://www.youtube.com/watch?v=mB5HXBb_HY8) – In-depth trace of recursive splitting and two-way array merges.
- **File Merging Algorithms**: For out-of-core sorting where input exceeds main memory, algorithms like `BalancedMerge` and `NaturalMerge` partition data runs across file riders via `copyrun` and `mergerun` subroutines[cite: 1, 4].
- **Timsort (The Modern Standard)**:
  - Developed by Tim Peters in 2002, Timsort serves as the default sorting engine in Python (`list.sort()`), Java (`Arrays.sort()`), Android, and the Google V8 JavaScript engine[cite: 4].
  - Achieves optimal real-world efficiency through adaptive $O(n)$ best-case behavior on presorted data while guaranteeing $O(n \log n)$ worst-case execution[cite: 4].
  - **Mechanisms**: Blends Insertion Sort (for small _Minruns_) with Merge Sort[cite: 4]. Maintains a **Run Stack** to enforce balanced merge invariants and switches into **Galloping Mode** to skip redundant element-by-element comparisons when one run dominates another[cite: 4, 6].
  - [Geekific: TimSort Explained and Implemented](https://www.youtube.com/watch?v=GhP5WbE4GYo) – Detailed explanation of run identification, stack invariants, and galloping.
  - [CPython `listsort.txt` Specification](https://github.com/python/cpython/blob/main/Objects/listsort.txt) – Tim Peters' original algorithm notes in the Python source repository.

## Module 4: Trees & Binary Search Trees (BST)

> **Interactive Resource:** [USFCA BST Visualizer](https://www.cs.usfca.edu/~galles/visualization/BST.html)

### 1. Tree Terminology & Architectures

- **Root**: The unique topmost node and entry point for all traversals[cite: 3, 4].
- **Internal Node**: A node with at least one child that enables sub-branching[cite: 3, 4].
- **Leaf**: A terminal node with no children[cite: 3, 4].
- **Subtree**: A specific node coupled with all its descendants[cite: 3, 4].
- **Sibling**: Child nodes that share the identical parent node[cite: 3, 4].
- **Node Implementations**: Standard `Node<T>` records store `Key`, `Left`, and `Right` pointers; advanced structures like `Node3<T>` include an explicit `Parent` pointer[cite: 3, 4].
- **Parenthesised Textual Form**: Trees map to string representations formatted as `( LeftSubtree Root RightSubtree )`[cite: 3, 4]. Bracket formatting rotates by level depth: $l \bmod 4 = 0 \implies \{\ \}$, $1 \implies [ \ ]$, $2 \implies ( \ )$, $3 \implies \langle \ \rangle$[cite: 3, 4].

### 2. Traversals & BST Operations

- **Preorder Traversal**: Root $\to$ Left $\to$ Right.
- **Inorder Traversal**: Left $\to$ Root $\to$ Right; produces the keys of a BST in strictly sorted ascending order.
- **Postorder Traversal**: Left $\to$ Right $\to$ Root.
- **Video Guides**:
  - [WilliamFiset: Binary Search Tree Traversals](https://www.youtube.com/watch?v=k7GkEbECZK0) – Visual recursive tracing of tree walks.
  - [WilliamFiset: Binary Search Tree Removal](https://www.youtube.com/watch?v=8K7EO7s_iFE) – Deletion procedures for nodes with 0, 1, or 2 children.

## Module 5: Hashing & Key Transformations

> **Interactive Resource:** [VisuAlgo Hash Table Sandbox](https://visualgo.net/en/hashtable)

- **Key Transformations**: Direct indexing via hash functions paired with deterministic collision resolution policies[cite: 4].
- **Collision Resolution Strategies**:
  - **Separate Chaining**: Hash buckets point to linked list heads[cite: 4].
    - [WilliamFiset: Hash Table Separate Chaining](https://www.youtube.com/watch?v=T9gct6Dx-jo)
  - **Linear Probing**: Open addressing probing sequential cells $h(k, i) = (h'(k) + i) \bmod m$, which is susceptible to primary clustering[cite: 4].
    - [WilliamFiset: Hash Table Open Addressing](https://www.youtube.com/watch?v=xIejolxzZS8)
  - **Double Hashing**: Uses an independent secondary hash function to calculate probing intervals $h(k, i) = (h_1(k) + i \cdot h_2(k)) \bmod m$, preventing clustering[cite: 4].
    - [WilliamFiset: Hash Table Double Hashing](https://www.youtube.com/watch?v=H5e9V5x92vI)

## Essential External References & Textbooks

1. **"Introduction to Algorithms" (CLRS)**: [MIT Press Official Resource](https://mitpress.mit.edu/9780262046305/introduction-to-algorithms/) – Standard reference for the $\Theta(n)$ build-heap mathematical proofs.
2. **"Algorithms + Data Structures = Programs" by Niklaus Wirth**: [Internet Archive Classic Text](https://archive.org/details/algorithmsdatast0000wirt) – Text establishing the invariants, record structures, file merges, and packed array models used throughout the course[cite: 1, 4].
3. **Harvard CS50**: [CS50 Official Channel](https://www.youtube.com/@cs50) – Recommended for visual memory diagrams and dynamic data structure animations.
4. **Abdul Bari Algorithms**: [Abdul Bari YouTube Channel](https://www.youtube.com/@abdul_bari) – Proofs, master theorem derivations, and algorithm trace walk-throughs.

> **Featured Lecture on Heaps & Priority Queues:**
> [Abdul Bari: Heap - Heap Sort - Heapify - Priority Queues](https://www.youtube.com/watch?v=HqPJF2L5h9U)
> Cohesive 51-minute breakdown of binary heap array layouts, the bottom-up `heapify` sinking routine, and priority queue implementation.
