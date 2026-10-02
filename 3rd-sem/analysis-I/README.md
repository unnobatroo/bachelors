# Analysis I

This master document serves as the ultimate blueprint for navigating the ELTE Analysis I course. It maps out the rigorous assessment architecture, breaks down every module for both computational practice and theoretical proofs, and links to top-tier external resources to help you master the material.

## 1. Course Assessment & Exam Architecture

The ELTE Analysis-1 evaluation is rigorously split into a Practice (seminar) grade and a Theory (lecture) exam, demanding both computational fluency and formal proof writing.

### Practice Grade (Test 1 + Test 2)

Based on two 90-minute written midterms worth **50 points each**.

- **Passing Requirement:** You must score $\ge 15$ points on _each_ test individually.
- **Grading Scale:** 30–45 points (Pass - 2), 46–62 (Satisfactory - 3), 63–79 (Good - 4), 80–100 (Excellent - 5).
- **Retakes:** If you fail or miss a test, you can write a 90-minute Correctional Test for your weakest midterm. If your total practice grade is still a fail, you must pass a Final Correctional Test (100 mins, 30+30 pts, requiring $\ge 10$ points per half).

### Theory Exam (Prerequisite: Practice Grade $\ge 2$)

Taken in person and structured as a progressive funnel to test conceptual depth:

- **Round 1 (Basic Level):** A Canvas quiz testing definitions and true/false logic from the **130 Basic Questions list**.
  - _Scoring:_ 0–8 (Fail), 9–11 (Pass - 2), 12 (Satisfactory - 3). If you score 13–15, you receive a Satisfactory (3) but earn the _invitation to Round 2_ to improve your grade.
- **Round 2 (Advanced Level):** Handwritten proofs on paper for students scoring $\ge 13$ in Round 1.
  - _Part 1A:_ 7 short theoretical questions (must score 5–7 points to pass).
  - _Part 1B:_ State and fully prove one theorem randomly selected from the **40 Required Theorems list**. If this proof fails, all of Part 1 fails regardless of 1A.
  - _Part 2:_ Prove additional theorems from the 40 Required Theorems list. Passing both Part 1 and Part 2 yields an Excellent (5).

## 2. MIDTERM 1 / TEST 1 OVERVIEW (Weeks 1 – 6)

### Module 1: Real Numbers, Boundedness & Inverse Functions

