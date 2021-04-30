package com.example.proyecto.partida;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.LobbyActivity;
import com.example.proyecto.R;
import com.example.proyecto.WaitingRoomActivity;
import com.example.proyecto.usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PartidaActivity extends AppCompatActivity {

    private TextView mTextView;
    private LinearLayout parentLayout;
    private LinearLayout parentLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida);
        parentLayout2 = (LinearLayout) findViewById(R.id.parentLayout2);
        mTextView = (TextView) findViewById(R.id.text);
        cargarPartidas();
    }

    private void cargarPartidas(){
        // obtenemos todas las partidas
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
                    JSONArray partidas = jsonRespuesta.getJSONArray("partidas");
                    for (int x = 0; x < partidas.length(); x++) {
                        JSONObject elemento = partidas.getJSONObject(x);
                        Partida partida = new Partida(Integer.parseInt(elemento.getString("p_id")), Integer.parseInt(elemento.getString("p_cantidadJugadores")),
                                elemento.getString("p_tipo"), elemento.getString("p_codigo"),
                                Integer.parseInt(elemento.getString("p_nivelMinimo")), Integer.parseInt(elemento.getString("p_nivelMinimo")));
                            agregarPartidas(partida);
                            Partida.partidas.add(partida);
                    }
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if (ok) {
                        System.out.println(jsonRespuesta);
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(PartidaActivity.this);
                        alerta.setMessage("Fallo en la partida").setNegativeButton("Reintentar", null).create().show();
                    }
                } catch (JSONException e) {
                    e.getMessage();
                }
            }
        };

        PartidaRequest r = new PartidaRequest(respuesta);
        RequestQueue cola = Volley.newRequestQueue(PartidaActivity.this);
        cola.add(r);
    }

    public void agregarPartidas(Partida partida) {
        // Agregando partidas
        LinearLayout partidaLinear = new LinearLayout(this);
        partidaLinear.setOrientation(LinearLayout.HORIZONTAL);
        partidaLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        partidaLinear.setBackgroundColor(Color.parseColor("#524f4f"));
        partidaLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));

        LinearLayout dataLinear = new LinearLayout(this);
        dataLinear.setOrientation(LinearLayout.VERTICAL);
        dataLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        dataLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));

        TextView txt_id = new TextView(this);
        txt_id.setText("# Partida: " + partida.getP_id());
        txt_id.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        txt_id.setTextColor(Color.parseColor("#FFFFFF"));
        TextView txt_jugadores = new TextView(this);
        txt_jugadores.setText("# Jugadores: " + partida.getP_cantidadJugadores());
        txt_jugadores.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                65));
        txt_jugadores.setTextColor(Color.parseColor("#FFFFFF"));
        dataLinear.addView(txt_id);
        if(partida.getP_tipo().equals("PR")) {
            TextView txtnivel = new TextView(this);
            txtnivel.setText("Nivel:" + partida.getP_nivelMinimo());
            txtnivel.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    50));
            txtnivel.setTextColor(Color.parseColor("#FFFFFF"));
            dataLinear.addView(txtnivel);
        }
        dataLinear.addView(txt_jugadores);

        LinearLayout buttonLinear = new LinearLayout(this);
        buttonLinear.setOrientation(LinearLayout.VERTICAL);
        buttonLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        buttonLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));
        buttonLinear.setGravity(Gravity.RIGHT);

        ImageView editbutton = new ImageView(this);
        if(Usuario.usuarioLogueado.getU_rol().equals("A")){
            editbutton.setImageResource(R.drawable.edit);
            editbutton.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(28),
                    calcularPixeles(28)));
            setMargins(editbutton, 0, 0, 0, 35);
            editbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent nextView = new Intent(PartidaActivity.this, CrearPartidaActivity.class);
                    nextView.putExtra("editActivity", "true");
                    nextView.putExtra("editPartida", partida);
                    nextView.putExtra("partidasActivity", "false");
                    PartidaActivity.this.startActivity(nextView);
                    finish();
                }
            });
        }

        ImageView deletebutton = new ImageView(this);
        if(Usuario.usuarioLogueado.getU_rol().equals("A")){
            deletebutton.setImageResource(R.drawable.delete);
            deletebutton.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(28),
                    calcularPixeles(28)));
            deletebutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // eliminamos el usuario
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
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if (ok) {
                                    parentLayout2.removeAllViews();// limpiamos la vista
                                    cargarPartidas();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(PartidaActivity.this);
                                    alerta.setMessage("Fallo al eliminar el usuario").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };
                    PartidaRequest r = new PartidaRequest(String.valueOf(partida.getP_id()), respuesta);
                    RequestQueue cola = Volley.newRequestQueue(PartidaActivity.this);
                    cola.add(r);
                }
            });
        }

        ImageView joinbutton = new ImageView(this);
        if(Usuario.usuarioLogueado.getU_rol().equals("J")){
            joinbutton.setImageResource(R.drawable.join);
            joinbutton.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(28),
                    calcularPixeles(28)));
            joinbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // agregamos el usuario a la partida
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
                                boolean ok = jsonRespuesta.getBoolean("success");
                                if (ok) {
                                    Intent nextActivity = new Intent(PartidaActivity.this, WaitingRoomActivity.class);
                                    nextActivity.putExtra("administrador", partida.getP_fkUsuario() == Usuario.usuarioLogueado.getU_id() ? "true" : "false");
                                    nextActivity.putExtra("p_id", partida.getP_id().toString());
                                    PartidaActivity.this.startActivity(nextActivity);
                                    PartidaActivity.this.finish();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(PartidaActivity.this);
                                    alerta.setMessage("Error al unirse a la partida").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };
                    PartidaRequest r = new PartidaRequest(Usuario.usuarioLogueado.getU_id(), partida.getP_id(), respuesta);
                    RequestQueue cola = Volley.newRequestQueue(PartidaActivity.this);
                    cola.add(r);
                }
            });
        }

        parentLayout2.addView(partidaLinear);
        partidaLinear.addView(dataLinear);
        if(Usuario.usuarioLogueado.getU_rol().equals("A")) {
            buttonLinear.addView(editbutton);
            buttonLinear.addView(deletebutton);
        }else if(Usuario.usuarioLogueado.getU_rol().equals("J")){
            buttonLinear.addView(joinbutton);
        }
        partidaLinear.addView(buttonLinear);
    }

    public int calcularPixeles(int dps) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    public void crearPartida(View view){
        //Redirecciona a la otra vista
        Intent nextActivity = new Intent(PartidaActivity.this, CrearPartidaActivity.class);
        nextActivity.putExtra("editActivity", "false");
        PartidaActivity.this.startActivity(nextActivity);
        PartidaActivity.this.finish();
    }

    public void volverLobby(View view){
        Intent nextActivity = new Intent(PartidaActivity.this, LobbyActivity.class);
        PartidaActivity.this.startActivity(nextActivity);
        PartidaActivity.this.finish();
    }
}