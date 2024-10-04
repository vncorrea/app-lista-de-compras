package com.example.lista_de_compras.data.model

import ProdutoItemCategoria
import ProdutoItemUnidade
import java.io.Serializable

class ProdutoItem(
    var id: String,
    var nome: String,
    var quantidade: Int,
    var unidade: ProdutoItemUnidade,
    var isChecked: Boolean,
    var categoria: ProdutoItemCategoria
): Serializable {

}
