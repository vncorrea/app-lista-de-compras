package com.example.lista_de_compras.data.model

class ProdutoItem(
    var nome: String,
    var quantidade: Int,
    var unidade: String,
    var isChecked: Boolean,
    var categoria: ProdutoItemCategoria
) {

}
