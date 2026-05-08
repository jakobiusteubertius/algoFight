---
name: write-a-skill
description: Use when creating or revising Codex skills; applies Codex skill anatomy, progressive disclosure, concise frontmatter, optional agents metadata, and bundled resources only when useful.
---

# Write A Skill

Use this to create or improve a Codex skill.

## Workflow

1. Clarify the job the skill should make easier.
2. Decide the right degree of freedom:
   - prose workflow for judgment-heavy work
   - scripts for deterministic repeated work
   - references for large domain details
3. Create the minimal structure:
   - `skill-name/SKILL.md`
   - optional `agents/openai.yaml`
   - optional `scripts/`, `references/`, or `assets/`
4. Write frontmatter:
   - `name`
   - `description` that clearly says when the skill should trigger
5. Keep the body focused on execution, not marketing or tutorial content.
6. Validate that optional resources are directly referenced from `SKILL.md`.

## Skill Skeleton

```md
---
name: example-skill
description: Use when ...
---

# Example Skill

## Workflow

1. ...
```

## Avoid

- Extra READMEs inside individual skill folders.
- Long background essays.
- Duplicating large reference content in `SKILL.md`.
- Creating scripts when a short workflow is enough.
