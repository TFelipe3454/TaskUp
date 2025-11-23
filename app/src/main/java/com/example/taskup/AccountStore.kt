package com.example.taskup

object AccountStore {

    val cuentas = mutableListOf(
        Account("usuario@taskup.com", "123456", "Cliente"),
        Account("prestador@taskup.com", "abcdef", "Prestador", "Plomero")
    )

    fun agregarCuenta(cuenta: Account) {
        cuentas.add(cuenta)
    }

    fun validar(correo: String, contra: String): Account? {
        return cuentas.find { it.correo == correo && it.contrasena == contra }
    }
}
