package com.example.taskup

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.taskup.databinding.ActivityMainBinding
import com.example.taskup.databinding.ActivityVerPerfilPrestadorBinding

class VerPerfilPrestador : AppCompatActivity() {

    private lateinit var binding: ActivityVerPerfilPrestadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerPerfilPrestadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recibir el usuario desde el Intent
        val usuario = intent.getSerializableExtra("usuarioSeleccionado") as? Usuario
        usuario?.let {
            binding.tvNombrePrestador.text = it.nombre
            binding.tvTrabajoPrestador.text = it.profesion
            // binding.imgPerfilPrestador.setImageResource(it.imagen) // si tienes drawable
        }

        binding.btnSolicitarServicio.setOnClickListener {
            Toast.makeText(this, "¡Servicio pedido correctamente!", Toast.LENGTH_LONG).show()
        }
    }

}