package com.example.proyecto;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class UsuariosActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        mTextView = (TextView) findViewById(R.id.text);

        Intent i = this.getIntent();
        String u_id = i.getStringExtra("u_id");
        String alias = i.getStringExtra("u_alias");
        String u_fechaNacimiento = i.getStringExtra("u_fechaNacimiento");
        String password = i.getStringExtra("u_password");
        String rol = i.getStringExtra("u_rol");
        String u_cantidadPartidasJugadas = i.getStringExtra("u_cantidadPartidasJugadas");
        String u_cantidadPartidasGanadas = i.getStringExtra("u_cantidadPartidasGanadas");
        String u_cantidadAmigos = i.getStringExtra("u_cantidadAmigos");
        String u_nivel = i.getStringExtra("u_nivel");
        String u_experiencia = i.getStringExtra("u_experiencia");

        System.out.println(u_id);
        System.out.println(alias);
        System.out.println(u_fechaNacimiento);
        System.out.println(password);
        System.out.println(rol);
        System.out.println(u_cantidadPartidasJugadas);
        System.out.println(u_cantidadPartidasGanadas);
        System.out.println(u_cantidadAmigos);
        System.out.println(u_nivel);
        System.out.println(u_experiencia);
    }
}