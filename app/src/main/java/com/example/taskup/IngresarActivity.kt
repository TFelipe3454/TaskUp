package com.example.taskup

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taskup.databinding.ActivityIngresarBinding

class IngresarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIngresarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngresarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSwitchRegistrar.setOnClickListener {
            startActivity(Intent(this, RegistrarActivity::class.java))
        }

        binding.btnIngresar.setOnClickListener {
            val correo = binding.etCorreo.text.toString().trim()
            val contra = binding.etContrasena.text.toString().trim()

            if (correo.isEmpty() || contra.isEmpty()) {
                Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val cuenta = AccountStore.validar(correo, contra)

            if (cuenta == null) {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Redirigir según rol
            when (cuenta.rol) {
                "Cliente" -> startActivity(Intent(this, InicioUsuarioActivity::class.java))
                "Prestador" -> startActivity(Intent(this, InicioPrestadorActivity::class.java))
            }

            finish()
        }
    }
}
