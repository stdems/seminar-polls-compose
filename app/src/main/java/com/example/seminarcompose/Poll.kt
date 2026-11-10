package com.example.seminarcompose

data class Poll(
    val identifier: Int,
    val question: String,
    val firstAnswer: String,
    val secondAnswer: String,
    val firstVotes: Int = 0,
    val secondVotes: Int = 0
)
