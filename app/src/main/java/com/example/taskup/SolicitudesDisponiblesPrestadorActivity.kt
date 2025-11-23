package com.example.taskup

import SolicitudAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskup.databinding.ActivitySolicitudesDisponiblesPrestadorBinding

class SolicitudesDisponiblesPrestadorActivity : AppCompatActivity(),
    SolicitudAdapter.OnSolicitudClickListener {

    private lateinit var binding: ActivitySolicitudesDisponiblesPrestadorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitudesDisponiblesPrestadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Crear varias solicitudes de plomería
        val listaSolicitudes = listOf(
            Solicitud("10:30 AM", "Calle 123 #45-67, Bogotá", "Fuga de agua en la cocina"),
            Solicitud("11:00 AM", "Carrera 12 #34-56, Bogotá", "Problema con el lavamanos"),
            Solicitud("11:45 AM", "Calle 78 #90-12, Bogotá", "Reparación de tubería principal"),
            Solicitud("12:30 PM", "Av. 5 #67-89, Bogotá", "Inodoro tapado"),
            Solicitud("1:15 PM", "Calle 23 #45-67, Bogotá", "Fuga en lavaplatos")
        )

        val adapter = SolicitudAdapter(listaSolicitudes, this)
        binding.recyclerSolicitudes.adapter = adapter
        binding.recyclerSolicitudes.layoutManager = LinearLayoutManager(this)
    }

    override fun onAceptarClick(solicitud: Solicitud) {
        Toast.makeText(this, "Aceptaste: ${solicitud.descripcion}", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, InicioPrestadorActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    override fun onRechazarClick(solicitud: Solicitud) {
        Toast.makeText(this, "Rechazaste: ${solicitud.descripcion}", Toast.LENGTH_SHORT).show()
    }
}
