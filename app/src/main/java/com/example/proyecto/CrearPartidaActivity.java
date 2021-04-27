package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CrearPartidaActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);

        mTextView = (TextView) findViewById(R.id.text);

    }

    public void volverPartida(View view){
        Intent nextActivity = new Intent(CrearPartidaActivity.this, PartidaActivity.class);
        CrearPartidaActivity.this.startActivity(nextActivity);
        CrearPartidaActivity.this.finish();
    }

    public void crearPartida(View view){
        //creamos partida
    }
}