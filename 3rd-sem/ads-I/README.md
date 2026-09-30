# ELTE Algorithms & Data Structures I — Complete Course Reference & Study Guide

---

# Module 1: Mathematical Foundations & Complexity Analysis

### 1. Pseudocode Notation & Syntax Rules
* **Assignment**: `:=` (e.g., `x := y`) vs. **Equality Test**: `=` (e.g., `x = y` returns `true`/`false`).
* **Subprogram Headers**:
  * **Procedure** (no return value): `procedureName(formal_parameters)`
  * **Function** (returns a value): `functionName(formal_parameters) : ReturnType`
* **Parameter Modifiers**:
  * **Value Passing** (default): `x : T` (input only, value copied).
  * **Reference Passing**: `&x : T` or `VAR x : T` (input/output parameter modified in place).
* **Array Indexing**:
  * **1-based indexing**: `A/1 : T[n]` or `A[1..n]` (indices range from `1` to `n`).
  * **0-based indexing**: `Z : T[]` or `Z[0..n-1]` (indices range from `0` to `Z.length - 1`).
* **Subarrays**: `A[u..v]` (closed interval) or `A[u..v)` (half-open interval).

---

### 2. Time & Space Complexity Framework
* **Time Complexity ($T(n)$)**: Counts basic operations, comparison tests $C(n)$, arithmetic additions $\text{Ö}(n)$, multiplications $S(n)$, subroutine calls, and loop condition evaluations as a function of input size $n$.
* **The Three Complexity Scenarios**:
  * **Best-Case ($mT(n)$)**: Minimum number of operations required across all inputs of size $n$.
  * **Average-Case ($AT(n)$)**: Expected value of operations assuming a uniform probability distribution over inputs.
  * **Worst-Case ($MT(n)$)**: Maximum guaranteed upper bound on operations ($mT(n) \le AT(n) \le MT(n)$).
* **Space Complexity ($S(n)$)**: Measures auxiliary memory allocated at runtime plus the maximum call stack depth required for recursion.
* **Asymptotic Growth Bounds**:
  * **$O(g(n))$ (Upper Bound)**: $f(n) \in O(g(n)) \iff \exists c > 0, n_0 : \forall n \ge n_0, 0 \le f(n) \le c \cdot g(n)$.
  * **$\Omega(g(n))$ (Lower Bound)**: $f(n) \in \Omega(g(n)) \iff \exists c > 0, n_0 : \forall n \ge n_0, 0 \le c \cdot g(n) \le f(n)$.
  * **$\Theta(g(n))$ (Tight Bound)**: $f(n) \in \Theta(g(n)) \iff f(n) \in O(g(n)) \cap \Omega(g(n))$.

---

### 3. Core Algorithms & Benchmarks

#### **A. Polynomial Evaluation**
* **Naïve Algorithm (`Polinom1(Z : T[], x : T) : T`)**:
  * *Logic*: Calculates each term $Z[i] \cdot x^i$ independently using an inner loop to compute $x^i$.
  * *Multiplications*: $S(n) = 1 + 2 + \dots + n = \frac{n(n+1)}{2} \in \Theta(n^2)$.
  * *Complexity*: $T(n) \in \Theta(n^2)$, Space $S(n) \in \Theta(1)$.
* **Horner's Scheme (`Horner(Z : T[], x : T) : T`)**:
  * *Logic*: Evaluates the polynomial inside-out by repeatedly factoring out $x$: $P(x) = (\dots((Z[n] \cdot x + Z[n-1])x + Z[n-2])x + \dots) + Z[0]$.
  * *Loop*: `y := Z[n]; for i := n-1 downto 0 do y := y * x + Z[i]`.
  * *Multiplications*: $S(n) = n \in \Theta(n)$.
  * *Complexity*: $T(n) \in \Theta(n)$, Space $S(n) \in \Theta(1)$.

---

# Module 2: Searching Algorithms

