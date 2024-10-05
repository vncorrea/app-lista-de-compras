package com.example.lista_de_compras.data.repository

import android.util.Log
import com.example.lista_de_compras.data.model.ShoppingProducts

class ShoppingProductsRepository private constructor() {
    private val shoppingProducts = mutableListOf<ShoppingProducts>()

    companion object {
        @Volatile
        private var INSTANCE: ShoppingProductsRepository? = null

        fun getInstance(): ShoppingProductsRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = ShoppingProductsRepository()
                INSTANCE = instance
                instance
            }
        }
    }

    fun add(shoppingProducts: ShoppingProducts) {
        Log.d("ShoppingProductsRepository", "Adding product: ${shoppingProducts.name}")
        this.shoppingProducts.add(shoppingProducts)
    }

    fun getAll(): List<ShoppingProducts> {
        return shoppingProducts
    }

    fun getProductsByListId(listId: Int): List<ShoppingProducts> {
        return this.shoppingProducts.filter { it.list.id == listId }
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
