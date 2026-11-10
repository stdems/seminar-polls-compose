package com.example.seminarcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun PollDetailsScreen(
    poll: Poll,
    onFirstVote: () -> Unit,
    onSecondVote: () -> Unit,
    onBack: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(poll.question)
        Text("Экран пока не готов")
        Button(onClick = onBack) {
            Text("Назад")
        }
    }
}