- **Practice Problem Types:**
  - **Invertibility Proofs:** Prove injectivity algebraically: $\forall x, t \in D_f : f(x) = f(t) \implies x = t$. [🎥 _Professor Leonard: One to One Functions_](https://www.youtube.com/watch?v=C0Q_m2UDerc)
  - **Domain / Range Calculation:** Evaluate $D_f$ and $R_f$, explicitly establishing that $D_{f^{-1}} = R_f$. [📖 _Paul's Online Math Notes: Inverse Functions_](https://tutorial.math.lamar.edu/Classes/CalcI/InverseFunctions.aspx)
  - **Formula Derivation:** Algebraically isolate $x$ in terms of $y$ to build $f^{-1}(y)$, keeping domain constraints in mind (e.g., handling principal square roots).
- **Theoretical Grounding (130 Questions):**
  - Axioms of real numbers ($\mathbb{R}$), intervals, and the Extended Real Number Set $\overline{\mathbb{R}}$.
  - Definitions of upper/lower bounds, supremum ($\sup H$), infimum ($\inf H$), maximum, minimum. [🎥 _Wrath of Math: Definition of Supremum and Infimum of a Set_](https://www.youtube.com/watch?v=QRGIhqz9vh4)
  - **The Axiom of Completeness:** Every non-empty subset of $\mathbb{R}$ that is bounded above has a supremum in $\mathbb{R}$.
- **Required Theorems to Prove:**
  - **Theorem 1:** Triangle Inequalities in $\mathbb{R}$ ($|x+y| \le |x|+|y|$ and $|x-y| \ge ||x|-|y||$).

### Module 2: Function Composition

- **Practice Problem Types:**
  - **Composite Domain Determination:** Solve the nested domain condition algebraically:
    $$D_{f \circ g} = \{x \in D_g \mid g(x) \in D_f\}$$
    _Warning:_ Never determine the domain from the _simplified_ final formula. [🎥 _Professor Leonard: Composition of Functions_](https://www.youtube.com/watch?v=EsgHKmLSPVc)
  - **Formula Derivation:** Evaluate $f(g(x))$ and simplify under domain conditions, particularly noting piecewise outputs or interval restrictions like $\sqrt{x^2} = |x|$.

### Module 3: Sequence Limits by Definition ($\varepsilon - N$ and $P - N$)

- **Practice Problem Types:**
  - **Finite Limits ($\lim a_n = A$):** Prove $|a_n - A| < \varepsilon$ by establishing an order-preserving upper bound to isolate $n$, finding a threshold index $N(\varepsilon)$. [🎥 _The Math Sorcerer: Epsilon-N Proofs for Sequences_](https://www.youtube.com/watch?v=FnghNJqL5PA)
  - **Infinite Limits ($\lim a_n = \pm\infty$):** Prove $a_n > P$ by establishing a lower bound and finding $N(P)$ for a threshold (e.g., $P = 100$). [🎥 _blackpenredpen: Epsilon-N definition for limits at infinity_](https://www.youtube.com/watch?v=9JMFLzHtljA)
- **Theoretical Grounding (130 Questions):**
  - Definitions of sequences, index sequences, subsequences, and zero sequences.
- **Required Theorems to Prove:**
  - **Theorem 2:** Connection between convergence and ordering relations.
  - **Theorem 3:** Convergent sequences are necessarily bounded.
  - **Theorem 4:** Five fundamental theorems (Th1–Th5) on zero sequences.

### Module 4: Algebraic Sequence Limit Computations

- **Practice Problem Types:**
  - **Dominant Term Factoring:** Factor out the highest power of $n$ for rational expressions (e.g., $\frac{n^k}{a^n} \to 0$ if $a > 1$). [📖 _Paul's Online Math Notes: Limits at Infinity_](https://tutorial.math.lamar.edu/Classes/CalcI/LimitsAtInfinityI.aspx)
  - **Conjugate Multiplication:** Rationalize radical differences using $\sqrt{A} - \sqrt{B} = \frac{A - B}{\sqrt{A} + \sqrt{B}}$.
  - **$n$-th Roots:** Evaluate using the Sandwich (Squeeze) theorem limits like $\lim \sqrt[n]{a} = 1$ and $\lim \sqrt[n]{n} = 1$.
- **Required Theorems to Prove:**
  - **Theorems 5–7:** Algebraic operations with convergent sequences (Addition, Multiplication, Reciprocals).
  - **Theorem 8:** The Sandwich (Squeeze) Theorem. [🎥 _Khan Academy: Squeeze Theorem_](https://www.khanacademy.org/math/ap-calculus-ab/ab-limits-new/ab-1-8/v/squeeze-sandwich-theorem)
  - **Theorems 9–12:** Convergence of geometric sequences, $\sqrt[n]{a}$, $\sqrt[n]{n}$, $n^k q^n$, and $\frac{x^n}{n!} \to 0$.

### Module 5: Monotone, Recursive, and $e$-Sequences

- **Practice Problem Types:**
  - **Recursive Sequences ($a\_{n+1} = f(a_n)$):** Use mathematical induction to prove boundedness and monotonicity, then find the finite limit by solving $L = f(L)$. [🎥 _Abdul Bari: Recurrence Relation_](https://www.youtube.com/watch?v=4V30R3I1vLI) | [🎥 _The Math Sorcerer: How to Write a Mathematical Induction Proof_](https://www.youtube.com/watch?v=IfdKxy7HFlc)
  - **Euler's $e$ Limits:** Transform sequence limits into the canonical form $\lim_{n\to\infty} \left(1 + \frac{x}{n}\right)^n = e^x$.
- **Theoretical Grounding & Theorems to Prove:**
  - **Theorem 13 (Monotone Convergence Theorem):** Monotonically increasing bounded sequences are convergent. [🎥 _Turnupmath: Monotone Convergence Theorem Proved_](https://www.youtube.com/watch?v=7TLtTIJ8hts)
  - **Theorem 14:** The sequence $\left(1 + \frac{1}{n}\right)^n$ is convergent (defining $e$).
  - **Theorems 15–16:** Infinite sequence limit rules for addition and multiplication.

## 3. MIDTERM 2 / TEST 2 OVERVIEW (Weeks 7 – 12)

### Module 6: Infinite Numerical Series & Exact Summation

- **Practice Problem Types:**
  - **Geometric Series:** Sum $\sum_{n=p}^\infty q^n = \frac{q^p}{1-q}$ when $|q| < 1$. Note the starting index $p$. [📖 _Paul's Online Math Notes: Series Basics_](https://tutorial.math.lamar.edu/classes/calcii/seriesintro.aspx)
  - **Telescoping Series:** Utilize partial fraction decomposition to evaluate sums via limits of partial sums $S_n$. [🎥 _The Organic Chemistry Tutor: Telescoping Series_](https://www.youtube.com/watch?v=XVkdhU6nJbo)
- **Required Theorems to Prove:**
  - **Theorem 17:** Convergence and sum of geometric series.
  - **Theorem 18:** The Zero-Sequence Test ($a_n \to 0$ as a strictly _necessary_, but not sufficient, condition for convergence).
  - **Theorem 19:** Cauchy's Convergence Test for series.

### Module 7: Series Convergence Tests

- **Practice Problem Types:**
  - **Comparison Tests:** Apply Majorant (upper bounding to prove convergence) and Minorant (lower bounding to prove divergence) tests.
  - **Root & Ratio Tests:** Evaluate $L = \lim \sqrt[n]{|a_n|}$ or $L = \lim \left|\frac{a_{n+1}}{a_n}\right|$ ($L<1$ converges absolutely, $L>1$ diverges, $L=1$ indeterminate). [📖 _Paul's Online Math Notes: Ratio & Root Tests_](https://tutorial.math.lamar.edu/classes/calcii/RatioTest.aspx)
  - **Leibniz Test:** Check if alternating series $\sum (-1)^n a_n$ terms strictly decrease to 0 for conditional convergence.
- **Required Theorems to Prove:**
  - **Theorem 20:** Comparison Tests (Major Test, Minor Test).
  - **Theorem 21:** Leibniz Criterion for alternating series.
  - **Theorem 22:** Root Test (and recognizing the indeterminate $L=1$ case).

### Module 8: Power Series & Analytic Functions

- **Practice Problem Types:**
  - **Convergence Sets:** Calculate the radius $R = \frac{1}{L}$ via Root/Ratio tests. Systematically test boundary endpoints ($x_0 - R$ and $x_0 + R$) manually to build the final convergence interval $S$ (e.g., $[-2, 4)$).
  - **Analytical Expansion:** Expand rational functions around $x_0$ by forcing them into the geometric series format: $\frac{1}{1-u} = \sum_{n=0}^\infty u^n$. [🎥 _3Blue1Brown: Taylor Series Visualized_](https://www.youtube.com/watch?v=3d6DsjIBzJ4)
- **Required Theorems to Prove:**
  - **Theorem 23:** Convergence set of a power series via the Root Test.
  - **Theorems 24–28:** Expansions and connections between $\exp, \cosh, \sinh, \cos, \sin$ (including Euler's Identity $e^{ix} = \cos x + i \sin x$ and Addition Formulas).
  - **Theorems 29–30:** Error estimation for $e = \exp(1)$ and the proof that $e \notin \mathbb{Q}$.

### Module 9: Function Limits ($\varepsilon - \delta$ & Algebra)

- **Practice Problem Types:**
  - **Formal Limit Proofs:** Prove $\lim_{x\to a} f(x) = A$ using the strict $\varepsilon - \delta$ definition. [🎥 _Khan Academy: Epsilon-Delta Definition_](https://www.khanacademy.org/math/ap-calculus-ab/ab-limits-new/ab-1-2/v/epsilon-delta-definition-of-limits)
  - **Algebraic Limit Evaluation:** Handle $\frac{0}{0}$ and $\frac{\infty}{\infty}$ indeterminate forms for polynomials and radicals via factoring and rationalization. (L'Hôpital's rule is generally barred at this stage).
- **Theoretical Grounding & Theorems to Prove:**
  - Definitions of right/left-sided limits, limits at $\pm\infty$, and continuity.
  - **Theorem 31:** Heine's Transference Principle for function limits.

### Module 10: Special Analytic Limits

- **Practice Problem Types:**
  - **Non-L'Hôpital Limit Solving:** Evaluate complex limits by identifying the foundational architectures of:
    $$
    \lim_{x\to 0} \frac{\sin x}{x} = 1, \quad \lim_{x\to 0} \frac{1-\cos x}{x^2} = \frac{1}{2}, \quad \lim_{x\to 0} \frac{e^x-1}{x} = 1
    $$
- **Required Theorems to Prove:**
  - **Theorem 32:** Formal proofs of the basic limits for trigonometric and exponential functions. [🎥 _Dr. Trefor Bazett: Geometric Proof of sin(x)/x Limit_](https://www.youtube.com/watch?v=f2PRu5QPa3o)

### Module 11: Topology of $\mathbb{R}$, Continuity & Global Theorems

- **Theoretical Grounding (Theory Exam Focus):**
  - Interior points ($int H$), boundary points ($\partial H$), exterior points ($ext H$), open sets, and closed sets. [🎥 _Dr. Bevin Maultsby: Compact Sets and Open Covers_](https://www.youtube.com/watch?v=onRI37DdBu4)
  - Continuity vs. uniform continuity.
- **Required Theorems to Prove:**
  - **Theorem 33:** Characterization of closed sets via convergent sequences.
  - **Theorem 34:** Heine-Borel Theorem (Compact $\iff$ Closed and Bounded in $\mathbb{R}$). [🎥 _Jason Bramburger: The Heine-Borel Covering Theorem_](https://www.youtube.com/watch?v=wZ_cdso7JGM)
  - **Theorem 35–37:** Weierstrass Extreme Value (Minimax) Theorem and minimal/maximal elements of compact sets.
  - **Theorem 38:** Proof that $f(x) = \frac{1}{x}$ is continuous on $(0, \infty)$ but _not_ uniformly continuous.
  - **Theorem 39:** Intermediate Value Theorem (Bolzano's Theorem).

## 4. Cross-Semester Master Matrix

| Course Module           | Practice Task Focus (Midterms)                                                            | Theoretical Proof Focus (Final Exam)                                                |
| :---------------------- | :---------------------------------------------------------------------------------------- | :---------------------------------------------------------------------------------- |
| **1. Inverses**         | Find $f^{-1}(y)$, $D_{f^{-1}}$, prove injectivity algebraically.                          | Triangle Inequalities (Thm 1).                                                      |
| **2. Sup/Inf**          | Prove bounds & evaluate $\sup/\inf/\max/\min$ natively.                                   | Completeness Axiom, Supremum properties.                                            |
| **3. Limit Proofs**     | Find threshold index $N(\varepsilon)$ or $N(P)$ for sequence limits.                      | Zero Sequences, Boundedness links (Thm 2-4).                                        |
| **4. Sequence Algebra** | Factor dominant terms, radicals, Euler's $e^x$ limits.                                    | Sandwich Theorem, $\sqrt[n]{n} \to 1$, $\frac{x^n}{n!} \to 0$ (Thm 5-12).           |
| **5. Recursion**        | Monotonicity & bounds via mathematical induction.                                         | Monotone Convergence & Sequence $e$ (Thm 13-16).                                    |
| **6. Series Sums**      | Exact sums of geometric & telescoping sequences.                                          | Geometric Series Sum, Zero-Sequence Test (Thm 17-19).                               |
| **7. Series Tests**     | Root, Ratio, Comparison, Leibniz tests application.                                       | Comparison Tests, Cauchy Root Test (Thm 20-22).                                     |
| **8. Power Series**     | Find radius $R$, convergence set $S$, and analytical expansion.                           | Convergence set logic, $\exp$, Euler's Identity, $e \notin \mathbb{Q}$ (Thm 23-30). |
| **9. Function Limits**  | Factor algebraic zeros, evaluate rational/radical limits.                                 | Transference Principle (Heine) (Thm 31).                                            |
| **10. Special Limits**  | Evaluate analytical forms: $\frac{\sin x}{x}$, $\frac{1-\cos x}{x^2}$, $\frac{e^x-1}{x}$. | Exact proofs of special trigonometric/exponential limits (Thm 32).                  |
| **11. Topology**        | Conceptual questions in Round 1 Canvas quiz.                                              | Closed sets, Heine-Borel, Weierstrass Minimax, Bolzano's Theorem (Thm 33-39).       |
