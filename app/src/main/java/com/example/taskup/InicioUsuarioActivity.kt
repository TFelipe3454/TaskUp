package com.example.taskup

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskup.databinding.ActivityInicioUsuarioBinding

class InicioUsuarioActivity : AppCompatActivity(), UsuarioAdapter.OnUsuarioClickListener {

    private lateinit var binding: ActivityInicioUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflate con ViewBinding
        binding = ActivityInicioUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView con ViewBinding
        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(this)

        // Crear lista de usuarios
        val listaUsuarios: MutableList<Usuario> = ArrayList()
        listaUsuarios.add(Usuario("Juan Pérez", "Plomero", 4.7)) // Usará drawable default
        listaUsuarios.add(Usuario("María López", "Electricista", 4.9))
        listaUsuarios.add(Usuario("Carlos Ruiz", "Jardinero", 4.6))

        // Configurar Adapter
        val adapter = UsuarioAdapter(listaUsuarios, this) // ✅ 'this' implementa OnUsuarioClickListener
        binding.recyclerUsuarios.adapter = adapter

        binding.btnPlomeria.setOnClickListener {
            val intent = Intent(this, SolicitudActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onSolicitarClick(usuario: Usuario) {
        val intent = Intent(this, VerPerfilPrestador::class.java)
        intent.putExtra("usuarioSeleccionado", usuario)
        startActivity(intent)
    }
}

