package com.example.vitality3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {

    EditText edtEmail, edtPassword;
    Button btnLogin;
    TextView txtRegister;
    Prefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        DataManager.init(this); // inicializar gestor de datos
        prefs = new Prefs(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtRegister = findViewById(R.id.txtRegister);

        btnLogin.setOnClickListener(view -> {
            String email = edtEmail.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(loginActivity.this, "Completa los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Usuario u = DataManager.validarLogin(email, password);
            if (u != null) {
                prefs.saveEmail(email);
                Toast.makeText(loginActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(loginActivity.this, homeActivity.class);
                intent.putExtra("usuarioEmail", email);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(loginActivity.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            }
        });

        txtRegister.setOnClickListener(view -> {
            Intent intent = new Intent(loginActivity.this, registrarseActivity.class);
            startActivity(intent);
        });
    }
}
