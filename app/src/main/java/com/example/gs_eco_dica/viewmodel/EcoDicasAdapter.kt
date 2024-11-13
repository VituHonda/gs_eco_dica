
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
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
        val dicaUrl = "https://www.fiap.com.br/graduacao/global-solution/"

        fun bind(item: EcoDicaModel) {
            textViewDica.text = item.title
            textViewDesc.text = item.description

            button.setOnClickListener {
                onItemRemoved(item)
            }

            itemView.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dicaUrl))
                itemView.context.startActivity(intent)
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

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val filteredList = if (charSequence.isNullOrEmpty()) {
                    items // Se a busca está vazia, mostra a lista completa
                } else {
                    val filterPattern = charSequence.toString().lowercase().trim()
                    items.filter { it.title.lowercase().contains(filterPattern) }
                }
                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(
                charSequence: CharSequence?,
                filterResults: FilterResults?
            ) {
                items = filterResults?.values as List<EcoDicaModel>
                notifyDataSetChanged()
            }
        }
    }

    fun updateEcoDicas(newItems: List<EcoDicaModel>) {
        items = newItems
        notifyDataSetChanged()
    }
}