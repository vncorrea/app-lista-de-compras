package com.example.lista_de_compras.ui.lista_produtos

import ProdutoAdapter
import android.content.Intent
import android.os.Bundle
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

        // Inicializa o ViewModel
        viewModel = ViewModelProvider(this).get(ProdutoItemListaViewModel::class.java)

        produtoAdapter = ProdutoAdapter(mutableListOf()) { produto ->
            ordenarProdutosPorCheck()
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = produtoAdapter

        viewModel.produtos.observe(this) { produtos ->
            produtoAdapter.updateList(produtos)
        }

        viewModel.getProdutos()

        binding.addButton.setOnClickListener {
            val intent = Intent(this, CriarProdutoItemActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    val novoProduto = data.getSerializableExtra("novo_produto") as ProdutoItem
                    viewModel.adicionarProduto(novoProduto)
                    viewModel.getProdutos()
                }
            }
        }

    private fun ordenarProdutosPorCheck() {
        viewModel.produtos.value?.let { produtos ->
            val produtosOrdenados = produtos.sortedBy { it.isChecked }
            produtoAdapter.updateList(produtosOrdenados)
        }
    }
}
