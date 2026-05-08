---
name: diagnose
description: Use for hard bugs, flaky behavior, regressions, and performance problems; follows a disciplined loop of reproduce, minimize, hypothesize, instrument, fix, and regression-test.
---

# Diagnose

Use this when the cause is unknown.

## Workflow

1. Reproduce the problem with the smallest command, test, route, or scenario.
2. Minimize the reproduction until irrelevant moving parts are gone.
3. State the strongest current hypothesis and what evidence would falsify it.
4. Instrument surgically with logs, assertions, traces, or focused tests.
5. Fix the narrowest confirmed cause.
6. Add a regression test or durable check.
7. Remove temporary instrumentation.
8. Run the relevant feedback loop and report results.

## Guardrails

- Do not patch around symptoms before proving the cause.
- Prefer evidence from the running system over code reading alone.
- Keep a short hypothesis log in the conversation for complex investigations.