### 1. Linear Search (`linearSearch(A/1 : T[n], x : T) : N`)
* **Precondition**: Array $A$ contains $n$ elements (unsorted or sorted).
* **Logic**: Iterates $i$ from $1$ to $n$; returns $i$ if $A[i] = x$, or $0$ if absent.
* **Complexity**: $mT(n) = 1 \in \Theta(1)$, $MT(n) = n+1 \in \Theta(n)$, $AT(n) = \frac{n+1}{2} \in \Theta(n)$.
* **Sentinel Variant (`linearSearchSentinel(&A/1 : T[n+1], x : T) : N`)**:
  * *Logic*: Appends $x$ at index $n+1$ (`A[n+1] := x`). Scans without checking array bounds (`while A[i] \ne x do i := i + 1`). Eliminates boundary test overhead.

### 2. Binary Search (`binarySearch(A/1 : T[n], x : T) : N`)
* **Precondition**: Array $A$ is strictly **sorted** ($A[1] \le A[2] \le \dots \le A[n]$).
* **Logic**: Maintains interval bounds $u := 1, v := n$. While $u \le v$, computes midpoint $m := \lfloor(u+v)/2\rfloor$. If $A[m] = x$, returns $m$. If $x < A[m]$, sets $v := m - 1$; else sets $u := m + 1$.
* **Complexity**: $mT(n) = 2 \in \Theta(1)$, $MT(n) = \lfloor\log_2 n\rfloor + 2 \in \Theta(\log n)$, Space $S(n) \in \Theta(1)$.

---

# Module 3: Linear Data Structures & Memory Mapping

### 1. Abstract Data Types (ADTs)

#### **A. Stack ADT (LIFO - Last-In, First-Out)**
* **Internal Representation**: Array `A : T[]`, element count `n : N`.
* **Operations**:
  * `push(x : T)`: If $n = A.\text{length}$, calls `doubleFullArray(A)`. Sets $A[n] := x$, $n := n + 1$.
  * `pop() : T`: Precondition $n > 0$. Decrements $n := n - 1$, returns $A[n]$.
  * `top() : T`: Precondition $n > 0$. Returns $A[n - 1]$.
  * `isEmpty() : B`: Returns $n = 0$.
  * `setEmpty()`: Resets $n := 0$.
* **Complexities**: `top`, `pop`, `isEmpty` run in $\Theta(1)$ time. `push` runs in $\Theta(1)$ amortized time ($\Theta(n)$ worst-case during array doubling).
* **Key Application**: Postfix / Reverse Polish Notation (RPN) evaluation (`EvaluatePostfix`) and Infix-to-Postfix conversion (Shunting-Yard algorithm).

#### **B. Queue ADT (FIFO - First-In, First-Out)**
* **Cyclic Array Representation**:
  * *Data Fields*: Memory array `Z : T[]`, front index `k : N`, size `n : N`, capacity `Z.length`.
  * *Front Element*: `Z[k]`.
  * *Next Free Insertion Slot*: `Z[(k + n) mod Z.length]`.
  * *`add(x : T)`*: Sets $Z[(k+n) \bmod Z.\text{length}] := x, n := n + 1$. (Reallocates if $n = Z.\text{length}$).
  * *`rem() : T`*: Saves $x := Z[k]$, updates $k := (k + 1) \bmod Z.\text{length}$, decrements $n := n - 1$, returns $x$.
  * *Complexity*: $\Theta(1)$ amortized for all operations.

---

### 2. Linked List Architectures & Operations

| Structure Type | Node Field Structure | Head/Tail Representation | Key Feature |
| :--- | :--- | :--- | :--- |
| **Singly Linked List (S1L)** | `E1 = record { key : T; next : E1* }` | `L : E1*` (points directly to 1st element) | Simple traversal; head insertion requires updating `L`. |
| **Header List (H1L)** | `E1 = record { key : T; next : E1* }` | `H : E1*` (dummy sentinel node; `H->next` is 1st element) | Eliminates edge cases when inserting/deleting at the head. |
| **List with Joker Node** | `E1 = record { key : T; next : E1* }` | `first : E1*`, `last : E1*` (points to dummy joker node at tail) | Used for **Linked Queue**: `add` writes into `last->key`, allocates new joker, and advances `last`. |
| **Circular Doubly Linked (C2L)** | `E2 = record { key : T; prev, next : E2* }` | `H : E2*` (header node; `H->next` is first, `H->prev` is last) | Bidirectional traversal; subsegment `splice` in $\Theta(1)$ time. |

