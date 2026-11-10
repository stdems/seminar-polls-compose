package com.example.seminarcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PollListScreen(
    polls: List<Poll>,
    onCreate: () -> Unit,
    onOpen: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Голосования", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onCreate) {
            Text("Создать голосование")
        }
        if (polls.isEmpty()) {
            Text("Пока нет голосований. Создай первое!")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(polls, key = { it.identifier }) { poll ->
                    Card(
                        onClick = { onOpen(poll.identifier) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(poll.question, style = MaterialTheme.typography.titleMedium)
                            Text("Голосов: ${poll.firstVotes + poll.secondVotes}")
                        }
                    }
                }
            }
        }
    }
}
