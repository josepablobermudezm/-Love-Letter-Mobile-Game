package com.example.proyecto.controladores;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.proyecto.R;
import com.example.proyecto.modelos.Carta;
import com.example.proyecto.servicios.CartaRequest;
import com.example.proyecto.utilidades.HiloSegundoPlano;
import com.example.proyecto.utilidades.PieSocketListener;
import com.example.proyecto.modelos.Usuario;
import com.example.proyecto.utilidades.Rotacion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;

import okhttp3.WebSocket;

public class GameActivity extends AppCompatActivity {

    private TextView mTextView;
    private LinearLayout parentLayout;
    private LinearLayout parentLayout2;
    private LinearLayout parentLayout3;
    public HorizontalScrollView ScrollHorizontal;
    public LinearLayout parentJugadores;
    public ArrayList<Carta> cartas = new ArrayList();
    private ArrayList<Carta> mazo = new ArrayList();
    private String administrador;
    public PieSocketListener listener;
    public WebSocket ws;
    private Usuario usuario;
    public static TextView txv_turno;
    public static int jugadorActual = 0;
    private ImageView arrow1;
    private ImageView arrow2;
    private ImageView arrow3;
    public static LinearLayout cartasContainer;
    public ImageView img1;
    public ImageView img2;
    public ImageView img3;
    private SensorManager mSensorManager;
    private Rotacion rotacion;
    private HiloSegundoPlano hilo;
    public static boolean cancillerMode = false;
    public static boolean reyMode = false;
    public static boolean principeMode = false;
    public static boolean baronMode = false;
    public static boolean sacerdoteMode = false;
    public static boolean turnoJugado = false;
    public static boolean modoGuardia = false;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
/*        rotacion = new Rotacion(this, mSensorManager);
        hilo = new HiloSegundoPlano(getApplicationContext());

        if( Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB ) {
            hilo.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            hilo.execute();
        }*/


        //Inicializa las funciones del juego
        iniciar();
    }
