package com.example.negocionaarea.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.negocionaarea.databinding.ActivityCadastroEmpresaBinding
import com.example.negocionaarea.model.empresaModel
import com.google.firebase.database.FirebaseDatabase

class cadastroEmpresa : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroEmpresaBinding
    private val database = FirebaseDatabase.getInstance()
    private val empresasRef = database.getReference("empresas")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroEmpresaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnVoltarLogin.setOnClickListener{
            startActivity(Intent(this, IntroActivity::class.java))
            finish()
        }

        binding.buttonCadastrarEmpresa.setOnClickListener {
            cadastrarEmpresa()
        }

    }

    private fun limparCNPJ(cnpj: String): String {
        return cnpj.replace(Regex("[^\\d]"), "")
    }

    private fun cadastrarEmpresa() {
        val cnpj = limparCNPJ(binding.editTextCNPJ.text.toString().trim())
        val nome = binding.editTextNomeEmpresa.text.toString().trim()
        val email = binding.editTextEmailEmpresa.text.toString().trim()
        val telefone = binding.editTextTelEmpresa.text.toString().trim()
        val descricao = binding.editTextDescricaoEmpresa.text.toString().trim()
        val senha = binding.editTextPasswordEmpresa.text.toString()

        // Validações básicas
        if (cnpj.isEmpty() || nome.isEmpty() || email.isEmpty() || telefone.isEmpty() || senha.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos obrigatórios", Toast.LENGTH_SHORT).show()
            return
        }

        if (senha.length < 6) {
            Toast.makeText(this, "A senha deve ter pelo menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        // Verifica se já existe empresa cadastrada com o mesmo CNPJ
        empresasRef.child(cnpj).get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                Toast.makeText(this, "Já existe uma empresa cadastrada com esse CNPJ", Toast.LENGTH_SHORT).show()
            } else {
                // Cria objeto empresa
                val empresa = empresaModel(
                    cnpj = cnpj,
                    nomeEmpresa = nome,
                    email = email,
                    telefone = telefone,
                    descricao = descricao,
                    senha = senha
                )

                // Salva no Realtime Database usando o CNPJ como chave
                empresasRef.child(cnpj).setValue(empresa)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        // Voltar para login ou outra tela
                        startActivity(Intent(this, IntroActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Erro ao cadastrar empresa. Tente novamente.", Toast.LENGTH_SHORT).show()
                    }
            }
        }.addOnFailureListener {
            Toast.makeText(this, "Erro na verificação do CNPJ. Tente novamente.", Toast.LENGTH_SHORT).show()
        }
    }
}