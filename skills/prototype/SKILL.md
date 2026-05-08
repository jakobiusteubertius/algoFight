---
name: prototype
description: Use when building a disposable prototype to resolve uncertainty about state, business logic, interaction design, or UI direction before committing production code.
---

# Prototype

Use this when the team needs to learn quickly.

## Workflow

1. State the uncertainty the prototype should resolve.
2. Choose the cheapest prototype shape:
   - terminal app for state machines or business logic
   - temporary route for UI or interaction alternatives
   - isolated script for data transforms or APIs
3. Keep production code untouched unless the user explicitly wants otherwise.
4. Build the smallest runnable version.
5. Make alternatives easy to compare when exploring UI direction.
6. Run the prototype and record what was learned.
7. Recommend whether to discard, evolve, or translate the prototype into
   production work.

## Guardrails

- Mark throwaway code clearly.
- Do not silently ship prototype code into core paths.
- Optimize for learning speed, not polish.
