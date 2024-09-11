package com.example.lista_de_compras

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityLoginBinding
import com.example.lista_de_compras.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}