#### **Essential Linked List Pointer Functions**:
* **`S1L_length(L : E1*) : N`**: Traverses `p := L` while $p \ne \text{NIL}$, incrementing count. $T(n) \in \Theta(n)$.
* **`Insert_H1L(L : E1*, k : T) : B`**: Traverses using `pprev := L, p := L->next`. If $k$ is absent, allocates `q := new E1`, sets `q->key := k`, `q->next := NIL`, links `pprev->next := q`. $T(n) \in \Theta(n)$.
* **`splice(p, q, r : E2*)`**: Unlinks subsegment $[p \dots q]$ from its current C2L list and inserts it immediately before node $r$. Executes in $\Theta(1)$ time via 6 pointer updates:
  ```text
  p1 := p->prev ; q2 := q->next
  p1->next := q2 ; q2->prev := p1
  p1 := r->prev
  p->prev := p1 ; q->next := r
  p1->next := p ; r->prev := q
  ```
* **`unionIntersection(Hu, Hi : E2*)`**: Merges two sorted C2L lists $H_u$ and $H_i$ into set union $H_u$ and intersection $H_i$ in $\Theta(n_1 + n_2)$ time using two pointers without extra allocations.

---

# Module 4: Comparison-Based Sorting Algorithms

### 1. Elementary / Straight Sorting ($\Theta(n^2)$)

#### **A. Selection Sort (`selectionSort(A/1 : T[n])`)**
* **Logic**: Loops $i$ from $1$ to $n-1$. Finds index $min$ of the minimum key in $A[i..n]$. Swaps $A[i]$ with $A[min]$.
* **Comparisons**: $C(n) = \frac{n(n-1)}{2} \in \Theta(n^2)$ for **all** best, average, and worst cases.
* **Stability**: **Unstable** (swapping non-adjacent elements across long gaps breaks equal-key ordering).

#### **B. Bubble Sort / Exchange Sort (`bubbleSort(A/1 : T[n])`)**
* **Logic**: Passes through array, swapping adjacent $A[j] > A[j+1]$.
* **Improved Version**: Tracks boundary index $u$ of the last swap performed. If no swaps occur in a pass, terminates early.
* **Complexities**: Best-case $mT(n) \in \Theta(n)$ (already sorted input), Average/Worst-case $T(n) \in \Theta(n^2)$, Space $S(n) \in \Theta(1)$.
* **Stability**: **Stable**.

#### **C. Straight Insertion Sort (`insertionSort(A/1 : T[n])`)**
* **Logic**: For $i := 2$ to $n$, extracts key $x := A[i]$, shifts elements in sorted subarray $A[1..i-1]$ rightward while $A[j] > x$, then places $x$ into slot $j+1$.
* **Complexities**: Best-case $mT(n) \in \Theta(n)$ (nearly sorted data), Average/Worst-case $T(n) \in \Theta(n^2)$, Space $S(n) \in \Theta(1)$.
* **Stability**: **Stable**. Adapted to linked lists (`H1L_insertionSort` and `C2L_insertionSort`).

---

### 2. Divide-and-Conquer Sorting ($\Theta(n \log n)$)

#### **A. Merge Sort (`mergeSort(&L : E1*)` / `ms(A, u, v)`)**
* **Logic**: Recursively divides list/array into two equal halves, sorts each half, and combines them using two-pointer merging (`merge(L1, L2 : E1*) : E1*`).
* **Complexities**: $mT(n) = AT(n) = MT(n) \in \Theta(n \log n)$ in **all cases**.
* **Auxiliary Space**: Requires $\Theta(n)$ extra array space for array implementations, but $\Theta(\log n)$ recursion stack space for linked lists.
* **Stability**: **Stable**.

#### **B. Quicksort (`quickSort(A, u, v)`)**
* **Logic**: Selects pivot $x := A[\lfloor(u+v)/2\rfloor]$. Partitions subsegment $A[u..v]$ using two pointers $i$ and $j$ scanning from left and right until $A[i] \ge x$ and $A[j] \le x$, then swaps $A[i]$ and $A[j]$. Recursively sorts left partition $A[u..j]$ and right partition $A[i..v]$.
* **Partitioning Cost**: $\Theta(n)$ per recursive level.
* **Complexities**:
  * *Best Case $mT(n)$*: $\Theta(n \log n)$ (pivot splits array into equal halves).
  * *Average Case $AT(n)$*: $\Theta(n \log n)$.
  * *Worst Case $MT(n)$*: $\Theta(n^2)$ (pivot is consistently minimum or maximum).
  * *Space Complexity*: Call stack takes $\Theta(\log n)$ average, $\Theta(n)$ worst-case.
