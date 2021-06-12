package com.example.proyecto.controladores;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.proyecto.R;

public class AboutUsActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        mTextView = (TextView) findViewById(R.id.text);
    }

    public void volverLobby(View view){
        Intent nextActivity = new Intent(AboutUsActivity.this, LobbyActivity.class);
        AboutUsActivity.this.startActivity(nextActivity);
        AboutUsActivity.this.finish();
    }
}