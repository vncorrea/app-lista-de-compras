import java.io.Serializable

class ProdutoItemCategoria(val nome: String): Serializable {

    companion object {
        private val categorias: List<ProdutoItemCategoria> by lazy {
            createCategorias()
        }

        fun createCategorias(): List<ProdutoItemCategoria> {
            return listOf(
                ProdutoItemCategoria("Fruta"),
                ProdutoItemCategoria("Verdura"),
                ProdutoItemCategoria("Carne"),
                ProdutoItemCategoria("Cereal"),
                ProdutoItemCategoria("Laticínio"),
                ProdutoItemCategoria("Carboidrato"),
                ProdutoItemCategoria("Outros")
            )
        }

        fun findCategoria(nome: String): ProdutoItemCategoria {
            return categorias.find { it.nome == nome } ?: throw IllegalArgumentException("Categoria não encontrada: $nome")
        }
    }
}
