package com.example.lista_de_compras.ui.lista_produtos

import ProdutoAdapter
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.data.model.ProdutoItem
import com.example.lista_de_compras.databinding.ActivityListaProdutosBinding
import com.example.lista_de_compras.ui.produto_item.CriarProdutoItemActivity
import com.example.lista_de_compras.viewmodel.ProdutoItemListaViewModel

class ListaProdutosActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListaProdutosBinding
    private lateinit var produtoAdapter: ProdutoAdapter
    private lateinit var viewModel: ProdutoItemListaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaProdutosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ProdutoItemListaViewModel::class.java)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        produtoAdapter = ProdutoAdapter(mutableListOf()) { orderableProducts() }
        produtoAdapter.onLongClick = { produto ->
            val intent = Intent(this, CriarProdutoItemActivity::class.java)
            intent.putExtra("produto_editar", produto)
            resultLauncher.launch(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = produtoAdapter
    }

    private fun setupObservers() {
        viewModel.produtos.observe(this) { produtos ->
            produtoAdapter.updateList(produtos)
        }
        viewModel.getProdutos()
    }

    private fun setupListeners() {
        binding.addButton.setOnClickListener {
            val intent = Intent(this, CriarProdutoItemActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    val newProduct = data.getSerializableExtra("novo_produto") as? ProdutoItem
                    val editableProduct = data.getSerializableExtra("produto_editado") as? ProdutoItem
                    val deletedProduct = data.getSerializableExtra("excluir_produto") as? ProdutoItem

                    Log.d("deletedProduct", "deletedProduct: $deletedProduct")

                    if (editableProduct != null) {
                        viewModel.update(editableProduct)
                    } else if (newProduct != null) {
                        viewModel.add(newProduct)
                    } else if (deletedProduct != null) {
                        viewModel.remove(deletedProduct)
                    }

                    viewModel.getProdutos()
                }
            }
        }

    private fun orderableProducts() {
        viewModel.produtos.value?.let { produtos ->
            val orderableProducts = produtos.sortedBy { it.isChecked }
            produtoAdapter.updateList(orderableProducts)
        }
    }
}