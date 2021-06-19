package com.example.proyecto.modelos;

public class Datos {
    private float X;
    private float Y;
    private float Z;

    private static Datos INSTANCE = null;


    public Datos() {
    }

    private static void createInstance() {
        if (INSTANCE == null) {
            synchronized (Datos.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Datos();
                }
            }
        }
    }

    public static Datos getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }


    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public float getZ() {
        return Z;
    }

    public void setZ(float z) {
        Z = z;
    }
}
