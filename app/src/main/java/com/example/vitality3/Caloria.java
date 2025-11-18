package com.example.vitality3;

public class Caloria {
    private String email;
    private String fecha; // yyyy-MM-dd HH:mm:ss
    private int cantidad;

    public Caloria() {}

    public Caloria(String email, String fecha, int cantidad) {
        this.email = email;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
}
