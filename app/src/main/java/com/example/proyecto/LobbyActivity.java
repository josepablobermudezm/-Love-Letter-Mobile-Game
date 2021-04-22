package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;
import org.json.JSONException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;

public class LobbyActivity extends AppCompatActivity {

    private TextView mTextView;
    Usuario usuarioLogueado = new Usuario();
    LinearLayout parentLayout;
    private WebSocket webSocket;
    private String SERVER_PATH = "ws://echo.websocket.org";
    private RecyclerView recyclerView;
    public Button PlayButton;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 10);


        mTextView = (TextView) findViewById(R.id.text);

        Intent i = this.getIntent();
        parentLayout = (LinearLayout) findViewById(R.id.padre);
        Button AcercaButton = new Button(this);
        AcercaButton.setText("ACERCA DE");

        Button usuarioButton = new Button(this);
        usuarioButton.setText(Usuario.usuarioLogueado.getU_rol().equals("A") ? "USUARIOS" : "CUENTA");
        usuarioButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(LobbyActivity.this, UsuariosActivity.class);
                LobbyActivity.this.startActivity(nextActivity);
                LobbyActivity.this.finish();
            }
        });

        if(Usuario.usuarioLogueado.getU_rol().equals("J")){
            PlayButton = new Button(this);
            PlayButton.setText("NUEVA PARTIDA");
            parentLayout.addView(PlayButton);

            PlayButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    //enviamos un mensaje de inicio de partida
                }
            });
        }

        parentLayout.addView(usuarioButton);
        parentLayout.addView(AcercaButton);
    }



    public void crearPartida(){

    }

    private void initiateSocketConnection(){
        OkHttpClient cliente = new OkHttpClient();
        Request request = new Request.Builder().url(SERVER_PATH).build();
        webSocket = cliente.newWebSocket(request, new SocketListener());

    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);

            runOnUiThread(() -> {
                Toast.makeText(LobbyActivity.this,
                        "Socket Connection Successful!",
                        Toast.LENGTH_SHORT).show();

                initializeView();
            });

        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);

            runOnUiThread(() -> {
                try {
                    JSONObject jsonObject = new JSONObject(text);
                    jsonObject.put("isSent", false);
                    messageAdapter.addItem(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            });

        }
    }

    private void initializeView() {
        PlayButton.setOnClickListener(v -> {
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("name", "nombre");
                jsonObject.put("message", "Hola mundo");
                webSocket.send(jsonObject.toString());
                jsonObject.put("isSent", true);
                messageAdapter.addItem(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
     }

}