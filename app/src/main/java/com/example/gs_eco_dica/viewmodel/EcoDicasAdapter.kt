
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gs_eco_dica.R
import com.example.gs_eco_dica.model.EcoDicaModel

class EcoDicasAdapter(private val onItemRemoved: (EcoDicaModel) -> Unit) :
    RecyclerView.Adapter<EcoDicasAdapter.ItemViewHolder>() {

    private var items = listOf<EcoDicaModel>()

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val textViewDica = view.findViewById<TextView>(R.id.textViewItemDica)
        val textViewDesc = view.findViewById<TextView>(R.id.textViewItemDesc)
        val button = view.findViewById<ImageButton>(R.id.imageButton)

        fun bind(item: EcoDicaModel) {
            textViewDica.text = item.title
            textViewDesc.text = item.description
            button.setOnClickListener {
                onItemRemoved(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.eco_dicas_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    fun updateEcoDicas(newItems: List<EcoDicaModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}