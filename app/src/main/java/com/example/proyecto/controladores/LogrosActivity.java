package com.example.proyecto.controladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.R;
import com.example.proyecto.modelos.Usuario;
import com.example.proyecto.servicios.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LogrosActivity extends AppCompatActivity {

    private TextView mTextView;

    private Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logros);

        mTextView = (TextView) findViewById(R.id.text);
        ActualizarUsuario();
    }

    public void volverLobby(View view) {
        Intent nextActivity = new Intent(LogrosActivity.this, LobbyActivity.class);
        LogrosActivity.this.startActivity(nextActivity);
        LogrosActivity.this.finish();
    }

    public void ActualizarUsuario(){
        Response.Listener<String> respuesta = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    response = response.replaceFirst("<font>.*?</font>", "");
                    int jsonStart = response.indexOf("{");
                    int jsonEnd = response.lastIndexOf("}");

                    if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                        response = response.substring(jsonStart, jsonEnd + 1);
                    }
                    JSONObject jsonRespuesta = new JSONObject(response);
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if(ok){
                        String u_id = jsonRespuesta.getString("u_id");
                        String alias = jsonRespuesta.getString("u_alias");
                        String password = jsonRespuesta.getString("u_password");
                        String rol = jsonRespuesta.getString("u_rol");
                        String u_picture = jsonRespuesta.getString("u_picture");
                        String u_fechaNacimiento = jsonRespuesta.getString("u_fechaNacimiento");
                        String u_cantidadPartidasJugadas = jsonRespuesta.getString("u_cantidadPartidasJugadas");
                        String u_cantidadPartidasGanadas = jsonRespuesta.getString("u_cantidadPartidasGanadas");
                        String u_cantidadAmigos = jsonRespuesta.getString("u_cantidadAmigos");
                        String u_nivel = jsonRespuesta.getString("u_nivel");
                        String u_experiencia = jsonRespuesta.getString("u_experiencia");


                        Usuario usuarioLogueado = new Usuario(Integer.parseInt(u_id), Integer.parseInt(u_cantidadPartidasJugadas),
                                Integer.parseInt(u_cantidadPartidasGanadas), Integer.parseInt(u_cantidadAmigos), Integer.parseInt(u_nivel),
                                Integer.parseInt(u_experiencia), alias, password, rol, u_picture, u_fechaNacimiento);
                        Usuario.usuarioLogueado = usuarioLogueado;// usuario logueado global
                        usuario = Usuario.usuarioLogueado;
                        logros();
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(LogrosActivity.this);
                        alerta.setMessage("Fallo en logros").setNegativeButton("Reintentar", null).create().show();
                    }
                }catch(JSONException e){
                    e.getMessage();
                }
            }
        };
        LoginRequest r = new LoginRequest(Usuario.usuarioLogueado.getU_alias(), Usuario.usuarioLogueado.getU_password(), respuesta);
        RequestQueue cola = Volley.newRequestQueue(LogrosActivity.this);
        cola.add(r);
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