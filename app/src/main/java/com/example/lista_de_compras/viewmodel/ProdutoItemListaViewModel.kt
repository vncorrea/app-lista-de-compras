package com.example.lista_de_compras.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lista_de_compras.data.model.ProdutoItem
import com.example.lista_de_compras.data.repository.ProdutoItemRepository

class ProdutoItemListaViewModel {
    private val repository = ProdutoItemRepository();

    private val _produtoItemResult = MutableLiveData<Result<ProdutoItem>>()
    val produtoItemResult: LiveData<Result<ProdutoItem>> = _produtoItemResult

    fun adicionarProduto(produtoItem: ProdutoItem) {
        val result = repository.add(produtoItem)
        _produtoItemResult.value = result
    }

    fun removerProduto(produtoItem: ProdutoItem) {
        val result = repository.remove(produtoItem)
        _produtoItemResult.value = result
    }

    fun getProdutos(): List<ProdutoItem> {
        return repository.getAll()
    }

    fun getProdutoPorNome(nome: String): ProdutoItem? {
        return repository.getByName(nome)
    }

    fun atualizarProduto(produtoItem: ProdutoItem) {
        val result = repository.update(produtoItem)
        _produtoItemResult.value = result
    }
}