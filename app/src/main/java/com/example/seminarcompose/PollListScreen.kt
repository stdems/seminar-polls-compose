package com.example.seminarcompose

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

enum class PollFilter(val title: String) {
    ALL("Все"),
    ACTIVE("Активные"),
    FINISHED("Завершённые")
}

@Composable
fun PollListScreen(
    polls: List<Poll>,
    selectedFilter: PollFilter,
    onFilterChange: (PollFilter) -> Unit,
    onCreate: () -> Unit,
    onOpen: (Int) -> Unit
) {
    val visiblePolls = when (selectedFilter) {
        PollFilter.ALL -> polls
        PollFilter.ACTIVE -> polls.filter { !it.isFinished }
        PollFilter.FINISHED -> polls.filter { it.isFinished }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Голосования", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onCreate) {
            Text("Создать голосование")
        }
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            for (filter in PollFilter.entries) {
                FilterChip(
                    selected = selectedFilter == filter,
                    onClick = { onFilterChange(filter) },
                    label = { Text(filter.title) }
                )
            }
        }
        if (visiblePolls.isEmpty()) {
            Text("Здесь пока нет голосований")
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(visiblePolls, key = { it.identifier }) { poll ->
                    Card(
                        onClick = { onOpen(poll.identifier) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(poll.question, style = MaterialTheme.typography.titleMedium)
                            Text(if (poll.isFinished) "Завершено" else "Активно")
                        }
                    }
                }
            }
        }
    }
}
