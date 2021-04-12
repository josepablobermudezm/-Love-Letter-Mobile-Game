package com.example.proyecto;

import android.os.Bundle;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

public class UsuariosActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        mTextView = (TextView) findViewById(R.id.text);

        Intent i = this.getIntent();
        String alias = i.getStringExtra("u_alias");
        String password = i.getStringExtra("u_password");
        String rol = i.getStringExtra("u_rol");

        System.out.println(alias);
        System.out.println(password);
        System.out.println(rol);
    }
}