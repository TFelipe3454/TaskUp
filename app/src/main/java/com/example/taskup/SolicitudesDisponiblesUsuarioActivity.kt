package com.example.taskup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskup.databinding.ActivitySolicitudesDisponiblesUsuarioBinding


class SolicitudesDisponiblesUsuarioActivity : AppCompatActivity(), UsuarioAdapter.OnUsuarioClickListener {

    private lateinit var binding: ActivitySolicitudesDisponiblesUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitudesDisponiblesUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Crear lista de usuarios
        val listaUsuarios: MutableList<Usuario> = ArrayList()
        listaUsuarios.add(Usuario("Juan Pérez", "Plomero", 4.7))
        listaUsuarios.add(Usuario("Ana Gómez", "Plomero", 4.8))
        listaUsuarios.add(Usuario("Sofía Martínez", "Plomero", 4.9))
        listaUsuarios.add(Usuario("Jorge Herrera", "Plomero", 4.8))
        listaUsuarios.add(Usuario("Andrés Morales", "Plomero", 4.7))
        listaUsuarios.add(Usuario("Diego Castro", "Plomero", 4.6))
        listaUsuarios.add(Usuario("Mateo Vargas", "Plomero", 4.7))

        // Configurar RecyclerView
        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(this)
        val adapter = UsuarioAdapter(listaUsuarios, this) // 'this' implementa OnUsuarioClickListener
        binding.recyclerUsuarios.adapter = adapter
    }

    override fun onSolicitarClick(usuario: Usuario) {
        val intent = Intent(this, VerPerfilPrestador::class.java)
        intent.putExtra("usuarioSeleccionado", usuario)
        startActivity(intent)
    }
}
