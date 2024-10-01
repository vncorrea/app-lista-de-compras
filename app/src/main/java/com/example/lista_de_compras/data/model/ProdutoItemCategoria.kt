package com.example.lista_de_compras.data.model

class ProdutoItemCategoria(val nome: String) {

    companion object {
        fun createCategorias(): List<ProdutoItemCategoria> {
            //quero icon de cada tipo de categoria

            val categorias = mutableListOf<ProdutoItemCategoria>()
            categorias.add(ProdutoItemCategoria("Fruta"))
            categorias.add(ProdutoItemCategoria("Verdura"))
            categorias.add(ProdutoItemCategoria("Carne"))
            categorias.add(ProdutoItemCategoria("Cereal"))
            categorias.add(ProdutoItemCategoria("Laticínio"))
            categorias.add(ProdutoItemCategoria("Carboidrato"))

            return categorias
        }
    }
}
