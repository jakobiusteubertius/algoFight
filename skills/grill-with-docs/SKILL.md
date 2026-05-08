---
name: grill-with-docs
description: Use before substantial coding or product work to challenge the plan, expose ambiguity, align on domain language, and update CONTEXT.md or ADRs with decisions discovered during the conversation.
---

# Grill With Docs

Use this when the user has an idea, plan, feature, refactor, or bug fix that is
not yet crisp enough to implement confidently.

## Workflow

1. Read the relevant repo context first:
   - `AGENTS.md`
   - `CONTEXT.md`
   - relevant ADRs in `docs/adr/`
   - files or tests likely to be touched
2. Ask focused questions until the implementation boundaries are clear.
3. Challenge assumptions against the existing domain model and architecture.
4. Identify new or changed shared language.
5. If the user confirms a decision, update:
   - `CONTEXT.md` for vocabulary and domain concepts
   - a new ADR for durable architectural decisions
6. Summarize the decided plan in implementation-sized steps.

## Question Style

- Prefer a few high-leverage questions over a long questionnaire.
- Ask about constraints, failure modes, success criteria, and affected modules.
- Stop grilling once the next engineering step is clear.

## Documentation Rules

- Do not document speculation as fact.
- Keep `CONTEXT.md` concise.
- Use ADRs only for decisions with future consequences.
