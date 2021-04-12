package com.example.proyecto;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegistroRequest extends StringRequest {

    private static final String ruta = "https://movilesproyecto.000webhostapp.com/registro.php";
    private Map<String, String> parametros;

    public RegistroRequest(String u_alias, String fechaNacimiento, String u_password, String u_rol,
            int u_cantidadPartidasJugadas, int u_cantidadPartidasGanadas, int u_cantidadAmigos, int u_nivel, int u_experiencia, Response.Listener<String> listener) {
        super(Request.Method.POST, ruta, listener, null);
        parametros = new HashMap<>();
        parametros.put("u_alias", u_alias + "");
        parametros.put("u_fechaNacimiento", fechaNacimiento + "");
        parametros.put("u_password", u_password + "");
        parametros.put("u_rol", u_rol + "");
        parametros.put("u_cantidadPartidasJugadas", u_cantidadPartidasJugadas + "");
        parametros.put("u_cantidadPartidasGanadas", u_cantidadPartidasGanadas + "");
        parametros.put("u_cantidadAmigos", u_cantidadAmigos + "");
        parametros.put("u_nivel", u_nivel + "");
        parametros.put("u_experiencia", u_experiencia + "");

        System.out.println(parametros);
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }
}
