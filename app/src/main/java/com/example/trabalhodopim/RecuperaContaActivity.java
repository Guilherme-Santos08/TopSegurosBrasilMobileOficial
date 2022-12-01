package com.example.trabalhodopim;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.example.trabalhodopim.databinding.ActivityRecuperaContaBinding;

public class RecuperaContaActivity extends AppCompatActivity {

    private ActivityRecuperaContaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecuperaContaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        iniciaToolbar();
    }

    private void iniciaToolbar() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }
}