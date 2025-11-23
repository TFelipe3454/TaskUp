package com.example.taskup

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskup.databinding.ItemUsuarioBinding

class UsuarioAdapter(
    private val listaUsuarios: List<Usuario>,
    private val listener: OnUsuarioClickListener
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    interface OnUsuarioClickListener {
        fun onSolicitarClick(usuario: Usuario)
    }

    inner class UsuarioViewHolder(val binding: ItemUsuarioBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]
        holder.binding.tvNombre.text = usuario.nombre
        holder.binding.tvEmpleo.text = usuario.profesion

        val imagenRes = if (usuario.imagen != 0) usuario.imagen else R.drawable.usuario_default
        holder.binding.imgUsuario.setImageResource(imagenRes)

        // Click en el botón Solicitar
        holder.binding.btnSolicitar.setOnClickListener {
            listener.onSolicitarClick(usuario)
        }
    }

    override fun getItemCount(): Int = listaUsuarios.size
}