/*
    @Override
    protected void onResume() {
        super.onResume();
        rotacion.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        rotacion.stop();
    }*/

    private void iniciar() {
        ScrollHorizontal = (HorizontalScrollView) findViewById(R.id.ScrollHorizontal);
        parentJugadores = (LinearLayout) findViewById(R.id.parentJugadores);
        parentLayout2 = (LinearLayout) findViewById(R.id.parentLayout2);
        parentLayout3 = findViewById(R.id.parentLayout3);
        mTextView = (TextView) findViewById(R.id.text);
        txv_turno = (TextView) findViewById(R.id.txv_turno);
        cartasContainer = (LinearLayout) findViewById(R.id.cartasContainer);
        this.ws = WaitingRoomActivity.ws;
        this.listener = WaitingRoomActivity.listener;
        Intent intent = this.getIntent();
        administrador = intent.getStringExtra("administrador");
        usuario = (Usuario) intent.getSerializableExtra("usuario");
        img1 = (ImageView) findViewById(R.id.Carta1);
        img2 = (ImageView) findViewById(R.id.Carta2);
        img3 = (ImageView) findViewById(R.id.Carta3);
        img2.setImageDrawable(null);
        img3.setImageDrawable(null);
        arrow1 = (ImageView) findViewById(R.id.arrow1);
        arrow2 = (ImageView) findViewById(R.id.arrow2);
        arrow3 = (ImageView) findViewById(R.id.arrow3);
        listener.setImg1(img1);
        listener.setImg2(img2);
        listener.setImg3(img3);
        TextView textView = findViewById(R.id.txv_turno);
        listener.setTurno(textView);
        listener.setContext(getApplicationContext());
        if (administrador.equals("true")) {
            listener.setUsuarios(WaitingRoomActivity.usuarios);
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
                        JSONArray cartasJson = jsonRespuesta.getJSONArray("cartas");
                        for (int x = 0; x < cartasJson.length(); x++) {
                            Carta carta = new Carta();
                            switch ((String) cartasJson.get(x)) {
                                case "espia":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(0);
                                    break;
                                case "guardia":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(1);
                                    break;
                                case "sacerdote":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(2);
                                    break;
                                case "baron":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(3);
                                    break;
                                case "doncella":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(4);
                                    break;
                                case "principe":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(5);
                                    break;
                                case "canciller":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(6);
                                    break;
                                case "rey":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(7);
                                    break;
                                case "condesa":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(8);
                                    break;
                                case "princesa":
                                    carta.setNombre((String) cartasJson.get(x));
                                    carta.setValor(9);
                                    break;
                            }
                            cartas.add(carta);
                        }
                        boolean ok = jsonRespuesta.getBoolean("success");
                        if (ok) {
                            int cantidadCartasOpcionales = WaitingRoomActivity.usuarios.size() == 2 ? 3 : 1;

                            //enviamos cartas de mazo opcional
                            for (int i = 0; i < cantidadCartasOpcionales; i++) {
                                Carta carta = cartas.remove(cartas.size() - 1);
                                for (Usuario u : WaitingRoomActivity.usuarios) {
                                    listener.enviarMensaje(ws, "enviarCartas," + carta.getNombre() + "," + carta.getValor() + "," + u.getU_id()
                                            + ",mazoOpcional");
                                }
                            }

                            // aquí le damos una carta inicial a cada jugador
                            for (Usuario u : WaitingRoomActivity.usuarios) {
                                Carta carta = cartas.get(cartas.size() - 1);
                                cartas.remove(carta);
                                listener.enviarMensaje(ws, "enviarCartas," + carta.getNombre() + "," + carta.getValor() + "," + u.getU_id()
                                        + ",mazo");
                            }

                            // aquí enviamos el mazo a todos los jugadores
                            for (Usuario u : WaitingRoomActivity.usuarios) {
                                for (Carta c : cartas) {
                                    listener.enviarMensaje(ws, "enviarCartas," + c.getNombre() + "," + c.getValor() + "," + u.getU_id()
                                            + ",mazoCentral");
                                }
                            }

                        } else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(GameActivity.this);
                            alerta.setMessage("Fallo en la partida").setNegativeButton("Reintentar", null).create().show();
                        }
                    } catch (JSONException e) {
                        System.out.println(e.getMessage());
                    }
                }
            };

            CartaRequest r = new CartaRequest(respuesta);
            RequestQueue cola = Volley.newRequestQueue(GameActivity.this);
            cola.add(r);
        }

        turno();
        txv_turno.setText(" " + WaitingRoomActivity.usuarios.get(jugadorActual).getU_alias());
    }

    public void turno() {
        Calendar today = new GregorianCalendar();
        today.setTime(new Date());

        // Sacar las edades de los jugadores
        for (Usuario u : WaitingRoomActivity.usuarios) {
            String[] fecha = u.getU_fechaNacimiento().split("/", 3);
            Calendar birthDay = new GregorianCalendar(Integer.parseInt(fecha[2]), Integer.parseInt(fecha[1]), Integer.parseInt(fecha[0]));
            int yearsInBetween = today.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
            u.setEdad(yearsInBetween);
        }

        // Ordenar la lista de los jugadores en la partida
        Collections.sort(WaitingRoomActivity.usuarios, new Comparator<Usuario>() {
            @Override
            public int compare(Usuario p1, Usuario p2) {

                if (p1.getEdad() > p2.getEdad()) {
                    return 1;
                }

                if (p1.getEdad() < p2.getEdad()) {
                    return -1;
                }
                return 0;
            }
        });

        // Se le da el turno al jugador más joven
        WaitingRoomActivity.usuarios.get(0).setTurno(true);
    }

    public void repartir(View view) {
        if (Usuario.usuarioLogueado.getU_id() == WaitingRoomActivity.usuarios.get(jugadorActual == -1 ? WaitingRoomActivity.usuarios.size() - 1 : jugadorActual).getU_id()) {
            if (img2.getDrawable() == null || img1.getDrawable() == null) {
                if (cancillerMode) {
                    listener.enviarMensaje(ws, "agregarCarta," + Usuario.usuarioLogueado.getU_id() + ",cancillerMode");
                } else {
                    listener.enviarMensaje(ws, "agregarCarta," + Usuario.usuarioLogueado.getU_id());
                }
                Toast.makeText(view.getContext(), "Es tu turno", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(view.getContext(), "No puedes pedir más cartas", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(view.getContext(), "No es tu turno", Toast.LENGTH_SHORT).show();
        }
    }

    public void actionCarta1(View view) {
        actionCartaGenerico(1);
    }

    public void actionCarta2(View view) {
        actionCartaGenerico(2);
    }

    public void actionCarta3(View view) {
        actionCartaGenerico(3);
    }

    public void actionCartaGenerico(int valor) {
        if (Usuario.usuarioLogueado.getU_id() == WaitingRoomActivity.usuarios.get(jugadorActual == -1 ? WaitingRoomActivity.usuarios.size() - 1 : jugadorActual).getU_id()) {
            String carta1 = "", carta2 = "";
            if (cancillerMode) {
                int code = this.getResources().getIdentifier(Usuario.usuarioLogueado.getMazo().get(valor - 1).getNombre(), "drawable", this.getPackageName());
                if (valor == 1) {
                    img1.setImageDrawable(null);
                    img2.setImageDrawable(null);
                    img3.setImageDrawable(null);
                    img1.setImageResource(code);
                    carta1 = Usuario.usuarioLogueado.getMazo().get(1) != null ? Usuario.usuarioLogueado.getMazo().get(1).getNombre() : "none";
                    carta2 = Usuario.usuarioLogueado.getMazo().get(2) != null ? Usuario.usuarioLogueado.getMazo().get(2).getNombre() : "none";
                } else if (valor == 2) {
                    img1.setImageDrawable(null);
                    img2.setImageDrawable(null);
                    img3.setImageDrawable(null);
                    img1.setImageResource(code);
                    carta1 = Usuario.usuarioLogueado.getMazo().get(0) != null ? Usuario.usuarioLogueado.getMazo().get(0).getNombre() : "none";
                    carta2 = Usuario.usuarioLogueado.getMazo().get(2) != null ? Usuario.usuarioLogueado.getMazo().get(2).getNombre() : "none";
                } else if (valor == 3) {
                    img1.setImageDrawable(null);
                    img2.setImageDrawable(null);
                    img3.setImageDrawable(null);
                    img1.setImageResource(code);
                    carta1 = Usuario.usuarioLogueado.getMazo().get(0) != null ? Usuario.usuarioLogueado.getMazo().get(0).getNombre() : "none";
                    carta2 = Usuario.usuarioLogueado.getMazo().get(1) != null ? Usuario.usuarioLogueado.getMazo().get(1).getNombre() : "none";
                }
                listener.setImg1(img1);
                listener.setImg2(img2);
                listener.setImg3(img3);
                listener.enviarMensaje(ws, "cancillerJugada," + Usuario.usuarioLogueado.getU_id() + "," + valor + "," + carta1 + "," + carta2);
                listener.enviarMensaje(ws, "cambioTurno");
                cancillerMode = false;
            } else if (img2.getDrawable() != null && img1.getDrawable() != null) {
                arrow2.setVisibility(valor == 2 ? View.VISIBLE : View.INVISIBLE);
                arrow1.setVisibility(valor == 2 ? View.INVISIBLE : View.VISIBLE);
            }
        } else {
            Toast.makeText(getApplicationContext(), "No es tu turno", Toast.LENGTH_SHORT).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void cartasJugadas(View view) {
        if (arrow1.getVisibility() == View.VISIBLE) {
            sacarCarta(img1, 0, arrow1);
        } else if (arrow2.getVisibility() == View.VISIBLE) {
            sacarCarta(img2, 1, arrow2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sacarCarta(ImageView img, int valor, ImageView arrow) {
        boolean puedeVotar = true;
        if (((Usuario.usuarioLogueado.getMazo().get(0) != null && Usuario.usuarioLogueado.getMazo().get(0).getNombre().equals("condesa"))
                || (Usuario.usuarioLogueado.getMazo().get(1) != null && Usuario.usuarioLogueado.getMazo().get(1).getNombre().equals("condesa"))) &&
                (((Usuario.usuarioLogueado.getMazo().get(0) != null && Usuario.usuarioLogueado.getMazo().get(0).getNombre().equals("principe"))
                        || (Usuario.usuarioLogueado.getMazo().get(1) != null && Usuario.usuarioLogueado.getMazo().get(1).getNombre().equals("principe"))) ||
                        ((Usuario.usuarioLogueado.getMazo().get(0) != null && Usuario.usuarioLogueado.getMazo().get(0).getNombre().equals("rey"))
                                || (Usuario.usuarioLogueado.getMazo().get(1) != null && Usuario.usuarioLogueado.getMazo().get(1).getNombre().equals("rey"))))
        ) {
            puedeVotar = Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("condesa") ? true : false;
        }
        if (puedeVotar) {
            int code = this.getResources().getIdentifier(Usuario.usuarioLogueado.getMazo().get(valor).getNombre(), "drawable", this.getPackageName());
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(code);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(calcularPixeles(63), calcularPixeles(100)));
            img.setImageDrawable(null);
            arrow.setVisibility(View.INVISIBLE);
            cartasContainer.addView(imageView);
            if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("princesa")) {
                listener.enviarMensaje(ws, "princesaJugada," + Usuario.usuarioLogueado.getU_id());
                Toast.makeText(getApplicationContext(), "Has perdido por haber jugado la princesa", Toast.LENGTH_SHORT).show();
            } else if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("canciller")) {
                Toast.makeText(getApplicationContext(), "Pide cartas del mazo", Toast.LENGTH_SHORT).show();
                cancillerMode = true;
            } else if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("rey")) {
                ScrollHorizontal.setVisibility(View.VISIBLE);
                reyMode = true;
                parentJugadores.removeAllViews();
                ListaJugadoresButton();
            } else if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("principe")) {
                ScrollHorizontal.setVisibility(View.VISIBLE);
                principeMode = true;
                parentJugadores.removeAllViews();
                ListaJugadoresButton();
            } else if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("doncella")) {
                listener.enviarMensaje(ws, "doncellaJugada," + Usuario.usuarioLogueado.getU_id());
            } else if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("baron")) {
                ScrollHorizontal.setVisibility(View.VISIBLE);
                baronMode = true;
                parentJugadores.removeAllViews();
                ListaJugadoresButton();
            } else if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("sacerdote")) {
                sacerdoteMode = true;
                ScrollHorizontal.setVisibility(View.VISIBLE);
                parentJugadores.removeAllViews();
                ListaJugadoresButton();
            } else if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("espia")) {
                listener.enviarMensaje(ws, "espiaJugado," + Usuario.usuarioLogueado.getU_id());
            } else if (Usuario.usuarioLogueado.getMazo().get(valor).getNombre().equals("guardia")) {
                System.out.println("entrando al método de guardia inicial #1");
                modoGuardia = true;
                ScrollHorizontal.setVisibility(View.VISIBLE);
                parentJugadores.removeAllViews();
                ListaJugadoresButton();
            }

            Usuario.usuarioLogueado.getMazo().set(valor, null);

            if (!cancillerMode && !reyMode && !principeMode && !baronMode && !sacerdoteMode && !modoGuardia && !turnoJugado) {
                System.out.println("entrando a aquí sdjfkj");
                listener.enviarMensaje(ws, "cambioTurno");
            }
            turnoJugado = false;
            listener.enviarMensaje(ws, "SacarCarta," + Usuario.usuarioLogueado.getU_id() + "," + valor);
        } else {
            Toast.makeText(getApplicationContext(), "Debes de botar la condesa", Toast.LENGTH_SHORT).show();
        }
    }

    public int calcularPixeles(int dps) {
        final float scale = this.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    private void setMargins(View view, int left, int top, int right, int bottom) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
            p.setMargins(left, top, right, bottom);
            view.requestLayout();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void ListaJugadoresButton() {
        System.out.println(WaitingRoomActivity.usuarios.stream().filter(x -> x.isDoncella() || x.isEliminado()).count() + " ==" + (WaitingRoomActivity.usuarios.size() - 1) + "" + " && " + !principeMode);
        if (WaitingRoomActivity.usuarios.stream().filter(x -> x.isDoncella() || x.isEliminado()).count() == WaitingRoomActivity.usuarios.size() - 1 && !principeMode) {
            listener.enviarMensaje(ws, "cambioTurno");
            sacerdoteMode = false;
            reyMode = false;
            principeMode = false;
            cancillerMode = false;
            baronMode = false;
            modoGuardia = false;
            turnoJugado = true;
            System.out.println("entrando a cambio de turno de carta sin efecto");
        } else {
            for (Usuario u : WaitingRoomActivity.usuarios) {
                if ((u.getU_id() != Usuario.usuarioLogueado.getU_id() || principeMode) && !u.isEliminado() && !u.isDoncella()) {
                    Button button = new Button(this);
                    button.setText(u.getU_alias());
                    button.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    setMargins(button, 10, 5, 0, 5);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (reyMode) {
                                listener.enviarMensaje(ws, "reyJugado," + u.getU_id() + "," + Usuario.usuarioLogueado.getU_id());
                                reyMode = false;
                            } else if (principeMode) {
                                listener.enviarMensaje(ws, "principeJugado," + u.getU_id());
                                principeMode = false;
                            } else if (baronMode) {
                                listener.enviarMensaje(ws, "baronJugado," + u.getU_id() + "," + Usuario.usuarioLogueado.getU_id());
                                baronMode = false;
                            } else if (sacerdoteMode) {
                                listener.enviarMensaje(ws, "sacerdoteJugado," + Usuario.usuarioLogueado.getU_id() + "," +
                                        ((u.getMazo().get(0) != null) ? u.getMazo().get(0).getNombre() : u.getMazo().get(1).getNombre()) + "," +
                                        ((u.getMazo().get(0) != null) ? u.getMazo().get(0).getValor() : u.getMazo().get(1).getValor()) + "," + u.getU_alias()
                                );
                                sacerdoteMode = false;
                            } else if (modoGuardia) {
                                System.out.println("entrando a metodo de guardia");
                                AlertDialog.Builder builder = new AlertDialog.Builder(GameActivity.this);
                                builder.setTitle("Selecciona una carta");
                                builder.setItems(R.array.cartas_array, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        System.out.println("carta seleccionada: ");
                                        System.out.println(which);
                                        listener.enviarMensaje(ws, "guardiaJugado," + u.getU_id() + "," + which);
                                    }
                                });

                                AlertDialog dialog = builder.create();
                                dialog.setCancelable(false);
                                dialog.show();
                            }
                            if (!modoGuardia) {
                                System.out.println("entrando al cambio de turno que no debería de enrtrar");
                                listener.enviarMensaje(ws, "cambioTurno");
                            }
                            ScrollHorizontal.setVisibility(View.INVISIBLE);
                        }
                    });
                    parentJugadores.addView(button);
                }
            }
        }
    }
}