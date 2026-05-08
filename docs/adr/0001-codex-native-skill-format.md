# ADR 0001: Use Codex-Native Skill Format

## Status

Accepted

## Context

The source inspiration uses Claude-oriented command and plugin conventions.
Codex skills are triggered from skill metadata and loaded through `SKILL.md`
files, with optional bundled resources.

## Decision

This repository uses Codex-native skill folders:

- `skills/<skill-name>/SKILL.md` is required.
- `agents/openai.yaml` is included for human-facing metadata.
- Optional `scripts/`, `references/`, and `assets/` folders are added only when
  a skill needs them.

## Consequences

The repository is immediately understandable to Codex and avoids carrying
Claude-specific command files that would not execute in this environment.
