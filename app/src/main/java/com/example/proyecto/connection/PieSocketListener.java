package com.example.proyecto.connection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyecto.WaitingRoomActivity;
import com.example.proyecto.partida.Partida;
import com.example.proyecto.registro.RegisterActivity;
import com.example.proyecto.usuarios.Usuario;
import com.example.proyecto.usuarios.UsuariosActivity;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class PieSocketListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private String text;
    private Context context;
    private Partida partida;
    private String administrador;
    private LinearLayout linearLayout;
    private Usuario usuario;

    public PieSocketListener(String text, Context context, Partida partida, String administrador, LinearLayout linearLayout, Usuario usuario) {
        this.text = text;
        this.context = context;
        this.partida = partida;
        this.administrador = administrador;
        this.linearLayout = linearLayout;
        this.usuario = usuario;
    }

    public PieSocketListener(String text) {
        this.text = text;
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        webSocket.send(this.text);
    }

    @Override
    public void onMessage(WebSocket webSocket, String text) {
        System.out.println("Received : " + text);
        if(text.equals("nuevoUsuario-" + partida.getP_id())){
            /*Intent nextView = new Intent(this.context, WaitingRoomActivity.class);
            nextView.putExtra("administrador", this.administrador);
            nextView.putExtra("partida", this.partida);
            nextView.putExtra("listenerPieSocket", "false");
            this.context.startActivity(nextView);
            ((Activity) this.context).finish();*/
            agregarWaitingRooms(this.linearLayout, this.usuario);
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
        System.out.println("Closing : " + code + " / " + reason);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println("Error : " + t.getMessage());
    }

    public void output(String text){
        Log.d("PieSocket",text);
    }

    public void agregarWaitingRooms(LinearLayout parentLayout2, Usuario usuario) {
        // Agregando usuarios
        LinearLayout userLinear = new LinearLayout(this.context);
        userLinear.setOrientation(LinearLayout.HORIZONTAL);
        userLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        userLinear.setBackgroundColor((Color.parseColor("#6D6969")));
        userLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));

        LinearLayout dataLinear = new LinearLayout(this.context);
        dataLinear.setOrientation(LinearLayout.VERTICAL);
        dataLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        dataLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));

        TextView txt_alias = new TextView(this.context);
        txt_alias.setText("Alias: " + usuario.getU_alias());
        txt_alias.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        txt_alias.setTextColor(Color.WHITE);
        TextView txt_nivel = new TextView(this.context);
        txt_nivel.setText("Nivel: " + usuario.getU_nivel());
        txt_nivel.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        txt_nivel.setTextColor(Color.WHITE);
        TextView txt_fechaNacimiento = new TextView(this.context);
        txt_fechaNacimiento.setText("Fec.Nac:" + usuario.getU_fechaNacimiento());
        txt_fechaNacimiento.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        txt_fechaNacimiento.setTextColor(Color.WHITE);
        dataLinear.addView(txt_alias);
        dataLinear.addView(txt_nivel);
        dataLinear.addView(txt_fechaNacimiento);

        parentLayout2.addView(userLinear);
        userLinear.addView(dataLinear);
    }

    public int calcularPixeles(int dps) {
        final float scale = this.context.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

}
