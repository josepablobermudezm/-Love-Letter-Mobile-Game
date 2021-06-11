package com.example.proyecto;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

public class HiloImagenes extends AsyncTask<String, Float, Integer> {
    private View v;
    private static class View {
        Context contexto;
        ImageView img1;
        ImageView img2;
        String carta1, carta2;
    }
    public HiloImagenes(Context contexto, ImageView img1, ImageView img2, String carta1, String carta2) {
        v = new View();
        v.contexto = contexto;
        v.img1 = img1;
        v.img2 = img2;
        v.carta1 = carta1;
        v.carta2 = carta2;
    }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected Integer doInBackground(String... variable_no_usada) {
        int code = this.v.contexto.getResources().getIdentifier(this.v.carta1, "drawable",
                this.v.contexto.getPackageName());
        this.v.img1.setImageResource(code);

        code = this.v.contexto.getResources().getIdentifier(this.v.carta2, "drawable",
                this.v.contexto.getPackageName());
        this.v.img2.setImageResource(code);
        return 1;
    }

    @Override
    protected void onProgressUpdate(Float... ejes) {

    }
    @Override
    protected void onPostExecute(Integer variable_no_usada) {
    }
    @Override
    protected void onCancelled (Integer variable_no_usada) {
    }
}
