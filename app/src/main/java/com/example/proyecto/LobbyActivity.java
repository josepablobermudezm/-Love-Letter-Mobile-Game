package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LobbyActivity extends AppCompatActivity {

    private TextView mTextView;
    Usuario usuarioLogueado = new Usuario();
    LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        mTextView = (TextView) findViewById(R.id.text);

        Intent i = this.getIntent();
        parentLayout = (LinearLayout) findViewById(R.id.padre);
        Button AcercaButton = new Button(this);
        AcercaButton.setText("ACERCA DE");

        Button usuarioButton = new Button(this);
        usuarioButton.setText(Usuario.usuarioLogueado.getU_rol().equals("A") ? "USUARIOS" : "CUENTA");
        usuarioButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(LobbyActivity.this, UsuariosActivity.class);
                LobbyActivity.this.startActivity(nextActivity);
                LobbyActivity.this.finish();
            }
        });

        if(Usuario.usuarioLogueado.getU_rol().equals("J")){
            Button PlayButton = new Button(this);
            PlayButton.setText("NUEVA PARTIDA");
            parentLayout.addView(PlayButton);
        }

        parentLayout.addView(usuarioButton);
        parentLayout.addView(AcercaButton);
    }
}