package com.example.vitality3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

public class diarioActivity extends AppCompatActivity {

    TextView tvWelcome, tvCaloriasTotales;
    Button btnCalorias, btnCerrarSesion;
    LinearProgressIndicator progressCalorias;
    String usuarioEmail;
    ImageButton Volver;
    int metaDiaria = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diario);

        DataManager.init(this);

        progressCalorias = findViewById(R.id.progressCalorias);
        Volver = findViewById(R.id.atras);
        progressCalorias.setIndeterminate(false);
        progressCalorias.setMax(metaDiaria);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvCaloriasTotales = findViewById(R.id.tvCaloriasTotales);
        btnCalorias = findViewById(R.id.btnCalorias);
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        Prefs prefs = new Prefs(this);
        usuarioEmail = prefs.getEmail();

        mostrarNombreUsuario();
        mostrarCaloriasTotales();

        btnCalorias.setOnClickListener(v -> {
            Intent intent = new Intent(diarioActivity.this, caloriasActivity.class);
            intent.putExtra("usuarioEmail", usuarioEmail);
            startActivity(intent);
        });

        btnCerrarSesion.setOnClickListener(v -> {
            // borrar sesión pero no datos
            Prefs p = new Prefs(diarioActivity.this);
            p.clear();
            Intent intent = new Intent(diarioActivity.this, loginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        Volver.setOnClickListener(view -> {
            Intent intent = new Intent(diarioActivity.this, homeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void mostrarNombreUsuario() {
        Usuario u = DataManager.getUsuarioPorEmail(usuarioEmail);
        if (u != null) {
            try {
                String nombre = u.getNombre();
                tvWelcome.setText("Hola, " + nombre);
            } catch (Exception e) {
                Log.e("diarioActivity", "Error obteniendo nombre", e);
                tvWelcome.setText("Hola");
            }
        } else {
            tvWelcome.setText("Hola");
        }
    }

    private void mostrarCaloriasTotales() {
        int total = DataManager.getTotalCaloriasUsuario(usuarioEmail);
        tvCaloriasTotales.setText("Calorías totales: " + total + " kcal");

        progressCalorias.setMax(metaDiaria);
        int progreso = Math.min(total, metaDiaria);
        progressCalorias.setProgressCompat(progreso, true);

        float porcentaje = (float) total / metaDiaria;

        if (total <= 0) {
            progressCalorias.setIndicatorColor(Color.parseColor("#BDBDBD")); // gris
        } else if (porcentaje < 1.0f) {
            progressCalorias.setIndicatorColor(Color.parseColor("#4CAF50")); // verde
        } else {
            progressCalorias.setIndicatorColor(Color.parseColor("#F44336")); // rojo
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mostrarCaloriasTotales();
    }
}
