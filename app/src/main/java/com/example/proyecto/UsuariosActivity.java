package com.example.proyecto;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuariosActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        mTextView = (TextView) findViewById(R.id.text);
        Intent i = this.getIntent();
        Usuario usuarioLogueado = new Usuario( Integer.parseInt(i.getStringExtra("u_id")), Integer.parseInt(i.getStringExtra("u_cantidadPartidasJugadas")),
                Integer.parseInt(i.getStringExtra("u_cantidadPartidasGanadas")), Integer.parseInt(i.getStringExtra("u_cantidadAmigos")),
                Integer.parseInt(i.getStringExtra("u_nivel")), Integer.parseInt(i.getStringExtra("u_experiencia")),
                i.getStringExtra("u_alias"), i.getStringExtra("u_password"), i.getStringExtra("u_rol"),
                i.getStringExtra("u_picture"), i.getStringExtra("u_fechaNacimiento"));

        System.out.println(usuarioLogueado.toString());

        // obtenemos todos los usuarios
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
                        System.out.println(jsonRespuesta);
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(UsuariosActivity.this);
                        alerta.setMessage("Fallo en el login").setNegativeButton("Reintentar", null).create().show();
                    }
                }catch(JSONException e){
                    e.getMessage();
                }
            }
        };
        UsuariosRequest r = new UsuariosRequest(respuesta);
        RequestQueue cola = Volley.newRequestQueue(UsuariosActivity.this);
        cola.add(r);
    }
}