package com.example.seminarcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PollDetailsScreen(
    poll: Poll,
    onFirstVote: () -> Unit,
    onSecondVote: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(poll.question, style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onFirstVote, modifier = Modifier.fillMaxWidth()) {
            Text("${poll.firstAnswer}: ${poll.firstVotes}")
        }
        Button(onClick = onSecondVote, modifier = Modifier.fillMaxWidth()) {
            Text("${poll.secondAnswer}: ${poll.secondVotes}")
        }
        TextButton(onClick = onBack) {
            Text("Назад")
        }
    }
}
