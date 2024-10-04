package com.example.lista_de_compras.ui.produto_item

import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.data.model.ProdutoItem
import com.example.lista_de_compras.databinding.ActivityCriarProdutoItemBinding
import com.example.lista_de_compras.viewmodel.ProdutoItemListaViewModel
import java.util.UUID

class CriarProdutoItemActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCriarProdutoItemBinding
    private val viewModel: ProdutoItemListaViewModel by viewModels()
    private var isEditing = false
    private var produtoParaEditar: ProdutoItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarProdutoItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val categorias = ProdutoItemCategoria.createCategorias().map { it.nome }
        val unidades = ProdutoItemUnidade.createUnidades().map { it.nome }

        val adapterCategoria = ArrayAdapter(this, R.layout.simple_spinner_item, categorias)
        adapterCategoria.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val adapterUnidade = ArrayAdapter(this, R.layout.simple_spinner_item, unidades)
        adapterUnidade.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spCategoria.adapter = adapterCategoria
        binding.spUnidade.adapter = adapterUnidade

        produtoParaEditar = intent.getSerializableExtra("produto_editar") as? ProdutoItem
        if (produtoParaEditar != null) {
            isEditing = true
            preencherCamposComProduto(produtoParaEditar!!)
        }

        binding.btnAdicionar.setOnClickListener {
            val nome = binding.inputNome.text.toString()
            val quantidade = binding.etQuantidade.text.toString().toIntOrNull() ?: 0
            val unidade = binding.spUnidade.selectedItem.toString()
            val categoria = binding.spCategoria.selectedItem.toString()

            if (nome.isNotEmpty() && quantidade > 0) {
                val intent = Intent()
                if (isEditing) {
                    val produtoEditado = ProdutoItem(
                        id = produtoParaEditar?.id ?: 0,
                        nome = nome,
                        quantidade = quantidade,
                        unidade = ProdutoItemUnidade.findUnidade(unidade),
                        isChecked = produtoParaEditar?.isChecked ?: false,
                        categoria = ProdutoItemCategoria.findCategoria(categoria)
                    )
                    intent.putExtra("produto_editado", produtoEditado)
                } else {
                    val produtoNovo = ProdutoItem(
                        id = UUID.randomUUID().mostSignificantBits.toInt(),
                        nome = nome,
                        quantidade = quantidade,
                        unidade = ProdutoItemUnidade.findUnidade(unidade),
                        isChecked = false,
                        categoria = ProdutoItemCategoria.findCategoria(categoria)
                    )
                    intent.putExtra("novo_produto", produtoNovo)
                }

                setResult(RESULT_OK, intent)
                finish()
            } else {
                binding.inputNome.error = "Campo obrigatório"
                binding.etQuantidade.error = "Campo obrigatório"
            }
        }

    }

    private fun preencherCamposComProduto(produto: ProdutoItem) {
        binding.inputNome.setText(produto.nome)
        binding.etQuantidade.setText(produto.quantidade.toString())

        val categoriaIndex = ProdutoItemCategoria.createCategorias().indexOfFirst {
            it.nome == produto.categoria.nome
        }
        binding.spCategoria.setSelection(categoriaIndex)

        val unidadeIndex = ProdutoItemUnidade.createUnidades().indexOfFirst {
            it.nome == produto.unidade.nome
        }
        binding.spUnidade.setSelection(unidadeIndex)

        binding.btnAdicionar.text = "Salvar"
    }
}

