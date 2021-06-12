package com.example.proyecto.utilidades;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.controladores.WaitingRoomActivity;
import com.example.proyecto.modelos.Partida;
import com.example.proyecto.servicios.PartidaRequest;
import com.example.proyecto.modelos.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HiloWaiting extends AsyncTask<String, Float, Integer> {
    private View v;
    private static class View {
        Context contexto;
        LinearLayout parentLayout2;
        Usuario usuario;
        Partida partida;
    }
    public HiloWaiting(Context context, LinearLayout parentLayout2, Usuario usuario, Partida partida) {
        v = new View();
        v.contexto = context;
        v.parentLayout2 = parentLayout2;
        v.usuario = usuario;
        v.partida = partida;

    }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected Integer doInBackground(String... variable_no_usada) {
        while(!isCancelled()){
            try{
                Thread.sleep( (long) (Math.random() * 100) );
                publishProgress();
            }catch (InterruptedException e){
                cancel(true);
                e.printStackTrace();
            }
        }
        return 1;
    }

    @Override
    protected void onProgressUpdate(Float... variable) {
        v.parentLayout2.removeAllViews();
        WaitingRoomActivity.usuarios.clear();
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
                    JSONArray usuarios = jsonRespuesta.getJSONArray("Usuarios");
                    for (int x = 0; x < usuarios.length(); x++) {
                        JSONObject elemento = usuarios.getJSONObject(x);
                        Usuario usuario = new Usuario(Integer.parseInt(elemento.getString("u_id")), Integer.parseInt(elemento.getString("u_cantidadPartidasJugadas")),
                                Integer.parseInt(elemento.getString("u_cantidadPartidasGanadas")), Integer.parseInt(elemento.getString("u_cantidadAmigos")),
                                Integer.parseInt(elemento.getString("u_nivel")), Integer.parseInt(elemento.getString("u_experiencia")),
                                elemento.getString("u_alias"), elemento.getString("u_password"), elemento.getString("u_rol"),
                                elemento.getString("u_picture"), elemento.getString("u_fechaNacimiento"));

                        agregarUsuario(usuario);

                        WaitingRoomActivity.usuarios.add(usuario);

                    }
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if (ok) {
                        //System.out.println(jsonRespuesta);
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(v.contexto);
                        alerta.setMessage("Error obteniendo los usuarios").setNegativeButton("Reintentar", null).create().show();
                    }
                } catch (JSONException e) {
                    e.getMessage();
                }
            }
        };
        PartidaRequest r = new PartidaRequest(Integer.valueOf(v.partida.getP_id()),respuesta, "UsuariosXPartida");
        RequestQueue cola = Volley.newRequestQueue(v.contexto);
        cola.add(r);
        cancel(true);
    }
    @Override
    protected void onPostExecute(Integer variable_no_usada) {
    }
    @Override
    protected void onCancelled (Integer variable_no_usada) {
    }

    public void agregarUsuario(Usuario usuario){
        LinearLayout userLinear = new LinearLayout(v.contexto);
        userLinear.setOrientation(LinearLayout.HORIZONTAL);
        userLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        userLinear.setBackgroundColor((Color.parseColor("#6D6969")));
        userLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));

        ImageView imageView = new ImageView(v.contexto);

        byte[] bytes = Base64.decode(usuario.getU_picture(), Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imageView.setImageBitmap(bitmap);

        imageView.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(90),
                calcularPixeles(90)));
        userLinear.addView(imageView);

        LinearLayout dataLinear = new LinearLayout(v.contexto);
        dataLinear.setOrientation(LinearLayout.VERTICAL);
        dataLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        dataLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));

        TextView txt_alias = new TextView(v.contexto);
        txt_alias.setText("Alias: " + usuario.getU_alias());
        txt_alias.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        txt_alias.setTextColor(Color.WHITE);
        TextView txt_nivel = new TextView(v.contexto);
        txt_nivel.setText("Nivel: " + usuario.getU_nivel());
        txt_nivel.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        txt_nivel.setTextColor(Color.WHITE);
        TextView txt_fechaNacimiento = new TextView(v.contexto);
        txt_fechaNacimiento.setText("Fec.Nac:" + usuario.getU_fechaNacimiento());
        txt_fechaNacimiento.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        txt_fechaNacimiento.setTextColor(Color.WHITE);
        dataLinear.addView(txt_alias);
        dataLinear.addView(txt_nivel);
        dataLinear.addView(txt_fechaNacimiento);

        v.parentLayout2.addView(userLinear);
        userLinear.addView(dataLinear);
    }
    public int calcularPixeles(int dps) {
        final float scale = v.contexto.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}
