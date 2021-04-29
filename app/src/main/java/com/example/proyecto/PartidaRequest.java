package com.example.proyecto;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PartidaRequest extends StringRequest {

    private static final String ruta = "http://winadate.atwebpages.com/login.php";
    private Map<String, String> parametros;
    public PartidaRequest(String p_cantidadJugadores, String p_tipo, String p_codigo ,Integer p_nivelMinimo, Integer p_fkUsuario, Response.Listener<String> listener){
        super(Method.POST, ruta, listener, null);
        parametros = new HashMap<>();

        parametros.put("p_tipo", p_tipo+"");
        parametros.put("p_cantidadJugadores", p_cantidadJugadores+"");
        parametros.put("p_fkUsuario", p_fkUsuario+"");
        parametros.put("p_nivelMinimo", p_nivelMinimo+"");
        parametros.put("p_codigo", p_codigo+"");
    }
    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
