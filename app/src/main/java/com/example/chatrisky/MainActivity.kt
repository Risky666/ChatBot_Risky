package com.example.chatrisky

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.example.chatrisky.ui.theme.ChatRiskyTheme

//Komponen utama aplikasi.
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {//savedInstanceState: Menyimpan data jika ada proses seperti rotasi layar.
        super.onCreate(savedInstanceState)//
        enableEdgeToEdge()
        val chatViewModel = ViewModelProvider(this)[ChatViewModel::class.java]//Membuat instance ChatViewModel untuk mengelola data dan logika aplikasi.
        setContent {
            ChatRiskyTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    ChatPage(Modifier.padding(innerPadding), chatViewModel)
                }
            }
        }
    }
}
