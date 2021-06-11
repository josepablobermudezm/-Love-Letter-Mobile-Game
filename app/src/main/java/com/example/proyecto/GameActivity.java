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
import com.example.proyecto.carta.Carta;
import com.example.proyecto.carta.CartaRequest;
import com.example.proyecto.connection.PieSocketListener;
import com.example.proyecto.login.LoginActivity;
import com.example.proyecto.partida.Partida;
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
    private ArrayList <Carta> cartas = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        parentLayout2 = (LinearLayout) findViewById(R.id.parentLayout2);
        parentLayout3 = (ConstraintLayout) findViewById(R.id.parentLayout3);
        mTextView = (TextView) findViewById(R.id.text);


        Response.Listener<String> respuesta = new Response.Listener<String>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(String response) {
                try {
                    response = response.replaceFirst("<font>.*?</font>", "");
                    int jsonStart = response.indexOf("{");
                    int jsonEnd = response.lastIndexOf("}");
                    if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                        response = response.substring(jsonStart, jsonEnd + 1);
                    }

                    JSONObject jsonRespuesta = new JSONObject(response);
                    JSONArray cartasJson = jsonRespuesta.getJSONArray("cartas");

                    for (int x = 0; x < cartasJson.length(); x++) {
                        Carta carta = new Carta();
                        switch ((String)cartasJson.get(x)){
                            case "espia":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(0);
                                break;
                            case "guardia":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(1);
                                break;
                            case "sacerdote":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(2);
                                break;
                            case "baron":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(3);
                                break;
                            case "doncella":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(4);
                                break;
                            case "principe":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(5);
                                break;
                            case "canciller":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(6);
                                break;
                            case "rey":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(7);
                                break;
                            case "condesa":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(8);
                                break;
                            case "princesa":
                                carta.setNombre((String)cartasJson.get(x));
                                carta.setValor(9);
                                break;
                        }
                        cartas.add(carta);
                    }

                    boolean ok = jsonRespuesta.getBoolean("success");

                    if (ok) {
                        
                        Carta carta = cartas.get((int) Math.random()*21 + 1);
                        cartas.remove(carta);
                        Carta carta2 = cartas.get((int) Math.random()*20 + 1);
                        cartas.remove(carta2);
                        int code = getResources().getIdentifier(carta.getNombre(), "drawable", getPackageName());
                        ((ImageView)findViewById(R.id.Carta1)).setImageResource(code);
                        code = getResources().getIdentifier(carta2.getNombre(), "drawable", getPackageName());
                        ((ImageView)findViewById(R.id.Carta2)).setImageResource(code);
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(GameActivity.this);
                        alerta.setMessage("Fallo en la partida").setNegativeButton("Reintentar", null).create().show();
                    }
                } catch (JSONException e) {
                    e.getMessage();
                }
            }
        };

        CartaRequest r = new CartaRequest(respuesta);

        RequestQueue cola = Volley.newRequestQueue(GameActivity.this);
        cola.add(r);
    }

    public void volverLobby(View view) {

        Intent nextActivity = new Intent(GameActivity.this, PartidaActivity.class);
        GameActivity.this.startActivity(nextActivity);
        GameActivity.this.finish();

    }

}