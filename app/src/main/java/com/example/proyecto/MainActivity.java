package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

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
                        String alias = jsonRespuesta.getString("u_alias");
                        String password = jsonRespuesta.getString("u_password");
                        String rol = jsonRespuesta.getString("u_rol");

                        Intent usuariosActivity = new Intent(MainActivity.this, UsuariosActivity.class);
                        usuariosActivity.putExtra("u_alias", alias);
                        usuariosActivity.putExtra("u_password", password);
                        usuariosActivity.putExtra("u_rol", rol);
                        MainActivity.this.startActivity(usuariosActivity);
                        MainActivity.this.finish();
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
                        alerta.setMessage("Fallo en el login").setNegativeButton("Reintentar", null).create().show();
                    }
                }catch(JSONException e){
                    e.getMessage();
                }
            }
        };
        LoginRequest r = new LoginRequest(txtAlias, txtContrasena,respuesta);
        RequestQueue cola = Volley.newRequestQueue(MainActivity.this);
        cola.add(r);

        txtAliasAux.setText("");
        txtContrasenaAux.setText("");
    }

    public void registrarse(View view) {
        Intent registro = new Intent(MainActivity.this, RegisterActivity.class);
        MainActivity.this.startActivity(registro);
        finish();
    }
}