* **Stability**: **Unstable**.
* **Hybrid Variant (`mixedQuicksort`)**: Switches to Insertion Sort when subsegment size falls below threshold $k$ (e.g., $k \le 16$).

---

# Module 5: Trees & Binary Search Trees (BST)

### 1. Tree Terminology & Classifications
* **Strictly Binary / Full**: Every internal node has exactly 2 children.
* **Nearly Complete**: Full binary tree of height $h$ where leaves are missing only on the right side of the bottom level $h$. Minimum node count for height $h$ is $2^h$.
* **Complete**: Leaves on lowest level $h$ are filled continuously from left to right without gaps.
* **Perfect**: Strictly binary tree where all leaves reside at the exact same depth $h$. Total nodes $n = 2^{h+1} - 1$.

---

### 2. Traversals ($\Theta(n)$ Time, $O(h)$ Space)
* **Preorder (`preorder(t : Node*)`)**: Visit Root $\rightarrow$ Process Left Subtree $\rightarrow$ Process Right Subtree.
* **Inorder (`inorder(t : Node*)`)**: Process Left Subtree $\rightarrow$ Visit Root $\rightarrow$ Process Right Subtree. (*Prints BST keys in strictly sorted ascending order*).
* **Postorder (`postorder(t : Node*)`)**: Process Left Subtree $\rightarrow$ Process Right Subtree $\rightarrow$ Visit Root.
* **Level-Order (`levelOrder(t : Node*)`)**: Traverses level-by-level top-to-bottom, left-to-right using an explicit **Queue**.

---

### 3. Binary Search Tree (BST) Standard Operations

* **Strict Subtree Invariant**: For every node $y$, all keys in $y$'s entire left subtree are $< y.\text{key}$, and all keys in $y$'s entire right subtree are $> y.\text{key}$.

```text
       ( Node Structure: Node = record { key : T; left, right : Node* } )
```

#### **A. Search (`search(t : Node*, k : T) : Node*`)**
* *Iterative Logic*:
  ```text
  while t ≠ NIL and t->key ≠ k do
      if k < t->key then t := t->left
      else t := t->right
  return t
  ```
* *Complexity*: $T(n) \in O(h)$ (where $h$ is tree height).

#### **B. Insertion (`insert(&t : Node*, k : T)`)**
* *Recursive Logic*:
  * If $t = \text{NIL}$, allocates $t := \text{new Node}(k)$.
  * If $k < t\rightarrow key$, calls `insert(t->left, k)`.
  * If $k > t\rightarrow key$, calls `insert(t->right, k)`.
  * If $k = t\rightarrow key$, `SKIP` (no duplicates).
* *Complexity*: $T(n) \in O(h)$.

#### **C. Deletion (`delete(&t : Node*, k : T)`)**
* *Logic (3 Cases)*:
  1. **Leaf Node**: Unlinks and deletes node directly.
  2. **Single Child**: Replaces node with its non-NIL child.
  3. **Two Children**: Finds in-order successor $s$ (minimum node in right subtree) via helper `remMin(&r : Node*) : Node*`. Replaces key with $s.\text{key}$, then deletes node $s$.
* *Complexity*: $T(n) \in O(h)$.

---

# Module 6: Priority Queues, Heaps & Heapsort

### 1. Binary Max-Heap Data Structure
* **Definition**: A complete binary tree stored in array $A[0..n-1]$ satisfying the **Max-Heap Property**: $A[\text{parent}(i)] \ge A[i]$ for all $i > 0$.
* **Arithmetic Index Mapping**:
  * $\text{left}(i) = 2i + 1$
  * $\text{right}(i) = 2i + 2$
  * $\text{parent}(i) = \lfloor (i - 1) / 2 \rfloor$

---

### 2. Core Heap Maintenance Algorithms

