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

    public UsuariosRequest(Usuario usuario, Response.Listener<String> listener){
        super(Method.POST, "https://movilesproyecto.000webhostapp.com/usuarios/editUsuarios.php", listener, null);
        parametros = new HashMap<>();
        System.out.println("estamos en el edit");
        parametros.put("u_id", usuario.getU_id()+"");
        parametros.put("u_alias", usuario.getU_alias()+"");
        parametros.put("u_fechaNacimiento", usuario.getU_fechaNacimiento()+"");
        parametros.put("u_password", usuario.getU_password()+"");
        parametros.put("u_rol", usuario.getU_rol()+"");
        parametros.put("u_picture", usuario.getU_picture()+"");
        parametros.put("u_cantidadPartidasJugadas", usuario.getU_cantidadPartidasJugadas()+"");
        parametros.put("u_cantidadPartidasGanadas", usuario.getU_cantidadPartidasGanadas()+"");
        parametros.put("u_cantidadAmigos", usuario.getU_cantidadAmigos()+"");
        parametros.put("u_nivel", usuario.getU_nivel()+"");
        parametros.put("u_experiencia", usuario.getU_experiencia()+"");
    }

    @Override
    protected Map<String, String> getParams() {
        return parametros;
    }

}
