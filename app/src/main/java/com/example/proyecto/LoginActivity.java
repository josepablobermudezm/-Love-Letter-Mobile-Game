package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void iniciarSesion(View view){
        EditText txtAliasAux = (EditText) findViewById(R.id.txtAlias);
        EditText txtContrasenaAux = (EditText) findViewById(R.id.txtContrasena);

        String txtAlias = txtAliasAux.getText().toString();
        String txtContrasena = txtContrasenaAux.getText().toString();

        Response.Listener<String> respuesta = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    response = response.replaceFirst("<font>.*?</font>", "");
                    int jsonStart = response.indexOf("{");
                    int jsonEnd = response.lastIndexOf("}");

                    if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                        response = response.substring(jsonStart, jsonEnd + 1);
                    } else {
                        // deal with the absence of JSON content here
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

                        Intent usuariosActivity = new Intent(LoginActivity.this, UsuariosActivity.class);
                        usuariosActivity.putExtra("u_id", u_id);
                        usuariosActivity.putExtra("u_alias", alias);
                        usuariosActivity.putExtra("u_password", password);
                        usuariosActivity.putExtra("u_rol", rol);
                        usuariosActivity.putExtra("u_picture", u_picture);
                        usuariosActivity.putExtra("u_fechaNacimiento", u_fechaNacimiento);
                        usuariosActivity.putExtra("u_cantidadPartidasJugadas", u_cantidadPartidasJugadas);
                        usuariosActivity.putExtra("u_cantidadPartidasGanadas", u_cantidadPartidasGanadas);
                        usuariosActivity.putExtra("u_cantidadAmigos", u_cantidadAmigos);
                        usuariosActivity.putExtra("u_nivel", u_nivel);
                        usuariosActivity.putExtra("u_experiencia", u_experiencia);
                        LoginActivity.this.startActivity(usuariosActivity);
                        LoginActivity.this.finish();
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(LoginActivity.this);
                        alerta.setMessage("Fallo en el login").setNegativeButton("Reintentar", null).create().show();
                    }
                }catch(JSONException e){
                    e.getMessage();
                }
            }
        };
        LoginRequest r = new LoginRequest(txtAlias, txtContrasena, respuesta);
        RequestQueue cola = Volley.newRequestQueue(LoginActivity.this);
        cola.add(r);
    }

    public void registrarse(View view) {
        Intent registro = new Intent(LoginActivity.this, RegisterActivity.class);
        LoginActivity.this.startActivity(registro);
        finish();
    }
}