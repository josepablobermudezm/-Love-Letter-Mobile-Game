package com.example.proyecto.utilidades;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.proyecto.controladores.GameActivity;
import com.example.proyecto.controladores.WaitingRoomActivity;
import com.example.proyecto.modelos.Carta;
import com.example.proyecto.modelos.Partida;
import com.example.proyecto.modelos.Usuario;

import java.util.ArrayList;

import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import static com.example.proyecto.controladores.GameActivity.txv_turno;

public class PieSocketListener extends WebSocketListener {

    private static final int NORMAL_CLOSURE_STATUS = 1000;
    private String text;
    private Context context;
    private Partida partida;
    private String administrador;
    private LinearLayout parentLayout2;
    private Usuario usuario;
    private ImageView img1,img2;
    private ArrayList<Usuario> usuarios;
    private TextView turno;

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
            String[] arrSplit_2 = text.split(",", 5);
            switch (arrSplit_2[0]){
                case "enviarCartas":
                    String carta1 = arrSplit_2[1];
                    int valor = Integer.valueOf(arrSplit_2[2]);
                    String id = arrSplit_2[3];
                    if(id.equals(String.valueOf(usuario.getU_id()))){
                        Carta cartaAux = new Carta(carta1, valor);
                        if(arrSplit_2[4].equals("mazoCentral")){
                            usuario.getMazoCentral().add(cartaAux);
                        }else{
                            usuario.getMazo().add(cartaAux);
                            HiloImagenes hilo = new HiloImagenes(this.getContext(), this.getImg1(), this.getImg2(), carta1, null);
                            hilo.execute();
                        }
                    }
                    break;
                case "turno":
                    System.out.println("TURNOOOOOO");
                    break;
                case "agregarCarta":
                    String id2 = arrSplit_2[1];
                    Carta cartaAux = usuario.getMazoCentral().get(usuario.getMazoCentral().size()-1);
                    usuario.getMazoCentral().remove(cartaAux);
                    if(id2.equals(String.valueOf(usuario.getU_id()))){
                        usuario.getMazo().add(cartaAux);
                        System.out.println(this.getImg1());
                        System.out.println(this.getImg2());
                        HiloImagenes hilo = new HiloImagenes(this.getContext(), this.getImg1(), this.getImg2(),
                                this.getImg1().getDrawable() != null ? null : cartaAux.getNombre(),
                                    this.getImg2().getDrawable() != null ? null : cartaAux.getNombre());
                        hilo.execute();
                    }
                    break;
                case "cambioTurno":
                    if(WaitingRoomActivity.usuarios.size()-1 == GameActivity.jugadorActual){
                        GameActivity.jugadorActual = 0;
                        break;
                    }
                    new AsyncTask<String, Float, Integer>(){
                        @Override
                        protected Integer doInBackground(String... strings) {
                            publishProgress();
                            return null;
                        }

                        @Override
                        protected void onProgressUpdate(Float... variable) {
                            txv_turno.setText(WaitingRoomActivity.usuarios.get(GameActivity.jugadorActual).getU_alias());
                        }
                    }.execute();

                    GameActivity.jugadorActual++;
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + arrSplit_2[0]);
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

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public TextView getTurno() {
        return turno;
    }

    public void setTurno(TextView turno) {
        this.turno = turno;
    }

    public void output(String text) {
        Log.d("PieSocket", text);
    }


}
