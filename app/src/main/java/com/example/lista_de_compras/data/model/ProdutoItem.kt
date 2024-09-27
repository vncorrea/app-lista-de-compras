package com.example.lista_de_compras.data.model

class ProdutoItem {
    private var nome: String = ""
    private var quantidade: Int = 0
    private var unidade: String = ""
    private var categoria: String = ""

    fun setNome(nome: String) {
        this.nome = nome
    }

    fun getNome(): String {
        return this.nome
    }

    fun setQuantidade(quantidade: Int) {
        this.quantidade = quantidade
    }

    fun getQuantidade(): Int {
        return this.quantidade
    }

    fun setUnidade(unidade: String) {
        this.unidade = unidade
    }

    fun getUnidade(): String {
        return this.unidade
    }

    fun setCategoria(categoria: String) {
        this.categoria = categoria
    }

    fun getCategoria(): String {
        return this.categoria
    }
}