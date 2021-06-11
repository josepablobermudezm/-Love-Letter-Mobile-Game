package com.example.proyecto.carta;

public class Carta {

    private String nombre;
    private int valor;

    public Carta(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public Carta() {
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

    @Override
    public String toString() {
        return "Carta{" +
                "nombre='" + nombre + '\'' +
                ", valor=" + valor +
                '}';
    }
}
