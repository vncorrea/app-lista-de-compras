package com.example.lista_de_compras.ui.login;

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityLoginBinding
import com.example.lista_de_compras.ui.shopping_lists.ShoppingListsActivity
import com.example.lista_de_compras.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener { login() }
        binding.btnRegister.setOnClickListener { register() }
    }

    private fun register() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    private fun login() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()

        if (email == "admin@admin.com" && password == "admin") {
            startActivity(Intent(this, ShoppingListsActivity::class.java))
        } else {
            showLoginError()
        }
    }

    private fun showLoginError() {
        binding.etEmail.error = "Email ou senha inválidos"
        binding.etPassword.error = "Email ou senha inválidos"
    }
}