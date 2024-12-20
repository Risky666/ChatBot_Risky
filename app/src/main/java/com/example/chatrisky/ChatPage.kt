//halaman chat


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


//ChatPage: Komponen utama halaman chat.
//modifier.fillMaxSize(): Memastikan kolom memenuhi ukuran layar penuh.
@Composable
fun ChatPage(modifier: Modifier = Modifier, viewModel: ChatViewModel) { //ChatPage: Komponen utama halaman chat.
    Column(modifier = modifier.fillMaxSize()) {//Column: Mengatur layout secara vertikal.

        //Menampilkan header aplikasi di bagian atas layar.
        AppHeader()

        ////MessageList: Bagian untuk menampilkan daftar pesan.
        MessageList(

            ////modifier.weight(1f): Mengisi ruang layar yang tersisa di bawah header dan di atas input pesan.
            modifier = Modifier.weight(1f),
            //messageList = viewModel.messagelist: Mengambil data pesan dari viewModel
            messageList = viewModel.messagelist
        )

        ////MessageInput: Menampilkan input untuk mengetik pesan.
        MessageInput { message ->
            //viewModel.sendMessage(message): Mengirim pesan ke viewModel.
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


        Box(//kolom chat
            modifier = Modifier
                .padding(
                    start = if (isModel) 8.dp else 70.dp,
                    end = if (isModel) 70.dp else 8.dp,
                    top = 8.dp,
                    bottom = 8.dp
                )
                .background(
                    //ini mengambil dari ui.theme color
                    //jika model(gemini) membalas chat dari user maka warna kolomnya merah
                    color = if (isModel) ColorModelMessage else ColorUserMessage,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        ) {
            //ini untuk pesan text chat
            Text(
                text = messageModel.message,
                color = Color.White,
                fontSize = 16.sp
            )
        }
    }
}




@Composable
//MessageInput: Komponen input untuk mengetik pesan.
fun MessageInput(onMessageSend: (String) -> Unit) {
    var message = remember { mutableStateOf("") }// Menyimpan teks input pengguna.
    //Row: Layout horizontal untuk input teks dan tombol kirim.
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Kotak input teks
        OutlinedTextField(
            value = message.value, // Teks yang diketik pengguna.
            onValueChange = { message.value = it },//Fungsi untuk memperbarui teks saat pengguna mengetik.
            modifier = Modifier.weight(1f),
            placeholder = { Text("Type a message") }//Teks petunjuk di dalam kotak input.
        )
        IconButton(//Tombol kirim pesan
            onClick = {//Mengirim pesan jika kotak input tidak kosong, lalu mengosongkan input
                if (message.value.isNotBlank()) {
                    onMessageSend(message.value)
                    message.value = ""
                }
            }
        ) {
            Icon(//Menampilkan ikon kirim dari Material Icons.
                imageVector = Icons.Filled.Send,
                contentDescription = "Send"
            )
        }
    }
}


//ini untuk hader Menampilkan tulisan ChatRisky/header aplikasi
@Composable
fun AppHeader() {
    Box(// Kontainer dengan latar belakang.
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)//Warna utama dari tema aplikasi.
    ) {
        Text(//Menampilkan teks "Chat Risky" sebagai judul aplikasi.
            text = "Chat Risky",
            color = Color.White,
            fontSize = 22.sp,
            modifier = Modifier.padding(16.dp)
        )
    }
}
