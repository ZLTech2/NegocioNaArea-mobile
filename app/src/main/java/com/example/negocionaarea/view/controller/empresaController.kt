package com.example.negocionaarea.view.controller

import com.example.negocionaarea.view.model.empresaModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class empresaController {
    private val database = FirebaseDatabase.getInstance()
    private val empresasRef = database.getReference("empresas")

    // Remove caracteres especiais do CNPJ
    fun limparCNPJ(cnpj: String): String {
        return cnpj.replace(Regex("[^\\d]"), "")
    }

    // Função para cadastrar empresa
    fun cadastrarEmpresa(empresa: empresaModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val chave = limparCNPJ(empresa.cnpj)

        // Salva a empresa no caminho "empresas/{cnpj-limpo}"
        empresasRef.child(chave).setValue(empresa)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onFailure(exception) }
    }

    // Função de login por email ou CNPJ
    fun autenticarEmpresa(input: String, senha: String, callback: (Boolean) -> Unit) {
        empresasRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var loginValido = false

                for (empresaSnap in snapshot.children) {
                    val empresa = empresaSnap.getValue(empresaModel::class.java)

                    val inputLimpo = limparCNPJ(input)
                    val cnpjNoBanco = limparCNPJ(empresa?.cnpj ?: "")
                    val emailNoBanco = empresa?.email?.trim()

                    if ((inputLimpo == cnpjNoBanco || input == emailNoBanco) && empresa?.senha == senha) {
                        loginValido = true
                        break
                    }
                }

                callback(loginValido)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false)
            }
        })
    }
}