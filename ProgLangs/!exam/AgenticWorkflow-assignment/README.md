> - Go to the lab course's assignment, and upload there.
> - The deadline for the assignment is strict and it won't be extended, keep that in mind.
> - The junit6-checkthat-demo.zip file under Teams/Files has been updated. Please use new version instead of the old one.

# Agentic Workflow Assignment (Java 25 + JUnit 6)

> Note that while the topic of this assignment is AI, and some parts allow/require you to use AI, such assistance will be forbidden during the exam.

## Theme

You will build a small simulation of an AI agent workflow.

In this assignment you will not call a real LLM API, as that would be too complex. Instead, you will build a Java model of such a workflow, load it from a text file, and simulate a run. That keeps the focus on object-oriented programming, file I/O, encapsulation, testing, collections, and clean design.

The assignment is split into three parts:

- **Basic task** — mandatory to be allowed to take the exam.
  - For this part, you must create all the code on your own.
- **AI-assisted task** — you may use AI tools, but must document how.
- **Advanced task** — for those with stronger skills and for covering later course topics.
## Scenario

You are implementing a tiny framework for an "agentic workflow".

A workflow file describes an agent and its steps. See sample_agent.txt for such a file.

Your program will read such a file, create Java objects, validate the content, and simulate the execution of the workflow.

No networking and no real AI are required.

## Part 1 — Basic task (mandatory)

Create all the types described in `AgenticWorkflowTestSuite.java` and its referenced structural tests.

The files also tell you most of the details of the code that you have to write. This description focuses on the big picture.

Also create the required test classes and methods according to the description.

You may add helper methods and classes if you want.

For the JUnit testing part, create at least two agent descriptions in files called `agent_DETAILS.txt` (for example, `agent_HotelBooker.txt`) and use them in there.

### General requirements: all classes have to do the following

- Constructors must validate input.
- Do not leak internal mutable data. If you use an array internally, return a copy. If you use a list internally, return an unmodifiable view or a defensive copy.

### About specific details

**StructuredOutput class:** This class represents the expected output of one workflow step.
- store one or more schema types in an array or collection,
- A step may have one schema type such as `INT`, or multiple schema types such as `[STRING, BOOLEAN]`.

**WorkflowStep class:** Represents one step of the agent workflow.
- name should be unique inside one agent,
- prompt and systemPrompt must not be blank.
- `simulateResponse()` does not call AI. It returns a simple placeholder string depending on the schema, for example:
  - `INT` → `"0"`
  - `STRING` → `"sample"`
  - `BOOLEAN` → `"true"`
  - `LIST_INT` → `"[1,2,3]"`

**Agent class:** Represents the whole workflow.
- `loadAgent` loads an agent from a file,
- validate the file format,
- reject duplicate step names,
- allow read-only access to steps,
- provide a `run()` method that simulates the workflow by printing each step and its simulated result.

### About the file format

Use the following format exactly for the basic task:

```
AGENT: <agent name>
STEP
name=<step name>
prompt=<prompt text>
systemPrompt=<system prompt text>
output=<schema type>
ENDSTEP
```

**Notes:**

- first non-empty line must begin with `AGENT:`
- each step begins with `STEP` and ends with `ENDSTEP`
- each step must contain all four properties exactly once
- whitespace around keys and values may be trimmed
- invalid files must throw `WorkflowFormatException`
- You may assume prompt and systemPrompt are single-line in the basic task.

### Basic task deliverables

Submit your Java source code (in the proper folder structure) including JUnit 6 functional tests in a zip file.

The `solution.zip` file is created by `check.cmd` for exactly this purpose.

If you manually create the zip, pay attention to the following:

- Do add all your source files.
- The compressed file's base folder needs to be the root folder of the compilation. Otherwise, the automatic tester will definitely reject your submission.
- Also include txt files used by the tester.
- Any content provided by us (structural tester and suite, docs) are not needed to be included. If they are, they will be ignored.
- `.class` files are also not needed and will be ignored, too.
- You may submit as many times as you wish until the deadline.
- The deadline is strict: don't be late, it won't be extended.

