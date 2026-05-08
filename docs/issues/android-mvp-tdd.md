# Android MVP TDD Issue Plan

These issues slice the Android TikTok Spanish overlay trainer into small,
testable increments. Each issue should start with the narrowest practical test
for its user-visible behavior, then grow the implementation only enough to pass.

## Issue 1: Classify Spanish TikTok OCR Signals

## Problem

The overlay needs a first-pass decision for whether visible TikTok text appears
aligned with Spanish learning before any Android overlay work can be useful.

## Scope

- Add a rule-based Spanish-learning classifier.
- Accept OCR text and optional known creator names as input.
- Return a frame color: green, red, or grey.
- Return reason signals used for the decision.

## Acceptance Criteria

- Spanish-learning hashtags, Spanish phrases, and known creators classify green.
- Empty or low-signal OCR text classifies grey.
- Clearly off-goal text classifies red only when there is enough readable text.
- Classifier decisions include reason codes suitable for analytics.

## Likely Touched Areas

- Android app module scaffold.
- Core classifier domain code.
- Unit tests.

## Validation

- `./gradlew testDebugUnitTest`

## Issue 2: Track Five-Video Batch Feedback Against Frame Decisions

## Problem

algoFight needs to compare model decisions with user feedback after each
5-video batch to learn whether the frame color is trustworthy.

## Scope

- Record frame decisions during a batch.
- Accept user feedback: Mostly Spanish, Mixed, Mostly junk.
- Compute batch alignment and disagreement metrics.
- Preserve enough data to inspect whether green/red decisions match feedback.

## Acceptance Criteria

- A 5-decision batch can be summarized into green/red/grey counts.
- Mostly Spanish feedback agrees with mostly-green model batches.
- Mostly junk feedback agrees with mostly-red model batches.
- Mixed feedback is treated as lower confidence rather than a hard model
  failure.

## Likely Touched Areas

- Analytics domain code.
- Local persistence model if needed.
- Unit tests.

## Validation

- `./gradlew testDebugUnitTest`

## Issue 3: Build The Spanish Training Journey State Machine

## Problem

The overlay must guide the user through ordered onboarding and training steps
instead of showing generic prompts.

## Scope

- Model the Spanish journey steps:
  - fresh TikTok account
  - follow creators
  - search terms
  - exclude content
  - 10-video training batch
  - first learning batch
- Persist completion state.
- Expose the current step prompt for the overlay.

## Acceptance Criteria

- The next step advances only when the current step is completed.
- Completed steps remain completed after app restart.
- The current overlay prompt matches the journey step.
- The first training batch defaults to 10 videos.

## Likely Touched Areas

- Journey domain code.
- Local storage.
- App navigation/onboarding UI.
- Unit and instrumentation tests.

## Validation

- `./gradlew testDebugUnitTest`
- `./gradlew connectedDebugAndroidTest`

## Issue 4: Show A TikTok-Only Overlay Frame

## Problem

The user needs a visible coach inside TikTok, but the app must disappear outside
TikTok and stop doing work.

## Scope

- Request and explain overlay permission.
- Render a thin frame overlay.
- Start overlay only when TikTok is foregrounded and a session is active.
- Hide overlay when TikTok is not foregrounded.

## Acceptance Criteria

- User can grant overlay permission from onboarding.
- The frame is visible over TikTok during an active session.
- The frame is not visible outside TikTok.
- The overlay can render grey, green, and red states.

## Likely Touched Areas

- Android permissions.
- Foreground app detection.
- Overlay service.
- Manual device verification.

## Validation

- `./gradlew testDebugUnitTest`
- Manual Android device check with TikTok installed.

## Issue 5: Sample TikTok Screen Text With MediaProjection And ML Kit

## Problem

The frame needs OCR signals from the current TikTok screen without slowing down
the TikTok experience.

## Scope

- Request and explain MediaProjection permission.
- Capture screen samples only while TikTok is foregrounded and a session is
  active.
- Crop TikTok text regions before OCR.
- Run Google ML Kit Text Recognition on-device.
- Stop capture immediately outside TikTok.

## Acceptance Criteria

- Screen analysis starts only after explicit user consent.
- OCR extracts visible caption, hashtag, creator, or subtitle text from TikTok.
- Capture and OCR stop when TikTok is no longer foregrounded.
- Raw screenshots are not persisted.

## Likely Touched Areas

- MediaProjection service.
- OCR adapter.
- Classifier integration.
- Privacy copy.
- Device performance checks.

## Validation

- `./gradlew testDebugUnitTest`
- Manual 10-video TikTok session.
- Measure OCR latency and visible TikTok smoothness.

## Issue 6: Connect OCR Decisions To The Live Frame And Batch Prompt

## Problem

Users need the frame color to update from OCR/classifier decisions and then give
simple feedback after every 5 videos.

## Scope

- Feed OCR text into the classifier.
- Update frame grey/green/red state.
- Store frame decisions for the active batch.
- Prompt user after every 5 videos.
- Store the user's 3-option feedback.

## Acceptance Criteria

- The frame changes color after classifier decisions.
- State changes may pulse briefly, then settle on the latest color.
- After 5 counted videos, user sees Mostly Spanish, Mixed, Mostly junk.
- Feedback is stored with the model decisions for that batch.

## Likely Touched Areas

- Overlay frame state.
- Classifier integration.
- Batch analytics.
- Feedback UI.
- Instrumentation and manual tests.

## Validation

- `./gradlew testDebugUnitTest`
- `./gradlew connectedDebugAndroidTest`
- Manual TikTok session.

## Issue 7: Add Daily Training Reminders And Progress

## Problem

The product needs a habit loop that brings the user back for short training
sessions and shows whether the feed is improving.

## Scope

- Request notification permission.
- Schedule daily training reminders.
- Open the next training step from notifications.
- Show a simple feed alignment trend from stored batches.

## Acceptance Criteria

- User can opt into reminders.
- Reminder opens the next unfinished training or learning step.
- Progress shows recent batch feedback and model alignment.
- Copy frames the session as learning/self-empowerment.

## Likely Touched Areas

- Notification scheduling.
- Progress screen.
- Local analytics storage.
- UI tests where practical.

## Validation

- `./gradlew testDebugUnitTest`
- Manual notification check on Android device.

## Issue 8: Add Spanish Training Pack Guidance

## Problem

The app currently has the technical overlay/OCR shell and a generic training
journey, but it does not yet tell the user what to do in TikTok to train a
Spanish-learning feed.

## Scope

- Add concrete guidance for each Spanish journey step.
- Show recommended Spanish-learning creators.
- Show recommended TikTok search terms.
- Explain how to handle off-goal content.
- Explain what the first 10-video training batch should do.
- Add an Open TikTok action target where practical.

## Acceptance Criteria

- The fresh-account step explains why a fresh TikTok account helps and gives a
  concise setup instruction.
- The follow-creators step lists recommended Spanish-learning creators.
- The search-terms step lists recommended search phrases.
- The exclude-content step explains skip and Not interested behavior.
- The initial-batch step explains green/red frame behavior during the first
  10 videos.
- The first-learning-batch step tells the user how to continue learning without
  polluting the feed.
- Presenter tests cover the concrete guidance for at least creators, search
  terms, and exclusion behavior.

## Likely Touched Areas

- Spanish training journey domain content.
- Journey presenter and view state.
- Main activity rendering.
- Unit tests.

## Validation

- `./gradlew testDebugUnitTest`
- `./gradlew assembleDebug`
- Emulator smoke check of the guidance screen.
