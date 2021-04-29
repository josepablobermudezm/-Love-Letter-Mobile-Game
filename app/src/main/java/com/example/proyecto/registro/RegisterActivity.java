package com.example.proyecto.registro;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.R;
import com.example.proyecto.usuarios.Usuario;
import com.example.proyecto.usuarios.UsuariosActivity;
import com.example.proyecto.usuarios.UsuariosRequest;
import com.example.proyecto.login.LoginActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    ImageView IVPreviewImage;
    String usuariosActivity;
    String editActivity;
    EditText aliasAux;
    EditText contrasenaAux;
    EditText fechaNacimientoAux;
    RadioButton administradorAux;
    RadioButton jugadorAux;
    Usuario editusuario = new Usuario();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button registerButton = (Button)findViewById(R.id.registerButton);
        aliasAux = (EditText) findViewById(R.id.txtNivel);
        contrasenaAux = (EditText) findViewById(R.id.txtContrasena);
        fechaNacimientoAux = (EditText) findViewById(R.id.txtcantJugadores);
        administradorAux = (RadioButton) findViewById(R.id.rbPrivado);
        jugadorAux = (RadioButton) findViewById(R.id.rbPublica);
        Intent i = this.getIntent();
        usuariosActivity = i.getStringExtra("UsuariosActivity");
        editActivity = i.getStringExtra("editActivity");
        registerButton.setText(usuariosActivity.equals("true") ? "Registrar" : editActivity.equals("true") ? "Editar" : "Registrarse");

        if(editActivity.equals("true")){
            editusuario = (Usuario) i.getSerializableExtra("editUsuario");
            aliasAux.setText(editusuario.getU_alias());
            contrasenaAux.setText(editusuario.getU_password());
            fechaNacimientoAux.setText(editusuario.getU_fechaNacimiento());
            administradorAux.setChecked(editusuario.getU_rol().equals("A"));
            jugadorAux.setChecked(editusuario.getU_rol().equals("J"));
        }
    }

    public void volverLogin(View view){
        Intent nextView = new Intent(RegisterActivity.this,
                usuariosActivity.equals("true") || editActivity.equals("true") ? UsuariosActivity.class : LoginActivity.class);
        RegisterActivity.this.startActivity(nextView);
        finish();
    }

    public void registrarse(View view){

        String nombre = aliasAux.getText().toString();
        String contrasena = contrasenaAux.getText().toString();
        String fechaNacimiento = fechaNacimientoAux.getText().toString();
        String rol = administradorAux.isChecked() ? "A" : jugadorAux.isChecked() ? "J" : null;
        if(editActivity.equals("true")) {
            editusuario.setU_alias(nombre);
            editusuario.setU_password(contrasena);
            editusuario.setU_fechaNacimiento(fechaNacimiento);
            editusuario.setU_rol(rol);
        }

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

        if(editActivity.equals("true")){
            UsuariosRequest r = new UsuariosRequest(editusuario, respuesta);
            // si el usuario que se está editando es el usuario logueado se debe actualizar el objeto estático
            if(Usuario.usuarioLogueado.getU_id()==editusuario.getU_id()){
                Usuario.usuarioLogueado = editusuario;
            }
            RequestQueue cola = Volley.newRequestQueue(RegisterActivity.this);
            cola.add(r);
        }else{
            RegistroRequest r = new RegistroRequest(nombre, fechaNacimiento, contrasena, rol, 0, 0,
                    0, 0, 0, respuesta);
            RequestQueue cola = Volley.newRequestQueue(RegisterActivity.this);
            cola.add(r);
        }
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