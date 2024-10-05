package com.example.lista_de_compras.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lista_de_compras.data.model.ShoppingProducts
import com.example.lista_de_compras.data.repository.ShoppingProductsRepository

class ShoppingProductsViewModel : ViewModel() {
    private val repository = ShoppingProductsRepository()
    private val _products = MutableLiveData<MutableList<ShoppingProducts>>(mutableListOf())
    val products: LiveData<MutableList<ShoppingProducts>> = _products

    fun add(shoppingProducts: ShoppingProducts) {
        repository.add(shoppingProducts)
        _products.value = repository.getAll().toMutableList()
    }

    fun getProducts() {
        _products.value = repository.getAll().toMutableList()
    }

    fun getProductsByListId(listId: Int) {
        _products.value = repository.getProductsByListId(listId).toMutableList()
    }

    fun remove(shoppingProducts: ShoppingProducts) {
        repository.remove(shoppingProducts)
        _products.value = repository.getAll().toMutableList()
    }

    fun update(product: ShoppingProducts) {
        repository.update(product)
        _products.value = repository.getAll().toMutableList()
    }
}

