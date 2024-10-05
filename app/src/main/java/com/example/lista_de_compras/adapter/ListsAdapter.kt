import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lista_de_compras.databinding.ActivityListBinding

class ListsAdapter(
    private var shoppingList: MutableList<ShoppingList>,
) : RecyclerView.Adapter<ListsAdapter.listsViewHolder>() {
    var onLongClick: ((ShoppingList) -> Unit)? = null

    inner class listsViewHolder(val binding: ActivityListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: ShoppingList) {
            binding.apply {
                tvNomeListaFeira.text = list.name

                root.setOnLongClickListener {
                    onLongClick?.invoke(list)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listsViewHolder {
        val binding =
            ActivityListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return listsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: listsViewHolder, position: Int) {
        holder.bind(shoppingList[position])
    }

    override fun getItemCount(): Int = shoppingList.size

    fun updateList(newList: List<ShoppingList>) {
        shoppingList.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }
}