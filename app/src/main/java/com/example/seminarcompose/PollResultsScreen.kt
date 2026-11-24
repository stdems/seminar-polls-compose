package com.example.seminarcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilterChip
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun PollResultsScreen(poll: Poll, onBack: () -> Unit) {
    var showPercentages by remember(poll.identifier) { mutableStateOf(false) }
    var sortByPopularity by remember(poll.identifier) { mutableStateOf(false) }
    val totalVotes = poll.firstVotes + poll.secondVotes
    val answerOrder = if (sortByPopularity && poll.secondVotes > poll.firstVotes) {
        listOf(2, 1)
    } else {
        listOf(1, 2)
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(poll.question, style = MaterialTheme.typography.headlineMedium)
        Text("Голосование завершено")
        Text("Всего голосов: $totalVotes")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = !showPercentages,
                onClick = { showPercentages = false },
                label = { Text("Голоса") }
            )
            FilterChip(
                selected = showPercentages,
                onClick = { showPercentages = true },
                label = { Text("Проценты") }
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("По популярности")
            Switch(
                checked = sortByPopularity,
                onCheckedChange = { sortByPopularity = it }
            )
        }
        for (answerNumber in answerOrder) {
            val answer = if (answerNumber == 1) poll.firstAnswer else poll.secondAnswer
            val votes = if (answerNumber == 1) poll.firstVotes else poll.secondVotes
            val voteShare = if (totalVotes == 0) 0f else votes.toFloat() / totalVotes
            val percentage = (voteShare * 100).roundToInt()

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(answer, style = MaterialTheme.typography.titleMedium)
                Text(if (showPercentages) "$percentage%" else "Голосов: $votes")
                LinearProgressIndicator(
                    progress = { voteShare },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
        TextButton(onClick = onBack) {
            Text("Назад")
        }
    }
}
