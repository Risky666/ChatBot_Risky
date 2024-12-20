package com.example.chatrisky

data class MessageModel(
    val message: String,
    val role: String
)

//MessageModel digunakan untuk menyimpan daftar pesan dari user/pengguna dan model (gemini).