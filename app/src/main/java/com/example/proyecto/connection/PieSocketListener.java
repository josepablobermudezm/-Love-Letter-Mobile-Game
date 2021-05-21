package com.example.proyecto.connection;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.proyecto.WaitingRoomActivity;
import com.example.proyecto.partida.Partida;
import com.example.proyecto.registro.RegisterActivity;
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

    public PieSocketListener(String text, Context context, Partida partida, String administrador) {
        this.text = text;
        this.context = context;
        this.partida = partida;
        this.administrador = administrador;
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
            Intent nextView = new Intent(this.context, WaitingRoomActivity.class);
            nextView.putExtra("administrador", this.administrador);
            nextView.putExtra("partida", this.partida);
            this.context.startActivity(nextView);
            ((Activity) this.context).finish();
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

}
