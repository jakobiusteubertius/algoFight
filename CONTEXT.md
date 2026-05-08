# Shared Language

## Terms

- **Skill** - A self-contained Codex workflow folder with a required
  `SKILL.md`.
- **Progressive disclosure** - Keeping the trigger metadata small, the skill
  body focused, and optional references loaded only when needed.
- **Vertical slice** - A small issue that delivers behavior across the relevant
  stack, rather than a horizontal technical layer.
- **Feedback loop** - A command, test, typecheck, browser check, or review step
  that tells Codex whether the change works.
- **ADR** - A short architecture decision record stored in `docs/adr/`.

## Repository Intent

algoFight is a Codex-oriented home for agentic engineering skills. It favors
the formats Codex already understands: `AGENTS.md` for repo behavior,
`SKILL.md` for skill execution, and small supporting scripts or references.
