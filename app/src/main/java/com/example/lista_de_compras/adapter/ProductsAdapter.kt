import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lista_de_compras.data.model.ShoppingProducts
import com.example.lista_de_compras.databinding.ActivityProductsBinding

class ProductsAdapter(
    private var shoppingProducts: MutableList<ShoppingProducts>,
    private val onCheckChange: (ShoppingProducts) -> Unit
) : RecyclerView.Adapter<ProductsAdapter.productViewHolder>() {
    var onLongClick: ((ShoppingProducts) -> Unit)? = null

    inner class productViewHolder(val binding: ActivityProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ShoppingProducts) {
            binding.apply {
                itemName.text = product.nome
                itemDetails.text = "${product.quantity}, ${product.unity.name}"
                itemCheck.setOnCheckedChangeListener(null)
                itemCheck.isChecked = product.isChecked
                itemCheck.setOnCheckedChangeListener { _, isChecked ->
                    product.isChecked = isChecked
                    onCheckChange(product)
                }
                root.setOnLongClickListener {
                    onLongClick?.invoke(product)
                    true
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
        val binding = ActivityProductsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return productViewHolder(binding)
    }

    override fun onBindViewHolder(holder: productViewHolder, position: Int) {
        holder.bind(shoppingProducts[position])
    }

    override fun getItemCount(): Int = shoppingProducts.size

    fun updateList(newList: List<ShoppingProducts>) {
        shoppingProducts.apply {
            clear()
            addAll(newList)
        }
        notifyDataSetChanged()
    }
}