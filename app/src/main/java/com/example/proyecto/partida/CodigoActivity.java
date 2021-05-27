package com.example.proyecto.partida;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.R;
import com.example.proyecto.WaitingRoomActivity;
import com.example.proyecto.usuarios.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class CodigoActivity extends AppCompatActivity {

    private EditText txtCodigo;
    private Button btnIngresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);
        Intent i = getIntent();
        Partida partida = (Partida) i.getSerializableExtra("partida");
        txtCodigo = (EditText) findViewById(R.id.txtcodigo);

        btnIngresar =  (Button) findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!txtCodigo.getText().toString().equals("")){
                    if(txtCodigo.getText().toString().equals(partida.getP_codigo())){
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
                                        Intent nextActivity = new Intent(getApplicationContext(), WaitingRoomActivity.class);
                                        nextActivity.putExtra("administrador", partida.getP_fkUsuario() == Usuario.usuarioLogueado.getU_id() ? "true" : "false");
                                        nextActivity.putExtra("partida", partida);
                                        nextActivity.putExtra("listenerPieSocket", "true");
                                        startActivity(nextActivity);

                                    } else {
                                        AlertDialog.Builder alerta = new AlertDialog.Builder(getApplicationContext());
                                        alerta.setMessage("Error al unirse a la partida").setNegativeButton("Reintentar", null).create().show();
                                    }
                                } catch (JSONException e) {
                                    e.getMessage();
                                }
                            }
                        };
                        PartidaRequest r = new PartidaRequest(Usuario.usuarioLogueado.getU_id(), partida.getP_id(), respuesta);
                        RequestQueue cola = Volley.newRequestQueue(getApplicationContext());
                        cola.add(r);
                    }
                }
                else{
                    Toast.makeText(v.getContext(), "Debes ingresar el código", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}