package com.example.proyecto.partida;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.example.proyecto.LobbyActivity;
import com.example.proyecto.R;
import com.example.proyecto.login.LoginActivity;
import com.example.proyecto.registro.RegisterActivity;
import com.example.proyecto.usuarios.Usuario;
import com.example.proyecto.usuarios.UsuariosActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class PartidaActivity extends AppCompatActivity {

    private TextView mTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);

        mTextView = (TextView) findViewById(R.id.text);

    }

    public void crearPartida(View view){
        //Redirecciona a la otra vista
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