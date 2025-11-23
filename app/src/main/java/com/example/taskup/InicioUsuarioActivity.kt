package com.example.taskup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskup.databinding.ActivityInicioUsuarioBinding
import com.google.gson.Gson

class InicioUsuarioActivity : AppCompatActivity(), UsuarioAdapter.OnUsuarioClickListener {

    private lateinit var binding: ActivityInicioUsuarioBinding
    private lateinit var adapter: UsuarioAdapter
    private lateinit var listaUsuarios: List<Usuario>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInicioUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar RecyclerView
        binding.recyclerUsuarios.layoutManager = LinearLayoutManager(this)

        // --------------------------
        // CATEGORÍAS (INCLUDES)
        // --------------------------

        val catPlomeria = findViewById<View>(R.id.cat_plomeria)
        catPlomeria.findViewById<ImageView>(R.id.imgCategoria)
            .setImageResource(R.drawable.plomeria)
        catPlomeria.findViewById<TextView>(R.id.tvCategoria).text = "Plomería"

        val catElectricidad = findViewById<View>(R.id.cat_electricidad)
        catElectricidad.findViewById<ImageView>(R.id.imgCategoria)
            .setImageResource(R.drawable.electricista)
        catElectricidad.findViewById<TextView>(R.id.tvCategoria).text = "Electricidad"

        val catJardineria = findViewById<View>(R.id.cat_jardineria)
        catJardineria.findViewById<ImageView>(R.id.imgCategoria)
            .setImageResource(R.drawable.jardineria)
        catJardineria.findViewById<TextView>(R.id.tvCategoria).text = "Jardinería"


        // --------------------------
        // LISTENERS DE CATEGORÍAS
        // --------------------------

        catPlomeria.setOnClickListener {
            val intent = Intent(this, SolicitudActivity::class.java)
            startActivity(intent)
        }

        catElectricidad.setOnClickListener {
            val intent = Intent(this, SolicitudActivity::class.java)
            startActivity(intent)
        }

        catJardineria.setOnClickListener {
            val intent = Intent(this, SolicitudActivity::class.java)
            startActivity(intent)
        }


        // --------------------------
        // Cargar JSON
        // --------------------------
        val json = assets.open("prestadores.json")
            .bufferedReader().use { it.readText() }

        listaUsuarios = Gson().fromJson(json, Array<Usuario>::class.java).toList()

        // Adapter
        adapter = UsuarioAdapter(listaUsuarios, this)
        binding.recyclerUsuarios.adapter = adapter


    }


    // --------------------------
    // FUNCIÓN FILTRAR
    // --------------------------
    private fun filtrarProfesionales(profesion: String) {
        val filtrados = listaUsuarios.filter { it.profesion.equals(profesion, ignoreCase = true) }
        adapter.updateList(filtrados)
    }


    override fun onSolicitarClick(usuario: Usuario) {
        val intent = Intent(this, VerPerfilPrestador::class.java)
        intent.putExtra("usuarioSeleccionado", usuario)
        startActivity(intent)
    }

    override fun onVerPerfilClick(usuario: Usuario) {
        val intent = Intent(this, VerPerfilPrestador::class.java)
        intent.putExtra("usuarioSeleccionado", usuario)
        startActivity(intent)
    }
}

