package com.example.negocionaarea.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.negocionaarea.databinding.ActivityIntroBinding
import com.example.negocionaarea.view.controller.empresaController

class IntroActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIntroBinding
    private val controller = empresaController()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCadastrar.setOnClickListener{
            startActivity(Intent(this, escolhaUsuario::class.java))
        }

        // Botão de login
        binding.btneEntrar.setOnClickListener {
            val input = binding.editTextEmailLogin.text.toString().trim()
            val senha = binding.editTextPasswordLogin.text.toString().trim()

            if (input.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            controller.autenticarEmpresa(input, senha) { sucesso ->
                if (sucesso) {
                    Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, loginEmpresa::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Email, CNPJ ou senha incorretos", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}