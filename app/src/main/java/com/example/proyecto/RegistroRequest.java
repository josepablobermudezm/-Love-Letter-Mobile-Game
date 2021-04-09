package com.example.proyecto;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest {

    private static final String ruta = "https://movilesproyecto.000webhostapp.com/registro.php";
    private Map<String, String> parametros;
    public RegistroRequest(String u_alias, String u_password, String u_rol, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("u_alias", u_alias+"");
        parametros.put("u_password", u_password+"");
        parametros.put("u_rol", u_rol+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
