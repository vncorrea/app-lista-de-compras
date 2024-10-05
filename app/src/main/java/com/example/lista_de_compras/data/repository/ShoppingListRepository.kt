package com.example.lista_de_compras.data.repository

import ShoppingList

class ShoppingListRepository {
    private val shoppingList = mutableListOf<ShoppingList>()

    fun add(shoppingList: ShoppingList) {
        this.shoppingList.add(shoppingList)
    }

    fun getAll(): List<ShoppingList> {
        return shoppingList
    }

    fun remove(shoppingList: ShoppingList) {
        val index = this.shoppingList.indexOfFirst { it.id == shoppingList.id }
        if (index != -1) {
            this.shoppingList.removeAt(index)
        }
    }

    fun update(shoppingList: ShoppingList) {
        val index = this.shoppingList.indexOfFirst { it.id == shoppingList.id }
        if (index != -1) {
            this.shoppingList[index] = shoppingList
        }
    }
}
