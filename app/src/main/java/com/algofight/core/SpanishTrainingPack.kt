package com.algofight.core

object SpanishTrainingPack {
    fun guidanceFor(step: SpanishTrainingStep): List<String> =
        when (step) {
            SpanishTrainingStep.FreshAccount -> listOf(
                "Use this account only for Spanish learning during training.",
                "A fresh account gives TikTok a cleaner signal than repairing an old feed.",
                "Avoid random entertainment on this account until the feed is stable.",
            )

            SpanishTrainingStep.FollowCreators -> listOf(
                "Spanish With Vicente",
                "Spanish After Hours",
                "Easy Spanish",
                "Butterfly Spanish",
                "The Spanish Dude",
                "Speak Spanish Faster",
            )

            SpanishTrainingStep.SearchTerms -> listOf(
                "learn Spanish beginner",
                "Spanish A1 conversation",
                "Spanish listening practice",
                "Spanish phrases for beginners",
                "Spanish pronunciation practice",
                "Spanish travel phrases",
            )

            SpanishTrainingStep.ExcludeContent -> listOf(
                "Skip off-goal videos quickly so they do not become strong signals.",
                "Long press repeated junk and choose Not interested.",
                "Do not like, save, comment on, or finish random entertainment videos.",
            )

            SpanishTrainingStep.InitialTrainingBatch -> listOf(
                "Watch 10 videos with the overlay active.",
                "Green frame: stay, watch longer, like or save if useful.",
                "Red frame: skip quickly or mark Not interested if the topic repeats.",
                "After every 5 videos, rate the batch in algoFight.",
            )

            SpanishTrainingStep.FirstLearningBatch -> listOf(
                "Keep this account focused on Spanish while the feed is still learning.",
                "Save lessons you would actually revisit.",
                "If the feed drifts, run another 5-video check and correct the signal.",
            )
        }
}
