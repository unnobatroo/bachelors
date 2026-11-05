# Analysis I

Study guide for the ELTE Analysis I course: how the assessment works, what each module covers (computational practice
and theory/proofs), and curated external resources for each topic.

## Contents

1. [Course Assessment & Exam Architecture](#1-course-assessment--exam-architecture)
2. [Midterm 1 Overview (Weeks 1–6)](#2-midterm-1--test-1-overview-weeks-16)
3. [Midterm 2 Overview (Weeks 7–12)](#3-midterm-2--test-2-overview-weeks-712)
4. [Cross-Semester Master Matrix](#4-cross-semester-master-matrix)

## 1. Course Assessment & Exam Architecture

The evaluation is split into a **Practice (seminar) grade** and a **Theory (lecture) exam**, demanding both
computational fluency and formal proof writing.

### Practice Grade (Test 1 + Test 2)

Two 90-minute written midterms worth **50 points each**.

* **Passing requirement:** at least $\ge 15$ points on *each* test individually.
* **Grading scale:**

  | Points  | Grade            |
  |---------|------------------|
  | 30–45   | Pass (2)         |
  | 46–62   | Satisfactory (3) |
  | 63–79   | Good (4)         |
  | 80–100  | Excellent (5)    |

* **Retakes:**
  * Failed or missed a test: write a 90-minute *Correctional Test* for your weakest midterm.
  * Still failing overall: pass the *Final Correctional Test* (100 min, 30 + 30 pts, at least $\ge 10$ points per half).

### Theory Exam

**Prerequisite:** Practice grade $\ge 2$. Taken in person, structured as a progressive funnel.

#### Round 1: Basic Level

A Canvas quiz on definitions and true/false logic, drawn from the **130 Basic Questions** list.

| Score | Result                                                  |
|-------|---------------------------------------------------------|
| 0–8   | Fail                                                    |
| 9–11  | Pass (2)                                                |
| 12    | Satisfactory (3)                                        |
| 13–15 | Satisfactory (3) **+ invitation to Round 2** to improve |

#### Round 2: Advanced Level

Handwritten proofs on paper, for students scoring $\ge 13$ in Round 1.

* **Part 1A:** 7 short theoretical questions (need 5–7 points to pass).
* **Part 1B:** state and fully prove one randomly selected theorem from the **40 Required Theorems** list. If this proof
  fails, all of Part 1 fails regardless of 1A.
* **Part 2:** prove additional theorems from the 40 Required Theorems list. Passing both Part 1 and Part 2 yields an
  Excellent (5).

## 2. Midterm 1 / Test 1 Overview (Weeks 1–6)

### Module 1: Real Numbers, Boundedness & Inverse Functions

**Practice problem types**

* **Invertibility proofs:** prove injectivity algebraically:
  $\forall x, t \in D_f : f(x) = f(t) \implies x = t$.
  * 🎥 [Professor Leonard: One to One Functions](https://www.youtube.com/watch?v=C0Q_m2UDerc)
  * 🎥 [Professor Leonard: Finding Inverse Functions (Precalculus Lecture 51)](http://www.youtube.com/watch?v=fK7IPeeZoFE)
* **Domain / range calculation:** evaluate $D_f$ and $R_f$, explicitly establishing that $D_{f^{-1}} = R_f$.
  * 📖 [Paul's Online Math Notes: Inverse Functions](https://tutorial.math.lamar.edu/Classes/CalcI/InverseFunctions.aspx)
  * 🎥 [SawFin Mathematics: Introduction to Functions and Function Notation (1.4.1)](https://www.youtube.com/@SawFinMathematics/videos)
* **Formula derivation:** isolate $x$ in terms of $y$ to build $f^{-1}(y)$, keeping domain constraints in mind (e.g.
  principal square roots).

**Theoretical grounding (130 Questions)**

* Axioms of the real numbers ($\mathbb{R}$), intervals, and the extended real number set $\overline{\mathbb{R}}$.
  * 🎥 [The Bright Side of Mathematics: Real Analysis Part 1 (Axioms & Absolute Value)](https://www.youtube.com/watch?v=Lu8RaHeTivc)
* Upper/lower bounds, supremum ($\sup H$), infimum ($\inf H$), maximum, minimum.
  * 🎥 [Wrath of Math: Definition of Supremum and Infimum of a Set](https://www.youtube.com/watch?v=QRGIhqz9vh4)
  * 🎥 [Michael Penn: Supremum & Infimum in Real Analysis](https://www.youtube.com/watch?v=hJ1fB9E-xXY)
* **Axiom of Completeness:** every non-empty subset of $\mathbb{R}$ that is bounded above has a supremum in
  $\mathbb{R}$.
  * 🌐 [The Bright Side of Mathematics: Real Analysis Course (Parts 6 & 7 - Supremum, Infimum & Completeness)](https://thebrightsideofmathematics.com/courses/real_analysis/overview/)

**Required theorems**

* **Theorem 1:** triangle inequalities in $\mathbb{R}$:
  $|x+y| \le |x|+|y|$ and $|x-y| \ge \big||x|-|y|\big|$.

### Module 2: Function Composition

**Practice problem types**

* **Composite domain determination:** solve the nested domain condition algebraically:

  $$D_{f \circ g} = \{x \in D_g \mid g(x) \in D_f\}$$

  > ⚠️ Never determine the domain from the *simplified* final formula.

  * 🎥 [Professor Leonard: Composition of Functions](https://www.youtube.com/watch?v=EsgHKmLSPVc)
  * 🎥 [Professor Leonard: Combining and Composition of Functions (Full Lecture)](https://www.youtube.com/watch?v=1bZE9Jp2Fz4&list=PLF797E961509B4EB5)
* **Formula derivation:** evaluate $f(g(x))$ and simplify under domain conditions, noting piecewise outputs or
  interval restrictions like $\sqrt{x^2} = |x|$.

### Module 3: Sequence Limits by Definition ($\varepsilon$–$N$ and $P$–$N$)

**Practice problem types**

* **Finite limits ($\lim a_n = A$):** prove $|a_n - A| < \varepsilon$ by finding an order-preserving upper bound that
  isolates $n$, giving a threshold index $N(\varepsilon)$.
  * 🎥 [The Math Sorcerer: Epsilon-N Proofs for Sequences](https://www.youtube.com/watch?v=FnghNJqL5PA)
  * 🎥 [SawFin Mathematics: Introduction to Sequences (2.4.1)](http://www.youtube.com/watch?v=x2Ul4NtyoHw)
* **Infinite limits ($\lim a_n = \pm\infty$):** prove $a_n > P$ by establishing a lower bound and finding $N(P)$ for a
  threshold (e.g. $P = 100$).
  * 🎥 [blackpenredpen: Epsilon-N definition for limits at infinity](https://www.youtube.com/watch?v=9JMFLzHtljA)

**Theoretical grounding (130 Questions)**

* Definitions of sequences, index sequences, subsequences, and zero sequences.
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.1 (Convergence and Divergence of Sequences)](http://www.youtube.com/watch?v=FoNLQvf4NUs)
  * 🎥 [SawFin Mathematics: Pattern Recognition for Sequences (9.1.2)](http://www.youtube.com/watch?v=6YFWJBE3o9w)

**Required theorems**

* **Theorem 2:** connection between convergence and ordering relations.
* **Theorem 3:** convergent sequences are necessarily bounded.
  * 🌐 [The Bright Side of Mathematics: Real Analysis Parts 2 & 3 (Sequences, Limits & Boundedness)](https://thebrightsideofmathematics.com/courses/real_analysis/overview/)
* **Theorem 4:** five fundamental theorems (Th1–Th5) on zero sequences.

### Module 4: Algebraic Sequence Limit Computations

**Practice problem types**

* **Dominant term factoring:** factor out the highest power of $n$ in rational expressions (e.g.
  $\frac{n^k}{a^n} \to 0$ if $a > 1$).
  * 📖 [Paul's Online Math Notes: Limits at Infinity](https://tutorial.math.lamar.edu/Classes/CalcI/LimitsAtInfinityI.aspx)
  * 🎥 [SawFin Mathematics: The Limit of a Sequence (9.1.1)](http://www.youtube.com/watch?v=dsjOpoGcs9Q)
* **Conjugate multiplication:** rationalize radical differences using
  $\sqrt{A} - \sqrt{B} = \frac{A - B}{\sqrt{A} + \sqrt{B}}$.
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.1 (Sequence Limit Techniques)](http://www.youtube.com/watch?v=FoNLQvf4NUs)
* **$n$-th roots:** use the Sandwich (Squeeze) theorem for limits like $\lim \sqrt[n]{a} = 1$ and
  $\lim \sqrt[n]{n} = 1$.

**Required theorems**

* **Theorems 5–7:** algebraic operations with convergent sequences (addition, multiplication, reciprocals).
* **Theorem 8:** the Sandwich (Squeeze) Theorem.
  * 🎥 [Khan Academy: Squeeze Theorem](https://www.khanacademy.org/math/ap-calculus-ab/ab-limits-new/ab-1-8/v/squeeze-sandwich-theorem)
  * 🎥 [Wrath of Math: Proof of the Squeeze Theorem for Sequences](https://www.youtube.com/watch?v=OftutcgZ_Fw)
* **Theorems 9–12:** convergence of geometric sequences, $\sqrt[n]{a}$, $\sqrt[n]{n}$, $n^k q^n$, and
  $\frac{x^n}{n!} \to 0$.

### Module 5: Monotone, Recursive, and $e$-Sequences

**Practice problem types**

* **Recursive sequences ($a_{n+1} = f(a_n)$):** use induction to prove boundedness and monotonicity, then find the
  finite limit by solving $L = f(L)$.
  * 🎥 [Abdul Bari: Recurrence Relation](https://www.youtube.com/watch?v=4V30R3I1vLI)
  * 🎥 [The Math Sorcerer: How to Write a Mathematical Induction Proof](https://www.youtube.com/watch?v=IfdKxy7HFlc)
  * 🎥 [SawFin Mathematics: Monotonic and Bounded Sequences (9.1.3)](http://www.youtube.com/watch?v=AwAAM_zmge4)
* **Euler's $e$ limits:** transform sequence limits into the canonical form
  $\lim_{n\to\infty} \left(1 + \frac{x}{n}\right)^n = e^x$.
  * 🎥 [3Blue1Brown: What's so special about Euler's number e? (Essence of Calculus Ch. 5)](https://www.youtube.com/watch?v=m2MIpDrF7Es)
  * 🎥 [Numberphile: e (Euler's Number)](http://www.youtube.com/watch?v=AuA2EAgAegE)

**Required theorems**

* **Theorem 13 (Monotone Convergence Theorem):** monotonically increasing bounded sequences are convergent.
  * 🎥 [Turnupmath: Monotone Convergence Theorem Proved](https://www.youtube.com/watch?v=7TLtTIJ8hts)
  * 🎥 [SawFin Mathematics: 9.1 Supplement - Monotonic and Bounded Sequences](http://www.youtube.com/watch?v=u5Y60i3pa0A)
* **Theorem 14:** the sequence $\left(1 + \frac{1}{n}\right)^n$ is convergent (defining $e$).
* **Theorems 15–16:** infinite sequence limit rules for addition and multiplication.

## 3. Midterm 2 / Test 2 Overview (Weeks 7–12)

### Module 6: Infinite Numerical Series & Exact Summation

**Practice problem types**

* **Geometric series:** $\sum_{n=p}^\infty q^n = \frac{q^p}{1-q}$ when $|q| < 1$. Watch the starting index $p$.
  * 📖 [Paul's Online Math Notes: Series Basics](https://tutorial.math.lamar.edu/classes/calcii/seriesintro.aspx)
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.2 (Series, Geometric Series, Harmonic Series, and Divergence Test)](https://www.youtube.com/watch?v=DGcWMdW-72M)
  * 🎥 [SawFin Mathematics: The Geometric Series (9.2.2)](http://www.youtube.com/watch?v=OX-9dHs4u-Q)
* **Telescoping series:** use partial fractions to evaluate sums as limits of partial sums $S_n$.
  * 🎥 [The Organic Chemistry Tutor: Telescoping Series](https://www.youtube.com/watch?v=XVkdhU6nJbo)
  * 🎥 [SawFin Mathematics: Infinite Series & Telescoping Sums (9.2.1)](http://www.youtube.com/watch?v=vzwfcNNmn1M)
  * 🎥 [Numberphile: Infinite Series](http://www.youtube.com/watch?v=Jwtn5_d2YCs)

**Required theorems**

* **Theorem 17:** convergence and sum of geometric series.
* **Theorem 18:** zero-sequence test ($a_n \to 0$ is *necessary* but not sufficient for convergence).
  * 🎥 [SawFin Mathematics: The nth Term Test for Divergence (9.2.3)](http://www.youtube.com/watch?v=vhonH59Rxi8)
* **Theorem 19:** Cauchy's convergence test for series.

### Module 7: Series Convergence Tests

**Practice problem types**

* **Comparison tests:** majorant (upper bound, proves convergence) and minorant (lower bound, proves divergence) tests,
  plus hyperharmonic ($p$-series) benchmarks.
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.3 (Integral Test & P-Series)](https://www.youtube.com/watch?v=DGcWMdW-72M)
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.4 (The Comparison Test & Limit Comparison Test)](https://www.youtube.com/watch?v=ei8WKMAHky0)
  * 🎥 [SawFin Mathematics: The Direct Comparison Test (9.4.1)](http://www.youtube.com/watch?v=oobg9RMaGjo)
  * 🎥 [SawFin Mathematics: The Limit Comparison Test (9.4.2)](http://www.youtube.com/watch?v=vrdEFULYkL8)
* **Root & ratio tests:** evaluate $L = \lim \sqrt[n]{|a_n|}$ or $L = \lim \left|\frac{a_{n+1}}{a_n}\right|$.
  $L<1$ converges absolutely, $L>1$ diverges, $L=1$ is indeterminate.
  * 📖 [Paul's Online Math Notes: Ratio & Root Tests](https://tutorial.math.lamar.edu/classes/calcii/RatioTest.aspx)
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.6 (Absolute Convergence, Ratio Test and Root Test)](https://www.youtube.com/watch?v=g4iZJOwMkjU)
  * 🎥 [SawFin Mathematics: The Ratio Test (9.6.1)](http://www.youtube.com/watch?v=uSsHlOb2wPA)
  * 🎥 [SawFin Mathematics: The Root Test (9.6.2)](http://www.youtube.com/watch?v=z85YBJUiu9s)
* **Leibniz test & absolute/conditional classification:** check that the terms of an alternating series
  $\sum (-1)^n a_n$ strictly decrease to 0 for conditional convergence.
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.5 (Alternating Series Test & Error of Sums)](https://www.youtube.com/watch?v=BhYPrQHDrjk)
  * 🎥 [SawFin Mathematics: The Alternating Series Test (9.5.1)](http://www.youtube.com/watch?v=cgxK4PH1XMY)
  * 🎥 [SawFin Mathematics: Absolute and Conditional Convergence (9.5.3)](http://www.youtube.com/watch?v=1OGOUQac1qQ)

**Required theorems**

* **Theorem 20:** comparison tests (major test, minor test).
* **Theorem 21:** Leibniz criterion for alternating series.
* **Theorem 22:** root test (and recognizing the indeterminate $L=1$ case).

### Module 8: Power Series & Analytic Functions

**Practice problem types**

* **Convergence sets:** compute the radius $R = \frac{1}{L}$ via root/ratio tests, then test the endpoints
  ($x_0 - R$ and $x_0 + R$) manually to build the convergence interval $S$ (e.g. $[-2, 4)$).
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.7 (Power Series & Interval of Convergence)](https://www.youtube.com/watch?v=TGD-TP1c7i4)
  * 🎥 [SawFin Mathematics: Power Series Radius of Convergence (9.8.1)](http://www.youtube.com/watch?v=u5k5Iy9My7Q)
  * 🎥 [SawFin Mathematics: Power Series Radius and Interval of Convergence (9.8.2)](https://www.youtube.com/watch?v=ToEWg269oV0)
* **Analytical expansion:** expand rational functions around $x_0$ by forcing them into geometric series form:
  $\frac{1}{1-u} = \sum_{n=0}^\infty u^n$.
  * 🎥 [3Blue1Brown: Taylor Series Visualized (Essence of Calculus Ch. 11)](https://www.youtube.com/watch?v=3d6DsjIBzJ4)
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.8 (Representation of Functions by Taylor & Maclaurin Series)](http://www.youtube.com/watch?v=3VHol7eosLA)
  * 🎥 [SawFin Mathematics: The Taylor and Maclaurin Series (9.10.1)](http://www.youtube.com/watch?v=xU4sUffRUks)

**Required theorems**

* **Theorem 23:** convergence set of a power series via the root test.
* **Theorems 24–28:** expansions and connections between $\exp, \cosh, \sinh, \cos, \sin$, including Euler's identity
  $e^{ix} = \cos x + i \sin x$ and the addition formulas.
  * 🎥 [3Blue1Brown: e^(iπ) in 3.14 minutes, using dynamics](https://www.youtube.com/watch?v=v0YEaeIClKY)
  * 🎥 [Numberphile: Euler's Formula](https://www.youtube.com/watch?v=CRj-sbi2i2I)
  * 🎥 [Professor Leonard: Calculus 2 Lecture 6.6 (A Discussion of Hyperbolic Functions)](http://www.youtube.com/watch?v=3kPg0gkJQgc)
* **Theorems 29–30:** error estimation for $e = \exp(1)$ and the proof that $e \notin \mathbb{Q}$.
  * 🎥 [Numberphile: A proof that e is irrational](http://www.youtube.com/watch?v=xOXsDfMMTjs)
  * 🎥 [Professor Leonard: Calculus 2 Lecture 9.9 (Approximation of Functions by Taylor Polynomials)](http://www.youtube.com/watch?v=RbreIk02B3c)

### Module 9: Function Limits ($\varepsilon$–$\delta$ & Algebra)

**Practice problem types**

* **Formal limit proofs:** prove $\lim_{x\to a} f(x) = A$ using the strict $\varepsilon$–$\delta$ definition.
  * 🎥 [3Blue1Brown: Limits, L'Hôpital's rule, and epsilon delta definitions (Essence of Calculus Ch. 7)](http://www.youtube.com/watch?v=kfF40MiS7zA)
  * 🎥 [SawFin Mathematics: The Formal Definition of a Limit (1.2.3)](https://www.youtube.com/watch?v=QBxxKXDaRvc)
  * 🎥 [Khan Academy: Epsilon-Delta Definition](https://www.khanacademy.org/math/ap-calculus-ab/ab-limits-new/ab-1-2/v/epsilon-delta-definition-of-limits)
* **Algebraic limit evaluation:** handle $\frac{0}{0}$ and $\frac{\infty}{\infty}$ indeterminate forms for polynomials
  and radicals via factoring and rationalization. L'Hôpital's rule is generally barred at this stage.
  * 🎥 [Professor Leonard: Calculus 1 Lecture 1.2 (Properties of Limits & Techniques of Limit Computation)](https://www.youtube.com/watch?v=VSqOZNULRjQ)
  * 🎥 [Professor Leonard: Calculus 1 Lecture 3.5 (Limits of Functions at Infinity)](http://www.youtube.com/watch?v=-PYebK8DKPc)
  * 🎥 [SawFin Mathematics: Determine Finite Limits at Infinity (3.5.1)](http://www.youtube.com/watch?v=hJ1rhSj6-0I)

**Theoretical grounding**

* Definitions of right/left-sided limits, limits at $\pm\infty$, and continuity.
  * 🎥 [Professor Leonard: Calculus 1 Lecture 1.4 (Continuity of Functions & One-Sided Limits)](https://www.youtube.com/watch?v=OEE5-M4aY4k)

**Required theorems**

* **Theorem 31:** Heine's transference principle for function limits.

### Module 10: Special Analytic Limits

**Practice problem types**

* **Non-L'Hôpital limit solving:** evaluate complex limits by recognizing the foundational forms:

  $$\lim_{x\to 0} \frac{\sin x}{x} = 1, \quad \lim_{x\to 0} \frac{1-\cos x}{x^2} = \frac{1}{2}, \quad \lim_{x\to 0} \frac{e^x-1}{x} = 1$$

**Required theorems**

* **Theorem 32:** formal proofs of the basic limits for trigonometric and exponential functions.
  * 🎥 [Dr. Trefor Bazett: Geometric Proof of sin(x)/x Limit](https://www.youtube.com/watch?v=f2PRu5QPa3o)
  * 🎥 [Professor Leonard: Calculus 1 Lecture 1.2 (Trigonometric Limit Identities & Squeeze Theorem)](https://www.youtube.com/watch?v=VSqOZNULRjQ)

### Module 11: Topology of $\mathbb{R}$, Continuity & Global Theorems

**Theoretical grounding (theory exam focus)**

* Interior points ($\text{int } H$), boundary points ($\partial H$), exterior points ($\text{ext } H$), open sets, and
  closed sets.
  * 🎥 [Dr. Bevin Maultsby: Compact Sets and Open Covers](https://www.youtube.com/watch?v=onRI37DdBu4)
  * 🌐 [The Bright Side of Mathematics: Real Analysis Parts 13 & 14 (Open, Closed, Compact Sets & Heine-Borel)](https://thebrightsideofmathematics.com/courses/real_analysis/overview/)
* Continuity vs. uniform continuity.
  * 🌐 [The Bright Side of Mathematics: Real Analysis Parts 27 & 28 (Continuity & Epsilon-Delta)](https://thebrightsideofmathematics.com/courses/real_analysis/overview/)

**Required theorems**

* **Theorem 33:** characterization of closed sets via convergent sequences.
* **Theorem 34:** Heine-Borel theorem (compact $\iff$ closed and bounded in $\mathbb{R}$).
  * 🎥 [Jason Bramburger: The Heine-Borel Covering Theorem](https://www.youtube.com/watch?v=wZ_cdso7JGM)
* **Theorems 35–37:** Weierstrass extreme value (minimax) theorem and minimal/maximal elements of compact sets.
* **Theorem 38:** $f(x) = \frac{1}{x}$ is continuous on $(0, \infty)$ but *not* uniformly continuous.
* **Theorem 39:** Intermediate Value Theorem (Bolzano's theorem).
  * 🎥 [The Bright Side of Mathematics: Real Analysis 32 (Intermediate Value Theorem)](https://www.youtube.com/watch?v=Pbf8b4DHVCc)

## 4. Cross-Semester Master Matrix

| Course Module                  | Practice Task Focus (Midterms)                                                  | Theoretical Proof Focus (Final Exam)                                               |
|--------------------------------|---------------------------------------------------------------------------------|------------------------------------------------------------------------------------|
| **1. Real Numbers & Inverses** | Find $f^{-1}(y)$, $D_{f^{-1}}$, prove injectivity algebraically                 | Triangle inequalities (Thm 1), completeness axiom, supremum/infimum properties     |
| **2. Function Composition**    | Composite domain $D_{f \circ g}$, simplify $f(g(x))$ under domain conditions    | Practice-only module, no required theorem                                          |
| **3. Limit Proofs**            | Find threshold index $N(\varepsilon)$ or $N(P)$ for sequence limits             | Zero sequences, boundedness links (Thm 2–4)                                        |
| **4. Sequence Algebra**        | Factor dominant terms, radicals, Euler's $e^x$ limits                           | Sandwich theorem, $\sqrt[n]{n} \to 1$, $\frac{x^n}{n!} \to 0$ (Thm 5–12)           |
| **5. Recursion**               | Monotonicity & bounds via mathematical induction                                | Monotone convergence & sequence $e$ (Thm 13–16)                                    |
| **6. Series Sums**             | Exact sums of geometric & telescoping series                                    | Geometric series sum, zero-sequence test (Thm 17–19)                               |
| **7. Series Tests**            | Root, ratio, comparison, Leibniz tests                                          | Comparison tests, Cauchy root test (Thm 20–22)                                     |
| **8. Power Series**            | Find radius $R$, convergence set $S$, analytical expansion                      | Convergence set logic, $\exp$, Euler's identity, $e \notin \mathbb{Q}$ (Thm 23–30) |
| **9. Function Limits**         | Factor algebraic zeros, evaluate rational/radical limits                        | Transference principle (Heine) (Thm 31)                                            |
| **10. Special Limits**         | Analytical forms: $\frac{\sin x}{x}$, $\frac{1-\cos x}{x^2}$, $\frac{e^x-1}{x}$ | Exact proofs of special trigonometric/exponential limits (Thm 32)                  |
| **11. Topology**               | Conceptual questions in Round 1 Canvas quiz                                     | Closed sets, Heine-Borel, Weierstrass minimax, Bolzano's theorem (Thm 33–39)       |

> For a walkthrough of sequence convergence, monotonicity, and limit techniques, watch
> [Professor Leonard's Calculus 2 Lecture 9.1 on Sequences](https://www.youtube.com/watch?v=FoNLQvf4NUs). It covers the
> core sequence mechanics tested in Modules 3, 4, and 5.
