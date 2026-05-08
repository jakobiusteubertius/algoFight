---
name: zoom-out
description: Use when the user asks for a broader explanation of unfamiliar code or architecture; explains local code in the context of the whole system, domain language, and design tradeoffs.
---

# Zoom Out

Use this to orient the user before or during implementation.

## Workflow

1. Read the local code in question.
2. Read nearby callers, callees, tests, and docs.
3. Explain the code at three levels:
   - what this code does locally
   - what role it plays in the wider system
   - what design pressures or tradeoffs shaped it
4. Name important domain terms from `CONTEXT.md` when available.
5. Identify the safest extension points and the riskiest change points.

## Output

Keep the explanation practical. Include file references and concrete examples
when they help the user act.
