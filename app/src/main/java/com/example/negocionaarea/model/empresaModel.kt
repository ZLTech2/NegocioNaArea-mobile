package com.example.negocionaarea.model
import com.google.firebase.database.FirebaseDatabase


data class empresaModel (
    var cnpj: String = "",
    var nomeEmpresa: String = "",
    var email: String = "",
    var telefone: String = "",
    var descricao: String = "",
    var senha: String = ""

)


