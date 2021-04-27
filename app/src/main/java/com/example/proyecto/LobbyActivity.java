package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONObject;
import org.json.JSONException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import com.example.proyecto.usuarios.UsuariosActivity;

public class LobbyActivity extends AppCompatActivity {

    private TextView mTextView;
    Usuario usuarioLogueado = new Usuario();
    LinearLayout parentLayout;
    private WebSocket webSocket;
    private String SERVER_PATH = "link://192.168.1.7:3000";
    private RecyclerView recyclerView;
    public Button PlayButton;
    private MessageAdapter messageAdapter = new MessageAdapter();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        mTextView = (TextView) findViewById(R.id.text);

        Intent i = this.getIntent();
        parentLayout = (LinearLayout) findViewById(R.id.padre);

        Button logrosButton = new Button(this);
        logrosButton.setText("Logros");
        logrosButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(LobbyActivity.this, LogrosActivity.class);
                LobbyActivity.this.startActivity(nextActivity);
                LobbyActivity.this.finish();
            }
        });

        Button AcercaButton = new Button(this);
        AcercaButton.setText("ACERCA DE");
        AcercaButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent nextActivity = new Intent(LobbyActivity.this, AboutUsActivity.class);
                LobbyActivity.this.startActivity(nextActivity);
                LobbyActivity.this.finish();
            }
        });

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
        }

        parentLayout.addView(usuarioButton);
        parentLayout.addView(AcercaButton);
        parentLayout.addView(logrosButton);
        //initiateSocketConnection();
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