## Part 2 — AI-assisted task

In this part, AI tools are allowed.

The goal is not to let AI do everything, but to practice using it responsibly.

### Your task

Add a utility class such as:

- `WorkflowFileWriter`, or
- `AgentExamples`

This class should create one or more valid workflow files programmatically using `PrintWriter`.

**Example use cases:**

- generate a calculator workflow,
- generate a travel planner workflow,
- generate a study coach workflow.

### AI usage rules

If you use AI assistance for this part, include a short text file named `AI_USAGE.md` containing:

- which tool you used,
- which prompt(s) you asked,
- which code or text you accepted,
- which parts you corrected manually.

### What we will evaluate

- whether you can review AI output instead of copying blindly,
- whether your generated file still follows your own program's format,
- whether your code is readable and tested.

## Part 3 — Advanced task

Choose at least two of the following extensions.

### Option A — More schema flexibility

Extend SchemaType and StructuredOutput so a step can support more complex outputs.

**Examples:**

- list of booleans,
- map of string to int,
- nested list types,
- optional output types.

A simple version is enough. The focus is on design.

### Option B — Collections and validation report

Use List, Set, and Map meaningfully.

**Ideas:**

- `List<WorkflowStep>` for order,
- `Set<String>` to detect duplicates,
- `Map<String, WorkflowStep>` for step lookup,
- validation method returning multiple problems instead of failing at the first one.

### Option C — Interfaces and generics

Introduce an interface such as:

```java
public interface StepExecutor<T> {
    T execute(WorkflowStep step);
}
```

Then create one or more implementations.

Or create a generic parser/formatter class such as:

```java
public interface ValueFormatter<T> {
    String format(T value);
}
```

This part should demonstrate that you understand why interfaces and generics are useful.

### Option D — Better text representation

Override:

- `toString()`
- `equals()`
- `hashCode()`

Do this only where it makes sense.

**For example:**

- two `WorkflowStep` objects may be equal if they have the same logical content,
- `Agent.toString()` may print a readable summary.

### Option E — Richer file format

Support one or more additional properties such as:

- `temperature=<int>`
- `dependsOn=<step name>`
- `retries=<int>`
- `tags=planning,math,safe`

This is a good place to use List, Set, or Map.

## Design expectations

### Encapsulation

A major grading point is not exposing internal mutable state.

**Examples of bad practice:**

- returning the exact internal array,
- storing a caller's array reference directly without copying it,
- public fields.

**Examples of good practice:**

- private fields,
- validating constructor/setter input,
- returning defensive copies or unmodifiable views.

### Memory model discussion

Be prepared to explain orally:

- which objects live on the heap,
- which variables are references,
- what becomes garbage after an Agent or WorkflowStep is no longer reachable,
- why returning a mutable internal array is dangerous.

This assignment is intentionally suitable for those discussions.

### Exception handling

Use exceptions meaningfully.

**Examples:**

- `IllegalArgumentException` for invalid constructor arguments,
- `WorkflowFormatException` for bad file content,
- `IOException` handling when loading files.

You may wrap low-level exceptions if your design justifies it.

## Grading Criterion

### Exam eligibility threshold (bare minimum; must pass)

- compiles,
- basic file loading works,
- uses packages correctly,
- uses classes and enum correctly,
- includes tests,
- shows encapsulation and validation.

### Good solution

- clear code structure,
- good naming,
- meaningful exceptions,
- several passing tests,
- no data leaks,
- correct file I/O.

### Excellent solution

- thoughtful advanced features,
- elegant use of collections/interfaces/generics,
- strong tests,
- good documentation,
- sensible `toString()`/`equals()`/`hashCode()`.

## Notes

- Keep the design simple.
- Do not over-engineer.
- A clean small solution is better than a huge incomplete one.
- Use tests as a development tool, not only as a final checkbox.
- You are building a simulation of an AI workflow, not a real AI system.
