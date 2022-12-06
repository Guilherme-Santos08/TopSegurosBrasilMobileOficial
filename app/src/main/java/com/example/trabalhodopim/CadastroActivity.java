package com.example.trabalhodopim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.trabalhodopim.databinding.ActivityCadastroBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class CadastroActivity extends AppCompatActivity {

    private ActivityCadastroBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCadastroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }

        iniciaToolbar();

        binding.btnCriarConta.setOnClickListener( v -> {
            String email = binding.editEmail.getText().toString().trim();
            String password = binding.editSenha.getText().toString().trim();

            if (!email.isEmpty()) {
                if (!password.isEmpty()) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                    mAuth.createUserWithEmailAndPassword(
                            email, password
                    ).addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {

                            finish();
                            startActivity(new Intent(this, MainActivity.class));
                        } else {
                            binding.progressBar.setVisibility(View.GONE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if( e instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(CadastroActivity.this, "Digite um email válido.", Toast.LENGTH_SHORT).show();
                            }
                            if( e instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(CadastroActivity.this, "Esta conta já foi cadastrada.", Toast.LENGTH_SHORT).show();
                            }
                            if( e instanceof FirebaseNetworkException){
                                Toast.makeText(CadastroActivity.this, "Sem conexão com a internet.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(this, "Informe uma senha.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Informe seu e-mail.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void iniciaToolbar() {
        Toolbar toolbar = binding.toolbar;
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }
}