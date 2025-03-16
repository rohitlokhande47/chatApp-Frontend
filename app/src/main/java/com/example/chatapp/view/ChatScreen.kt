package com.example.chatapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.chatapp.viewmodel.ChatViewModel

@Composable
fun ChatScreen(chatViewModel: ChatViewModel) {
    val messages by chatViewModel.messages.collectAsState()
    var publicText by remember { mutableStateOf("") }
    var privateText by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        LazyColumn(
            modifier = Modifier.weight(1f),
            reverseLayout = true
        ) {
            items(messages.reversed()) { message ->
                Text(text = message.content, style = MaterialTheme.typography.body1)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Public message row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = publicText,
                onValueChange = { publicText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Enter public message") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (publicText.isNotBlank()) {
                    chatViewModel.sendMessage(publicText)
                    publicText = ""
                }
            }) {
                Text("Send Public")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        // Private message row
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = target,
                onValueChange = { target = it },
                modifier = Modifier.weight(0.3f),
                placeholder = { Text("Target Socket ID") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = privateText,
                onValueChange = { privateText = it },
                modifier = Modifier.weight(0.7f),
                placeholder = { Text("Enter private message") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (target.isNotBlank() && privateText.isNotBlank()) {
                    chatViewModel.sendPrivateMessage(target, privateText)
                    privateText = ""
                }
            }) {
                Text("Send Private")
            }
        }
    }
}