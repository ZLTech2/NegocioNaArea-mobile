package com.example.negocionaarea.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.negocionaarea.databinding.ActivityEscolhaUsuarioBinding

class escolhaUsuario : AppCompatActivity() {
    private lateinit var binding: ActivityEscolhaUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEscolhaUsuarioBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.btnCadastroEmpresa.setOnClickListener{
            startActivity(Intent(this, cadastroEmpresa::class.java))
        }

        binding.btnCadastroCliente.setOnClickListener{
            startActivity(Intent(this, cadastro_cliente::class.java))
        }




    }
}
