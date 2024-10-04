package com.example.lista_de_compras.ui.produto_item

import ProdutoItemCategoria
import ProdutoItemUnidade
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

        setupSpinners()
        checkForEditing()

        binding.btnAdicionar.setOnClickListener { handleAddButtonClick() }
        binding.fabExcluir.setOnClickListener { handleDeleteButtonClick() }
    }

    private fun setupSpinners() {
        val categorias = ProdutoItemCategoria.createCategorias().map { it.nome }
        val unidades = ProdutoItemUnidade.createUnidades().map { it.nome }

        binding.spCategoria.adapter = createAdapter(categorias)
        binding.spUnidade.adapter = createAdapter(unidades)
    }

    private fun createAdapter(items: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(this, R.layout.simple_spinner_item, items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun checkForEditing() {
        produtoParaEditar = intent.getSerializableExtra("produto_editar") as? ProdutoItem
        if (produtoParaEditar != null) {
            isEditing = true
            preparingEdition(produtoParaEditar!!)
        }
    }

    private fun handleDeleteButtonClick() {
        val intent = Intent().apply {
            putExtra(
                "excluir_produto",
                deleteProdutoItem(produtoParaEditar!!)
            )
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun handleAddButtonClick() {
        val nome = binding.inputNome.text.toString()
        val quantidade = binding.etQuantidade.text.toString().toIntOrNull() ?: 0
        val unidade = binding.spUnidade.selectedItem.toString()
        val categoria = binding.spCategoria.selectedItem.toString()

        if (nome.isNotEmpty() && quantidade > 0) {
            val intent = Intent().apply {
                putExtra(
                    if (isEditing) "produto_editado" else "novo_produto",
                    createProdutoItem(nome, quantidade, unidade, categoria)
                )
            }
            setResult(RESULT_OK, intent)
            finish()
        } else {
            showInputErrors()
        }
    }

    private fun deleteProdutoItem(produto: ProdutoItem): ProdutoItem {
        return produto
    }

    private fun createProdutoItem(
        nome: String,
        quantidade: Int,
        unidade: String,
        categoria: String
    ): ProdutoItem {
        return ProdutoItem(
            id = if (isEditing) produtoParaEditar?.id
                ?: 0 else UUID.randomUUID().mostSignificantBits.toInt(),
            nome = nome,
            quantidade = quantidade,
            unidade = ProdutoItemUnidade.findUnidade(unidade),
            isChecked = produtoParaEditar?.isChecked ?: false,
            categoria = ProdutoItemCategoria.findCategoria(categoria)
        )
    }

    private fun showInputErrors() {
        binding.inputNome.error = "Campo obrigatório"
        binding.etQuantidade.error = "Campo obrigatório"
    }

    private fun preparingEdition(produto: ProdutoItem) {
        binding.inputNome.setText(produto.nome)
        binding.etQuantidade.setText(produto.quantidade.toString())
        binding.spCategoria.setSelection(
            getIndexCategoryForItem(
                produto.categoria.nome,
                ProdutoItemCategoria.createCategorias()
            )
        )
        binding.spUnidade.setSelection(
            getIndexUnityForItem(
                produto.unidade.nome,
                ProdutoItemUnidade.createUnidades()
            )
        )
        binding.btnAdicionar.text = "Salvar"
        binding.tvTitulo.text = "Editar Produto"
        binding.fabExcluir.show();
    }

    private fun getIndexCategoryForItem(
        nome: String,
        createCategorias: List<ProdutoItemCategoria>
    ): Int {
        return createCategorias.indexOfFirst { it.nome == nome }
    }

    private fun getIndexUnityForItem(nome: String, createUnidades: List<ProdutoItemUnidade>): Int {
        return createUnidades.indexOfFirst { it.nome == nome }
    }
}