package com.example.negocionaarea.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.negocionaarea.R
import com.example.negocionaarea.databinding.ActivityCadastroClienteBinding
import com.example.negocionaarea.databinding.ActivityCadastroEmpresaBinding
import com.example.negocionaarea.model.clienteModel
import com.google.android.play.integrity.internal.l
import com.google.firebase.database.FirebaseDatabase

class cadastro_cliente : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroClienteBinding
    private val database = FirebaseDatabase.getInstance()
    private val clientesRef = database.getReference("clientes")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroClienteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonVoltarLogin.setOnClickListener{
            startActivity(Intent(this, IntroActivity::class.java))
        }

        binding.buttonCadastrarCliente.setOnClickListener {
            cadastrarCliente()
        }
    }

    private fun cadastrarCliente() {
        val nome = binding.editTextNomeCliente.text.toString().trim()
        val telefone = binding.editTextTelCliente.text.toString().trim()
        val email = binding.editTextEmailCliente.text.toString().trim()
        val senha = binding.editTextPasswordCliente.text.toString()

        // Validações básicas
        if (nome.isEmpty() || telefone.isEmpty() || email.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        if (senha.length < 6) {
            Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        // Cria objeto cliente
        val cliente = clienteModel(
            nomeCliente = nome,
            emailCliente = email,
            telefoneCliente = telefone,
            senhaCliente = senha
        )

        // Gera ID aleatório e salva no banco
        val novaChave = clientesRef.push().key
        if (novaChave != null) {
            clientesRef.child(novaChave).setValue(cliente)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, IntroActivity::class.java))
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Erro ao cadastrar cliente. Tente novamente.", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(this, "Erro ao gerar ID único para o cliente.", Toast.LENGTH_SHORT).show()
        }
    }
}