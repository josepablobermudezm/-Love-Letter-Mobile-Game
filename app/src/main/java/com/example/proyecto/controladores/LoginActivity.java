package com.example.proyecto.controladores;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void iniciarSesion(View view){
        EditText txtAliasAux = (EditText) findViewById(R.id.txtNivel);
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


                        Intent nextActivity = new Intent(LoginActivity.this, LobbyActivity.class);
                        Usuario usuarioLogueado = new Usuario(Integer.parseInt(u_id), Integer.parseInt(u_cantidadPartidasJugadas),
                                Integer.parseInt(u_cantidadPartidasGanadas), Integer.parseInt(u_cantidadAmigos), Integer.parseInt(u_nivel),
                                Integer.parseInt(u_experiencia), alias, password, rol, u_picture, u_fechaNacimiento);
                        Usuario.usuarioLogueado = usuarioLogueado;// usuario logueado global
                        LoginActivity.this.startActivity(nextActivity);
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
        registro.putExtra("UsuariosActivity", "false");
        registro.putExtra("editActivity", "false");
        LoginActivity.this.startActivity(registro);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}