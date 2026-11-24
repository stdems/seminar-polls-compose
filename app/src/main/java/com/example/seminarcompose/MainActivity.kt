package com.example.seminarcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize().safeDrawingPadding().padding(16.dp)
                ) {
                    PollApp()
                }
            }
        }
    }
}

@Composable
fun PollApp() {
    val polls = remember { mutableStateListOf<Poll>() }
    var nextIdentifier by remember { mutableIntStateOf(1) }
    var isCreatingPoll by remember { mutableStateOf(false) }
    var selectedPollIdentifier by remember { mutableIntStateOf(0) }
    var selectedFilter by remember { mutableStateOf(PollFilter.ALL) }
    val selectedPollIndex = polls.indexOfFirst { it.identifier == selectedPollIdentifier }

    BackHandler(enabled = isCreatingPoll || selectedPollIndex >= 0) {
        isCreatingPoll = false
        selectedPollIdentifier = 0
    }

    when {
        isCreatingPoll -> CreatePollScreen(
            onCreate = { question, firstAnswer, secondAnswer ->
                val newPoll = Poll(
                    identifier = nextIdentifier,
                    question = question.trim(),
                    firstAnswer = firstAnswer.trim(),
                    secondAnswer = secondAnswer.trim()
                )
                if (newPoll.question.isNotEmpty() &&
                    newPoll.firstAnswer.isNotEmpty() &&
                    newPoll.secondAnswer.isNotEmpty()
                ) {
                    polls.add(newPoll)
                    nextIdentifier++
                    isCreatingPoll = false
                    selectedFilter = PollFilter.ALL
                }
            },
            onBack = { isCreatingPoll = false }
        )

        selectedPollIndex >= 0 -> {
            val poll = polls[selectedPollIndex]
            if (poll.isFinished) {
                PollResultsScreen(
                    poll = poll,
                    onBack = { selectedPollIdentifier = 0 }
                )
            } else {
                PollDetailsScreen(
                    poll = poll,
                    onVote = { answer ->
                        polls[selectedPollIndex] = polls[selectedPollIndex].vote(answer)
                    },
                    onNextParticipant = {
                        polls[selectedPollIndex] = polls[selectedPollIndex].nextParticipant()
                    },
                    onFinish = {
                        polls[selectedPollIndex] = polls[selectedPollIndex].finish()
                    },
                    onBack = { selectedPollIdentifier = 0 }
                )
            }
        }

        else -> PollListScreen(
            polls = polls,
            selectedFilter = selectedFilter,
            onFilterChange = { selectedFilter = it },
            onCreate = { isCreatingPoll = true },
            onOpen = { identifier -> selectedPollIdentifier = identifier }
        )
    }
}
