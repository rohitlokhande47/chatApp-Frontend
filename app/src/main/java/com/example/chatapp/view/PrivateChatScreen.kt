package com.example.chatapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.chatapp.viewmodel.ChatViewModel

@Composable
fun PrivateChatScreen(chatViewModel: ChatViewModel, targetId: String, navController: NavController) {
    val messages by chatViewModel.messages.collectAsState()
    var text by remember { mutableStateOf("") }
    val currentUserId = chatViewModel.mySocketId // Assuming this exists in your ViewModel

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chat with User") },
                backgroundColor = Color(0xFF075E54),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Messages list
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                reverseLayout = true,
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(messages.reversed()) { message ->
                    val isCurrentUser = message.senderId == currentUserId

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start
                    ) {
                        Box(
                            modifier = Modifier
                                .widthIn(max = 280.dp)
                                .clip(
                                    RoundedCornerShape(
                                        topStart = 16.dp,
                                        topEnd = 16.dp,
                                        bottomStart = if (isCurrentUser) 16.dp else 0.dp,
                                        bottomEnd = if (isCurrentUser) 0.dp else 16.dp
                                    )
                                )
                                .background(
                                    if (isCurrentUser) Color(0xFFDCF8C6)
                                    else Color.White
                                )
                                .padding(12.dp)
                        ) {
                            Text(
                                text = message.content,
                                style = MaterialTheme.typography.body1,
                                color = Color.Black
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))
                }
            }

            // Input area with elevated card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = 4.dp,
                shape = RoundedCornerShape(24.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("Type a message...") },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        maxLines = 4
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    FloatingActionButton(
                        onClick = {
                            if (text.isNotBlank()) {
                                chatViewModel.sendPrivateMessage(targetId, text)
                                text = ""
                            }
                        },
                        modifier = Modifier.size(48.dp),
                        backgroundColor = Color(0xFF075E54)
                    ) {
                        Icon(
                            Icons.Filled.Send,
                            contentDescription = "Send",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}