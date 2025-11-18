package com.example.vitality3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.List;

public class caloriasActivity extends AppCompatActivity {

    EditText edtCalorias;
    Button btnGuardar;
    ListView lvHistorial;
    String usuarioEmail;
    ArrayList<String> historialList;
    ArrayAdapter<String> adapter;
    ImageButton Volver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calorias);

        DataManager.init(this);

        edtCalorias = findViewById(R.id.edtCalorias);
        btnGuardar = findViewById(R.id.btnGuardar);
        lvHistorial = findViewById(R.id.lvHistorial);
        Volver = findViewById(R.id.back);

        historialList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, historialList);
        lvHistorial.setAdapter(adapter);

        Prefs prefs = new Prefs(this);
        usuarioEmail = prefs.getEmail();

        mostrarHistorial();

        btnGuardar.setOnClickListener(v -> {
            String caloriasStr = edtCalorias.getText().toString().trim();

            if (caloriasStr.isEmpty()) {
                Toast.makeText(caloriasActivity.this, "Ingresa la cantidad de calorías", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                int cantidad = Integer.parseInt(caloriasStr);
                String fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());

                DataManager.agregarCaloria(usuarioEmail, fecha, cantidad);

                Toast.makeText(caloriasActivity.this, "Calorías registradas correctamente", Toast.LENGTH_SHORT).show();
                edtCalorias.setText("");
                mostrarHistorial();
            } catch (NumberFormatException e) {
                Toast.makeText(caloriasActivity.this, "Ingresa un número válido", Toast.LENGTH_SHORT).show();
            }
        });

        Volver.setOnClickListener(view -> {
            Intent intent = new Intent(caloriasActivity.this, homeActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void mostrarHistorial() {
        historialList.clear();

        List<Caloria> lista = DataManager.obtenerCaloriasPorEmail(usuarioEmail);

        if (lista != null && !lista.isEmpty()) {
            for (Caloria c : lista) {
                historialList.add(c.getFecha() + " → " + c.getCantidad() + " kcal");
            }
        } else {
            historialList.add("No hay registros aún.");
        }

        adapter.notifyDataSetChanged();
    }
}
