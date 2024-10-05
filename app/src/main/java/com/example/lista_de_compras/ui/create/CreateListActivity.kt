package com.example.lista_de_compras.ui.create

import ProductsAdapter
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lista_de_compras.R
import com.example.lista_de_compras.data.model.ShoppingProducts
import com.example.lista_de_compras.databinding.ActivityShoppingProductsListBinding
import com.example.lista_de_compras.ui.shopping_products.ShoppingProductsActivity
import com.example.lista_de_compras.viewmodel.ShoppingProductsViewModel

class CreateListActivity : AppCompatActivity() {

    private lateinit var listTitleEditText: EditText
    private lateinit var createButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_createlist)

        listTitleEditText = findViewById(R.id.listTitleEditText)
        createButton = findViewById(R.id.createButton)

        createButton.setOnClickListener {
            val newListTitle = listTitleEditText.text.toString()
            if (newListTitle.isNotEmpty()) {
                val resultIntent = Intent()
                resultIntent.putExtra("newListTitle", newListTitle)
                setResult(Activity.RESULT_OK, resultIntent)
                finish() // Volta para ListsActivity
            } else {
                Toast.makeText(this, "Por favor, insira o nome da lista.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
