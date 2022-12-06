package com.example.trabalhodopim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.trabalhodopim.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();

        binding.textCadastro.setOnClickListener(view -> {
            startActivity(new Intent(this, CadastroActivity.class));
        });

        binding.textRecuperaConta.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperaContaActivity.class)));

        binding.btnLogin.setOnClickListener(v -> validaDados());
    }

    private void validaDados() {
        String email = binding.editEmail.getText().toString().trim();
        String senha = binding.editSenha.getText().toString().trim();

        if (!email.isEmpty()) {
            if (!senha.isEmpty()) {

                binding.progressBar.setVisibility(View.VISIBLE);

                loginFirebase(email, senha);

            } else {
                Toast.makeText(this, "Informe uma senha.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Informe seu e-mail.", Toast.LENGTH_SHORT).show();
        }
    }

    private void loginFirebase(String email, String senha) {
        mAuth.signInWithEmailAndPassword(
                email, senha
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
                if( e instanceof FirebaseAuthInvalidUserException){
                    Toast.makeText(LoginActivity.this, "Este usuário não foi encontrado , Crie uma nova conta.", Toast.LENGTH_SHORT).show();
                }
                if( e instanceof FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(LoginActivity.this, "Senha inválida, Por favor, coloque uma senha valida", Toast.LENGTH_SHORT).show();
                }
                if(e instanceof FirebaseNetworkException){
                    Toast.makeText(LoginActivity.this, "Por favor, verifique sua conexão com a internet", Toast.LENGTH_SHORT).show();
                }
            }
        });;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}