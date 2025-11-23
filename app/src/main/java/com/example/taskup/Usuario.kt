package com.example.taskup

import java.io.Serializable

data class Usuario(
    val nombre: String,
    val profesion: String,
    val calificacion: Double,
    val descripcion: String,
    val imagen: String   // ahora nombre del drawable
) : Serializable