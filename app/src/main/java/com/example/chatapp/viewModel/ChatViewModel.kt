package com.example.chatapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.chatapp.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONArray
import org.json.JSONObject
import java.net.URISyntaxException

class ChatViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _users = MutableStateFlow<List<String>>(emptyList())
    val users: StateFlow<List<String>> = _users

    // Own socket id stored here
    var mySocketId: String? = null

    private lateinit var mSocket: Socket

    private val onConnect: Emitter.Listener = Emitter.Listener {
        mySocketId = mSocket.id() // assign own socket id
        addMessage("Connected as $mySocketId", senderId = mySocketId ?: "unknown")
    }

    private val onNewMessage: Emitter.Listener = Emitter.Listener { args ->
        if (args.isNotEmpty()) {
            val msg = args[0] as String
            addMessage(msg, senderId = "server")
        }
    }

    private val onPrivateMessage: Emitter.Listener = Emitter.Listener { args ->
        if (args.isNotEmpty()) {
            val data = args[0] as JSONObject
            val from = data.getString("from")
            val message = data.getString("message")
            addMessage("Private from $from: $message", senderId = from)
        }
    }

    private val onUserList: Emitter.Listener = Emitter.Listener { args ->
        if (args.isNotEmpty()) {
            val array = args[0] as JSONArray
            val list = mutableListOf<String>()
            for (i in 0 until array.length()) {
                list.add(array.getString(i))
            }
            viewModelScope.launch {
                _users.value = list
            }
        }
    }

    private val onDisconnect: Emitter.Listener = Emitter.Listener {
        addMessage("Disconnected from server", senderId = "server")
    }

    init {
        try {
            mSocket = IO.socket("http://10.0.2.2:3000")
        } catch (e: URISyntaxException) {
            e.printStackTrace()
        }

        mSocket.on(Socket.EVENT_CONNECT, onConnect)
        mSocket.on("chat message", onNewMessage)
        mSocket.on("private message", onPrivateMessage)
        mSocket.on("user_list", onUserList)
        mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
        mSocket.connect()
    }

    fun sendMessage(text: String) {
        mSocket.emit("chat message", text)
        addMessage("Me: $text")
    }

    fun sendPrivateMessage(target: String, text: String) {
        val data = JSONObject().apply {
            put("target", target)
            put("message", text)
        }
        mSocket.emit("private message", data)
        addMessage("Private to $target: $text")
    }

    private fun addMessage(content: String, senderId: String = mySocketId ?: "unknown") {
        viewModelScope.launch {
            _messages.value = _messages.value + Message(senderId = senderId, content = content)
        }
    }

    override fun onCleared() {
        super.onCleared()
        mSocket.disconnect()
        mSocket.off(Socket.EVENT_CONNECT, onConnect)
        mSocket.off("chat message", onNewMessage)
        mSocket.off("private message", onPrivateMessage)
        mSocket.off("user_list", onUserList)
        mSocket.off(Socket.EVENT_DISCONNECT, onDisconnect)
    }
}