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
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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
        Usuario usuarioLogueado = new Usuario(Integer.parseInt(i.getStringExtra("u_id")), Integer.parseInt(i.getStringExtra("u_cantidadPartidasJugadas")),
                Integer.parseInt(i.getStringExtra("u_cantidadPartidasGanadas")), Integer.parseInt(i.getStringExtra("u_cantidadAmigos")),
                Integer.parseInt(i.getStringExtra("u_nivel")), Integer.parseInt(i.getStringExtra("u_experiencia")),
                i.getStringExtra("u_alias"), i.getStringExtra("u_password"), i.getStringExtra("u_rol"),
                i.getStringExtra("u_picture"), i.getStringExtra("u_fechaNacimiento"));

        System.out.println(usuarioLogueado.toString());

        // obtenemos todos los usuarios
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
                    JSONArray usuarios = jsonRespuesta.getJSONArray("usuarios");
                    for (int x = 0; x < usuarios.length(); x++) {
                        JSONObject elemento = usuarios.getJSONObject(x);
                        Usuario usuario = new Usuario(Integer.parseInt(elemento.getString("u_id")), Integer.parseInt(elemento.getString("u_cantidadPartidasJugadas")),
                                Integer.parseInt(elemento.getString("u_cantidadPartidasGanadas")), Integer.parseInt(elemento.getString("u_cantidadAmigos")),
                                Integer.parseInt(elemento.getString("u_nivel")), Integer.parseInt(elemento.getString("u_experiencia")),
                                elemento.getString("u_alias"), elemento.getString("u_password"), elemento.getString("u_rol"),
                                elemento.getString("u_picture"), elemento.getString("u_fechaNacimiento"));
                        agregarUsuarios(usuario);
                        Usuario.usuarios.add(usuario);
                    }
                    boolean ok = jsonRespuesta.getBoolean("success");
                    if (ok) {
                        System.out.println(jsonRespuesta);
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(UsuariosActivity.this);
                        alerta.setMessage("Fallo en el login").setNegativeButton("Reintentar", null).create().show();
                    }
                } catch (JSONException e) {
                    e.getMessage();
                }
            }
        };
        UsuariosRequest r = new UsuariosRequest(respuesta);
        RequestQueue cola = Volley.newRequestQueue(UsuariosActivity.this);
        cola.add(r);

        //calculando dps

    }

    public void agregarUsuarios(Usuario usuario) {
        // Agregando usuarios
        LinearLayout userLinear = new LinearLayout(this);
        userLinear.setOrientation(LinearLayout.HORIZONTAL);
        userLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        userLinear.setBackgroundColor(Color.parseColor("#e8e8e8"));
        userLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.drawable.perfil);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(90),
                calcularPixeles(90)));
        userLinear.addView(imageView);

        LinearLayout dataLinear = new LinearLayout(this);
        dataLinear.setOrientation(LinearLayout.VERTICAL);
        dataLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));
        dataLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));

        TextView txt_alias = new TextView(this);
        txt_alias.setText("Alias: " + usuario.getU_alias());
        txt_alias.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        TextView txt_nivel = new TextView(this);
        txt_nivel.setText("Nivel: " + usuario.getU_nivel());
        txt_nivel.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        TextView txt_fechaNacimiento = new TextView(this);
        txt_fechaNacimiento.setText("Fec.Nac:" + usuario.getU_fechaNacimiento());
        txt_fechaNacimiento.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                50));
        dataLinear.addView(txt_alias);
        dataLinear.addView(txt_nivel);
        dataLinear.addView(txt_fechaNacimiento);

        LinearLayout buttonLinear = new LinearLayout(this);
        buttonLinear.setOrientation(LinearLayout.VERTICAL);
        buttonLinear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        buttonLinear.setPadding(calcularPixeles(10), calcularPixeles(10), calcularPixeles(10), calcularPixeles(10));
        buttonLinear.setGravity(Gravity.RIGHT);

        ImageView editbutton = new ImageView(this);
        editbutton.setImageResource(R.drawable.edit);
        editbutton.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(28),
                calcularPixeles(28)));
        setMargins(editbutton, 0, 0, 0, 35);

        editbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put code on click operation
                System.out.println("se elimina el usuario");
            }
        });

        ImageView deletebutton = new ImageView(this);
        deletebutton.setImageResource(R.drawable.delete);
        deletebutton.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(28),
                calcularPixeles(28)));

        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // put code on click operation
                System.out.println("se elimina el usuario");
            }
        });
        parentLayout.addView(userLinear);
        userLinear.addView(dataLinear);
        buttonLinear.addView(editbutton);
        buttonLinear.addView(deletebutton);
        userLinear.addView(buttonLinear);

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
}