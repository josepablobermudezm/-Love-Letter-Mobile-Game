package com.example.proyecto.carta;

public class carta {
    private String nombre;
    private int valor;

    public carta(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public carta() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}
