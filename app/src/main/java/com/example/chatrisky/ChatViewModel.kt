package com.example.chatrisky

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    val messagelist = mutableStateListOf<MessageModel>()

    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = "AIzaSyAgtFpzsEJ97u3-vR7X4llNJcix_nuHZd0" // Replace with your API key
    )

    fun sendMessage(question: String) {
        viewModelScope.launch {
            val chat = generativeModel.startChat(
                history = messagelist.map {
                    content(it.role) { text(it.message) }
                }
            )

            messagelist.add(MessageModel(question, "user"))

            val response = chat.sendMessage(question)
            messagelist.add(MessageModel(response.text.toString(), "model"))

            Log.i("Response from Gemini", response.text.toString())
        }
    }
}
