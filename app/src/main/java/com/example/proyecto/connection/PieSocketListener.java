package com.example.proyecto.connection;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.GameActivity;
import com.example.proyecto.HiloImagenes;
import com.example.proyecto.HiloWaiting;
import com.example.proyecto.WaitingRoomActivity;
import com.example.proyecto.carta.Carta;
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

import static com.example.proyecto.WaitingRoomActivity.usuarios;

public class PieSocketListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private String text;
    private Context context;
    private Partida partida;
    private String administrador;
    private LinearLayout parentLayout2;
    private Usuario usuario;
    private ImageView img1,img2;


    public PieSocketListener(String text, Context context, Partida partida, String administrador, LinearLayout parentLayout2, Usuario usuario) {
        this.text = text;
        this.partida = partida;
        this.administrador = administrador;
        this.usuario = usuario;
        this.parentLayout2 = parentLayout2;
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ImageView getImg1() {
        return img1;
    }

    public void setImg1(ImageView img1) {
        this.img1 = img1;
    }

    public ImageView getImg2() {
        return img2;
    }

    public void setImg2(ImageView img2) {
        this.img2 = img2;
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
        if (text.equals("nuevoUsuario-" + partida.getP_id())) {
            HiloWaiting hilo = new HiloWaiting(this.context, this.parentLayout2, this.usuario, this.partida);
            hilo.execute();
        } else if (text.equals("inicio partida")) {
            Intent intent = new Intent(this.context, GameActivity.class);
            intent.putExtra("administrador", administrador);
            intent.putExtra("usuario", usuario);
            context.startActivity(intent);
        }else {
            String[] arrSplit_2 = text.split(",", 6);
             for(int i = 0; i<arrSplit_2.length; i++){
                 System.out.println("Posición: " + i + " " +arrSplit_2[i]);
             }
            switch (arrSplit_2[0]){
                case "enviarCartas":
                    String carta1 = arrSplit_2[1];
                    int valor = Integer.valueOf(arrSplit_2[2]);
                    String carta2 = arrSplit_2[3];
                    int valor2 = Integer.valueOf(arrSplit_2[4]);
                    String id = arrSplit_2[5];

                    if(id.equals(String.valueOf(usuario.getU_id()))){//Si somos
                        Carta cartaAux = new Carta(carta1, valor);
                        Carta cartaAux2 = new Carta(carta2, valor2);

                        usuario.getMazo().add(cartaAux);
                        usuario.getMazo().add(cartaAux2);
                        HiloImagenes hilo = new HiloImagenes(this.getContext(), this.getImg1(), this.getImg2(), carta1, carta2);
                        hilo.execute();
                    }
                    break;
                case "":
                    break;
            }
            if(text.replace("enviarCartas", "").equals(usuario.getU_id())){

            }
        }
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        webSocket.close(NORMAL_CLOSURE_STATUS, null);
    }

    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        System.out.println("Error : " + t.getMessage());
    }

    public void enviarMensaje(WebSocket webSocket, String text) {
        webSocket.send(text);
    }

    public void output(String text) {
        Log.d("PieSocket", text);
    }
}
