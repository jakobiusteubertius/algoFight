# algoFight

Codex-native skills for serious agentic software engineering.

This repository adapts the small, composable engineering workflow pattern from
`mattpocock/skills` for Codex. It keeps the same spirit: preserve developer
control, make alignment explicit, keep feedback loops tight, and let project
knowledge accumulate in lightweight docs instead of being rediscovered every
session.

## What This Repo Contains

- `skills/` - Codex skill folders, each with a required `SKILL.md`.
- `.codex-plugin/plugin.json` - A plugin manifest for bundling the skills.
- `AGENTS.md` - Repo-level operating instructions for Codex.
- `CONTEXT.md` - Shared language and project memory.
- `docs/adr/` - Architecture decision records.
- `scripts/` - Small helper scripts used by the skills.

## Skills

### Engineering

- `setup-codex-agentic-engineering` - Configure a repo for these workflows.
- `grill-with-docs` - Interrogate a plan and update project docs inline.
- `tdd` - Build features or fixes with a red-green-refactor loop.
- `diagnose` - Debug with reproduce, minimize, hypothesize, instrument, fix,
  and regression-test.
- `triage` - Sort issues into a clear state machine and next action.
- `prototype` - Build throwaway prototypes to test design uncertainty quickly.
- `zoom-out` - Explain local code in the context of the wider system.
- `to-prd` - Turn conversation context into a concise PRD.
- `to-issues` - Break a PRD or plan into vertical-slice issues.
- `improve-codebase-architecture` - Find design improvements grounded in the
  project domain and ADRs.

### Productivity

- `grill-me` - A non-code grilling session for plans, product ideas, and
  decisions.
- `caveman` - Ultra-compressed technical communication for token-sensitive
  work.
- `write-a-skill` - Create or revise Codex skills using progressive disclosure.

## Installing Locally

Copy or symlink the skill folders into your Codex skills directory, or install
this repository as a plugin once your Codex environment supports local plugin
loading.

For a manual local install:

```sh
./scripts/install-local.sh ~/.codex/skills
```

After install, ask Codex to use one of the skills by name, for example:

```text
Use grill-with-docs on this feature idea.
Use tdd to implement this bug fix.
Use diagnose on this failing test.
```

## Operating Pattern

1. Use `setup-codex-agentic-engineering` once per target repo.
2. Use `grill-with-docs` before substantial work to reduce misalignment.
3. Use `to-prd` and `to-issues` when the task needs planning and tracking.
4. Use `triage` to keep issue queues actionable.
5. Use `tdd`, `diagnose`, or `prototype` during implementation.
6. Use `zoom-out` when the local change is hard to understand in isolation.
7. Use `improve-codebase-architecture` periodically to keep the codebase easy
   to change.

The point is not to make Codex follow a giant process. The point is to give it
small, sharp rituals that match how experienced engineers already work.
