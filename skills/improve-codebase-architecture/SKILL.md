---
name: improve-codebase-architecture
description: Use when evaluating or improving codebase architecture; finds design deepening opportunities grounded in domain language, existing modules, tests, and ADRs.
---

# Improve Codebase Architecture

Use this when the user wants to make the system easier to change, not merely
prettier.

## Workflow

1. Read `CONTEXT.md`, relevant ADRs, module boundaries, and recent tests.
2. Identify architecture pressure:
   - duplicated domain rules
   - shallow modules with leaky internals
   - unclear ownership
   - tangled dependencies
   - missing feedback loops around critical behavior
3. Pick improvements that deepen modules or clarify boundaries.
4. Prefer one small architecture move with tests over a broad rewrite.
5. Document durable decisions in ADRs.
6. Run affected tests or explain why validation is not available.

## Output

Lead with actionable findings. For each improvement, include:

- current friction
- proposed change
- why it improves future work
- risk and validation

Only implement when the user asks for changes or the task clearly includes
implementation.
