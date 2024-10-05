import com.example.lista_de_compras.R
import java.io.Serializable

class ShoppingProductsCategory(val name: String): Serializable {

    companion object {
        private val categories: List<ShoppingProductsCategory> by lazy {
            createCategories()
        }

        fun createCategories(): List<ShoppingProductsCategory> {
            return listOf(
                ShoppingProductsCategory("Fruta"),
                ShoppingProductsCategory("Verdura"),
                ShoppingProductsCategory("Carne"),
                ShoppingProductsCategory("Cereal"),
                ShoppingProductsCategory("Laticínio"),
                ShoppingProductsCategory("Carboidrato"),
                ShoppingProductsCategory("Outros")
            )
        }

        fun getIconForCategory(name: String): Int {
            return when (name) {
                "Fruta" -> R.drawable.ic_fruta
                "Verdura" -> R.drawable.ic_verdura
                "Carne" -> R.drawable.ic_carne
                "Cereal" -> R.drawable.ic_cereal
                "Laticínio" -> R.drawable.ic_laticinio
                "Carboidrato" -> R.drawable.ic_carboidrato
                else -> R.drawable.ic_outros
            }
        }

        fun findCategory(name: String): ShoppingProductsCategory {
            return categories.find { it.name == name } ?: throw IllegalArgumentException("Categoria não encontrada: $name")
        }
    }
}
