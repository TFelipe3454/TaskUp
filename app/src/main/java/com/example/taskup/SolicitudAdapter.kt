import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskup.Solicitud
import com.example.taskup.databinding.ItemSolicitudPrestadorBinding

class SolicitudAdapter(
    private val listaSolicitudes: List<Solicitud>,
    private val listener: OnSolicitudClickListener
) : RecyclerView.Adapter<SolicitudAdapter.SolicitudViewHolder>() {

    interface OnSolicitudClickListener {
        fun onAceptarClick(solicitud: Solicitud)
        fun onRechazarClick(solicitud: Solicitud)
    }

    inner class SolicitudViewHolder(val binding: ItemSolicitudPrestadorBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitudViewHolder {
        val binding = ItemSolicitudPrestadorBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return SolicitudViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SolicitudViewHolder, position: Int) {
        val solicitud = listaSolicitudes[position]
        holder.binding.tvHoraSolicitud.text = "Hora: ${solicitud.hora}"
        holder.binding.tvDireccionSolicitud.text = "Dirección: ${solicitud.direccion}"
        holder.binding.tvDescripcionSolicitud.text = "Servicio: ${solicitud.descripcion}"

        holder.binding.btnAceptar.setOnClickListener {
            listener.onAceptarClick(solicitud)
        }

        holder.binding.btnRechazar.setOnClickListener {
            listener.onRechazarClick(solicitud)
        }
    }

    override fun getItemCount(): Int = listaSolicitudes.size
}
