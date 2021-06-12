package com.example.proyecto.controladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.WebSocket;

import com.example.proyecto.R;
import com.example.proyecto.modelos.Usuario;

public class LobbyActivity extends AppCompatActivity {

    private TextView mTextView;
    Usuario usuarioLogueado = new Usuario();
    LinearLayout parentLayout;
    private WebSocket webSocket;
    private String SERVER_PATH = "link://192.168.1.7:3000";
    private RecyclerView recyclerView;
    public Button PlayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        mTextView = (TextView) findViewById(R.id.text);

        Intent i = this.getIntent();
        parentLayout = (LinearLayout) findViewById(R.id.padre);

        Button logrosButton = new Button(this);
        if (Usuario.usuarioLogueado.getU_rol().equals("J")) {
            logrosButton.setText("Logros");
            logrosButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextActivity = new Intent(LobbyActivity.this, LogrosActivity.class);
                    LobbyActivity.this.startActivity(nextActivity);

                }
            });
        }

        Button CerrarSesionButton = new Button(this);
        CerrarSesionButton.setText("Cerrar Sesión");
        CerrarSesionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(LobbyActivity.this, LoginActivity.class);
                LobbyActivity.this.startActivity(nextActivity);

            }
        });

        Button AcercaButton = new Button(this);
        AcercaButton.setText("ACERCA DE");
        AcercaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(LobbyActivity.this, AboutUsActivity.class);
                LobbyActivity.this.startActivity(nextActivity);

            }
        });

        Button usuarioButton = new Button(this);
        usuarioButton.setText(Usuario.usuarioLogueado.getU_rol().equals("A") ? "USUARIOS" : "CUENTA");
        usuarioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(LobbyActivity.this, UsuariosActivity.class);
                LobbyActivity.this.startActivity(nextActivity);

            }
        });
        PlayButton = new Button(this);
        PlayButton.setText(Usuario.usuarioLogueado.getU_rol().equals("A") ?"PARTIDAS":"NUEVA PARTIDA");
        PlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(LobbyActivity.this, PartidaActivity.class);
                LobbyActivity.this.startActivity(nextActivity);
            }
        });

        parentLayout.addView(PlayButton);

        parentLayout.addView(usuarioButton);
        parentLayout.addView(AcercaButton);
        if (Usuario.usuarioLogueado.getU_rol().equals("J")) {
            parentLayout.addView(logrosButton);
        }
        parentLayout.addView(CerrarSesionButton);
    }

    public void crearPartida() {

    }
}