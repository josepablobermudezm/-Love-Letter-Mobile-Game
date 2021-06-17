package com.example.proyecto.controladores;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
import com.example.proyecto.R;
import com.example.proyecto.modelos.Carta;
import com.example.proyecto.servicios.CartaRequest;
import com.example.proyecto.utilidades.PieSocketListener;
import com.example.proyecto.modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import okhttp3.WebSocket;

public class GameActivity extends AppCompatActivity {

    private TextView mTextView;
    private LinearLayout parentLayout;
    private LinearLayout parentLayout2;
    private LinearLayout parentLayout3;
    private ArrayList <Carta> cartas = new ArrayList();
    private ArrayList <Carta> mazo = new ArrayList();
    private String administrador;
    public PieSocketListener listener;
    public WebSocket ws;
    private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        parentLayout2 = (LinearLayout) findViewById(R.id.parentLayout2);
        parentLayout3 = findViewById(R.id.parentLayout3);
        mTextView = (TextView) findViewById(R.id.text);

        this.ws = WaitingRoomActivity.ws;
        this.listener = WaitingRoomActivity.listener;

        Intent intent = this.getIntent();

        administrador = intent.getStringExtra("administrador");
        usuario = (Usuario) intent.getSerializableExtra("usuario");

        ImageView img1 = (ImageView) findViewById(R.id.Carta1);
        ImageView img2 = (ImageView) findViewById(R.id.Carta2);
        listener.setImg1(img1);
        listener.setImg2(img2);

        TextView textView = findViewById(R.id.txv_turno);
        listener.setTurno(textView);
        listener.setContext(getApplicationContext());

        if(administrador.equals("true")){
            turno();
            listener.setUsuarios(WaitingRoomActivity.usuarios);
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
                            for(Usuario u : WaitingRoomActivity.usuarios){
                                Carta carta = cartas.get(cartas.size()-1);
                                cartas.remove(carta);
                                String value = "enviarCartas,"+carta.getNombre()+","+ carta.getValor() +","+u.getU_id();
                                listener.enviarMensaje(ws, value);
                            }

                            String turno = "turno," + listener.getUsuarios().get(0).getU_alias();
                            listener.enviarMensaje(ws, turno);
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

    }

    public void turno(){
        Calendar today = new GregorianCalendar();
        today.setTime(new Date());

        // Sacar las edades de los jugadores
        for(Usuario u : WaitingRoomActivity.usuarios) {
            String[] fecha = u.getU_fechaNacimiento().split("/", 3);
            Calendar birthDay = new GregorianCalendar(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
            int yearsInBetween = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            u.setEdad(yearsInBetween);
        }

        // Ordenar la lista de los jugadores en la partida
        Collections.sort(WaitingRoomActivity.usuarios, new Comparator<Usuario>() {
            @Override
            public int compare(Usuario p1, Usuario p2) {
                if( p1.getEdad() > p2.getEdad() ){
                    return 1;
                }
                if( p1.getEdad() < p2.getEdad() ){
                    return -1;
                }
                return 0;
            }
        });

        // Se le da el turno al jugador más joven
        WaitingRoomActivity.usuarios.get(0).setTurno(true);
    }
}