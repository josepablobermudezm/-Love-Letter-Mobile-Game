package com.example.proyecto.partida;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PartidaRequest extends StringRequest {

    private static final String ruta = "http://winadate.atwebpages.com/partidas/partidas.php";
    private Map<String, String> parametros;

    //mostramos todas las partidas
    public PartidaRequest(Response.Listener<String> listener) {
        super(Method.POST, ruta, listener, null);
    }

    //eliminamos partidas
    public PartidaRequest(String p_id, Response.Listener<String> listener) {
        super(Method.POST, "http://winadate.atwebpages.com/partidas/deletePartida.php", listener, null);
        parametros = new HashMap<>();
        parametros.put("p_id", p_id+"");
    }

    //agregamos partidas
    public PartidaRequest(Integer p_cantidadJugadores, String p_tipo, String p_codigo ,Integer p_nivelMinimo, Integer p_fkUsuario, Response.Listener<String> listener){
        super(Method.POST, "http://winadate.atwebpages.com/partidas/partida.php", listener, null);
        parametros = new HashMap<>();
        parametros.put("p_cantidadJugadores", p_cantidadJugadores+"");
        parametros.put("p_tipo", p_tipo+"");
        parametros.put("p_codigo", p_codigo+"");
        parametros.put("p_nivelMinimo", p_nivelMinimo+"");
        parametros.put("p_fkUsuario", p_fkUsuario+"");
    }

    //editamos partida
    public PartidaRequest(Partida partida, Response.Listener<String> listener){
        super(Method.POST, "http://winadate.atwebpages.com/partidas/editPartida.php", listener, null);
        parametros = new HashMap<>();
        parametros.put("p_cantidadJugadores", partida.getP_cantidadJugadores()+"");
        parametros.put("p_tipo", partida.getP_tipo()+"");
        parametros.put("p_codigo", partida.getP_codigo()+"");
        parametros.put("p_nivelMinimo", partida.getP_nivelMinimo()+"");
        parametros.put("p_id", partida.getP_id()+"");
    }

    //agregamos en tb_partida_usuario
    public PartidaRequest(int pu_fkUsuario, int pu_fkPartida, Response.Listener<String> listener){
        super(Method.POST, "http://winadate.atwebpages.com/partidas/partidaUsuario.php", listener, null);
        parametros = new HashMap<>();
        parametros.put("pu_fkUsuario", pu_fkUsuario+"");
        parametros.put("pu_fkPartida", pu_fkPartida+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
