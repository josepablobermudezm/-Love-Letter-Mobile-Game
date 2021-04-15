package com.example.proyecto;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuariosActivity extends AppCompatActivity {

    private TextView mTextView;
    private LinearLayout parentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);
        mTextView = (TextView) findViewById(R.id.text);
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        Intent i = this.getIntent();
        Usuario usuarioLogueado = new Usuario( Integer.parseInt(i.getStringExtra("u_id")), Integer.parseInt(i.getStringExtra("u_cantidadPartidasJugadas")),
                Integer.parseInt(i.getStringExtra("u_cantidadPartidasGanadas")), Integer.parseInt(i.getStringExtra("u_cantidadAmigos")),
                Integer.parseInt(i.getStringExtra("u_nivel")), Integer.parseInt(i.getStringExtra("u_experiencia")),
                i.getStringExtra("u_alias"), i.getStringExtra("u_password"), i.getStringExtra("u_rol"),
                i.getStringExtra("u_picture"), i.getStringExtra("u_fechaNacimiento"));

        System.out.println(usuarioLogueado.toString());

        // obtenemos todos los usuarios
        Response.Listener<String> respuesta = new Response.Listener<String>(){
            @Override
            public void onResponse(String response){
                try {
                    response = response.replaceFirst("<font>.*?</font>", "");
                    int jsonStart = response.indexOf("{");
                    int jsonEnd = response.lastIndexOf("}");
                    if (jsonStart >= 0 && jsonEnd >= 0 && jsonEnd > jsonStart) {
                        response = response.substring(jsonStart, jsonEnd + 1);
                    }
                    JSONObject jsonRespuesta = new JSONObject(response);
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if(ok){
                        System.out.println(jsonRespuesta);
                    }else{
                        AlertDialog.Builder alerta = new AlertDialog.Builder(UsuariosActivity.this);
                        alerta.setMessage("Fallo en el login").setNegativeButton("Reintentar", null).create().show();
                    }
                }catch(JSONException e){
                    e.getMessage();
                }
            }
        };
        UsuariosRequest r = new UsuariosRequest(respuesta);
        RequestQueue cola = Volley.newRequestQueue(UsuariosActivity.this);
        cola.add(r);

        //calculando dps


        // Agregando usuarios
        LinearLayout userLinear = new LinearLayout(this);
        userLinear.setOrientation(LinearLayout.HORIZONTAL);
        userLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        userLinear.setBackgroundColor(Color.parseColor("#c4c4c4"));
        // userLinear.setPadding(0,padding,0,0);

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.perfil);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(100),
                calcularPixeles(100)));
        userLinear.addView(imageView);

        LinearLayout dataLinear = new LinearLayout(this);
        dataLinear.setOrientation(LinearLayout.VERTICAL);
        dataLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 200));

        TextView txt_alias = new TextView(this);
        txt_alias.setText("JosePablo");
        txt_alias.setTag("nombre" + "5");
        txt_alias.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                50));
        dataLinear.addView(txt_alias);
        parentLayout.addView(userLinear);
        userLinear.addView(dataLinear);
    }

    public int calcularPixeles(int dps){
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }
}