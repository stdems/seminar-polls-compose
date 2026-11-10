package com.example.seminarcompose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreatePollScreen(
    onCreate: (String, String, String) -> Unit,
    onBack: () -> Unit
) {
    var question by remember { mutableStateOf("") }
    var firstAnswer by remember { mutableStateOf("") }
    var secondAnswer by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Создание голосования", style = MaterialTheme.typography.headlineMedium)
        OutlinedTextField(
            value = question,
            onValueChange = { question = it },
            label = { Text("Вопрос") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )
        OutlinedTextField(
            value = firstAnswer,
            onValueChange = { firstAnswer = it },
            label = { Text("Первый ответ") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        OutlinedTextField(
            value = secondAnswer,
            onValueChange = { secondAnswer = it },
            label = { Text("Второй ответ") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Button(
            onClick = { onCreate(question, firstAnswer, secondAnswer) },
            enabled = question.isNotBlank() && firstAnswer.isNotBlank() && secondAnswer.isNotBlank()
        ) {
            Text("Создать")
        }
        TextButton(onClick = onBack) {
            Text("Назад")
        }
    }
}
