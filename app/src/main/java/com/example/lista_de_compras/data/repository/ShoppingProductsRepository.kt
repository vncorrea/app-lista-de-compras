package com.example.lista_de_compras.data.repository

import com.example.lista_de_compras.data.model.ShoppingProducts

class ShoppingProductsRepository {
    private val shoppingProducts = mutableListOf<ShoppingProducts>()

    fun add(shoppingProducts: ShoppingProducts) {
        this.shoppingProducts.add(shoppingProducts)
    }

    fun getAll(): List<ShoppingProducts> {
        return shoppingProducts
    }

    fun getProductsByListId(listId: Int): List<ShoppingProducts> {
        return shoppingProducts.filter { it.id == listId }
    }

    fun remove(shoppingProducts: ShoppingProducts) {
        val index = this.shoppingProducts.indexOfFirst { it.id == shoppingProducts.id }
        if (index != -1) {
            this.shoppingProducts.removeAt(index)
        }
    }

    fun update(shoppingProducts: ShoppingProducts) {
        val index = this.shoppingProducts.indexOfFirst { it.id == shoppingProducts.id }
        if (index != -1) {
            this.shoppingProducts[index] = shoppingProducts
        }
    }
}
