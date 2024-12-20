//ini adalah viewmodel 



package com.example.chatrisky

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

// Class ini mengelola data dan logika untuk halaman chat (terkait dengan Compose).
class ChatViewModel : ViewModel() {
//nerativeModel: Inisialisasi objek model generatif ( Gemini).


    val messagelist = mutableStateListOf<MessageModel>()

    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-pro",//Menentukan model yang digunakan (gemini-pro).
        apiKey = "AIzaSyAgtFpzsEJ97u3-vR7X4llNJcix_nuHZd0" //apiKey  untuk otentikasi ke gemini.
    )

    fun sendMessage(question: String) {//Fungsi untuk mengirim pertanyaan pengguna dan mendapatkan balasan dari model(gemini).
        viewModelScope.launch {// Menjalankan logika di coroutine latar belakang untuk menjaga UI tetap responsif.
            val chat = generativeModel.startChat(//Memulai sesi chat dengan model generatif
                history = messagelist.map {//Mengonversi setiap item dalam messagelist menjadi format yang sesuai (role dan teks).
                    content(it.role) { text(it.message) }
                }
            )

            messagelist.add(MessageModel(question, "user"))//Membuat objek pesan dengan teks dari user/pengguna(question)

            val response = chat.sendMessage(question)//Mengirim pertanyaan user/pengguna ke model dan menunggu balasan.

            messagelist.add(MessageModel(response.text.toString(), "model"))//Menambahkan balasan model ke messagelist.
            response.text.toString() //Mengonversi balasan gemini menjadi teks untuk ditampilkan.

            Log.i("Response from Gemini", response.text.toString())//Mencatat respons model ke konsol untuk debugging
        }
    }
}
