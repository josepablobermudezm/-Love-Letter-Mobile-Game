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
        String password = i.getStringExtra("u_password");
        String rol = i.getStringExtra("u_rol");
        String u_picture = i.getStringExtra("u_picture");
        String u_fechaNacimiento = i.getStringExtra("u_fechaNacimiento");
        String u_cantidadPartidasJugadas = i.getStringExtra("u_cantidadPartidasJugadas");
        String u_cantidadPartidasGanadas = i.getStringExtra("u_cantidadPartidasGanadas");
        String u_cantidadAmigos = i.getStringExtra("u_cantidadAmigos");
        String u_nivel = i.getStringExtra("u_nivel");
        String u_experiencia = i.getStringExtra("u_experiencia");
    }
}