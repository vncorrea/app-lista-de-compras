import java.io.Serializable

data class ShoppingList(
    val id: Int,
    val name: String,
    val items: MutableList<String> = mutableListOf(),
    val picture: String? = null
): Serializable
