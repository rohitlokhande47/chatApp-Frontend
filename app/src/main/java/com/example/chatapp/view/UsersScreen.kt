package com.example.chatapp.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.chatapp.viewmodel.ChatViewModel
import com.google.firebase.firestore.auth.User
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UsersScreen(chatViewModel: ChatViewModel, navController: NavController) {
    val users = chatViewModel.users.collectAsState()
    val filteredUsers = users.value.filter { it != chatViewModel.mySocketId }

    Column(modifier = Modifier.fillMaxSize()) {
        // WhatsApp-style Toolbar
        WhatsAppToolbar()

        // Search bar
        SearchBar()

        // User list
        LazyColumn {
            items(filteredUsers) { userId ->
                UserListItem(
                    userId = userId,
                    onUserClick = {
                        navController.navigate("private_chat/$userId")
                    }
                )
            }
        }
    }
}

@Composable
fun WhatsAppToolbar() {
    TopAppBar(
        title = {
            Text(
                text = "WhatsApp",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        backgroundColor = Color(0xFF075E54),
        actions = {
            IconButton(onClick = { /* Search functionality */ }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White
                )
            }
            IconButton(onClick = { /* More options menu */ }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "More options",
                    tint = Color.White
                )
            }
        }
    )
}

@Composable
fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = MaterialTheme.shapes.small,
        elevation = 4.dp
    ) {
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Search...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}

@Composable
fun UserListItem(userId: String, onUserClick: () -> Unit) {
    // In a real app, you'd have more user data like profile pic, last message, time, etc.
    // For now, we'll simulate with placeholder data
    val lastMessage = "Hey, how are you doing?"
    val timestamp = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onUserClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile picture
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color(0xFFE0E0E0)),
            contentAlignment = Alignment.Center
        ) {
            // In a real app, you'd load an actual profile picture here
            Text(
                text = userId.take(1).uppercase(),
                color = Color.Gray,
                fontWeight = FontWeight.Bold
            )
        }

        // User info
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Text(
                text = "User $userId",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = lastMessage,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        // Timestamp
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = timestamp,
                color = Color.Gray,
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Unread message indicator (as an example)
            if (userId.hashCode() % 3 == 0) {  // Just to show some with unread messages
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF25D366)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }

    // Divider
    Divider(
        modifier = Modifier.padding(start = 80.dp),
        color = Color.LightGray,
        thickness = 0.5.dp
    )
}