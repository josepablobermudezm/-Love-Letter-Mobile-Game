package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

        txtAliasAux.setText("");
        txtContrasenaAux.setText("");
    }

    public void registrarse(View view) {
        //cambiamos de vista


    }
}