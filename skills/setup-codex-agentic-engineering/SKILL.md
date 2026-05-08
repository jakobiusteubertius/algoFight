---
name: setup-codex-agentic-engineering
description: Use when setting up a repository to work with the Codex agentic engineering skills; creates or updates project configuration, issue-tracker preferences, shared language docs, and ADR locations consumed by the other skills.
---

# Setup Codex Agentic Engineering

Use this once per target repository before relying on the other workflows.

## Workflow

1. Inspect the repo root for `AGENTS.md`, `CONTEXT.md`, `docs/adr/`, package
   scripts, test commands, and issue-tracker configuration.
2. Identify the issue tracker: GitHub, Linear, local files, or none.
3. Identify the core feedback loops:
   - typecheck
   - lint
   - unit tests
   - integration or browser checks
   - build
4. Create missing docs only when useful:
   - `CONTEXT.md` for shared language
   - `docs/adr/` for decisions
   - repo-level `AGENTS.md` if the project lacks Codex operating notes
5. Record commands and conventions in `AGENTS.md`, not in the skill.
6. End by telling the user which workflows are now ready and which feedback
   loops are still unknown.

## Defaults

- Use GitHub issues when the repo already has a GitHub remote.
- Use `docs/adr/` for ADRs unless the repo already has another ADR location.
- Keep setup edits minimal; do not reorganize the project.
