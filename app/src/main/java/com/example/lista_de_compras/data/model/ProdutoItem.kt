package com.example.lista_de_compras.data.model

import ProdutoItemCategoria
import java.io.Serializable

class ProdutoItem(
    var nome: String,
    var quantidade: Int,
    var unidade: String,
    var isChecked: Boolean,
    var categoria: ProdutoItemCategoria
): Serializable {

}
