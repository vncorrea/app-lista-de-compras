package com.example.lista_de_compras.ui.produto_item

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.data.model.ProdutoItem
import com.example.lista_de_compras.databinding.ActivityCriarProdutoItemBinding
import com.example.lista_de_compras.viewmodel.ProdutoItemListaViewModel

class CriarProdutoItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCriarProdutoItemBinding
    private val viewModel: ProdutoItemListaViewModel by viewModels() // Usar ViewModelProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarProdutoItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAdicionar.setOnClickListener {
            val nome = binding.inputNome.text.toString()
            val quantidade = binding.etQuantidade.text.toString().toIntOrNull() ?: 0
            val unidade = binding.spUnidade.selectedItem.toString()
            val categoria = binding.spCategoria.selectedItem.toString()

            if (nome.isNotEmpty() && quantidade > 0) {
                val intent = Intent()
                intent.putExtra("novo_produto", ProdutoItem(nome, quantidade, unidade, false, ProdutoItemCategoria.findCategoria(categoria)))
                setResult(RESULT_OK, intent)
                finish()
            } else {
                binding.inputNome.error = "Campo obrigatório"
                binding.etQuantidade.error = "Campo obrigatório"
            }
        }
    }
}
