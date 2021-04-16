package com.example.proyecto;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UsuariosRequest extends StringRequest {

    private static final String ruta = "https://movilesproyecto.000webhostapp.com/usuarios/usuarios.php";
    Map<String, String> parametros = new HashMap<String, String>();
    public UsuariosRequest(Response.Listener<String> listener){
        super(Method.POST, ruta, listener, null);
    }
    public UsuariosRequest(String u_id, Response.Listener<String> listener){
        super(Method.POST, "https://movilesproyecto.000webhostapp.com/usuarios/deleteUsuarios.php", listener, null);
        parametros = new HashMap<>();
        parametros.put("u_id", u_id+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }

}
