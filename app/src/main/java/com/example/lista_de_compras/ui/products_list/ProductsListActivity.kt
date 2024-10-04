package com.example.lista_de_compras.ui.products_list

import ProductsAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.data.model.ShoppingProducts
import com.example.lista_de_compras.databinding.ActivityShoppingProductsListBinding
import com.example.lista_de_compras.ui.shopping_products.ShoppingProductsActivity
import com.example.lista_de_compras.viewmodel.ShoppingProductsViewModel

class ProductsListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingProductsListBinding
    private lateinit var productAdapter: ProductsAdapter
    private lateinit var viewModel: ShoppingProductsViewModel
    private var allProducts: List<ShoppingProducts> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingProductsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ShoppingProductsViewModel::class.java)
        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        productAdapter = ProductsAdapter(mutableListOf()) { orderableProducts() }
        productAdapter.onLongClick = { product ->
            val intent = Intent(this, ShoppingProductsActivity::class.java)
            intent.putExtra("edit_product", product)
            resultLauncher.launch(intent)
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = productAdapter
    }

    private fun setupObservers() {
        viewModel.products.observe(this) { products ->
            allProducts = products
            productAdapter.updateList(products)
        }
        viewModel.getProducts()
    }

    private fun setupListeners() {
        binding.addButton.setOnClickListener {
            val intent = Intent(this, ShoppingProductsActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterProducts(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterProducts(query: String) {
        val filteredProducts = allProducts.filter { it.name.contains(query, ignoreCase = true) }
        productAdapter.updateList(filteredProducts)
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    val newProduct = data.getSerializableExtra("new_product") as? ShoppingProducts
                    val editableProduct =
                        data.getSerializableExtra("edit_product") as? ShoppingProducts
                    val deletedProduct =
                        data.getSerializableExtra("delete_product") as? ShoppingProducts

                    if (editableProduct != null) {
                        viewModel.update(editableProduct)
                    } else if (newProduct != null) {
                        viewModel.add(newProduct)
                    } else if (deletedProduct != null) {
                        viewModel.remove(deletedProduct)
                    }

                    viewModel.getProducts()
                    orderableProducts()
                }
            }
        }

    private fun orderableProducts() {
        viewModel.products.value?.let { products ->
            val orderableProducts = products.sortedBy { it.isChecked }
            productAdapter.updateList(orderableProducts)
        }
    }
}