#### **A. Sinking / Heapify (`sink(A : T[], k, n : N)`)**
* **Logic**: Restores heap property downward starting at index $k$ within heap boundary $n$. Compares $A[k]$ with its largest child $A[j]$ (where $j \in \{2k+1, 2k+2\}$). If $A[k] < A[j]$, swaps $A[k]$ and $A[j]$, then sets $k := j$ and continues sinking down.
* **Complexity**: $T(n) \in O(\log n)$ (bounded by tree height $\lfloor \log_2 n \rfloor$).

#### **B. Building a Heap (`buildMaxHeap(A/1 : T[n])`)**
* **Logic**: Converts an unsorted array into a max-heap bottom-up by sinking internal nodes starting from the last non-leaf parent down to index 1:
  ```text
  for k := parent(n) downto 1 do
      sink(A, k, n)
  ```
* **Complexity**: Runs in linear time $\Theta(n)$ (sum of height-bounded sinks converges to $2n$).

---

### 3. Priority Queue Operations (`PrQueue`)
* **`add(x : T)`**: Appends $x$ at $A[n]$, increments $n$, then floats element up (`rise`) by swapping with $A[\text{parent}(i)]$ while $A[i] > A[\text{parent}(i)]$. Cost: $O(\log n)$ ($\Theta(n)$ worst-case if dynamic array doubles).
* **`remMax() : T`**: Saves root $A[0]$, moves last element $A[n-1]$ to $A[0]$, decrements $n$, calls `sink(A, 0, n)`, and returns saved root. Cost: $O(\log n)$.

---

### 4. Heapsort (`heapSort(A/1 : T[n])`)
* **Algorithm Steps**:
  1. `buildMaxHeap(A)` $\rightarrow$ Transforms $A$ into a max-heap in $\Theta(n)$ time.
  2. For $i := n$ downto $2$:
     * Swap root $A[1]$ with end element $A[i]$.
     * Decrease heap size boundary to $i - 1$.
     * Restore heap property at root: `sink(A, 1, i - 1)`.
* **Complexities**: $mT(n) = AT(n) = MT(n) \in \Theta(n \log n)$ in **all cases**.
* **Auxiliary Space**: $\Theta(1)$ (in-place).
* **Stability**: **Unstable**.

---

# Module 7: Linear-Time / Non-Comparison Sorting Algorithms

### 1. Comparison Lower Bound Theorem
* **Theorem**: Any comparison-based sorting algorithm requires at least $\Omega(n \log n)$ key comparisons in the worst case to sort $n$ elements.
* **Proof Concept**: Represented as a decision tree with $n!$ leaves (representing all possible permutations). The tree height $h$ satisfies $2^h \ge n! \implies h \ge \log_2(n!) \in \Omega(n \log n)$.

---

### 2. Linear-Time Algorithms

#### **A. Counting Sort (`countingSort(A, B/1 : T[n], k : N)`)**
* **Precondition**: Integer keys lie in a small bounded range $[0 \dots k]$.
* **Logic**:
  1. Initializes frequency array $C[0..k]$ to 0.
  2. Counts occurrences of each key: $C[A[i]] := C[A[i]] + 1$.
  3. Computes prefix sums: $C[j] := C[j] + C[j-1]$ (so $C[j]$ stores output position).
  4. Scans $A$ backward ($i := n$ downto $1$) and places $A[i]$ into output array $B[C[A[i]]]$, then decrements $C[A[i]]$.
* **Complexities**: Time $T(n) \in \Theta(n + k)$, Space $S(n) \in \Theta(n + k)$.
* **Stability**: **Stable** (due to backward placement pass).

#### **B. Radix Sort (`radixSort(A/1 : T[n], d : N)`)**
* **Logic**: Sorts elements digit-by-digit across $d$ digit positions starting from the Least Significant Digit (LSD) up to the Most Significant Digit (MSD) using a stable intermediate pass (like Counting Sort) for each digit.
* **Complexities**: Time $T(n) \in \Theta(d \cdot (n + k))$, Space $S(n) \in \Theta(n + k)$.
* **Stability**: **Stable**.

