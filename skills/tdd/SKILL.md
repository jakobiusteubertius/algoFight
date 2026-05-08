---
name: tdd
description: Use when implementing a feature or bug fix with test-driven development; follows a red-green-refactor loop using the repository's existing test framework and feedback commands.
---

# TDD

Use this for behavior changes that can be protected with automated tests.

## Workflow

1. Inspect existing tests and package scripts.
2. Define the smallest user-visible behavior slice.
3. Write or update one failing test for that behavior.
4. Run the narrowest relevant test command and confirm the failure is for the
   intended reason.
5. Implement the minimum code needed to pass.
6. Run the narrow test again.
7. Refactor while keeping tests green.
8. Run broader feedback commands when the change touches shared behavior.
9. Report the test commands and outcomes.

## Test Quality

- Prefer behavior tests over implementation-detail tests.
- Avoid broad snapshots unless the repo already relies on them.
- Add regression tests for bugs.
- Keep fixtures small and named after domain concepts.

## When TDD Does Not Fit

If the repo has no practical test harness, say so, then create the best
available feedback loop before changing production code.
