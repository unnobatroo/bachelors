# readme_2 - Survival Guide for Agentic Workflow Assignment

This guide is the practical version of `README.md` for your current repository state.

You currently have mostly empty implementation files, and the suite expects both:
- real implementation code, and
- your own functional tests.

Use this file as a TODO map from zero to done.

## 1) What this assignment wants (in plain words)

You are building a mini "AI workflow simulator".

A text file describes an agent and steps.
Your Java code must:
1. parse that file,
2. validate it,
3. build objects (`Agent`, `WorkflowStep`, etc.),
4. simulate output strings (fake AI responses),
5. pass structural + functional tests.

No real AI API, no network.

## 2) Exact classes you must implement

Based on the provided structural tests, implement these types under the existing packages.

### A) `agentic.workflow.llm.SchemaType`
Create enum values exactly:
- `INT`
- `STRING`
- `BOOLEAN`
- `LIST_INT`
- `LIST_STRING`
- `MAP_STRING_STRING`

### B) `agentic.workflow.llm.StructuredOutput`
Field:
- private `SchemaType[] schemaTypes` (encapsulated)

Required behavior:
- Constructor takes one or more schema types.
- Throw `IllegalArgumentException` if no types.
- Throw `NullPointerException` if any provided element is null.
- Defensive copy in constructor.
- Getter returns defensive copy.
- `boolean contains(SchemaType schemaType)`
- `int size()`

### C) `agentic.workflow.WorkflowStep`
Fields (private):
- `String name`
- `String prompt`
- `String systemPrompt`
- `StructuredOutput structuredOutput`

Required behavior:
- Constructor validates:
  - `name`, `prompt`, `systemPrompt` must be non-null and not blank.
  - `structuredOutput` must be non-null.
  - invalid -> `IllegalArgumentException`.
- Getters and setters expected by structure tests.
- `boolean expectsStructuredOutput()`
  - true when output has at least one schema type.
- `String simulateResponse()`
  - based on first schema type:
  - `INT` -> `"0"`
  - `STRING` -> `"sample"`
  - `BOOLEAN` -> `"true"`
  - `LIST_INT` -> `"[1,2,3]"`
  - `LIST_STRING` -> `"[\"a\",\"b\"]"`
  - `MAP_STRING_STRING` -> `"{\"key\":\"value\"}"`

### D) `agentic.workflow.WorkflowFormatException`
Make it a checked exception:
- extend `Exception`

Constructors:
- `(String message)`
- `(String message, Throwable cause)`

### E) `agentic.workflow.Agent`
Fields (private):
- `String name`
- `List<WorkflowStep> steps`

Required behavior:
- Constructor `Agent(String name)` validates non-null/non-blank.
- `void addStep(WorkflowStep step)`
  - reject null
  - reject duplicate step names
- `int getStepCount()`
- `WorkflowStep findStepByName(String stepName)`
  - validate `stepName` non-null/non-blank
  - compare with trimmed search text
  - return null if missing
- `void run()`
  - print each step name + simulated response in insertion order
- `static Agent loadAgent(String filename)`
  - validate filename non-null/non-blank
  - read file and parse format
  - throws `IllegalArgumentException`, `WorkflowFormatException`, `IOException`
- private static `WorkflowStep parseStep(BufferedReader reader)`
  - parse until `ENDSTEP`
  - require properties exactly once: `name`, `prompt`, `systemPrompt`, `output`
  - reject unknown keys, bad lines, duplicates, missing fields
  - `output` must map to `SchemaType`

Encapsulation rule for `steps`:
- internal list must not be directly modifiable from outside.
- getter should return unmodifiable view or defensive copy.

## 3) File format you must support (basic task)

The parser must accept this structure:

```text
AGENT: <agent name>
STEP
name=<step name>
prompt=<prompt text>
systemPrompt=<system prompt text>
output=<schema type>
ENDSTEP
```

Validation rules:
- First non-empty line must start with `AGENT:`.
- Every `STEP` must end with `ENDSTEP`.
- Each step includes all 4 required properties exactly once.
- Trim around keys and values.
- Duplicate step names are invalid.
- Invalid format -> throw `WorkflowFormatException`.

## 4) Functional tests you still need to add

Your suite references these test classes, but they are currently missing:
- `agentic/workflow/AgentTest.java`
- `agentic/workflow/WorkflowStepTest.java`
- `agentic/workflow/WorkflowFormatExceptionTest.java`
- `agentic/workflow/llm/StructuredOutputTest.java`

Minimum good coverage:
- constructor validation tests (valid + invalid)
- duplicate step rejection
- successful and failing file parsing
- `simulateResponse` mapping
- encapsulation checks (copy/unmodifiable behavior)

Also create at least two workflow text files for tests as required in the original README, e.g.:
- `agent_HotelBooker.txt`
- `agent_StudyCoach.txt`

## 5) Recommended coding order (fastest path)

1. `SchemaType` enum
2. `StructuredOutput`
3. `WorkflowFormatException`
4. `WorkflowStep`
5. `Agent` core methods (without parser)
6. `Agent.loadAgent` + `parseStep`
7. functional test classes
8. test workflow `.txt` files
9. run full suite repeatedly

This order minimizes dependency pain.

## 6) How to run checks here

From project root:

```bash
chmod +x check.cmd
./check.cmd AgenticWorkflowTestSuite.java AgenticWorkflowTestSuite
```

If you want only one structure test:

```bash
./check.cmd agentic/workflow/AgentStructureTest.java agentic.workflow.AgentStructureTest
```

If you want one functional test (after you create it):

```bash
./check.cmd agentic/workflow/AgentTest.java agentic.workflow.AgentTest
```

## 7) Common mistakes that will burn points

- Returning internal mutable array/list directly (data leak).
- Forgetting to trim strings before blank checks.
- Not checking duplicate properties in one step.
- Allowing duplicate step names in `Agent`.
- Throwing wrong exception type (`IllegalArgumentException` vs `WorkflowFormatException`).
- Missing one of the required test classes referenced by suite.

## 8) Reality check for your current repo

Current implementation status is basically starter skeleton:
- `Agent.java` is empty.
- `WorkflowStep.java` is empty.
- `WorkflowFormatException.java` is empty.
- `SchemaType.java` is empty.
- `StructuredOutput.java` is empty shell.
- functional test classes are missing.

So yes: this is normal "start from scratch" territory.

## 9) Definition of done (exam-eligibility baseline)

You are in good shape when all of these are true:
- project compiles,
- structural tests pass,
- your functional tests compile and pass,
- parser handles good and bad files correctly,
- encapsulation is safe,
- `check.cmd` can build `solution.zip` cleanly.

## 10) If you get stuck

Debug in this order:
1. make one class compile,
2. run only that class test,
3. fix one failure at a time,
4. only then run full suite.

Do not try to solve the whole project in one giant edit.

---

If you want, next step I can implement all mandatory Part 1 classes + test skeletons directly in this repository in one pass.