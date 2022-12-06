package com.example.trabalhodopim;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.trabalhodopim.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnDeslogar.setOnClickListener( view -> {
            FirebaseAuth.getInstance().signOut();
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        });


        db.collection("Usuarios").document(FirebaseAuth.getInstance().getUid())
                .addSnapshotListener((snapshot, e) -> {
                    if (e != null) {
                        Log.w("UserError", "Listen failed.", e);
                        return;
                    }

                    if (snapshot != null && snapshot.exists()) {
                        binding.txtNameUser.setText("Nome: " + snapshot.getString("Nome"));
                        binding.txtDataNascimento.setText("Data de Nascimento: " + snapshot.getString("DataNascimento"));
                        binding.txtCpf.setText("CPF: " + snapshot.getString("Cpf"));
                        binding.txtCelular.setText("Celular: " + snapshot.getString("Celular"));
                        binding.txtEmail.setText("Email: " + snapshot.getString("Email"));
                        binding.txtTelefone.setText("Telefone: " + snapshot.getString("Telefone"));
                        binding.txtBairro.setText("Bairro: " + snapshot.getString("Bairro"));
                        binding.txtCep.setText("Cep: " + snapshot.getString("Cep"));
                        binding.txtCidade.setText("Cidade: " + snapshot.getString("Cidade"));
                        binding.txtEstado.setText("Estado: " + snapshot.getString("Estado"));
                        binding.txtNumeroResidencia.setText("Numero da Residencia: " + snapshot.getDouble("NumResidencia"));
                        binding.txtRua.setText("Rua: " + snapshot.getString("Rua"));
                        binding.txtTipoDoSeguro.setText("Tipo de Seguro: " + snapshot.getString("TipoDeSeguro"));
                    } else {
                        Log.d("User", "Current data: null");
                    }
                });    }
}