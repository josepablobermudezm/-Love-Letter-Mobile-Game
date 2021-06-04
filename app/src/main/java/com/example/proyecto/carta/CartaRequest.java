package com.example.proyecto.carta;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.proyecto.usuarios.Usuario;

import java.util.HashMap;
import java.util.Map;

public class CartaRequest extends StringRequest {

    private static final String ruta = "http://winadate.atwebpages.com/cartas/cartasIniciales.php";
    Map<String, String> parametros = new HashMap<String, String>();

    public CartaRequest(Response.Listener<String> listener){
        super(Method.POST, ruta, listener, null);
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }

}
