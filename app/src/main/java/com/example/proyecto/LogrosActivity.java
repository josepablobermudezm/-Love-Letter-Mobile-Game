package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LogrosActivity extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logros);

        mTextView = (TextView) findViewById(R.id.text);
    }

    public void volverLobby(View view){
        Intent nextActivity = new Intent(LogrosActivity.this, LobbyActivity.class);
        LogrosActivity.this.startActivity(nextActivity);
        LogrosActivity.this.finish();
    }
}