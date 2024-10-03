package com.example.lista_de_compras.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lista_de_compras.data.model.ProdutoItem
import com.example.lista_de_compras.data.repository.ProdutoItemRepository

class ProdutoItemListaViewModel : ViewModel() {
    private val repository = ProdutoItemRepository()
    private val _produtos = MutableLiveData<MutableList<ProdutoItem>>(mutableListOf())
    val produtos: LiveData<MutableList<ProdutoItem>> = _produtos

    fun adicionarProduto(produtoItem: ProdutoItem) {
        repository.add(produtoItem)
        // Atualizar a LiveData após adicionar o produto
        _produtos.value = repository.getAll().toMutableList()
    }

    fun getProdutos() {
        _produtos.value = repository.getAll().toMutableList()
    }

    fun removerProduto(produtoItem: ProdutoItem) {
        repository.remove(produtoItem)
        _produtos.value = repository.getAll().toMutableList() // Atualizar a lista após remover
    }

    fun atualizarProduto(produto: ProdutoItem) {
        repository.update(produto)
        _produtos.value = repository.getAll().toMutableList()
    }
}

