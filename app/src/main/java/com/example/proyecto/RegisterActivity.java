package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

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

    }
}