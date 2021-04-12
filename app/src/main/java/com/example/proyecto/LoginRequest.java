package com.example.proyecto;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String ruta = "https://movilesproyecto.000webhostapp.com/login.php";
    private Map<String, String> parametros;
    public LoginRequest(String u_alias, String u_password, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("u_alias", u_alias+"");
        parametros.put("u_password", u_password+"");

        System.out.println(parametros);
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }


}