#### **C. Bucket Sort (`bucketSort(&L : E1*)`)**
* **Precondition**: Keys are uniformly distributed over interval $[0, 1)$.
* **Logic**: Divides range into $n$ linked-list buckets $B[0..n-1]$. Distributes elements into bucket index $j := \lfloor n \cdot \text{key} \rfloor$. Sorts each bucket using Merge Sort, then concatenates buckets $B[0 \dots n-1]$ into $L$.
* **Complexities**: Average Time $AT(n) \in \Theta(n)$, Worst-case $MT(n) \in \Theta(n^2)$ (when keys cluster into a single bucket), Space $S(n) \in \Theta(n)$.
* **Stability**: **Stable**.

---

# Module 8: Hash Tables & Key Transformations

### 1. Hashing Fundamentals
* **Objective**: Store keys in array $T[0..m-1]$ of size $m$ to achieve statistical $\Theta(1)$ average time for `search`, `insert`, and `delete`.
* **Hash Functions**:
  * **Division Method**: $h(k) = k \bmod m$ (where $m$ is chosen as a prime number not close to powers of 2).
  * **Multiplication Method**: $h(k) = \lfloor m \cdot (k \cdot A \bmod 1) \rfloor$ (where $0 < A < 1$).

---

### 2. Collision Resolution Strategies

#### **A. Separate Chaining (Direct Chaining)**
* **Structure**: Hash table array $T[0..m-1]$ stores pointers to singly linked lists (buckets).
* **Load Factor**: $\alpha = n / m$ (average elements per list).
* **Search / Delete Cost**: Unsuccessful search takes $\Theta(1 + \alpha)$ steps; successful search takes $\Theta(1 + \alpha / 2)$ steps.

#### **B. Open Addressing**
* **Structure**: All records are stored directly within hash table array slots $T[0..m-1]$ without linked lists.
* **Probing Sequence**: Uses sequence $h(k, i)$ for probe attempt $i \in \{0, 1, \dots, m-1\}$.
* **Probing Schemes**:
  1. **Linear Probing**: $h(k, i) = (h'(k) + i) \bmod m$. (*Simple, but suffers from Primary Clustering—long contiguous blocks of occupied slots build up*).
  2. **Quadratic Probing**: $h(k, i) = (h'(k) + c_1 i + c_2 i^2) \bmod m$. (*Eliminates primary clustering, but suffers from Secondary Clustering*).
  3. **Double Hashing**: $h(k, i) = (h_1(k) + i \cdot h_2(k)) \bmod m$ (where $h_2(k)$ is relatively prime to $m$). (*Best distribution; eliminates clustering*).
* **Deletion Rule**: Deleted entries must be marked with a special sentinel flag `DELETED` (rather than `NIL`) so probe chains during subsequent searches are not broken.

---

# Master Comparison Table: All Course Sorting Algorithms

| Algorithm | Best Time $mT(n)$ | Average Time $AT(n)$ | Worst Time $MT(n)$ | Auxiliary Space | Stable? | Primary Paradigm / Category |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Selection Sort** | $\Theta(n^2)$ | $\Theta(n^2)$ | $\Theta(n^2)$ | $\Theta(1)$ | ✘ No | Selection |
| **Bubble Sort** | $\Theta(n)$ *(improved)* | $\Theta(n^2)$ | $\Theta(n^2)$ | $\Theta(1)$ | ✔ Yes | Exchange / Neighbor Swaps |
| **Insertion Sort** | $\Theta(n)$ | $\Theta(n^2)$ | $\Theta(n^2)$ | $\Theta(1)$ | ✔ Yes | Insertion |
| **Merge Sort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n)$ | ✔ Yes | Divide & Conquer |
| **Quicksort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n^2)$ | $\Theta(\log n)$ avg | ✘ No | Divide & Conquer / Partitioning |
| **Heapsort** | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(n \log n)$ | $\Theta(1)$ | ✘ No | Selection / Binary Heap |
| **Counting Sort** | $\Theta(n + k)$ | $\Theta(n + k)$ | $\Theta(n + k)$ | $\Theta(n + k)$ | ✔ Yes | Key Transformation / Frequency |
| **Radix Sort** | $\Theta(d(n+k))$ | $\Theta(d(n+k))$ | $\Theta(d(n+k))$ | $\Theta(n + k)$ | ✔ Yes | Positional / Digit Distribution |
| **Bucket Sort** | $\Theta(n)$ | $\Theta(n)$ | $\Theta(n^2)$ | $\Theta(n)$ | ✔ Yes | Distribution / Linked Buckets |
