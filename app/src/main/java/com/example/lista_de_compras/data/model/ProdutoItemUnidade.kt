import java.io.Serializable

class ProdutoItemUnidade(val nome: String) : Serializable {

    companion object {
        private val unidades: List<ProdutoItemUnidade> by lazy {
            createUnidades()
        }

        fun createUnidades(): List<ProdutoItemUnidade> {
            return listOf(
                ProdutoItemUnidade("un"),
                ProdutoItemUnidade("kg"),
                ProdutoItemUnidade("l"),
                ProdutoItemUnidade("g"),
                ProdutoItemUnidade("ml"),
            )
        }

        fun findUnidade(nome: String): ProdutoItemUnidade {
            return unidades.find { it.nome == nome }
                ?: throw IllegalArgumentException("Unidade não encontrada: $nome")
        }
    }
}
