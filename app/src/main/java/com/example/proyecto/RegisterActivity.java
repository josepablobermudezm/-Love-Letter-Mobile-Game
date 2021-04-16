package com.example.proyecto;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    ImageView IVPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void volverLogin(View view){
        Intent login = new Intent(RegisterActivity.this, LoginActivity.class);
        RegisterActivity.this.startActivity(login);
        finish();
    }

    public void registrarse(View view){
        //me registro
        EditText aliasAux = (EditText) findViewById(R.id.txtAlias);
        EditText contrasenaAux = (EditText) findViewById(R.id.txtContrasena);
        EditText fechaNacimientoAux = (EditText) findViewById(R.id.editTextDate);
        RadioButton administradorAux = (RadioButton) findViewById(R.id.radioButtonAdministrador);
        RadioButton jugadorAux = (RadioButton) findViewById(R.id.radioButtonJugador);
        String nombre = aliasAux.getText().toString();
        String contrasena = contrasenaAux.getText().toString();
        String fechaNacimiento = fechaNacimientoAux.getText().toString();
        String rol = administradorAux.isChecked() ? "A" : jugadorAux.isChecked() ? "J" : null;

        Response.Listener<String> respuesta = new Response.Listener<String>() {
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
                        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
                        RegisterActivity.this.startActivity(i);
                        RegisterActivity.this.finish();
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(RegisterActivity.this);
                        alerta.setMessage("Fallo en el registro").setNegativeButton("Reintentar", null).create().show();
                    }
                }catch(JSONException e){
                    e.getMessage();
                    System.out.println(e.getMessage());
                }
            }
        };
        RegistroRequest r = new RegistroRequest(nombre, fechaNacimiento, contrasena, rol, 0, 0,
                0, 0, 0, respuesta);
        RequestQueue cola = Volley.newRequestQueue(RegisterActivity.this);
        cola.add(r);
    }

    public void loadPicture(View view){
        // crea instancia de el intent del tipo de imagen
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pasa esta constante para compararla con el resultado
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // esta función pasa cuando el usuario selecciona algo del imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compara el resultado con el SELECT_PICTURE
            if (requestCode == SELECT_PICTURE) {
                // obtiene el url de la data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // pone la imagen en la vista
                    ImageView imageView = (ImageView) findViewById(R.id.IVPreviewImage);
                    imageView.setImageURI(selectedImageUri);
                }
            }
        }
    }
}