package com.example.seminarcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@Composable
fun PollDetailsScreen(
    poll: Poll,
    onVote: (Int) -> Unit,
    onNextParticipant: () -> Unit,
    onFinish: () -> Unit,
    onBack: () -> Unit
) {
    var selectedAnswer by remember(poll.identifier, poll.hasVoted) { mutableIntStateOf(0) }
    var showFinishConfirmation by remember(poll.identifier) { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(poll.question, style = MaterialTheme.typography.headlineMedium)
        if (poll.hasVoted) {
            Text("Голос учтён")
            Button(onClick = onNextParticipant) {
                Text("Следующий участник")
            }
        } else {
            Column(modifier = Modifier.selectableGroup()) {
                for (answerNumber in 1..2) {
                    val answer = if (answerNumber == 1) poll.firstAnswer else poll.secondAnswer
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .selectable(
                                selected = selectedAnswer == answerNumber,
                                onClick = { selectedAnswer = answerNumber },
                                role = Role.RadioButton
                            )
                            .padding(vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(selected = selectedAnswer == answerNumber, onClick = null)
                        Text(answer, modifier = Modifier.padding(start = 8.dp).weight(1f))
                    }
                }
            }
            Button(
                onClick = { onVote(selectedAnswer) },
                enabled = selectedAnswer != 0
            ) {
                Text("Проголосовать")
            }
        }
        TextButton(onClick = { showFinishConfirmation = true }) {
            Text("Завершить голосование")
        }
        TextButton(onClick = onBack) {
            Text("Назад")
        }
    }

    if (showFinishConfirmation) {
        AlertDialog(
            onDismissRequest = { showFinishConfirmation = false },
            title = { Text("Завершить голосование?") },
            text = { Text("После завершения новые голоса добавить нельзя.") },
            confirmButton = {
                TextButton(onClick = {
                    showFinishConfirmation = false
                    onFinish()
                }) {
                    Text("Завершить")
                }
            },
            dismissButton = {
                TextButton(onClick = { showFinishConfirmation = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}
