package com.example.chatrisky

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.chatrisky.ui.theme.ColorModelMessage
import com.example.chatrisky.ui.theme.ColorUserMessage

@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel) {
    Column(modifier = modifier.fillMaxSize()) {
        AppHeader()
        MessageList(
            modifier = Modifier.weight(1f),
            messageList = viewModel.messagelist
        )
        MessageInput { message ->
            viewModel.sendMessage(message)
        }
    }
}

@Composable
fun MessageList(modifier: Modifier, messageList: List<MessageModel>) {
    LazyColumn(
        modifier = modifier.padding(8.dp),
        reverseLayout = true
    ) {
        items(messageList.reversed()) { message ->
            MessageRow(messageModel = message)
        }
    }
}

@Composable
fun MessageRow(messageModel: MessageModel) {
    val isModel = messageModel.role == "model"

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isModel) Arrangement.Start else Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .background(
                    color = if (isModel) ColorModelMessage else ColorUserMessage,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            Text(
                text = messageModel.message,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message = remember { mutableStateOf("") }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = message.value,
            onValueChange = { message.value = it },
            modifier = Modifier.weight(1f),
            placeholder = { Text("Type a message") }
        )
        IconButton(
            onClick = {
                if (message.value.isNotBlank()) {
                    onMessageSend(message.value)
                    message.value = ""
                }
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = "Send"
            )
        }
    }
}

@Composable
fun AppHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = "Chat Risky",
            color = Color.White,
            fontSize = 22.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}
