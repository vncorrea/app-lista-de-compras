package com.example.lista_de_compras.ui.lista_produtos

import ProdutoAdapter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.data.model.ProdutoItem
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

        produtos.add(ProdutoItem("Arroz", 2, "kg", false))
        produtos.add(ProdutoItem("Feijão", 1, "kg", true))
        produtos.add(ProdutoItem("Macarrão", 500, "g", false))

        produtoAdapter.notifyDataSetChanged()
        
        binding.addButton.setOnClickListener {
            produtos.add(ProdutoItem("Novo Produto", 1, "unidade", false))
            produtoAdapter.notifyItemInserted(produtos.size - 1)
        }
    }
}
