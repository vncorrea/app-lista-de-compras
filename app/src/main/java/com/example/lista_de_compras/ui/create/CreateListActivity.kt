package com.example.lista_de_compras.ui.create

import ShoppingList
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityCreatelistBinding
import java.util.UUID

class CreateListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreatelistBinding
    private var isEditing = false
    private var listForEditing: ShoppingList? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreatelistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkForEditing()

        binding.btnAdd.setOnClickListener { handleAddButtonClick() }
        binding.fabDelete.setOnClickListener { handleDeleteButtonClick() }
        binding.btnBack.setOnClickListener { finish() }
    }

    private fun checkForEditing() {
        listForEditing = intent.getSerializableExtra("edit_list") as? ShoppingList
        if (listForEditing != null) {
            isEditing = true
            preparingEdition(listForEditing!!)
        }
    }

    private fun handleDeleteButtonClick() {
        val intent = Intent().apply {
            putExtra(
                "delete_list",
                deleteShoppingProduct(listForEditing!!)
            )
        }
        setResult(RESULT_OK, intent)
        finish()
    }

    private fun handleAddButtonClick() {
        val name = binding.etNomeLista.text.toString()

        if (name.isNotEmpty()) {
            val intent = Intent().apply {
                putExtra(
                    if (isEditing) "edit_list" else "new_list",
                    createList(name)
                )
            }
            setResult(RESULT_OK, intent)
            finish()
        } else {
            showInputErrors()
        }
    }

    private fun deleteShoppingProduct(list: ShoppingList): ShoppingList {
        return list
    }

    private fun createList(
        name: String
    ): ShoppingList {
        return ShoppingList(
            id = if (isEditing) listForEditing?.id
                ?: 0 else UUID.randomUUID().mostSignificantBits.toInt(),
            name = name,
            picture = null,
        )
    }

    private fun showInputErrors() {
        binding.etNomeLista.error = "Campo obrigatório"
    }

    private fun preparingEdition(list: ShoppingList) {
        binding.etNomeLista.setText(list.name)
        binding.btnAdd.text = "Salvar"
        binding.tvTitle.text = "Editar lista"
        binding.fabDelete.show();
    }
}