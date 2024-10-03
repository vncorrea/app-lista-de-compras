import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lista_de_compras.data.model.ProdutoItem
import com.example.lista_de_compras.databinding.ActivityProdutosBinding


class ProdutoAdapter(
    private val produtos: MutableList<ProdutoItem>,
    private val onCheckChange: (ProdutoItem) -> Unit
) : RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder>() {

    inner class ProdutoViewHolder(val binding: ActivityProdutosBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(produto: ProdutoItem) {
            binding.itemName.text = produto.nome
            binding.itemDetails.text = "${produto.quantidade}, ${produto.unidade}"

            binding.itemCheck.setOnCheckedChangeListener(null)

            binding.itemCheck.isChecked = produto.isChecked

            // Adicionar o novo listener
            binding.itemCheck.setOnCheckedChangeListener { _, isChecked ->
                produto.isChecked = isChecked
                onCheckChange(produto)
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
}


