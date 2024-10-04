package com.example.lista_de_compras.data.model

import ShoppingProductsCategory
import ShoppingProductsUnity
import java.io.Serializable

data class ShoppingProducts(
    val id: Int,
    val name: String,
    val quantity: Int,
    val unity: ShoppingProductsUnity,
    var isChecked: Boolean,
    val category: ShoppingProductsCategory
) : Serializable
