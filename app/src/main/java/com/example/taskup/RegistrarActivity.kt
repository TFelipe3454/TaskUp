package com.example.taskup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskup.databinding.ActivityRegistrarBinding

class RegistrarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCrearCuenta.setOnClickListener {
            val correo = binding.etCorreo.text.toString().trim()
            val contra = binding.etContrasena.text.toString().trim()
            val rol = binding.spinnerRol.selectedItem.toString()
            val profesion = binding.etProfesion.text.toString().trim()

            if (correo.isEmpty() || contra.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (rol == "Prestador" && profesion.isEmpty()) {
                Toast.makeText(this, "Ingrese la profesión", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear cuenta nueva
            val cuenta = Account(correo, contra, rol, profesion)
            AccountStore.agregarCuenta(cuenta)

            Toast.makeText(this, "Cuenta creada", Toast.LENGTH_SHORT).show()

            // Redirigir
            if (rol == "Cliente") {
                startActivity(Intent(this, InicioUsuarioActivity::class.java))
            } else {
                startActivity(Intent(this, InicioPrestadorActivity::class.java))
            }

            finish()
        }

        // Mostrar campo de profesión según rol
        binding.spinnerRol.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                binding.etProfesion.visibility =
                    if (parent.getItemAtPosition(position) == "Prestador") View.VISIBLE else View.GONE
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }
}
