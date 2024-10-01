package com.example.lista_de_compras.ui.lista_produtos

import ProdutoAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.data.model.ProdutoItem
import com.example.lista_de_compras.data.model.ProdutoItemCategoria
import com.example.lista_de_compras.databinding.ActivityListaProdutosBinding

class ListaProdutosActivity: AppCompatActivity() {
    private lateinit var binding: ActivityListaProdutosBinding
    private lateinit var produtoAdapter: ProdutoAdapter
    private val produtos = mutableListOf<ProdutoItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        produtoAdapter = ProdutoAdapter(produtos)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = produtoAdapter

        val categorias = ProdutoItemCategoria.createCategorias()
        produtos.add(ProdutoItem("Maçã", 2, "unidade", false, categorias[0]))
        produtos.add(ProdutoItem("Feijão", 1, "kg", false, categorias[5]))
        produtos.add(ProdutoItem("Macarrão", 500, "g", false, categorias[5]))

        produtoAdapter.notifyDataSetChanged()

        binding.addButton.setOnClickListener {
            produtos.add(ProdutoItem("Novo Produto", 1, "unidade", false, categorias[0]))
            produtoAdapter.notifyItemInserted(produtos.size - 1)
        }
    }
}
