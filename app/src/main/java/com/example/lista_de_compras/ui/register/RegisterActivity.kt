package com.example.lista_de_compras.ui.register

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.lista_de_compras.databinding.ActivityRegisterBinding
import com.example.lista_de_compras.ui.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener {
            if (validateInputs()) {
                binding.loadingOverlay.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
                binding.btnRegister.isEnabled = false

                Handler(Looper.getMainLooper()).postDelayed({
                    binding.progressBar.visibility = View.GONE
                    binding.btnRegister.isEnabled = true
                    
                    Handler(Looper.getMainLooper()).postDelayed({
                        binding.loadingOverlay.visibility = View.GONE
                    }, 1000)

                }, 2000)

                Toast.makeText(this, "Usuário cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        if (binding.etName.text.toString().trim().isEmpty()) {
            binding.etName.error = "O nome é obrigatório"
            isValid = false
        } else {
            binding.etName.error = null
        }

        val email = binding.etEmail.text.toString().trim()
        if (email.isEmpty()) {
            binding.etEmail.error = "O email é obrigatório"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etEmail.error = "Formato de email inválido"
            isValid = false
        } else {
            binding.etEmail.error = null
        }

        val password = binding.etPassword.text.toString().trim()
        if (password.isEmpty()) {
            binding.etPassword.error = "A senha é obrigatória"
            isValid = false
        } else if (password.length < 6) {
            binding.etPassword.error = "A senha deve ter pelo menos 6 caracteres"
            isValid = false
        } else {
            binding.etPassword.error = null
        }

        return isValid
    }
}
