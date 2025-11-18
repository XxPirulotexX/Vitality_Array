package com.example.vitality3;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.card.MaterialCardView;

public class homeActivity extends AppCompatActivity {

    private MaterialCardView cardDiario;
    private MaterialCardView cardDietas;

    private String usuarioEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Ocultar ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Recuperar email del usuario logeado
        Prefs prefs = new Prefs(this);
        usuarioEmail = prefs.getEmail();  // Este es el email YA guardado en login o registro

        // Si por alguna razón viene un extra desde login, lo priorizamos
        String emailIntent = getIntent().getStringExtra("usuarioEmail");
        if (emailIntent != null) {
            usuarioEmail = emailIntent;
            prefs.saveEmail(emailIntent); // Lo guardamos para mantenerlo actualizado
        }

        // Vistas
        cardDiario = findViewById(R.id.card_diario);
        cardDietas = findViewById(R.id.card_dietas);

        // Click a Diario
        cardDiario.setOnClickListener(v -> {
            Intent intent = new Intent(homeActivity.this, diarioActivity.class);
            intent.putExtra("usuarioEmail", usuarioEmail);
            startActivity(intent);
        });

        // Click a Dietas
        cardDietas.setOnClickListener(v ->
                startActivity(new Intent(homeActivity.this, DietasActivity.class))
        );
    }
}
