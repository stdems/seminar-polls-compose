package com.example.seminarcompose

data class Poll(
    val identifier: Int,
    val question: String,
    val firstAnswer: String,
    val secondAnswer: String,
    val firstVotes: Int = 0,
    val secondVotes: Int = 0,
    val isFinished: Boolean = false,
    val hasVoted: Boolean = false
) {
    fun vote(answer: Int): Poll {
        if (isFinished || hasVoted) {
            return this
        }
        return when (answer) {
            1 -> copy(firstVotes = firstVotes + 1, hasVoted = true)
            2 -> copy(secondVotes = secondVotes + 1, hasVoted = true)
            else -> this
        }
    }

    fun nextParticipant(): Poll {
        if (isFinished) {
            return this
        }
        return copy(hasVoted = false)
    }

    fun finish(): Poll {
        return copy(isFinished = true)
    }
}
