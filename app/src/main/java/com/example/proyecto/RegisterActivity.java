package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

public class RegisterActivity extends AppCompatActivity {

    int SELECT_PICTURE = 200;

    ImageView IVPreviewImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void volverLogin(View view){
        System.out.println("yo mama");
        Intent login = new Intent(RegisterActivity.this, MainActivity.class);
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
        String tipoUsuario = administradorAux.isChecked() ? "A" : jugadorAux.isChecked() ? "J" : null;

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