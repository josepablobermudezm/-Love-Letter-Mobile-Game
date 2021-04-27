package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class PartidaActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        mTextView = (TextView) findViewById(R.id.text);

    }

    public void crearPartida(View view){
        Intent nextActivity = new Intent(PartidaActivity.this, CrearPartidaActivity.class);
        PartidaActivity.this.startActivity(nextActivity);
        PartidaActivity.this.finish();
    }

    public void volverLobby(View view){
        Intent nextActivity = new Intent(PartidaActivity.this, LobbyActivity.class);
        PartidaActivity.this.startActivity(nextActivity);
        PartidaActivity.this.finish();
    }
}