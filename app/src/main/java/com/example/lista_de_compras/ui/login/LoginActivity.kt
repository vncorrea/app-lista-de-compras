package com.example.lista_de_compras.ui.login

import com.example.lista_de_compras.ui.lista_produtos.ListaProdutosActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.ui.register.RegisterActivity
import com.example.lista_de_compras.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            if (email == "admin@admin.com" && password == "admin") {
                val intent = Intent(this, ListaProdutosActivity::class.java)

                startActivity(intent)
            } else {
                binding.etEmail.error = "Email ou senha inválidos"
                binding.etPassword.error = "Email ou senha inválidos"
            }
        }

        binding.btnRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)

            startActivity(intent)
        }
    }
}