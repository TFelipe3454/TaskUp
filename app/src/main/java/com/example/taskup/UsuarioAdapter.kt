package com.example.taskup

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskup.databinding.ItemUsuarioBinding

class UsuarioAdapter(
    private var listaUsuarios: List<Usuario>,
    private val listener: OnUsuarioClickListener
) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {

    interface OnUsuarioClickListener {
        fun onSolicitarClick(usuario: Usuario)
        fun onVerPerfilClick(usuario: Usuario)
    }

    inner class UsuarioViewHolder(val binding: ItemUsuarioBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val binding = ItemUsuarioBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UsuarioViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = listaUsuarios[position]
        val context = holder.binding.root.context

        holder.binding.tvNombre.text = usuario.nombre
        holder.binding.tvEmpleo.text = usuario.profesion
        holder.binding.tvDescripcion.text = usuario.descripcion

        // Calificación en estrellas (solo texto por ahora)
        holder.binding.tvCalificacion.text = "⭐ ${usuario.calificacion}"

        // Cargar imagen a partir del nombre (string) del drawable
        val imagenRes = getDrawableId(context, usuario.imagen)
        if (imagenRes != 0) {
            holder.binding.imgUsuario.setImageResource(imagenRes)
        }

        // Botón solicitar
        holder.binding.btnSolicitar.setOnClickListener {
            listener.onSolicitarClick(usuario)
        }

        // Click en la card → ver perfil
        holder.binding.root.setOnClickListener {
            listener.onVerPerfilClick(usuario)
        }
    }

    override fun getItemCount(): Int = listaUsuarios.size

    // ⭐ Para filtros dinámicos (Plomería, Electricidad, etc.)
    fun updateList(nuevaLista: List<Usuario>) {
        listaUsuarios = nuevaLista
        notifyDataSetChanged()
    }

    // ⭐ Convierte nombre (string) a drawable resource
    private fun getDrawableId(context: Context, imagenNombre: String): Int {
        return context.resources.getIdentifier(
            imagenNombre,  // por ej “plomero1”
            "drawable",
            context.packageName
        )
    }
}

