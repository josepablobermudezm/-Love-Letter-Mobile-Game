package com.example.proyecto.connection;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.GameActivity;
import com.example.proyecto.HiloWaiting;
import com.example.proyecto.WaitingRoomActivity;
import com.example.proyecto.partida.Partida;
import com.example.proyecto.partida.PartidaRequest;
import com.example.proyecto.usuarios.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class PieSocketListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private String text;
    private Context context;
    private Partida partida;
    private String administrador;
    private LinearLayout parentLayout2;
    private Usuario usuario;

    public PieSocketListener(String text, Context context, Partida partida, String administrador, LinearLayout parentLayout2, Usuario usuario) {
        this.text = text;
        this.partida = partida;
        this.administrador = administrador;
        this.usuario = usuario;
        this.parentLayout2 =  parentLayout2;
        this.context = context;


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
            HiloWaiting hilo = new HiloWaiting(this.context,this.parentLayout2,this.usuario, this.partida);
            hilo.execute();
        }else if(text.equals("inicio partida")){
            Intent intent = new Intent(this.context, GameActivity.class);
            context.startActivity(intent);

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

    public void enviarMensaje(WebSocket webSocket, String text){
        webSocket.send(text);
    }
    public void output(String text){
        Log.d("PieSocket",text);
    }
}
