package com.example.lista_de_compras.data.model

class ProdutoItem(
    var nome: String,
    var quantidade: Int,
    var unidade: String,
    var isChecked: Boolean
) {
    private var categoria: String = ""

    fun setCategoria(categoria: String) {
        this.categoria = categoria
    }

    fun getCategoria(): String {
        return this.categoria
    }
}
