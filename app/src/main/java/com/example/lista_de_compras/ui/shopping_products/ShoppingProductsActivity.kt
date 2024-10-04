package com.example.lista_de_compras.ui.shopping_products

import ShoppingProductsCategory
import ShoppingProductsUnity
import android.R
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.data.model.ShoppingProducts
import com.example.lista_de_compras.databinding.ActivityShoppingProductsBinding
import com.example.lista_de_compras.viewmodel.ShoppingProductsViewModel
import java.util.UUID

class ShoppingProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingProductsBinding
    private val viewModel: ShoppingProductsViewModel by viewModels()
    private var isEditing = false
    private var productForEditing: ShoppingProducts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        checkForEditing()

        binding.btnAdd.setOnClickListener { handleAddButtonClick() }
        binding.fabDelete.setOnClickListener { handleDeleteButtonClick() }
    }

    private fun setupSpinners() {
        val categories = ShoppingProductsCategory.createCategories().map { it.name }
        val unities = ShoppingProductsUnity.createUnities().map { it.name }

        binding.spCategory.adapter = createAdapter(categories)
        binding.spUnity.adapter = createAdapter(unities)
    }

    private fun createAdapter(items: List<String>): ArrayAdapter<String> {
        return ArrayAdapter(this, R.layout.simple_spinner_item, items).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
    }

    private fun checkForEditing() {
        productForEditing = intent.getSerializableExtra("edit_product") as? ShoppingProducts
        if (productForEditing != null) {
            isEditing = true
            preparingEdition(productForEditing!!)
        }
    }

    private fun handleDeleteButtonClick() {
        val intent = Intent().apply {
            putExtra(
                "delete_product",
                deleteProdutoItem(productForEditing!!)
            )
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun handleAddButtonClick() {
        val nome = binding.inputNome.text.toString()
        val quantidade = binding.etQuantity.text.toString().toIntOrNull() ?: 0
        val unidade = binding.spUnity.selectedItem.toString()
        val categoria = binding.spCategory.selectedItem.toString()

        if (nome.isNotEmpty() && quantidade > 0) {
            val intent = Intent().apply {
                putExtra(
                    if (isEditing) "edit_product" else "new_product",
                    createProdutoItem(nome, quantidade, unidade, categoria)
                )
            }
            setResult(RESULT_OK, intent)
            finish()
        } else {
            showInputErrors()
        }
    }

    private fun deleteProdutoItem(produto: ShoppingProducts): ShoppingProducts {
        return produto
    }

    private fun createProdutoItem(
        nome: String,
        quantity: Int,
        unity: String,
        category: String
    ): ShoppingProducts {
        return ShoppingProducts(
            id = if (isEditing) productForEditing?.id
                ?: 0 else UUID.randomUUID().mostSignificantBits.toInt(),
            nome = nome,
            quantity = quantity,
            unity = ShoppingProductsUnity.findUnidade(unity),
            isChecked = productForEditing?.isChecked ?: false,
            category = ShoppingProductsCategory.findCategory(category)
        )
    }

    private fun showInputErrors() {
        binding.inputNome.error = "Campo obrigatório"
        binding.etQuantity.error = "Campo obrigatório"
    }

    private fun preparingEdition(produto: ShoppingProducts) {
        binding.inputNome.setText(produto.nome)
        binding.etQuantity.setText(produto.quantity.toString())
        binding.spCategory.setSelection(
            getIndexCategoryForItem(
                produto.category.name,
                ShoppingProductsCategory.createCategories()
            )
        )
        binding.spUnity.setSelection(
            getIndexUnityForItem(
                produto.unity.name,
                ShoppingProductsUnity.createUnities()
            )
        )
        binding.btnAdd.text = "Salvar"
        binding.tvTitle.text = "Editar Produto"
        binding.fabDelete.show();
    }

    private fun getIndexCategoryForItem(
        name: String,
        createCategories: List<ShoppingProductsCategory>
    ): Int {
        return createCategories.indexOfFirst { it.name == name }
    }

    private fun getIndexUnityForItem(
        name: String,
        createUnities: List<ShoppingProductsUnity>
    ): Int {
        return createUnities.indexOfFirst { it.name == name }
    }
}