package com.algofight.core

enum class SpanishTrainingStep(
    val prompt: String,
) {
    FreshAccount("Create or switch to a fresh TikTok account for Spanish learning."),
    FollowCreators("Follow the recommended Spanish-learning creators."),
    SearchTerms("Search the recommended Spanish beginner terms."),
    ExcludeContent("Skip or mark off-goal content as not interested."),
    InitialTrainingBatch("Complete your first 10-video training batch."),
    FirstLearningBatch("Start your first Spanish learning batch."),
}
