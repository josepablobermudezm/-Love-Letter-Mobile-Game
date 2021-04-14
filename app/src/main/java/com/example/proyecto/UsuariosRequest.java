package com.example.proyecto;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class UsuariosRequest extends StringRequest {

    private static final String ruta = "https://movilesproyecto.000webhostapp.com/usuarios.php";
    public UsuariosRequest(Response.Listener<String> listener){
        super(Method.POST, ruta, listener, null);
    }
}
