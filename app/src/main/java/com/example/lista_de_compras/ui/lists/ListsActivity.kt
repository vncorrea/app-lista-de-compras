package com.example.lista_de_compras.ui.lists

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.R
import com.example.lista_de_compras.ui.create.CreateListActivity

class ListsActivity : AppCompatActivity() {

    private lateinit var listView: ListView
    private lateinit var addButton: Button
    private val Lists = arrayListOf(
        List("Lista do Mercado"),
        List("Lista da Feira"),
        List("Lista da Farmácia")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lists)

        listView = findViewById(R.id.listView)
        addButton = findViewById(R.id.addButton)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, shoppingLists.map { it.name })
        listView.adapter = adapter


        addButton.setOnClickListener {
            val intent = Intent(this, CreateListActivity::class.java)
            startActivityForResult(intent, CREATE_LIST_REQUEST_CODE)
        }

        // Clique em item da lista
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedList = Lists[position]
            Toast.makeText(this, "Abrindo ${selectedList.name}", Toast.LENGTH_SHORT).show()
            // Abrir a lista selecionada aqui (ex: Activity para ver os itens da lista)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == CREATE_LIST_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val newListTitle = data?.getStringExtra("newListTitle")
            if (!newListTitle.isNullOrEmpty()) {
                val newLists = Lists(newListTitle)
                Lists.add(newLists)
                (listView.adapter as ArrayAdapter<String>).notifyDataSetChanged() // Atualiza a lista
            }
        }
    }

    companion object {
        const val CREATE_LIST_REQUEST_CODE = 1
    }
}
