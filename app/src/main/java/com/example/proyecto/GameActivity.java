package com.example.proyecto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.connection.PieSocketListener;
import com.example.proyecto.partida.PartidaActivity;
import com.example.proyecto.partida.PartidaRequest;
import com.example.proyecto.usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;

public class GameActivity extends AppCompatActivity {

    private TextView mTextView;
    private LinearLayout parentLayout;
    private LinearLayout parentLayout2;
    private ConstraintLayout parentLayout3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        parentLayout2 = (LinearLayout) findViewById(R.id.parentLayout2);
        parentLayout3 = (ConstraintLayout) findViewById(R.id.parentLayout3);
        mTextView = (TextView) findViewById(R.id.text);
        Button connect = (Button) findViewById(R.id.connect);

        OkHttpClient client = new OkHttpClient();

        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("PieSocket","Connecting");
                String apiKey = "dwRO3yR7VvymQk1HfYHqJBK22coq0TnEW90aqcN4"; //Demo key, get yours at https://piesocket.com
                int channelId = 1;

                Request request = new Request.Builder()
                        .url("http://winadate.atwebpages.com/websocket.php/v3/1?apikey=dwRO3yR7VvymQk1HfYHqJBK22coq0TnEW90aqcN4&notify_self")
                        .build();

                PieSocketListener listener =  new PieSocketListener();
                WebSocket ws = client.newWebSocket(request, listener);
            }
        });
    }

    public void volverLobby(View view){
        Intent nextActivity = new Intent(GameActivity.this, WaitingRoomActivity.class);
        GameActivity.this.startActivity(nextActivity);
        GameActivity.this.finish();
    }
}