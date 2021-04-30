package com.example.proyecto;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.partida.CrearPartidaActivity;
import com.example.proyecto.partida.Partida;
import com.example.proyecto.partida.PartidaActivity;
import com.example.proyecto.partida.PartidaRequest;
import com.example.proyecto.usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WaitingRoomActivity extends AppCompatActivity {

    private TextView mTextView;
    private LinearLayout parentLayout;
    private LinearLayout parentLayout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waitingroom);
        parentLayout2 = (LinearLayout) findViewById(R.id.parentLayout2);
        mTextView = (TextView) findViewById(R.id.text);
    }

    public void agregarWaitingRooms(Partida partida) {
        // Agregando WaitingRooms
        /*LinearLayout partidaLinear = new LinearLayout(this);
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
                    Intent nextView = new Intent(WaitingRoomActivity.this, CrearPartidaActivity.class);
                    nextView.putExtra("editActivity", "true");
                    nextView.putExtra("editPartida", partida);
                    nextView.putExtra("WaitingRoomsActivity", "false");
                    WaitingRoomActivity.this.startActivity(nextView);
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
                                    cargarWaitingRooms();
                                } else {
                                    AlertDialog.Builder alerta = new AlertDialog.Builder(WaitingRoomActivity.this);
                                    alerta.setMessage("Fallo al eliminar el usuario").setNegativeButton("Reintentar", null).create().show();
                                }
                            } catch (JSONException e) {
                                e.getMessage();
                            }
                        }
                    };
                    PartidaRequest r = new PartidaRequest(String.valueOf(partida.getP_id()), respuesta);
                    RequestQueue cola = Volley.newRequestQueue(WaitingRoomActivity.this);
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
                    // eliminamos el usuario
                    /*Response.Listener<String> respuesta = new Response.Listener<String>() {
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
                                    cargarWaitingRooms();
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

        parentLayout2.addView(partidaLinear);
        partidaLinear.addView(dataLinear);
        if(Usuario.usuarioLogueado.getU_rol().equals("A")) {
            buttonLinear.addView(editbutton);
            buttonLinear.addView(deletebutton);
        }else if(Usuario.usuarioLogueado.getU_rol().equals("J")){
            buttonLinear.addView(joinbutton);
        }
        partidaLinear.addView(buttonLinear);*/
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

    public void empezarPartida(View view){
        //Redirecciona a la otra vista
        /*Intent nextActivity = new Intent(WaitingRoomActivity.this, CrearPartidaActivity.class);
        WaitingRoomActivity.this.startActivity(nextActivity);
        WaitingRoomActivity.this.finish();*/
    }

    public void volverLobby(View view){
        Intent nextActivity = new Intent(WaitingRoomActivity.this, PartidaActivity.class);
        WaitingRoomActivity.this.startActivity(nextActivity);
        WaitingRoomActivity.this.finish();
    }
}