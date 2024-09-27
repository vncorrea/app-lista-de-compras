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
        val index = this.produtoItem.indexOfFirst { it.getNome() == produtoItem.getNome() }
        this.produtoItem[index] = produtoItem

        return Result.success(produtoItem)
    }

    fun getAll(): List<ProdutoItem> {
        return this.produtoItem
    }

    fun getByName(nome: String): ProdutoItem? {
        return this.produtoItem.find { it.getNome() == nome }
    }

    fun getByCategory(categoria: String): List<ProdutoItem> {
        return this.produtoItem.filter { it.getCategoria() == categoria }
    }
}