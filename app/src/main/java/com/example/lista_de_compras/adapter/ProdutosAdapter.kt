import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lista_de_compras.data.model.ProdutoItem
import com.example.lista_de_compras.databinding.ActivityProdutosBinding

class ProdutoAdapter(
    private var produtos: MutableList<ProdutoItem>,
    private val onCheckChange: (ProdutoItem) -> Unit
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {
    var onLongClick: ((ProdutoItem) -> Unit)? = null

    inner class ProdutoViewHolder(val binding: ActivityProdutosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(produto: ProdutoItem) {
            binding.itemName.text = produto.nome
            binding.itemDetails.text = "${produto.quantidade}, ${produto.unidade.nome}"

            binding.itemCheck.setOnCheckedChangeListener(null)

            binding.itemCheck.isChecked = produto.isChecked

            binding.itemCheck.setOnCheckedChangeListener { _, isChecked ->
                produto.isChecked = isChecked
                onCheckChange(produto)
            }

            binding.root.setOnLongClickListener {
                onLongClick?.invoke(produto)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProdutoViewHolder {
        val binding =
            ActivityProdutosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProdutoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProdutoViewHolder, position: Int) {
        holder.bind(produtos[position])
    }

    override fun getItemCount(): Int = produtos.size

    fun updateList(newList: List<ProdutoItem>) {
        produtos.clear()
        produtos.addAll(newList)
        notifyDataSetChanged()
    }
}
