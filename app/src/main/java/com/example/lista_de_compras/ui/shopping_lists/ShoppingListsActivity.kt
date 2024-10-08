package com.example.lista_de_compras.ui.shopping_lists

import ListsAdapter
import ShoppingList
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.databinding.ActivityShoppingListsBinding
import com.example.lista_de_compras.ui.create.CreateListActivity
import com.example.lista_de_compras.ui.products_list.ProductsListActivity
import com.example.lista_de_compras.viewmodel.ShoppingListViewModel
import java.util.Locale.filter

class ShoppingListsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShoppingListsBinding
    private lateinit var listsAdapter: ListsAdapter
    private lateinit var viewModel: ShoppingListViewModel
    private var allShoppingList: List<ShoppingList> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShoppingListsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ShoppingListViewModel::class.java)
        listsAdapter = ListsAdapter(mutableListOf())

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }



    private fun setupRecyclerView() {
        listsAdapter.onClick = { selectedList ->
            val intent = Intent(this, ProductsListActivity::class.java)
            intent.putExtra("selected_list", selectedList)
            startActivity(intent)
        }

        listsAdapter.onLongClick = { selectedList ->
            val intent = Intent(this, CreateListActivity::class.java)
            intent.putExtra("edit_list", selectedList)
            resultLauncher.launch(intent)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = listsAdapter
    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener { finish() }
        binding.addListButton.setOnClickListener {
            val intent = Intent(this, CreateListActivity::class.java)
            resultLauncher.launch(intent)
        }

        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filterShoppingLists(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun filterShoppingLists(query: String) {
        val filteredProducts = allShoppingList.filter { it.name.contains(query, ignoreCase = true) }
        listsAdapter.updateList(filteredProducts)
    }

    private fun setupObservers() {
        viewModel.lists.observe(this) { list ->
            allShoppingList = list
            listsAdapter.updateList(list)
        }



        viewModel.getLists()
    }

    private val resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                result.data?.let { data ->
                    val newList = data.getSerializableExtra("new_list") as? ShoppingList
                    val editableList =
                        data.getSerializableExtra("edit_list") as? ShoppingList
                    val deletedList =
                        data.getSerializableExtra("delete_list") as? ShoppingList

                    if (editableList != null) {
                        viewModel.update(editableList)
                    } else if (newList != null) {
                        viewModel.add(newList)
                    } else if (deletedList != null) {
                        viewModel.remove(deletedList)
                    }

                    viewModel.getLists()
                }
            }
        }
}