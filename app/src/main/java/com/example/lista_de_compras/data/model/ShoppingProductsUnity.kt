import java.io.Serializable

class ShoppingProductsUnity(val name: String) : Serializable {

    companion object {
        private val unities: List<ShoppingProductsUnity> by lazy {
            createUnities()
        }

        fun createUnities(): List<ShoppingProductsUnity> {
            return listOf(
                ShoppingProductsUnity("un"),
                ShoppingProductsUnity("kg"),
                ShoppingProductsUnity("l"),
                ShoppingProductsUnity("g"),
                ShoppingProductsUnity("ml"),
            )
        }

        fun findUnidade(name: String): ShoppingProductsUnity {
            return unities.find { it.name == name }
                ?: throw IllegalArgumentException("Unidade não encontrada: $name")
        }
    }
}
