package com.example.negocionaarea.controller

import com.example.negocionaarea.model.clienteModel
import com.example.negocionaarea.model.empresaModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class clienteController {
        private val database = FirebaseDatabase.getInstance()
        private val clientesRef = database.getReference("clientes")

    // Função para cadastrar cliente com ID aleatório

    fun cadastrarCliente(cliente: clienteModel, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        val novaChave = clientesRef.push().key

        if (novaChave != null) {
            clientesRef.child(novaChave).setValue(cliente)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { exception -> onFailure(exception) }
        } else {
            onFailure(Exception("Erro ao gerar ID único para o cliente"))
        }
    }

    // Função para autenticar cliente por e-mail ou telefone
    fun autenticarCliente(
        input: String,
        senha: String,
        callback: (Boolean) -> Unit
    ) {
        clientesRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var loginValido = false

                for (clienteSnap in snapshot.children) {
                    val cliente = clienteSnap.getValue(clienteModel::class.java)

                    val emailBanco = cliente?.emailCliente?.trim()
                    val telefoneBanco = cliente?.telefoneCliente?.trim()

                    if ((input == emailBanco || input == telefoneBanco) && cliente?.senhaCliente == senha) {
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