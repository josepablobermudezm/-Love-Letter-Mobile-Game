package com.example.proyecto;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter {

    private static final int TYPE_MESSAGE_SENT = 0;
    private static final int TYPE_MESSAGE_RECEIVED = 1;

    private LayoutInflater inflater;
    private List<JSONObject> messages = new ArrayList<>();

    public MessageAdapter (LayoutInflater inflater) {
        this.inflater = inflater;
    }
    public MessageAdapter(){}

    public int getItemViewType(int position) {
        JSONObject message = messages.get(position);
        try {
            if (message.getBoolean("isSent")) {
                if (message.has("message")) {
                    return TYPE_MESSAGE_SENT;
                }
            } else {
                if (message.has("message")){
                    return TYPE_MESSAGE_RECEIVED;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void onBindViewHolder() {
        JSONObject message = messages.get(getItemCount()-1);
        String tipoMensaje = getItemViewType(getItemCount()-1) == 1 ? "Recibido" : "Enviado";
        System.out.println(tipoMensaje);
        try {
            if (message.getBoolean("isSent")) {
                if (message.has("message")) {
                    System.out.println(message.getString("message"));
                }
            } else {
                if (message.has("message")) {
                    System.out.println(message.getString("name"));
                    System.out.println(message.getString("message"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return messages.size();
    }

    public void addItem (JSONObject jsonObject) {
        messages.add(jsonObject);
       onBindViewHolder();
    }
}