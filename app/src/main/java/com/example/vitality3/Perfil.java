package com.example.vitality3;

public class Perfil {
    private String email; // link con Usuario
    private int edad;
    private double peso;
    private int altura;
    private String sexo; // "Hombre" o "Mujer"
    private String condiciones; // texto con condiciones separadas por comas

    public Perfil() {}

    public Perfil(String email, int edad, double peso, int altura, String sexo, String condiciones) {
        this.email = email;
        this.edad = edad;
        this.peso = peso;
        this.altura = altura;
        this.sexo = sexo;
        this.condiciones = condiciones;
    }

    // Getters y setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public double getPeso() { return peso; }
    public void setPeso(double peso) { this.peso = peso; }
    public int getAltura() { return altura; }
    public void setAltura(int altura) { this.altura = altura; }
    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }
    public String getCondiciones() { return condiciones; }
    public void setCondiciones(String condiciones) { this.condiciones = condiciones; }
}
