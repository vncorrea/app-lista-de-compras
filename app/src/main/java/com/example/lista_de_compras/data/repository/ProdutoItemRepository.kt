package com.example.lista_de_compras.data.repository

import com.example.lista_de_compras.data.model.ProdutoItem

class ProdutoItemRepository {
    private val produtos = mutableListOf<ProdutoItem>()

    fun add(produtoItem: ProdutoItem) {
        produtos.add(produtoItem)
    }

    fun getAll(): List<ProdutoItem> {
        return produtos
    }

    fun remove(produtoItem: ProdutoItem) {
        produtos.remove(produtoItem)
    }

    fun update(produtoItem: ProdutoItem) {
        val index = produtos.indexOfFirst { it.id == produtoItem.id }
        if (index != -1) {
            produtos[index] = produtoItem
        }
    }
}
