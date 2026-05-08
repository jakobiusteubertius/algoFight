# Codex Operating Guide

This repository contains Codex skills for agentic software engineering.

When editing this repo:

- Keep skills concise and action-oriented.
- Put trigger-critical details in `SKILL.md` frontmatter descriptions.
- Put long examples, schemas, and references in `references/` files only when a
  skill genuinely needs them.
- Prefer small scripts for deterministic repeated work.
- Do not add broad process documents unless they directly help Codex execute a
  workflow.
- Validate skill changes by checking that each `SKILL.md` has valid
  frontmatter and a clear workflow.
- For Android work, use the repo Gradle wrapper. This machine has a user-local
  JDK at `/Users/jt/Library/Java/JavaVirtualMachines/jdk-21.0.11+10/Contents/Home`
  and Android SDK at `/Users/jt/Library/Android/sdk`.
- Core Android feedback loop: `./gradlew testDebugUnitTest`.
- APK smoke feedback loop: `./gradlew assembleDebug`.

When using these skills in a target codebase:

- Read the target repo before prescribing a process.
- Preserve user changes.
- Prefer existing tests, package scripts, and local conventions.
- Make feedback loops explicit before taking large implementation steps.
