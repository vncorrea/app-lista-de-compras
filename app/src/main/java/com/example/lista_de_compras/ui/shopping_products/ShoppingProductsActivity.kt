package com.example.lista_de_compras.ui.shopping_products

import ShoppingList
import ShoppingProductsCategory
import ShoppingProductsUnity
import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.data.model.ShoppingProducts
import com.example.lista_de_compras.databinding.ActivityShoppingProductsBinding
import com.example.lista_de_compras.viewmodel.ShoppingProductsViewModel
import java.util.UUID

class ShoppingProductsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingProductsBinding
    private var isEditing = false
    private var productForEditing: ShoppingProducts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingProductsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSpinners()
        checkForEditing()

        binding.btnBack.setOnClickListener { finish() }
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
                deleteShoppingProduct(productForEditing!!)
            )
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun handleAddButtonClick() {
        val name = binding.inputName.text.toString()
        val quantidade = binding.etQuantity.text.toString().toIntOrNull() ?: 0
        val unidade = binding.spUnity.selectedItem.toString()
        val categoria = binding.spCategory.selectedItem.toString()

        val selectedList = intent.getSerializableExtra("selected_list") as? ShoppingList

        if (name.isNotEmpty() && quantidade > 0 && selectedList != null) {
            val intent = Intent().apply {
                putExtra(
                    if (isEditing) "edit_product" else "new_product",
                    createShoppingProduct(name, quantidade, unidade, categoria, selectedList)
                )
            }
            setResult(RESULT_OK, intent)
            finish()
        } else {
            showInputErrors()
        }
    }

    private fun deleteShoppingProduct(product: ShoppingProducts): ShoppingProducts {
        return product
    }

    private fun createShoppingProduct(
        name: String,
        quantity: Int,
        unity: String,
        category: String,
        list: ShoppingList
    ): ShoppingProducts {
        return ShoppingProducts(
            id = if (isEditing) productForEditing?.id
                ?: 0 else UUID.randomUUID().mostSignificantBits.toInt(),
            name = name,
            quantity = quantity,
            unity = ShoppingProductsUnity.findUnidade(unity),
            isChecked = productForEditing?.isChecked ?: false,
            category = ShoppingProductsCategory.findCategory(category),
            list = list
        )
    }

    private fun showInputErrors() {
        binding.inputName.error = "Campo obrigatório"
        binding.etQuantity.error = "Campo obrigatório"
    }

    private fun preparingEdition(product: ShoppingProducts) {
        binding.inputName.setText(product.name)
        binding.etQuantity.setText(product.quantity.toString())
        binding.spCategory.setSelection(
            getIndexCategoryForItem(
                product.category.name,
                ShoppingProductsCategory.createCategories()
            )
        )
        binding.spUnity.setSelection(
            getIndexUnityForItem(
                product.unity.name,
                ShoppingProductsUnity.createUnities()
            )
        )
        binding.btnAdd.text = "Salvar"
        binding.tvTitle.text = "Editar product"
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