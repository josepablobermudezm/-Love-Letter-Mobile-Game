package com.example.proyecto.modelos;

import java.io.Serializable;
import java.util.ArrayList;

public class Partida implements Serializable {
    private Integer p_id;
    private Integer p_cantidadJugadores;
    private String p_tipo;
    private String p_codigo;
    private Integer p_nivelMinimo;
    private Integer p_fkUsuario;
    public static ArrayList<Partida> partidas = new ArrayList<>();

    public Partida() {
    }

    public Partida(Integer p_id, Integer p_cantidadJugadores, String p_tipo, String p_codigo, Integer p_nivelMinimo, Integer p_fkUsuario) {
        this.p_id = p_id;
        this.p_cantidadJugadores = p_cantidadJugadores;
        this.p_tipo = p_tipo;
        this.p_codigo = p_codigo;
        this.p_nivelMinimo = p_nivelMinimo;
        this.p_fkUsuario = p_fkUsuario;
    }

    public Integer getP_id() {
        return p_id;
    }

    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    public Integer getP_cantidadJugadores() {
        return p_cantidadJugadores;
    }

    public void setP_cantidadJugadores(Integer p_cantidadJugadores) {
        this.p_cantidadJugadores = p_cantidadJugadores;
    }

    public String getP_tipo() {
        return p_tipo;
    }

    public void setP_tipo(String p_tipo) {
        this.p_tipo = p_tipo;
    }

    public String getP_codigo() {
        return p_codigo;
    }

    public void setP_codigo(String p_codigo) {
        this.p_codigo = p_codigo;
    }

    public Integer getP_nivelMinimo() {
        return p_nivelMinimo;
    }

    public void setP_nivelMinimo(Integer p_nivelMinimo) {
        this.p_nivelMinimo = p_nivelMinimo;
    }

    public Integer getP_fkUsuario() {
        return p_fkUsuario;
    }

    public void setP_fkUsuario(Integer p_fkUsuario) {
        this.p_fkUsuario = p_fkUsuario;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "p_id=" + p_id +
                ", p_cantidadJugadores=" + p_cantidadJugadores +
                ", p_tipo='" + p_tipo + '\'' +
                ", p_codigo='" + p_codigo + '\'' +
                ", p_nivelMinimo=" + p_nivelMinimo +
                ", p_fkUsuario=" + p_fkUsuario +
                '}';
    }
}
