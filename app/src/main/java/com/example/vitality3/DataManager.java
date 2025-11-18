package com.example.vitality3;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

//DataManager: maneja listas en memoria y las persiste en SharedPreferences como JSON.

public class DataManager {
    private static final String PREFS_NAME = "vitality_data";
    private static final String KEY_USUARIOS = "usuarios";
    private static final String KEY_PERFILES = "perfiles";
    private static final String KEY_CALORIAS = "calorias";

    private static SharedPreferences prefs;
    private static Gson gson = new Gson();

    public static ArrayList<Usuario> usuarios = new ArrayList<>();
    public static ArrayList<Perfil> perfiles = new ArrayList<>();
    public static ArrayList<Caloria> calorias = new ArrayList<>();

    private static boolean initialized = false;

    public static void init(Context context) {
        if (initialized) return;
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadAll();
        initialized = true;
    }

    private static void loadAll() {
        try {
            String uJson = prefs.getString(KEY_USUARIOS, null);
            String pJson = prefs.getString(KEY_PERFILES, null);
            String cJson = prefs.getString(KEY_CALORIAS, null);

            Type tu = new TypeToken<ArrayList<Usuario>>(){}.getType();
            Type tp = new TypeToken<ArrayList<Perfil>>(){}.getType();
            Type tc = new TypeToken<ArrayList<Caloria>>(){}.getType();

            usuarios = (uJson == null) ? new ArrayList<>() : gson.fromJson(uJson, tu);
            perfiles = (pJson == null) ? new ArrayList<>() : gson.fromJson(pJson, tp);
            calorias = (cJson == null) ? new ArrayList<>() : gson.fromJson(cJson, tc);

            if (usuarios == null) usuarios = new ArrayList<>();
            if (perfiles == null) perfiles = new ArrayList<>();
            if (calorias == null) calorias = new ArrayList<>();
        } catch (Exception e) {
            Log.e("DataManager", "Error al cargar datos: " + e.getMessage());
            usuarios = new ArrayList<>();
            perfiles = new ArrayList<>();
            calorias = new ArrayList<>();
        }
    }

    private static void saveAll() {
        try {
            prefs.edit()
                    .putString(KEY_USUARIOS, gson.toJson(usuarios))
                    .putString(KEY_PERFILES, gson.toJson(perfiles))
                    .putString(KEY_CALORIAS, gson.toJson(calorias))
                    .apply();
        } catch (Exception e) {
            Log.e("DataManager", "Error al guardar datos: " + e.getMessage());
        }
    }

    // Usuarios
    public static boolean emailExiste(String email) {
        for (Usuario u : usuarios) if (u.getEmail().equalsIgnoreCase(email)) return true;
        return false;
    }

    public static boolean registrarUsuario(String nombre, String email, String password) {
        if (emailExiste(email)) return false;
        Usuario nuevo = new Usuario(nombre, email, password);
        usuarios.add(nuevo);
        saveAll();
        return true;
    }

    public static Usuario validarLogin(String email, String password) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public static Usuario getUsuarioPorEmail(String email) {
        for (Usuario u : usuarios) {
            if (u.getEmail().equalsIgnoreCase(email)) return u;
        }
        return null;
    }

    // Perfiles
    public static boolean guardarPerfil(String email, int edad, double peso, int altura, String sexo, String condiciones) {
        Perfil existing = obtenerPerfil(email);
        if (existing != null) {
            existing.setEdad(edad);
            existing.setPeso(peso);
            existing.setAltura(altura);
            existing.setSexo(sexo);
            existing.setCondiciones(condiciones);
        } else {
            Perfil p = new Perfil(email, edad, peso, altura, sexo, condiciones);
            perfiles.add(p);
        }
        saveAll();
        return true;
    }

    public static Perfil obtenerPerfil(String email) {
        for (Perfil p : perfiles) {
            if (p.getEmail().equalsIgnoreCase(email)) return p;
        }
        return null;
    }

    // Calorías
    public static void agregarCaloria(String email, String fecha, int cantidad) {
        Caloria c = new Caloria(email, fecha, cantidad);
        calorias.add(0, c); // agregar al inicio para mantener orden descendente como en tu BD
        saveAll();
    }

    public static List<Caloria> obtenerCaloriasPorEmail(String email) {
        List<Caloria> res = new ArrayList<>();
        for (Caloria c : calorias) {
            if (c.getEmail().equalsIgnoreCase(email)) res.add(c);
        }
        return res;
    }

    public static int getTotalCaloriasUsuario(String email) {
        int total = 0;
        for (Caloria c : calorias) {
            if (c.getEmail().equalsIgnoreCase(email)) total += c.getCantidad();
        }
        return total;
    }

    // Para debugging / limpieza
    public static void clearAll() {
        usuarios.clear();
        perfiles.clear();
        calorias.clear();
        saveAll();
    }
}
