# Android TikTok Spanish Overlay Trainer

## Problem

Adults who want to use TikTok for learning are often pulled back into low-value
For You feeds. Existing screen-time and parental-control products usually block
or limit apps instead of helping users intentionally train recommendation
systems toward useful goals.

For the first product wedge, algoFight helps an adult create and train a fresh
TikTok account into a Spanish-learning feed. The product should guide behavior
inside TikTok without secretly controlling the user's account.

## Goals

- Provide an Android overlay that appears only while TikTok is active.
- Guide the user through a fresh-account Spanish training journey.
- Use Android MediaProjection and on-device OCR to infer whether the current
  TikTok video appears aligned with the Spanish-learning goal.
- Show a simple full-screen frame state:
  - green when the current video appears aligned with the learning goal
  - red when the current video appears off-goal
  - grey when the app is uncertain or has no signal yet
- Keep the overlay lightweight enough that TikTok remains smooth.
- Stop all overlay and analysis behavior when TikTok is no longer foregrounded.
- Track OCR/model frame decisions so they can be compared against user feedback.
- Ask the user for low-friction feedback after every 5 videos:
  - Mostly Spanish
  - Mixed
  - Mostly junk
- Produce a feed alignment history that helps evaluate whether the training
  protocol and classifier are working.

## Non-Goals

- Do not log into TikTok on behalf of the user.
- Do not automate likes, follows, saves, comments, watch time, or account
  creation in the first version.
- Do not attempt to copy or sell TikTok profiles in the MVP.
- Do not support parents, child accounts, or supervision features in the MVP.
- Do not support platforms other than TikTok in the MVP.
- Do not support learning goals beyond Spanish in the first shipped slice,
  though the design should allow more goals later.
- Do not use server-side screen analysis for the MVP; screen understanding
  should happen on-device.

## Requirements

### Onboarding

- The app explains the core promise: train a fresh TikTok account into a
  Spanish-learning feed.
- The app explains required permissions in plain language:
  - overlay permission for the frame
  - MediaProjection permission for screen analysis
  - notification permission for training reminders
- The app guides the user to create or use a fresh TikTok account.
- The app recommends using a dedicated email or alias, but does not create email
  accounts for the user.
- The app makes clear that algoFight guides the user and analyzes visible screen
  signals; it does not secretly operate TikTok.

### Training Journey

- The first Spanish journey includes ordered steps:
  - create or switch to a fresh TikTok account
  - follow recommended Spanish-learning creators
  - search recommended Spanish-learning terms
  - train negative signals by skipping or marking off-goal content as not
    interested
  - complete an initial 10-video training batch
  - continue into the first learning batch
- The overlay frame can guide the current onboarding step with a minimal text
  prompt.
- The app records completion state for each onboarding and training step.
- Initial training batch size defaults to 10 videos.
- User feedback prompt appears after every 5 videos during active sessions.

### Overlay Frame

- The overlay appears as a thin frame over TikTok.
- Frame states:
  - grey: no current classification, uncertain classification, or analysis
    warming up
  - green: current visible content appears aligned with Spanish learning
  - red: current visible content appears off-goal
- The frame may pulse shortly when the state changes, then remain in the latest
  classified color.
- The overlay should avoid covering core TikTok controls as much as practical.
- The overlay disappears when TikTok is no longer the foreground app.

### Screen Analysis

- The app uses Android MediaProjection to sample the screen only while TikTok is
  foregrounded and an active training or learning session is running.
- The app uses on-device OCR, initially Google ML Kit Text Recognition, to read
  visible text signals such as:
  - captions
  - hashtags
  - creator names
  - subtitles or embedded text
- The app analyzes cropped regions where TikTok text commonly appears to reduce
  latency and battery impact.
- The app samples periodically instead of continuously.
- Analysis stops immediately when TikTok is not foregrounded.

### Classification

