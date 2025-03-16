package com.example.chatapp.model

data class Message(
    val senderId: String,
    val content: String,
    val receiverId: String? = null
)