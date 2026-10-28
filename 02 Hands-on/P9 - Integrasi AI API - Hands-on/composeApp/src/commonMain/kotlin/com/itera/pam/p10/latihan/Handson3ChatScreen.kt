package com.itera.pam.p10.latihan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itera.pam.p10.AiRepository
import com.itera.pam.p10.MockAiRepository
import kotlinx.coroutines.launch

// Hands-on 3: AI-Powered Chat UI
// UI chat sederhana: TextField input, tombol "Kirim", daftar bubble pesan
// yang bisa di-scroll, dan indikator loading saat menunggu jawaban AI.

data class ChatMessage(val isUser: Boolean, val text: String)

@Composable
fun Handson3Screen(repository: AiRepository = remember { MockAiRepository() }) {
    var messages by remember { mutableStateOf(listOf<ChatMessage>()) }
    var input by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    // auto scroll ke pesan paling bawah setiap ada pesan baru
    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) listState.animateScrollToItem(messages.lastIndex)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Hands-on 3: AI Chat")

        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f).fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages) { message ->
                ChatBubble(message)
            }
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = input,
                onValueChange = { input = it },
                label = { Text("Tulis pesan") },
                modifier = Modifier.weight(1f)
            )
            Button(
                onClick = {
                    val pertanyaan = input.trim()
                    messages = messages + ChatMessage(isUser = true, text = pertanyaan)
                    input = ""
                    isLoading = true
                    scope.launch {
                        val jawaban = repository.ask(pertanyaan)
                            .getOrElse { "Error: ${it.message}" }
                        messages = messages + ChatMessage(isUser = false, text = jawaban)
                        isLoading = false
                    }
                },
                enabled = input.isNotBlank() && !isLoading,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Kirim")
            }
        }
    }
}

@Composable
private fun ChatBubble(message: ChatMessage) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = if (message.isUser) Alignment.CenterEnd else Alignment.CenterStart
    ) {
        Text(
            text = message.text,
            color = if (message.isUser) Color.White else Color.Black,
            modifier = Modifier
                .widthIn(max = 280.dp)
                .background(
                    color = if (message.isUser) Color(0xFF1976D2) else Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp)
        )
    }
}
