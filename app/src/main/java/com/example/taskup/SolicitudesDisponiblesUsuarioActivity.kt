package com.example.taskup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskup.databinding.ActivitySolicitudesDisponiblesUsuarioBinding

class SolicitudesDisponiblesUsuarioActivity : AppCompatActivity(),
    UsuarioAdapter.OnUsuarioClickListener {

    private lateinit var binding: ActivitySolicitudesDisponiblesUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitudesDisponiblesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Crear lista de usuarios
        val listaUsuarios: MutableList<Usuario> = ArrayList()
        listaUsuarios.add(Usuario("Juan Pérez", "Plomero", 4.7, "Experto en reparaciones", "plomero1"))
        listaUsuarios.add(Usuario("Ana Gómez", "Plomero", 4.8, "Atención rápida", "plomera1"))
        listaUsuarios.add(Usuario("Sofía Martínez", "Plomero", 4.9, "10 años de experiencia", "plomera2"))
        listaUsuarios.add(Usuario("Jorge Herrera", "Plomero", 4.8, "Especialista en tuberías", "plomero2"))
        listaUsuarios.add(Usuario("Andrés Morales", "Plomero", 4.7, "Disponible 24/7", "plomero3"))
        listaUsuarios.add(Usuario("Diego Castro", "Plomero", 4.6, "Trabajo económico", "plomero4"))
        listaUsuarios.add(Usuario("Mateo Vargas", "Plomero", 4.7, "Servicio garantizado", "plomero5"))

        // Recycler
        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(this)
        val adapter = UsuarioAdapter(listaUsuarios, this)
        binding.recyclerUsuarios.adapter = adapter
    }

    override fun onSolicitarClick(usuario: Usuario) {
        val intent = Intent(this, VerPerfilPrestador::class.java)
        intent.putExtra("usuarioSeleccionado", usuario)
        startActivity(intent)
    }

    // ⭐ ESTE FALTABA
    override fun onVerPerfilClick(usuario: Usuario) {
        val intent = Intent(this, VerPerfilPrestador::class.java)
        intent.putExtra("usuarioSeleccionado", usuario)
        startActivity(intent)
    }
}
