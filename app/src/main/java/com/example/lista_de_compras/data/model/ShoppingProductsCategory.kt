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

        fun findCategory(name: String): ShoppingProductsCategory {
            return categories.find { it.name == name } ?: throw IllegalArgumentException("Categoria não encontrada: $name")
        }
    }
}
