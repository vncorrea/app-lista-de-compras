data class ShoppingList(
    val name: String,      // Nome da lista (ex: "Lista do Mercado")
    val items: MutableList<String> = mutableListOf()  // Itens da lista de compras
)
