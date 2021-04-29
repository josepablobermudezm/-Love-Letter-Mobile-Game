package com.example.proyecto.partida;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.R;
import com.example.proyecto.login.LoginActivity;
import com.example.proyecto.registro.RegisterActivity;
import com.example.proyecto.registro.RegistroRequest;
import com.example.proyecto.usuarios.Usuario;
import com.example.proyecto.usuarios.UsuariosActivity;
import com.example.proyecto.usuarios.UsuariosRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

public class CrearPartidaActivity extends AppCompatActivity {

    private TextView mTextView;
    private Partida partida;
    String editActivity;
    Partida editPartida;
    EditText txtNivel;
    EditText txtcantJugadores;
    RadioButton rbPrivado, rbPublica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_partida);

        txtNivel = (EditText) findViewById(R.id.txtNivel);
        txtcantJugadores = (EditText) findViewById(R.id.txtcantJugadores);
        rbPrivado = (RadioButton) findViewById(R.id.rbPrivado);
        rbPublica = (RadioButton) findViewById(R.id.rbPublica);

        mTextView = (TextView) findViewById(R.id.text);
        ((RadioGroup) findViewById(R.id.groupRadio)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ((EditText) findViewById(R.id.txtNivel)).setText("");
                switch (i) {
                    case R.id.rbPrivado:
                        ((EditText) findViewById(R.id.txtNivel)).setEnabled(true);
                        break;
                    case R.id.rbPublica:
                        ((EditText) findViewById(R.id.txtNivel)).setEnabled(false);
                        break;
                }
            }
        });

        Intent i = this.getIntent();
        editActivity = i.getStringExtra("editActivity");



        if(editActivity.equals("true")){
            editPartida = (Partida) i.getSerializableExtra("editPartida");
            txtNivel.setText(String.valueOf(editPartida.getP_nivelMinimo()));
            txtcantJugadores.setText(String.valueOf(editPartida.getP_cantidadJugadores()));
            rbPrivado.setChecked(editPartida.getP_tipo().equals("PR"));
            rbPublica.setChecked(editPartida.getP_tipo().equals("PU"));
        }
    }

    public void volverPartida(View view) {
        Intent nextActivity = new Intent(CrearPartidaActivity.this, PartidaActivity.class);
        CrearPartidaActivity.this.startActivity(nextActivity);
        CrearPartidaActivity.this.finish();
    }

    private Boolean isValidado() {
        if (partida.getP_tipo().equals("PR")) {
            if (partida.getP_nivelMinimo() != null) {
                if (partida.getP_nivelMinimo() >= 0) {
                    if (partida.getP_cantidadJugadores() != null) {
                        if (partida.getP_cantidadJugadores() >= 2 && partida.getP_cantidadJugadores() <= 6) {
                            return true;
                        }else{
                            AlertDialog.Builder alerta = new AlertDialog.Builder(CrearPartidaActivity.this);
                            alerta.setMessage("La cantidad de jugadores tiene que estar entre 2 y 6.")
                                    .setNegativeButton("Reintentar", null).create().show();
                        }
                    } else {
                        AlertDialog.Builder alerta = new AlertDialog.Builder(CrearPartidaActivity.this);
                        alerta.setMessage("No has digitado la cantidad de jugadores.")
                                .setNegativeButton("Reintentar", null).create().show();
                        //No esta entre el rango
                    }
                } else {
                    //Mensaje de jugadores vaccio
                    AlertDialog.Builder alerta = new AlertDialog.Builder(CrearPartidaActivity.this);
                    alerta.setMessage("El nivel mínimo no puede ser negativo.")
                            .setNegativeButton("Reintentar", null).create().show();
                }
            } else {
                //Mensaje de mayor
                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearPartidaActivity.this);
                alerta.setMessage("No has digitado el nivel mínimo.")
                        .setNegativeButton("Reintentar", null).create().show();
            }
        } else {
            //Mensaje de vacio minimo
            if (partida.getP_cantidadJugadores() != null) {
                if (partida.getP_cantidadJugadores() >= 2 && partida.getP_cantidadJugadores() <= 6) {
                    return true;
                } else {
                    //No esta entre el rango
                    AlertDialog.Builder alerta = new AlertDialog.Builder(CrearPartidaActivity.this);
                    alerta.setMessage("La cantidad de jugadores tiene que estar entre 2 y 6.")
                            .setNegativeButton("Reintentar", null).create().show();
                }
            } else {
                //Mesnaje de jugadores vaccio
                AlertDialog.Builder alerta = new AlertDialog.Builder(CrearPartidaActivity.this);
                alerta.setMessage("No has digitado la cantidad de jugadores.")
                        .setNegativeButton("Reintentar", null).create().show();
                //No esta entre el rango
            }
        }
        return false;
    }

    private String generarCodigo(){
        String codigo = "";
        for (int j = 0; j < 7; j++) {
            codigo += (char)Math.floor(Math.random()*(24)+65);
        }
        return codigo;
    }

    public void crearPartida(View view) {
        //creamos partida
        String p_nivelMinimo = ((EditText) findViewById(R.id.txtNivel)).getText().toString();
        String p_cantidadJugadores = ((EditText) findViewById(R.id.txtcantJugadores)).getText().toString();
        String p_codigo = generarCodigo();
        RadioButton rbPublico = ((RadioButton) findViewById(R.id.rbPublica));
        //RadioButton rbPrivate = ((RadioButton) findViewById(R.id.rbPrivado));
        String p_tipo = rbPublico.isChecked() ? "PU" : "PR";
        int p_fkUsuario = Usuario.usuarioLogueado.getU_id();
        //Ingresa los datos de la partida

        if(editActivity.equals("true")) {
            editPartida.setP_cantidadJugadores((p_cantidadJugadores.isEmpty()) ? null : Integer.valueOf(p_cantidadJugadores));
            editPartida.setP_nivelMinimo((p_nivelMinimo.isEmpty()) ? null : Integer.valueOf(p_nivelMinimo));
            editPartida.setP_tipo(p_tipo);
        }
        partida = new Partida(null, (p_cantidadJugadores.isEmpty()) ? null : Integer.valueOf(p_cantidadJugadores), p_tipo, p_codigo, (p_nivelMinimo.isEmpty()) ? null : Integer.valueOf(p_nivelMinimo), p_fkUsuario);

        if (isValidado()) {
            Response.Listener<String> respuesta = new Response.Listener<String>() {
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
                            Intent nextView = new Intent(CrearPartidaActivity.this, PartidaActivity.class);
                            CrearPartidaActivity.this.startActivity(nextView);
                            finish();
                        } else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(CrearPartidaActivity.this);
                            alerta.setMessage("Fallo en el registro")
                                    .setNegativeButton("Reintentar", null).create().show();
                        }
                    } catch (JSONException e) {
                        System.out.println("ERROR " + e.getMessage());
                    }
                }
            };

            if(editActivity.equals("true")){
                PartidaRequest r = new PartidaRequest(editPartida, respuesta);
                RequestQueue cola = Volley.newRequestQueue(CrearPartidaActivity.this);
                cola.add(r);
            }else{
                PartidaRequest r = new PartidaRequest(partida.getP_cantidadJugadores(), partida.getP_tipo(),
                        partida.getP_codigo(), partida.getP_nivelMinimo(), partida.getP_fkUsuario(), respuesta);
                RequestQueue cola = Volley.newRequestQueue(CrearPartidaActivity.this);
                cola.add(r);
            }
        }

    }
}