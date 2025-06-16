package com.example.negocionaarea.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.negocionaarea.R
import com.example.negocionaarea.databinding.ActivityCadastroClienteBinding
import com.example.negocionaarea.databinding.ActivityCadastroEmpresaBinding
import com.google.android.play.integrity.internal.l

class cadastro_cliente : AppCompatActivity() {
    private lateinit var binding: ActivityCadastroClienteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCadastroClienteBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.buttonVoltarLogin.setOnClickListener{
            startActivity(Intent(this, IntroActivity::class.java))
        }

    }
}