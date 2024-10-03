package com.example.lista_de_compras.data.repository

import android.util.Log
import com.example.lista_de_compras.data.model.ProdutoItem

class ProdutoItemRepository {
    private val produtos = mutableListOf<ProdutoItem>()

    fun add(produtoItem: ProdutoItem) {
        produtos.add(produtoItem)
        Log.d("ProdutoItemRepository", "Produto adicionado: $produtoItem")
    }

    fun getAll(): List<ProdutoItem> {
        Log.d("ProdutoItemRepository", "Produtos retornados: $produtos")
        return produtos
    }

    fun remove(produtoItem: ProdutoItem) {
        produtos.remove(produtoItem)
    }

    fun update(produtoItem: ProdutoItem) {
        val index = produtos.indexOfFirst { it.nome == produtoItem.nome }
        if (index != -1) {
            produtos[index] = produtoItem
        }
    }
}
