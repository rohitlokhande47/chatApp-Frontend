package com.example.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.chatapp.view.PrivateChatScreen
import com.example.chatapp.view.UsersScreen
import com.example.chatapp.viewmodel.ChatViewModel

class MainActivity : ComponentActivity() {
    private val chatViewModel = ChatViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "users") {
                        composable("users") {
                            UsersScreen(chatViewModel = chatViewModel, navController = navController)
                        }
                        composable("private_chat/{targetId}") { backStackEntry ->
                            val targetId = backStackEntry.arguments?.getString("targetId") ?: ""
                            PrivateChatScreen(chatViewModel = chatViewModel, targetId = targetId, navController = navController)
                        }
                    }
                }
            }
        }
    }
}