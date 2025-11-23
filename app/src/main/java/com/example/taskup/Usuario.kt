package com.example.taskup

import java.io.Serializable

data class Usuario(
    val nombre: String,
    val profesion: String,
    val calificacion: Double,
    val imagen: Int = R.drawable.usuario_default // drawable por defecto
): Serializable