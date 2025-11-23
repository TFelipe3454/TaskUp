package com.example.taskup

data class Account(
    val correo: String,
    val contrasena: String,
    val rol: String,
    val profesion: String = ""
)
