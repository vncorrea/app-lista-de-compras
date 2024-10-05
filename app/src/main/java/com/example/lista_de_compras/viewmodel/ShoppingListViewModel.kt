package com.example.lista_de_compras.viewmodel

import ShoppingList
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lista_de_compras.data.repository.ShoppingListRepository

class ShoppingListViewModel : ViewModel() {
    private val repository = ShoppingListRepository()
    private val _lists = MutableLiveData<MutableList<ShoppingList>>(mutableListOf())
    val lists: LiveData<MutableList<ShoppingList>> = _lists

    fun add(list: ShoppingList) {
        repository.add(list)
        _lists.value = repository.getAll().toMutableList()
    }

    fun getLists() {
        _lists.value = repository.getAll().toMutableList()
    }

    fun remove(list: ShoppingList) {
        repository.remove(list)
        _lists.value = repository.getAll().toMutableList()
    }

    fun update(list: ShoppingList) {
        repository.update(list)
        _lists.value = repository.getAll().toMutableList()
    }
}

