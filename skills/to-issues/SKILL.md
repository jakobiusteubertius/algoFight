---
name: to-issues
description: Use when breaking a PRD, plan, or large feature into small independently implementable issues, preferably vertical slices with clear acceptance criteria.
---

# To Issues

Use this when a plan is too large to implement safely in one pass.

## Workflow

1. Read the plan, PRD, or conversation context.
2. Identify the smallest vertical slices that deliver observable behavior.
3. Order slices by dependency and feedback value.
4. For each issue, include:
   - title
   - problem
   - scope
   - acceptance criteria
   - likely touched areas
   - validation commands or checks
5. Avoid issues that are only "create database layer" or "add UI components"
   unless they independently reduce risk.
6. If using GitHub, prepare issue bodies or create issues when the user has
   explicitly asked for that write action.

## Issue Template

```md
## Problem

## Scope

## Acceptance Criteria

## Likely Touched Areas

## Validation
```
