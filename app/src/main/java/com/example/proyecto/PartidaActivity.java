package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.example.proyecto.login.LoginActivity;
import com.example.proyecto.registro.RegisterActivity;
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
/*
        String nivel = ((EditText)findViewById(R.id.txtNivel)).getText().toString();
        String cantidad = ((EditText)findViewById(R.id.txtcantJugadores)).getText().toString();
        String fechaNacimiento = fechaNacimientoAux.getText().toString();
        String rol = administradorAux.isChecked() ? "A" : jugadorAux.isChecked() ? "J" : null;
        editusuario.setU_alias(nombre);
        editusuario.setU_password(contrasena);
        editusuario.setU_fechaNacimiento(fechaNacimiento);
        editusuario.setU_rol(rol);


        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                System.out.println("hola, estoy aquí ");
                try {
                    System.out.println(response);
                    response = response.replaceFirst("<font>.*?</font>", "");
                    int jsonStart = response.indexOf("{");
                    int jsonEnd = response.lastIndexOf("}");

                    if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                        response = response.substring(jsonStart, jsonEnd + 1);
                    } else {
                        // deal with the absence of JSON content here
                    }
                    JSONObject jsonRespuesta = new JSONObject(response);
                    System.out.println(jsonRespuesta);
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if(ok){
                        Intent nextView = new Intent(RegisterActivity.this,
                                usuariosActivity.equals("true") || editActivity.equals("true") ? UsuariosActivity.class : LoginActivity.class);
                        RegisterActivity.this.startActivity(nextView);
                        finish();
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RegisterActivity.this);
                        alerta.setMessage(editActivity.equals("true") ? "Hubo un error en la edición" : "Fallo en el registro")
                                .setNegativeButton("Reintentar", null).create().show();
                    }
                }catch(JSONException e){
                    e.getMessage();
                }
            }
        };


        //Redirecciona a la otra vista
        /*Intent nextActivity = new Intent(PartidaActivity.this, CrearPartidaActivity.class);
        PartidaActivity.this.startActivity(nextActivity);
        PartidaActivity.this.finish();*/
    }

    public void volverLobby(View view){
        Intent nextActivity = new Intent(PartidaActivity.this, LobbyActivity.class);
        PartidaActivity.this.startActivity(nextActivity);
        PartidaActivity.this.finish();
    }
}