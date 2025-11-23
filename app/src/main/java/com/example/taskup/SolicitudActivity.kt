package com.example.taskup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskup.databinding.ActivitySolicitudBinding

class SolicitudActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySolicitudBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySolicitudBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar DatePicker
        binding.etFecha.setOnClickListener {
            val calendario = java.util.Calendar.getInstance()
            val year = calendario.get(java.util.Calendar.YEAR)
            val month = calendario.get(java.util.Calendar.MONTH)
            val day = calendario.get(java.util.Calendar.DAY_OF_MONTH)

            val datePicker = android.app.DatePickerDialog(this, { _, y, m, d ->
                binding.etFecha.setText("$d/${m + 1}/$y")
            }, year, month, day)

            datePicker.show()
        }

        // Configurar TimePicker
        binding.etHora.setOnClickListener {
            val calendario = java.util.Calendar.getInstance()
            val hour = calendario.get(java.util.Calendar.HOUR_OF_DAY)
            val minute = calendario.get(java.util.Calendar.MINUTE)

            val timePicker = android.app.TimePickerDialog(this, { _, h, m ->
                binding.etHora.setText(String.format("%02d:%02d", h, m))
            }, hour, minute, true)

            timePicker.show()
        }

        // Botón enviar
        binding.btnEnviarSolicitud.setOnClickListener {
            val tipoServicio = binding.spinnerTipoServicio.selectedItem.toString()
            val ubicacion = binding.etUbicacion.text.toString().trim()
            val fecha = binding.etFecha.text.toString().trim()
            val hora = binding.etHora.text.toString().trim()
            val descripcion = binding.etDescripcion.text.toString().trim()
            val precio = binding.etPrecio.text.toString().trim()
            val telefono = binding.etTelefono.text.toString().trim()

            // Validación de campos
            if (ubicacion.isEmpty() || fecha.isEmpty() || hora.isEmpty() ||
                descripcion.isEmpty() || precio.isEmpty() || telefono.isEmpty()
            ) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Mostrar mensaje de éxito
                Toast.makeText(this, "¡Solicitud enviada correctamente!", Toast.LENGTH_LONG).show()
                val intent = Intent(this, SolicitudesDisponiblesUsuarioActivity::class.java)
                startActivity(intent)
            }
        }

    }
}
