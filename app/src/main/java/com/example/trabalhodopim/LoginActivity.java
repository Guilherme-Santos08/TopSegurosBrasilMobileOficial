package com.example.trabalhodopim;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.trabalhodopim.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.textCadastro.setOnClickListener(view -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textRecuperaConta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));
    }
}