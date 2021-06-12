package com.example.proyecto.controladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;
import com.example.proyecto.modelos.Usuario;

public class LogrosActivity extends AppCompatActivity {

    private TextView mTextView;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logros);

        mTextView = (TextView) findViewById(R.id.text);
        usuario = Usuario.usuarioLogueado;
        logros();
    }

    public void volverLobby(View view) {
        Intent nextActivity = new Intent(LogrosActivity.this, LobbyActivity.class);
        LogrosActivity.this.startActivity(nextActivity);
        LogrosActivity.this.finish();
    }

    private void logros() {
        if(usuario.getU_cantidadAmigos()>=5){
            ((ImageView)findViewById(R.id.iv_amigos)).setImageResource(R.drawable.friends);
        }
        if(usuario.getU_cantidadPartidasGanadas()>=15){
            ((ImageView)findViewById(R.id.iv_victorias)).setImageResource(R.drawable.trophy);
        }
        if(usuario.getU_cantidadPartidasJugadas()>=20){
            ((ImageView)findViewById(R.id.iv_partidas)).setImageResource(R.drawable.medal);
        }
        if(usuario.getU_nivel()>=10){
            ((ImageView)findViewById(R.id.iv_nivel)).setImageResource(R.drawable.wreath);
        }
    }

}