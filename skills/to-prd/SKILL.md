---
name: to-prd
description: Use when turning the current conversation, plan, or feature idea into a concise product requirements document suitable for implementation planning or issue creation.
---

# To PRD

Use this after enough context exists to write a useful PRD. Do not run a full
interview unless the user asks; synthesize what is already known and call out
gaps.

## Workflow

1. Read relevant `CONTEXT.md`, ADRs, and touched modules when available.
2. Draft a PRD with:
   - problem
   - goals
   - non-goals
   - users or actors
   - required behavior
   - edge cases
   - affected modules
   - feedback loops
   - open questions
3. If the user wants GitHub tracking, create or prepare a GitHub issue body.
4. Keep the PRD implementation-ready but not over-specified.

## Output Shape

Use this structure:

```md
# <Feature or Change Name>

## Problem
## Goals
## Non-Goals
## Requirements
## Edge Cases
## Affected Areas
## Feedback Loops
## Open Questions
```
