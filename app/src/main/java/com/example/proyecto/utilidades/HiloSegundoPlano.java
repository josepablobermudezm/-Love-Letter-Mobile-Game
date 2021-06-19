package com.example.proyecto.utilidades;

import android.content.Context;
import android.os.AsyncTask;

import com.example.proyecto.modelos.Datos;



public class HiloSegundoPlano extends AsyncTask<String, Float, Integer> {

    private View v;

    private static class View {
        Context contexto;
        /*ProgressBar pgb_nivel;
        TextView txv_grados;*/
    }

    public HiloSegundoPlano(Context context /*, TextView txv_grados, ProgressBar pgb_nivel*/) {
        v = new View();
        v.contexto = context;
       /* v.txv_grados = txv_grados;
        v.pgb_nivel = pgb_nivel;
        lista.clear();*/

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... strings) {

        float ejeX, ejeY;
        while (!isCancelled()) {
            try {
                Thread.sleep((long) (Math.random() * 100));
                ejeX = Datos.getInstance().getX();
                ejeY = Datos.getInstance().getY();
                publishProgress(ejeX,ejeY);
            } catch (InterruptedException e) {
                cancel(true);
                e.printStackTrace();
            }
        }
        return 1;
    }

    @Override
    protected void onProgressUpdate(Float... ejes) {
        super.onProgressUpdate(ejes);
        int ejeX = Math.round((ejes[0]));
        int ejeY = Math.round((ejes[1]));
        //numero entero redondeado
        int aX = (Math.abs(ejeX)); // Valor absoluto
        int aY = (Math.abs(ejeY)); // Valor absoluto
        System.out.println(aX+ " "+ aY);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }

    @Override
    protected void onCancelled(Integer integer) {
        super.onCancelled(integer);
    }


}
