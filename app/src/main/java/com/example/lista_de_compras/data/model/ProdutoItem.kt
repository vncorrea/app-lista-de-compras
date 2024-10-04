package com.example.lista_de_compras.data.model

import ProdutoItemCategoria
import ProdutoItemUnidade
import java.io.Serializable

data class ProdutoItem(
    val nome: String,
    val quantidade: Int,
    val unidade: ProdutoItemUnidade,
    var isChecked: Boolean,
    val categoria: ProdutoItemCategoria
) : Serializable
