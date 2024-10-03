package com.example.lista_de_compras.data.repository

import com.example.lista_de_compras.data.model.ProdutoItem

class ProdutoItemRepository {
    private val produtoItem = mutableListOf<ProdutoItem>()

    fun add(produtoItem: ProdutoItem): Result<ProdutoItem> {
        this.produtoItem.add(produtoItem)

        return Result.success(produtoItem)
    }

    fun remove(produtoItem: ProdutoItem): Result<ProdutoItem> {
        this.produtoItem.remove(produtoItem)

        return Result.success(produtoItem)
    }

    fun update(produtoItem: ProdutoItem): Result<ProdutoItem> {
        val index = this.produtoItem.indexOfFirst { it.nome == produtoItem.nome }
        if (index != -1) {
            this.produtoItem[index] = produtoItem
            return Result.success(produtoItem)
        }
        return Result.failure(Exception("Produto não encontrado"))
    }


    fun getAll(): List<ProdutoItem> {
        return this.produtoItem
    }

    fun getByName(nome: String): ProdutoItem? {
        return this.produtoItem.find { it.nome == nome }
    }
}