- The first classifier can be rule-based.
- Green signals include:
  - Spanish words or phrases
  - Spanish-learning keywords
  - hashtags such as learn Spanish, Spanish lesson, Spanish beginner, or
    equivalent Spanish-language tags
  - known recommended creator names
  - visible subtitles or captions that appear to be Spanish
- Red signals include:
  - content with no detected Spanish or language-learning signal after OCR has
    enough readable text
  - known off-goal hashtags or repeated distraction topics
- Grey is used when OCR confidence is low, visible text is missing, or the
  classifier is uncertain.
- The classifier should store both the final color and the reason signals used
  for that decision.

### Feedback And Analytics

- After every 5 videos, the user is asked for one feedback choice:
  - Mostly Spanish
  - Mixed
  - Mostly junk
- The app stores the frame classifications shown during that 5-video batch.
- The app compares user feedback against the frame classification distribution.
- The app shows a simple feed alignment trend over time.
- The app exposes enough local analytics to answer:
  - Is the TikTok feed becoming more Spanish-aligned?
  - Is the classifier over-marking content green?
  - Is the classifier over-marking content red?
  - Which training steps correlate with improvement?

### Notifications

- The app can send reminders for short daily training sessions.
- Notification copy should frame the action as self-empowerment and learning,
  not shame.
- Notifications should open directly into the next training or learning step.

### Privacy And Trust

- The app should clearly state when screen analysis is active.
- Screen analysis should only run while TikTok is foregrounded and the user has
  started or resumed an algoFight session.
- OCR/classification should happen on-device for the MVP.
- Store the minimum data needed for progress and classifier evaluation.
- Do not capture, store, or transmit raw screenshots in the MVP unless the user
  explicitly opts into a later diagnostic mode.

## Edge Cases

- User denies overlay permission.
- User denies MediaProjection permission.
- User denies notification permission.
- TikTok UI changes and cropped OCR regions become unreliable.
- TikTok is opened in split-screen, picture-in-picture, or unusual display
  modes.
- OCR finds no readable text for several videos in a row.
- The current video is Spanish but visual-only, so the classifier marks grey or
  red.
- The current video contains Spanish text but is not actually useful for
  learning.
- User watches TikTok outside an active algoFight session.
- User switches away from TikTok during a session.
- User feedback contradicts frame classifications.
- Fresh account setup is skipped and the user trains an existing noisy account.

## Affected Areas

- Android app shell and navigation.
- Permission onboarding for overlay, MediaProjection, and notifications.
- Foreground app detection for TikTok-only activation.
- Overlay frame service.
- MediaProjection capture pipeline.
- On-device OCR pipeline using Google ML Kit Text Recognition.
- Rule-based Spanish-learning classifier.
- Training journey state machine.
- Batch feedback flow.
- Local analytics and progress storage.
- Notification scheduling.
- Privacy copy and user-facing consent screens.

## Feedback Loops

- Run Android unit tests for classifier rules and scoring logic.
- Run instrumentation tests for onboarding and training journey state changes.
- Manually verify on Android device:
  - overlay appears on TikTok
  - overlay disappears outside TikTok
  - analysis stops outside TikTok
  - grey/green/red frame states update without noticeable lag
  - 5-video feedback prompt appears at the expected cadence
- Measure OCR latency and battery impact during a 10-video session.
- Compare classifier output against user batch feedback across test sessions.
- Track day 1 and day 7 feed alignment for early testers.

## Open Questions

- What exact Android minimum version should the MVP support?
- Should TikTok foreground detection rely on usage stats, accessibility events,
  or another mechanism?
- What sampling interval gives the best balance between accuracy, battery, and
  smoothness?
- Which screen regions should be cropped first for TikTok OCR?
- How many creators and search terms are needed for the initial Spanish pack?
- Should the first release support only fresh accounts, or allow an explicit
  "train existing account" path with weaker expectations?
- How should the app count videos without direct TikTok integration?
- What is the first threshold for "feed trained" after user feedback and OCR
  scoring are combined?
